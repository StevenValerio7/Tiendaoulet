package com.tiendaS.controller;

import com.tiendaS.service.CategoriaService;
import com.tiendaS.domain.Categoria;
import com.tiendaS.domain.Rol;
import com.tiendaS.domain.Role;
import com.tiendaS.service.FirebaseStorageService;
import com.tiendaS.service.RolService;
import com.tiendaS.service.RoleService;
import com.tiendaS.service.UsuarioService;
import com.tiendaS.domain.Usuario;
import java.util.ArrayList;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario_role")
public class UsuarioRoleController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolService rolService;

    @GetMapping("/asignar")
    public String asignar(Usuario usuario, Model model) {

        if (usuario == null) {
            usuario = new Usuario();
        }

        usuario = usuarioService.getUsuarioPorUsername(usuario.getUsername());

        if (usuario != null) {

            model.addAttribute("usuario", usuario);

            var lista = roleService.getRoles();
            ArrayList<String> roleDisponibles = new ArrayList<>();
            for (Role r : lista) {
                roleDisponibles.add(r.getRol());
            }

            var rolesAsignados = usuario.getRoles();

            for (Rol r : rolesAsignados) {
                roleDisponibles.remove(r.getNombre());
            }

            model.addAttribute("rolesDisponibles", roleDisponibles);
            model.addAttribute("rolesAsignados", rolesAsignados);
            model.addAttribute("idUsuario", usuario.getIdUsuario());
            model.addAttribute("username", usuario.getUsername());

        }

        return "/usuario_role/asignar";
    }
    
    @GetMapping("/agregar")
    public String agregar(Usuario usuario, Rol rol, Model model) {
        rolService.save(rol);
        return "redirect:/usuario_role/asignar?username="+usuario.getUsername();
    }
    
    @GetMapping("/eliminar")
    public String eliminar(Usuario usuario, Rol rol, Model model) {
        rolService.delete(rol);
        //model.addAttribute("usuario", usuario);
        return "redirect:/usuario_role/asignar?username="+usuario.getUsername();
    }
    
}
