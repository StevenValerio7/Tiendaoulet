package com.tiendaS.repository;

import com.tiendaS.domain.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long>{
    
}
