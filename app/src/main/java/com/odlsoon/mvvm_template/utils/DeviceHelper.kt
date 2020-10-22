package com.odlsoon.mvvm_template.utils

import android.app.Activity
import android.graphics.Point

object DeviceHelper {

    fun getDeviceWidth(activity: Activity):Int{
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        return size.x
    }


}