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
                    'class'=>'form-control',
                ]
            ])
            ->add('payement_method', ChoiceType::class, [
                'choices' => [
                    'Check / Money order' => 'check',
                    'Bank transfer payment' => 'bank',
                    'Cash On Delivery' => 'cash',
                ],
                'attr' => [
                    'class'=>'form-control',
                ],
                // attributes for input elements
                'choice_attr' => [
                    'check' => ['class' => 'whatever'],
                    'bank' => ['class' => 'whatever'],
                    'cash' => ['class' => 'whatever'],
                 ],
                // setting these options results in radio buttons
                // being generated, instead of a select element
                'expanded' => true,
                'multiple' => false,
            ])
            ->add('date', DateType::class, [ 
                'widget' => 'single_text',
                'format' => 'yyyy-MM-dd',
                'data' => new \DateTime(),
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            ->add('delivery_comment', TextAreaType::class,[
                'attr' => [
                    'class'=>'form-control',
                    'style' => 'width: 350px; height: 70px; font-size: 16px; font-family: cursive;',
                ],
                'required' => false,
            ])
            ->add('newsletter', CheckboxType::class, [
                'label_attr' => [
                    'class'=>'switch-custom',
                ],
                'required' => true,
            ])
            
            ->add('order_comment', TextAreaType::class,[
                'attr' => [
                    'class'=>'form-control',
                    'style' => 'width: 350px; height: 70px; font-size: 16px; font-family: cursive;',
                ],
                'required' => false,
            ])
            ->add('codeCoup', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ],
                'required' => false,
            ])
            ->add('etat', TextAreaType::class,[
                'attr' => [
                    'class'=>'form-control',
                    'style' => 'display: none;',
                ],
                'label_attr' => [
                    'style' => 'display: none;'
                ],
            ])
            ->add('subtotal', TextType::class,[
                'attr' => [
                    'class'=>'form-control',
                    'style' => 'display: none;',
                ],
                'label_attr' => [
                    'style' => 'display: none;'
                ],
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
