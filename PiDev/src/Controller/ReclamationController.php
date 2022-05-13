<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Form\ReclamationFormType ;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Reclamation ; 
use Symfony\Component\Form\Extension\Core\Type\SubmitType ;
use App\Repository\ReclamationRepository ; 
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

class ReclamationController extends AbstractController
{
    /**
     * @Route("/reclamation", name="reclamation")
     */
    public function reclamation_send(Request $request, Reclamation $reclamation=null): Response
    {
        if (!$reclamation) {
            $reclamation = new Reclamation();
        }
        
        $form = $this->createForm(ReclamationFormType::class, $reclamation) ; 
         
        $form->handleRequest($request);
     
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($reclamation) ; 
            $em->flush() ; 
            return $this->redirectToRoute('reclamation',array('sent'=>1)) ; 
        }

 

        return $this->render('reclamation/contact-us.html.twig', [
            'form' => $form->createView() 
        ]);
    }


    // ====================== ADMIN =========================
    /**
     * @Route("/admin/reclamation", name="admin_reclamation")
     */
    public function showReclamation(): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Reclamation::class) ; 
        $reclamations = $repo->findAll() ; 

         

        return $this->render('reclamation/admin_reclamation.html.twig', [
            'reclamations' => $reclamations ,
            'details' =>  null
        ]);
    }



    /**
     * @Route("/reclamation_id/{id}", name="reclamation_id")
     */
    public function showRecsId($id,  ReclamationRepository $repository): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Reclamation::class) ; 
        $reclamations = $repo->findById($id) ;
         

        return $this->render('reclamation/admin_reclamation.html.twig', [
            'reclamations' => $reclamations ,
            'details' => "reclamation",
            'mail_sent'=>0
        ]);
    }


    /** 
     * @Route("/reclamation_traite/{id}" , name="reclamation_traite")
     */
    public function modifierTraiteReclamation(Request $request,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $reclamation = $em->getRepository(Reclamation::class)->find($id);
        $reclamation->setTraiteReclamation(1) ;  

        $em->persist($reclamation) ;  
        $em->flush() ; 
        return $this->redirectToRoute('reclamation_id', array('id'=>$id)) ; 
        
    }

    /**
     * @Route("/delete_reclamation/{id}", name="delete_reclamation")
     */
    public function delete_reclamation_back($id, ReclamationRepository $repository): Response
    {   
        $reclamation = $repository->find($id) ; 
        $em = $this->getDoctrine()->getManager() ; 
        $em->remove($reclamation) ; 
        $em->flush() ; 
        return $this->redirectToRoute('admin_reclamation') ;
    }


    /**
     * @Route("/mail_reclamation/{id}", name="mail_reclamation")
     */
    public function mail(\Swift_Mailer $mailer , $id): Response
    {   
         
        $address = $_POST['address'] ; 
        $reply = $_POST['reply']  ;  
        $name = $_POST['name']  ;  
        $message= (new \Swift_Message('Reply To Report'))
          
            ->setFrom('contact@metatech.com')
            ->setTo($address)
           
            ->setBody("<html>
                        <head>
                            <title></title>
                        </head>
                        <body>

                            <center>
                                
                                <h1 style='color : green ; margin-top : 15px ; font-size : 25px ;  '>Thank you for your report</h1>
                                <br> 
                            </center>	
                                <div>
                                    <p style='color : black ; font-size : 16px ; '>
                                        Hello <strong>".$name."</strong>,<br><br>
                                    </p>
                                    <p style='color : black !important ; '>
                                        ".$reply."
                                        <br>
                                        <br>
                                        <div style='color : black ;float:right'> Abdelaziz Makhlouf, Development Team</div><br>
                                        <div style='color : black ;float:right'> Cordially,</div><br>
                                        <div style='color : black ;float:right'> MetaTech</div>
                                    </p>
                                </div>
                                
                                <img height='300px' scr='logoFinal.png' />

                        </body>
                        </html>", 'text/html') 
                ;
           // envoi email
           $mailer->send($message);


           $repo = $this->getDoctrine()->getRepository(Reclamation::class) ; 
           $reclamations = $repo->findById($id) ;
         

        return $this->render('reclamation/admin_reclamation.html.twig', [
            'reclamations' => $reclamations ,
            'details' => "reclamation",
            'mail_sent'=>1
        ]); 
    }




    // ==================== CRYPTO =================================
    /**
     * @Route("/crypto", name="crypto")
     */
    public function crypto(): Response
    {   
        // ================== API CURRENCIES CRYPTO ========================
        $curl = curl_init(); 
        curl_setopt_array($curl, [
            CURLOPT_URL => "https://crypto-price-feed.p.rapidapi.com/api/crypto-feed",
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_FOLLOWLOCATION => true,
            CURLOPT_ENCODING => "",
            CURLOPT_MAXREDIRS => 10,
            CURLOPT_TIMEOUT => 30,
            CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
            CURLOPT_CUSTOMREQUEST => "GET",
            CURLOPT_HTTPHEADER => [
                "x-rapidapi-host: crypto-price-feed.p.rapidapi.com",
                "x-rapidapi-key: ffccef661fmsh65d228e6a669cf1p1aba63jsne6686937e077"
            ],
        ]);

        $response = curl_exec($curl);
        $err = curl_error($curl);

        curl_close($curl);

        if ($err) {
            echo "cURL Error #:" . $err;
        }  
        $json = json_decode($response, true);
        

        
        return $this->render('crypto/crypto.html.twig', [
            'tables' => $json["result"] 
        ]);
    }


    
    // ========================== JSON MOBILE ================================
    
    /**
     * @Route("/AddReport", name="AddReport")
     */
    public function AddReportJSON(Request $request, NormalizerInterface $Normalizer): Response
    {   
        $em = $this->getDoctrine()->getManager() ; 
        $reclamation = new Reclamation() ; 
 

        $reclamation->setNameReclamation($request->get('nameReclamation')) ; 
        $reclamation->setEmailReclamation($request->get('emailReclamation')) ;
        $reclamation->setDescReclamation($request->get('descReclamation')) ;
        $reclamation->setTraiteReclamation(0) ; 

        $em->persist($reclamation) ; 
        $em->flush() ; 
 
       // http://127.0.0.1:8000/AddReport?nameReclamation=Test&emailReclamation=aaa@ff.com&descReclamation=good!
        $jsonContent = $Normalizer->normalize($reclamation, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }

}
