<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Publication;
use App\Entity\Commentaire;
use App\Form\PublicationFormType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ;  
use Symfony\Component\Security\Core\Security;




class BlogController extends AbstractController
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
     * @Route("/blog", name="blog")
     */
    public function index(): Response
    {   
        $user = $this->security->getUser();
     $repo = $this->getDoctrine()->getRepository(Publication::class);
        if($user)
      { $publications = $repo->findOtherPublications($user->getId());
        $mesPublications = $repo->findBy(["utilisateur" => $user]);
      }
      else 
      {
        $publications = $repo->findAll();
        $mesPublications = null ;
      }
        
        return $this->render('blog/index.html.twig', [
           //'controller_name' => 'BlogController',
            'publications' => $publications ,
            'mesPublications' => $mesPublications
        ]);
    }

/**
     * @Route("/blog/ajout", name="ajouter_blog") 
     */
    public function ajouterBlog(Request $request,Publication $publication=null): Response
    {
        if (!$publication) {
            $publication = new Publication();
        }
        
        $form = $this->createForm(PublicationFormType::class, $publication) ; 
        $form->add('Ajouter une publication', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $publication->setTempsPubl(new \DateTime); // date system date
            $user = $this->security->getUser();
            $publication->setUtilisateur($user); // connected user
            $em = $this->getDoctrine()->getManager();
           $em->persist($publication) ; 
            $em->flush() ; 
           // $this->addFlash(
           //     'info',
           //  ' votre publication a été envoyer',  
          //);
           return $this->redirectToRoute('blog') ; 
        }
        return $this->render('blog/ajouterBlog.html.twig', [
           'form' => $form->createView(),
            'editMode' => $publication->getId() !== null 
        ]);
    }


    
        /**
         * @Route("/back_blog", name="back_blog")
         */
        public function back(): Response
        {
            $repo = $this->getDoctrine()->getRepository(Publication::class) ; 
        $publications = $repo->findAll() ; 

        return $this->render('blog/listeBlog.html.twig', [
            'publications' => $publications
        ]);
            
        }


        /**
         * @Route("/back_consulter_publ/{id}", name="consulter_publ")
         */
        public function consulter_Publ_back(int $id): Response
        {
            $publication = $this->getDoctrine()->getRepository(Publication::class)->find($id); 
        //$publications = $repo->findAll() ; 

        return $this->render('blog/consulterBlogback.html.twig', [
            'publication' => $publication,
        ]);
            
        }



/**
     * @Route("/blog/{id}", name="blog_show")
     */
    
        public function publShow($id): Response {

            $user = $this->security->getUser();

            $repo = $this->getDoctrine()->getRepository(Publication::class); 
            $publication = $repo->findById($id) ; 
            $isLiked = false ;
        // $repo->findlLikeIfExist($user , $id);
               

           

            return $this->render('blog/show.html.twig', [
                'publications' => $publication , 'isLiked' => $isLiked
            ]);
        }
       
     

    
    

    /**
     * @param $id
     *  @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route("/delete_publication/{id}", name="delete_publ")
     */
    public function delete_publ($id, Publication $repository): Response
    {   
       // $publication = $repository->find($id) ; 
        $publication= $this->getDoctrine()->getRepository(Publication::class)->find($id); 
        $em = $this->getDoctrine()->getManager() ; 
        $em->remove($publication) ; 
        $em->flush() ; 
        return $this->redirectToRoute('blog') ;
        
    }

/**
     * @param $id
     *  @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route("/delete_publication_back/{id}", name="delete_publ_back")
     */
    public function delete_publ_back($id, Publication $repository): Response
    {   
       // $publication = $repository->find($id) ; 
        $publication= $this->getDoctrine()->getRepository(Publication::class)->find($id); 
        $em = $this->getDoctrine()->getManager() ; 
        $em->remove($publication) ; 
        $em->flush() ; 
        return $this->redirectToRoute('back') ;
    }






    
/** 
     * @Route("/blog_modifier/{id}" , name="modifier_blog")
     */
    public function modifierCateg(Request $request,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $publication = $em->getRepository(publication::class)->find($id);

        
        $form = $this->createForm(PublicationFormType::class, $publication) ; 
         $form->add('Modifier', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
        

        if ($form->isSubmitted() && $form->isValid()) {
             
            $em->persist($publication) ;  
            $em->flush() ; 
            return $this->redirectToRoute('blog') ; 
        }
        return $this->render('blog/ajouterBlog.html.twig', [
            'form' => $form->createView()  ,
            
            
        ]);
    }



/**
     * @Route("/blog/commenter/{id}", name="commenter_blog") 
     */
    public function commenterBlog(Request $request,$id): Response
    {
        
     
        if($id != null) 
        {


            $desc = $request->get('description');
            $idCommentaire = $request->get('idCommentaire');

            
            $curl = curl_init();

curl_setopt_array($curl, array(
  CURLOPT_URL => "https://api.apilayer.com/bad_words",
  CURLOPT_HTTPHEADER => array(
    "Content-Type: text/plain",
    "apikey: zd7MoNVNwgUZjU3FwoZlk88vMBapI2ZW"
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

$response = json_decode(curl_exec($curl) , true );

curl_close($curl);
    if( isset($response['censored_content']))
            $desc = $response['censored_content'] ;
            else $desc = "api key expired" ;

            
            if($idCommentaire != null )//modifier
            {
                $em = $this->getDoctrine()->getManager();
                $commentaire = $em->getRepository(Commentaire::class)->find($idCommentaire);
                if( $commentaire != null )
                {
                $commentaire->setTempsComm( new \DateTime);
                $commentaire->setDescriptionComm($desc);    
                $em->persist($commentaire) ; 
                $em->flush() ; //pour appliquer la modification
                return $this->redirectToRoute('blog_show' , [ 'id' => $id ]) ; 
                }
            } else //ajout
            {
                
            $em = $this->getDoctrine()->getManager();
            $publication = $em->getRepository(publication::class)->find($id);

            $user = $this->security->getUser();
            $commentaire = new Commentaire();
            $commentaire->setUtilisateur($user);
            $commentaire->setIdPubl($publication);
            $commentaire->setDescriptionComm($desc);
            $commentaire->setTempsComm( new \DateTime);
            $em = $this->getDoctrine()->getManager();
            $em->persist($commentaire) ; 
            $em->flush() ; 
            return $this->redirectToRoute('blog_show' , [ 'id' => $id ]) ; 

            }
        }
        return $this->redirectToRoute('blog_show' , [ 'id' => $id ]) ; 

    }

 // supprrimer_commentaire_blog
/**
     * @Route("/blog/commenter/{idPublication}/{id}", name="supprrimer_commentaire_blog") 
     */
    public function supprimerCommentaireBlog(Request $request,$idPublication , $id): Response
    {
        
        if($id != null)
        {
            $em = $this->getDoctrine()->getManager();
            $commentaire = $em->getRepository(Commentaire::class)->find($id);
            $em = $this->getDoctrine()->getManager();
            $em->remove($commentaire) ; 
            $em->flush() ; 
            return $this->redirectToRoute('blog_show' , [ 'id' => $idPublication ]) ; 

        }
        return $this->redirectToRoute('blog_show' , [ 'id' => $idPublication ]) ; 

    }

    
/**
     * @Route("/blog/aimer/{id}", name="aimer_blog") 
     */
    public function aimerBlog(Request $request,$id): Response
    {
        
     
        if($id != null) 
        {
            if($id != null )//modifier
            {
                
                
            $em = $this->getDoctrine()->getManager();
            $publication = $em->getRepository(publication::class)->find($id);

            $user = $this->security->getUser();
            $publication->addLike($user);
            $em = $this->getDoctrine()->getManager();
            $em->persist($publication) ; 
            $em->flush() ; 
            return $this->redirectToRoute('blog_show' , [ 'id' => $id ]) ; 

            }
        }
        return $this->redirectToRoute('blog_show' , [ 'id' => $id ]) ; 

    }

}
