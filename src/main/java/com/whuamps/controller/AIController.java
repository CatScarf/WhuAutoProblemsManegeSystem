package com.whuamps.controller;

import com.whuamps.entity.Question;
import com.whuamps.repository.QuestionRepository;
import com.whuamps.weka.ClassifyService;
import com.whuamps.weka.entity.HKeyWord;
import com.whuamps.weka.reposiry.HKeyWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Vector;

@Controller
public class AIController {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    HKeyWordRepository hKeyWordRepository;

    @Autowired
    ClassifyService classifyService;

    //来到训练页面
    @GetMapping("/train")
    public String toTrain(Model model){
        model.addAttribute("question_quantity",questionRepository.count());
        model.addAttribute("htrainningset_quantity",hKeyWordRepository.count());
        model.addAttribute("model_path",classifyService.getModelPath());
        return "train/train";
    }

    //来到训练页面（含自定义信息）
    private String toTrainWithMsg(String msg, Model model){
        model.addAttribute("question_quantity",questionRepository.count());
        model.addAttribute("htrainningset_quantity",hKeyWordRepository.count());
        model.addAttribute("msg", msg);
        model.addAttribute("model_path",classifyService.getModelPath());
        return "train/train";
    }

    //删：清空训练集
    @DeleteMapping("/deleteHTrainingSet")
    public String deleteHTrainingSet(Model model){
        hKeyWordRepository.deleteAll();
        return toTrainWithMsg("成功清空训练集！",model);
    }
    //增：生成训练集
    @PostMapping("/generateHTrainingSet")
    public String generateHTrainingSet(Model model){
        List<Question> questionList = questionRepository.getAll();
        Vector<HKeyWord> hKeyWords = new Vector<HKeyWord>();
        for(Question question : questionList){
            String[] text = question.getText().split("\n");
            for(int i = 0; i < text.length; i++){
                HKeyWord hKeyWord = new HKeyWord();
                if(i==0){ //如果是首行则分类为1
                    hKeyWord.setClassifyId(1);
                    hKeyWord.setKeywordName(text[i]);
                }else{ //否则分类为2
                    hKeyWord.setClassifyId(2);
                    hKeyWord.setKeywordName(text[i]);
                }
                hKeyWords.add(hKeyWord);
            }
        }
        //存储
        hKeyWordRepository.saveAll(hKeyWords);
        return toTrainWithMsg("成功生成训练集！",model);
    }
    //开始训练
    @PostMapping("/trainModel")
    public String trainModel(Model model, HttpServletRequest req){

        List<HKeyWord> hKeyWords = hKeyWordRepository.getAll();

        for(HKeyWord hKeyWord : hKeyWords){
            System.out.println(hKeyWord.getId() + " " + hKeyWord.getClassifyId() + " " + hKeyWord.getKeywordName());
        }

        try{
            //文件名
            String fileName = "model.arff";
            //路径名
            String modelPath = System.getProperty("user.dir") + File.separator + "model" + File.separator + fileName;
            File destFile = new File(modelPath);
            //创建文件夹
            destFile.getParentFile().mkdirs();
            classifyService.createHeadWekaModel(modelPath);
            return toTrainWithMsg("模型训练成功！",model);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return toTrainWithMsg("模型训练失败！" + e.getMessage(), model);
        }
    }

    @GetMapping("/getResult/{word}")
    public String modelExercise(@PathVariable("word")String word,Model model) {
        try {
            String result = classifyService.getResultByExecuteParticipleAndClassify(word);
            return toTrainWithMsg("结果为：" + result , model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return toTrainWithMsg("识别失败！" +  e.getMessage(),model);
        }
    }

    @PostMapping("/qustionhead")
    public String qustionHead(Model model, HttpServletRequest request){
        try {
            String word = request.getParameterMap().get("qustionhead")[0];
            String result = classifyService.getResultByExecuteParticipleAndClassify(word);
            return toTrainWithMsg("结果为：" + result , model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return toTrainWithMsg("识别失败！" +  e.getMessage(),model);
        }
    }
}
