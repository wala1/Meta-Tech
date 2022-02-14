<?php

namespace App\Entity;

use App\Repository\PublicationRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=PublicationRepository::class)
 */
class Publication
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
    private $titre_publ;

    /**
     * @ORM\Column(type="text")
     */
    private $description_publ;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $image_publ;

    /**
     * @ORM\Column(type="datetime")
     */
    private $temps_publ;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitrePubl(): ?string
    {
        return $this->titre_publ;
    }

    public function setTitrePubl(string $titre_publ): self
    {
        $this->titre_publ = $titre_publ;

        return $this;
    }

    public function getDescriptionPubl(): ?string
    {
        return $this->description_publ;
    }

    public function setDescriptionPubl(string $description_publ): self
    {
        $this->description_publ = $description_publ;

        return $this;
    }

    public function getImagePubl(): ?string
    {
        return $this->image_publ;
    }

    public function setImagePubl(string $image_publ): self
    {
        $this->image_publ = $image_publ;

        return $this;
    }

    public function getTempsPubl(): ?\DateTimeInterface
    {
        return $this->temps_publ;
    }

    public function setTempsPubl(\DateTimeInterface $temps_publ): self
    {
        $this->temps_publ = $temps_publ;

        return $this;
    }
}
