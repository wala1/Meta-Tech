<?php

namespace App\Entity;

use App\Repository\SousCategorieRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=SousCategorieRepository::class)
 */
class SousCategorie
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Sub Category name must not be empty.")
     */
    private $nomSousCateg;

    /**
     * @ORM\OneToMany(targetEntity=Produit::class, mappedBy="sousCategProd")
     */
    private $produits;

    public function __construct()
    {
        $this->produits = new ArrayCollection();
    }

   

     

    

     

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomSousCateg(): ?string
    {
        return $this->nomSousCateg;
    }

    public function setNomSousCateg(string $nomSousCateg): self
    {
        $this->nomSousCateg = $nomSousCateg;

        return $this;
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
            $produit->setSousCategProd($this);
        }

        return $this;
    }

    public function removeProduit(Produit $produit): self
    {
        if ($this->produits->removeElement($produit)) {
            // set the owning side to null (unless already changed)
            if ($produit->getSousCategProd() === $this) {
                $produit->setSousCategProd(null);
            }
        }

        return $this;
    }

    

    

    

    
}
