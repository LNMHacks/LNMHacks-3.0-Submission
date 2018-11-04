package com.couponsecure.couponsecure.ChainModels;

import com.google.gson.annotations.SerializedName;

public class TransactionModel {


       @SerializedName("amount")
       Double amount;
       @SerializedName("recipient")
       String recipient;
       @SerializedName("sender")
        String sender;

    public TransactionModel(Double amount, String recipient, String sender) {
        this.amount = amount;
        this.recipient = recipient;
        this.sender = sender;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
