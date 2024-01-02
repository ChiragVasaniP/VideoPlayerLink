package com.mediaPlayer.link.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

open class BaseViewModel(app: Application) : AndroidViewModel(app) {
    val isProgress: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
}
