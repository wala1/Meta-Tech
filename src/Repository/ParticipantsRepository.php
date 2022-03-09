<?php

namespace App\Repository;

use App\Entity\Participants;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method Participants|null find($id, $lockMode = null, $lockVersion = null)
 * @method Participants|null findOneBy(array $criteria, array $orderBy = null)
 * @method Participants[]    findAll()
 * @method Participants[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ParticipantsRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Participants::class);
    }

//    /**
//     * @return Participants[] Returns an array of Participants objects
//     */
    
//     public function findByEvent($id)
//     {
//         return $this->createQueryBuilder('p')
//             ->andWhere('p.event = :val')
//             ->setParameter('val', $id)
//             // ->orderBy('p.id', 'ASC')
//             // ->setMaxResults(10)
//             ->getQuery()
//             ->getResult()
//         ;
//     }
    

    
    // public function findOneByUser(): ?Participants
    // {

    //     return $this->createQueryBuilder('p')
    //         ->select()
    //         ->andWhere('p.event_id = :val')
    //         ->setParameter('val', $id)
    //         ->getQuery()
    //         ->getOneOrNullResult()
    //     ;
    // }
    
}
