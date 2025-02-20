package com.example.back.controller.admin;

import com.example.back.model.Role;
import com.example.back.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public String insert(@RequestBody Role role){
        roleService.insert(role);
        return "insertion reussit!";
    }

    @GetMapping("/{id}")
    public Role findById(@PathVariable Integer id){
        return roleService.findById(id);
    }
}
