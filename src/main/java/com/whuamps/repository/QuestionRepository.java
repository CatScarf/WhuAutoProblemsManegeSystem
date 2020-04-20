package com.whuamps.repository;

import com.whuamps.entity.Problem;
import com.whuamps.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    int countByProblemid(Integer problemid);
}
