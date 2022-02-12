<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Produit ; 
use App\Form\ProduitFormType ;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ; 
use App\Repository\ProduitRepository ; 

class ProduitController extends AbstractController
{
    /**
     * @Route("/produit", name="produit")
     */
    public function index(): Response
    {
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findAll() ; 

        return $this->render('produit/all-categories.html.twig', [
            'produits' => $produits
        ]);
    }


    /**
     * @Route("/admin/produit", name="admin_product")
     */
    public function showProds(): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findAll() ; 

        return $this->render('admin/gestionProd.html.twig', [
            'produits' => $produits,
            'table' => 'produits'
        ]);
    }

    


    /**
     * @Route("/product_show/{id}", name="product_show")
     */
    public function productShow($id): Response
    {
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findById($id) ; 

        return $this->render('produit/product.html.twig', [
            'produits' => $produits,
        ]);
    }


    /**
     * @Route("/admin/ajouterProduit", name="ajouter_produit") 
     */
    public function ajouterProd(Request $request, Produit $produit=null): Response
    {
        if (!$produit) {
            $produit = new Produit();
        }
        
        $form = $this->createForm(ProduitFormType::class, $produit) ; 
        $form->add('Ajouter', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
     
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($produit) ; 
            $em->flush() ; 
            return $this->redirectToRoute('admin_product') ; 
        }
        return $this->render('admin/ajouterProd.html.twig', [
            'form' => $form->createView(),
            'editMode' => $produit->getId() !== null,
            'table' => 'produits' 
        ]);
    }


    /**
     * @Route("/delete_prod/{id}", name="delete_prod")
     */
    public function delete_prod($id, ProduitRepository $repository): Response
    {   
        $produit = $repository->find($id) ; 
        $em = $this->getDoctrine()->getManager() ; 
        $em->remove($produit) ; 
        $em->flush() ; 
        return $this->redirectToRoute('admin_product') ;
    }


    /** 
     * @Route("/produit/{id}" , name="modif")
     */
    public function modifierProd(Request $request,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $produit = $em->getRepository(Produit::class)->find($id);
        
          
        $produit_details = $em->getRepository(Produit::class)->findAll() ; 
        
        $form = $this->createForm(ProduitFormType::class, $produit) ; 
         $form->add('Enregistrer', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
        

        if ($form->isSubmitted() && $form->isValid()) {
             
            $em->persist($produit) ;  
            $em->flush() ; 
            return $this->redirectToRoute('admin_product') ; 
        }
        return $this->render('admin/ajouterProd.html.twig', [
            'form' => $form->createView()  ,
            'editMode' => $produit->getId() !== null,
            'produit_details' => $produit_details,
            'table' => 'produits',
            'id' => $id
        ]);
    }


     


     
}
