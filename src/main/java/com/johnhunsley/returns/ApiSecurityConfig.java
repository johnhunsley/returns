package com.johnhunsley.returns;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 06/03/2017
 */
@Configuration
@EnableWebSecurity(debug = true)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;

    @Value(value = "${auth0.issuer}")
    private String issuer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/app/**").permitAll()
                .antMatchers(HttpMethod.POST, "/app/return").hasAnyRole("CUSTOMER", "SERVICE_PROVIDER")
                .antMatchers(HttpMethod.GET, "/app/return/**").hasRole("SERVICE_PROVIDER")
                .antMatchers("/app/returns/").hasRole("SERVICE_PROVIDER")
                .antMatchers("/app/returns/**").hasRole("SERVICE_PROVIDER")
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();

    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")

                        .allowedOrigins("*")
                        .allowedMethods("PUT", "DELETE", "GET", "OPTIONS", "POST")
                        .allowCredentials(false).maxAge(3600);
            }
        };
    }

    /**
     * <p>
     *     Upgrade version of Spring prevents encoded slashes by default
     *
     *     http://stackoverflow.com/questions/41588506/spring-security-defaulthttpfirewall-the-requesturi-cannot-contain-encoded-slas
     * </p>
     */
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    /**
     * <p>
     *     Upgrade version of Spring prevents encoded slashes by default
     *
     *     http://stackoverflow.com/questions/41588506/spring-security-defaulthttpfirewall-the-requesturi-cannot-contain-encoded-slas
     * </p>
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }
}
