<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;

/**
 * @ORM\Entity(repositoryClass=CommandeRepository::class)
 */
class Commande
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $firstName;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $lastName;
    
    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $street_Adress;

    /**
     * @ORM\Column(type="string", length=255)
     */
    public $city;

    /**
     * @ORM\Column(type="integer", length=255)
     * @Assert\Length(min="4",minMessage="Your zip/post code must be at least 4 characters")
     * @Assert\NotNull(message="This field is required")
     */
    public $zip_PostalCode;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $country;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $company;

    /**
     * @ORM\Column(type="integer", length=8)
     * @Assert\Length(min="8",minMessage="Your phone number must be at least 8 characters")
     * @Assert\NotNull(message="This field is required")
     */
    public $phone_Number;
    
        /**
     * @ORM\Column(type="string", length=10)
     * @Assert\NotNull(message="This field is required")
     */
    public $payement_method;

    /**
     * @ORM\Column(type="date", length=255)
     * @Assert\NotNull(message="This field is required")
     */
    public $date;
    
    /**
     * @ORM\Column(type="string", length=500)
     */
    public $delivery_comment;

    /**
     * @ORM\Column(type="string")
     */
    public $newsletter;
    
        /**
     * @ORM\Column(type="string", length=500)
     */
    public $order_comment;

    /**
     * @ORM\Column(type="string", length=20)
     */
    public $code_Coupon;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getPayementMethod(): ?string
    {
        return $this->payement_method;
    }

    public function setPayementMethod(string $payement_method): self
    {
        $this->payement_method = $payement_method;

        return $this;
    }

    public function getCodeCoupon(): ?string
    {
        return $this->code_Coupon;
    }

    public function setCodeCoupon(?string $code_Coupon): self
    {
        $this->code_Coupon = $code_Coupon;

        return $this;
    }

    public function getComment(): ?string
    {
        return $this->comment;
    }

    public function setComment(?string $comment): self
    {
        $this->comment = $comment;

        return $this;
    }

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getPhoneNumber(): ?int
    {
        return $this->phone_Number;
    }

    public function setPhoneNumber(?int $phone_Number): self
    {
        $this->phone_Number = $phone_Number;

        return $this;
    }

    public function getStreetAdress(): ?string
    {
        return $this->street_Adress;
    }

    public function setStreetAdress(string $street_Adress): self
    {
        $this->street_Adress = $street_Adress;
        return $this;
    }

    public function setCity(string $city): self
    {
        $this->city = $city;
        return $this;
    }

    public function setZip_PostalCode(int $zip_PostalCode): self
    {
        $this->zip_PostalCode = $zip_PostalCode;
        return $this;
    }

    public function setCountry(string $country): self
    {
        $this->country = $country;
        return $this;
    }

    public function setCompany(string $company): self
    {
        $this->company = $company;
        return $this;
    }

    public function setDelivery_comment(string $delivery_comment): self
    {
        $this->delivery_comment = $delivery_comment;
        return $this;
    }

    public function setNewsletter(string $newsletter): self
    {
        $this->newsletter = $newsletter;
        return $this;
    }

    public function setOrder_comment(string $order_comment): self
    {
        $this->order_comment = $order_comment;
        return $this;
    }

    
}
