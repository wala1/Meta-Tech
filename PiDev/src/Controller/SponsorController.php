<?php

namespace App\Controller;
use App\Repository\SponsorRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ;
use App\Form\SponsorType;
use App\Entity\Sponsor;
use Symfony\Component\HttpFoundation\Request;


class SponsorController extends AbstractController
{
    /**
     * @Route("/sponsor", name="sponsor")
     */
    public function index(): Response
    {
        return $this->render('sponsor/index.html.twig', [
            'controller_name' => 'SponsorController',
        ]);
    }



     

         /**
      * @Route("/sponsor", name="sponsor")
     */ 
    public function  Sponsor(SponsorRepository $sponsor) {
        return $this->render('sponsor/index.html.twig', [
             'sponsor' => $sponsor->findAll()
         ]);
       
       
       } 
  
  
  
       
                      /***************  AJOUTER UN SPONSOR ***************/
  
      /**
       * @Route("/addS", name="addS")
       */
      function AddS(Request $request){
          $sponsor=new Sponsor();
          $form=$this->createform(SponsorType::class,$sponsor);
          $form->add('Add New', SubmitType::class) ; 
          $form->handleRequest($request) ; 
          if ($form->isSubmitted() && $form->isValid()) {
              $em = $this->getDoctrine()->getManager() ; 
              $em->persist($sponsor) ; 
              $em->flush() ; 
              return $this->redirectToRoute('sponsor') ; //lien
  
          } 
  
          return $this->render("sponsor/AddS.html.twig", [
              'form' => $form->createView()
          ]) ;
  
     }
                      /***************  MODIFIER UN Sponsor ***************/
  
     /**
       * @Route("/modify/{id}", name="modify")
       */
      public function UpdateS(Request $request,$id )
      {
          $sponsor = $this->getDoctrine()->getRepository(Sponsor::class)->find($id) ; 
          $form = $this->createForm(SponsorType::class,$sponsor) ; 
          $form->add("Modify",SubmitType::class) ; 
          $form->handleRequest($request) ; 
          if ($form->isSubmitted()) {
              $em = $this->getDoctrine()->getManager() ; 
              $em->persist($sponsor);
              $em->flush() ; 
              return $this->redirectToRoute('sponsor') ; 
  
          } 
  
          return $this->render("sponsor/UpdateS.html.twig", [
              'form' => $form->createView()
          ]) ;
      }
                      /***************  SUPPRIMER UN Sponsor ***************/
  
    
   /**
     * @Route("/deleteS/{id}", name="deleteS")
     */
    public function DeleteS(Sponsor $sponsor)
    {
            $em = $this->getDoctrine()->getManager() ; 
            $em->remove($sponsor);
            $em->flush() ; 
            return $this->redirectToRoute('sponsor') ; 
    }

}
