package com.example.projet.DTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransfersBody {
    private Integer fromOfficeId = 1;
    private Integer fromClientId = 1;
    private Integer fromAccountType = 2;
    private Integer fromAccountId = 1;
    private Integer toOfficeId = 1;
    private Integer toClientId = 2;
    private Integer toAccountType = 2;
    private Integer toAccountId;
    private String dateFormat = "dd MMMM yyyy";
    private String locale = "en";
    private String transferDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MM yyyy"));
    private String transferAmount;
    private String transferDescription = " Transfert note Frais ";

    public Integer getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Integer toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Integer getFromOfficeId() {
        return fromOfficeId;
    }

    public void setFromOfficeId(Integer fromOfficeId) {
        this.fromOfficeId = fromOfficeId;
    }

    public Integer getFromClientId() {
        return fromClientId;
    }

    public void setFromClientId(Integer fromClientId) {
        this.fromClientId = fromClientId;
    }

    public Integer getFromAccountType() {
        return fromAccountType;
    }

    public void setFromAccountType(Integer fromAccountType) {
        this.fromAccountType = fromAccountType;
    }

    public Integer getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Integer fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public Integer getToOfficeId() {
        return toOfficeId;
    }

    public void setToOfficeId(Integer toOfficeId) {
        this.toOfficeId = toOfficeId;
    }

    public Integer getToClientId() {
        return toClientId;
    }

    public void setToClientId(Integer toClientId) {
        this.toClientId = toClientId;
    }

    public Integer getToAccountType() {
        return toAccountType;
    }

    public void setToAccountType(Integer toAccountType) {
        this.toAccountType = toAccountType;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferDescription() {
        return transferDescription;
    }

    public void setTransferDescription(String transferDescription) {
        this.transferDescription = transferDescription;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }
}
