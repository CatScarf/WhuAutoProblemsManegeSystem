package com.whuamps.service;

import com.whuamps.entity.LoginUser;
import com.whuamps.entity.MyUserEntity;
import com.whuamps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

//登陆验证服务
@Component
public class MyLoginService implements UserDetailsService {

    //自动注入连接池
    @Resource
    UserRepository userRepository;

    //查找密码并匹配
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException{
        MyUserEntity myUserEntity = userRepository.findByUsername(s);
        //用户不存在则抛出异常
        if(myUserEntity == null) throw new UsernameNotFoundException("user \"" + s + "\" does not exist.");
        List<GrantedAuthority> authorityLists = AuthorityUtils.commaSeparatedStringToAuthorityList(myUserEntity.getRole().toString());
        //System.out.println(new BCryptPasswordEncoder().encode("123"));
        //注意：commaSeparatedStringToAuthorityList放入角色时需要加前缀ROLE_，而在controller使用时不需要加ROLE_前缀
        return new LoginUser(myUserEntity.getUsername(), myUserEntity.getPassword(),authorityLists);
    }
}
