<?php

namespace App\Entity;

use App\Repository\PubBackRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Vich\UploaderBundle\Mapping\Annotation as Vich;
//use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\File\File;

/**
 * @ORM\Entity(repositoryClass=PubBackRepository::class)
 * @Vich\Uploadable
 */
class PubBack
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups("post:read")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups("post:read")
     */
    private $nomPub;

    /**
     * @ORM\Column(type="string", length=255)
       * @Assert\NotBlank(message="Event Description is required")
       * @Groups("post:read")
      * @Assert\Length(
     *      min = 2,
     *      max = 50,
     *      minMessage = "The Event Description must be at least {{ limit }} characters long",
     *      maxMessage = "The Event Description cannot be longer than {{ limit }} characters"
     * )
     */
    private $descPub;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups("post:read")
     */
    private $prixPub;

   /**
     * @ORM\Column(type="string", length=255)
     * @Groups("post:read")
     */
    private $promoPub;

    /**
     * NOTE: This is not a mapped field of entity metadata, just a simple property.
     * @Vich\UploadableField (mapping="pubBack", fileNameProperty=" imageName")
     * @var File
    */
    private $imagePub;

    /**
     * @ORM\Column(type="string", length=255)
     * @var string
    */
    private $imageName;
 
 
    /**@ORM\Column (type="datetime")
     * @var \DateTime
    */
    private $updatedAt;

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

    /**
     * @ORM\ManyToOne(targetEntity=Sponsor::class, inversedBy="pubBacks")
     */
    private $sponsor;

    // /**
    //  * @ORM\ManyToOne(targetEntity=Calendar::class, inversedBy="pubBacks")
    //  */
    // private $calendar;

    public function __construct()
    {
        $this->coupon = new ArrayCollection();
        $this->updatedAt = new \Datetime();
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

         public function getImagePub(): ?File
         {
             return $this->imagePub;
         }

        /**
          * If manually uploading a file (i.e. not using Symfony Form) ensure an instance
          * of 'UploadedFile' is injected into this setter to trigger the update. If this
          * bundle's configuration parameter 'inject_on_load' is set to 'true' this setter
          * must be able to accept an instance of 'File' as the bundle will inject one here
          * during Doctrine hydration.
          *
          * @param File|\Symfony\Component\HttpFoundation\File\UploadedFile $imageFile
        */
         public function setImagePub(?File $imagePub = null): void
        {
        $this->imagePub = $imagePub;

        if (null !== $imagePub) {
            // It is required that at least one field changes if you are using doctrine
            // otherwise the event listeners won't be called and the file is lost
            $this->updatedAt = new \DateTimeImmutable();
        }
     }

     public function setImageName(?string $imageName): void
     {
         $this->imageName = $imageName;
     }
 
     public function getImageName(): ?string
     {
         return $this->imageName;
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

         public function getSponsor(): ?Sponsor
         {
             return $this->sponsor;
         }

         public function setSponsor(?Sponsor $sponsor): self
         {
             $this->sponsor = $sponsor;

             return $this;
         }

 
         
}
