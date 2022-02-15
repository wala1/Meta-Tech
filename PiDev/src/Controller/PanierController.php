<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Produit ; 
use App\Form\PanierProdFormType ;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ; 
use App\Repository\ProduitRepository ;
use App\Entity\Panier; 

class PanierController extends AbstractController
{

    /**
     * @Route("/panier", name="panier")
     */
    public function index(): Response
    {
        return $this->render('panier/index.html.twig', [
            'controller_name' => 'PanierController',
        ]);
    }

    /**
     * @Route("/afficherPanier", name="afficherPanier");
     */
    public function afficherPanier(){
        $produits = $this->getDoctrine()->getRepository(Produit::class)->findAll();
        $panier = $this->getDoctrine()->getRepository(Panier::class)->findAll();
        return $this->render("panier/panier.html.twig", [
            "produits" => $produits,
            "panier" => $panier,
        ]);
    }

    /**
     * @Route("/showPanierAdmin", name="showPanierAdmin");
     */
    public function showPanierAdmin(){
        $produits = $this->getDoctrine()->getRepository(Produit::class)->findAll();
        return $this->render("panier/gestionPanier.html.twig", [
            "produits" => $produits
        ]);
    }

     /** 
     * @Route("/updateProdDepuisPanier/{id}" , name="updateProdDepuisPanier")
     */
     function updateProdDepuisPanier(Request $request,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $produit = $em->getRepository(Produit::class)->find($id);
        $form = $this->createForm(PanierProdFormType::class, $produit) ; 
         $form->add('Save Changes', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($produit) ;  
            $em->flush() ; 
            return $this->redirectToRoute('showPanierAdmin') ; 
        }
        return $this->render('panier/updatepanier.html.twig', [
            'formA' => $form->createView(),
        ]);
    }
}