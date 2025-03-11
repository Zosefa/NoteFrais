package com.example.projet.service;

import com.example.projet.model.Role;
import com.example.projet.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void insert(Role role){
        roleRepository.save(role);
    }

    public Role findById(Integer id){
        return roleRepository.findById(id).get();
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role findByRoleName(String roleName) {
        try {
            Role.RoleName roleEnum = Role.RoleName.valueOf(roleName);
            Optional<Role> role = roleRepository.findByRole(roleEnum);
            return role.orElse(null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
