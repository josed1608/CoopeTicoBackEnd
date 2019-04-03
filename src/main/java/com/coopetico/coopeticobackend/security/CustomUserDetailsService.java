package com.coopetico.coopeticobackend.security;

import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import com.coopetico.coopeticobackend.repositorios.UsuariosRepositorio;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Implementación custom de UserDetailsService para devolver instancias de nuestro UserDetails custom
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UsuariosRepositorio users;

    @Autowired
    public CustomUserDetailsService(UsuariosRepositorio users) {
        this.users = users;
    }

    /**
     * Método que se sobrecarga para devolver instancias de CustomUserDetails
     *
     * @param username correo del usuario
     * @return retorna el UserDetails del usuario que se dió por parámetro
     * @throws UsernameNotFoundException en caso de que el correo no corresponda a ningún usuario
     */
    @Override
    @Transactional
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