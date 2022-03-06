<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use App\Repository\UserRepository;
use App\Repository\ParticipantsRepository;
use App\Form\EditUserType;
use App\Form\AddUserType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\Participants;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
class AdminController extends AbstractController
{
     /**
      * @Route("/admin", name="admin")
  */
     public function index(): Response
     {
        return $this->render('admin/index.html.twig', [
             'controller_name' => 'AdminController',
        ]);
     }

      /**
     * @Route("utilisateurs", name="liste_utilisateurs")
     */ 
    public function  usersList(UserRepository $users,ParticipantsRepository $participants) {
   return $this->render("admin/listeUsers.html.twig", [
       'users' => $users->findAll(),
       'participants' => $participants->findAll()


   ]);


    } 
      /**
      
     * @Route("addUser", name="add_user" ,methods = {"GET", "POST"})
     */

    public function create(Request $request, EntityManagerInterface $manager,UserPasswordEncoderInterface $encoder)
    {
         $user = new User();
         $form =$this->createForm(AddUserType::class,$user);  
                     $form->handleRequest($request);
                  
                     if($form->isSubmitted() && $form->isValid())

                     {
                        $hash=$encoder->encodePassword($user,$user->getPassword());
                        $user->setPassword($hash);
                        $user->setRoles(["ROLE_ADMIN"]);
                         $manager->persist($user);
                         $manager->flush();
                         return $this->redirectToRoute('liste_utilisateurs');

                     }

        return $this->render('admin/addUser.html.twig',[
            'userAddForm' => $form->createView()
        ]);
         
    }
   /**
     * @Route("/editUser/{id}", name="edit_user")
     */
    public function update(Request $request,$id )
    {
        $user = $this->getDoctrine()->getRepository(User::class)->find($id) ; 
        $form = $this->createForm(editUserType::class,$user) ; 
      
        $form->handleRequest($request) ;
         
        if ($form->isSubmitted()) {
            $em = $this->getDoctrine()->getManager() ; 
            $em->persist($user);
            $em->flush() ; 
            return $this->redirectToRoute('liste_utilisateurs') ; 

        } 

        return $this->render("admin/modifyUser.html.twig", [
            'editUserForm' => $form->createView()
        ]) ;
    }
    /**
 * @Route("utilisateurs/desactivate/{id}", name="desactiver-user")
 * @param User $user

 
 */
    public function desactiverUser(User $user)
{
    $user->setEtat(1);

      $em =$this->getDoctrine()->getManager();
     $em->persist($user);
    $em->flush();
    return $this->redirectToRoute('liste_utilisateurs');
}
/**
 * @Route("utilisateurs/activate/{id}", name="activer-user")
 * @param User $user
 */
public function activerUser(User $user)
{
        $user->setEtat(0);
  $em =$this->getDoctrine()->getManager();
     $em->persist($user);
    $em->flush();
    return $this->redirectToRoute('liste_utilisateurs');
}

      /**
     * @Route("/utilisateurs/delete/{id}", name="delete_user")
     */ 

       public function deleteUser(User $user){
       $em =$this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();
        return $this->redirectToRoute('liste_utilisateurs');
       }

    























       

 ##########################JSON#################################




  /**
     * @Route("utilisateursJson", name="liste_utilisateurs1")
     */ 
    public function  usersList1(NormalizerInterface $Normalizer) {
        $repo=$this->getDoctrine()->getRepository(User::class);
        $users=$repo->findAll();
        $repo1=$this->getDoctrine()->getRepository(Participants::class);
        $participants=$repo1->findAll();
        $jsonContent=$Normalizer->normalize($users,'json',['groups'=>'post:read']);
         return new Response (json_encode($jsonContent));
    
     
     
         } 

 /**
      
     * @Route("addAdminJson/new", name="add_admin" ,methods = {"GET", "POST"})
     */
    

    public function  addAdmin(Request $request,NormalizerInterface $Normalizer) {

       $em=$this->getDoctrine()->getManager();
       $user=new User();
       $user->setUsername($request->get('username'));
       $user->setEmail($request->get('email'));
       $user->setPassword($request->get('password'));
       $user->setRoles(["ROLE_ADMIN"]);

       $em->persist($user);
       $em->flush();
       $jsonContent=$Normalizer->normalize($user,'json',['groups' => 'post:read']);
       return new Response(json_encode($jsonContent));

         } 


/**
      
     * @Route("updateUserJson/{id}", name="update_user_json" ,methods = {"GET", "POST"})
     */
    

    public function  updateUserJson(Request $request,NormalizerInterface $Normalizer,$id) {
        $em=$this->getDoctrine()->getManager();
        $user=$em->getRepository(User::class)->find($id);
        $user->setRoles($request->get('Roles'));
        $em->flush();
        $jsonContent=$Normalizer->normalize($user,'json',['groups' => 'post:read']);
        return new Response("Information updated successfuly".json_encode($jsonContent));


    }

  /**
    * @Route("deleteUserJson/{id}", name="deleteUserJson" ,methods = {"GET", "POST"})
    */
   

   public function deleteUserJson(Request $request,NormalizerInterface $Normalizer,$id) {

    $em=$this->getDoctrine()->getManager();
    $user=$em->getRepository(User::class)->find($id);
    $em->remove($user);
    $em->flush();
    $jsonContent=$Normalizer->normalize($user,'json',['groups' => 'post:read']);
    return new Response("Custumor Deleted successfuly".json_encode($jsonContent));

   }



                      /*************   STATISTIQUE GENDER ***********/

    /**
     * @Route("/dashboard", name="dashboard")
     */ 
    public function  dashboard(UserRepository $user) {
       
       $users = $user ->findAll();
       $gender =[];
       $age = [];
       
       foreach($users as $user){
        $gender[] = $user->getGender();
        $age[] = $user->getAge();
       }
        return $this->render("admin/dashbaord.html.twig", [
            'gender' => Json_encode($gender),
            'age' => json_encode($gender),
                ]);
     
            
         
        }
    }