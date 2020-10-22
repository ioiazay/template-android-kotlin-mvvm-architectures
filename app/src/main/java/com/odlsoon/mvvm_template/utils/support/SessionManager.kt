package com.odlsoon.mvvm_template.utils.support

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


class SessionManager {
    private var context: Context
    private val PRIVATE_MODE = 0

    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor? = null

    private val ACCOUNT_DATA_KEY = "com.odlsoon.mvvm_template.utils.support.SessionManager.ACCOUNT_DATA_KEY"
    private val ACCOUNT_ID_KEY = "com.odlsoon.mvvm_template.utils.support.ACCOUNT_ID_KEY"

    @SuppressLint("CommitPrefEdits")
    constructor(context: Context) {
        this.context = context
        pref = context.getSharedPreferences(ACCOUNT_DATA_KEY, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun createLoginSession(phone: String) {
        editor?.putString(ACCOUNT_ID_KEY, phone)
        editor?.commit()
    }

    fun clearSession() {
        editor?.clear()
        editor?.commit()
    }

    fun getId(): String?{
        return pref.getString(ACCOUNT_ID_KEY, null)
    }

}