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
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

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
        

        if (isset($_POST['submit'])) {

            $desc = $_POST['detail'] ; 
            $rating = $_POST['rating']-10 ;
            $id = $_POST['prod'] ; 

            $prod = $avis->getIdProd() ; 


            // ======================== API INAPPROPRIATE WORDS =================================
                $curl = curl_init(); 
                curl_setopt_array($curl, array(
                CURLOPT_URL => "https://api.apilayer.com/bad_words?censor_character=#",
                CURLOPT_HTTPHEADER => array(
                    "Content-Type: text/plain",
                    "apikey: L7P4JUb9Kv8d3VaIWJO85xKWMcPNXaQy"
                ),
                CURLOPT_RETURNTRANSFER => true,
                CURLOPT_ENCODING => "",
                CURLOPT_MAXREDIRS => 10,
                CURLOPT_TIMEOUT => 0,
                CURLOPT_FOLLOWLOCATION => true,
                CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
                CURLOPT_CUSTOMREQUEST => "POST",
                CURLOPT_POSTFIELDS =>  $desc
                ));

                $response = curl_exec($curl);

                // important
                $json = json_decode($response, true);

                curl_close($curl); 

                // ========================== END API ===============================

            $avis->setIdProd($prod) ;
            $avis->setRatingAvis($rating) ;
            $avis->setDescAvis($json["censored_content"]) ;   

            $em->persist($avis) ;  
            $em->flush() ; 
            return $this->redirectToRoute('product_show', array('id'=>$id)) ; 

        }

         

        $user = $this->security->getUser();

        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produits = $repo->findAll() ;
 
       
        if (($user!=null)&&($user==$avis->getIdUser())) {
            return $this->render('avis/editAvis.html.twig', [
                'avis' => $test,
                //'form' => $form->createView(),
                'produits'=>$produits    
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




    /**
     * @Route("/ajouter/avis", name="ajouter_avis")
     */    
    public function ajouterProd(): Response
    {
        
            
            if (isset($_POST['submit'])) {
                $desc = $_POST['detail'] ;
                $prod = $_POST['prod'] ;
                $rating = $_POST['rating']-10 ; 
                $prod = $this->getDoctrine()->getRepository(Produit::class)->findOneBy(['id'=>$prod]) ;  
                $user = $this->security->getUser();

                // ======================== API INAPPROPRIATE WORDS =================================
                $curl = curl_init(); 
                curl_setopt_array($curl, array(
                CURLOPT_URL => "https://api.apilayer.com/bad_words?censor_character=#",
                CURLOPT_HTTPHEADER => array(
                    "Content-Type: text/plain",
                    "apikey: L7P4JUb9Kv8d3VaIWJO85xKWMcPNXaQy"
                ),
                CURLOPT_RETURNTRANSFER => true,
                CURLOPT_ENCODING => "",
                CURLOPT_MAXREDIRS => 10,
                CURLOPT_TIMEOUT => 0,
                CURLOPT_FOLLOWLOCATION => true,
                CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
                CURLOPT_CUSTOMREQUEST => "POST",
                CURLOPT_POSTFIELDS =>  $desc
                ));

                $response = curl_exec($curl);

                // important
                $json = json_decode($response, true);

                curl_close($curl); 

                // ========================== END API ===============================

                $avis = new Avis();
                $avis->setIdProd($prod) ;
                $avis->setRatingAvis($rating) ;
                $avis->setDescAvis($json["censored_content"]) ; 
                $avis->setIdUser($user) ; 
                $avis->setTimeAvis(new \DateTime()) ;

                $em = $this->getDoctrine()->getManager();
                $em->persist($avis) ; 
                $em->flush() ;  
                
                


                $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
                $produits = $repo->findById($prod) ;

                $avis = $this->getDoctrine()->getRepository(Avis::class) ; 
                $avis = $avis->findByidProd($prod) ;


                // ================= RECOMMENDATION SYSTEM =====================
                $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
                $produits2 = $repo->findAll() ; 
                
                $avis2 = $this->getDoctrine()->getRepository(Avis::class) ; 
                $avis2 = $avis2->findByidUser($user) ;

                $avis3 = $this->getDoctrine()->getRepository(Avis::class) ; 
                $avis3 = $avis3->findAll() ;

                return $this->render('produit/product.html.twig', [
                    'produits' => $produits,
                    'avis' => $avis,
                    'user'=>$user,
                    'produits_all' => $produits2,
                    'avis_user' => $avis2,
                    'avis_all' => $avis3
                ]);

            } 

              
             
    } 




    // ======================== JSON MOBILE ============================================
    
    /**
     * @Route("/AddReview", name="AddReview")
     */
    public function AddReview(Request $request, NormalizerInterface $Normalizer): Response
    {   
        $em = $this->getDoctrine()->getManager() ; 
        $avis = new Avis() ; 

        $repo = $this->getDoctrine()->getRepository(Produit::class) ; 
        $produit = $repo->findOneBy(['id'=>$request->get('id_prod')]) ; 

        $repo = $this->getDoctrine()->getRepository(User::class) ; 
        $user = $repo->findOneBy(['username'=>$request->get('id_user')]) ;
        $desc = $request->get('desc') ; 
        // ======================== API INAPPROPRIATE WORDS =================================
                $curl = curl_init(); 
                curl_setopt_array($curl, array(
                CURLOPT_URL => "https://api.apilayer.com/bad_words?censor_character=#",
                CURLOPT_HTTPHEADER => array(
                    "Content-Type: text/plain",
                    "apikey: L7P4JUb9Kv8d3VaIWJO85xKWMcPNXaQy"
                ),
                CURLOPT_RETURNTRANSFER => true,
                CURLOPT_ENCODING => "",
                CURLOPT_MAXREDIRS => 10,
                CURLOPT_TIMEOUT => 0,
                CURLOPT_FOLLOWLOCATION => true,
                CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
                CURLOPT_CUSTOMREQUEST => "POST",
                CURLOPT_POSTFIELDS =>  $desc
                ));

                $response = curl_exec($curl);

                // important
                $json = json_decode($response, true);

                curl_close($curl); 

                // ========================== END API ===============================

            
        $avis->setDescAvis($json["censored_content"]) ;   
 
        $avis->setRatingAvis($request->get('rating')) ; 
        $avis->setIdProd($produit) ;
        $avis->setIdUser($user) ; 
        $avis->setTimeAvis(new \DateTime()) ; 



        $em->persist($avis) ; 
        $em->flush() ; 
 
       // http://127.0.0.1:8000/AddReview?id_prod=29&id_user=88&desc=niceProdGreat&rating=1 
        $jsonContent = $Normalizer->normalize($avis, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }



    /**
     * @Route("/EditReview/{id}", name="EditReview")
     */
    public function EditReview(Request $request, $id , NormalizerInterface $Normalizer): Response
    {   
        $em = $this->getDoctrine()->getManager() ; 
        $avis = $em->getRepository(Avis::class)->find($id) ; 

        $desc = $request->get('desc') ; 
        // ======================== API INAPPROPRIATE WORDS =================================
                $curl = curl_init(); 
                curl_setopt_array($curl, array(
                CURLOPT_URL => "https://api.apilayer.com/bad_words?censor_character=#",
                CURLOPT_HTTPHEADER => array(
                    "Content-Type: text/plain",
                    "apikey: L7P4JUb9Kv8d3VaIWJO85xKWMcPNXaQy"
                ),
                CURLOPT_RETURNTRANSFER => true,
                CURLOPT_ENCODING => "",
                CURLOPT_MAXREDIRS => 10,
                CURLOPT_TIMEOUT => 0,
                CURLOPT_FOLLOWLOCATION => true,
                CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
                CURLOPT_CUSTOMREQUEST => "POST",
                CURLOPT_POSTFIELDS =>  $desc
                ));

                $response = curl_exec($curl);

                // important
                $json = json_decode($response, true);

                curl_close($curl); 

                // ========================== END API ===============================

            
        $avis->setDescAvis($json["censored_content"]) ;  
         
        $avis->setRatingAvis($request->get('rating')) ; 
        $avis->setTimeAvis(new \DateTime()) ; 

        $em->persist($avis) ; 
        $em->flush() ; 
 
       // http://127.0.0.1:8000/EditReview/53?desc=gooooood&rating=1 
        $jsonContent = $Normalizer->normalize($avis, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }



    /**
     * @Route("/AllAvis/{id}", name="AllAvis")
     */
    public function AllAvis(NormalizerInterface $Normalizer, $id): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Avis::class) ; 
        $avis = $repo->findBy(['idProd' => $id ]) ;  
        $jsonContent = $Normalizer->normalize($avis, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }
     
 
    /**
     * @Route("/DeleteAvis/{id}", name="DeleteAvis")
     */
    public function DeleteAvisJSON(Request $request , $id , NormalizerInterface $Normalizer): Response
    {   
        $em = $this->getDoctrine()->getManager() ;
        $avis = $em->getRepository(Avis::class)->find($id) ; 
        $em->remove($avis) ; 
        $em->flush() ;

        // http://127.0.0.1:8000/DeleteProduct/25 
        $jsonContent = $Normalizer->normalize($avis, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }

}
