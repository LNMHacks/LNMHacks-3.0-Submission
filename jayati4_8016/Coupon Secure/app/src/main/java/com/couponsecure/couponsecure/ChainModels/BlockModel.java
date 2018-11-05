package com.couponsecure.couponsecure.ChainModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BlockModel {

    @SerializedName("index")
    int index;
    @SerializedName("previous_hash" )
    String pre_hash;
    @SerializedName("proof")
    int proof;
    @SerializedName("timestamp")
    Double timestamp;
    @SerializedName("transactions")
    ArrayList<TransactionModel> ind;

    public BlockModel(int index, String pre_hash, int proof, Double timestamp, ArrayList<TransactionModel> ind) {
        this.index = index;
        this.pre_hash = pre_hash;
        this.proof = proof;
        this.timestamp = timestamp;
        this.ind = ind;
    }
}
