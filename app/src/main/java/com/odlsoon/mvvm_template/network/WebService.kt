package com.odlsoon.mvvm_template.network

import com.odlsoon.mvvm_template.network.responses.DataResponse
import com.odlsoon.mvvm_template.network.responses.base.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("answers")
    fun getData(
        @Query("page") page: Int,
        @Query("pagesize") pagesize: Int,
        @Query("site") site: String
    ): Call<DataResponse>

    @GET("null")
    fun getDataNull(): Call<BaseResponse<DataResponse>>

}