<?php

namespace App\Entity;

use App\Repository\ParticipantsRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=ParticipantsRepository::class)
 */
class Participants
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\ManyToOne(targetEntity=Calendar::class, inversedBy="participants" , cascade={"remove"})
     */
    private $event;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="participants" )
     */
    private $user;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getEvent(): ?Calendar
    {
        return $this->event;
    }

    public function setEvent(?Calendar $event): self
    {
        $this->event = $event;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }
}
