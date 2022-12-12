package com.codewithisa.userservice.service;

import com.codewithisa.userservice.entity.Role;
import com.codewithisa.userservice.entity.enumeration.ERoles;
import com.codewithisa.userservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(ERoles name) {
        return roleRepository.findByName(name);
    }
}
