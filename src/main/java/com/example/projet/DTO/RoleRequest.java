package com.example.projet.DTO;

public class RoleRequest {
    private String id;
    private String name;
    private String containerId;

    private boolean clientRole;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public boolean isClientRole() {
        return clientRole;
    }

    public void setClientRole(boolean clientRole) {
        this.clientRole = clientRole;
    }
}
