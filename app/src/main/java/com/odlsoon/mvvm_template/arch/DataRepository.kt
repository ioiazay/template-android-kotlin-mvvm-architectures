package com.odlsoon.mvvm_template.arch

import android.os.AsyncTask
import com.odlsoon.mvvm_template.network.WebClient
import com.odlsoon.mvvm_template.network.responses.DataResponse
import com.odlsoon.mvvm_template.network.responses.base.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepository(
    private val dataDao: DataDao
) {

    fun getDataFromServer(
        page: Int,
        pageSize: Int,
        site: String,
        callback: (response: DataResponse?) -> Unit) {

        WebClient.instance.getService().getData(page, pageSize, site).enqueue(object : Callback<DataResponse>{
            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                callback(null)
            }

            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                if(response.isSuccessful && response.body() != null){
                    callback(response.body())
                }else{
                    callback(null)
                }
            }
        })
    }


    fun getDataNullFromServer(callback: (response: DataResponse?, code: Int?, message: String?) -> Unit){
        WebClient.instance.getService().getDataNull().enqueue(object : Callback<BaseResponse<DataResponse>>{
            override fun onFailure(call: Call<BaseResponse<DataResponse>>, t: Throwable) {
                callback(null, null, t.toString())
            }

            override fun onResponse(
                call: Call<BaseResponse<DataResponse>>,
                response: Response<BaseResponse<DataResponse>>
            ) {
                if(response.isSuccessful && response.body() != null && response.body()?.data != null){
                    callback(response.body()?.data, response.body()?.code!!, response.body()?.message)
                }else{
                    callback(null, response.body()?.code!!, response.body()?.message)
                }
            }
        })
    }

    fun insert(data: Data, callback: (isSuccess: Boolean, id: Long?) -> Unit){
        InsertAsync(dataDao, data, callback).execute(null)
    }

    fun getDataFromLocal(
        callback: (isSuccess: Boolean, data: List<Data>?) -> Unit
    ){
        GetDataFromLocalAsync(dataDao, callback).execute(null)
    }

    private class InsertAsync(
        private val dataDao: DataDao,
        private val data: Data,
        private val callback: (isSuccess: Boolean, id: Long?) -> Unit
    ): AsyncTask<Void, Void, Long?>(){

        override fun onPostExecute(result: Long?) {
            super.onPostExecute(result)
            callback(true, result!!)
        }

        override fun doInBackground(vararg params: Void?): Long? {
            var id: Long? = null

            try {
                id = dataDao.insert(data)
            }catch (e: java.lang.Exception){
                callback(false, null)
            }

            return id
        }
    }

    private class GetDataFromLocalAsync(
        private val dataDao: DataDao,
        private val callback: (isSuccess: Boolean, data: List<Data>?) -> Unit
    ): AsyncTask<Void, Void, List<Data>>(){

        override fun onPostExecute(result: List<Data>?) {
            super.onPostExecute(result)
            callback(true, result)
        }

        override fun doInBackground(vararg params: Void?): List<Data> {
            var data : List<Data> = listOf()

            try{
                data = dataDao.getAllData()
            }catch (e: Exception){
                callback(false, null)
            }

            return data
        }
    }
}