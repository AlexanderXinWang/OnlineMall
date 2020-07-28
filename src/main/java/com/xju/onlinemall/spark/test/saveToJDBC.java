package com.xju.onlinemall.spark.test;

import com.xju.onlinemall.spark.Rates;
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
        JavaRDD<Rates> ratesJavaRDD = spark.read().textFile("recommendResult/part-00000")
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
        properties.setProperty("password","199811"); //你的数据库密码
        //保存在本地数据库上
        dataset.write().mode(SaveMode.Append).jdbc("jdbc:mysql://127.0.0.1:3306/test", "ratesresult", properties);


        spark.close();
    }
}
