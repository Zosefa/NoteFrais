package com.example.projet.DTO;

public class AdresseDTO {
    private Integer addressTypeId = 805;
    private boolean isActive = true;
    private String street = "rapchik";
    private Integer stateProvinceId = 800;
    private Integer countryId = 802;

    public Integer getAddressTypeId() {
        return addressTypeId;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getStreet() {
        return street;
    }

    public Integer getStateProvinceId() {
        return stateProvinceId;
    }

    public Integer getCountryId() {
        return countryId;
    }
}
