package com.whuamps.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

//用户信息的实体类
//使用JPA注解配置映射关系
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@Entity //告诉JPA这是一个实体类
@Table(name="tbl_user") //@Table指定和哪个数据表对应，如果省略默认表名为user（类名小写）
public class MyUserEntity {
    @Id //标注这是一个主键
    @GeneratedValue(strategy= GenerationType.IDENTITY) //自增主键
    private Integer id;

    @Column(name="username",length=255)
    private String username;

    @Column(name="password",length=255)
    private String password;

    @Column(name="role",length=255)
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
