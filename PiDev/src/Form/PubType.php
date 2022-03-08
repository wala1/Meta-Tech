<?php

namespace App\Form;

use App\Entity\PubBack;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Vich\UploaderBundle\Form\Type\VichImageType;

class PubType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nomPub')
            ->add('descPub')
            ->add('prixPub')
            ->add('promoPub')
            ->add('imagePub',VichImageType::class)
            //->add('imagePub',FileType::class,['label'=>'Ajouter une image : ', 'data_class' => null],
           
            

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => PubBack::class,
        ]);
    }
}
