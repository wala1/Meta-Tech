<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\SousCategorie ; 
use Symfony\Component\HttpFoundation\Request;
use App\Form\SousCategorieFormType ;
use Symfony\Component\Form\Extension\Core\Type\SubmitType ; 
use App\Repository\SousCategorieRepository ;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

class SousCategorieController extends AbstractController
{
    /**
     * @Route("/admin/souscategorie", name="admin_souscategorie")
     */
    public function showSousCategs(): Response
    {
        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $souscategorie = $repo->findAll() ; 

        return $this->render('admin/gestionProd.html.twig', [
            'souscategories' => $souscategorie,
            'table' => 'souscategories'
        ]);
    }


    /**
     * @Route("/admin/ajouterSousCategorie", name="ajouter_souscategorie") 
     */
    public function ajouterSousCateg(Request $request, SousCategorie $souscategorie=null): Response
    {
        if (!$souscategorie) {
            $souscategorie = new SousCategorie();
        }
        
        $form = $this->createForm(SousCategorieFormType::class, $souscategorie) ; 
        $form->add('Ajouter', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
     
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($souscategorie) ; 
            $em->flush() ; 
            return $this->redirectToRoute('admin_souscategorie') ; 
        }
        return $this->render('admin/ajouterProd.html.twig', [
            'form' => $form->createView(),
            'editMode' => $souscategorie->getId() !== null,
            'table' => 'souscategories' 
        ]);
    } 
    
    
    /**
     * @Route("/delete_souscateg/{id}", name="delete_souscateg")
     */
    public function delete_souscateg($id, SousCategorieRepository $repository): Response
    {   
        $souscategorie = $repository->find($id) ; 
        $em = $this->getDoctrine()->getManager() ; 
        $em->remove($souscategorie) ; 
        $em->flush() ; 
        return $this->redirectToRoute('admin_souscategorie') ;
    }


    /** 
     * @Route("/souscategorie/{id}" , name="modifSousCateg")
     */
    public function modifierSousCateg(Request $request,$id): Response
    {
        $em=$this->getDoctrine()->getManager();
        $souscategorie = $em->getRepository(SousCategorie::class)->find($id);

        
        $form = $this->createForm(SousCategorieFormType::class, $souscategorie) ; 
        $form->add('Modifier', SubmitType::class,[
                'attr' => [
                    'class'=>'btn btn-success waves-effect waves-light'
                ]
            ]) ;
        $form->handleRequest($request);
        

        if ($form->isSubmitted() && $form->isValid()) {
             
            $em->persist($souscategorie) ;  
            $em->flush() ; 
            return $this->redirectToRoute('admin_souscategorie') ; 
        }
        return $this->render('admin/ajouterProd.html.twig', [
            'form' => $form->createView()  ,
            'editMode' => $souscategorie->getId() !== null,
            'table' => 'souscategories'
        ]);
    }








    // =============================== JSON MOBILE ==================================
    /**
     * @Route("/AllSubCategories", name="AllSubCategories")
     */
    public function AllSubCategories(NormalizerInterface $Normalizer): Response
    {   
        $repo = $this->getDoctrine()->getRepository(SousCategorie::class) ; 
        $subcategories = $repo->findAll() ; 

        $jsonContent = $Normalizer->normalize($subcategories, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }


    /**
     * @Route("/AddSubCategory", name="AddSubCategory")
     */
    public function AddSubCategoryJSON(Request $request, NormalizerInterface $Normalizer): Response
    {   
        $em = $this->getDoctrine()->getManager() ; 
        $subcategorie = new SousCategorie() ; 

         

        $subcategorie->setNomSousCateg($request->get('nom')) ; 
        
        $em->persist($subcategorie) ; 
        $em->flush() ; 
 
       // http://127.0.0.1:8000/AddSubCategory?nom=test
        $jsonContent = $Normalizer->normalize($subcategorie, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }


    /**
     * @Route("/ModifySubCategory/{id}", name="ModifySubCategory")
     */
    public function ModifySubCategoryJSON(Request $request, NormalizerInterface $Normalizer, $id): Response
    {   
        $em = $this->getDoctrine()->getManager() ;  
        $souscategorie = $em->getRepository(SousCategorie::class)->find($id) ; 
 

        if (($request->get('nom')!=null)) {
            $souscategorie->setNomSousCateg($request->get('nom')) ; 
        }
 
        $em->flush() ; 
 
       // http://127.0.0.1:8000/ModifySubCategory/11?nom=ZZZZZ
        $jsonContent = $Normalizer->normalize($souscategorie, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }



     /**
     * @Route("/DeleteSubCategory/{id}", name="DeleteSubCategory")
     */
    public function DeleteSubCategoryJSON(Request $request , $id , NormalizerInterface $Normalizer): Response
    {   
        $em = $this->getDoctrine()->getManager() ;
        $souscategorie = $em->getRepository(SousCategorie::class)->find($id) ; 
        $em->remove($souscategorie) ; 
        $em->flush() ;

        // http://127.0.0.1:8000/DeleteSubCategory/11 
        $jsonContent = $Normalizer->normalize($souscategorie, 'json', ['groups' => 'post:read']) ; 

        return new Response(json_encode($jsonContent)) ; 
 
    }


}
