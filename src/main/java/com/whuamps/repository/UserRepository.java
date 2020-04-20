package com.whuamps.repository;

import com.whuamps.entity.MyUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<MyUserEntity, Integer> {
    MyUserEntity findByUsername(String s);
}
