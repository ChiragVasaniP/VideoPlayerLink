package com.mediaPlayer.link.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent


object QuerekaAds {

    fun openChromeCustomTabPrediction(context: Context?, getquereka_link: String) {
        try {
            if (context?.let { isAppInstalled(it, "com.android.chrome") } == true) {
                val builder = CustomTabsIntent.Builder()
                val coolorInt = Color.parseColor("#448AFF")
                builder.setToolbarColor(coolorInt)
                val customTabsIntent = builder.build()
                customTabsIntent.intent.setPackage("com.android.chrome")
                customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                customTabsIntent.launchUrl(context, Uri.parse(getquereka_link))
            } else {
                val builder = CustomTabsIntent.Builder()
                val coolorInt = Color.parseColor("#448AFF")
                builder.setToolbarColor(coolorInt)
                val customTabsIntent = builder.build()
                customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                customTabsIntent.launchUrl(context!!, Uri.parse(getquereka_link))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun isAppInstalled(context: Context, uri: String?): Boolean {
        val pm = context.packageManager
        try {
            pm.getPackageInfo(uri!!, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return false
    }
}