<?php

namespace App\Entity;

use App\Repository\CouponRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

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
     * @Assert\Length(
     *      min = 4,
     *      max = 8,
     *      minMessage = "The Coupon Code must be at least {{ limit }} characters long",
     *      maxMessage = "The Coupon Code cannot be longer than {{ limit }} characters"
     * )
     */
    private $codeCoup;

    /**
     * @ORM\ManyToOne(targetEntity=PubBack::class, inversedBy="coupon")
     * 
     */
    private $pubBack;

    /**
     * @ORM\OneToMany(targetEntity=PubBack::class, mappedBy="coupon1")
     */
    private $pubBack1;

    public function __construct()
    {
        $this->pubBack1 = new ArrayCollection();
    }
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

    public function getPubBack(): ?PubBack
    {
        return $this->pubBack;
    }

    public function setPubBack(?PubBack $pubBack): self
    {
        $this->pubBack = $pubBack;

        return $this;
    }

    /**
     * @return Collection|PubBack[]
     */
    public function getPubBack1(): Collection
    {
        return $this->pubBack1;
    }

    public function addPubBack1(PubBack $pubBack1): self
    {
        if (!$this->pubBack1->contains($pubBack1)) {
            $this->pubBack1[] = $pubBack1;
            $pubBack1->setCoupon1($this);
        }

        return $this;
    }

    public function removePubBack1(PubBack $pubBack1): self
    {
        if ($this->pubBack1->removeElement($pubBack1)) {
            // set the owning side to null (unless already changed)
            if ($pubBack1->getCoupon1() === $this) {
                $pubBack1->setCoupon1(null);
            }
        }

        return $this;
    public function getCommande(): ?string
    {
        return $this->commande;
    }
}
