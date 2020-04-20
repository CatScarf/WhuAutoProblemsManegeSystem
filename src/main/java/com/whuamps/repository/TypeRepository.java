package com.whuamps.repository;

import com.whuamps.entity.Subject;
import com.whuamps.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Integer> {
    //查询所有题型
    //nativeQuery=true表示SQL，否则为HQL
    @Query(value="select * from types", nativeQuery = true)
    List<Type> getAll();

    int countById(Integer id);

    @Transactional
    @Modifying
    @Query(value = "insert into types (id, type) values (?1, ?2)", nativeQuery = true)
    int insert(Integer id, String type);
}
