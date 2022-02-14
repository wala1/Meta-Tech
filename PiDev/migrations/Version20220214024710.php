<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220214024710 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE categorie (id INT AUTO_INCREMENT NOT NULL, nom_categorie VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE produit (id INT AUTO_INCREMENT NOT NULL, categorie_prod_id INT DEFAULT NULL, sous_categ_prod_id INT NOT NULL, nom_prod VARCHAR(255) NOT NULL, desc_prod VARCHAR(600) NOT NULL, image_prod VARCHAR(400) NOT NULL, prix_prod DOUBLE PRECISION NOT NULL, promo_prod DOUBLE PRECISION NOT NULL, rating_prod INT NOT NULL, model_prod VARCHAR(255) DEFAULT NULL, INDEX IDX_29A5EC275E4B91D7 (categorie_prod_id), INDEX IDX_29A5EC276348D6B3 (sous_categ_prod_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE sous_categorie (id INT AUTO_INCREMENT NOT NULL, nom_sous_categ VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE produit ADD CONSTRAINT FK_29A5EC275E4B91D7 FOREIGN KEY (categorie_prod_id) REFERENCES categorie (id)');
        $this->addSql('ALTER TABLE produit ADD CONSTRAINT FK_29A5EC276348D6B3 FOREIGN KEY (sous_categ_prod_id) REFERENCES sous_categorie (id)');
        $this->addSql('ALTER TABLE panier ADD prix_totale DOUBLE PRECISION NOT NULL, DROP prix');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE produit DROP FOREIGN KEY FK_29A5EC275E4B91D7');
        $this->addSql('ALTER TABLE produit DROP FOREIGN KEY FK_29A5EC276348D6B3');
        $this->addSql('DROP TABLE categorie');
        $this->addSql('DROP TABLE produit');
        $this->addSql('DROP TABLE sous_categorie');
        $this->addSql('ALTER TABLE panier ADD prix VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, DROP prix_totale');
        $this->addSql('ALTER TABLE user CHANGE email email VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE username username VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE password password VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE reset_token reset_token VARCHAR(50) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE roles roles LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci` COMMENT \'(DC2Type:json)\'');
    }
}
