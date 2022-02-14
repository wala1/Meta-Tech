<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\CategorieRepository ; 
use App\Entity\Categorie ; 
use Symfony\Component\HttpFoundation\Request;
use App\Form\CategorieFormType ;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ; 

class CategorieController extends AbstractController
{
    /**
     * @Route("/admin/categorie", name="admin_categorie")
     */
    public function showCategs(): Response
    {
        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categorie = $repo->findAll() ; 

        return $this->render('admin/gestionProd.html.twig', [
            'categories' => $categorie,
            'table' => 'categories'
        ]);
    }


    /**
     * @Route("/admin/ajouterCategorie", name="ajouter_categorie") 
     */
    public function ajouterCateg(Request $request, Categorie $categorie=null): Response
    {
        if (!$categorie) {
            $categorie = new Categorie();
        }
        
        $form = $this->createForm(CategorieFormType::class, $categorie) ; 
        $form->add('Ajouter', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
     
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($categorie) ; 
            $em->flush() ; 
            return $this->redirectToRoute('admin_categorie') ; 
        }
        return $this->render('admin/ajouterProd.html.twig', [
            'form' => $form->createView(),
            'editMode' => $categorie->getId() !== null,
            'table' => 'categories' 
        ]);
    }   


    /**
     * @Route("/delete_categ/{id}", name="delete_categ")
     */
    public function delete_categ($id, CategorieRepository $repository): Response
    {   
        $categorie = $repository->find($id) ; 
        $em = $this->getDoctrine()->getManager() ; 
        $em->remove($categorie) ; 
        $em->flush() ; 
        return $this->redirectToRoute('admin_categorie') ;
    }


    /** 
     * @Route("/categorie/{id}" , name="modifCateg")
     */
    public function modifierCateg(Request $request,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $categorie = $em->getRepository(Categorie::class)->find($id);

        
        $form = $this->createForm(CategorieFormType::class, $categorie) ; 
         $form->add('Modifier', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
        

        if ($form->isSubmitted() && $form->isValid()) {
             
            $em->persist($categorie) ;  
            $em->flush() ; 
            return $this->redirectToRoute('admin_categorie') ; 
        }
        return $this->render('admin/ajouterProd.html.twig', [
            'form' => $form->createView()  ,
            'editMode' => $categorie->getId() !== null,
            'table' => 'categories'
        ]);
    }


}
