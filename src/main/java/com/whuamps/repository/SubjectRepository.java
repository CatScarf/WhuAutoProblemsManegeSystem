package com.whuamps.repository;

import com.whuamps.entity.Subject;
import com.whuamps.entity.ViewProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    //查询所有科目
    //nativeQuery=true表示SQL，否则为HQL
    @Query(value="select * from subjects", nativeQuery = true)
    List<Subject> getAll();

    int countById(Integer id);

    @Transactional
    @Modifying
    @Query(value = "insert into subjects (id, subject) values (?1, ?2)", nativeQuery = true)
    int insert(Integer id, String subject);
}
