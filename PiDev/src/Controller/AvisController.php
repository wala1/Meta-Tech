<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\AvisFormType ;
use Symfony\Component\HttpFoundation\Request;
use App\Repository\AvisRepository ; 
use App\Repository\ProduitRepository ; 
use Symfony\Component\Form\Extension\Core\Type\SubmitType ;
use App\Entity\Avis ; 
use App\Entity\Produit ; 
use App\Entity\User ; 
use Symfony\Component\Security\Core\Security;

class AvisController extends AbstractController
{

    /**
     * @var Security
     */
    private $security;

    public function __construct(Security $security)
    {
       $this->security = $security;
    }



    /**
     * @Route("/avis", name="avise")
     */
    public function index(): Response
    {
        return $this->render('avis/index.html.twig', [
            'controller_name' => 'AvisController',
        ]);
    }


    /** 
     * @Route("/avis/{id}" , name="modifAvis")
     */
    public function modifierAvis(Request $request,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $avis = $em->getRepository(Avis::class)->find($id);
        
        $test = $avis ; 

        $idProd = $avis->getIdProd() ; 
        $id = $idProd->getId() ; 

        $form = $this->createForm(AvisFormType::class, $avis) ; 
         $form->add('Enregistrer', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
        

        if ($form->isSubmitted() && $form->isValid()) {
             
            $em->persist($avis) ;  
            $em->flush() ; 
            return $this->redirectToRoute('product_show', array('id'=>$id)) ; 
        }

        $user = $this->security->getUser();
        if (($user!=null)&&($user==$avis->getIdUser())) {
            return $this->render('avis/editAvis.html.twig', [
                'avis' => $test,
                'form' => $form->createView()     
            ]);
        } else {
             return $this->redirectToRoute('product_show', array('id'=>$id)) ; 
        }
        
    }
    

    /**
     * @Route("/delete_avis/{id}", name="delete_avis")
     */
    public function delete_avis($id, AvisRepository $repository): Response
    {   
        $avis = $repository->find($id) ; 
        $idProd = $avis->getIdProd() ; 
        $id = $idProd->getId() ; 

        $em = $this->getDoctrine()->getManager() ; 
        $em->remove($avis) ; 
        $em->flush() ; 
        return $this->redirectToRoute('product_show', array('id'=>$id)) ; 
    }








    // ================== ADMIN ============================

    /**
     * @Route("/admin/avis", name="admin_avis")
     */
    public function showProds(): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findAll() ;  
        return $this->render('admin/gestionAvis.html.twig', [
            'produits' => $produits,
            'details' => null  
        ]);
    }


    /**
     * @Route("/avis_prod/{id}", name="avis_prod_admin")
     */
    public function showAviss($id,  AvisRepository $repository): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis = $repo->findByIdProd($id) ;
         

        return $this->render('admin/gestionAvis.html.twig', [
            'avis' => $avis ,
            'details' => "avis"  
        ]);
    }



    /**
     * @Route("/delete_avis_admin/{id}", name="delete_avis_avis")
     */
    public function delete_avis_back($id, AvisRepository $repository): Response
    {   
        $avis = $repository->find($id) ; 
        $em = $this->getDoctrine()->getManager() ; 
        $idProd = $avis->getIdProd() ; 
        $idpr = $idProd->getId() ; 


        $em->remove($avis) ; 
        $em->flush() ; 
        return $this->redirectToRoute('avis_prod_admin', array('id'=>$idpr)) ; 
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
