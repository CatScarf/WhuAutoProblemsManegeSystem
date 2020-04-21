package com.whuamps.weka.reposiry;

import com.whuamps.weka.entity.HKeyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HKeyWordRepository extends JpaRepository<HKeyWord, Integer> {
    @Query(value="select * from hkeywords", nativeQuery = true)
    List<HKeyWord> getAll();

    List<HKeyWord> findByClassifyId(Integer classifyid);
}
