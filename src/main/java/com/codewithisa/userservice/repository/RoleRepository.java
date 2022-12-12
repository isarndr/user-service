package com.codewithisa.userservice.repository;

import com.codewithisa.userservice.entity.Role;
import com.codewithisa.userservice.entity.enumeration.ERoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERoles name);
}
