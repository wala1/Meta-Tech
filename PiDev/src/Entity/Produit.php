<?php

namespace App\Entity;

use App\Repository\ProduitRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;


/**
 * @ORM\Entity(repositoryClass=ProduitRepository::class)
 */
class Produit
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Product Name is required")
     */
    public $nom_prod;

    /**
     * @ORM\Column(type="string", length=600)
     * @Assert\NotBlank(message="Product Description is required")
     */
    public $desc_prod;

    /**
     * @ORM\ManyToMany(targetEntity=Commande::class, mappedBy="produits")
     */
    public $commands;

    /**
     * @return Collection|Commande[]
     */
    public function getCommands(): Collection
    {
        return $this->commands;
    }
    public function addCommands(Commande $command): self
    {
        if (!$this->commands->contains($command)) {
            $this->commands[] = $command;
        }
        return $this;
    }
    public function removeCommande(Commande $command): self
    {
        $this->commands->removeElement($command);
        return $this;
    }

    /**
     * @ORM\OneToMany(targetEntity=Panier::class, mappedBy="produit_panier")
    */
    private $panier;

    public function __construct()
    {
        $this->panier = new ArrayCollection();
        $this->commands = new ArrayCollection();
    }

    /**
     * @return Collection|Panier[]
    */
    public function getPanier(): Collection
    {
        return $this->panier;
    }

    /**
     * @ORM\ManyToOne(targetEntity=Categorie::class, inversedBy="produits")
     */
    public $categorie_prod;

    /**
     * @ORM\Column(type="string", length=400)
     * @Assert\NotBlank(message="Product Image is required")
     */
    public $image_prod;

    /**
     * @ORM\Column(type="float")
     * @Assert\NotBlank(message="Product Price is  required")
     * @Assert\Type(type="double", message="Product discount must be float.")
     */
    public $prix_prod;

    /**
     * @ORM\Column(type="float")
     * @Assert\NotBlank(message="Please make sure to enter 0 if the product has no discount.")
     * @Assert\Type(type="float",message="Product discount must be float.")
     */
    public $promo_prod;

    /**
     * @ORM\Column(type="integer")
     */
    public $ratingProd;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    public $model_prod;

    /**
     * @ORM\Column(type="integer", length=255, nullable=true)
     */
    public $inStock;

    /**
     * @ORM\Column(type="integer")
     */
    public $quantite_prod;
    /**
     * @ORM\ManyToOne(targetEntity=SousCategorie::class, inversedBy="produits")
     * @ORM\JoinColumn(nullable=false)
     */
    public $sousCategProd;
    

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomProd(): ?string
    {
        return $this->nom_prod;
    }

    public function setNomProd(string $nom_prod): self
    {
        $this->nom_prod = $nom_prod;

        return $this;
    }

    public function getDescProd(): ?string
    {
        return $this->desc_prod;
    }

    public function setDescProd(string $desc_prod): self
    {
        $this->desc_prod = $desc_prod;

        return $this;
    }

    public function getImageProd(): ?string
    {
        return $this->image_prod;
    }

    public function setImageProd(string $image_prod): self
    {
        $this->image_prod = $image_prod;

        return $this;
    }

    public function getPrixProd(): ?float
    {
        return $this->prix_prod;
    }

    public function setPrixProd(float $prix_prod): self
    {
        $this->prix_prod = $prix_prod;

        return $this;
    }

    public function getPromoProd(): ?float
    {
        return $this->promo_prod;
    }

    public function setPromoProd(float $promo_prod): self
    {
        $this->promo_prod = $promo_prod;

        return $this;
    }

    public function getRatingProd(): ?int
    {
        return $this->ratingProd;
    }

    public function setRatingProd(int $ratingProd): self
    {
        $this->ratingProd = $ratingProd;

        return $this;
    }

    public function getModelProd(): ?string
    {
        return $this->model_prod;
    }

    public function setModelProd(?string $model_prod): self
    {
        $this->model_prod = $model_prod;

        return $this;
    }    

    public function getInStock(): ?int
    {
        return $this->inStock;
    }

    public function setInStock(?int $inStock): self
    {
        $this->inStock = $inStock;

        return $this;
    }

    public function getQuantiteProd(): ?int
    {
        return $this->quantite_prod;
    }

    public function setQuantiteProd(?int $quantite_prod): self
    {
        $this->quantite_prod = $quantite_prod;

        return $this;
    }

    public function getSousCategProd(): ?SousCategorie
    {
        return $this->sousCategProd;
    }

    public function setSousCategProd(?SousCategorie $sousCategProd): self
    {
        $this->sousCategProd = $sousCategProd;

        return $this;
    }

    public function getCategProd(): ?Categorie
    {
        return $this->categorie_prod;
    }

    public function setCategProd(?Categorie $categorie_prod): self
    {
        $this->categorie_prod = $categorie_prod;

        return $this;
    }

    
    public function addPanier(Panier $produipaniert): self
    {
        if (!$this->panier->contains($panier)) {
            $this->panier[] = $panier;
            $panier->setProduitPanier($this);
        }

        return $this;
    }

    public function removePanier(Panier $panier): self
    {
        if ($this->panier->removeElement($panier)) {
            if ($panier->getProduitPanier() === $this) {
                $panier->setProduitPanier(null);
            }
        }

        return $this;
    }
}
