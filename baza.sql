/*
SQLyog Community v13.3.1 (64 bit)
MySQL - 8.0.44 : Database - njt_projekat
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`njt_projekat` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `njt_projekat`;

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `street` varchar(200) NOT NULL,
  `number` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `postal_code` bigint NOT NULL,
  `client_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `client_address` (`client_id`),
  CONSTRAINT `client_address` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `address` */

insert  into `address`(`id`,`street`,`number`,`city`,`postal_code`,`client_id`) values 
(4,'u1','1','beograd',1,2),
(6,'u2','2','Novi Sad',22,4);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `category` */

insert  into `category`(`id`,`name`,`description`) values 
(1,'Proteini','Protein u prahu i suplementi za izgradnju misica'),
(2,'Vitamini i minerali','Podrska imunitetu i opstem zdravlju'),
(3,'Kreatin','Suplementi za snagu i eksplozivnost'),
(4,'Aminokiseline','BCAA, EAA i pojedinacne aminokiseline'),
(5,'Pre-workout','Energija i fokus pre treninga'),
(6,'Zdrav snek','Proteinski barovi i zdrave grickalice');

/*Table structure for table `client` */

DROP TABLE IF EXISTS `client`;

CREATE TABLE `client` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `phone` varchar(100) NOT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `client` */

insert  into `client`(`id`,`username`,`password`,`firstname`,`lastname`,`phone`,`birthday`) values 
(2,'sara','sara','sara','sara','123','2026-07-10'),
(3,'pera','pera','pera','pera','123','2026-07-07'),
(4,'ana','ana','ana','ana','123','2026-07-01');

/*Table structure for table `order_item` */

DROP TABLE IF EXISTS `order_item`;

CREATE TABLE `order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `price_at_purchase` double NOT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orderitem_order` (`order_id`),
  KEY `orderitem_product` (`product_id`),
  CONSTRAINT `orderitem_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `orderitem_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `order_item` */

insert  into `order_item`(`id`,`quantity`,`price_at_purchase`,`order_id`,`product_id`) values 
(5,1,2711.5,5,1),
(6,1,3527.5,6,3),
(7,1,3190,7,2);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ordered` date NOT NULL,
  `arrival` date DEFAULT NULL,
  `client_id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orders_client` (`client_id`),
  KEY `orders_user` (`user_id`),
  CONSTRAINT `orders_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  CONSTRAINT `orders_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `orders` */

insert  into `orders`(`id`,`ordered`,`arrival`,`client_id`,`user_id`) values 
(5,'2026-07-10','2026-07-10',2,NULL),
(6,'2026-07-10','2026-07-10',2,NULL),
(7,'2026-07-11','2026-07-11',2,NULL);

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(200) NOT NULL,
  `price` double NOT NULL,
  `stock` int NOT NULL,
  `form` varchar(20) NOT NULL,
  `image_url` varchar(500) DEFAULT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_category` (`category_id`),
  CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `product` */

insert  into `product`(`id`,`name`,`description`,`price`,`stock`,`form`,`image_url`,`category_id`) values 
(1,'Whey Protein Cokolada 750g','Protein surutke sa ukusom cokolade, 25 doza',3190,39,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/cokoladni_protein.png',1),
(2,'Whey Protein Vanila 750g','Protein surutke sa ukusom vanile, 25 doza',3190,32,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/vanila_protein.png',1),
(3,'Whey Isolate Cokolada 750g','Izolat proteina surutke, nizak sadrzaj masti i secera',4150,19,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/isolate_cokoladni_protein.png',1),
(4,'Whey Protein Jagoda 750g','Protein surutke sa ukusom jagode, 25 doza',3190,25,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/jagoda_protein.png',1),
(5,'Vegan Protein 750g','Protein na bazi graska i pirinca, 100% biljno',2590,18,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/vegan_protein.png',1),
(6,'Natural Protein 750g','Protein surutke sa neutralnim ukusom, 25 doza',3190,30,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/natural_protein.png',1),
(7,'Calcium + Magnesium + Zinc + D3','Podrska imunitetu i zdravlju kostiju',1200,100,'CAPSULE','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/calcium.png',2),
(8,'Collagen Narandza 300g','Pruža regeneraciju vezivnog tkiva, napravljen od goveđeg kolagena.',2470,90,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/collagen_narandza.png',2),
(9,'Collagen Visnja 300g','Pruža regeneraciju vezivnog tkiva, napravljen od goveđeg kolagena',2470,60,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/collagen_visnja.png',2),
(10,'Magnezijum 100 kapsula','Podrska nervnom i misicnom sistemu',1290,70,'CAPSULE','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/magnesium.png',2),
(11,'Omega3','Bogat dragocenim biljnim uljem',1590,80,'CAPSULE','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/omega3.png',2),
(12,'C-vitamin plus 100tabl','Vitamin C, Vitamin D3 i Cink',1390,50,'TABLET','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/c_plus.png',2),
(13,'Kreatin Monohidrat 300g','Cista mikronizovana formula za snagu',2350,45,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/kreatin300.png',3),
(14,'Kreatin Monohidrat 500g','Vece pakovanje, ista cista formula',3190,30,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/kreatin500.png',3),
(17,'Beta Alanine 100g','Alat za pomeranje granica izdržljivosti',990,35,'CAPSULE','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/beta-alanine.png',4),
(18,'Citrulline 100g','Brutalan pump i čista izdržljivost',990,25,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/citrulin.png',4),
(19,'L-Arginine 100g(50 doza)','Prirodna podrška tvojoj cirkulaciji i srcu',990,30,'TABLET','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/l-arginine.png',4),
(22,'Pre-Workout Tropsko Voce 300g','Energija, fokus i pumpa pre treninga',3490,30,'POWDER','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/preworkout.png',5),
(27,'Barbell Cookies & Caramel 60g','25g proteina, nizak secer',350,100,'BAR','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/cookies&caramel.jpeg',6),
(28,'Barbell Chocolate Dough 60g','Hrskavi bar bogat proteinima',350,90,'BAR','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/chocolate_dough.jpeg',6),
(29,'Protein Gumms','Zdrave gumene bombone',340,40,'GUMMY','https://raw.githubusercontent.com/saranovkov1c/supplement-images/main/images/gummies.jpg',6);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `role` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`firstname`,`lastname`,`role`) values 
(1,'admin','admin123','Admin','Administratorovic','ADMIN');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
