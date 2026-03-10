package com.tiendaS.service;

import com.tiendaS.domain.Producto;
import com.tiendaS.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo) {
        var lista = productoRepository.findAll();

        if (activo) {
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {

        return productoRepository.findById(producto.getIdProducto()).orElse(null);

    }

    @Transactional
    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    @Transactional
    public boolean delete(Producto producto) {
        try {
            productoRepository.delete(producto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    
    @Transactional(readOnly = true)
    public List<Producto> consultaAmpliada(double precioInf, double precioSup){
        return productoRepository.findByPrecioBetweenOrderByPrecio(precioInf, precioSup);
    }
    
    
    @Transactional(readOnly = true)
    public List<Producto> consultaJPQL(double precioInf, double precioSup){
        return productoRepository.consultaJPQL(precioInf, precioSup);
    }
    
    @Transactional(readOnly = true)
    public List<Producto> consultaSQL(double precioInf, double precioSup){
        return productoRepository.consultaSQL(precioInf, precioSup);
    }
    
    

}
