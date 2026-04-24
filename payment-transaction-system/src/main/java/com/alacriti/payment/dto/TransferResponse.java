package com.alacriti.payment.dto;

import java.math.BigDecimal;

public class TransferResponse {

    private Long transactionId;
    private String status;
    private BigDecimal fromBalance;
    private BigDecimal toBalance;

    public TransferResponse() {}

    public TransferResponse(Long transactionId, String status,
                            BigDecimal fromBalance, BigDecimal toBalance) {
        this.transactionId = transactionId;
        this.status = status;
        this.fromBalance = fromBalance;
        this.toBalance = toBalance;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getFromBalance() {
        return fromBalance;
    }

    public void setFromBalance(BigDecimal fromBalance) {
        this.fromBalance = fromBalance;
    }

    public BigDecimal getToBalance() {
        return toBalance;
    }

    public void setToBalance(BigDecimal toBalance) {
        this.toBalance = toBalance;
    }
}
