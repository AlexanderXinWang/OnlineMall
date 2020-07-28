package com.xju.onlinemall.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 商场项目:
 * 读取数据,训练模型
 *
 * 数据的格式
 *  userId productId rating  三个属性中间不是空格,是\t,制表符
 *  三个属性类型分别是 integer integer Double(大写开头的)
 *
 * 以txt结尾
 *  训练数据存放的位置在HDFS文件系统里,所以要先把训练数据上传到HDFS里
 * */
public class createModel {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf()
                .setAppName("createModel")
//                .setMaster("local");
                        .setMaster("-yarn");  //这个如果要在hdfs中运行,改成-yarn


        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);

        //读取训练数据,其实也就是我们的用户数据
        //这里面填的是HDFS中的文件名字,你们可以把u.txt换成其他的
        JavaRDD<String> rawRDD = javaSparkContext.textFile("hdfs://192.168.101.8:9000/u.txt");

        JavaRDD<Rating> ratingRDD = rawRDD.map(
                line -> {
                    String[] parts = line.split("\t");
                    return new Rating(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),Double.parseDouble(parts[2]));
                }
        );
        //拆分训练集和测试集,训练集百分之80,测试集百分之20
        JavaRDD<Rating>[] splitRDD = ratingRDD.randomSplit(new double[]{0.9, 0.1});
        JavaRDD<Rating> trainingSet = splitRDD[0]; //训练集
        JavaRDD<Rating> testSet = splitRDD[1]; //测试集

        //训练模型
        MatrixFactorizationModel model = ALS.train(trainingSet.rdd(), 50, 10, 0.01);

        //保存模型
        model.save(javaSparkContext.sc(),"hdfs://192.168.101.8:9000/onlineMallModel");


        javaSparkContext.close();

    }
}
