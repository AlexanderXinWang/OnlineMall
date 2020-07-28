package com.xju.onlinemall.spark;


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
//                .setMaster("local");//这个如果要在本地测试,改成local
                .setMaster("-yarn");  //这个如果要在hdfs中运行,改成-yarn


        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        //直接使用训练好的模型
        MatrixFactorizationModel model = MatrixFactorizationModel.load(javaSparkContext.sc(), "hdfs://192.168.101.8:9000/onlineMallModel");


        //获得用户的数据,其实就是获得用户的userId  ,此处的文件是咱们本地数据库中用户的信息,和训练时所用到的数据集格式一致
        JavaRDD<String> rawRDD = javaSparkContext.textFile("hdfs://192.168.101.8:9000/u.txt");
        JavaRDD<Integer> userIdRDD = rawRDD.map(
                (line) -> {
                    String[] parts = line.split("\t");
                    return Integer.parseInt(parts[0]);
                }
        );

        //这里面是获得的所有userId
        List<Integer> userIds = userIdRDD.collect();

        //预测
        //这里面是所有的推荐数据
        List<Rates> ratesList=new ArrayList<>();

        //给出每个用户最高评分的商品列表，每个用户推荐5个,可以自行修改
        for (Integer user:userIds){
            //当前用户的5个推荐
            Rating[] ratings = model.recommendProducts(user, 5);
            //把这5个推荐都加入到总推荐中去
            for (Rating rating:ratings){
                ratesList.add(new Rates(rating.user(),rating.product(),rating.rating()));
            }
        }

        JavaRDD<Rates> rdd = javaSparkContext.parallelize(ratesList);

        //保存到hdfs://192.168.101.8:9000/recommendResult文件中去
        rdd.saveAsTextFile("hdfs://192.168.101.8:9000/recommendResult");

        javaSparkContext.close();

    }
}
