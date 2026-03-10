package com.tiendaS.repository;

import com.tiendaS.domain.Constante;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstanteRepository extends JpaRepository<Constante, Long>{
    public Constante findByAtributo(String atributo);
}
