//package kr.co.anbu.config;
//
//import kr.co.anbu.security.jwt.JwtAuthenticationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//                .antMatchers("/**", "/static/**", "/webjars/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//
//        http
//                .cors()
//                .and()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.authorizeRequests()
//                .antMatchers("/api/**").permitAll();
//
//        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
//
//    }
//
//}
