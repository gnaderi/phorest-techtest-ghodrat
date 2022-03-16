package com.naderi.phorest.salon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final String REMEMBER_ME_KEY = "RSM-remember-me";
    public static final String RSM_REMEMBER_ME_PARAM = "RSM-remember-me";
    public static final String RSM_REMEMBER_ME_COOKIE_NAME = "RSM-remember-me";
    private static final String[] PUBLIC_RESOURCE_LIST = new String[]{"/", "/public", "/js/**", "/img/**", "/css/**"};
    private static final String[] SECURED_RESOURCE_LIST = new String[]{"**/**"};


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_RESOURCE_LIST)   //these two lines
                .permitAll()                              // defines the public url-s.
                .antMatchers(SECURED_RESOURCE_LIST)       // Secured urls
                .authenticated()                     // is only accessed by authenticated users with proper permissions

                .and()
                .httpBasic()

                .and()
                .formLogin()
                .permitAll()
                .and()
                .headers()
                .cacheControl()
                .and()
                .frameOptions()
                .deny()
                .and()
                .rememberMe()
                .rememberMeCookieName(RSM_REMEMBER_ME_COOKIE_NAME)
                .rememberMeParameter(RSM_REMEMBER_ME_PARAM)
                .useSecureCookie(true)
                .key(REMEMBER_ME_KEY)
                .tokenValiditySeconds(60 * 60 * 24 * 10) // 10 days
                .tokenRepository(persistentTokenRepository())
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout")
                .clearAuthentication(true)
                .deleteCookies(RSM_REMEMBER_ME_COOKIE_NAME)
                .invalidateHttpSession(true)
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login?expired");
        http.
                csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("naderi")
                .password(passwordEncoder().encode("test"))
                .roles("Admin");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }


}
