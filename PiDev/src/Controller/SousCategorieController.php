<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\SousCategorie ; 
use Symfony\Component\HttpFoundation\Request;
use App\Form\SousCategorieFormType ;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ; 
use App\Repository\SousCategorieRepository ;

class SousCategorieController extends AbstractController
{
    /**
     * @Route("/admin/souscategorie", name="admin_souscategorie")
     */
    public function showSousCategs(): Response
    {
        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $souscategorie = $repo->findAll() ; 

        return $this->render('admin/gestionProd.html.twig', [
            'souscategories' => $souscategorie,
            'table' => 'souscategories'
        ]);
    }


    /**
     * @Route("/admin/ajouterSousCategorie", name="ajouter_souscategorie") 
     */
    public function ajouterSousCateg(Request $request, SousCategorie $souscategorie=null): Response
    {
        if (!$souscategorie) {
            $souscategorie = new SousCategorie();
        }
        
        $form = $this->createForm(SousCategorieFormType::class, $souscategorie) ; 
        $form->add('Ajouter', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
     
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($souscategorie) ; 
            $em->flush() ; 
            return $this->redirectToRoute('admin_souscategorie') ; 
        }
        return $this->render('admin/ajouterProd.html.twig', [
            'form' => $form->createView(),
            'editMode' => $souscategorie->getId() !== null,
            'table' => 'souscategories' 
        ]);
    } 
    
    
    /**
     * @Route("/delete_souscateg/{id}", name="delete_souscateg")
     */
    public function delete_souscateg($id, SousCategorieRepository $repository): Response
    {   
        $souscategorie = $repository->find($id) ; 
        $em = $this->getDoctrine()->getManager() ; 
        $em->remove($souscategorie) ; 
        $em->flush() ; 
        return $this->redirectToRoute('admin_souscategorie') ;
    }


    /** 
     * @Route("/souscategorie/{id}" , name="modifSousCateg")
     */
    public function modifierSousCateg(Request $request,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $souscategorie = $em->getRepository(SousCategorie::class)->find($id);

        
        $form = $this->createForm(SousCategorieFormType::class, $souscategorie) ; 
        $form->add('Modifier', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
        

        if ($form->isSubmitted() && $form->isValid()) {
             
            $em->persist($souscategorie) ;  
            $em->flush() ; 
            return $this->redirectToRoute('admin_souscategorie') ; 
        }
        return $this->render('admin/ajouterProd.html.twig', [
            'form' => $form->createView()  ,
            'editMode' => $souscategorie->getId() !== null,
            'table' => 'souscategories'
        ]);
    }


}
