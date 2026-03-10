package com.tiendaS.repository;

import com.tiendaS.domain.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String>{
    
}
