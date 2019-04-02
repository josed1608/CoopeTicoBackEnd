package com.coopetico.coopeticobackend.security;

import com.coopetico.coopeticobackend.entidades.PermisosGrupoEntidad;
import com.coopetico.coopeticobackend.entidades.UsuarioEntidad;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private UsuarioEntidad user;

    public CustomUserDetails(UsuarioEntidad user) {
        this.user = user;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return user
                .getGrupoByIdGrupo()
                .getPermisosGruposByPkId()
                .stream()
                .map(PermisosGrupoEntidad::getPermisoByPkIdPermisos)
                .map(permiso -> new SimpleGrantedAuthority(Integer.toString(permiso.getPkId())))
                .collect(Collectors.toList());
    }

    public String getGrupo(){
        return user.getGrupoByIdGrupo().getPkId();
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