package com.example.projet.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer idRole;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, unique = true)
    private RoleName role;

    public enum RoleName {
        ADMIN,
        USER,
        GESTIONNAIRE,
        SUPERADMIN
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }
}

