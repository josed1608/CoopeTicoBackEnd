package com.coopetico.coopeticobackend.security;

import com.coopetico.coopeticobackend.security.jwt.JwtConfigurer;
import com.coopetico.coopeticobackend.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración de Spring Security
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final
    JwtTokenProvider jwtTokenProvider;

    private final
    UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, @Qualifier("customUserDetailsService") UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Crea el bean de Authentication Manager
     *
     * @return la instancia de AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Configura el bean de PasswordEncoder que se utilizará para encriptar.
     *
     * @return la instancia del PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }

    /**
     *  Método principal de configuración donde se incluyen las reglas de autenticación y acceso a los endpoints
     *
     * @param http instancia de seguridad
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // Aquí se agregan las reglas de autenticación para acceder a los endpoint
                .antMatchers("/auth/signin").permitAll()
                // El has authority se usa para definir cuáles permisos permiten acceder a ese endpoint
                .antMatchers("/auth/usuarios").hasAuthority("1")
                .anyRequest().permitAll()
                .and()
                // Se aplica el filtro de los JWT
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
