<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220307124543 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE reclamation (id INT AUTO_INCREMENT NOT NULL, name_reclamation VARCHAR(255) NOT NULL, email_reclamation VARCHAR(255) NOT NULL, desc_reclamation VARCHAR(1000) NOT NULL, traite_reclamation INT DEFAULT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE sponsor (id INT AUTO_INCREMENT NOT NULL, marque_sponsor VARCHAR(255) NOT NULL, logo_sponsor VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('DROP TABLE panier');
        $this->addSql('ALTER TABLE coupon ADD pub_back_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE coupon ADD CONSTRAINT FK_64BF3F02F620505 FOREIGN KEY (pub_back_id) REFERENCES pub_back (id)');
        $this->addSql('CREATE INDEX IDX_64BF3F02F620505 ON coupon (pub_back_id)');
        $this->addSql('ALTER TABLE produit ADD stock_prod INT DEFAULT NULL, CHANGE model_prod model_prod VARCHAR(1500) DEFAULT NULL');
        $this->addSql('ALTER TABLE pub_back ADD coupon1_id INT DEFAULT NULL, ADD produit_id INT DEFAULT NULL, ADD calender_id INT DEFAULT NULL, ADD sponsor_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE pub_back ADD CONSTRAINT FK_C4D759155C6F21EA FOREIGN KEY (coupon1_id) REFERENCES coupon (id)');
        $this->addSql('ALTER TABLE pub_back ADD CONSTRAINT FK_C4D75915F347EFB FOREIGN KEY (produit_id) REFERENCES produit (id)');
        $this->addSql('ALTER TABLE pub_back ADD CONSTRAINT FK_C4D75915FFC00408 FOREIGN KEY (calender_id) REFERENCES calendar (id)');
        $this->addSql('ALTER TABLE pub_back ADD CONSTRAINT FK_C4D7591512F7FB51 FOREIGN KEY (sponsor_id) REFERENCES sponsor (id)');
        $this->addSql('CREATE INDEX IDX_C4D759155C6F21EA ON pub_back (coupon1_id)');
        $this->addSql('CREATE INDEX IDX_C4D75915F347EFB ON pub_back (produit_id)');
        $this->addSql('CREATE INDEX IDX_C4D75915FFC00408 ON pub_back (calender_id)');
        $this->addSql('CREATE INDEX IDX_C4D7591512F7FB51 ON pub_back (sponsor_id)');
        $this->addSql('ALTER TABLE user ADD gender VARCHAR(255) NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE pub_back DROP FOREIGN KEY FK_C4D7591512F7FB51');
        $this->addSql('CREATE TABLE panier (id INT AUTO_INCREMENT NOT NULL, produit_panier_id INT DEFAULT NULL, user_panier_id INT DEFAULT NULL, prix_totale DOUBLE PRECISION NOT NULL, quantite INT NOT NULL, INDEX IDX_24CC0DF2F79D1F98 (user_panier_id), INDEX IDX_24CC0DF273EACC90 (produit_panier_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('ALTER TABLE panier ADD CONSTRAINT FK_24CC0DF273EACC90 FOREIGN KEY (produit_panier_id) REFERENCES produit (id)');
        $this->addSql('ALTER TABLE panier ADD CONSTRAINT FK_24CC0DF2F79D1F98 FOREIGN KEY (user_panier_id) REFERENCES user (id)');
        $this->addSql('DROP TABLE reclamation');
        $this->addSql('DROP TABLE sponsor');
        $this->addSql('ALTER TABLE avis CHANGE desc_avis desc_avis VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE calendar CHANGE title title VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description description LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE background_color background_color VARCHAR(7) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE border_color border_color VARCHAR(7) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE text_color text_color VARCHAR(7) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_event image_event VARCHAR(400) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE origine origine VARCHAR(6) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE categorie CHANGE nom_categorie nom_categorie VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE commande CHANGE place place VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE payement_method payement_method VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE code_coupon code_coupon VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE comment comment VARCHAR(600) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE street street VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE coupon DROP FOREIGN KEY FK_64BF3F02F620505');
        $this->addSql('DROP INDEX IDX_64BF3F02F620505 ON coupon');
        $this->addSql('ALTER TABLE coupon DROP pub_back_id, CHANGE code_coup code_coup VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE produit DROP stock_prod, CHANGE nom_prod nom_prod VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_prod desc_prod VARCHAR(600) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_prod image_prod VARCHAR(400) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE model_prod model_prod VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE pub_back DROP FOREIGN KEY FK_C4D759155C6F21EA');
        $this->addSql('ALTER TABLE pub_back DROP FOREIGN KEY FK_C4D75915F347EFB');
        $this->addSql('ALTER TABLE pub_back DROP FOREIGN KEY FK_C4D75915FFC00408');
        $this->addSql('DROP INDEX IDX_C4D759155C6F21EA ON pub_back');
        $this->addSql('DROP INDEX IDX_C4D75915F347EFB ON pub_back');
        $this->addSql('DROP INDEX IDX_C4D75915FFC00408 ON pub_back');
        $this->addSql('DROP INDEX IDX_C4D7591512F7FB51 ON pub_back');
        $this->addSql('ALTER TABLE pub_back DROP coupon1_id, DROP produit_id, DROP calender_id, DROP sponsor_id, CHANGE nom_pub nom_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_pub desc_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE prix_pub prix_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE promo_pub promo_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_pub image_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE publication CHANGE titre_publ titre_publ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description_publ description_publ LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_publ image_publ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE sous_categorie CHANGE nom_sous_categ nom_sous_categ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE user DROP gender, CHANGE email email VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE username username VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE password password VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE reset_token reset_token VARCHAR(50) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE roles roles LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci` COMMENT \'(DC2Type:json)\', CHANGE activation_token activation_token VARCHAR(50) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE facebook_id facebook_id VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE facebook_access_token facebook_access_token VARCHAR(255) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`');
    }
}
