<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\CategorieRepository ; 
use App\Entity\Categorie ; 
use Symfony\Component\HttpFoundation\Request;
use App\Form\CategorieFormType ;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ; 
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


class CategorieController extends AbstractController
{
    /**
     * @Route("/admin/categorie", name="admin_categorie")
     */
    public function showCategs(): Response
    {
        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categorie = $repo->findAll() ; 

        return $this->render('admin/gestionProd.html.twig', [
            'categories' => $categorie,
            'table' => 'categories'
        ]);
    }


    /**
     * @Route("/admin/ajouterCategorie", name="ajouter_categorie") 
     */
    public function ajouterCateg(Request $request, Categorie $categorie=null): Response
    {
        if (!$categorie) {
            $categorie = new Categorie();
        }
        
        $form = $this->createForm(CategorieFormType::class, $categorie) ; 
        $form->add('Ajouter', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-primary waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
     
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($categorie) ; 
            $em->flush() ; 
            return $this->redirectToRoute('admin_categorie') ; 
        }
        return $this->render('admin/ajouterProd.html.twig', [
            'form' => $form->createView(),
            'editMode' => $categorie->getId() !== null,
            'table' => 'categories' 
        ]);
    }   


    /**
     * @Route("/delete_categ/{id}", name="delete_categ")
     */
    public function delete_categ($id, CategorieRepository $repository): Response
    {   
        $categorie = $repository->find($id) ; 
        $em = $this->getDoctrine()->getManager() ; 
        $em->remove($categorie) ; 
        $em->flush() ; 
        return $this->redirectToRoute('admin_categorie') ;
    }


    /** 
     * @Route("/categorie/{id}" , name="modifCateg")
     */
    public function modifierCateg(Request $request,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $categorie = $em->getRepository(Categorie::class)->find($id);

        
        $form = $this->createForm(CategorieFormType::class, $categorie) ; 
         $form->add('Modifier', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
        

        if ($form->isSubmitted() && $form->isValid()) {
             
            $em->persist($categorie) ;  
            $em->flush() ; 
            return $this->redirectToRoute('admin_categorie') ; 
        }
        return $this->render('admin/ajouterProd.html.twig', [
            'form' => $form->createView()  ,
            'editMode' => $categorie->getId() !== null,
            'table' => 'categories'
        ]);
    }





    // =============================== JSON MOBILE ==================================
    /**
     * @Route("/AllCategories", name="AllCategories")
     */
    public function AllCategories(NormalizerInterface $Normalizer): Response
    {   
        $repo = $this->getDoctrine()->getRepository(Categorie::class) ; 
        $categories = $repo->findAll() ; 

        $jsonContent = $Normalizer->normalize($categories, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }


    /**
     * @Route("/AddCategory", name="AddCategory")
     */
    public function AddCategoryJSON(Request $request, NormalizerInterface $Normalizer): Response
    {   
        $em = $this->getDoctrine()->getManager() ; 
        $categorie = new categorie() ; 

         

        $categorie->setNomCategorie($request->get('nom')) ; 
        
        $em->persist($categorie) ; 
        $em->flush() ; 
 
       // http://127.0.0.1:8000/AddCategory?nom=test
        $jsonContent = $Normalizer->normalize($categorie, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }



     /**
     * @Route("/ModifyCategory/{id}", name="ModifyCategory")
     */
    public function ModifyCategoryJSON(Request $request, NormalizerInterface $Normalizer, $id): Response
    {   
        $em = $this->getDoctrine()->getManager() ;  
        $categorie = $em->getRepository(Categorie::class)->find($id) ; 
 

        if (($request->get('nom')!=null)) {
            $categorie->setNomCategorie($request->get('nom')) ; 
        }
 
        $em->flush() ; 
 
       // http://127.0.0.1:8000/ModifyCategory/11?nom=ZZZZZ
        $jsonContent = $Normalizer->normalize($categorie, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }



     /**
     * @Route("/DeleteCategory/{id}", name="DeleteCategory")
     */
    public function DeleteCategoryJSON(Request $request , $id , NormalizerInterface $Normalizer): Response
    {   
        $em = $this->getDoctrine()->getManager() ;
        $categorie = $em->getRepository(Categorie::class)->find($id) ; 
        $em->remove($categorie) ; 
        $em->flush() ;

        // http://127.0.0.1:8000/DeleteCategory/11 
        $jsonContent = $Normalizer->normalize($categorie, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }


}
