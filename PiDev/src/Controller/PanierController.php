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
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use PhpOffice\PhpSpreadsheet\Spreadsheet;
use PhpOffice\PhpSpreadsheet\Writer\Xlsx;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;
use PhpOffice\PhpSpreadsheet\Style\Alignment;
use Symfony\Component\Validator\Constraints\DateTime;

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


    /**
     * @Route("/commande_execel", name="commande_execel")
     */
    public function excel()
    {
        
        $commandes = $this->getDoctrine()->getRepository(Commande::class)->findAll();

        
        /* @var $sheet \PhpOffice\PhpSpreadsheet\Writer\Xlsx\Worksheet */
        

        $spreadsheet = new Spreadsheet();
        $sheet = $spreadsheet->getActiveSheet();
        $sheet->setTitle("All Command");
        $sheet->setCellValue('A1', 'All Command')->mergeCells('A1:M3');
        $columnNames = [
            'First Name',
            'Last Name',
            'Number',
            'Payment',
            'Company',
            'Country',
            'City',
            'street Adress',
            'zip/PostalCode',
            'Code',
            'Date',
            'Etat',
            'Subtotal',
        ];
        $columnLetter = 'A';
        foreach ($columnNames as $columnName) {
            $sheet->setCellValue($columnLetter.'4', $columnName);
            $columnLetter++;
        }
        $columnValues = [];
        foreach ($commandes as $commande) {
            $tab = [ 
                $commande->getFirstName(),
                $commande->getLastName(),
                $commande->getPhoneNumber(),
                $commande->getPayementMethod(),
                $commande->getCompany(),
                $commande->getCountry(),
                $commande->getCity(),
                $commande->getStreetAdress(),
                $commande->getZip_PostalCode(),
                $commande->getCodeCoupon(),
                $commande->getDate(),
                $commande->getEtat(),
                $commande->getSubtotal(),
            ];
            
            array_push($columnValues,$tab);
        }
        $i = 5; // Beginning row for active sheet
        $total = 0;
        foreach ($columnValues as $columnValue) {
            $columnLetter = 'A';
            foreach ($columnValue as $value) {
                $sheet->setCellValue($columnLetter.$i, $value);
                $columnLetter++;
            }
            $i++;
            $total = ((float)$total + (float)$value);
        }
        



        $sheet->setCellValue('A'.$i,'Subtotal')->mergeCells('A'.$i.':L'.$i);
        $sheet->setCellValue('M'.$i,$total);
        $sheet->getStyle('A'.$i)->getFont()->setBold(true);
        $sheet->getStyle('M'.$i)->getFont()->setBold(true);
        $j = $i +1 ;
        $k = $j + 20;
        $sheet->setCellValue('A'.$k,'')->mergeCells('A'.$j.':M'.$k);
    

        $columnLetter = 'A';
        foreach ($columnNames as $columnName) {
            // Center text
            $sheet->getStyle($columnLetter.'4')->getAlignment()->setHorizontal(Alignment::HORIZONTAL_CENTER);
            $sheet->getStyle($columnLetter.'1')->getAlignment()->setHorizontal(Alignment::HORIZONTAL_CENTER);
            $sheet->getStyle($columnLetter.'2')->getAlignment()->setHorizontal(Alignment::HORIZONTAL_CENTER);
            $sheet->getStyle($columnLetter.'1')->getAlignment()->setVertical(Alignment::VERTICAL_CENTER);
            $sheet->getStyle($columnLetter.'2')->getAlignment()->setVertical(Alignment::HORIZONTAL_CENTER);
            // Text in bold
            $sheet->getStyle($columnLetter.'4')->getFont()->setBold(true);
            $sheet->getStyle($columnLetter.'1')->getFont()->setBold(true);
            $sheet->getStyle($columnLetter.'2')->getFont()->setBold(true);
            // Autosize column
            $sheet->getColumnDimension($columnLetter)->setAutoSize(true);
            $columnLetter++;
        }
        

        // Create your Office 2007 Excel (XLSX Format)
        $writer = new Xlsx($spreadsheet);
        
        // Create a Temporary file in the system
        $fileName = 'my_first_excel_symfony4.xlsx';
        $temp_file = tempnam(sys_get_temp_dir(), $fileName);
        
        // Create the excel file in the tmp directory of the system
        $writer->save($temp_file);
        
        // Return the excel file as an attachment
        return  $this->file($temp_file, $fileName, ResponseHeaderBag::DISPOSITION_INLINE);
    }



        /***************  Ajout JSON UNE PANIER ***************/

    
/**
    * @Route("addPanierJson/{id}/{idUser}", name="addPanierJson" ,methods = {"GET", "POST"})
    */
    
    /* pour tester http://127.0.0.1:8000/addPanierJson/200/104?quantite=1 */
    public function addPanierJson($id, $idUser, Request $request,NormalizerInterface $Normalizer) {

        $em=$this->getDoctrine()->getManager();
        $produit = $this->getDoctrine()->getRepository(Produit::class)->find($id);
        $user = $this->getDoctrine()->getRepository(User::class)->find($idUser);

        $panier=new Panier();
        $panier->setQuantite($request->get('quantite'));
        $panier->setUserPanier($user);
        $panier->setProduitPanier($produit);
 
        $em->persist($panier);
        $em->flush();
        $jsonContent=$Normalizer->normalize($panier,'json',['groups' => 'post:read']);
        return new Response(json_encode($jsonContent));
    }
 

    /**
     * @Route("deletePanierJson/{id}", name="deletePanierJson" ,methods = {"GET", "POST"})
     */
    
    /* pour tester http://127.0.0.1:8000/deletePanierJson/528*/

    public function deletePanierJson(Request $request,NormalizerInterface $Normalizer,$id) {
 
        $em=$this->getDoctrine()->getManager();
        $panier=$em->getRepository(Panier::class)->find($id);
        $em->remove($panier);
        $em->flush();
        $jsonContent=$Normalizer->normalize($panier,'json',['groups' => 'post:read']);
        return new Response("Custumor Deleted successfuly".json_encode($jsonContent));
    
    }



    /**
     * @Route("PanierJson/{id}", name="PanierJson")
     */ 
 
     /*pour tester http://127.0.0.1:8000/PanierJson/104 */
    public function PanierJson($id, NormalizerInterface $Normalizer) {

        $repo=$this->getDoctrine()->getRepository(Panier::class);
        $user = $this->getDoctrine()->getRepository(User::class)->find($id);
        $panier=$repo->findBy(
            [ "user_panier" => $user ]
        );
        $jsonContent=$Normalizer->normalize($panier,'json',['groups'=>'post:read']);
         return new Response (json_encode($jsonContent));
    }

    /**
       
      * @Route("updatePanierJson/{id}", name="updatePanierJson" ,methods = {"GET", "POST"})
      */
     
        /*pour tester http://127.0.0.1:8000/updatePanierJson/308?quantite=1&user_panier_id=104&produit_panier_id=200*/
      public function  updatePanierJson(Request $request,NormalizerInterface $Normalizer,$id) {
        $em=$this->getDoctrine()->getManager();
        $panier=$em->getRepository(Panier::class)->find($id);
        $panier->setQuantite($request->get('quantite'));
        /*$panier->getUserPanier()->getId($request->get('user_panier_id'));
        $panier->getProduitPanier()->getId($request->get('produit_panier_id')); */
        $em->flush();
        $jsonContent=$Normalizer->normalize($panier,'json',['groups' => 'post:read']);
        return new Response("Information updated successfuly".json_encode($jsonContent));
    }

    /**
     * @Route("clearPanierJson/{id}", name="clearPanierJson" ,methods = {"GET", "POST"})
     */
    
    /* pour tester http://127.0.0.1:8000/clearPanierJson/104*/

    public function clearPanierJson(Request $request,NormalizerInterface $Normalizer,$id) {
 
        $em=$this->getDoctrine()->getManager();
        $user = $this->getDoctrine()->getRepository(User::class)->find($id);
        $panier=$em->getRepository(Panier::class)->findBy(
            [ "user_panier" => $user ]
        );
        
        foreach($panier as $cart){
           
            
            $em->remove($cart) ; 
        }
        $em->flush();
        $jsonContent=$Normalizer->normalize($panier,'json',['groups' => 'post:read']);
        return new Response("Custumor Deleted successfuly".json_encode($jsonContent));
    
    }



                            /* Command JSON */




    /**
    * @Route("addCommandJson/{id}", name="addCommandJson" ,methods = {"GET", "POST"})
    */
    
    /* pour tester http://127.0.0.1:8000/addCommandJson/104?firstName=Oumaima&lastName=Ayachi&street_Adress="street dar salem"&city=kairouan&zip_PostalCode=3100&country=tunes&company=tunes&phone_Number=54029564&payement_method=bank&date=2022-05-09&delivery_comment=hii&order_comment=hello&codeCoup=1452&subtotal=500*/
    public function  addCommandJson($id, Request $request,NormalizerInterface $Normalizer) {

        $user = $this->getDoctrine()->getRepository(User::class)->find($id);
        $em=$this->getDoctrine()->getManager();

        $commande=new Commande();
        $commande->setFirstName($request->get('firstName'));
        $commande->setLastName($request->get('lastName'));
        $commande->setStreetAdress($request->get('street_Adress'));
        $commande->setCity($request->get('city'));
        $commande->setZip_PostalCode($request->get('zip_PostalCode'));
        $commande->setCountry($request->get('country'));
        $commande->setCompany($request->get('company'));
        $commande->setPhoneNumber($request->get('phone_Number'));
        $commande->setPayementMethod($request->get('payement_method'));
        $commande->setNewsletter("1");
        $date = new \DateTime('@'.strtotime('now'));
        $commande->setDate($date);
        $commande->setDelivery_comment($request->get('delivery_comment'));
        $commande->setOrder_comment($request->get('order_comment'));
        $commande->setEtat("en cours");
        $commande->setCodeCoupon($request->get('codeCoup'));
        $commande->setSubtotal($request->get('subtotal'));
        $commande->setUserCommande($user);
 
        $em->persist($commande);
        $em->flush();
        $jsonContent=$Normalizer->normalize($commande,'json',['groups' => 'post:read']);
        return new Response(json_encode($jsonContent));
 
    }

    /**
     * @Route("CommandeJson", name="CommandeJson")
     */
 
     /*pour tester http://127.0.0.1:8000/CommandeJson */
     public function  CommandeJson(NormalizerInterface $Normalizer) {

        $repo=$this->getDoctrine()->getRepository(Commande::class);
        $commande=$repo->findAll();
        $jsonContent=$Normalizer->normalize($commande,'json',['groups'=>'post:read']);
         return new Response (json_encode($jsonContent));
    }

    /**
     * @Route("clearCommandeJson", name="clearCommandeJson" ,methods = {"GET", "POST"})
     */
    
    /* pour tester http://127.0.0.1:8000/clearCommandeJson*/

    public function clearCommandeJson(Request $request,NormalizerInterface $Normalizer) {
 
        $em=$this->getDoctrine()->getManager();
        $commande=$em->getRepository(Commande::class)->findAll();
        
        foreach($commande as $cmd){
            $em->remove($cmd) ; 
        }
        $em->flush();
        $jsonContent=$Normalizer->normalize($commande,'json',['groups' => 'post:read']);
        return new Response("Custumor Deleted successfuly".json_encode($jsonContent));
    
    }


    /**
     * @Route("removeCommandeJson/{id}", name="removeCommandeJson" ,methods = {"GET", "POST"})
     */
    
    /* pour tester http://127.0.0.1:8000/removeCommandeJson/528*/

    public function removeCommandeJson(Request $request,NormalizerInterface $Normalizer,$id) {
 
        $em=$this->getDoctrine()->getManager();
        $commande=$em->getRepository(Commande::class)->find($id);
        $em->remove($commande);
        $em->flush();
        $jsonContent=$Normalizer->normalize($commande,'json',['groups' => 'post:read']);
        return new Response("Custumor Deleted successfuly".json_encode($jsonContent));
    }


    /**
     * @Route("/commandeExecelJson", name="commandeExecelJson" ,methods = {"GET", "POST"})
     */
    
    /* pour tester http://127.0.0.1:8000/commandeExecelJson*/

    public function commandeExecelJson(Request $request,NormalizerInterface $Normalizer)
    {
        
        $commandes = $this->getDoctrine()->getRepository(Commande::class)->findAll();

        
        /* @var $sheet \PhpOffice\PhpSpreadsheet\Writer\Xlsx\Worksheet */
        

        $spreadsheet = new Spreadsheet();
        $sheet = $spreadsheet->getActiveSheet();
        $sheet->setTitle("All Command");
        $sheet->setCellValue('A1', 'All Command')->mergeCells('A1:M3');
        $columnNames = [
            'First Name',
            'Last Name',
            'Number',
            'Payment',
            'Company',
            'Country',
            'City',
            'street Adress',
            'zip/PostalCode',
            'Code',
            'Date',
            'Etat',
            'Subtotal',
        ];
        $columnLetter = 'A';
        foreach ($columnNames as $columnName) {
            $sheet->setCellValue($columnLetter.'4', $columnName);
            $columnLetter++;
        }
        $columnValues = [];
        foreach ($commandes as $commande) {
            $tab = [ 
                $commande->getFirstName(),
                $commande->getLastName(),
                $commande->getPhoneNumber(),
                $commande->getPayementMethod(),
                $commande->getCompany(),
                $commande->getCountry(),
                $commande->getCity(),
                $commande->getStreetAdress(),
                $commande->getZip_PostalCode(),
                $commande->getCodeCoupon(),
                $commande->getDate(),
                $commande->getEtat(),
                $commande->getSubtotal(),
            ];
            
            array_push($columnValues,$tab);
        }
        $i = 5; // Beginning row for active sheet
        $total = 0;
        foreach ($columnValues as $columnValue) {
            $columnLetter = 'A';
            foreach ($columnValue as $value) {
                $sheet->setCellValue($columnLetter.$i, $value);
                $columnLetter++;
            }
            $i++;
            $total = ((float)$total + (float)$value);
        }
        



        $sheet->setCellValue('A'.$i,'Subtotal')->mergeCells('A'.$i.':L'.$i);
        $sheet->setCellValue('M'.$i,$total);
        $sheet->getStyle('A'.$i)->getFont()->setBold(true);
        $sheet->getStyle('M'.$i)->getFont()->setBold(true);
        $j = $i +1 ;
        $k = $j + 20;
        $sheet->setCellValue('A'.$k,'')->mergeCells('A'.$j.':M'.$k);
    

        $columnLetter = 'A';
        foreach ($columnNames as $columnName) {
            // Center text
            $sheet->getStyle($columnLetter.'4')->getAlignment()->setHorizontal(Alignment::HORIZONTAL_CENTER);
            $sheet->getStyle($columnLetter.'1')->getAlignment()->setHorizontal(Alignment::HORIZONTAL_CENTER);
            $sheet->getStyle($columnLetter.'2')->getAlignment()->setHorizontal(Alignment::HORIZONTAL_CENTER);
            $sheet->getStyle($columnLetter.'1')->getAlignment()->setVertical(Alignment::VERTICAL_CENTER);
            $sheet->getStyle($columnLetter.'2')->getAlignment()->setVertical(Alignment::HORIZONTAL_CENTER);
            // Text in bold
            $sheet->getStyle($columnLetter.'4')->getFont()->setBold(true);
            $sheet->getStyle($columnLetter.'1')->getFont()->setBold(true);
            $sheet->getStyle($columnLetter.'2')->getFont()->setBold(true);
            // Autosize column
            $sheet->getColumnDimension($columnLetter)->setAutoSize(true);
            $columnLetter++;
        }
        

        // Create your Office 2007 Excel (XLSX Format)
        $writer = new Xlsx($spreadsheet);
        
        // Create a Temporary file in the system
        $fileName = 'my_first_excel_symfony4.xlsx';
        $temp_file = tempnam(sys_get_temp_dir(), $fileName);
        
        // Create the excel file in the tmp directory of the system
        $writer->save($temp_file);
        
        // Return the excel file as an attachment

        
        $jsonContent=$Normalizer->normalize($temp_file,'json',['groups' => 'post:read']);
        return new Response("Custumor Deleted successfuly".json_encode($jsonContent));

        //return  $this->file($temp_file, $fileName, ResponseHeaderBag::DISPOSITION_INLINE);
    }

}





