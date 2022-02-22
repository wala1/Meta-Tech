<?php

namespace App\Entity;

use App\Repository\CalendarRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


/**
 * @ORM\Entity(repositoryClass=CalendarRepository::class)
 */
class Calendar
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
    * @Assert\NotBlank(message="Event title is required")
      * @Assert\Length(
     *      min = 3,
     *      max = 10,
     *      minMessage = "The Event Title must be at least {{ limit }} characters long",
     *      maxMessage = "The Event Title cannot be longer than {{ limit }} characters"
     * )
     * @Assert\Regex(
     *     pattern="/\d/",
     *     match=false,
     *     message="The Event Title  cannot contain a number"
     * )

     */
    private $title;

    /**
     * @ORM\Column(type="datetime")
    * @Assert\NotBlank(message="Event start time is required")
    

    */
    private $start;

    /**
     * @ORM\Column(type="datetime")
    * @Assert\NotBlank(message="Event end time is required")

     */
    private $end;

    /**
     * @ORM\Column(type="text")
     * @Assert\NotBlank(message="Event Description is required")
      * @Assert\Length(
     *      min = 2,
     *      max = 50,
     *      minMessage = "The Event Description must be at least {{ limit }} characters long",
     *      maxMessage = "The Event Description cannot be longer than {{ limit }} characters"
     * )
     */
    private $description;

    /**
     * @ORM\Column(type="boolean")
     */
    private $allDay;

    /**
     * @ORM\Column(type="string", length=7)
     */
    private $backgroundColor;

    /**
     * @ORM\Column(type="string", length=7)
     */
    private $borderColor;

    /**
     * @ORM\Column(type="string", length=7)
     */
    private $textColor;

    /**
     * @ORM\Column(type="string", length=400)
     * @Assert\NotBlank(message="Event image is required")
     */
    private $imageEvent;
    //Each event have many users / each user have one event


    /**
     * @ORM\OneToMany(targetEntity=User::class, mappedBy="calendar")
     */
    private $participant;

  

    public function __construct()
    {
        $this->user = new ArrayCollection();
        $this->participant = new ArrayCollection();
    }

  
    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): self
    {
        $this->title = $title;

        return $this;
    }

    public function getStart(): ?\DateTimeInterface
    {
        return $this->start;
    }

    public function setStart(\DateTimeInterface $start): self
    {
        $this->start = $start;

        return $this;
    }

    public function getEnd(): ?\DateTimeInterface
    {
        return $this->end;
    }

    public function setEnd(\DateTimeInterface $end): self
    {
        $this->end = $end;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getAllDay(): ?bool
    {
        return $this->allDay;
    }

    public function setAllDay(bool $allDay): self
    {
        $this->allDay = $allDay;

        return $this;
    }

    public function getBackgroundColor(): ?string
    {
        return $this->backgroundColor;
    }

    public function setBackgroundColor(string $backgroundColor): self
    {
        $this->backgroundColor = $backgroundColor;

        return $this;
    }

    public function getBorderColor(): ?string
    {
        return $this->borderColor;
    }

    public function setBorderColor(string $borderColor): self
    {
        $this->borderColor = $borderColor;

        return $this;
    }

    public function getTextColor(): ?string
    {
        return $this->textColor;
    }

    public function setTextColor(string $textColor): self
    {
        $this->textColor = $textColor;

        return $this;
    }

    public function getImageEvent(): ?string
    {
        return $this->imageEvent;
    }

    public function setImageEvent(string $imageEvent): self
    {
        $this->imageEvent = $imageEvent;

        return $this;
    }

    /**
     * @return Collection|User[]
     */
    public function getParticipant(): Collection
    {
        return $this->participant;
    }

    public function addParticipant(User $participant): self
    {
        if (!$this->participant->contains($participant)) {
            $this->participant[] = $participant;
            $participant->setCalendar($this);
        }

        return $this;
    }

    public function removeParticipant(User $participant): self
    {
        if ($this->participant->removeElement($participant)) {
            // set the owning side to null (unless already changed)
            if ($participant->getCalendar() === $this) {
                $participant->setCalendar(null);
            }
        }

        return $this;
    }

  


}
