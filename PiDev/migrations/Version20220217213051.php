<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220217213051 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE publication (id INT AUTO_INCREMENT NOT NULL, titre_publ VARCHAR(255) NOT NULL, description_publ LONGTEXT NOT NULL, image_publ VARCHAR(255) NOT NULL, temps_publ DATETIME NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE reclamation (id INT AUTO_INCREMENT NOT NULL, name_reclamation VARCHAR(255) NOT NULL, email_reclamation VARCHAR(255) NOT NULL, desc_reclamation VARCHAR(1000) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('DROP TABLE commande');
        $this->addSql('ALTER TABLE avis CHANGE id_user_id id_user_id INT NOT NULL, CHANGE time_avis time_avis DATETIME NOT NULL');
        $this->addSql('ALTER TABLE produit CHANGE rating_prod rating_prod INT NOT NULL');
        $this->addSql('ALTER TABLE pub_back DROP promo_pub');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE commande (id INT AUTO_INCREMENT NOT NULL, place VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`, payement_method VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`, code_coupon VARCHAR(255) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, comment VARCHAR(600) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, date DATE NOT NULL, phone_number INT DEFAULT NULL, street VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_unicode_ci`, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('DROP TABLE publication');
        $this->addSql('DROP TABLE reclamation');
        $this->addSql('ALTER TABLE avis CHANGE id_user_id id_user_id INT DEFAULT 103 NOT NULL, CHANGE desc_avis desc_avis VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE time_avis time_avis DATETIME DEFAULT CURRENT_TIMESTAMP');
        $this->addSql('ALTER TABLE categorie CHANGE nom_categorie nom_categorie VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE coupon CHANGE code_coup code_coup VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE produit CHANGE nom_prod nom_prod VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_prod desc_prod VARCHAR(600) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_prod image_prod VARCHAR(400) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE rating_prod rating_prod INT DEFAULT NULL, CHANGE model_prod model_prod VARCHAR(1500) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE pub_back ADD promo_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE nom_pub nom_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_pub desc_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE prix_pub prix_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_pub image_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE sous_categorie CHANGE nom_sous_categ nom_sous_categ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE user CHANGE email email VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE username username VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE password password VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE reset_token reset_token VARCHAR(50) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE roles roles LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci` COMMENT \'(DC2Type:json)\'');
    }
}
