package com.xju.onlinemall.spark.test;


import com.xju.onlinemall.spark.Rates;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 该类在服务器上运行
 * 使用训练好的模型产生用户的推荐数据
 * 并保存到HDFS中
 *
 *
 * */
public class createResult {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf()
                .setAppName("createModel")
                .setMaster("local");//这个如果要在本地测试,改成local


        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        //直接使用训练好的模型
        MatrixFactorizationModel model = MatrixFactorizationModel.load(javaSparkContext.sc(), "testModel1");

        //获得用户的数据,其实就是获得用户的userId
        /**
         * user.txt 的内容按照以下格式
         * userID productId 评分  中间用\t隔开
         * 196	242	3
         * 186	302	3
         * 22	377	1
         * 244	51	2
         * 166	346	1
         * 298	474	4
         * 115	265	2
         * 253	465	5
         *
         * */
        JavaRDD<String> rawRDD = javaSparkContext.textFile("user.txt");

        JavaRDD<Integer> userIdRDD = rawRDD.map(
                (line) -> {
                    String[] parts = line.split("\t");
                    return Integer.parseInt(parts[0]);
                }

        );

        List<Integer> userIds = userIdRDD.collect();


        //预测

        //这里面是所有的推荐数据
        List<Rates> ratesList=new ArrayList<>();

        //给出每个用户最高评分的商品列表，每个用户推荐10个
        for (Integer user:userIds){

            //当前用户的5个推荐
            Rating[] ratings = model.recommendProducts(user, 5);

            //把这5个推荐都加入到总推荐中去
            for (Rating rating:ratings){
                ratesList.add(new Rates(rating.user(),rating.product(),rating.rating()));
            }
        }

        JavaRDD<Rates> rdd = javaSparkContext.parallelize(ratesList);

        rdd.saveAsTextFile("recommendResult");

        javaSparkContext.close();

    }
}
