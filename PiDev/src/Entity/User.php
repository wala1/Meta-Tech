<?php

namespace App\Entity;

use App\Repository\UserRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
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

    /**
     * @ORM\OneToMany(targetEntity=Commande::class, mappedBy="user_commande")
     */
    private $commande;


    public function __construct()
    {
        $this->panier = new ArrayCollection();
        $this->commande = new ArrayCollection();
    }

    /**
     * @return Collection|Panier[]
     */
    public function getPanier(): Collection
    {
        return $this->panier;
    }

    /**
     * @return Collection|Commande[]
     */
    public function getCommande(): Collection
    {
        return $this->commande;
    }

    public function addPanier(Panier $panier): self
    {
        if (!$this->panier->contains($panier)) {
            $this->panier[] = $panier;
            $panier->setUserPanier($this);
        }

        return $this;
    }

    public function removePanier(Panier $panier): self
    {
        if ($this->panier->removeElement($panier)) {
            if ($panier->getUserPanier() === $this) {
                $panier->setUserPanier(null);
            }
        }

        return $this;
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

    public function setPassword(string $password): self
    {
        $this->password = $password;

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

  

}
