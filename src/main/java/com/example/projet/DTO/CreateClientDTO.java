package com.example.projet.DTO;

import java.util.List;

public class CreateClientDTO {
    private String firstname;
    private String lastname;
    private String externalId;
    private String dateFormat = "dd MMMM yyyy";
    private String locale = "en";
    private boolean active = true;
    private String activationDate = "04 March 2009";
    private String submittedOnDate = "04 March 2009";
    private Integer legalFormId = 1;
    private Integer officeId = 1;
    private List<AdresseDTO> address;

    public String getDateFormat() {
        return dateFormat;
    }

    public String getLocale() {
        return locale;
    }

    public boolean isActive() {
        return active;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public String getSubmittedOnDate() {
        return submittedOnDate;
    }

    public Integer getLegalFormId() {
        return legalFormId;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public List<AdresseDTO> getAddress() {
        return address;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
