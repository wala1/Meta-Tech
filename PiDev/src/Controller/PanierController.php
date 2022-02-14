<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
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
        $panier = $this->getDoctrine()->getRepository(Panier::class)->findAll();
        return $this->render("panier/panier.html.twig", ["panier" => $panier]);
    }

    /**
     * @Route("/showPanierAdmin", name="showPanierAdmin");
     */
    public function showPanierAdmin(){
        return $this->render("panier/gestionPanier.html.twig", [
        ]);
    }

}
