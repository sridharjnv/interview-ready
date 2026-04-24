package com.alacriti.payment.dto;

public class CreateAccountRequest {

    private String accountHolderName;
    private String email;

    public CreateAccountRequest() {}

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
