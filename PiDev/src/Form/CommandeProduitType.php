<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CommandeProduitType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('commande', EntityType::class, [
                'required' => false,
                'label' => 'commande',
                'expanded' => false,
                'multiple' => true,
                'class' => DesiFibre::class,
                'choice_label' => 'nom',
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('d')
                        ->where('d.statut = 1');
                },
                'attr' => [
                    'class' => 'form-control-sm select2'
                ]
            ])
            ->add('produit', EntityType::class, [
                'required' => false,
                'label' => 'produit',
                'expanded' => false,
                'multiple' => true,
                'class' => DesiFibre::class,
                'choice_label' => 'nom',
                'query_builder' => function (EntityRepository $er) {
                    return $er->createQueryBuilder('d')
                        ->where('d.statut = 1');
                },
                'attr' => [
                    'class' => 'form-control-sm select2'
                ]
            ]) 
            ->add('quantite', NumberType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            // Configure your form options here
        ]);
    }
}
