<?php

namespace App\Entity;

use App\Repository\SponsorRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=SponsorRepository::class)
 */
class Sponsor
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
    private $marqueSponsor;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $LogoSponsor;

    /**
     * @ORM\OneToMany(targetEntity=PubBack::class, mappedBy="sponsor")
     */
    private $pubBacks;

    public function __construct()
    {
        $this->pubBacks = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getMarqueSponsor(): ?string
    {
        return $this->marqueSponsor;
    }

    public function setMarqueSponsor(string $marqueSponsor): self
    {
        $this->marqueSponsor = $marqueSponsor;

        return $this;
    }

    public function getLogoSponsor(): ?string
    {
        return $this->LogoSponsor;
    }

    public function setLogoSponsor(string $LogoSponsor): self
    {
        $this->LogoSponsor = $LogoSponsor;

        return $this;
    }

    /**
     * @return Collection|PubBack[]
     */
    public function getPubBacks(): Collection
    {
        return $this->pubBacks;
    }

    public function addPubBack(PubBack $pubBack): self
    {
        if (!$this->pubBacks->contains($pubBack)) {
            $this->pubBacks[] = $pubBack;
            $pubBack->setSponsor($this);
        }

        return $this;
    }

    public function removePubBack(PubBack $pubBack): self
    {
        if ($this->pubBacks->removeElement($pubBack)) {
            // set the owning side to null (unless already changed)
            if ($pubBack->getSponsor() === $this) {
                $pubBack->setSponsor(null);
            }
        }

        return $this;
    }
}
