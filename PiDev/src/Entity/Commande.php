<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Doctrine\Common\Collections\ArrayCollection;

/**
 * @ORM\Entity(repositoryClass=CommandeRepository::class)
 */
class Commande
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\ManyToMany(targetEntity=Produit::class, inversedBy="commands")
     */
    public $produits;

    public function __construct()
    {
        $this->produits = new ArrayCollection();
    }

    /**
     * @return Collection|Produit[]
     */
    public function getProduits(): Collection
    {
        return $this->produits;
    }
    public function addProduit(Produit $produit): self
    {
        if (!$this->produits->contains($produit)) {
            $this->produits[] = $produit;
        }
        return $this;
    }
    public function removeProduit(Produit $produit): self
    {
        $this->produits->removeElement($produit);
        return $this;
    }

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $firstName;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $lastName;
    
    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $street_Adress;

    /**
     * @ORM\Column(type="string", length=255)
     */
    public $city;

    /**
     * @ORM\Column(type="integer", length=255)
     * @Assert\Length(min="4",minMessage="Your zip/post code must be at least 4 characters")
     * @Assert\NotNull(message="This field is required")
     */
    public $zip_PostalCode;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $country;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $company;

    /**
     * @ORM\Column(type="integer", length=8)
     * @Assert\Length(min="8",minMessage="Your phone number must be at least 8 characters")
     * @Assert\NotNull(message="This field is required")
     */
    public $phone_Number;
    
        /**
     * @ORM\Column(type="string", length=10)
     * @Assert\NotNull(message="This field is required")
     */
    public $payement_method;

    /**
     * @ORM\Column(type="date", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $date;
    
    /**
     * @ORM\Column(type="string", length=500, nullable=true)
     */
    public $delivery_comment;

    /**
     * @ORM\Column(type="string")
     */
    public $newsletter;
    
    /**
     * @ORM\Column(type="string", length=500, nullable=true)
     */
    public $order_comment;

    /**
     * @ORM\Column(type="string", length=500, nullable=true)
     * @ORM\OneToOne(targetEntity=Coupon::class, inversedBy="commande")
     */
    public $codeCoup;

    /**
     * @ORM\Column(type="string", length=20)
    */
    public $etat;

    /**
     * @ORM\Column(type="float", length=20)
    */
    public $subtotal;


    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="commande")
     */
    protected $user_commande;

    public function getUserCommande(): ?User
    {
        return $this->user_commande;
    }

    public function setUserCommande(?User $user_commande): self
    {
        $this->user_commande = $user_commande;

        return $this;
    }


    public function getId(): ?int
    {
        return $this->id;
    }



    public function getPayementMethod(): ?string
    {
        return $this->payement_method;
    }

    public function setPayementMethod(string $payement_method): self
    {
        $this->payement_method = $payement_method;

        return $this;
    }

    public function getCodeCoupon(): ?string
    {
        return $this->codeCoup;
    }

    public function setCodeCoupon(?string $codeCoup): self
    {
        $this->codeCoup = $codeCoup;

        return $this;
    }

    public function getComment(): ?string
    {
        return $this->comment;
    }

    public function setComment(?string $comment): self
    {
        $this->comment = $comment;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getPhoneNumber(): ?int
    {
        return $this->phone_Number;
    }

    public function setPhoneNumber(?int $phone_Number): self
    {
        $this->phone_Number = $phone_Number;

        return $this;
    }

    public function getStreetAdress(): ?string
    {
        return $this->street_Adress;
    }   
    
    public function getEtat(): ?string
    {
        return $this->etat;
    }

    public function getSubtotal(): ?float
    {
        return $this->subtotal;
    }

    public function setStreetAdress(string $street_Adress): self
    {
        $this->street_Adress = $street_Adress;
        return $this;
    }

    public function setCity(string $city): self
    {
        $this->city = $city;
        return $this;
    }

    public function setZip_PostalCode(int $zip_PostalCode): self
    {
        $this->zip_PostalCode = $zip_PostalCode;
        return $this;
    }

    public function setCountry(string $country): self
    {
        $this->country = $country;
        return $this;
    }

    public function setCompany(string $company): self
    {
        $this->company = $company;
        return $this;
    }

    public function setDelivery_comment(string $delivery_comment): self
    {
        $this->delivery_comment = $delivery_comment;
        return $this;
    }

    public function setNewsletter(string $newsletter): self
    {
        $this->newsletter = $newsletter;
        return $this;
    }

    public function setOrder_comment(string $order_comment): self
    {
        $this->order_comment = $order_comment;
        return $this;
    }

    public function setEtat(string $etat): self
    {
        $this->etat = $etat;
        return $this;
    }
    
    public function setSubtotal(float $subtotal): self
    {
        $this->subtotal = $subtotal;
        return $this;
    }

    
}