<?php

namespace App\Entity;

use App\Repository\CouponRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=CouponRepository::class)
 */
class Coupon
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
    private $codeCoup;

    /**
     * @ORM\OneToOne(targetEntity=Commande::class, mappedBy="codeCoup")
     */
    private $commande;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getCodeCoup(): ?string
    {
        return $this->codeCoup;
    }

    public function setCodeCoup(string $codeCoup): self
    {
        $this->codeCoup = $codeCoup;

        return $this;
    }

    public function getCommande(): ?string
    {
        return $this->commande;
    }
}
