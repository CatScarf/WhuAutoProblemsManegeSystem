package com.whuamps.config;

import com.whuamps.component.MyAuthenticationFailureHandler;
import com.whuamps.component.MyAuthenticationSuccessHandler;
import com.whuamps.service.MyLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//安全相关配置
@EnableWebSecurity  //@EnableWebSecurity已经带有@Configuration注解了
public class MySecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/login","/hello","/login.html","/webjars/**","~/asserts/**","~/js/**","/*.js","/*.css").permitAll()
                .antMatchers("/","/problems","/questions","/subjects","/types",
                        "/problem","/question","/subject","type",
                        "/problem/**","/question/**","/subject/**","/type/**",
                        "/auto","/upload","/autohandle","/autohandle1","/autohandle2"
                        ).hasRole("USER");
        //开启自动配置的登陆功能
        http.formLogin().usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/LoginProcess")
                .successHandler(myAuthenticationSuccessHandler) //登陆成功的操作
                .failureHandler(myAuthenticationFailureHandler) //登录失败操作
                .loginPage("/login");

        //开启自动配置的注销功能
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/");  //访问/Logout表示用户注销
        //开启记住我功能
        http.rememberMe().rememberMeParameter("remember-me");
        //关闭CSRF功能，不拦截delete请求,否则在删除时会出现403错误
        http.csrf().disable();
    }

    @Autowired
    private MyLoginService myLoginService;

    //定义密码认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       // super.configure(auth);
//        auth.inMemoryAuthentication().withUser("zhangsan").password(new BCryptPasswordEncoder().encode("123456")).roles("USER","ADMIN")
//                .and().withUser("lisi").password("123456").roles("USER");
        auth.userDetailsService(myLoginService);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
