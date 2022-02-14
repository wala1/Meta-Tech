<?php

namespace App\Repository;
use App\Entity\PubBack;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @method PubBack|null find($id, $lockMode = null, $lockVersion = null)
 * @method PubBack|null findOneBy(array $criteria, array $orderBy = null)
 * @method PubBack[]    findAll()
 * @method PubBack[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class PubBackRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, PubBack::class);
    }

    // /**
    //  * @return PubBack[] Returns an array of PubBack objects
    //  */
    /*
    public function findByExampleField($value)
    {
        return $this->createQueryBuilder('p')
            ->andWhere('p.exampleField = :val')
            ->setParameter('val', $value)
            ->orderBy('p.id', 'ASC')
            ->setMaxResults(10)
            ->getQuery()
            ->getResult()
        ;
    }
    */

    /*
    public function findOneBySomeField($value): ?PubBack
    {
        return $this->createQueryBuilder('p')
            ->andWhere('p.exampleField = :val')
            ->setParameter('val', $value)
            ->getQuery()
            ->getOneOrNullResult()
        ;
    }
    */
}
