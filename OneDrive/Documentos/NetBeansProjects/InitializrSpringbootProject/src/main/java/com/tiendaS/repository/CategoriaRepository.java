package com.tiendaS.repository;

import com.tiendaS.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    List<Categoria> findByActivo(boolean activo);
}
