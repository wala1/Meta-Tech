<?php

namespace App\Entity;

use App\Repository\AvisRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Form\Extension\Core\Type\IntegerType ;

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
     * @Assert\Range(
     *      min = 1,
     *      max = 5,
     *      notInRangeMessage = "Please insert a rating between 1 and 5.",
     * )
     * @Assert\NotBlank(message="Please add a rating.")
     * @Assert\Type(type="numeric", message="Rating should be an integer.")
     */
    private $ratingAvis;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Make sure to add your review.")
     */
    private $descAvis;

    /**
     * @ORM\Column(type="datetime")
     * @ORM\JoinColumn(nullable=true)
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
