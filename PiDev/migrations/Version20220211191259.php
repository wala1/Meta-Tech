<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20220211191259 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE pub_back ADD promo_pub VARCHAR(255) NOT NULL, ADD image_pub VARCHAR(255) NOT NULL');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE categorie CHANGE nom_categorie nom_categorie VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE produit CHANGE nom_prod nom_prod VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_prod desc_prod VARCHAR(600) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_prod image_prod VARCHAR(400) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE pub_back DROP promo_pub, DROP image_pub, CHANGE nom nom VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_pub desc_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE prix_pub prix_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
        $this->addSql('ALTER TABLE publicity CHANGE nom_pub nom_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE desc_pub desc_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE prix_pub prix_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE image_pub image_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`, CHANGE promo_pub promo_pub VARCHAR(255) NOT NULL COLLATE `utf8mb4_unicode_ci`');
    }
}
