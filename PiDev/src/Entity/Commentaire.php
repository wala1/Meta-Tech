<?php

namespace App\Entity;

use App\Repository\CommentaireRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=CommentaireRepository::class)
 */
class Commentaire
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
    private $username;

    /**
     * @ORM\ManyToOne(targetEntity=Publication::class, inversedBy="commentaires")
     * @ORM\JoinColumn(nullable=false)
     */
    private $id_Publ;

    /**
     * @ORM\Column(type="text")
     */
    private $description_comm;

    /**
     * @ORM\Column(type="datetime")
     */
    private $temps_comm;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getUsername(): ?string
    {
        return $this->username;
    }

    public function setUsername(string $username): self
    {
        $this->username = $username;

        return $this;
    }

    public function getIdPubl(): ?Publication
    {
        return $this->id_Publ;
    }

    public function setIdPubl(?Publication $id_Publ): self
    {
        $this->id_Publ = $id_Publ;

        return $this;
    }

    public function getDescriptionComm(): ?string
    {
        return $this->description_comm;
    }

    public function setDescriptionComm(string $description_comm): self
    {
        $this->description_comm = $description_comm;

        return $this;
    }

    public function getTempsComm(): ?\DateTimeInterface
    {
        return $this->temps_comm;
    }

    public function setTempsComm(\DateTimeInterface $temps_comm): self
    {
        $this->temps_comm = $temps_comm;

        return $this;
    }
}
