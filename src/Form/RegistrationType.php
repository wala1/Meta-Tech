<?php

namespace App\Form;

use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
// use Symfony\Component\Form\Extension\Core\Type\Choice;

use Symfony\Component\Form\Extension\Core\Type\TextType ;
use Captcha\Bundle\CaptchaBundle\Form\Type\CaptchaType;
use Captcha\Bundle\CaptchaBundle\Validator\Constraints\ValidCaptcha;
use Symfony\Component\Form\Extension\Core\Type\NumberType ;


class RegistrationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('email', EmailType::class)
            ->add('username', TextType::class,[
                'attr' => [
                    'class'=>'form-control'
                ]
            ])
            // ->add('gender', 'choice', array(
            //     'choices'  => array('m' => 'Male', 'f' => 'Female'),
            //     'required' => false,
            // ))  
            ->add('password',PasswordType::class)
            ->add('confirm_password', PasswordType::class)
            
             ->add('phoneNumber', NumberType::class,[
                'attr' => [
                    'class'=>'form-control' 
                ]
            ])
            ->add("captchaCode",CaptchaType::class,[
                'captchaConfig' => 'ExampleCaptchaUserResgistration',
                'constraints' => [
                    new ValidCaptcha([
                        'message' => 'Invalid captcha,please try again'
                    ])
                ]
            ])

            ////dddd
        ;
                    }


    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => User::class,
        ]);
    }
}
