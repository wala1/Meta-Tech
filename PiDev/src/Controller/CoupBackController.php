<?php

namespace App\Controller;
use App\Repository\CouponRepository;
use App\Entity\Coupon;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Form\CouponType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ;



class CoupBackController extends AbstractController
{
    /**
     * @Route("/coup/back", name="coup_back")
     */
    public function index(): Response
    {
        return $this->render('coup_back/index.html.twig', [
            'controller_name' => 'CoupBackController',
        ]);
    }


     

         /**
      * @Route("/coupon", name="coupon")
     */ 
    public function  Coupon(CouponRepository $coupBack) {
      return $this->render('coup_back/index.html.twig', [
           'coupBack' => $coupBack->findAll()
       ]);
     
     
     } 



     
                    /***************  AJOUTER UN COUPON ***************/

    /**
     * @Route("/addC", name="addC")
     */
    function AddC(Request $request){
        $coupBack=new Coupon();
        $form=$this->createform(CouponType::class,$coupBack);
        $form->add('Add New', SubmitType::class) ; 
        $form->handleRequest($request) ; 
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager() ; 
            $em->persist($coupBack) ; 
            $em->flush() ; 
            return $this->redirectToRoute('coupon') ; //lien

        } 

        return $this->render("coup_back/AddC.html.twig", [
            'form' => $form->createView()
        ]) ;

   }
                    /***************  MODIFIER UN COUPON ***************/

   /**
     * @Route("/modify/{id}", name="modify")
     */
    public function UpdateC(Request $request,$id )
    {
        $coupBack = $this->getDoctrine()->getRepository(Coupon::class)->find($id) ; 
        $form = $this->createForm(CouponType::class,$coupBack) ; 
        $form->add("Modify",SubmitType::class) ; 
        $form->handleRequest($request) ; 
        if ($form->isSubmitted()) {
            $em = $this->getDoctrine()->getManager() ; 
            $em->persist($coupBack);
            $em->flush() ; 
            return $this->redirectToRoute('coupon') ; 

        } 

        return $this->render("coup_back/UpdateC.html.twig", [
            'form' => $form->createView()
        ]) ;
    }
                    /***************  SUPPRIMER UN COUPON ***************/

  
   /**
     * @Route("/deleteC/{id}", name="deleteC")
     */
    public function DeleteC(Coupon $coupBack)
    {
            $em = $this->getDoctrine()->getManager() ; 
            $em->remove($coupBack);
            $em->flush() ; 
            return $this->redirectToRoute('coupon') ; 
    }



}
