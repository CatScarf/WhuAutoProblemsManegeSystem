package com.whuamps.weka.reposiry;

import com.whuamps.weka.entity.HKeyWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HKeyWordRepository extends JpaRepository<HKeyWord, Integer> {
    @Transactional
    @Modifying
    @Query(value = "delete from hkeywords",nativeQuery = true)
    public int deleteAllData();

    public List<HKeyWord> findByClassifyId(Integer classifyid);
}
