/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/12 18:48:26                           */
/*==============================================================*/


drop table if exists order_info;

drop table if exists tb_cart;

drop table if exists tb_category;

drop table if exists tb_comment;

drop table if exists tb_coupon;

drop table if exists tb_order;

drop table if exists tb_output_order;

drop table if exists tb_product;

drop table if exists tb_product_manage;

drop table if exists tb_star;

drop table if exists tb_system_log;

drop table if exists tb_user;

/*==============================================================*/
/* Table: order_info                                            */
/*==============================================================*/
create table order_info
(
   info_id              int not null auto_increment,
   order_id             int,
   product_id           int,
   primary key (info_id)
);

/*==============================================================*/
/* Table: tb_cart                                               */
/*==============================================================*/
create table tb_cart
(
   cart_id              int not null auto_increment,
   user_id              int,
   is_delete            tinyint not null,
   product_id           int,
   primary key (cart_id)
);

/*==============================================================*/
/* Table: tb_category                                           */
/*==============================================================*/
create table tb_category
(
   category_id          int not null auto_increment,
   name                 varchar(50),
   is_delete            tinyint,
   primary key (category_id)
);

/*==============================================================*/
/* Table: tb_comment                                            */
/*==============================================================*/
create table tb_comment
(
   comment_id           int not null auto_increment,
   context              varchar(500),
   score                int,
   comment_time         date,
   is_delete            tinyint,
   user_id              int,
   product_id           int,
   primary key (comment_id)
);

/*==============================================================*/
/* Table: tb_coupon                                             */
/*==============================================================*/
create table tb_coupon
(
   coupon_id            integer not null auto_increment,
   coupon_name          varchar(255) not null,
   coupon_info          varchar(255),
   coupon_is_used       tinyint,
   coupon_is_delete     tinyint,
   coupon_keep_field_   varchar(255),
   category_id          int,
   product_id           int,
   primary key (coupon_id)
);

/*==============================================================*/
/* Table: tb_order                                              */
/*==============================================================*/
create table tb_order
(
   order_id             int not null auto_increment,
   user_id              int,
   order_number         varchar(30) not null,
   create_time          date,
   output_time          date,
   pay_money            double not null,
   pay_state            tinyint not null,
   is_delete            tinyint not null,
   address              varchar(50),
   is_deliver           tinyint,
   primary key (order_id)
);

/*==============================================================*/
/* Table: tb_output_order                                       */
/*==============================================================*/
create table tb_output_order
(
   out_id               integer not null auto_increment,
   pm_id                integer,
   out_number           int,
   out_date             date,
   out_status           int,
   out_is_delete        tinyint,
   out_keep_field       varchar(255),
   product_id           int,
   primary key (out_id)
);

/*==============================================================*/
/* Table: tb_product                                            */
/*==============================================================*/
create table tb_product
(
   product_id           int not null auto_increment,
   category_id          int,
   pro_no               varchar(32),
   name                 varchar(50),
   price                float,
   pimage               longblob,
   context              varchar(500),
   pkey                 varchar(50),
   status               tinyint,
   add_time             date,
   count                int,
   is_delete            tinyint,
   pm_id                int,
   primary key (product_id)
);

/*==============================================================*/
/* Table: tb_product_manage                                     */
/*==============================================================*/
create table tb_product_manage
(
   pm_id                integer not null auto_increment,
   pm_is_delete         tinyint,
   user_id              int,
   primary key (pm_id)
);

/*==============================================================*/
/* Table: tb_star                                               */
/*==============================================================*/
create table tb_star
(
   star_id              integer not null auto_increment,
   user_id              int,
   star_is_delete       tinyint,
   star_keep_field      varchar(255),
   product_id           int,
   primary key (star_id)
);

/*==============================================================*/
/* Table: tb_system_log                                         */
/*==============================================================*/
create table tb_system_log
(
   log_id               int not null auto_increment,
   user_id              int,
   create_time          date,
   operation            varchar(50),
   level                int,
   primary key (log_id)
);

/*==============================================================*/
/* Table: tb_user                                               */
/*==============================================================*/
create table tb_user
(
   user_id              int not null auto_increment,
   user_name            varchar(50) not null,
   password             varchar(20) not null,
   user_role            int not null,
   sex                  int,
   phone                varchar(30) not null,
   email                varchar(50),
   pay_password         varchar(6) not null,
   register_time        date,
   is_delete            tinyint not null,
   primary key (user_id)
);

alter table order_info add constraint FK_orderItem foreign key (order_id)
      references tb_order (order_id) on delete restrict on update restrict;

alter table tb_cart add constraint FK_cartuser foreign key (user_id)
      references tb_user (user_id) on delete restrict on update restrict;

alter table tb_order add constraint FK_orderUser foreign key (user_id)
      references tb_user (user_id) on delete restrict on update restrict;

alter table tb_output_order add constraint FK_outproduct foreign key (pm_id)
      references tb_product_manage (pm_id) on delete restrict on update restrict;

alter table tb_product add constraint FK_productcategory foreign key (category_id)
      references tb_category (category_id) on delete restrict on update restrict;

alter table tb_star add constraint FK_staruser foreign key (user_id)
      references tb_user (user_id) on delete restrict on update restrict;

alter table tb_system_log add constraint FK_loguserRe foreign key (user_id)
      references tb_user (user_id) on delete restrict on update restrict;

