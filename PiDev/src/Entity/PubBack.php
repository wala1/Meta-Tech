<?php

namespace App\Entity;

use App\Repository\PubBackRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
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

   /**
     * @ORM\Column(type="string", length=255)
     */
    private $promoPub;

  
 

        /**
     * @ORM\Column(type="string", length=255)
     */
    private $imagePub;

    /**
     * @ORM\OneToMany(targetEntity=Coupon::class, mappedBy="pubBack")
     */
    private $coupon;

    /**
     * @ORM\ManyToOne(targetEntity=Coupon::class, inversedBy="pubBack1")
     */
    private $coupon1;

    /**
     * @ORM\ManyToOne(targetEntity=Produit::class, inversedBy="pubBacks")
     */
    private $produit;

    /**
     * @ORM\ManyToOne(targetEntity=Calendar::class, inversedBy="pubBacks")
     */
    private $calender;

    // /**
    //  * @ORM\ManyToOne(targetEntity=Calendar::class, inversedBy="pubBacks")
    //  */
    // private $calendar;

    public function __construct()
    {
        $this->coupon = new ArrayCollection();
    }

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

         /**
          * @return Collection|Coupon[]
          */
         public function getCoupon(): Collection
         {
             return $this->coupon;
         }

         public function addCoupon(Coupon $coupon): self
         {
             if (!$this->coupon->contains($coupon)) {
                 $this->coupon[] = $coupon;
                 $coupon->setPubBack($this);
             }

             return $this;
         }

         public function removeCoupon(Coupon $coupon): self
         {
             if ($this->coupon->removeElement($coupon)) {
                 // set the owning side to null (unless already changed)
                 if ($coupon->getPubBack() === $this) {
                     $coupon->setPubBack(null);
                 }
             }

             return $this;
         }

         public function getCoupon1(): ?Coupon
         {
             return $this->coupon1;
         }

         public function setCoupon1(?Coupon $coupon1): self
         {
             $this->coupon1 = $coupon1;

             return $this;
         }

         public function getProduit(): ?Produit
         {
             return $this->produit;
         }

         public function setProduit(?Produit $produit): self
         {
             $this->produit = $produit;

             return $this;
         }

         public function getCalender(): ?Calendar
         {
             return $this->calender;
         }

         public function setCalender(?Calendar $calender): self
         {
             $this->calender = $calender;

             return $this;
         }

 
         
}
