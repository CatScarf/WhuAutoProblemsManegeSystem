package com.whuamps.controller;

import com.whuamps.entity.Question;
import com.whuamps.repository.QuestionRepository;
import com.whuamps.weka.ClassifyService;
import com.whuamps.weka.entity.HKeyWord;
import com.whuamps.weka.reposiry.HKeyWordRepository;
import javassist.compiler.ast.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
        return "train/train";
    }

    //来到训练页面（含自定义信息）
    private String toTrainWithMsg(String msg, Model model){
        model.addAttribute("question_quantity",questionRepository.count());
        model.addAttribute("htrainningset_quantity",hKeyWordRepository.count());
        model.addAttribute("msg", msg);
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
                //存储
                hKeyWordRepository.save(hKeyWord);
            }
        }
        return toTrainWithMsg("成功生成训练集！",model);
    }
    //开始训练
    @PostMapping("/trainModel")
    public String trainModel(Model model){
        try{
            classifyService.createHeadWekaModel();
            return toTrainWithMsg("模型训练成功！",model);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return toTrainWithMsg("模型训练失败！",model);
    }
}
