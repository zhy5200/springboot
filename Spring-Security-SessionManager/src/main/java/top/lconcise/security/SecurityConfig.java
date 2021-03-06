package top.lconcise.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.lconcise.security.handler.MyAuthenticationFailureHandler;
import top.lconcise.security.handler.MyAuthenticationSuccessHandler;
import top.lconcise.security.smscode.SmsAuthenticationConfig;
import top.lconcise.security.smscode.SmsCodeFilter;
import top.lconcise.security.validate.code.ValidateCodeFilter;

/**
 * Created by liusj on 2019/7/23
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    @Autowired
    private SmsCodeFilter smsCodeFilter;
    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;
    @Autowired
    private MySessionExpiredStrategy mySessionExpiredStrategy;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加验证码校验过滤器
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)  // 添加短信验证码校验过滤器
                .formLogin() // 表单登录
                .loginPage("/login.html")       // 登录跳转url
//                .loginPage("/authentication/require")
                .loginProcessingUrl("/login")   // 处理表单登录url
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                    .authorizeRequests()            // 授权配置
                    .antMatchers("/login.html", "/css/**", "/authentication/require", "/code/image", "/code/sms", "/session/invalid").permitAll()  // 无需认证
                    .anyRequest()                   // 所有请求
                    .authenticated()                // 都需要认证
                 .and()
                    .csrf().disable()
                    .apply(smsAuthenticationConfig)
                .and()
                    .sessionManagement()
                    .invalidSessionUrl("/session/invalid")
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredSessionStrategy(mySessionExpiredStrategy);

    }
}
