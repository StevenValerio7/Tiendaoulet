package com.tiendaS.service;

import com.tiendaS.domain.Categoria;
import com.tiendaS.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activo) {
        var lista = categoriaRepository.findAll();

        if (activo) {
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public Categoria getCategoria(Categoria categoria) {

        return categoriaRepository.findById(categoria.getIdCategoria()).orElse(null);

    }

    @Transactional
    public void save(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    @Transactional
    public boolean delete(Categoria categoria) {
        try {
            categoriaRepository.delete(categoria);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Categoria> getCategoriasPorEstado(boolean activo){
        return categoriaRepository.findByActivo(activo);
    }

}
