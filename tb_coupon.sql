/*
SQLyog Trial v12.2.1 (64 bit)
MySQL - 8.0.18 : Database - online_mall
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `tb_coupon` */

DROP TABLE IF EXISTS `tb_coupon`;

CREATE TABLE `tb_coupon` (
  `coupon_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '优惠券id',
  `coupon_name` varchar(255) NOT NULL COMMENT '优惠券名称',
  `min_amount` double DEFAULT NULL COMMENT '使用的最低金额',
  `coupon_amount` double DEFAULT NULL COMMENT '优惠券金额',
  `user_id` int(11) DEFAULT NULL COMMENT '商家id',
  `category_id` int(11) DEFAULT NULL COMMENT '可优惠商品类别id',
  `product_id` int(11) DEFAULT NULL COMMENT '可优惠商品id',
  `create_time` datetime DEFAULT NULL COMMENT '开始日期',
  `expire_time` datetime DEFAULT NULL COMMENT '截止日期',
  `coupon_is_delete` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tb_coupon` */

insert  into `tb_coupon`(`coupon_id`,`coupon_name`,`min_amount`,`coupon_amount`,`user_id`,`category_id`,`product_id`,`create_time`,`expire_time`,`coupon_is_delete`) values 
(1,'店铺优惠券',199,15,7,3,NULL,'2020-07-01 00:00:00','2020-08-30 00:00:00',3),
(2,'店铺优惠券',300,40,7,3,NULL,'2020-07-01 00:00:00','2020-08-30 00:00:00',3),
(3,'东坡酥优惠券',60,5,7,3,25,'2020-07-01 00:00:00','2020-08-30 00:00:00',3),
(4,'桂花糕优惠券',120,20,7,3,28,'2020-07-01 00:00:00','2020-08-30 00:00:00',3),
(5,'批量删除测试1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,4),
(6,'批量删除测试2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,4),
(7,'批量删除测试3',NULL,NULL,NULL,NULL,NULL,NULL,NULL,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
