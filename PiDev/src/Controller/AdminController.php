<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use App\Repository\UserRepository;
use App\Form\EditUserType;
use App\Form\AddUserType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;
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
    public function  usersList(UserRepository $users) {
   return $this->render("admin/listeUsers.html.twig", [
       'users' => $users->findAll()
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
     * @Route("/utilisateurs/delete/{id}", name="delete_user")
     */ 

       public function deleteUser(User $user){
       $em =$this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();
        return $this->redirectToRoute('liste_utilisateurs');
       }

    }
