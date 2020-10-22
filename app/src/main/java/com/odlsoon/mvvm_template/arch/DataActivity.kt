package com.odlsoon.mvvm_template.arch

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.odlsoon.mvvm_template.R
import com.odlsoon.mvvm_template.databinding.DataActBinding
import com.odlsoon.mvvm_template.utils.CameraHelper
import com.odlsoon.mvvm_template.utils.RequestHelper
import kotlinx.android.synthetic.main.data_act.*

class DataActivity : AppCompatActivity() {

    private val viewModel: DataViewModel by viewModels {
        Injector.provideDataViewModelFactory(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        initRecyclerView()
        initLiveData()
    }

    private fun initDataBinding(){
        val binding = DataBindingUtil.setContentView<DataActBinding>(this, R.layout.data_act)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initRecyclerView(){
        rv_data.apply {
            adapter = DataRVAdapter(viewModel.dataList!!, DataRVAdapter.AdapterType.TYPE_1)
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@DataActivity)
            setHasFixedSize(true)
        }

        nested.setOnScrollChangeListener { v: NestedScrollView?, _, scrollY, _, oldScrollY ->
            if(v?.getChildAt(v.childCount - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) && scrollY > oldScrollY) {
                    viewModel.getDataFromServer(v)
                }
            }
        }
    }

    private fun initLiveData(){
        viewModel.dataList.observe(this, Observer {
            Toast.makeText(this, "size item network = ${it?.size}", Toast.LENGTH_SHORT).show()
            rv_data.adapter?.notifyDataSetChanged()
        })

        viewModel.dataLocal.observe(this, Observer {
            Toast.makeText(this, "size item local = ${it?.size}", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){
            Activity.RESULT_OK -> {
                when(requestCode){
                    RequestHelper.OPEN_CAMERA_PRIVATE_REQUEST_CODE -> {
                        val bitmap = CameraHelper.getImageBitmap()
                        val uri = CameraHelper.getImageUri()
                        val path = CameraHelper.getImagePath()

                        Glide.with(this).load(bitmap).into(iv_camera)
                    }

                    RequestHelper.OPEN_CAMERA_PUBLIC_REQUEST_CODE -> {
                        Log.d("TAG", "DATA: " + data?.data)
                        CameraHelper.addImageToGallery(this)
                    }

                    RequestHelper.OPEN_GALLERY_REQUEST_CODE -> {
                        val uri = data?.data
                        Glide.with(this).load(uri).into(iv_camera)
                    }


                }
            }

            Activity.RESULT_CANCELED -> {

            }
        }

    }

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, DataActivity::class.java))
        }
    }
}
