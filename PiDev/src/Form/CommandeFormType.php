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
use Symfony\Component\Form\Extension\Core\Type\RadioType ;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType ;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType ;

class CommandeFormType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder            
            ->add('firstName', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('lastName', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('street_Adress', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('city', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('zip_PostalCode', null, [
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('country', ChoiceType::class, [
                'choices' => [
                    '' => 'null',
                    'Tunisia' => 'TN',
                    'Algeria' => 'DZ',
                    'Egypt' => 'EG',
                    'Marroco' => 'MC',
                ],
                'preferred_choices' => ['muppets', 'arr'],
            ])
            ->add('company', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('phone_Number', NumberType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('payement_method', ChoiceType::class, [
                'choices' => [
                    'Check / Money order' => 'info',
                    'Bank Transfer Payment' => 'star',
                    'Cash On Delivery' => 'other',
                ],
                // attributes for label elements
                'attr' => [
                    'class'=>'form-control'
                ],
                // attributes for input elements
                'choice_attr' => [
                    'Info' => ['class' => 'whatever'],
                    'Star' => ['class' => 'whatever'],
                    'Some other label' => ['class' => 'whatever'],
                 ],
                // setting these options results in radio buttons
                // being generated, instead of a select element
                'expanded' => true,
                'multiple' => false,
            ])

            ->add('date', DateType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('delivery_comment', TextAreaType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('newsletter', CheckboxType::class, [
                'label_attr' => [
                    'class'=>'switch-custom',
                ],
                'required' => true,
            ])
            
            ->add('order_comment', TextAreaType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('code_Coupon', TextType::class,[
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
