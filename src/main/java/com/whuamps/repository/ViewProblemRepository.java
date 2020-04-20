package com.whuamps.repository;

import com.whuamps.entity.ViewProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;
import java.util.List;

public interface ViewProblemRepository extends JpaRepository<ViewProblem, Integer> {
    //查询所有题目
    //nativeQuery=true表示SQL，否则为HQL
    @Query(value="select * from view_problems", nativeQuery = true)
    List<ViewProblem> getAll();
}
