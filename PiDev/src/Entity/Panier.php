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
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="panier")
     */
    private $user_panier;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $prix; 

    /**
     * @ORM\Column(type="integer")
     */
    private $num_panier;

    /**
     * @ORM\Column(type="integer")
     */
    private $quantite;

    /**
     * @ORM\Column(type="date")
     */
    private $date_Ajout;

    /**
     * @ORM\Column(type="float")
     */
    private $prix_totale;

    /****les getters et setters****/

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getPrix(): ?float
    {
        return $this->prix;
    }

    public function setPrix(float $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getNum_panier(): ?int
    {
        return $this->prix;
    }

    public function setNum_panier(int $num_panier): self
    {
        $this->num_panier = $num_panier;

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
    
    public function getDate_Ajout(): ?Date
    {
        return $this->date_Ajout;
    }

    public function setDate_Ajout(Date $date_Ajout): self
    {
        $this->date_Ajout = $date_Ajout;

        return $this;
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
