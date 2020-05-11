package com.whuamps.controller;

import com.whuamps.entity.Question;
import com.whuamps.repository.QuestionRepository;
import com.whuamps.weka.HClassifyService;
import com.whuamps.weka.TClassifyService;
import com.whuamps.weka.entity.HKeyWord;
import com.whuamps.weka.entity.TKeyWord;
import com.whuamps.weka.reposiry.HKeyWordRepository;
import com.whuamps.weka.reposiry.TKeyWordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    HClassifyService hClassifyService;
    @Autowired
    TKeyWordRepository tKeyWordRepository;
    @Autowired
    TClassifyService tClassifyService;

    //来到训练页面
    @GetMapping("/train")
    public String toTrain(Model model){
        model.addAttribute("question_quantity",questionRepository.count());
        model.addAttribute("htrainningset_quantity",hKeyWordRepository.count());
        model.addAttribute("model_path", hClassifyService.getModelPath());

        model.addAttribute("ttrainningset_quantity",tKeyWordRepository.count());
        model.addAttribute("tmodel_path", tClassifyService.getModelPath());
        return "train/train";
    }

    //来到训练页面（含自定义信息）
    private String toTrainWithMsg(String msg, Model model){
        model.addAttribute("question_quantity",questionRepository.count());
        model.addAttribute("htrainningset_quantity",hKeyWordRepository.count());
        model.addAttribute("model_path", hClassifyService.getModelPath());

        model.addAttribute("ttrainningset_quantity",tKeyWordRepository.count());
        model.addAttribute("tmodel_path", tClassifyService.getModelPath());

        model.addAttribute("msg", msg);
        return "train/train";
    }

    //删：清空首行训练集
    @DeleteMapping("/deleteHTrainingSet")
    public String deleteHTrainingSet(Model model){
        hKeyWordRepository.deleteAllData();
        return toTrainWithMsg("成功清空首行判断训练集！",model);
    }

    //增：生成首行训练集
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
    //开始首行训练
    @PostMapping("/trainModel1")
    public String trainModel(Model model, HttpServletRequest req){
        List<HKeyWord> hKeyWords = hKeyWordRepository.findAll();

        try{
            //文件名
            String fileName = "model_head.arff";
            //路径名
            String modelPath = System.getProperty("user.dir") + File.separator + "model" + File.separator + fileName;
            File destFile = new File(modelPath);
            //创建文件夹
            destFile.getParentFile().mkdirs();
            hClassifyService.createHClassifyWekaModel(modelPath);
            return toTrainWithMsg("模型训练成功！",model);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return toTrainWithMsg("模型训练失败！" + e.getMessage(), model);
        }
    }

    //快速判断是否为首行
    @PostMapping("/qustionhead")
    public String qustionHead(Model model, HttpServletRequest request){
        try {
            String word = request.getParameterMap().get("qustionhead")[0];
            String result = hClassifyService.getResultByExecuteParticipleAndClassify(word);
            return toTrainWithMsg("结果为：" + result , model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return toTrainWithMsg("识别失败！" +  e.getMessage(),model);
        }
    }

    //删：清空题型训练集
    @DeleteMapping("/deleteTTrainingSet")
    public String deleteTTrainingSet(Model model){
        tKeyWordRepository.deleteAllData();
        return toTrainWithMsg("成功清空题型分类训练集！",model);
    }

    //增：生成题型分类训练集
    @PostMapping("/generateTTrainingSet")
    public String generateTTrainingSet(Model model){
        List<Question> questionList = questionRepository.getAll();
        Vector<TKeyWord> tKeyWords = new Vector<TKeyWord>();
        for(Question question : questionList){
                TKeyWord tKeyWord = new TKeyWord();
                tKeyWord.setClassifyId(question.getTypeid());
                tKeyWord.setKeywordName(question.getText());
                tKeyWords.add(tKeyWord);
            }

        //存储
        tKeyWordRepository.saveAll(tKeyWords);
        return toTrainWithMsg("成功生成题型分类训练集！",model);
    }
    //开始题型分类训练
    @PostMapping("/trainModel2")
    public String trainModel2(Model model, HttpServletRequest req){

        List<TKeyWord> tKeyWords = tKeyWordRepository.findAll();

        for(TKeyWord tKeyWord : tKeyWords){
            //System.out.println(tKeyWord.getId() + " " + tKeyWord.getClassifyId() + " " + tKeyWord.getKeywordName());
        }

        try{
            //文件名
            String fileName = "model_type.arff";
            //路径名
            String modelPath = System.getProperty("user.dir") + File.separator + "model" + File.separator + fileName;
            File destFile = new File(modelPath);
            //创建文件夹
            destFile.getParentFile().mkdirs();
            tClassifyService.createTypeWekaModel(modelPath);
            return toTrainWithMsg("题型分类模型训练成功！",model);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return toTrainWithMsg("题型分类模型训练失败！" + e.getMessage(), model);
        }
    }

    @PostMapping("/judgetype")
    public String judgeType(Model model, HttpServletRequest request){
        try {
            String word = request.getParameterMap().get("judgetype")[0];
            String result = tClassifyService.getResultByExecuteParticipleAndClassify(word);
            return toTrainWithMsg("结果为：" + result , model);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return toTrainWithMsg("识别失败！" +  e.getMessage(),model);
        }
    }
}
