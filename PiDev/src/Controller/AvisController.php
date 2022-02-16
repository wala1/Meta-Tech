<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\AvisFormType ;
use Symfony\Component\HttpFoundation\Request;
use App\Repository\AvisRepository ; 
use Symfony\Component\Form\Extension\Core\Type\SubmitType ;
use App\Entity\Avis ; 
use App\Entity\Produit ; 
use App\Entity\User ; 

class AvisController extends AbstractController
{
    /**
     * @Route("/avis", name="avise")
     */
    public function index(): Response
    {
        return $this->render('avis/index.html.twig', [
            'controller_name' => 'AvisController',
        ]);
    }


      
    /* 
    public function ajouterProd(): Response
    {
        
            $desc = $_POST['detail'] ;
            $prod = $_POST['prod'] ;
            $rating = $_POST['rating']-10 ;
            if (isset($_POST['submit'])) {

                $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
                $produit = $repo->findById($prod) ; 

                $repo1 = $this->getDoctrine()->getRepository(User::class) ; 
                $user = $repo1->findById(103) ; 

                 

                $avis = new Avis();
                $avis->setIdProd($prod) ;
                $avis->setRatingAvis($rating) ;
                $avis->setDescAvis($desc) ; 
                $avis->setIdUser($user) ; 


                $em = $this->getDoctrine()->getManager();
                $em->persist($avis) ; 
                $em->flush() ;   

            } 

            
 
            return $this->redirectToRoute("avise") ; 
    } 
    */

}
