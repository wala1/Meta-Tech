<?php

namespace App\Controller;
use App\Repository\PubBackRepository;
use App\Entity\PubBack;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\PubType;
use Knp\Component\Pager\PaginatorInterface; // Nous appelons le bundle KNP Paginator
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
class PubBackController extends AbstractController
{
    /**
     * @Route("/back", name="pub_back")
     */
    public function index(Request $request, PaginatorInterface $paginator)
    {
        $donnees = $this->getDoctrine()->getRepository(PubBack::class)->findBy([],['created_at' => 'desc']);
        $pubBack = $paginator->paginate(
            $donnees, // Requête contenant les données à paginer (ici nos articles)
            $request->query->getInt('page', 1), // Numéro de la page en cours, passé dans l'URL, 1 si aucune page
            // Nombre de résultats par page
        );



        return $this->render('pub_back/index.html.twig', [
            'controller_name' => 'PubBackController',
        ]);
    }


/********************************************************************************************************************/

  /**************************************************  FRONT  **************************************************/

 /********************************************************************************************************************/

        /**
     * @Route("/", name="pubFront")
     */ 
    public function  pubFront(PubBackRepository $pubBack) {
        



        return $this->render("publicity/index.html.twig", [
            'pubBack' => $pubBack->findAll(),
        ]);
     
     
         } 


    /**
     * @Route("/storelocator", name="storelocator")
     */ 
    public function  storelocator(PubBackRepository $pubBack) {
        return $this->render("publicity/storelocator.html.twig", [
            'pubBack' => $pubBack->findAll()
        ]);
     
     
         }  
    
    /**
     * @Route("/news", name="news")
     */ 
    public function  news(PubBackRepository $pubBack) {
        
        $curl = curl_init();

        curl_setopt_array($curl, [
            CURLOPT_URL => "https://gaming-news.p.rapidapi.com/news",
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_FOLLOWLOCATION => true,
            CURLOPT_ENCODING => "",
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 30,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => "GET",
            CURLOPT_HTTPHEADER => [
                "x-rapidapi-host: gaming-news.p.rapidapi.com",
                "x-rapidapi-key: c739891e54msh628b9ee19abc52ep153420jsn9b386ba85875"
            ],
        ]);
        
        $response = curl_exec($curl);
        $err = curl_error($curl);
        
        curl_close($curl);
        $json = json_decode($response,true);

        if ($err) {
            echo "cURL Error #:" . $err;
        
        }


        return $this->render("publicity/news.html.twig", [
            'pubBack' => $pubBack->findAll(),
            'items' => $json
        ]);
     
     
         }



         
//     /**
//      * @Route("/chatbot", name="chatbot")
//      */ 
//     public function  chatbot(PubBackRepository $pubBack) {
        
       
// $curl = curl_init();

// curl_setopt_array($curl, [
// 	CURLOPT_URL => "https://ai-chatbot.p.rapidapi.com/chat/free?message=What's%20your%20name%3F&uid=user1",
// 	CURLOPT_RETURNTRANSFER => true,
// 	CURLOPT_FOLLOWLOCATION => true,
// 	CURLOPT_ENCODING => "",
// 	CURLOPT_MAXREDIRS => 10,
// 	CURLOPT_TIMEOUT => 30,
// 	CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
// 	CURLOPT_CUSTOMREQUEST => "GET",
// 	CURLOPT_HTTPHEADER => [
// 		"x-rapidapi-host: ai-chatbot.p.rapidapi.com",
// 		"x-rapidapi-key: c739891e54msh628b9ee19abc52ep153420jsn9b386ba85875"
// 	],
// ]);

// $response = curl_exec($curl);
// $err = curl_error($curl);

// curl_close($curl);
// $json = json_decode($response,true);

// if ($err) {
// 	echo "cURL Error #:" . $err;
// }

//         return $this->render("publicity/chatbot.html.twig", [
//             'pubBack' => $pubBack->findAll(),
//             'chatbots' => $json
//         ]);
     
     
//          }
/********************************************************************************************************************/

  /**************************************************  BACK  **************************************************/

 /********************************************************************************************************************/

        /**
     * @Route("/publicity", name="publicite")
     */ 
    public function  publicite(PubBackRepository $pubBack) {
        return $this->render("pub_back/index.html.twig", [
            'pubBack' => $pubBack->findAll()
        ]);
     
     
         } 

    



 

                    /***************  AJOUTER UNE PUBLICATION ***************/

    /**
     * @Route("/addP", name="addP")
     */
    function Add(Request $request){
        $pubBack=new PubBack();
        $form=$this->createform(PubType::class,$pubBack);
        $form->add('Add New ', SubmitType::class,[
            'attr' => [
                'class'=>'btn btn-info waves-effect waves-light',
                'style'=>'margin-left : 50px '
            ]
        ]) ;
        $form->handleRequest($request) ; 
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager() ; 
            $em->persist($pubBack) ; 
            $em->flush() ; 
            return $this->redirectToRoute('publicite') ; //lien

        } 

        return $this->render("pub_back/Add.html.twig", [
            'form' => $form->createView()
        ]) ;

   }
                    /***************  MODIFIER UNE PUBLICATION ***************/

   /**
     * @Route("/update/{id}", name="update")
     */
    public function update(Request $request,$id )
    {
        $pubBack = $this->getDoctrine()->getRepository(PubBack::class)->find($id) ; 
        $form = $this->createForm(PubType::class,$pubBack) ; 
        $form->add("Modify",SubmitType::class,[
            'attr' => [
                'class'=>'btn btn-info waves-effect waves-light',
                'style'=>'margin-left : 50px '
            ]
        ]) ; 
        $form->handleRequest($request) ; 
        if ($form->isSubmitted()) {
            $em = $this->getDoctrine()->getManager() ; 
            $em->persist($pubBack);
            $em->flush() ; 
            return $this->redirectToRoute('publicite') ; 

        } 

        return $this->render("pub_back/Update.html.twig", [
            'form' => $form->createView()
        ]) ;
    }
                    /***************  SUPPRIMER UNE PUBLICATION ***************/

  
   /**
     * @Route("/delete/{id}", name="delete")
     */ 
    public function delete(Request $request, $id){
        $em =$this->getDoctrine()->getManager();
         $em->remove($pubBack);
         $em->flush();
         return $this->redirectToRoute('publicite');
        }


    
        

    /***************  Ajout JSON UNE PUBLICATION ***************/

    
/**
      
     * @Route("addPublicityJson/new", name="add_publicity" ,methods = {"GET", "POST"})
     */
    

    public function  addPublicity(Request $request,NormalizerInterface $Normalizer) {

        $em=$this->getDoctrine()->getManager();
        $pubBack=new PubBack();
        $pubBack->setNomPub($request->get('nomPub'));
        $pubBack->setDescPub($request->get('descPub'));
        $pubBack->setPrixPub($request->get('prixPub'));
        $pubBack->setImageName($request->get('imageName'));

        $pubBack->setPromoPub($request->get('promoPub'));
 
        $em->persist($pubBack);
        $em->flush();
        $jsonContent=$Normalizer->normalize($pubBack,'json',['groups' => 'post:read']);
        return new Response(json_encode($jsonContent));
 
          } 
 
 
 /**
       
      * @Route("updatePublicityJson/{id}", name="update_publicity_json" ,methods = {"GET", "POST"})
      */
     
 
     public function  updatePublicityJson(Request $request,NormalizerInterface $Normalizer,$id) {
         $em=$this->getDoctrine()->getManager();
         $pubBack=$em->getRepository(PubBack::class)->find($id);
         $pubBack->setNomPub($request->get('nomPub'));
         $pubBack->setDescPub($request->get('descPub'));
         $pubBack->setPrixPub($request->get('prixPub'));
         $pubBack->setImageName($request->get('imageName'));
         $pubBack->setPromoPub($request->get('promoPub'));  
        $em->flush();
         $jsonContent=$Normalizer->normalize($pubBack,'json',['groups' => 'post:read']);
         return new Response("Information updated successfuly".json_encode($jsonContent));
 
 
     }
 
   /**
     * @Route("deletePublicityJson/{id}", name="deletePublicityJson" ,methods = {"GET", "POST"})
     */
    
 
    public function deletePublicityJson(Request $request,NormalizerInterface $Normalizer,$id) {
 
     $em=$this->getDoctrine()->getManager();
     $pubBack=$em->getRepository(PubBack::class)->find($id);
     $em->remove($pubBack);
     $em->flush();
     $jsonContent=$Normalizer->normalize($pubBack,'json',['groups' => 'post:read']);
     return new Response("Custumor Deleted successfuly".json_encode($jsonContent));
 
    }


    /**
     * @Route("PublicityJson", name="liste_publicity")
     */ 
    public function  publicityList(NormalizerInterface $Normalizer) {
        $repo=$this->getDoctrine()->getRepository(PubBack::class);
        $pubBack=$repo->findAll();
        $jsonContent=$Normalizer->normalize($pubBack,'json',['groups'=>'post:read']);
         return new Response (json_encode($jsonContent));
    
     
     
         }

}
