<?php

namespace App\Controller;
use  vendor\autoload;
use Twilio\Rest\Client;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\User;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use App\Form\RegistrationType;

use Symfony\Component\HttpFoundation\Request;

use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Doctrine\ORM\EntityManagerInterface;
use App\Security\UsersAuthenticator;
use Symfony\Component\Security\Guard\GuardAuthenticatorHandler;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;

use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;
use App\Form\ResetPassType;
use App\Repository\UserRepository;

use Symfony\Component\DependencyInjection\Exception\InvalidArgumentException;


class SecurityController extends AbstractController
{
    
    /**
     * @Route("/inscription",name="security_registration")
     */
    public function registration(Request $request, EntityManagerInterface $EntityManager,UserPasswordEncoderInterface $encoder , \Swift_Mailer $mailer)
    {
        $user =new User();
        $sid="AC2aec24735743905f9787c482c8ab8ad3";
        $token="ad2834aada2b0863970aa5f4016a663f";
         $form=$this->createForm(RegistrationType::class,$user);
         $form->handleRequest($request);
         if($form->isSubmitted() && $form->isValid())
         {

            $hash=$encoder->encodePassword($user,$user->getPassword());
            $user->setPassword($hash);
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
             
          
            //  ->setBody(
            //     "<p>Bonjour,</p><p>Une demande de réinitialisation de mot de passe a été effectuée . Veuillez cliquer sur le lien suivant :</p>",
            //     'text/html' ) 
            //     ;
               ->setBody(
                  $this->renderView(
               'emails\activation.html.twig' ,['token' => $user->getActivationToken()]
              ) ,
               'text/html'

               ) ;
            $mailer->send($message);


             //////Envoi sms/////////////
           
            //  $twilio_number = "+18623622028";
            //  $client = new Client($sid, $token);
            //   $client->messages->create(
            //           // Where to send a text message (your cell phone?)
            //              '++21622407863',
            //                  array(
            //                'from' => $twilio_number,
            //               'body' => 'test'
            //                     )
            //                  );


             return $this->redirectToRoute('security_login');
            
            }
         return $this->render('security/registration.html.twig' , [
             'form' => $form->createView()
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
     * @Route("/connexion",name="security_login")
     */
    public function login(){
      
        return $this->render('security\login.html.twig');
 
     
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
        
        


  
}
