package com.example.projet.DTO.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SavingsAccountResponse {
    @JsonProperty("savingsAccounts")
    private SavingsAccount[] savingsAccounts;

    public SavingsAccount[] getSavingsAccounts() {
        if (savingsAccounts == null) {
            return new SavingsAccount[]{new SavingsAccount()};
        }
        return savingsAccounts;
    }

    public void setSavingsAccounts(SavingsAccount[] savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
    }

    public static class SavingsAccount {
        @JsonProperty("accountBalance")
        private double accountBalance = 0;

        @JsonProperty("id")
        private Integer id;

        public SavingsAccount() {
            this.accountBalance = 0;
        }


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public double getAccountBalance() {
            return accountBalance;
        }

        public void setAccountBalance(double accountBalance) {
            this.accountBalance = accountBalance;
        }

    }
}

