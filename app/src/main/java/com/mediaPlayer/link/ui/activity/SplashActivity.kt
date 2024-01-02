package com.mediaPlayer.link.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.mediaPlayer.link.BuildConfig
import com.mediaPlayer.link.R
import com.mediaPlayer.link.core.BaseActivity
import com.mediaPlayer.link.databinding.ActivitySplashBinding
import com.mediaPlayer.link.model.viewmodels.MainViewModel
import com.mediaPlayer.link.utils.NetworkUtils
import com.mediaPlayer.link.utils.SessionManager
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@SuppressLint("CustomSplashScreen")
class SplashActivity :
    BaseActivity<MainViewModel, ActivitySplashBinding>(MainViewModel::class.java) {
    private lateinit var viewModel: MainViewModel


    override fun getBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initViewModel(viewModel: MainViewModel) {
        this.viewModel = viewModel
    }

    override fun observe() {

    }

    override fun onCreateBase() {
        if (NetworkUtils.isNetworkAvailable(this@SplashActivity)) {
            admobLoad()
        } else {
            Toast.makeText(this@SplashActivity, "Connect Internet and Restart Application.", Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun admobLoad() {
        val asyncHttpClient = AsyncHttpClient()
        asyncHttpClient.addHeader("Authorization", "Bearer KAv3FUFSiwXWIzp5Fk90147JqhqtCFJ9Yt45kqMLJc1SCizqehEHcwAEUPOWYxlZ6RpeHS2nFnXtVQ9w")
        asyncHttpClient.get("https://179789a.com/a/api/api/ads_settings", object :
            JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Array<Header>, response: JSONArray) {
                super.onSuccess(statusCode, headers, response)
                try {
                    val jsonObject: JSONObject = response.getJSONObject(2)
                    Log.e(" SplashActivity_Tag", "jsonObject_result: $jsonObject")
                    sessionManager.setString(SessionManager.UPDATEVERSION, jsonObject.getString("facebook_banner_id"))
                    sessionManager.setString(SessionManager.UPDATEURI, jsonObject.getString("facebook_native_id"))
                    sessionManager.setString(SessionManager.ENABLEGOOGLEADMOBID, jsonObject.getString("enable_google_admob_id"))
                    sessionManager.setString(SessionManager.TELEGRAMLINK, jsonObject.getString("facebook_interstitial_id"))
                    sessionManager.setString(SessionManager.QUEREKAADSURI, jsonObject.getString("google_admob_app_id"))
                    sessionManager.setString(SessionManager.TOTALCLICKADS, jsonObject.getString("google_admob_banner_id"))

//                    BaseActivity.TotalClick= sessionManager.getString(SessionManager.TOTALCLICKADS)?.toInt()!!
//                    BaseActivity.TotalClick= 3

                    Log.e(" SplashActivity_Tag", "TelegramLink: " + sessionManager.getString(SessionManager.TELEGRAMLINK))
                    Log.e("SplashActivity_Tag", "UpdateVersion: " + sessionManager.getString(SessionManager.UPDATEVERSION))
                    Log.e("SplashActivity_Tag", "VERSION_NAME: " + BuildConfig.VERSION_NAME)


                    if (sessionManager.getString(SessionManager.UPDATEVERSION) != BuildConfig.VERSION_NAME) {
                        findViewById<View>(R.id.updateDialoge).visibility = View.VISIBLE
                        mBinding?.txtCancel?.setOnClickListener {
                            findViewById<View>(R.id.updateDialoge).visibility = View.GONE
                            val ii = Intent(applicationContext, MainActivity::class.java)
                            startActivity(ii)
                        }
                        mBinding?.txtOk?.setOnClickListener {
                            if (NetworkUtils.isNetworkAvailable(this@SplashActivity)) {
                                try {
                                    val uri: Uri =
                                        Uri.parse(sessionManager.getString(SessionManager.UPDATEURI))
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                } catch (e: Exception) {
                                }
                            } else {
                                Toast.makeText(this@SplashActivity, "Please Check Your Internet Connection And Try Again.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    } else {

                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }, 100)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseString: String, throwable: Throwable) {
                super.onFailure(statusCode, headers, responseString, throwable)
                Log.e("SplashActivity_Tag: ", "onFailure")
            }

            override fun onFinish() {
                super.onFinish()
                Log.e("SplashActivity_Tag: ", "onFinish")
            }
        })


    }

}