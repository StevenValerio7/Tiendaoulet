package com.tiendaS.controller;

import com.tiendaS.domain.Categoria;
import com.tiendaS.service.ProductoService;
import com.tiendaS.domain.Producto;
import com.tiendaS.service.CategoriaService;
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
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = productoService.getProductos(false);
        model.addAttribute("productos", lista);
        model.addAttribute("totalProductos", lista.size());

        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);

        Producto producto = new Producto();
        producto.setCategoria(new Categoria()); // evita NPE al bindear categoria.idCategoria
        model.addAttribute("producto", producto);

        return "/producto/listado";
    }

    @Autowired
    private FirebaseStorageService firebaseStorageService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping("/guardar")
    public String guardar(Producto producto,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        // Recuperar la categoría existente de la BD
        Categoria catPersistida = categoriaService.getCategoria(producto.getCategoria());
        producto.setCategoria(catPersistida);

        if (!imagenFile.isEmpty()) {
            productoService.save(producto);
            String rutaImagen = firebaseStorageService.cargaImagen(imagenFile, "producto", producto.getIdProducto());
            producto.setRutaImagen(rutaImagen);
        }

        productoService.save(producto);
        redirectAttributes.addFlashAttribute("todoOk", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/producto/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(Producto producto,
            RedirectAttributes redirectAttrs) {
        producto = productoService.getProducto(producto);

        if (producto == null) {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("producto.error01", null, Locale.getDefault()));
        } else if (false) {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("producto.error02", null, Locale.getDefault()));
        } else if (productoService.delete(producto)) {
            redirectAttrs.addFlashAttribute("todoOk", messageSource.getMessage("mensaje.eliminado", null, Locale.getDefault()));
        } else {
            redirectAttrs.addFlashAttribute("error", messageSource.getMessage("producto.error03", null, Locale.getDefault()));
        }
        return "redirect:/producto/listado";

        /* va en lugar del false... producto.getProductos() != null && !producto.getProductos().isEmpty()*/
    }

    @PostMapping("/modificar")
    public String modificar(Producto producto, Model model) {
        producto = productoService.getProducto(producto);
        model.addAttribute("producto", producto);

        var categorias = categoriaService.getCategorias(true);
        model.addAttribute("categorias", categorias);

        return "/producto/modifica";
    }

}
