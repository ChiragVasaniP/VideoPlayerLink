package com.mediaPlayer.link.core

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.mediaPlayer.link.utils.DialogHelper
import com.mediaPlayer.link.utils.QuerekaAds
import com.mediaPlayer.link.utils.SessionManager

abstract class BaseActivity<VM : BaseViewModel, Binding : ViewBinding?>(private val mViewModelClass: Class<VM>) :
    AppCompatActivity()/*, ConnectivityChangeReceiver.OnConnectivityChangedListener*/ {


    companion object {
        var TotalClick = 1
    }

    @JvmField
    protected var mBinding: Binding? = null
    var gson: Gson = Gson()
    lateinit var sessionManager: SessionManager

    var querekaAdsUri = ""


    private val viewModel by lazy {
        ViewModelProvider(this)[mViewModelClass]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get FCM_TOKEN
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        initBinding()
        setContentView(mBinding!!.root)
        initViewModel(viewModel)
        sessionManager = SessionManager(this@BaseActivity, SessionManager.savedSharedPreferences)


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result

            Log.d("TAG_Token", token)

        })

        viewModel.isProgress.observe(this@BaseActivity) { isProgress ->
            if (isProgress != null) {
                if (isProgress) {
                    DialogHelper.showProgressDialog(this@BaseActivity)
                } else
                    DialogHelper.hideProgressDialog()
            }

        }
        //Init
        onCreateBase()
        //Use For Methods

        //Use for ViewModelObserver
        observe()


    }

    open fun showQurekaAds(context: Context) {
        if (TotalClick == sessionManager.getString(SessionManager.TOTALCLICKADS)?.toInt()!!) {
            TotalClick = 1
            if (sessionManager.getString(SessionManager.ENABLEGOOGLEADMOBID).toString() == "1") {
                QuerekaAds.openChromeCustomTabPrediction(
                    context, sessionManager.getString(SessionManager.QUEREKAADSURI).toString()
                )
            }
        } else {
            TotalClick++
        }

    }

    protected abstract fun getBinding(): Binding

    abstract fun initViewModel(viewModel: VM)

    private fun initBinding() {
        mBinding = getBinding()
    }

    abstract fun onCreateBase()


    abstract fun observe()


}