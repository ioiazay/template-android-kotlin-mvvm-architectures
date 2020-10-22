package com.odlsoon.mvvm_template.network.responses.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

class BaseResponse <T> {

    @SerializedName("message") @Expose var message: String = ""
    @SerializedName("code") @Expose var code: Int = 0
    @SerializedName("data") @Expose var data: T? = null
    @SerializedName("results") @Expose var results: ResponseBody? = null

}