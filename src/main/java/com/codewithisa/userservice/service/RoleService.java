package com.codewithisa.userservice.service;

import com.codewithisa.userservice.entity.Role;
import com.codewithisa.userservice.entity.enumeration.ERoles;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(ERoles name);
}
