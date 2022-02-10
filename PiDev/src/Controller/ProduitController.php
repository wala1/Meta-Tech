<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Produit ; 

class ProduitController extends AbstractController
{
    /**
     * @Route("/", name="produit")
     */
    public function index(): Response
    {
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findAll() ; 

        return $this->render('produit/all-categories.html.twig', [
            'produits' => $produits,
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
}
