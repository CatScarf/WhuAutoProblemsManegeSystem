package com.whuamps.weka;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomTree;
import weka.core.Debug;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.*;

public class WekaUtil {
    private WekaUtil(){}

    //加载数据生成Instances
    public static Instances loadDataset(String fileName) throws FileNotFoundException {
        System.out.println("Util:加载数据生成Instances");
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
                ArffLoader.ArffReader arffReader=new ArffLoader.ArffReader(reader);
                Instances trainData = arffReader.getData();
                trainData.setClassIndex(0); //必须设置分类索引为0
                return trainData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //拿到数据集后开始训练
    public static FilteredClassifier evaluateAndLearn(Instances trainData){
        //System.out.println("Util:数据集已确认，开始训练");
        try{
            StringToWordVector filter = new StringToWordVector();
            filter.setAttributeIndices("last");
            FilteredClassifier classifier = new FilteredClassifier();
            classifier.setFilter(filter);
            //System.out.println("算法：RandomTree");
            //可选择不同的算法
            classifier.setClassifier(new RandomTree());
            System.out.println("即将加载训练数据");
            classifier.buildClassifier(trainData); //加载训练数据
            System.out.println("成功加载训练数据");
            Evaluation eval = new Evaluation(trainData);
            System.out.println("开始训练");
            eval.crossValidateModel(classifier, trainData,2,new Debug.Random(1));
            trainData.setClassIndex(0);
            return classifier;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //把分类器存入文件
    public static void saveModel(String fileName, FilteredClassifier classifier) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(classifier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //加载分类器
    public static FilteredClassifier loadModel(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {

            Object tmp = in.readObject();
            return (FilteredClassifier) tmp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
