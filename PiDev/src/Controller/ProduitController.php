<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use App\Entity\Avis ;
use App\Entity\User ; 
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Produit ; 
use App\Form\ProduitFormType ;
use App\Form\AvisFormType ;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ; 
use App\Repository\ProduitRepository ; 
use App\Repository\AvisRepository ; 
use Symfony\Component\Validator\Constraints\DateTime ; 
use Symfony\Component\Security\Core\Security;

class ProduitController extends AbstractController
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
    public function productShow($id, Avis $avis=null, Request $request): Response
    {
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findById($id) ; 

        $avis = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis = $avis->findByidProd($id) ;

        $prod = $this->getDoctrine()->getRepository(Produit::class)->findOneBy(['id'=>$id]) ; 
        //$user = $this->getDoctrine()->getRepository(User::class)->findOneBy(['id'=> 103 ]) ; 
        $user = $this->security->getUser();

        $avisNew = new Avis() ; 
        $form = $this->createForm(AvisFormType::class, $avisNew) ; 
        $form->add('Submit Review', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
 

        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            
            if ($user==null) {
                return $this->redirectToRoute('security_login') ; 
            }

            $avisNew->setIdProd($prod) ; 
            $user = $this->security->getUser();
            $avisNew->setIdUser($user) ; 
            $avisNew->setTimeAvis(new \DateTime()) ; 

            $em = $this->getDoctrine()->getManager(); 
            $em->persist($avisNew) ; 
            $em->flush() ; 
            
            
            return $this->redirectToRoute('product_show', array('id'=>$id, 'produits' => $produits, 'form' => $form->createView(), 'avis' => $avis, 'user'=>$user  )) ;
             

             
        } 
                  

        return $this->render('produit/product.html.twig', [
            'produits' => $produits,
            'avis' => $avis,
            'user'=>$user,
            'form' => $form->createView() 
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
            $produit->setRatingProd(1) ; 
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
            //return $this->redirectToRoute('admin_product') ; 
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
