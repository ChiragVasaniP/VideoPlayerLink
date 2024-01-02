@file:Suppress("unused", "unused")

package com.mediaPlayer.link.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.mediaPlayer.link.R

object DialogHelper {
    private var dialog: Dialog? = null

    fun showProgressDialog(context: Context) {
        dialog = Dialog(context)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog_progress)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (dialog?.isShowing == false) {
            dialog?.show()
        }
    }

    fun hideProgressDialog() {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }
    }

    fun showError(context: Context, strMessage: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_error)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val txtOk: TextView = dialog.findViewById(R.id.txt_ok)
        val txtMessage: TextView = dialog.findViewById(R.id.txt_message)
        txtMessage.text = strMessage
        txtOk.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    fun showServerError(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_error)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val txtOk: TextView = dialog.findViewById(R.id.txt_ok)
        txtOk.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

}