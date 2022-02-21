<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\AvisFormType ;
use Symfony\Component\HttpFoundation\Request;
use App\Repository\AvisRepository ; 
use Symfony\Component\Form\Extension\Core\Type\SubmitType ;

class AvisController extends AbstractController
{
    /**
     * @Route("/avis", name="avis")
     */
    public function index(): Response
    {
        return $this->render('avis/index.html.twig', [
            'controller_name' => 'AvisController',
        ]);
    }


    /* 
     
    public function ajouterProd(Request $request, Avis $avis=null): Response
    {
        if (!$avis) {
            $avis = new Produit();
        }
        
        $form = $this->createForm(AvisFormType::class, $avis) ; 
        $form->add('Submit Review Now', SubmitType::class,[
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
            'form' => $form->createView()
        ]);
    }*/

}
