-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 06 mai 2022 à 19:22
-- Version du serveur :  10.4.14-MariaDB
-- Version de PHP : 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `metatech`
--

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `categorie_prod_id` int(11) DEFAULT NULL,
  `nom_prod` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `desc_prod` varchar(600) COLLATE utf8mb4_unicode_ci NOT NULL,
  `image_prod` varchar(400) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prix_prod` double NOT NULL,
  `promo_prod` double NOT NULL,
  `rating_prod` int(11) DEFAULT NULL,
  `model_prod` varchar(1500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sous_categ_prod_id` int(11) NOT NULL,
  `stock_prod` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `categorie_prod_id`, `nom_prod`, `desc_prod`, `image_prod`, `prix_prod`, `promo_prod`, `rating_prod`, `model_prod`, `sous_categ_prod_id`, `stock_prod`) VALUES
(1, 1, 'Iphone 13 Pro Max', 'L\'iPhone 13, annoncé le 14 septembre 2021, est le modèle central de la 15e itération du smartphone d\'Apple. Il est équipé d\'un écran OLED de 6,1 pouces 60 Hz, d\'un SoC Apple A15 Bionic compatible 5G (NR & Sub-6) et d\'un double capteur photo de 12+12 mégapixels (grand-angle et ultra grand-angle) avec OIS.', 'https://selectshop.tn/18375-medium_default/smartphone-apple-iphone-13-pro-max-128-go-bleu-alpin.jpg', 3300, 3200, 0, '<iframe style=\"min-height:450px;min-width:300px;width:100%;height:100%;\" src=\"https://sketchfab.com/models/0a8de332cc1c4184800419015799c95c/embed?autostart=1&amp;autospin=-0.1&amp;autostart=1&amp;camera=0&amp;double_click=0&amp;internal=1&amp;max_texture_<iframe style=\"min-height:450px;min-width:300px;width:100%;height:100%;\" src=\"https://sketchfab.com/models/0a8de332cc1c4184800419015799c95c/embed?autostart=1&amp;autospin=-0.1&amp;autostart=1&amp;camera=0&amp;double_click=0&amp;internal=1&amp;max_texture_size=1024&amp;preload=1&amp;scrollwheel=0&amp;sound_enable=0&amp;transparent=1&amp;ui_animations=0&amp;ui_annotations=0&amp;ui_ar=1&amp;ui_ar_help=0&amp;ui_color=white&amp;ui_fadeout=0&amp;ui_fullscreen=0&amp;ui_help=0&amp;ui_infos=0&amp;ui_inspector=0&amp;ui_settings=0&amp;ui_stop=0&amp;ui_theatre=0&amp;ui_theme=dark&amp;ui_vr=0&amp;ui_watermark=0\" frameborder=\"0\"    ></iframe>', 2, 10),
(6, 2, 'PC ROG STRIX', 'PC GAMER', 'https://www.mencorner.com/media/produits/ROG_Strix_SCAR_15_G533_5.jpg', 3500, 0, 0, '<iframe style=\"min-height:450px;min-width:300px;width:100%;height:100%;\" src=\"https://sketchfab.com/models/72073859431343f78584ff479552440d/embed?autostart=1&amp;autospin=-0.1&amp;autostart=1&amp;camera=0&amp;double_click=0&amp;internal=1&amp;max_texture_size=1024&amp;preload=1&amp;scrollwheel=0&amp;sound_enable=0&amp;transparent=1&amp;ui_animations=0&amp;ui_annotations=0&amp;ui_ar=1&amp;ui_ar_help=0&amp;ui_color=white&amp;ui_fadeout=0&amp;ui_fullscreen=0&amp;ui_help=0&amp;ui_infos=0&amp;ui_inspector=0&amp;ui_settings=0&amp;ui_stop=0&amp;ui_theatre=0&amp;ui_theme=dark&amp;ui_vr=0&amp;ui_watermark=0\" frameborder=\"0\"    ></iframe>', 1, 14),
(12, 7, 'Apple Watch Gold Case Midnight Blue', 'A very good watch.', 'https://preview.free3d.com/img/2019/04/2279606290617468695/06r99vjq-900.jpg', 1200, 1150, 1, '<iframe style=\"min-height:450px;min-width:300px;width:100%;height:100%;\" src=\"https://sketchfab.com/models/b1b0f75ae2aa41ff9ec6250efed2954b/embed?autostart=1&amp;autospin=-0.1&amp;autostart=1&amp;camera=0&amp;double_click=0&amp;internal=1&amp;max_texture_size=1024&amp;preload=1&amp;scrollwheel=0&amp;sound_enable=0&amp;transparent=1&amp;ui_animations=0&amp;ui_annotations=0&amp;ui_ar=1&amp;ui_ar_help=0&amp;ui_color=white&amp;ui_fadeout=0&amp;ui_fullscreen=0&amp;ui_help=0&amp;ui_infos=0&amp;ui_inspector=0&amp;ui_settings=0&amp;ui_stop=0&amp;ui_theatre=0&amp;ui_theme=dark&amp;ui_vr=0&amp;ui_watermark=0\" frameborder=\"0\"    ></iframe>', 2, 5),
(13, 7, 'MSI Headset', 'Gaming Headset', 'https://media.ldlc.com/r1600/ld/products/00/05/47/59/LD0005475952_2.jpg', 240, 220, 1, '<iframe style=\"min-height:450px;min-width:300px;width:100%;height:100%;\" src=\"https://sketchfab.com/models/1f6ed065522b4531b1fc840b49d5d230/embed?autostart=1&amp;autospin=-0.1&amp;autostart=1&amp;camera=0&amp;double_click=0&amp;internal=1&amp;max_texture_size=1024&amp;preload=1&amp;scrollwheel=0&amp;sound_enable=0&amp;transparent=1&amp;ui_animations=0&amp;ui_annotations=0&amp;ui_ar=1&amp;ui_ar_help=0&amp;ui_color=white&amp;ui_fadeout=0&amp;ui_fullscreen=0&amp;ui_help=0&amp;ui_infos=0&amp;ui_inspector=0&amp;ui_settings=0&amp;ui_stop=0&amp;ui_theatre=0&amp;ui_theme=dark&amp;ui_vr=0&amp;ui_watermark=0\" frameborder=\"0\"    ></iframe>', 4, 8),
(14, 7, 'MSI Clutch GM50', 'Gaming Mouse MSI.', 'https://m.media-amazon.com/images/I/51EvccZqriL.jpg', 75, 72, 1, '<iframe style=\"min-height:450px;min-width:300px;width:100%;height:100%;\" src=\"https://sketchfab.com/models/8ebe54b1053543b4a14a7d9079e4d225/embed?autostart=1&amp;autospin=-0.1&amp;autostart=1&amp;camera=0&amp;double_click=0&amp;internal=1&amp;max_texture_size=1024&amp;preload=1&amp;scrollwheel=0&amp;sound_enable=0&amp;transparent=1&amp;ui_animations=0&amp;ui_annotations=0&amp;ui_ar=1&amp;ui_ar_help=0&amp;ui_color=white&amp;ui_fadeout=0&amp;ui_fullscreen=0&amp;ui_help=0&amp;ui_infos=0&amp;ui_inspector=0&amp;ui_settings=0&amp;ui_stop=0&amp;ui_theatre=0&amp;ui_theme=dark&amp;ui_vr=0&amp;ui_watermark=0\" frameborder=\"0\"    ></iframe>', 4, 15),
(15, 1, 'Samsung Galaxy Note8', 'Samsung Galaxy Note8', 'https://dari-shop.tn/8294/4811.jpg', 1800, 1600, 1, '<iframe style=\"min-height:450px;min-width:300px;width:100%;height:100%;\" src=\"https://sketchfab.com/models/4d3ad58f32a941d5baeb8e3b3041a705/embed?autostart=1&amp;autospin=-0.1&amp;autostart=1&amp;camera=0&amp;double_click=0&amp;internal=1&amp;max_texture_size=1024&amp;preload=1&amp;scrollwheel=0&amp;sound_enable=0&amp;transparent=1&amp;ui_animations=0&amp;ui_annotations=0&amp;ui_ar=1&amp;ui_ar_help=0&amp;ui_color=white&amp;ui_fadeout=0&amp;ui_fullscreen=0&amp;ui_help=0&amp;ui_infos=0&amp;ui_inspector=0&amp;ui_settings=0&amp;ui_stop=0&amp;ui_theatre=0&amp;ui_theme=dark&amp;ui_vr=0&amp;ui_watermark=0\" frameborder=\"0\"    ></iframe>', 3, 2),
(16, 7, 'Apple iMac', 'Apple iMac Screen', 'https://mk-media.mytek.tn/media/catalog/product/cache/8be3f98b14227a82112b46963246dfe1/i/m/imac_1.jpg', 960, 870, 1, '<iframe style=\"min-height:450px;min-width:300px;width:100%;height:100%;\" src=\"https://sketchfab.com/models/45a93080f6604407bc74a987a9cd382a/embed?autostart=1&amp;autospin=-0.1&amp;autostart=1&amp;camera=0&amp;double_click=0&amp;internal=1&amp;max_texture_size=1024&amp;preload=1&amp;scrollwheel=0&amp;sound_enable=0&amp;transparent=1&amp;ui_animations=0&amp;ui_annotations=0&amp;ui_ar=1&amp;ui_ar_help=0&amp;ui_color=white&amp;ui_fadeout=0&amp;ui_fullscreen=0&amp;ui_help=0&amp;ui_infos=0&amp;ui_inspector=0&amp;ui_settings=0&amp;ui_stop=0&amp;ui_theatre=0&amp;ui_theme=dark&amp;ui_vr=0&amp;ui_watermark=0\" frameborder=\"0\"    ></iframe>', 2, 3),
(17, 7, 'Gaming Chair', 'Gaming Chair', 'https://i5.walmartimages.com/asr/2abde8e5-015a-48f0-b460-2b1eb435db45.08cb313fe0758c8091a74b40a02663e7.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF', 530, 500, 1, '<iframe style=\"min-height:450px;min-width:300px;width:100%;height:100%;\" src=\"https://sketchfab.com/models/2a40f5afe72c41b4b85b67cb841c1a67/embed?autostart=1&amp;autospin=-0.1&amp;autostart=1&amp;camera=0&amp;double_click=0&amp;internal=1&amp;max_texture_size=1024&amp;preload=1&amp;scrollwheel=0&amp;sound_enable=0&amp;transparent=1&amp;ui_animations=0&amp;ui_annotations=0&amp;ui_ar=1&amp;ui_ar_help=0&amp;ui_color=white&amp;ui_fadeout=0&amp;ui_fullscreen=0&amp;ui_help=0&amp;ui_infos=0&amp;ui_inspector=0&amp;ui_settings=0&amp;ui_stop=0&amp;ui_theatre=0&amp;ui_theme=dark&amp;ui_vr=0&amp;ui_watermark=0\" frameborder=\"0\"    ></iframe>', 4, 14),
(18, 2, 'ROG Setup', 'Setup gaming.', 'https://media.karousell.com/media/photos/products/2020/5/25/asus_rog_gaming_setup_1590386889_cc23c203.jpg', 5600, 5300, 1, NULL, 1, 5),
(21, 1, 'Iphone XR', 'Iphone XR', 'https://fr.shopping.rakuten.com/photo/1943867137_L.jpg', 1500, 0, 1, NULL, 2, 4),
(22, 2, 'PC PORTABLE HP', 'PC Portable HP', 'https://www.tunisianet.com.tn/161016-large/pc-portable-hp-pavilion-gaming-15-dk0019nk-i5-9e-gen-16-go-sim-orange-offerte-60-go.jpg', 2500, 2300, 1, NULL, 9, 5),
(23, 2, 'PC Fix MSI', 'MSI - MEG-Aegis-Ti5-10TD-007EU - Noir', 'https://www.rueducommerce.fr/medias/2fcaddeff6823b8ab3ccae0c9ad33aa9/p_640x640/aegisti5-3.jpg', 1600, 1580, 1, NULL, 4, 2),
(24, 7, 'Ecran HP Gamer', 'Ecran', 'https://www.rueducommerce.fr/medias/c696fd1092d13d9ea99a5c05c195cb96/p_1000x1000/0192545213608.jpg', 1000, 940, 1, NULL, 9, 4),
(26, 1, 'prodZAA', 'GreatIphone', 'https://images.frandroid.com/wp-content/uploads/2019/04/iphone-xr-2018.png', 60000, 300, 0, NULL, 1, 140),
(27, 12, 'Iphone Exclusive Golden Case 24 Karat', 'Very Rare gold case.', 'https://leronza.com/wp-content/uploads/2021/09/24K-1-1024x1024.png', 0.18, 0.16, 1, NULL, 2, 2),
(28, 12, 'Golden Rare Airpods 24 Karat', 'Very Rare .', 'https://i.pinimg.com/736x/a1/7b/75/a17b75c7489d0427fd1b7ad9d0ecc040.jpg', 0.3, 0.28, 1, NULL, 2, 3),
(29, 12, 'XBOX Gold Controller', 'rare product.', 'https://heavy.com/wp-content/uploads/2014/11/xbox-one-controller-mods.jpg', 0.3, 0.28, 1, NULL, 3, 3),
(42, 7, 'test Iphone', 'ddddddddd', 'https://selectshop.tn/18375-medium_default/smartphone-apple-iphone-13-pro-max-128-go-bleu-alpin.jpg', 1400, 1100, 0, NULL, 9, 10);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_29A5EC275E4B91D7` (`categorie_prod_id`),
  ADD KEY `IDX_29A5EC276348D6B3` (`sous_categ_prod_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `FK_29A5EC275E4B91D7` FOREIGN KEY (`categorie_prod_id`) REFERENCES `categorie` (`id`),
  ADD CONSTRAINT `FK_29A5EC276348D6B3` FOREIGN KEY (`sous_categ_prod_id`) REFERENCES `sous_categorie` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
