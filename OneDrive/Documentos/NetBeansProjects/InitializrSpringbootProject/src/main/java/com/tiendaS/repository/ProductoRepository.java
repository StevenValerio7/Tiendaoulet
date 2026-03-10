package com.tiendaS.repository;

import com.tiendaS.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository 
        extends JpaRepository<Producto, Long>{
    
    //Consulta para hacer un filtro por precio, ordenado por precio
    
    public List<Producto> findByPrecioBetweenOrderByPrecio(double precioInf,double precioSup);
    
    //Consulta JPQL para hacer un filtro por precio, ordenado por precio
    
    @Query(value="SELECT a FROM Producto a WHERE a.precio BETWEEN :precioInf AND :precioSup ORDER BY a.precio")
    public List<Producto> consultaJPQL(double precioInf,double precioSup);
    
    
    //Consulta SQL para hacer un filtro por precio, ordenado por precio
    
    @Query(nativeQuery= true, value="SELECT * FROM producto a WHERE a.precio BETWEEN :precioInf AND precioSup ORDER BY a.precio")
    public List<Producto> consultaSQL(double precioInf,double precioSup);
    
}
