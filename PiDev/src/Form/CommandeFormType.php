<?php

namespace App\Form;

use App\Entity\Commande;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextType ;
use Symfony\Component\Form\Extension\Core\Type\TextareaType ;
use Symfony\Component\Form\Extension\Core\Type\NumberType ;
use Symfony\Component\Form\Extension\Core\Type\DateType ;

class CommandeFormType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('place', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('payement_method', NumberType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('code_Coupon', NumberType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('comment', TextAreaType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('date', DateType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('phone_Number', NumberType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('street', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Commande::class,
        ]);
    }
}
