<?php

namespace App\Entity;

use App\Repository\PublicationRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert ; 
use Symfony\Component\Serializer\Annotation\Groups; 
/**
 * @ORM\Entity(repositoryClass=PublicationRepository::class)
 */
class Publication
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @Groups("post:read")
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups("post:read")
     * @Assert\Length(min=6, max=255, minMessage="Votre titre est court")
     */
    private $titre_publ;

    /**
     * @ORM\Column(type="text")
     * @Groups("post:read")
     * @Assert\Length(min=10)
     */
    private $description_publ;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups("post:read")
     *  @Assert\Url()
     */
    private $image_publ;

    /**
     * @ORM\Column(type="datetime")
     *  @Groups("post:read")
     */
    private $temps_publ;

    /**
     * @ORM\OneToMany(targetEntity=Commentaire::class, mappedBy="id_Publ", orphanRemoval=true)
     *@Groups("post:read")
     */
    private $commentaires;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="publications")
     *@Groups("post:read")
     */
    private $utilisateur;

    /**
     * @ORM\ManyToMany(targetEntity=User::class)
     */
    private $likes;

    public function __construct()
    {
        $this->commentaires = new ArrayCollection();
        $this->likes = new ArrayCollection();
    }
    

    
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

    /**
     * @return Collection|Commentaire[]
     */
    public function getCommentaires(): Collection
    {
        return $this->commentaires;
    }

    public function addCommentaire(Commentaire $commentaire): self
    {
        if (!$this->commentaires->contains($commentaire)) {
            $this->commentaires[] = $commentaire;
            $commentaire->setIdPubl($this);
        }

        return $this;
    }

    public function removeCommentaire(Commentaire $commentaire): self
    {
        if ($this->commentaires->removeElement($commentaire)) {
            // set the owning side to null (unless already changed)
            if ($commentaire->getIdPubl() === $this) {
                $commentaire->setIdPubl(null);
            }
        }

        return $this;
    }

    public function getUtilisateur(): ?User
    {
        return $this->utilisateur;
    }

    public function setUtilisateur(?User $utilisateur): self
    {
        $this->utilisateur = $utilisateur;

        return $this;
    }

    /**
     * @return Collection<int, User>
     */
    public function getLikes(): Collection
    {
        return $this->likes;
    }

    public function addLike(User $like): self
    {
        if (!$this->likes->contains($like)) {
            $this->likes[] = $like;
        }

        return $this;
    }

    public function removeLike(User $like): self
    {
        $this->likes->removeElement($like);

        return $this;
    }

    

    
}
