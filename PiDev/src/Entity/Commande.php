<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use Doctrine\ORM\Mapping as ORM;

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
     */
    private $place;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $payement_method;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    private $code_Coupon;

    /**
     * @ORM\Column(type="string", length=600, nullable=true)
     */
    private $comment;

    /**
     * @ORM\Column(type="date")
     */
    private $date;

    /**
     * @ORM\Column(type="integer", nullable=true)
     */
    private $phone_Number;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $street;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getPlace(): ?string
    {
        return $this->place;
    }

    public function setPlace(string $place): self
    {
        $this->place = $place;

        return $this;
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

    public function getStreet(): ?string
    {
        return $this->street;
    }

    public function setStreet(string $street): self
    {
        $this->street = $street;

        return $this;
    }
}
