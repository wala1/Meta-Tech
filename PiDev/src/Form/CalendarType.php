<?php

namespace App\Form;

use App\Entity\Calendar;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\ColorType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType ;





class CalendarType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('title', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('start' ,DateTimeType::class, [
                'date_widget' => 'single_text'
            ])
            ->add('end' ,DateTimeType::class, [
                'date_widget' => 'single_text'
            ])
            ->add('description', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('allDay')
            ->add('backgroundColor', ColorType::class)
            ->add('borderColor', ColorType::class)
            ->add('textColor', ColorType::class)
            ->add('imageEvent', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])


        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Calendar::class,
        ]);
    }
}
