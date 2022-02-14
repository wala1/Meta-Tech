<?php

namespace App\Form;

use App\Entity\Publication;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextType ;
use Symfony\Component\Form\Extension\Core\Type\TextareaType ; 
use Symfony\Component\Form\Extension\Core\Type\DateType ; 


class PublicationFormType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titre_publ', TextType::class, [
                'attr' => [
                    'class' => 'form-control'
                ]
            ] )
            ->add('description_publ', TextAreaType::class, [
                'attr' => [
                    'class' => 'form-control'
                ]
            ])
            ->add('image_publ',TextType::class, [
                'attr' => [
                    'class' => 'form-control'
                ]
            ])  
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Publication::class,
        ]);
    }
}
