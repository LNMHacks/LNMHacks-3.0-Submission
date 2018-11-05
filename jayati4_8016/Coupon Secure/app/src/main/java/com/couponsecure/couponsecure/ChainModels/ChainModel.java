package com.couponsecure.couponsecure.ChainModels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChainModel {

    @SerializedName("chain")
    ArrayList<BlockModel> blocks;

    @SerializedName("length")
    int length;

}

