package com.mediaPlayer.link.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.gson.Gson
import com.mediaPlayer.link.utils.SessionManager

abstract class BaseFragment<VM : BaseViewModel, Binding : ViewBinding>(private val mViewModelClass: Class<VM>) :
    Fragment() {

    lateinit var mViewModel: VM
    open lateinit var mBinding: Binding
    var gson: Gson = Gson()
    lateinit var sessionManager: SessionManager


    open fun init() {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModelClass()
        sessionManager = SessionManager(requireActivity(), SessionManager.savedSharedPreferences)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = getBinding(inflater, container!!)
        super.onCreateView(inflater, container, savedInstanceState)
        initialize()
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated()
        observer()
    }

    protected abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup): Binding

    protected abstract fun getViewModelClass(): VM

    abstract fun initialize()

    abstract fun viewCreated()

    abstract fun observer()

    open fun refresh() {}

}