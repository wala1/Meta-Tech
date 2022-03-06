<?php

namespace App\Form;

use App\Entity\Avis;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextareaType ;
use Symfony\Component\Form\Extension\Core\Type\NumberType ;
use Symfony\Component\Form\Extension\Core\Type\HiddenType ;

class AvisFormType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('ratingAvis', NumberType::class,[
                'label' => 'Rating' 
            ])
            ->add('descAvis', TextareaType::class,[
                'label' => 'Review' 
            ]) 
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Avis::class,
        ]);
    }
}
