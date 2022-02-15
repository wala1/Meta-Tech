<?php

namespace App\Form;

use App\Entity\Produit;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextType ;

class PanierProdFormType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('prix_prod', TextType::class,[
            'attr' => [
                'class'=>'form-control'
            ]
        ])
        ->add('quantite', TextType::class,[
            'attr' => [
                'class'=>'form-control'
            ]
        ])
        ->add('promo_prod', TextType::class,[
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
