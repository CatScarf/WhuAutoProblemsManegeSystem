package com.whuamps.repository;

import com.whuamps.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Collection;

public interface ProblemRepository extends JpaRepository<Problem, Integer> {
    //查询所有题目
    //nativeQuery=true表示SQL，否则为HQL
//    @Query(value = "select id, subjectid, left(stemtext,20) as stemtext from problems", nativeQuery = true)
//    Collection<Problem> getAll();
    int countById(Integer id);

    @Transactional
    @Modifying
    @Query(value = "insert into problems (id, stemtext, subjectid) values (?1, ?2, ?3)", nativeQuery = true)
    int insert(Integer id, String stemtext, Integer subjectid);
}
