<?php

namespace App\Entity;

use App\Repository\UserRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
/**
 * @ORM\Entity(repositoryClass=UserRepository::class)
 * @UniqueEntity(
 * fields={"email"},
 * message= "This email is already in use")
 */
class User implements UserInterface
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\OneToMany(targetEntity=Panier::class, mappedBy="user_panier")
     */
    private $panier;

    public function __construct()
    {
        $this->panier = new ArrayCollection();
        $this->calendars = new ArrayCollection();
        
    }

    /**
     * @return Collection|Panier[]
     */
    public function getPanier()
    {
        return $this->panier;
    }

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Email(message = "The email '{{ value }}' is not a valid email.")
     * @Assert\NotNull(message="this field is  required")
     
     */
    private $email;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull(message="this field is  required")
      * @Assert\Length(
     *      min = 3,
     *      max = 10,
     *      minMessage = "Your username must be at least {{ limit }} characters long",
     *      maxMessage = "Your username  cannot be longer than {{ limit }} characters"
     * )
     
     
     */
    private $username;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Length(min="8",minMessage="Your password must be at least 8 characters")
     * @Assert\NotNull(message="this field is  required")
     
    
     */
    private $password;
    
 /**
    
     * @Assert\EqualTo(propertyPath="password",message="Password does not match")
     * @Assert\NotNull(message="this field is  required")
     */
    public $confirm_password;

    /**
     * @ORM\Column(type="string", length=50, nullable=true)
     */
    private $reset_token;

    /**
     * @ORM\Column(type="json")
     */
    private $Roles = [];

    /**
     * @ORM\OneToMany(targetEntity=Avis::class, mappedBy="idUser")
     */
    private $avis;

    /**
     * @ORM\ManyToMany(targetEntity=Calendar::class, mappedBy="user")
     */
    private $calendars;

    // /**
    //  * @ORM\Column(type="bigint")
    //   * @Assert\Positive(message="Your phone number cannot be negative")
    //     * @Assert\Length(
    //  *      min = 8,
    //  *      max=10,
    //  *      minMessage = "The Phone Number should have exactly {{limit}}",
    //  *      maxMessage = "The Event Title cannot be longer than {{ limit }} characters"
    //  * )
     

    

    //  */
    // private $phoneNumber;

    /*public function __construct()
    {
        $this->avis = new ArrayCollection();
    }*/

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
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

    public function getPassword(): ?string
    {
        return $this->password;
    }
    public function getConfirm_password(): ?string
    {
        return $this->confirm_password;
    }

    public function setPassword(string $password): self
    {
        $this->password = $password;

        return $this;
    }
    public function setConfirm_password(string $confirm_password): self
    {
        $this->confirm_password = $confirm_password;

        return $this;
    }
    public function eraseCredentials(){}
    public function getSalt(){}
    public function getRoles(): ?array 
    {
        // return ['ROLE_USER'];
        $roles = $this->Roles;
        // guarantee every user at least has ROLE_USER
        $roles[] = 'ROLE_USER';

        return array_unique($roles);
    }

    public function getResetToken(): ?string
    {
        return $this->reset_token;
    }

    public function setResetToken(?string $reset_token): self
    {
        $this->reset_token = $reset_token;

        return $this;
    }

    public function setRoles(array $Roles): self
    {
        $this->Roles = $Roles;

        return $this;
    }

    /**
     * @return Collection|Avis[]
     */
    public function getAvis(): Collection
    {
        return $this->avis;
    }

    public function addAvi(Avis $avi): self
    {
        if (!$this->avis->contains($avi)) {
            $this->avis[] = $avi;
            $avi->setIdUser($this);
        }

        return $this;
    }

    public function removeAvi(Avis $avi): self
    {
        if ($this->avis->removeElement($avi)) {
            // set the owning side to null (unless already changed)
            if ($avi->getIdUser() === $this) {
                $avi->setIdUser(null);
            }
        }

        return $this;
    }

    // public function getPhoneNumber(): ?string
    // {
    //     return $this->phoneNumber;
    // }

    // public function setPhoneNumber(string $phoneNumber): self
    // {
    //     $this->phoneNumber = $phoneNumber;

    //     return $this;
    // }

    /**
     * @return Collection|Calendar[]
     */
    public function getCalendars(): Collection
    {
        return $this->calendars;
    }

    public function addCalendar(Calendar $calendar): self
    {
        if (!$this->calendars->contains($calendar)) {
            $this->calendars[] = $calendar;
            $calendar->addUser($this);
        }

        return $this;
    }

    public function removeCalendar(Calendar $calendar): self
    {
        if ($this->calendars->removeElement($calendar)) {
            $calendar->removeUser($this);
        }

        return $this;
    }

}
