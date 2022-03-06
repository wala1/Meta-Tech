<?php

namespace App\Entity;

use App\Repository\ReclamationRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=ReclamationRepository::class)
 */
class Reclamation
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Please enter your name.")
     */
    private $nameReclamation;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Email(
     *     message = "Please enter a valid email.",
     *     checkMX = true
     * )
     * @Assert\NotBlank(message="Please enter your email.")
     */
    private $EmailReclamation;

    /**
     * @ORM\Column(type="string", length=1000) 
     * @Assert\NotBlank(message="Please enter a description.")
     * @Assert\Length(
     *      min = 10,
     *      max = 1500,
     *      minMessage = "Description must be atleast 10 characters.",
     *      maxMessage = "Description must be no longer than 1500 characters."
     * )
     */
    private $descReclamation;

    /**
     * @ORM\Column(type="integer", nullable=true)
     */
    private $traiteReclamation=0;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNameReclamation(): ?string
    {
        return $this->nameReclamation;
    }

    public function setNameReclamation(string $nameReclamation): self
    {
        $this->nameReclamation = $nameReclamation;

        return $this;
    }

    public function getEmailReclamation(): ?string
    {
        return $this->EmailReclamation;
    }

    public function setEmailReclamation(string $EmailReclamation): self
    {
        $this->EmailReclamation = $EmailReclamation;

        return $this;
    }

    public function getDescReclamation(): ?string
    {
        return $this->descReclamation;
    }

    public function setDescReclamation(string $descReclamation): self
    {
        $this->descReclamation = $descReclamation;

        return $this;
    }

    public function getTraiteReclamation(): ?int
    {
        return $this->traiteReclamation;
    }

    public function setTraiteReclamation(?int $traiteReclamation): self
    {
        $this->traiteReclamation = $traiteReclamation;

        return $this;
    }
}
