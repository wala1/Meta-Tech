<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220215085829 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE commande (id INT AUTO_INCREMENT NOT NULL, place VARCHAR(255) NOT NULL, payement_method VARCHAR(255) NOT NULL, code_coupon VARCHAR(255) DEFAULT NULL, comment VARCHAR(600) DEFAULT NULL, date DATE NOT NULL, phone_number INT DEFAULT NULL, street VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE panier DROP FOREIGN KEY FK_24CC0DF2A76ED395');
        $this->addSql('DROP INDEX UNIQ_24CC0DF2A76ED395 ON panier');
        $this->addSql('ALTER TABLE panier ADD user_panier_id INT DEFAULT NULL, ADD quantite INT NOT NULL, CHANGE user_id produit_panier_id INT DEFAULT NULL');
        $this->addSql('ALTER TABLE panier ADD CONSTRAINT FK_24CC0DF273EACC90 FOREIGN KEY (produit_panier_id) REFERENCES produit (id)');
        $this->addSql('ALTER TABLE panier ADD CONSTRAINT FK_24CC0DF2F79D1F98 FOREIGN KEY (user_panier_id) REFERENCES user (id)');
        $this->addSql('CREATE INDEX IDX_24CC0DF273EACC90 ON panier (produit_panier_id)');
        $this->addSql('CREATE INDEX IDX_24CC0DF2F79D1F98 ON panier (user_panier_id)');
        $this->addSql('ALTER TABLE produit DROP FOREIGN KEY FK_29A5EC27FB72FBF4');
        $this->addSql('DROP INDEX IDX_29A5EC27FB72FBF4 ON produit');
        $this->addSql('ALTER TABLE produit DROP panier_prod_id, CHANGE model_prod model_prod VARCHAR(255) DEFAULT NULL');
        $this->addSql('ALTER TABLE user DROP FOREIGN KEY FK_8D93D649F77D927C');
        $this->addSql('DROP INDEX UNIQ_8D93D649F77D927C ON user');
        $this->addSql('ALTER TABLE user DROP panier_id');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('DROP TABLE commande');
        $this->addSql('ALTER TABLE avis CHANGE desc_avis desc_avis VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE categorie CHANGE nom_categorie nom_categorie VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE coupon CHANGE code_coup code_coup VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE panier DROP FOREIGN KEY FK_24CC0DF273EACC90');
        $this->addSql('ALTER TABLE panier DROP FOREIGN KEY FK_24CC0DF2F79D1F98');
        $this->addSql('DROP INDEX IDX_24CC0DF273EACC90 ON panier');
        $this->addSql('DROP INDEX IDX_24CC0DF2F79D1F98 ON panier');
        $this->addSql('ALTER TABLE panier ADD user_id INT DEFAULT NULL, DROP produit_panier_id, DROP user_panier_id, DROP quantite');
        $this->addSql('ALTER TABLE panier ADD CONSTRAINT FK_24CC0DF2A76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('CREATE UNIQUE INDEX UNIQ_24CC0DF2A76ED395 ON panier (user_id)');
        $this->addSql('ALTER TABLE produit ADD panier_prod_id INT DEFAULT NULL, CHANGE nom_prod nom_prod VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_prod desc_prod VARCHAR(600) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_prod image_prod VARCHAR(400) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE model_prod model_prod VARCHAR(1500) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE produit ADD CONSTRAINT FK_29A5EC27FB72FBF4 FOREIGN KEY (panier_prod_id) REFERENCES panier (id)');
        $this->addSql('CREATE INDEX IDX_29A5EC27FB72FBF4 ON produit (panier_prod_id)');
        $this->addSql('ALTER TABLE pub_back CHANGE nom_pub nom_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_pub desc_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE prix_pub prix_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_pub image_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE publication CHANGE titre_publ titre_publ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE description_publ description_publ LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_publ image_publ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE sous_categorie CHANGE nom_sous_categ nom_sous_categ VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE user ADD panier_id INT DEFAULT NULL, CHANGE email email VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE username username VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE password password VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE reset_token reset_token VARCHAR(50) DEFAULT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE roles roles LONGTEXT NOT NULL COLLATE `utf8mb4_unicode_ci` COMMENT \'(DC2Type:json)\'');
        $this->addSql('ALTER TABLE user ADD CONSTRAINT FK_8D93D649F77D927C FOREIGN KEY (panier_id) REFERENCES panier (id)');
        $this->addSql('CREATE UNIQUE INDEX UNIQ_8D93D649F77D927C ON user (panier_id)');
    }
}
