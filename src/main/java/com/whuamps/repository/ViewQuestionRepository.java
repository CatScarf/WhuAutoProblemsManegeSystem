package com.whuamps.repository;

import com.whuamps.entity.ViewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ViewQuestionRepository extends JpaRepository<ViewQuestion, Integer> {
    //查询所有题目
    //nativeQuery=true表示SQL，否则为HQL
    @Query(value="select * from view_questions", nativeQuery = true)
    List<ViewQuestion> getAll();
}
