<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220215083721 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE avis (id INT AUTO_INCREMENT NOT NULL, id_prod_id INT NOT NULL, id_user_id INT NOT NULL, rating_avis INT NOT NULL, desc_avis VARCHAR(255) NOT NULL, time_avis DATETIME NOT NULL, INDEX IDX_8F91ABF0DF559605 (id_prod_id), INDEX IDX_8F91ABF079F37AE5 (id_user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE avis ADD CONSTRAINT FK_8F91ABF0DF559605 FOREIGN KEY (id_prod_id) REFERENCES produit (id)');
        $this->addSql('ALTER TABLE avis ADD CONSTRAINT FK_8F91ABF079F37AE5 FOREIGN KEY (id_user_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE produit CHANGE model_prod model_prod VARCHAR(255) DEFAULT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE avis');
        $this->addSql('ALTER TABLE categorie CHANGE nom_categorie nom_categorie VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE coupon CHANGE code_coup code_coup VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE produit CHANGE nom_prod nom_prod VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_prod desc_prod VARCHAR(600) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_prod image_prod VARCHAR(400) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE model_prod model_prod VARCHAR(1500) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE pub_back CHANGE nom_pub nom_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_pub desc_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE prix_pub prix_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_pub image_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE publication CHANGE titre_publ titre_publ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description_publ description_publ LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_publ image_publ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE sous_categorie CHANGE nom_sous_categ nom_sous_categ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE user CHANGE email email VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE username username VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE password password VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE reset_token reset_token VARCHAR(50) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE roles roles LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci` COMMENT \'(DC2Type:json)\'');
    }
}
