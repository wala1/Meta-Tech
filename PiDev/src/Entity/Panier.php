<?php

namespace App\Entity;

use App\Repository\PanierRepository;
use Symfony\Component\Serializer\Annotation\Groups;
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
     * @Groups("post:read")
     */

    private $id;

     /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="panier")
     * @ORM\JoinColumn(name="user_panier_id", nullable=false)
     * @Groups("post:read")
     */
    protected $user_panier;
    
    /**
     * @ORM\ManyToOne(targetEntity=Produit::class, inversedBy="panier")
     * @ORM\JoinColumn(name="produit_panier_id", nullable=false)
     * @Groups("post:read")
    */
    private $produit_panier;

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
     * @ORM\Column(type="integer")
     * @Groups("post:read")
     */
    private $quantite;

    /****les getters et setters****/

    public function getId(): ?int
    {
        return $this->id;
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
