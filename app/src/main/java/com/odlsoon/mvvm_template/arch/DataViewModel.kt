package com.odlsoon.mvvm_template.arch

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odlsoon.mvvm_template.network.responses.DataResponse
import com.odlsoon.mvvm_template.utils.CameraHelper
import com.odlsoon.mvvm_template.utils.FileHelper
import com.odlsoon.mvvm_template.utils.RequestHelper
import kotlinx.coroutines.launch

class DataViewModel(
    private val context: Context,
    private val repository: DataRepository
):ViewModel(){
    private var dataPage = 1
    private val pageSize = 10
    private val site = "stackoverflow"

    val dataList = MutableLiveData(mutableListOf<DataResponse.Item>())
    val dataLocal = MutableLiveData(listOf<Data>())

    fun getDataFromServer(view: View){
        viewModelScope.launch {
            repository.getDataFromServer(dataPage, pageSize, site){response ->
                if(response != null){
                    if(response.has_more){
                        dataPage++

                        val dataListTemp = dataList.value
                        dataListTemp?.addAll(response.items?.toList()!!)
                        dataList.value = dataListTemp
                    }else{
                        Toast.makeText(context, "No more data", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun getDataNullFromServer(){
        viewModelScope.launch {
            repository.getDataNullFromServer { response, code, message ->
                when(code){
                    200 -> {
                        // do something with data response
                    }

                    400 -> {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun insert(data: Data){
        repository.insert(data){isSuccess, id ->

            if(isSuccess){
                // do something
            }

        }
    }

    fun getDataFromLocal(view: View){
        repository.getDataFromLocal{isSuccess, data ->
            if(isSuccess){
                dataLocal.value = data
            }
        }
    }

    fun openCameraPrivateSave(v: View){
        CameraHelper.openCameraPrivateSave(v.context, RequestHelper.OPEN_CAMERA_PRIVATE_REQUEST_CODE)
    }

    fun openCameraPublicSave(v: View){
        CameraHelper.openCameraPublicSave(v.context, RequestHelper.OPEN_CAMERA_PUBLIC_REQUEST_CODE)
    }

    fun openGallery(v: View){
        FileHelper.openGallery(v.context, RequestHelper.OPEN_GALLERY_REQUEST_CODE)
    }
}