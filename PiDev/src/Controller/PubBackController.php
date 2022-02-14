<?php

namespace App\Controller;
use App\Repository\PubBackRepository;
use App\Entity\PubBack;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\PubType;

class PubBackController extends AbstractController
{
    /**
     * @Route("/back", name="pub_back")
     */
    public function index(): Response
    {
        return $this->render('pub_back/index.html.twig', [
            'controller_name' => 'PubBackController',
        ]);
    }


/********************************************************************************************************************/

  /**************************************************  FRONT  **************************************************/

 /********************************************************************************************************************/

        /**
     * @Route("/", name="pubFront")
     */ 
    public function  pubFront(PubBackRepository $pubBack) {
        return $this->render("publicity/index.html.twig", [
            'pubBack' => $pubBack->findAll()
        ]);
     
     
         } 


    
/********************************************************************************************************************/

  /**************************************************  BACK  **************************************************/

 /********************************************************************************************************************/

        /**
     * @Route("/publicity", name="publicite")
     */ 
    public function  publicite(PubBackRepository $pubBack) {
        return $this->render("pub_back/index.html.twig", [
            'pubBack' => $pubBack->findAll()
        ]);
     
     
         } 

                    /***************  AJOUTER UNE PUBLICATION ***************/

    /**
     * @Route("/addP", name="addP")
     */
    function Add(Request $request){
        $pubBack=new PubBack();
        $form=$this->createform(PubType::class,$pubBack);
        $form->add('Add New', SubmitType::class) ; 
        $form->handleRequest($request) ; 
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager() ; 
            $em->persist($pubBack) ; 
            $em->flush() ; 
            return $this->redirectToRoute('publicite') ; //lien

        } 

        return $this->render("pub_back/Add.html.twig", [
            'form' => $form->createView()
        ]) ;

   }
                    /***************  MODIFIER UNE PUBLICATION ***************/

   /**
     * @Route("/update/{id}", name="update")
     */
    public function update(Request $request,$id )
    {
        $pubBack = $this->getDoctrine()->getRepository(PubBack::class)->find($id) ; 
        $form = $this->createForm(PubType::class,$pubBack) ; 
        $form->add("Modify",SubmitType::class) ; 
        $form->handleRequest($request) ; 
        if ($form->isSubmitted()) {
            $em = $this->getDoctrine()->getManager() ; 
            $em->persist($pubBack);
            $em->flush() ; 
            return $this->redirectToRoute('publicite') ; 

        } 

        return $this->render("pub_back/Update.html.twig", [
            'form' => $form->createView()
        ]) ;
    }
                    /***************  SUPPRIMER UNE PUBLICATION ***************/

  
   /**
     * @Route("/delete/{id}", name="delete")
     */ 

    public function delete(PubBack $pubBack){
        $em =$this->getDoctrine()->getManager();
         $em->remove($pubBack);
         $em->flush();
         return $this->redirectToRoute('publicite');
        }




}
