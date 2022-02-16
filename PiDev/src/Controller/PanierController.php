<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Produit ; 
use App\Form\PanierProdFormType ;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ; 
use Symfony\Component\Finder\Exception\AccessDeniedException;
use App\Repository\ProduitRepository ;
use App\Entity\Panier; 
use App\Entity\User;
use App\Entity\Commande;
use App\Controller\EntityManager;
use App\Repository\PanierRepository;
use App\Form\CommandeFormType;

class PanierController extends AbstractController
{

    /************************* Front  *******************/
    /**
     * @Route("/cart", name="cart")
     */
    public function index(): Response
    {
        return $this->render('panier/index.html.twig', [
            'controller_name' => 'PanierController',
        ]);
    }

    /**
     * @Route("/cart_show", name="showCart");
     */
    public function showCart(){
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $panier = $this->getDoctrine()->getRepository(Panier::class)->findAll();
        $produits = $this->getDoctrine()->getRepository(Produit::class)->findAll();
        return $this->render("panier/panier.html.twig", [
            "produits" => $produits,
            "panier" => $panier,
            'user' => $user,
            "mode" => "cart"
        ]);
    }

    
    /**
     * @Route("/cart_clear/{id}", name="clearCart");
     */
    public function clearCart($id , ProduitRepository $repository): Response{
        $tab= [];
        $i = 0;
        $ch="";
        for($j=0; $j<strlen($id); $j=$j+1){
            $ch = substr($id, $j, strpos($id,","));
            $tab[$i] = $ch;
            $j=$j+strlen($ch);
            $i=$i+1;
        }
        for( $i=0; $i<count($tab); $i=$i+1){
            $panier = $this->getDoctrine()->getRepository(Panier::class)->find($tab[$i]);
            $em = $this->getDoctrine()->getManager() ; 
            $em->remove($panier) ; 
            $em->flush() ; 
        }
        
        $user = $this->get('security.token_storage')->getToken()->getUser();
        return $this->render("panier/panier.html.twig", [
            "panier"=> array(),
            "user"=> $user,
            "mode" => "cart"

        ]);
    }

    /******************* Front Commande ******************/
    
    /**
     * @Route("/command/{userId}", name="command")
     */
    public function commande($userId, Request $request): Response
    {
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $panier = $this->getDoctrine()->getRepository(Panier::class)->findAll();
        $produits = $this->getDoctrine()->getRepository(Produit::class)->findAll();
        $commande = new Commande();
        $form = $this->createForm(CommandeFormType::class, $commande);
        $form->add('Place Order',SubmitType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
            $em = $this->getDoctrine()->getManager();
            $em->persist($Commande);
            $em->flush();
            return $this->redirectToRoute("command");
        }
        return $this->render("panier/panier.html.twig", [
            "formA"=> $form->createView(),
            "user" => $user,
            "panier" => $panier,
            "produits"=> $produits,
            "mode" => "store"
        ]);
    }

    /******************* Back Commande ******************/

    /**
     * @Route("/command_admin_show", name="showCommandAdmin");
     */
    public function showCommandAdmin(){
        $produits = $this->getDoctrine()->getRepository(Produit::class)->findAll();
        return $this->render("panier/gestionPanier.html.twig", [
            "produits" => $produits
        ]);
    }

    ///** 
    //* @Route("/command_admin_update/{id}" , name="updateCommand")
    //*/

    /*
     function updateCommand(Request $request,$id): Response
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
            return $this->redirectToRoute('showCommandAdmin') ; 
        }
        return $this->render('panier/updatepanier.html.twig', [
            'formA' => $form->createView(),
        ]);
    }*/

}