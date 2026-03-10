package com.tiendaS.controller;

import com.tiendaS.service.CategoriaService;
import com.tiendaS.domain.Categoria;
import com.tiendaS.service.FirebaseStorageService;
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
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = categoriaService.getCategorias(false);
        model.addAttribute("categorias", lista);
        model.addAttribute("totalCategorias", lista.size());

        return "/categoria/listado";
    }

    @Autowired
    private FirebaseStorageService firebaseStorageService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping("/guardar")
    public String guardar(Categoria categoria,
            @RequestParam("imagenFile") MultipartFile imagenFile, RedirectAttributes redirectAttributes) {

        if (!imagenFile.isEmpty()) {
            categoriaService.save(categoria);
            String rutaImagen = firebaseStorageService.cargaImagen(imagenFile, "categoria", categoria.getIdCategoria());
            categoria.setRutaImagen(rutaImagen);

        }

        categoriaService.save(categoria);

        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null,
                        Locale.getDefault()));

        return "redirect:/categoria/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(Categoria categoria,
            RedirectAttributes redirectAttrs) {
        categoria = categoriaService.getCategoria(categoria);

        if (categoria == null) {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("categoria.error01", null, Locale.getDefault()));
        } else if (false) {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("categoria.error02", null, Locale.getDefault()));
        } else if (categoriaService.delete(categoria)) {
            redirectAttrs.addFlashAttribute("todoOk", messageSource.getMessage("mensaje.eliminado", null, Locale.getDefault()));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("categoria.error03", null, Locale.getDefault()));
        }
        return "redirect:/categoria/listado";

        /* va en lugar del false... categoria.getProductos() != null && !categoria.getProductos().isEmpty()*/
    }

    @PostMapping("/modificar")
    public String modificar(Categoria categoria, Model model) {
        categoria = categoriaService.getCategoria(categoria);
        model.addAttribute("categoria", categoria);

        return "/categoria/modifica";
    }
    
    @GetMapping("/estado")
    public String buscarPorEstado(@RequestParam("activo") boolean activo, Model model){
        var lista = categoriaService.getCategoriasPorEstado(activo);
        model.addAttribute("categorias", lista);
        model.addAttribute("totalCategorias", lista.size());
        return "/categoria/listado";
    }

}
