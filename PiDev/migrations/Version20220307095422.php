<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220307095422 extends AbstractMigration
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
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE avis');
        $this->addSql('ALTER TABLE calendar CHANGE title title VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description description LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE background_color background_color VARCHAR(7) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE border_color border_color VARCHAR(7) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE text_color text_color VARCHAR(7) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_event image_event VARCHAR(400) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE origine origine VARCHAR(6) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE categorie CHANGE nom_categorie nom_categorie VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE commande CHANGE place place VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE payement_method payement_method VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE code_coupon code_coupon VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE comment comment VARCHAR(600) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE street street VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE coupon CHANGE code_coup code_coup VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE produit CHANGE nom_prod nom_prod VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_prod desc_prod VARCHAR(600) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_prod image_prod VARCHAR(400) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE model_prod model_prod VARCHAR(1500) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE pub_back CHANGE nom_pub nom_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_pub desc_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE prix_pub prix_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE promo_pub promo_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_pub image_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE publication CHANGE titre_publ titre_publ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description_publ description_publ LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_publ image_publ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE reclamation CHANGE name_reclamation name_reclamation VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE email_reclamation email_reclamation VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_reclamation desc_reclamation VARCHAR(1000) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE sous_categorie CHANGE nom_sous_categ nom_sous_categ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE sponsor CHANGE marque_sponsor marque_sponsor VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE logo_sponsor logo_sponsor VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE user CHANGE email email VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE username username VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE password password VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE reset_token reset_token VARCHAR(50) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE roles roles LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci` COMMENT \'(DC2Type:json)\', CHANGE activation_token activation_token VARCHAR(50) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE facebook_id facebook_id VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE facebook_access_token facebook_access_token VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE age age VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE gender gender VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
    }
}
