package com.whuamps.weka.reposiry;

import com.whuamps.weka.entity.HClassify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HClassifyRepository extends JpaRepository<HClassify, Integer> {
    @Query(value="select * from hclassifys", nativeQuery = true)
    List<HClassify> getAll();
}
