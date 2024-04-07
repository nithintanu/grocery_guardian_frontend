package com.example.imageupload.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BasicResponse(
    @SerializedName("status")
    @Expose
    val status: Int,

    @SerializedName("message")
    @Expose
    val message: String,


    @SerializedName("output")
    @Expose
    val output: String,
)