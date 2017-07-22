package com.binlix26.userFront.repository;

import com.binlix26.userFront.model.security.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by binlix26 on 17/07/17.
 */
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
