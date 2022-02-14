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
     * @ORM\OneToMany(targetEntity=Produit::class, mappedBy="categorie_prod")
     */
    private $produits;

    public function __construct()
    {
        $this->produits = new ArrayCollection();
    }

    /**
     * @return Collection|Produit[]
     */
    public function getProduits()
    {
        return $this->produits;
    }

    /**
     * @ORM\OneToOne(targetEntity="User", inversedBy="panier")
     * @ORM\JoinColumn(name="user_id", referencedColumnName="id")
     */
    protected $user;

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
}
