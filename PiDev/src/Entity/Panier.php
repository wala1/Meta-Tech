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
