<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220307155433 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE avis (id INT AUTO_INCREMENT NOT NULL, id_prod_id INT NOT NULL, id_user_id INT NOT NULL, rating_avis INT NOT NULL, desc_avis VARCHAR(255) NOT NULL, time_avis DATETIME NOT NULL, INDEX IDX_8F91ABF0DF559605 (id_prod_id), INDEX IDX_8F91ABF079F37AE5 (id_user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE calendar (id INT AUTO_INCREMENT NOT NULL, title VARCHAR(255) NOT NULL, start DATETIME NOT NULL, end DATETIME NOT NULL, description LONGTEXT NOT NULL, all_day TINYINT(1) NOT NULL, background_color VARCHAR(7) NOT NULL, border_color VARCHAR(7) NOT NULL, text_color VARCHAR(7) NOT NULL, image_event VARCHAR(400) NOT NULL, origine VARCHAR(6) NOT NULL, etat INT NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE panier (id INT AUTO_INCREMENT NOT NULL, user_panier_id INT NOT NULL, produit_panier_id INT NOT NULL, quantite INT NOT NULL, INDEX IDX_24CC0DF2F79D1F98 (user_panier_id), INDEX IDX_24CC0DF273EACC90 (produit_panier_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE participants (id INT AUTO_INCREMENT NOT NULL, event_id INT DEFAULT NULL, user_id INT DEFAULT NULL, INDEX IDX_7169709271F7E88B (event_id), INDEX IDX_71697092A76ED395 (user_id), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE avis ADD CONSTRAINT FK_8F91ABF0DF559605 FOREIGN KEY (id_prod_id) REFERENCES produit (id)');
        $this->addSql('ALTER TABLE avis ADD CONSTRAINT FK_8F91ABF079F37AE5 FOREIGN KEY (id_user_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE panier ADD CONSTRAINT FK_24CC0DF2F79D1F98 FOREIGN KEY (user_panier_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE panier ADD CONSTRAINT FK_24CC0DF273EACC90 FOREIGN KEY (produit_panier_id) REFERENCES produit (id)');
        $this->addSql('ALTER TABLE participants ADD CONSTRAINT FK_7169709271F7E88B FOREIGN KEY (event_id) REFERENCES calendar (id)');
        $this->addSql('ALTER TABLE participants ADD CONSTRAINT FK_71697092A76ED395 FOREIGN KEY (user_id) REFERENCES user (id)');
        $this->addSql('ALTER TABLE pub_back DROP FOREIGN KEY FK_C4D759155C6F21EA');
        $this->addSql('ALTER TABLE pub_back DROP FOREIGN KEY FK_C4D75915F347EFB');
        $this->addSql('ALTER TABLE pub_back ADD promo_pub VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE pub_back ADD CONSTRAINT FK_C4D75915FFC00408 FOREIGN KEY (calender_id) REFERENCES calendar (id)');
        $this->addSql('ALTER TABLE pub_back ADD CONSTRAINT FK_C4D7591512F7FB51 FOREIGN KEY (sponsor_id) REFERENCES sponsor (id)');
        $this->addSql('CREATE INDEX IDX_C4D75915FFC00408 ON pub_back (calender_id)');
        $this->addSql('CREATE INDEX IDX_C4D7591512F7FB51 ON pub_back (sponsor_id)');
        $this->addSql('DROP INDEX fk_c4d759155c6f21ea ON pub_back');
        $this->addSql('CREATE INDEX IDX_C4D759155C6F21EA ON pub_back (coupon1_id)');
        $this->addSql('DROP INDEX fk_c4d75915f347efb ON pub_back');
        $this->addSql('CREATE INDEX IDX_C4D75915F347EFB ON pub_back (produit_id)');
        $this->addSql('ALTER TABLE pub_back ADD CONSTRAINT FK_C4D759155C6F21EA FOREIGN KEY (coupon1_id) REFERENCES coupon (id)');
        $this->addSql('ALTER TABLE pub_back ADD CONSTRAINT FK_C4D75915F347EFB FOREIGN KEY (produit_id) REFERENCES produit (id)');
        $this->addSql('ALTER TABLE user ADD calendar_id INT DEFAULT NULL, ADD etat INT NOT NULL, ADD activation_token VARCHAR(50) DEFAULT NULL, ADD facebook_id VARCHAR(255) DEFAULT NULL, ADD facebook_access_token VARCHAR(255) DEFAULT NULL, ADD gender VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE user ADD CONSTRAINT FK_8D93D649A40A2C8 FOREIGN KEY (calendar_id) REFERENCES calendar (id)');
        $this->addSql('CREATE INDEX IDX_8D93D649A40A2C8 ON user (calendar_id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE participants DROP FOREIGN KEY FK_7169709271F7E88B');
        $this->addSql('ALTER TABLE pub_back DROP FOREIGN KEY FK_C4D75915FFC00408');
        $this->addSql('ALTER TABLE user DROP FOREIGN KEY FK_8D93D649A40A2C8');
        $this->addSql('DROP TABLE avis');
        $this->addSql('DROP TABLE calendar');
        $this->addSql('DROP TABLE panier');
        $this->addSql('DROP TABLE participants');
        $this->addSql('ALTER TABLE pub_back DROP FOREIGN KEY FK_C4D7591512F7FB51');
        $this->addSql('DROP INDEX IDX_C4D75915FFC00408 ON pub_back');
        $this->addSql('DROP INDEX IDX_C4D7591512F7FB51 ON pub_back');
        $this->addSql('ALTER TABLE pub_back DROP FOREIGN KEY FK_C4D759155C6F21EA');
        $this->addSql('ALTER TABLE pub_back DROP FOREIGN KEY FK_C4D75915F347EFB');
        $this->addSql('ALTER TABLE pub_back DROP promo_pub');
        $this->addSql('DROP INDEX idx_c4d75915f347efb ON pub_back');
        $this->addSql('CREATE INDEX FK_C4D75915F347EFB ON pub_back (produit_id)');
        $this->addSql('DROP INDEX idx_c4d759155c6f21ea ON pub_back');
        $this->addSql('CREATE INDEX FK_C4D759155C6F21EA ON pub_back (coupon1_id)');
        $this->addSql('ALTER TABLE pub_back ADD CONSTRAINT FK_C4D759155C6F21EA FOREIGN KEY (coupon1_id) REFERENCES coupon (id)');
        $this->addSql('ALTER TABLE pub_back ADD CONSTRAINT FK_C4D75915F347EFB FOREIGN KEY (produit_id) REFERENCES produit (id)');
        $this->addSql('DROP INDEX IDX_8D93D649A40A2C8 ON user');
        $this->addSql('ALTER TABLE user DROP calendar_id, DROP etat, DROP activation_token, DROP facebook_id, DROP facebook_access_token, DROP gender');
    }
}
