package com.odlsoon.mvvm_template.arch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.odlsoon.mvvm_template.R
import com.odlsoon.mvvm_template.network.responses.DataResponse
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.data_item_v1.view.*
import kotlinx.android.synthetic.main.data_item_v2.view.*

class DataRVAdapter(
    private val dataList: MutableLiveData<MutableList<DataResponse.Item>>,
    private val adapterType: AdapterType = AdapterType.TYPE_1
) : RecyclerView.Adapter<DataRVAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (adapterType){
            AdapterType.TYPE_1 -> {
                ViewHolderV1(inflater.inflate(R.layout.data_item_v1, parent, false))
            }

            AdapterType.TYPE_2 -> {
                ViewHolderV2(inflater.inflate(R.layout.data_item_v2, parent, false))
            }
        }
    }

    override fun getItemCount(): Int = dataList.value!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(adapterType){
            AdapterType.TYPE_1 -> {
                val viewHolderV1 = holder as ViewHolderV1
                viewHolderV1.onBind(dataList.value?.get(position)!!)
            }

            AdapterType.TYPE_2 -> {
                val viewHolderV2 = holder as ViewHolderV2
                viewHolderV2.onBind(dataList.value?.get(position)!!)
            }
        }
    }

    open class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    class ViewHolderV1(view: View): ViewHolder(view){
        private val context = view.context
        private val tvName = view.tv_name
        private val ivAva = view.iv_ava

        fun onBind(item: DataResponse.Item){
            tvName.text = item.owner?.display_name

            val multiRequest = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(10, 0)
            )

            Glide.with(context)
                .load(item.owner?.profile_image)
                .apply(RequestOptions.bitmapTransform(multiRequest))
                .into(ivAva)
        }
    }

    class ViewHolderV2(view: View): ViewHolder(view){
        private val context = view.context
        private val tvNameV2 = view.tv_name_v2
        private val ivAvaV2 = view.iv_ava_v2

        fun onBind(item: DataResponse.Item){
            tvNameV2.text = item.owner?.display_name

            Glide.with(context)
                .load(item.owner?.profile_image)
                .into(ivAvaV2)
        }
    }

    enum class AdapterType{
        TYPE_1,
        TYPE_2
    }
}