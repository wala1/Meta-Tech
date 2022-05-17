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
-- Structure de la table `avis`
--

CREATE TABLE `avis` (
  `id` int(11) NOT NULL,
  `id_prod_id` int(11) NOT NULL,
  `id_user_id` int(11) NOT NULL,
  `rating_avis` int(11) NOT NULL,
  `desc_avis` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `time_avis` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `avis`
--

INSERT INTO `avis` (`id`, `id_prod_id`, `id_user_id`, `rating_avis`, `desc_avis`, `time_avis`) VALUES
(13, 6, 103, 5, 'Well done!', '0000-00-00 00:00:00'),
(16, 6, 103, 4, 'GREAT PRODUCT', '2022-02-18 21:29:45'),
(20, 1, 106, 2, 'niceeeee', '2022-02-21 15:00:10'),
(21, 1, 107, 4, 'Good Pricing. I like it..', '2022-02-21 16:09:03'),
(23, 13, 107, 5, 'High Quality !', '2022-02-21 17:18:52'),
(24, 16, 107, 5, 'Nice Product', '2022-02-21 17:24:54'),
(25, 15, 103, 5, 'Great product. Nice Design.', '2022-02-21 22:56:45'),
(26, 15, 107, 4, 'Very good', '2022-02-21 22:58:16'),
(27, 17, 103, 5, 'Nice product !', '2022-02-22 09:38:32'),
(37, 12, 103, 4, 'Good Product .                        ', '2022-02-24 18:24:34'),
(41, 1, 108, 5, 'nice !gg\r\n                        ', '2022-02-25 21:17:53'),
(42, 18, 108, 2, 'great !', '2022-02-25 21:25:07'),
(43, 13, 109, 2, 'bad product', '2022-02-26 22:07:01'),
(44, 1, 109, 5, 'nice product', '2022-02-26 22:07:31'),
(45, 6, 109, 4, 'nice', '2022-02-26 22:08:13'),
(46, 13, 109, 5, 'huu', '2022-02-26 22:10:30'),
(48, 1, 103, 3, 'this product ***** ********\r\n                        ', '2022-02-28 20:19:53'),
(49, 14, 103, 5, 'good\r\n                        ', '2022-03-01 10:31:53'),
(50, 13, 110, 5, 'great', '2022-03-01 10:39:31'),
(51, 6, 110, 4, 'SSS', '2022-03-01 10:45:07'),
(53, 1, 103, 3, 'great prod!', '2022-03-21 12:25:02'),
(54, 12, 111, 2, 'nice', '2022-03-01 11:17:20'),
(55, 12, 111, 5, 'nice !', '2022-03-01 11:17:32'),
(56, 15, 111, 4, 'good', '2022-03-01 11:18:06'),
(57, 6, 103, 5, 'hey ********', '2022-03-03 21:47:21'),
(58, 1, 112, 5, 'cool', '2022-03-05 09:58:54'),
(59, 24, 112, 5, 'nice', '2022-03-05 10:01:00'),
(60, 16, 112, 4, 'good !', '2022-03-05 10:01:49'),
(63, 29, 88, 1, 'niceProdGreat', '2022-03-20 12:57:48'),
(64, 29, 88, 1, 'niceProdGreat', '2022-03-20 12:57:57'),
(66, 6, 103, 2, 'niced', '2022-03-20 19:51:23'),
(68, 13, 103, 2, 'nice headset!', '2022-03-20 20:33:32'),
(69, 1, 103, 3, 'hehehe ****', '2022-03-25 19:45:16'),
(70, 1, 103, 4, 'Good Product !', '2022-03-31 23:16:06'),
(71, 17, 103, 2, '**** Product !', '2022-03-31 23:16:59'),
(73, 14, 88, 5, 'gg', '2022-04-20 19:18:57'),
(74, 15, 88, 2, 'average', '2022-04-20 22:23:21'),
(76, 17, 88, 1, 'cool', '2022-04-20 22:30:24'),
(77, 12, 88, 4, 'tested', '2022-04-23 22:44:23'),
(78, 12, 88, 5, 'cool', '2022-04-23 22:49:06'),
(79, 16, 88, 3, 'zz', '2022-04-23 22:49:47'),
(80, 16, 88, 2, 'GAE', '2022-04-23 22:53:36');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `avis`
--
ALTER TABLE `avis`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_8F91ABF0DF559605` (`id_prod_id`),
  ADD KEY `IDX_8F91ABF079F37AE5` (`id_user_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `avis`
--
ALTER TABLE `avis`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=83;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `avis`
--
ALTER TABLE `avis`
  ADD CONSTRAINT `FK_8F91ABF079F37AE5` FOREIGN KEY (`id_user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_8F91ABF0DF559605` FOREIGN KEY (`id_prod_id`) REFERENCES `produit` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
