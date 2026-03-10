package com.tiendaS.service;

import com.tiendaS.domain.Rol;
import com.tiendaS.domain.Usuario;
import com.tiendaS.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService {

    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private HttpSession session;
    
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) { //no lo encontro
            throw new UsernameNotFoundException(username);
        }
        
        //Si estamos aca todo bien...
        session.removeAttribute("imagenUsuario");
        session.setAttribute("imagenUsuario", usuario.getRutaImagen());
        
        //Se van a cargar los roles del usuario... como roles seguridad...
        var roles=new ArrayList<GrantedAuthority>();
        
        for(Rol rol: usuario.getRoles()){
            roles.add(new SimpleGrantedAuthority("ROLE_"+rol.getNombre()));
        }
        
        return new User(usuario.getUsername(),usuario.getPassword(), roles);
    }
    
}
