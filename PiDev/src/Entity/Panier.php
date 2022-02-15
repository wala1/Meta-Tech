<?php

namespace App\Entity;

use App\Repository\PanierRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=PanierRepository::class)
 */
class Panier
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */

    private $id;

    /**
     * @ORM\ManyToOne(targetEntity=Produit::class, inversedBy="panier")
     */
    private $produit_panier;

     /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="panier")
     */
    protected $user_panier;

    public function getProduitPanier(): ?Produit
    {
        return $this->produit_panier;
    }

    public function setProduitPanier(?Produit $produit_panier): self
    {
        $this->produit_panier = $produit_panier;
        return $this;
    }

    public function getUserPanier(): ?User
    {
        return $this->user_panier;
    }

    public function setUserPanier(?User $user_panier): self
    {
        $this->user_panier = $user_panier;

        return $this;
    }

    /*
    * Set panier_id
     *
     * @param string $panierId
     */
    public function setPanierId($panierId)
    {
        $this->panier_id = $panierId;
    }
    
    /**
     * Get panier_id
     *
     * @return string 
     */
    public function getPanierId()
    {
        return $this->panier_id;
    }
    
    /**
     * @ORM\Column(type="float")
     */
    private $prix_totale;

    /**
     * @ORM\Column(type="integer")
     */
    private $quantite;

    /****les getters et setters****/

    public function getId(): ?int
    {
        return $this->id;
    }
    
    public function getPrix_totale(): ?float
    {
        return $this->prix_totale;
    }

    public function setPrix_totale(float $prix_totale): self
    {
        $this->prix_totale = $prix_totale;

        return $this;
    }
    
    public function getQuantite(): ?int
    {
        return $this->quantite;
    }

    public function setQuantite(int $quantite): self
    {
        $this->quantite = $quantite;

        return $this;
    }

}
