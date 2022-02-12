<?php

namespace App\Form;

use App\Entity\Produit;
use App\Entity\Categorie;
use App\Entity\SousCategorie;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\TextType ;
use Symfony\Component\Form\Extension\Core\Type\TextareaType ;
use Symfony\Component\Form\Extension\Core\Type\NumberType ;
use Symfony\Component\Form\Extension\Core\Type\FileType;

class ProduitFormType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom_prod', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('desc_prod', TextAreaType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('image_prod', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('prix_prod', NumberType::class,[
                'attr' => [
                    'class'=>'form-control' 
                ]
            ])
            ->add('promo_prod', NumberType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])  
            ->add('sousCategProd', EntityType::class,[
                'class'=>SousCategorie::class, 
                'choice_label'=>'nomSousCateg',
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('model_prod', TextAreaType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('categorie_prod', EntityType::class, [
                'class'=>Categorie::class, 
                'choice_label'=>'nomCategorie',
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Produit::class,
        ]);
    }
}
