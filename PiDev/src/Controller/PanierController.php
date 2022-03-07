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

    /************************* Front Cart *******************/


    /**
     * @Route("/cart_show", name="showCart");
     */
    public function showCart(Request $request): Response{
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
     * @Route("/cart_clear", name="clearCart");
     */
    public function clearCart(ProduitRepository $repository): Response{

        $em = $this->getDoctrine()->getManager() ;
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $panier = $this->getDoctrine()->getRepository(Panier::class)->findBy(
            [ "user_panier" => $user ]
        );
        foreach($panier as $cart){
            $produit = $this->getDoctrine()->getRepository(Produit::class)->findOneBy(
                ['id' => $cart->getProduitPanier()->getId()]
            );
            $produit->setQuantiteProd($produit->getQuantiteProd() + $cart->getQuantite());
            if($produit->getInStock() == 0){
                $produit->setInStock(1);
            }
            
            $em->remove($cart) ; 
        }
        $em->flush(); 
        return $this->render("panier/panier.html.twig", [
            "panier"=> array(),
            "user"=> $user,
            "mode" => "cart",
            "produits" => array(),

        ]);
    }

        /**
     * @Route("/remove_produit_panier/{id}", name="removeProduitPanier");
     */
    public function removeProduitPanier($id, ProduitRepository $repository): Response{

        $em = $this->getDoctrine()->getManager() ;
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $panier = $this->getDoctrine()->getRepository(Panier::class)->find($id);
        $produit = $this->getDoctrine()->getRepository(Produit::class)->findOneBy(
            ['id' => $panier->getProduitPanier()->getId()]
        );
        $produit->setQuantiteProd($produit->getQuantiteProd() + $panier->getQuantite());
        if($produit->getInStock() == 0){
            $produit->setInStock(1);
        }
        $em->remove($panier);
        $em->flush(); 
        return $this->redirectToRoute('showCart') ;
    }



    /**
     * @Route("/Panier_Produit_add/{id}", name="ajouterProduitPanier") 
     */
    public function ajouterProduitPanier($id, Request $request, Panier $panier=null): Response
    {
        if (!$panier) {
            $panier = new Panier();
        }
        
        $panier = new Panier();
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $produit = $this->getDoctrine()->getRepository(Produit::class)->find($id);
        $oldPanier = $this->getDoctrine()->getRepository(Panier::class)->findOneBy(
            [ "user_panier" => $user, "produit_panier" => $produit]
        );
        $form = $this->createForm(PanierProdFormType::class, $panier);
        $form->add('Add to Cart', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
        $panier->setUserPanier($user);

        if($oldPanier){
            $newQuantite = $panier->getQuantite();
            $quantite = $newQuantite + $oldPanier->getQuantite();
            $oldForm = $this->createForm(PanierProdFormType::class, $oldPanier);
            $oldForm->get('quantite')->setData($quantite);
            $oldPanier = $oldForm->getData(); 
            $oldPanier->setQuantite($quantite);
        }
        if ($form->isSubmitted() && $form->isValid()) {
            $panier->setProduitPanier($produit);
            $panier->setUserPanier($user);
            $em = $this->getDoctrine()->getManager();

            if($oldPanier){

                $q = $produit->getQuantiteProd() - $newQuantite;
                if($q > 0){
                    $em->persist($oldPanier);
                    $produit->setQuantiteProd($q);
                }
                else{
                    if($q == 0){
                        $em->persist($oldPanier);
                        $produit->setInStock(0);
                        $produit->setQuantiteProd($q);
                    }else{
                        /* erreur */
                    }
                }
            }else{
                $q = $produit->getQuantiteProd() - $panier->getQuantite() ;
                if($q > 0){
                    $em->persist($panier);
                    $produit->setQuantiteProd($q);
                }else{
                    if($q == 0){
                        $em->persist($panier);
                        $produit->setInStock(0);
                        $produit->setQuantiteProd($q);
                    }else{
                        /* erreur */
                    }
                }
            }
            $em->flush() ;
            return $this->redirectToRoute('showCart') ; 
        }
        return $this->render('panier/addproduitPanier.html.twig', [
            'form' => $form->createView(),
            "produit" => $produit,
        ]);
    }

    
    /** 
     * @Route("/update_produit_panier/{id}" , name="updatePanierProduit")
     */
    public function updatePanierProduit(Request $request,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $panier = $em->getRepository(Panier::class)->find($id);
        $oldQuantite = $panier->getQuantite(); //ancien quantite 
        $form = $this->createForm(PanierProdFormType::class, $panier) ; 
        $form->add('Update', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $produit = $this->getDoctrine()->getRepository(Produit::class)->findOneBy(
                ['id' => $panier->getProduitPanier()->getId()]
            );
            $newQuantite = $form->get('quantite')->getData();
            if($oldQuantite > $newQuantite){
                //Remove panier
                $q = $produit->getQuantiteProd() + ( $oldQuantite - $newQuantite);
                $produit->setQuantiteProd($q);
                if($produit->getInStock() == 0){
                    $produit->setInStock(1);
                }
            }
            else{
                //add panier
                $q = $produit->getQuantiteProd() - ( $newQuantite - $oldQuantite);
                if($q > 0){
                    $produit->setQuantiteProd($q);
                }else{
                    if($q == 0){
                        $produit->setInStock(0);
                        $produit->setQuantiteProd(0);
                    }else{
                        /* erreur */
                    }
                }
            }
            $em->persist($panier);
            $em->flush() ; 
            return $this->redirectToRoute('showCart');
        }
        return $this->render('panier/updatepanier.html.twig', [
            'form' => $form->createView(),
            "panier" => $panier,
        ]);
    }


    /******************* Front Commande ******************/
    
    /**
     * @Route("/command/{userId}", name="command")
     */
    public function commande($userId, Request $request, \Swift_Mailer $mailer): Response
    {
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $panier = $this->getDoctrine()->getRepository(Panier::class)->findBy(
            [ "user_panier" => $user ]
        );
        $produits = $this->getDoctrine()->getRepository(Produit::class)->findAll();
        $commande = new Commande();
        $form1 = $this->createForm(CommandeFormType::class, $commande);
        $form1->add('Place Order',SubmitType::class,[
            'attr' => [
                'class'=>'btn btn-success waves-effect waves-light',
                'style' => 'width:9.2cm;margin-left: 13px;',
            ]
        ]) ;
        $form1->get('etat')->setData("en cours");
        $form1->handleRequest($request);
        if($form1->isSubmitted() && $form1->isValid())
        {
            $em = $this->getDoctrine()->getManager();
            $em->persist($commande);
            $em->flush();
            $message= (new \Swift_Message('Commande added'))
            ->setTo($user->getEmail())
           
            ->setBody(
                "<p>Bonjour,</p><p>Une commande a été passé. </p>",
                'text/html' ) 
                ;
           $mailer->send($message);
            //return $this->redirectToRoute("showCart", ["etat" => "final"]);
            
            return $this->render("panier/pan.html.twig", [
                "form"=> null,
                "user" => $user,
                "panier" => null,
                "produits"=> null,
                "mode" => "success",
                "command" => $commande,
                "form1"=>null
            ]);
        }
        return $this->render("panier/pan.html.twig", [
            "form"=> $form1->createView(),
            "user" => $user,
            "panier" => $panier,
            "produits"=> $produits,
            "mode" => "store",
            "form1"=>null,
        ]);
    }

    /**
     * @Route("/ajouterCommande", name="ajouterCommande")
     */
    public function ajouterCommande(Request $request): Response
    {
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $panier = $this->getDoctrine()->getRepository(Panier::class)->findAll();
        $produits = $this->getDoctrine()->getRepository(Produit::class)->findAll();
        $commande = new Commande();
        $commande->setEtat("en cours");
        $commande->setUserCommande($user);

        $paniers = $this->getDoctrine()->getRepository(Panier::class)->findBy(
            ["user" => $user]
        );
        foreach($Paniers as $paniers){
            $produit = $Paniers->getProduitPanier();
            $commande->addProduit($produit);
        }

        $form = $this->createForm(CommandeFormType::class, $commande);
        $form->add('Place Order',SubmitType::class,[
            'attr' => [
                'class'=>'btn btn-success waves-effect waves-light',
                'style' => 'width:9.5cm',
            ]
        ]) ;
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
            $em = $this->getDoctrine()->getManager();
            $em->persist($Commande);
            $em->flush();
            return $this->redirectToRoute("showCart");
        }
        return $this->render("panier/pan.html.twig", [
            "formA"=> $form->createView(),
            "user" => $user,
            "panier" => $panier,
            "produits"=> $produits
        ]);
    }


    /******************* Back Commande ******************/

    /**
     * @Route("/command_admin_show", name="showCommandAdmin");
     */
    public function showCommandAdmin(){
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $commandes = $this->getDoctrine()->getRepository(Commande::class)->findBy(
            ['etat' => 'en cours']
        );
        return $this->render("panier/gestionCommande.html.twig", [
            "commandes" => $commandes,
            "user" => $user,
            "message" => null,
        ]);
    }

    /**
     * @Route("/command_admin_add", name="addCommandAdmin");
     */
    public function addCommandAdmin(Request $request): Response{
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $produits = $this->getDoctrine()->getRepository(Produit::class)->findAll();
        $commande = new Commande();
        $commande->setEtat("en cours");
        $commande->setUserCommande($user);
        $form = $this->createForm(CommandeFormType::class, $commande);
        $form->add('Place Order',SubmitType::class,[
            'attr' => [
                'class'=>'btn btn-primary waves-effect waves-light',
                'style' => 'width: 9.25cm;margin-top: 10px;'
            ]
        ]) ;
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
            $em = $this->getDoctrine()->getManager();
            /*
            $commands = $this->getDoctrine()->getRepository(Command::class)->findAll();
            $codeCoupon = $this->getDoctrine()->getRepository(Coupon::class)->findAll();            
            $codeCoup = $form->getData('codeCoup');
            if($codeCoup IN $codeCoupon ){
                if($codeCoup NOT IN $commands->getCodeCoup())  {*/
                    $em->persist($commande);
                    $em->flush();
                    if ($form->isSubmitted() && $form->isValid()) {
                        $this->addFlash('success', 'Command Created successfuly!');
                    }
                    return $this->redirectToRoute("showCommandAdmin");
                /*}else{
                    $this->addFlash('success', 'Code coupon used !!!');
                }
            }else{
                $this->addFlash('danger', 'Code coupon does not exist!!!');
            }*/
        }
        return $this->render("panier/addCommande.html.twig", [
            "form"=> $form->createView(),
            "user" => $user,
            "produits"=> $produits
        ]);
    }

    
    /**
     * @Route("/command_admin_clear/{id}", name="clearCommandAdmin");
     */
    public function clearCommandAdmin($id)
    {
        $em=$this->getDoctrine()->getManager();
        $commande = $em->getRepository(Commande::class)->find($id);
        
        $em->remove($commande);
        $em->flush();
        $this->addFlash('success', 'Command removed successfuly!');
        return $this->redirectToRoute("showCommandAdmin");
    }

    /**
     * @Route("/command_admin_clear", name="clearAllCommandAdmin");
     */
    public function clearAllCommandAdmin()
    {
        $em=$this->getDoctrine()->getManager();
        $commandes = $em->getRepository(Commande::class)->findAll();

        $commande =  new Commande();
        foreach($commandes as $commande){
            $em->remove($commande);
        }
        $em->flush();
        $this->addFlash('success', 'All the store cleared successfuly!');
        return $this->redirectToRoute("showCommandAdmin");
    }

    /**
     * @Route("/command_delivered_admin_show", name="showCommandDeliveredAdmin");
     */
    public function showCommandDeliveredAdmin(){
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $commandes = $this->getDoctrine()->getRepository(Commande::class)->findBy(
            ['etat' => 'delivered']
        );
        return $this->render("panier/deliveredCommand.html.twig", [
            "commandes" => $commandes,
            "user" => $user,
            "message" => null,
        ]);
    }

    /**
     * @Route("/changeStatusDelivered/{id}", name="changeStatusDelivered");
     */
    public function changeStatusDelivered(Request $request,$id): Response
    {
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $em=$this->getDoctrine()->getManager();
        $commande = $this->getDoctrine()->getRepository(Commande::class)->find($id);
        $commande->setEtat("delivered");

        $em = $this->getDoctrine()->getManager();
        $em->persist($commande);
        $em->flush();
        $this->addFlash('success', 'Order Delivered Successfuly!');
        return $this->redirectToRoute("showCommandDeliveredAdmin");
    }


    /**
     * @Route("/command_canceled_admin_show", name="showCommandCanceledAdmin");
    */
    public function showCommandCanceledAdmin(){
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $commandes = $this->getDoctrine()->getRepository(Commande::class)->findBy(
            ['etat' => 'canceled']
        );
        return $this->render("panier/canceledCommand.html.twig", [
            "commandes" => $commandes,
            "user" => $user,
            "message" => null,
        ]);
    }

    /**
     * @Route("/changeStatusCancel/{id}", name="changeStatusCancel");
     */
    public function changeStatusCancel(Request $request,$id): Response
    {
        $user = $this->get('security.token_storage')->getToken()->getUser();
        $em=$this->getDoctrine()->getManager();
        $commande = $this->getDoctrine()->getRepository(Commande::class)->find($id);
        $commande->setEtat("canceled");

        $em = $this->getDoctrine()->getManager();
        $em->persist($commande);
        $em->flush();
        $this->addFlash('success', 'Order Canceled Successfuly!');
        return $this->redirectToRoute("showCommandCanceledAdmin");
    }

        
    /** 
    * @Route("/updateCommand/{id}" , name="updateCommand")
    */
   public function updateCommand(Request $request,$id): Response
   {
       $em=$this->getDoctrine()->getManager();
       $commande = $em->getRepository(Commande::class)->find($id); 
       
       $form = $this->createForm(CommandeFormType::class, $commande) ; 
        $form->add('Update Changes', SubmitType::class,[
               'attr' => [
                   'class'=>'btn btn-success waves-effect waves-light'
               ]
           ]) ;
       $form->handleRequest($request);
       if ($form->isSubmitted() && $form->isValid()) {
           $em->persist($commande) ;  
           $em->flush() ;
       }
       return $this->render("panier/addCommande.html.twig", [
           "form"=> $form->createView(),
           "user" => $user,
           "produits"=> $produits
       ]);
   }







    /**
     * @Route("/add_to_cart_new", name="add_to_cart_new") 
     */
    public function ajouterProduitPanier2(): Response
    {
        if (isset($_GET['submit'])) {
            $idProd = $_GET['idProd'] ;
            $qty = $_GET['quantityProd'] ;
            $produit = $this->getDoctrine()->getRepository(Produit::class)->find($idProd);
            $user = $this->get('security.token_storage')->getToken()->getUser();
            
            //$idUser = $user->getId();

            $panier = new Panier() ; 
            $panier->setQuantite($qty) ;
            $panier->setUserPanier($user) ;
            $panier->setProduitPanier($produit) ; 
            

            $em = $this->getDoctrine()->getManager();
            $em->persist($panier) ; 
            $em->flush() ;

            return $this->redirectToRoute('showCart') ; 
            
        } else {
            echo "NOOOOOOOOOO" ; 
        }
        
        

    }



}