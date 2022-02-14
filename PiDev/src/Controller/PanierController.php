<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Produit ; 

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
        return $this->render("panier/panier.html.twig", [
            "produits" => $produits
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

}
