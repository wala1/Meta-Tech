<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Publication;
use App\Form\PublicationFormType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ;  




class BlogController extends AbstractController
{
    /**
     * @Route("/blog", name="blog")
     */
    public function index(): Response
    {   
     $repo = $this->getDoctrine()->getRepository(Publication::class);
        $publications = $repo->findAll();


        return $this->render('blog/index.html.twig', [
           //'controller_name' => 'BlogController',
            'publications' => $publications
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
           
            $em = $this->getDoctrine()->getManager();
           $em->persist($publication) ; 
            $em->flush() ; 
           return $this->redirectToRoute('blog') ; 
        }
        return $this->render('blog/ajouterBlog.html.twig', [
           'form' => $form->createView(),
            'editMode' => $publication->getId() !== null 
        ]);
    }


    
        /**
         * @Route("/back", name="back")
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
     * @Route("/blog/{id}", name="blog_show")
     */
    
        public function publShow($id): Response {
            $repo = $this->getDoctrine()->getRepository(Publication::class) ; 
           
            $publication = $repo->findById($id) ; 

            return $this->render('blog/show.html.twig', [
                'publications' => $publication
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
     * @Route("/delete_publication_back/{id}", name="delete_publ")
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







}
