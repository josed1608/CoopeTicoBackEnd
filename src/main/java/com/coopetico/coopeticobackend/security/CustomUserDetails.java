package com.coopetico.coopeticobackend.security;

import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación custom de UserDetails para introducir la lógica especial de negocio de los grupos y permisos
 * @author      Jose David Vargas Artavia
 */
public class CustomUserDetails implements UserDetails {
    private UsuarioEntidad user;
    private Collection<SimpleGrantedAuthority> permisos;
    private String grupoId;

    public CustomUserDetails(UsuarioEntidad user, List<String> permisos) {
        this.user = user;
        this.permisos = permisos
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        this.grupoId = user.getGrupoByIdGrupo().getPkId();
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return this.permisos;
    }

    public String getGrupo(){
        return this.grupoId;
    }

    @Override
    public String getPassword() {
        return user.getContrasena();
    }

    @Override
    public String getUsername() {
        return user.getPkCorreo();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}