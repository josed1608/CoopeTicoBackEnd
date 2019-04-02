package com.coopetico.coopeticobackend.security;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UsuariosRepositorio users;

    public CustomUserDetailsService(UsuariosRepositorio users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioEntidad> usuario = users.findById(username);
        if(usuario.isPresent()){
            return new CustomUserDetails(usuario.get());
        }
        else{
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }
    }
}