<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\UserRepository;
use App\Entity\User;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;

use Symfony\Component\HttpFoundation\Request;
use App\Form\ProfileType ;
use App\Form\ChangePassType ;
use App\Repository\ParticipantsRepository;




class ProfileController extends AbstractController
{
    /**
     * @Route("/account", name="account")
     */
    public function index(ParticipantsRepository $participants): Response
    {
        return $this->render('profile/account.html.twig', [
            'controller_name' => 'ProfileController',
            'participants' => $participants->findAll()

        ]);
    }






 /**
     * @Route("/editProfile", name="edit_account")
     */
    public function update(Request $request)
    {

         $user=$this->getUser();
        $form = $this->createForm(ProfileType::class,$user) ; 

        $form->handleRequest($request) ;
         
        if ($form->isSubmitted() ) {
            $em = $this->getDoctrine()->getManager() ; 
            $em->persist($user);
            $em->flush() ; 
            $this->addFlash('message','The changes have been saved');
            return $this->redirectToRoute('account') ; 

        } 

        return $this->render("profile/editAccount.html.twig", [
            'accountForm' => $form->createView()
        ]) ;
    }



//    /**
//       * @Route("/changePass", name="change_pass")
//      */
//      public function changePass(Request $request,UserPasswordEncoderInterface $encoder)
//      {

//          $user=$this->getUser();
//          $form = $this->createForm(ChangePassType::class,$user) ; 
//          $form->handleRequest($request) ;
//          if ($form->isSubmitted()  ) {

        
//         if( $user->getPassword() == $user->getConfirm_password())
//              {
//                 $hash=$encoder->encodePassword($user,$user->getPassword());
//                 $user->setPassword($hash);

//                 $em = $this->getDoctrine()->getManager() ; 
//             $em->persist($user);
//              $em->flush() ; 
//              $this->addFlash('message','Password is succesfully changed');
//              return $this->redirectToRoute('account') ; 
//              }
//              else
//              {
//                 $this->addFlash('message','Password does not match');

//              }

//          } 
//          return $this->render("profile/changePass.html.twig", [
//              'passForm' => $form->createView()
//          ]) ;


//      }















 /**
     * @Route("/changePass", name="change_pass")
     */
    public function changePass(Request $request,UserPasswordEncoderInterface $encoder)
    {

         if($request->isMethod('Post')){


         $em=$this->getDoctrine()->getManager();
         $user=$this->getUser();
               if($request->request->get('pass') == $request->get('pass2'))
               {
                 $user->setPassword($encoder->encodePassword($user,$request->request->get('pass')));
                 $em->persist($user);
                 $em->flush();
                 $this->addFlash('message','Password changed successfully');
                 return $this->redirectToRoute('account');

               }else
               {
                   $this->addFlash('message','Passwords do not match');
                 }
        
        
        
        
            }







         return $this->render("profile/changePass.html.twig");
      }





/**
     * @Route("/eventUser", name="events_user")
     */
    public function eventUser(ParticipantsRepository $participants): Response
    {
        return $this->render('profile/eventUser.html.twig', [
            'controller_name' => 'ProfileController',
            'participants' => $participants->findAll()

        ]);
    }






}
