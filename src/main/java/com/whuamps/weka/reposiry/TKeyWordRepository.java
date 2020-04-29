package com.whuamps.weka.reposiry;

import com.whuamps.weka.entity.TKeyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TKeyWordRepository extends JpaRepository<TKeyWord, Integer> {
    @Transactional
    @Modifying
    @Query(value = "delete from tkeywords",nativeQuery = true)
    public int deleteAllData();

    public List<TKeyWord> findByClassifyId(Integer classifyid);
}
