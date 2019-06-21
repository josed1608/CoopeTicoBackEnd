package com.coopetico.coopeticobackend.security;

import com.coopetico.coopeticobackend.security.jwt.JwtConfigurer;
import com.coopetico.coopeticobackend.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Clase de configuración de Spring Security
 * @author      Jose David Vargas Artavia
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
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
        return new BCryptPasswordEncoder();
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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                // Aquí se agregan las reglas de autenticación para acceder a los endpoint
                .antMatchers(HttpMethod.POST,"/auth/signin").permitAll()
                .antMatchers(HttpMethod.POST,"/clientes").permitAll()
                .antMatchers(HttpMethod.GET, "/ws/**").permitAll()
                .antMatchers(HttpMethod.GET, "/ws-flutter/**").permitAll()
                .antMatchers(HttpMethod.POST, "/viajes/aceptar-rechazar").authenticated()
                // El has authority se usa para definir cuáles permisos permiten acceder a ese endpoint
                .anyRequest().permitAll()
                .and()
                // Se aplica el filtro de los JWT
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
}
