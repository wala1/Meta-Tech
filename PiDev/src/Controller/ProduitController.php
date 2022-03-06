<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use App\Entity\Avis ;
use App\Entity\User ; 
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Produit ;
use App\Entity\Categorie ; 
use App\Entity\SousCategorie ;
use App\Form\ProduitFormType ;
use App\Form\AvisFormType ;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ; 
use App\Repository\ProduitRepository ; 
use App\Repository\AvisRepository ; 
use Symfony\Component\Validator\Constraints\DateTime ; 
use Symfony\Component\Security\Core\Security;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


use Dompdf\Dompdf;
use Dompdf\Options;

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

        $repo = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categories = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $sous_categories = $repo->findAll() ;

        return $this->render('produit/all-categories.html.twig', [
            'produits' => $produits,
            'produits_all' => $produits,
            'avis' => $avis,
            'categories' => $categories,
            'sous_categories' => $sous_categories
        ]);
    }


    /**
     * @Route("/crypto_products", name="crypto_products")
     */
    public function crypto_products(): Response
    {   
         
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findAll() ; 

        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categories = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $sous_categories = $repo->findAll() ;

        return $this->render('produit/all-categories.html.twig', [
            'produits' => $produits,
            'produits_all' => $produits,
            'avis' => $avis,
            'categories' => $categories,
            'sous_categories' => $sous_categories,
            'crypto' => 1
        ]);
    }


    /**
     * @Route("/search_produit", name="search_produit")
     */
    public function search_prods(): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits_all = $repo->findAll() ; 

        $repo = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categories = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $sous_categories = $repo->findAll() ;

        if (isset($_GET['submit'])) {
            $name=$_GET['input'] ; 
            $em = $this->getDoctrine()->getManager() ;
            $produits = $em->createQuery('SELECT p FROM App\Entity\Produit p WHERE p.nom_prod LIKE :nom') 
                            ->setParameter('nom', '%'.$name.'%')  
                            ->getResult() ; 
        }
        return $this->render('produit/all-categories.html.twig', [
            'produits' => $produits,
            'produits_all' => $produits_all,
            'avis' => $avis,
            'categories' => $categories,
            'sous_categories' => $sous_categories
        ]);
    }


    /**
     * @Route("/admin/produit", name="admin_product")
     */
    public function showProds(): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findAll() ; 

        $repo1 = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categories = $repo1->findAll() ; 

        $repo2 = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $sous_categories = $repo2->findAll() ; 

        return $this->render('admin/gestionProd.html.twig', [
            'produits' => $produits,
            'table' => 'produits',
            'categories' => $categories,
            'sous_categories' => $sous_categories
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
        $user = $this->security->getUser();

        $avisNew = new Avis() ; 

        // ================= RECOMMENDATION SYSTEM =====================
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits2 = $repo->findAll() ; 
        
        $avis2 = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis2 = $avis2->findByidUser($user) ;

        $avis3 = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis3 = $avis3->findAll() ;

         
                  

        return $this->render('produit/product.html.twig', [
            'produits' => $produits,
            'produits_all' => $produits2,
            'avis' => $avis,
            'user'=>$user,
            'avis_user' => $avis2,
            'avis_all' => $avis3 
            //'form' => $form->createView() 
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



    // ======================== FILTERS CATEGORIES =============================
    /**
     * @Route("/produit_categorie/{id}", name="produit_categorie")
     */
    public function produit_categorie($id): Response
    {
        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categ = $repo->find($id) ;

        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findBy(array('categorie_prod' => $categ)) ; 

        $repo = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categories = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $sous_categories = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits_all = $repo->findAll() ; 

        return $this->render('produit/all-categories.html.twig', [
            'produits' => $produits,
            'produits_all' => $produits_all,
            'avis' => $avis,
            'categories' => $categories,
            'sous_categories' => $sous_categories
        ]);
    }



    // ======================== FILTERS SUBCATEGORIES =============================
    /**
     * @Route("/produit_souscategorie/{id}", name="produit_souscategorie")
     */
    public function produit_souscategorie($id): Response
    {
        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $souscateg = $repo->find($id) ;

        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findBy(array('sousCategProd' => $souscateg)) ; 

        $repo = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categories = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $sous_categories = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits_all = $repo->findAll() ; 

        return $this->render('produit/all-categories.html.twig', [
            'produits' => $produits,
            'produits_all' => $produits_all,
            'avis' => $avis,
            'categories' => $categories,
            'sous_categories' => $sous_categories
        ]);
    }




    // ======================== FILTERS PRICES =============================
    /**
     * @Route("/produit_prices/{id}", name="produit_prices")
     */
    public function produit_prices($id): Response
    {
        //$repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        //$souscateg = $repo->find($id) ;
        if ($id==1) {
            $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
            $produits = $repo->createQueryBuilder('s') 
                            ->where('s.prix_prod < 100')
                            ->getQuery()
                            ->getResult() ;
        }

        if ($id==2) {
            $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
            $produits = $repo->createQueryBuilder('s') 
                            ->where('s.prix_prod >= 100 and s.prix_prod < 500')
                            ->getQuery()
                            ->getResult() ;
        }

        if ($id==3) {
            $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
            $produits = $repo->createQueryBuilder('s') 
                            ->where('s.prix_prod >= 500 and s.prix_prod < 1000')
                            ->getQuery()
                            ->getResult() ;
        }

        if ($id==4) {
            $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
            $produits = $repo->createQueryBuilder('s') 
                            ->where('s.prix_prod >= 1000 and s.prix_prod < 2000')
                            ->getQuery()
                            ->getResult() ;
        }

        if ($id==5) {
            $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
            $produits = $repo->createQueryBuilder('s') 
                            ->where('s.prix_prod >= 2000 ')
                            ->getQuery()
                            ->getResult() ;
        }

        $repo = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categories = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $sous_categories = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits_all = $repo->findAll() ; 

        return $this->render('produit/all-categories.html.twig', [
            'produits' => $produits,
            'produits_all' => $produits_all,
            'avis' => $avis,
            'categories' => $categories,
            'sous_categories' => $sous_categories
        ]);
    }

    // ============================ API ALI EXPRESS ==========================================

    /**
     * @Route("/aliexpress_products", name="aliexpress_products")
     */
    public function ali_express(Request $request): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits_all = $repo->findAll() ; 

        $repo = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categories = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $sous_categories = $repo->findAll() ;
 

            $curl = curl_init(); 
            curl_setopt_array($curl, [
                CURLOPT_URL => "https://ali-express1.p.rapidapi.com/productsByCategoryV2/205838503?sort_type=default&page=2&page_size=20&sort_order=default",
                CURLOPT_RETURNTRANSFER => true,
                CURLOPT_FOLLOWLOCATION => true,
                CURLOPT_ENCODING => "",
                CURLOPT_MAXREDIRS => 20,
                CURLOPT_TIMEOUT => 30,
                CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
                CURLOPT_CUSTOMREQUEST => "GET",
                CURLOPT_HTTPHEADER => [
                    "x-rapidapi-host: ali-express1.p.rapidapi.com",
                    "x-rapidapi-key: ffccef661fmsh65d228e6a669cf1p1aba63jsne6686937e077"
                ],
            ]);

            $response = curl_exec($curl);
            $err = curl_error($curl);

            curl_close($curl);
            // important
            $json = json_decode($response, true);
            
            if ($err) {
                echo "cURL Error #:" . $err;
            } else {
                $produits =  $json["data"]["searchResult"]["mods"]["itemList"]["content"]   ; 
                return $this->render('produit/all-categories.html.twig', [
                    'produits' => $produits,
                    'produits_all' => $produits_all,
                    'avis' => $avis,
                    'categories' => $categories,
                    'sous_categories' => $sous_categories,
                    'aliexpress' => 1
                ]);
            }      
 
        
        
    }




    // ============================ AMAZON EXPRESS ==========================================

    /**
     * @Route("/amazon_products", name="amazon_products")
     */
    public function amazon(Request $request): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits_all = $repo->findAll() ; 

        $repo = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categories = $repo->findAll() ;

        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $sous_categories = $repo->findAll() ;
 

            $curl = curl_init();

            curl_setopt_array($curl, [
                CURLOPT_URL => "https://amazon23.p.rapidapi.com/product-search?query=PC%20Gamer&country=US",
                CURLOPT_RETURNTRANSFER => true,
                CURLOPT_FOLLOWLOCATION => true,
                CURLOPT_ENCODING => "",
                CURLOPT_MAXREDIRS => 10,
                CURLOPT_TIMEOUT => 30,
                CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
                CURLOPT_CUSTOMREQUEST => "GET",
                CURLOPT_HTTPHEADER => [
                    "x-rapidapi-host: amazon23.p.rapidapi.com",
                    "x-rapidapi-key: ffccef661fmsh65d228e6a669cf1p1aba63jsne6686937e077"
                ],
            ]);

            $response = curl_exec($curl);
            $err = curl_error($curl);

            curl_close($curl);
            // important
            $json = json_decode($response, true);
            
            if ($err) {
                echo "cURL Error #:" . $err;
            } else {
                $produits =  $json["result"]    ; 
                return $this->render('produit/all-categories.html.twig', [
                    'produits' => $produits,
                    'produits_all' => $produits_all,
                    'avis' => $avis,
                    'categories' => $categories,
                    'sous_categories' => $sous_categories,
                    'amazon' => 1
                ]);
            }        
 
        
        
    }





    // ========================= PDF =============================================

    /**
     * @Route("/produit_pdf", name="produit_pdf")
     */
    public function produit_pdf( ): Response
    {
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        
        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        

        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits  = $repo->findAll() ; 
 
        
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('produit/produitPDF.html.twig', [
            'produits' => $produits 
        ]);
        
        // Load HTML to Dompdf
        $dompdf->loadHtml($html);
        
        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => true
        ]);



        
    }

    

     

    // =============================== JSON MOBILE ==================================
    /**
     * @Route("/AllProducts", name="AllProducts")
     */
    public function AllProducts(NormalizerInterface $Normalizer): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findAll() ; 

        $jsonContent = $Normalizer->normalize($produits, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }


    /**
     * @Route("/AddProduct", name="AddProduct")
     */
    public function AddProductJSON(Request $request, NormalizerInterface $Normalizer): Response
    {   
        $em = $this->getDoctrine()->getManager() ; 
        $product = new produit() ; 

        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categorie = $repo->findOneBy(['id'=>$request->get('categorie')]) ; 

        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $souscategorie = $repo->findOneBy(['id'=>$request->get('souscategorie')]) ;

        $product->setNomProd($request->get('nom')) ; 
        $product->setDescProd($request->get('desc')) ;
        $product->setPrixProd($request->get('prix')) ;
        $product->setPromoProd($request->get('promo')) ;
        $product->setImageProd($request->get('image')) ;
        $product->setRatingProd(0) ;
        $product->setCategorieProd($categorie) ;
        $product->setSousCategProd($souscategorie) ;
        $product->setStockProd($request->get('stock')) ;
        $em->persist($product) ; 
        $em->flush() ; 
 
       // http://127.0.0.1:8000/AddProduct?nom=prodTest&desc=GreatIphone&prix=1000&promo=300&image=https://images.frandroid.com/wp-content/uploads/2019/04/iphone-xr-2018.png&categorie=2&souscategorie=2&stock=10
        $jsonContent = $Normalizer->normalize($product, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }


    /**
     * @Route("/ModifyProduct/{id}", name="ModifyProduct")
     */
    public function ModifyProductJSON(Request $request, NormalizerInterface $Normalizer, $id): Response
    {   
        $em = $this->getDoctrine()->getManager() ;  
        $product = $em->getRepository(Produit::class)->find($id) ; 

        if (($request->get('categorie')!=null)) {
            $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
            $categorie = $repo->findOneBy(['id'=>$request->get('categorie')]) ; 
            $product->setCategorieProd($categorie) ;
        }

        if (($request->get('souscategorie'))!=null) {
            $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
            $souscategorie = $repo->findOneBy(['id'=>$request->get('souscategorie')]) ;
            $product->setSousCategProd($souscategorie) ;
        }

        if (($request->get('nom')!=null)) {
            $product->setNomProd($request->get('nom')) ; 
        }

        if (($request->get('desc')!=null)) {
            $product->setDescProd($request->get('desc')) ;
        }

        if (($request->get('prix')!=null)) {
            $product->setPrixProd($request->get('prix')) ;
        }

        if (($request->get('promo')!=null)) {
            $product->setPromoProd($request->get('promo')) ;
        }

        if (($request->get('image')!=null)) {
            $product->setImageProd($request->get('image')) ; 
        }
        
        if (($request->get('stock')!=null)) {
            $product->setStockProd($request->get('stock')) ;
        }
        $em->flush() ; 
 
       // http://127.0.0.1:8000/ModifyProduct/26?nom=prodZZZZ&desc=GreatIphone&prix=1000&promo=300&image=https://images.frandroid.com/wp-content/uploads/2019/04/iphone-xr-2018.png&categorie=2&souscategorie=2&stock=10
        $jsonContent = $Normalizer->normalize($product, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }



    /**
     * @Route("/DeleteProduct/{id}", name="DeleteProduct")
     */
    public function DeleteProductJSON(Request $request , $id , NormalizerInterface $Normalizer): Response
    {   
        $em = $this->getDoctrine()->getManager() ;
        $product = $em->getRepository(Produit::class)->find($id) ; 
        $em->remove($product) ; 
        $em->flush() ;

        // http://127.0.0.1:8000/DeleteProduct/25 
        $jsonContent = $Normalizer->normalize($product, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }

     
}
