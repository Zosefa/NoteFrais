package com.example.projet.DTO.response;

public class ClientResponseDTO {
    private int officeId;
    private int clientId;
    private int resourceId;
    private String resourceExternalId;

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceExternalId() {
        return resourceExternalId;
    }

    public void setResourceExternalId(String resourceExternalId) {
        this.resourceExternalId = resourceExternalId;
    }
}
