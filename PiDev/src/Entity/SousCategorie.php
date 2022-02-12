<?php

namespace App\Entity;

use App\Repository\SousCategorieRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

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
     */
    private $nomSousCateg;

    /**
     * @ORM\ManyToOne(targetEntity=Categorie::class, inversedBy="sousCategories")
     * @ORM\JoinColumn(nullable=false)
     */
    private $categorieProd;

     

    

     

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

    public function getCategorieProd(): ?Categorie
    {
        return $this->categorieProd;
    }

    public function setCategorieProd(?Categorie $categorieProd): self
    {
        $this->categorieProd = $categorieProd;

        return $this;
    }

    

    

    
}
