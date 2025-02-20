package com.example.back.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SavingsAccountResponse {
    @JsonProperty("savingsAccounts")
    private SavingsAccount[] savingsAccounts;

    public SavingsAccount[] getSavingsAccounts() {
        return savingsAccounts;
    }

    public void setSavingsAccounts(SavingsAccount[] savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
    }

    public static class SavingsAccount {
        @JsonProperty("accountBalance")
        private double accountBalance = 0;

        public double getAccountBalance() {
            return accountBalance;
        }

        public void setAccountBalance(double accountBalance) {
            this.accountBalance = accountBalance;
        }
    }
}
