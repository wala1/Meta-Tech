<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Commande;

class DashboardController extends AbstractController
{
    /**
     * @Route("/dashboard", name="dashboard")
     */
    public function dashboard(): Response
    {
        $commandes = $this->getDoctrine()->getRepository(Commande::class)->findBy(
            ['etat' => 'delivered']
        );
        return $this->render('dashboard/dashboard.html.twig', [
            'commandes' => $commandes
        ]);
    }
}
