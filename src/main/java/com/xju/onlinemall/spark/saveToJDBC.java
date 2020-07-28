package com.xju.onlinemall.spark;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

/**
 *
 * 该类在本地运行
 * 从HDFS中获取
 *
 * */
public class saveToJDBC {
    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder()
                .appName("spark Save Recommend")
                .master("local")
                .getOrCreate();


        //这个读取的文件,要根据最值的文件名来改
        JavaRDD<Rates> ratesJavaRDD = spark.read().textFile("hdfs://192.168.101.8:9000/recommendResult/part-00000")
                .javaRDD()
                .map(line -> {
                    String[] parts = line.split("\t");
                    Rates rates = new Rates();
                    rates.setUser(Integer.parseInt(parts[0]));
                    rates.setProduct(Integer.parseInt(parts[1]));
                    rates.setRating(Double.parseDouble(parts[2]));

                    return rates;
                });

        Dataset<Rates> dataset = spark.createDataset(ratesJavaRDD.rdd(), Encoders.bean(Rates.class));

        dataset.show();


        Properties properties = new Properties();
        properties.setProperty("user","root"); //你的数据库用户名
        properties.setProperty("password","root"); //你的数据库密码
        //保存在本地数据库上                                     数据库的名称                      数据库中的表的名字
        dataset.write().mode(SaveMode.Append).jdbc("jdbc:mysql://127.0.0.1:3306/test", "ratesresult", properties);
        /**
         * ratesresult这个表的结构：
         *
         *CREATE TABLE `ratesresult`  (
         *   `user` int(255) NULL DEFAULT NULL,
         *   `product` int(255) NULL DEFAULT NULL,
         *   `rating` double(255, 0) NULL DEFAULT NULL
         * )
         *
         * */


        spark.close();
    }
}
