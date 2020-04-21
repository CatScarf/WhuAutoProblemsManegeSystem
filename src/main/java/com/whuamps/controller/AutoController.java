package com.whuamps.controller;

import com.whuamps.entity.*;
import com.whuamps.repository.ProblemRepository;
import com.whuamps.repository.QuestionRepository;
import com.whuamps.repository.SubjectRepository;
import com.whuamps.repository.TypeRepository;
import com.whuamps.weka.ClassifyService;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Controller
public class AutoController {
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    ClassifyService classifyService;

    @GetMapping({"/upload","/autohandle1","autohandel2"})
    public String back(){
        return "redirect:/auto";
    }

    //自动提取试题-上传页面
    @GetMapping({"/auto","/"})
    public String auto(){
        return "upload";
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest req, Model model) {
        try {
            //文件名
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            //路径名
            String path = req.getServletContext().getRealPath("") + "uploaded" + File.separator + fileName;
            //创建文件
            File destFile = new File(path);
            //创建文件夹
            destFile.getParentFile().mkdirs();
            //存储文件
            file.transferTo(destFile);

            String buffer = "";
            if (path.endsWith("doc")) {
                InputStream is = new FileInputStream(new File(path));
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
            } else if (path.endsWith("docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();
            } else {
                System.out.println("此文件不是word文件！");
            }

            String[] text = buffer.split("\n");

            //把文件名称和路径放入Model
            model.addAttribute("fileName", fileName);
            model.addAttribute("path", destFile);

            //机器学习
            Boolean[] isDeleted = new Boolean[text.length]; //是否删除
            Boolean[] isHead = new Boolean[text.length]; //是否为首行


            Arrays.fill(isDeleted, false);
            Arrays.fill(isHead, false);

            //判断是否删除
            for(int i = 0; i < text.length; i++){
                if(text[i].isBlank()){
                    isDeleted[i] = true;
                }else{
                    String result = classifyService.getResultByExecuteParticipleAndClassify(text[i]);
                    System.out.println("问题 " + i + " 的判断结果: " + result);
                    if(result.equals("true")){
                        isHead[i] = true;
                    }else{
                        isHead[i] = false;
                    }
                }
            }


            //把问题放入Model
            List<TextAndInfo> textAndInfos = new ArrayList<>();
            for(int i = 0; i < text.length ; i++){
                textAndInfos.add(new TextAndInfo(text[i],isDeleted[i],isHead[i]));
            }


            model.addAttribute("TextAndInfos",textAndInfos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败," + e.getMessage();
        } catch (XmlException e) {
            return "上传失败," + e.getMessage();
        } catch (OpenXML4JException e) {
            return "上传失败," + e.getMessage();
        }
        return "autohandle1";
    }

    //初步处理问题
    @PostMapping("/autohandle1")
    public String autoHandle(HttpServletRequest request, Model model) {
        try {
            Map<String, String[]> map = new HashMap(request.getParameterMap());
            String path = map.get("path")[0]; //正在处理的文件路径
            map.remove("submit");
            map.remove("path");
            String buffer = "";
            if (path.endsWith("doc")) {
                InputStream is = new FileInputStream(new File(path));
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
            } else if (path.endsWith("docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();
            } else {
                System.out.println("此文件不是word文件！");
            }
            String[] text = buffer.split("\n"); //按行分开的文档

            //根据上一步的信息将题目分开
            //初始化分割数组
            Vector<Integer> head = new Vector<Integer>(); //每个问题的首行
            int[] delete = new int[text.length]; //某行是否删除
            int i = 0;
            for (String key : map.keySet()) {
                if(Integer.parseInt(key) >= 0 ){
                    head.add(Integer.parseInt(key));
                    i++;
                }else{
                    delete[-Integer.parseInt(key)] = 1;
                }
            }
            Collections.sort(head); //排序
            //分割
            StringBuffer[] questions = new StringBuffer[head.size()];
            for (i = 0; i < head.size(); i++) {
                if (i < head.size() - 1) { //非最后一个题目
                    for (int j = head.get(i); j < head.get(i + 1); j++) {
                        if (j == head.get(i)) {
                            questions[i] = new StringBuffer(text[j]);
                        } else {
                            if(delete[j] == 0){ //如果该题目没有被删除
                                questions[i].append("\n");
                                questions[i].append(text[j]);
                            }
                        }
                    }
                } else { //最后一个题目
                    for (int j = head.get(i); j < text.length; j++) {
                        if (j == head.get(i)) {
                            questions[i] = new StringBuffer(text[j]);
                        } else {
                            if(delete[j] == 0) { //如果该题目没有被删除
                                questions[i].append("\n");
                                questions[i].append(text[j]);
                            }
                        }
                    }
                }
            }
            //写入model
            model.addAttribute("questions", questions);
            //所有题目类型
            List<Type> types = typeRepository.getAll();
            model.addAttribute("types", types);
            //所有科目
            List<Subject> subjects = subjectRepository.getAll();
            model.addAttribute("subjects", subjects);
            return "autohandle2";
        } catch (Exception e) {
            e.printStackTrace();
            return "处理失败," + e.getMessage();
        }
    }

    @PostMapping("/autohandle2")
    public String autoHandle2(Model model, HttpServletRequest request) {
        try {
            Map<String, String[]> map = new HashMap(request.getParameterMap());
            Integer i = 0, typeid, subjectid;
            String text;
            //入库
            while (map.get(i.toString()) != null) { //遍历所有问题
                text = map.get(i.toString())[0];
                typeid = Integer.parseInt(map.get("typeid" + i.toString())[0]);
                subjectid = Integer.parseInt(map.get("subjectid" + i.toString())[0]);
                //创建题目
                Problem problem = new Problem();
                problem.setSubjectid(subjectid);
                problem = problemRepository.saveAndFlush(problem);
                //System.out.println("problem: " + problem.getId() + " " + problem.getSubjectid().toString());
                //创建问题
                Integer problemid = problem.getId();
                Question question = new Question();
                question.setProblemid(problemid);
                question.setText(text);
                //System.out.print(text);
                //System.out.print("\n");
                question.setInnerorder(-1);
                question.setOptions(0);
                question.setTypeid(typeid);
                //System.out.println("question:" + question.getId() + " " + question.getTypeid() + " " + question.getText());
                questionRepository.save(question);

                i++;
            }
            return "redirect:/questions";
        } catch (Exception e) {
            e.printStackTrace();
            return "处理失败," + e.getMessage();
        }
    }
}