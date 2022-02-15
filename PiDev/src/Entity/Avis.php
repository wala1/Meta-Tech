<?php

namespace App\Entity;

use App\Repository\AvisRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=AvisRepository::class)
 */
class Avis
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\ManyToOne(targetEntity=Produit::class, inversedBy="avis")
     * @ORM\JoinColumn(nullable=false)
     */
    private $idProd;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="avis")
     * @ORM\JoinColumn(nullable=false)
     */
    private $idUser;

    /**
     * @ORM\Column(type="integer")
     */
    private $ratingAvis;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $descAvis;

    /**
     * @ORM\Column(type="datetime")
     */
    private $timeAvis;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getIdProd(): ?Produit
    {
        return $this->idProd;
    }

    public function setIdProd(?Produit $idProd): self
    {
        $this->idProd = $idProd;

        return $this;
    }

    public function getIdUser(): ?User
    {
        return $this->idUser;
    }

    public function setIdUser(?User $idUser): self
    {
        $this->idUser = $idUser;

        return $this;
    }

    public function getRatingAvis(): ?int
    {
        return $this->ratingAvis;
    }

    public function setRatingAvis(int $ratingAvis): self
    {
        $this->ratingAvis = $ratingAvis;

        return $this;
    }

    public function getDescAvis(): ?string
    {
        return $this->descAvis;
    }

    public function setDescAvis(string $descAvis): self
    {
        $this->descAvis = $descAvis;

        return $this;
    }

    public function getTimeAvis(): ?\DateTimeInterface
    {
        return $this->timeAvis;
    }

    public function setTimeAvis(\DateTimeInterface $timeAvis): self
    {
        $this->timeAvis = $timeAvis;

        return $this;
    }
}
