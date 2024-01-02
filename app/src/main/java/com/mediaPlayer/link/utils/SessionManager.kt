package com.mediaPlayer.link.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context, sharedName: String?) {

    private var sharedPref: SharedPreferences
    private var mEditor: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE)
        mEditor = sharedPref.edit()
    }


    fun getBoolean(key: String?): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun getString(key: String?): String? {
        return sharedPref.getString(key, "")
    }

    fun setBoolean(key: String?, value: Boolean?) {
        mEditor.putBoolean(key, value!!)
        mEditor.commit()
    }

    fun setString(key: String?, value: String?) {
        mEditor.putString(key, value)
        mEditor.commit()
    }

    fun logOut() {
        mEditor.clear()
        mEditor.commit()
    }

    companion object {
        var savedSharedPreferences = "movieLinkSharedPreferences"
        const val Privacy = "https://privacypolicycoolgame.blogspot.com/2022/07/privacy-policy.html"
        const val P_Name = "P_Name"
        const val UPDATEVERSION = "UpdateVersion"
        const val UPDATEURI = "UpdateUri"
        const val ENABLEGOOGLEADMOBID = "enableGoogleAdmobId "
        const val TELEGRAMLINK = "TelegramLink"
        const val QUEREKAADSURI = "querekaAdsUri"
        const val TOTALCLICKADS = "totalClickAds"

    }


}