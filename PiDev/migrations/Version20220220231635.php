<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220220231635 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE categorie (id INT AUTO_INCREMENT NOT NULL, nom_categorie VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE commentaire (id INT AUTO_INCREMENT NOT NULL, id_publ_id INT NOT NULL, username VARCHAR(255) NOT NULL, description_comm LONGTEXT NOT NULL, temps_comm DATETIME NOT NULL, INDEX IDX_67F068BCFF744AE3 (id_publ_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE produit (id INT AUTO_INCREMENT NOT NULL, categorie_prod_id INT DEFAULT NULL, nom_prod VARCHAR(255) NOT NULL, desc_prod VARCHAR(600) NOT NULL, image_prod VARCHAR(400) NOT NULL, prix_prod DOUBLE PRECISION NOT NULL, promo_prod DOUBLE PRECISION NOT NULL, INDEX IDX_29A5EC275E4B91D7 (categorie_prod_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE commentaire ADD CONSTRAINT FK_67F068BCFF744AE3 FOREIGN KEY (id_publ_id) REFERENCES publication (id)');
        $this->addSql('ALTER TABLE produit ADD CONSTRAINT FK_29A5EC275E4B91D7 FOREIGN KEY (categorie_prod_id) REFERENCES categorie (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE produit DROP FOREIGN KEY FK_29A5EC275E4B91D7');
        $this->addSql('DROP TABLE categorie');
        $this->addSql('DROP TABLE commentaire');
        $this->addSql('DROP TABLE produit');
    }
}
