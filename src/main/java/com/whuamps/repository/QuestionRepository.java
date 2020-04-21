package com.whuamps.repository;

import com.whuamps.entity.Problem;
import com.whuamps.entity.Question;
import com.whuamps.entity.ViewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    int countByProblemid(Integer problemid);

    //查询所有题目
    //nativeQuery=true表示SQL，否则为HQL
    @Query(value="select * from questions", nativeQuery = true)
    List<Question> getAll();
}
