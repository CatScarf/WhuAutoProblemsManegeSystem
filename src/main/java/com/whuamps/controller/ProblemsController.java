package com.whuamps.controller;

import com.whuamps.entity.*;
import com.whuamps.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

//查看所有题目
@Controller
public class ProblemsController {


    @Autowired
    ViewProblemRepository viewProblemRepository;

    //查：所有题目列表
    @GetMapping({"/problems"})
    public String problems(Model model){
        List<ViewProblem> problems = viewProblemRepository.getAll();
        model.addAttribute("problems", problems);
        return "problems/problems";
    }

    //增：来到题目添加页面
    @GetMapping("/problem")
    public String toAddProblem(){
        return "problems/addproblem";
    }

    @Autowired
    ProblemRepository problemRepository;

    //增：添加题目
    @PostMapping("/problem")
    public String addProblem(ProblemRow row){
        //主键不能重复
        if(row.getId() == "" || row.getId() == null){
            //do nothing
        }else{
            Integer id = Integer.parseInt(row.getId());
            Integer count = problemRepository.countById(id);
            if(count == 0){
                problemRepository.insert(id, row.getText(), Integer.parseInt(row.getSubjectid()));
                return "redirect:/problems";
            }else{
            }
        }
        Problem problem = new Problem();
        problem.setStemtext(row.getText());
        problem.setSubjectid(Integer.parseInt(row.getSubjectid()));
        problemRepository.save(problem);
        return "redirect:/problems";
    }

    //删：删除题目
    @DeleteMapping("/problem/{id}")
    public String deleteProblem(@PathVariable("id") Integer id){
        problemRepository.deleteById(id);
        return "redirect:/problems";
    }

    //改：来到修改题目页面
    @GetMapping("/problem/{id}")
    public String toEditProblem(@PathVariable("id") Integer id, Model model){
        Problem problem =  problemRepository.getOne(id);
        ProblemRow row = new ProblemRow();
        row.setId(id.toString());
        row.setSubjectid(problem.getSubjectid().toString());
        row.setText(problem.getStemtext());
        model.addAttribute("problem", row);
        return "problems/changeproblem";
    }

    //改：修改题目
    @PutMapping("/problem")
    public String changeProblem(ProblemRow row){
        problemRepository.deleteById(Integer.parseInt(row.getId()));
        problemRepository.insert(Integer.parseInt(row.getId()), row.getText(), Integer.parseInt(row.getSubjectid()));
        return "redirect:/problems";
    }

    @Autowired
    ViewQuestionRepository viewQuestionRepository;

    //查：所有问题
    @GetMapping({"/questions"})
    public String questions(Model model){
        Collection<ViewQuestion> questions = viewQuestionRepository.getAll();
        model.addAttribute("questions", questions);
        return "problems/questions";
    }

    //增：来到问题添加页面
    @GetMapping("/question")
    public String toAddQuestion(){
        return "problems/addquestion";
    }

    @Autowired
    QuestionRepository questionRepository;

    //增：添加问题
    @PostMapping("/question")
    public String addQuestion(QuestionRow row){
        Question question = RowtoQuestion(row);
        questionRepository.save(question);
        return "redirect:/questions";
    }

    //删：删除问题
    @DeleteMapping("/question/{id}")
    public String deleteQuestion(@PathVariable("id") Integer id){
        questionRepository.deleteById(id);
        return "redirect:/questions";

    }

    //改：来到修改问题页面
    @GetMapping("/question/{id}")
    public String toEditQuestion(@PathVariable("id") Integer id, Model model){
        Question question = questionRepository.getOne(id);
        QuestionRow row = new QuestionRow();
        row.setId(id.toString());
        row.setProblemid(question.getProblemid().toString());
        row.setTypeid(question.getTypeid().toString());
        row.setText(question.getText());
        row.setAnswer(question.getAnswer());
        row.setOptions(question.getOptions().toString());

        row.setOptiona(question.getOptiona());
        row.setOptionb(question.getOptionb());
        row.setOptionc(question.getOptionc());
        row.setOptiond(question.getOptiond());
        row.setOptione(question.getOptione());
        row.setOptionf(question.getOptionf());
        row.setOptiong(question.getOptiong());

        model.addAttribute("question", row);
        return "problems/changequestion";
    }

    //改：修改问题
    @PutMapping("/question")
    public String changeQuestion(QuestionRow row){

        questionRepository.deleteById(Integer.parseInt(row.getId()));
        Question question = RowtoQuestion(row);
        questionRepository.save(question);
        return "redirect:/questions";
    }

    public Question RowtoQuestion(QuestionRow row){
        Question question = new Question();
        try {
            question.setProblemid(Integer.parseInt(row.getProblemid()));
            question.setTypeid(Integer.parseInt(row.getTypeid()));
            question.setInnerorder(questionRepository.countByProblemid(Integer.parseInt(row.getProblemid())));
            question.setText(row.getText());
            question.setAnswer(row.getAnswer());
            question.setOptions(Integer.parseInt(row.getOptions()));
            question.setOptiona(row.getOptiona());
            question.setOptiona(row.getOptionb());
            question.setOptiona(row.getOptionc());
            question.setOptiona(row.getOptiond());
            question.setOptiona(row.getOptione());
            question.setOptiona(row.getOptionf());
            question.setOptiona(row.getOptiong());

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return question;
    }

    @Autowired
    SubjectRepository subjectRepository;

    //查：所有科目列表
    @GetMapping({"/subjects"})
    public String subjects(Model model){
        List<Subject> subjects = subjectRepository.getAll();
        model.addAttribute("subjects", subjects);
        return "problems/subjects";
    }

    //增：来到科目添加页面
    @GetMapping("/subject")
    public String toAddSubject(){
        return "problems/addsubject";
    }


    //增：添加科目
    @PostMapping("/subject")
    public String addSubject(SubjectRow row){
        //主键不能重复
        if(row.getId() == "" || row.getId() == null){
            //do nothing
        }else{
            Integer id = Integer.parseInt(row.getId());
            Integer count = subjectRepository.countById(id);
            if(count == 0){
                subjectRepository.insert(id, row.getSubject());
                return "redirect:/subjects";
            }else{

            }
        }
        Subject subject = new Subject();
        subject.setSubject(row.getSubject());
        subjectRepository.save(subject);
        return "redirect:/subjects";
    }

    //删：删除科目
    @DeleteMapping("/subject/{id}")
    public String deleteSubject(@PathVariable("id") Integer id){
        subjectRepository.deleteById(id);
        return "redirect:/subjects";
    }

    //改：来到修改题目页面
    @GetMapping("/subject/{id}")
    public String toEditSubject(@PathVariable("id") Integer id, Model model){
        Subject subject =  subjectRepository.getOne(id);
        SubjectRow row = new SubjectRow();
        row.setId(id.toString());
        row.setSubject(subject.getSubject());
        model.addAttribute("subject", row);
        return "problems/changesubject";
    }

    //改：修改题目
    @PutMapping("/subject")
    public String changeSubject(SubjectRow row){
        subjectRepository.deleteById(Integer.parseInt(row.getId()));
        subjectRepository.insert(Integer.parseInt(row.getId()),row.getSubject());
        return "redirect:/subjects";
    }

    @Autowired
    TypeRepository typeRepository;

    //查：所有题型列表
    @GetMapping({"/types"})
    public String types(Model model){
        List<Type> types = typeRepository.getAll();
        model.addAttribute("types", types);
        return "problems/types";
    }

    //增：来到题型添加页面
    @GetMapping("/type")
    public String toAddType(){
        return "problems/addtype";
    }


    //增：添加题型
    @PostMapping("/type")
    public String addType(TypeRow row){
        //主键不能重复
        if(row.getId() == "" || row.getId() == null){
            //do nothing
        }else{
            Integer id = Integer.parseInt(row.getId());
            Integer count = typeRepository.countById(id);
            if(count == 0){
                typeRepository.insert(id, row.getType());
                return "redirect:/types";
            }else{

            }
        }
        Type type = new Type();
        type.setType(row.getType());
        typeRepository.save(type);
        return "redirect:/types";
    }

    //删：删除题型
    @DeleteMapping("/type/{id}")
    public String deleteType(@PathVariable("id") Integer id){
        typeRepository.deleteById(id);
        return "redirect:/types";
    }

    //改：来到修改题型页面
    @GetMapping("/type/{id}")
    public String toEditType(@PathVariable("id") Integer id, Model model){
        Type type = typeRepository.getOne(id);
        TypeRow row = new TypeRow();
        row.setId(id.toString());
        row.setType(type.getType());
        model.addAttribute("type", row);
        return "problems/changetype";
    }

    //改：修改提醒
    @PutMapping("/type")
    public String changeType(TypeRow row){
        typeRepository.deleteById(Integer.parseInt(row.getId()));
        typeRepository.insert(Integer.parseInt(row.getId()),row.getType());
        return "redirect:/types";
    }
}
