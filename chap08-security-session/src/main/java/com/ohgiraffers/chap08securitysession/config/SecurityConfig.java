package com.ohgiraffers.chap08securitysession.config;

import com.ohgiraffers.chap08securitysession.common.UserRole;
import com.ohgiraffers.chap08securitysession.config.handler.AuthFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthFailHandler authFailHandler;

    // 비밀번호 인코딩 Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 정적 리소스 요청 제외 Bean( css, js, image 등 )
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 필터체인 커스텀
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
                                //auth 는 authorizeHttpRequests 의 반환물
        http.authorizeHttpRequests(auth -> { // 서버의 리소스에 접근 가능한 권한 설정
            // 이 요청들은 모든 사용자에게 허용 - 인증 필요 없음.
            auth.requestMatchers("/auth/login", "/user/signup", "/auth/fail", "/").permitAll();
            // Role_admin 에게만 권한을 허용하겠다.
            auth.requestMatchers("/admin/*").hasAnyAuthority(UserRole.ADMIN.getRole());
            // Role_user 에게만 권한을 허용하겠다.
            auth.requestMatchers("/user/*").hasAnyAuthority(UserRole.USER.getRole());
            // 위에 것들 빼고 모든 요청을 인증된 사용자에게 허용 해주겠다..
            auth.anyRequest().authenticated();
        }).formLogin(login -> {
            login.loginPage("/auth/login");     // 해당 주소에 get 요청 매핑이 있으면 같은 주소의 post 요청은 security가 처리해주겠다~~
            login.usernameParameter("user");
            login.passwordParameter("pass");
            login.defaultSuccessUrl("/"); // 로그인 성공 시 보낼 곳 설정.. mapping 이 존재해야함.
            login.failureHandler(authFailHandler); // 실패시 처리
        }).logout(logout -> {
            // 로그아웃 시 요청을 날릴 url 설정 (페이지 만들 필요없음)
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
            logout.deleteCookies("JSESSIONID");     // 로그인시 세션이 만들어지면 JSESSION 이라는 쿠키가 생김
            // 이걸 못쓰게 제거해주면 사용자가 세션을 쓰지 못함.
            logout.invalidateHttpSession(true); // 세션이 소멸하는 걸 허용하는 메소드
            logout.logoutSuccessUrl("/"); // 로그아웃 완료 후 이동할 페이지 설정
        }).sessionManagement(session -> {
            session.maximumSessions(1); // 세션의 갯수 제한 1로 설정 시 중복 로그인 X
            session.invalidSessionUrl("/"); // 세션 만료시 이동할 페이지
        }).csrf(csrf -> csrf.disable());    // csrf 처리 안함.
        return http.build();
    }
}
