package com.couponsecure.couponsecure.ResponseModels;

import com.couponsecure.couponsecure.ChainModels.TransactionModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MiningResponseModel {

    @SerializedName("index")
    int index;

    @SerializedName("message")
    String message;

    @SerializedName("previous_hash")
    String previousHash;

    @SerializedName("proof")
    int proof;

    @SerializedName("transactions")
    ArrayList<TransactionModel> transactions;


}
