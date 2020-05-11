package com.whuamps.weka;

import com.whuamps.entity.Type;
import com.whuamps.repository.TypeRepository;
import com.whuamps.weka.entity.CreateData;
import com.whuamps.weka.entity.TKeyWord;
import com.whuamps.weka.reposiry.TKeyWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

@Service
public class TClassifyService {

    @Autowired
    TypeRepository typeRepository; //分类名

    @Autowired
    TKeyWordRepository tKeyWordRepository; //训练集

//    @Value("${model.path}")
//    private String modelPath;
    private String modelPath = "No Model";

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    //训练模型
    @Transactional
    public void createTypeWekaModel(String modelPath) {
        this.modelPath = modelPath;
        // 从数据库查找到所有分类数据
        List<Type> allTypeList = typeRepository.getAll();
        if (allTypeList == null || allTypeList.isEmpty()) {
            System.out.println("没有从数据库查找到分类数据");
            return;
        }
        System.out.println("分类模型训练开始");
        generateInstanceAndModelLearn(allTypeList);
    }

    private void generateInstanceAndModelLearn(List<Type> allTypeList) {
        // 生成Instances（每个Instances对应一个ARFF）
        Instances trainData = generateInstance(allTypeList);
        // 模型学习
        FilteredClassifier evaluateAndLearn = WekaUtil.evaluateAndLearn(trainData);
        WekaUtil.saveModel(modelPath, evaluateAndLearn);
        System.out.println("题型分类模型训练完毕并存储到硬盘");
    }

    /**
     * 程序构建Arff文件
     *
     * @param allTypeList
     * @return
     */
    private Instances generateInstance(List<Type> allTypeList) {
        //System.out.println("开始生成Instance");
        // 得到所有的分类名
        List<String> varietyOfClassify = new ArrayList<>(100);
        for (Type type : allTypeList) {
            //System.out.println("分类名：" + Type.getypeName());
            varietyOfClassify.add(type.getType());
        }
        // 构建@data训练集数据
        List<CreateData> entities = createArffData(allTypeList);

        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("@@class@@", varietyOfClassify));
        attributes.add(new Attribute("text", true));

        // 构建instances
        Instances instances = new Instances("classify", attributes, 500);
        // 设置分类的索引
        instances.setClassIndex(instances.numAttributes() - 1);

        // 添加数据到@data
        for (CreateData secRepoEntity : entities) {
            Instance instance = new DenseInstance(attributes.size());
            // 必须放在创建一个新的instance后 否则会报没加入Dataset异常
            instance.setDataset(instances);
            if (varietyOfClassify.contains(secRepoEntity.getClassifyName())) {
                instance.setValue(0, secRepoEntity.getClassifyName());
                instance.setValue(1, secRepoEntity.getTestValue());
            }
            instances.add(instance);
        }

        instances.setClassIndex(0);
        return instances;
    }


    /**
     * 准备ArffData数据
     *
     * @param allTypeList
     * @return
     */
    private List<CreateData> createArffData(List<Type> allTypeList) {
        //System.out.println("开始创建ArffData数据");
        List<CreateData> createArffData = new ArrayList<>();
        for (Type type : allTypeList) {
            List<TKeyWord> classifyKeywordByClassifyId = tKeyWordRepository.findByClassifyId(type.getId());
            //System.out.println("分类id: " + Type.getId() + " 分类名: " + Type.getypeName() + "该分类的数据数量" + classifyKeywordByClassifyId.size());
            for (int i = 0; i < classifyKeywordByClassifyId.size(); i++) {
                createArffData.add(new CreateData(type.getType(), classifyKeywordByClassifyId.get(i).getKeywordName()));
            }
        }
        return createArffData;
    }

    @Transactional
    public String getResultByExecuteParticipleAndClassify(String word) {
        try {
            if (word.isBlank()) {
                return "";
            }

            // 加载词库模型
            FilteredClassifier model = WekaUtil.loadModel(modelPath);
            List<Type> allTypeList = typeRepository.getAll();
            List<String> nameString = allTypeList.stream().map(Type::getType).collect(Collectors.toList());
            // 得到分类结果

            for(String s : nameString){
                System.out.println(s);
            }

            String result = makeInstance(model, nameString, word);
            return result;
        } catch (Exception e) {
            System.out.println("wordclassify error ,  detail message:{}" + e.toString());
        }
        return "";
    }

    @Transactional
    public Vector<String> getResultsByExecuteParticipleAndClassify(Vector<String> words) {
        try {
            for(String word : words){
                //System.out.println(word);
            }

            // 加载词库模型
            FilteredClassifier model = WekaUtil.loadModel(modelPath);
            List<Type> allTypeList = typeRepository.getAll();
            List<String> nameString = allTypeList.stream().map(Type::getType).collect(Collectors.toList());
            // 得到分类结果
            Vector<String> results = new Vector<String>();
            for(int i = 0; i < words.size(); i++){
                String result = makeInstance(model, nameString, words.get(i));
                results.add(result);
                System.out.println(i + "/" +  words.size() + " " + result);
            }

            // System.out.println("分类结果" + result);
            return results;
        } catch (Exception e) {
            System.out.println("wordclassify error ,  detail message:{}" + e.toString());
        }
        return new Vector<String>();
    }

    /**
     * 生成一个新的instance用于得出结果
     *
     * @param evaluateAndLearn
     * @param varietyOfClassify
     */
    public String makeInstance(FilteredClassifier evaluateAndLearn, List<String> varietyOfClassify, String word) {

        // 添加第一个分类值
        FastVector fvNominalVal = new FastVector(50);
        for (String classify : varietyOfClassify) {
            fvNominalVal.addElement(classify);
        }
        Attribute attribute1 = new Attribute("@@class@@", fvNominalVal);
        Attribute attribute2 = new Attribute("text", (FastVector) null);

        FastVector fvWekaAttributes = new FastVector(2);
        fvWekaAttributes.addElement(attribute1);
        fvWekaAttributes.addElement(attribute2);
        Instances instances = new Instances("cardniu_text_classify", fvWekaAttributes, 1);
        // 设置索引
        instances.setClassIndex(0);
        // 创造一个新instance
        DenseInstance instance = new DenseInstance(2);
        instance.setValue(attribute2, word);
        instances.add(instance);
        double pred;
        try {
            pred = evaluateAndLearn.classifyInstance(instances.instance(0));
            return instances.classAttribute().value((int) pred);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    /**
     * 程序构建Arff文件
     * @param entities
     * @param varietyOfClassify
     * @return
     */
    private static Instances generatePopularInstance(List<SecRepoEntity> entities,List<String> varietyOfClassify) {

        ArrayList<Attribute> attributes = new ArrayList();
        //第一个属性为分类
        attributes.add(new Attribute("@@class@@", varietyOfClassify));
        //本例需求为文本分类   所以第二个属性为文本
        attributes.add(new Attribute("text", true));

        /* 设置instances，相当于一个ARFF文件  第一个参数为ARFF  @relation <relation-name> 第二个参数设置属性
         * 第三个属性为ArrayList的容量，instances内部设置@data时是个ArrayList  根据实际需要设置*/
        Instances instances = new Instances("repo_popular", attributes, 1000);
        //设置分类的索引   从0开始
        instances.setClassIndex(instances.numAttributes() - 1);
        System.out.println("instances" + instances.toString());
        // add instance
        for (SecRepoEntity secRepoEntity : entities) {
            Instance instance = new DenseInstance(attributes.size());

            //必须放在创建一个新的instance后  否则会报没加入Dataset异常
            instance.setDataset(instances);
            for (int i = 0; i < 10; i++) {
                if (varietyOfClassify.contains(secRepoEntity.getClassName())) {
                    instance.setValue(0, secRepoEntity.getClassName());
                    instance.setValue(1, secRepoEntity.getValue());
                }
                instances.add(instance);
            }
        }
        return instances;
    }
}