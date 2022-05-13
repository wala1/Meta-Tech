<?php

namespace App\Controller;
use  vendor\autoload;
use Twilio\Rest\Client;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\User;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use App\Form\RegistrationType;

use Symfony\Component\HttpFoundation\Request;
use App\Entity\PubBack;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Doctrine\ORM\EntityManagerInterface;
use App\Security\UsersAuthenticator;
use Symfony\Component\Security\Guard\GuardAuthenticatorHandler;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;
use App\Form\ResetPassType;
use App\Repository\UserRepository;
use MercurySeries\FlashyBundle\FlashyNotifier;

use Symfony\Component\DependencyInjection\Exception\InvalidArgumentException;


class SecurityController extends AbstractController
{
    
    /**
     * @Route("/inscription",name="security_registration")
     */
    public function registration(Request $request, EntityManagerInterface $EntityManager,UserPasswordEncoderInterface $encoder , \Swift_Mailer $mailer)
    {
        $repo = $this->getDoctrine()->getRepository(PubBack::class) ; 
        $pubBack = $repo->findAll() ;
        //,FlashyNotifier $flashy
        $user =new User();
        $sid="AC2aec24735743905f9787c482c8ab8ad3";
        $token="ad2834aada2b0863970aa5f4016a663f";
         $form=$this->createForm(RegistrationType::class,$user);
         $form->handleRequest($request);
         if($form->isSubmitted() && $form->isValid())
         {

            $hash=$encoder->encodePassword($user,$user->getPassword());
            $user->setPassword($hash);
            $user->setGender("homme");
            $user->setEtat(0);
            $user->setRoles(["ROLE_CLIENT"]);
           


            //On genere le token d'activation

            $user->setActivationToken(md5(uniqid()));
            $EntityManager->persist($user);
            $EntityManager->flush();
            //////////    Envoi Mail d'activation ////////////////

            $message=(new \Swift_Message('Activate account') )
            ->setFrom('aalimi.wala@gmail.com')

             ->setTo($user->getEmail())
               ->setBody(
                  $this->renderView(
               'emails\activation.html.twig' ,['token' => $user->getActivationToken()]
              ) ,
               'text/html'

               ) ;
            $mailer->send($message);


             //////Envoi sms/////////////
           
              $twilio_number = "+18623622028";
              $client_number='++216'.$user->getPhoneNumber();
              $client = new Client($sid, $token);
               $client->messages->create(
                       // Where to send a text message (your cell phone?)
                       $client_number,
                             array(
                           'from' => $twilio_number,
                          'body' => 'Thank you for choosing Meta-Tech'
                                )
                             );

            $this->addFlash('message', 'you have successfully created your account , check your inbox to validate your email ');
            // $request->getSession()->getFlash()  ;
            //          $flashy->getSession()->success('Event created!', 'http://your-awesome-link.com');


             return $this->redirectToRoute('security_login');
            
            }
         return $this->render('security/registration.html.twig' , [
             'form' => $form->createView(),
             'pubBack' => $pubBack
         ]);

    }
        

    
 /**
     * @Route("/activation/{token}", name="activation")
     */
    public function activation($token, UserRepository $usersRepo)
    {
      // On vérifie si un utilisateur a ce token
      $user = $usersRepo->findOneBy(['activation_token' => $token]);

      // Si aucun utilisateur n'existe avec ce token
      if(!$user){
          // Erreur 404
          throw $this->createNotFoundException('Cet utilisateur n\'existe pas');
      }
      // On supprime le token
      $user->setActivationToken(null);
      $entityManager = $this->getDoctrine()->getManager();
      $entityManager->persist($user);
      $entityManager->flush();

      // On envoie un message flash
      $this->addFlash('message', 'Vous avez bien activé votre compte');

      // On retoure à la page de connexion
      return $this->redirectToRoute('security_login');

    }
    
    /**
     * @Route("/login",name="security_login")
     */
    public function login(){

        // $this->addFlash('danger', 'you can\'t have  access to your account , you may be blocked or you didn\'t activate your account yet  ');

        $repo = $this->getDoctrine()->getRepository(PubBack::class) ; 
        $pubBack = $repo->findAll() ;
        return $this->render('security\login.html.twig' , [
           
             'pubBack' => $pubBack
         ]);
 
     
    }
    /**
     * @Route("/deonnexion",name="security_logout")
     
     */
    public function logout(){}


  /**
     * @Route("/oubliPass", name="forgotton_pass")
     
     */
    public function forgottonPass(Request $request,UserRepository $userRepo, TokenGeneratorInterface $tokenGenerator , \Swift_Mailer $mailer){

        $form = $this->createForm(ResetPassType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
            //on recupere les donnes 
            $donnes =$form->getData();
            //on cherche si un utilisateur a cet email
            $user=$userRepo->findOneByEmail($donnes['email']);
            if(!$user){
                $this->addFlash('danger', 'This email adress in invalid');
                $this->redirectToRoute('security_login');
            }
            else {$token = $tokenGenerator->generateToken();
            
                $user->setResetToken($token);
                $EntityManager=$this->getDoctrine()->getManager();
        
                $EntityManager->persist($user);
                $EntityManager->flush();
        
            
            //on genere l'URL de réinitialisation demot de passe 
            $url=$this->generateUrl('reset_pass',['token'=>$token], UrlGeneratorInterface::ABSOLUTE_URL);
            //envoi msg 
            $message= (new \Swift_Message('forgotton password'))
          
            ->setFrom('wala.alimi@esprit.tn')
            ->setTo($user->getEmail())
           
            ->setBody(
                "<p>Bonjour,</p><p>Une demande de réinitialisation de mot de passe a été effectuée . Veuillez cliquer sur le lien suivant : " . $url .'</p>',
                'text/html' ) 
                ;
           // envoi email
           $mailer->send($message);
           //cree msg flash
           $this->addFlash('message', 'you will receive an email with a link to reset your password.');}
           
           return $this->redirectToRoute('security_login'); 
           //
        
        }
        //on envoi vers la page de demande d email 
        return $this->render('security/forgottenPassword.html.twig',[
            'emailForm' => $form->createView()]);
        
            }



            
 
    /**
   * @Route("/resetpass/{token}", name="reset_pass")
   */
 public function resetPassword($token, Request $request, UserPasswordEncoderInterface $passwordEncoder){
    // On cherche l'utilisateur avec le token fourni
    $user = $this->getDoctrine()->getRepository(User::class)->findOneBy(['reset_token' => $token]);

    if(!$user){
        $this->addFlash('danger', 'Token inconnu');
        return $this->redirectToRoute('security_login');
    }

    // Si le formulaire est envoyé en méthode POST
    if($request->isMethod('POST')){
        // On supprime le token
        $user->setResetToken(null);

        // On chiffre le mot de passe
        $user->setPassword($passwordEncoder->encodePassword($user, $request->request->get('password')));
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($user);
        $entityManager->flush();

        $this->addFlash('message', 'Mot de passe modifié avec succès');

        return $this->redirectToRoute('security_login');
    }else{
        return $this->render('security/resetPassword.html.twig', ['token' => $token]);
    }

}
        
###################################### JSON ###################################################"""

/**
 *@Route("/signup", name="security_registration_json")
  */
public function signup(Request $request,UserPasswordEncoderInterface $encoder) {
 $email = $request->query->get( "email");
 $username = $request->query->get("username");
 $password = $request->query->get( "password");
 $imageUser=$request->query->get( "imageUser");
  if(!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    return new Response( "email invalid.");
  }
 $user= new User();
 $user->setUsername($username) ;
 $user->setEmail($email);
 $user->setImageUser($imageUser);
  $user->setPassword($password);

 $user->setEtat(0);
 $user->setRoles(["ROLE_CLIENT"]);
 try {
     $em = $this->getDoctrine()->getManager();
     $em->persist($user);
     $em->flush();
    return new JsonResponse("Account is created",200);//200 ya3ni http result te3 server OK
 }
catch (\Exception $ex) {
    return new Response("exeception ". $ex->getMessage());
}
}

/**
 * @Route("/signin", name="security_login_json")
 */
 
public function signin(Request $request) {
$email =$request->query->get("email");
$password = $request->query->get("password");
$em= $this->getDoctrine()->getManager ();
$user =$em->getRepository(User::class)->findoneBy(['email'=>$email]);

if($user){
     if ($password== $user->getPassword() ){
         if($user->getEtat()==0){

         
     $serializer = new Serializer([new ObjectNormalizer ()]);
      $formatted = $serializer->normalize($user);
     return new JsonResponse ($formatted);
     }else {
        return new Response ("You are blocked");
         }
    }
      else {
       return new Response ("password not found ");
        }
    

}
else {
return new Response( "user not found");
}
}



/** 

*@Route("/editAccountUser", name="security_edit_profile")
*/
public function editAccountUser(Request $request) {
 $id = $request->get( "id");//kina query->get mala get directement c la meme chose
 $username = $request->query->get("username");
 $password = $request->query->get( "password");
 $email = $request->query->get("email");
 //$phoneNumber = $request->query->get("phoneNumber");

 $em=$this->getDoctrine()->getManager();
 $user = $em->getRepository(User::class)->find($id);
 //bon 1 modification bch naselouha bel inage yalni kif tbadl profile tašik tzid image
//  if($request->files>get("photo")!= null) {
//     $file = $request->files->get("photo");//njib inage fi url
//     $fileName=$file->getClientoriginalName ();//nom te3ha
//     //tow nasouha w n7otaho fi dossier uptond ely tet7t fih les inages en principe te7t public folder
//     $file->move(
//       $fileNane
//     );
//     $user->setPhoto($fileName);
// }
    $user->setUsername($username);
    $user->setPassword($password);
$user->setEmail($email);
//$user->setPhoneNumber($phoneNumber);
$user->setEtat(0);
 $user->setRoles(["ROLE_CLIENT"]);

try {
    $em = $this->getDoctrine()->getManager ();
    $em->persist($user);
    $em->flush();
    return new JsonResponse("profile was updates successfully",200);//290 ya3ni http result tas server OK
}catch (\Exception $ex) {
    return new Response("failed ".$ex->getMessage());
}
}

/** 
 *@Route("/getPasswordByEmail", name="app_password_email")

*/
public function getPassswordByEmail(Request $request) {
 $email = $request->get('email');
 $user = $this->getDoctrine()->getManager()->getRepository(User::class)->findOneBy(['email'=>$email]);
 if($user) {
     $password = $user->getPassword();
     $serializer = new Serializer([new ObjectNormalizer()]);
     $formatted = $serializer->normalize($password);
     return new JsonResponse($formatted);
 return new Response("user not found");



 }
}


  
}
