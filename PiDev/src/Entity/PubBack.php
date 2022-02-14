<?php

namespace App\Entity;

use App\Repository\PubBackRepository;
use Doctrine\ORM\Mapping as ORM;
//use Symfony\Component\HttpFoundation\File\UploadedFile;

/**
 * @ORM\Entity(repositoryClass=PubBackRepository::class)
 */
class PubBack
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $nomPub;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $descPub;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $prixPub;


    private $promoPub;

  
    // /**
    // * @ORM\OneToOne(targetEntity="Image", cascade={"persist", "remove"})   
    // **/

        /**
     * @ORM\Column(type="string", length=255)
     */
    private $imagePub;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomPub(): ?string
    {
        return $this->nomPub;
    }

    public function setNomPub(string $nomPub): self
    {
        $this->nomPub = $nomPub;

        return $this;
    }

    public function getDescPub(): ?string
    {
        return $this->descPub;
    }

    public function setDescPub(string $descPub): self
    {
        $this->descPub = $descPub;

        return $this;
    }

    public function getPrixPub(): ?string
    {
        return $this->prixPub;
    }

    public function setPrixPub(string $prixPub): self
    {
        $this->prixPub = $prixPub;

        return $this;
    }

    public function getPromoPub(): ?string
    {
        return $this->promoPub;
    }

    public function setPromoPub(string $promoPub): self
    {
        $this->promoPub = $promoPub;

        return $this;
    }

         public function getImagePub(): ?string
         {
             return $this->imagePub;
         }

         public function setImagePub(string $imagePub): self
         {
             $this->imagePub = $imagePub;
             return $this;

         }
}
