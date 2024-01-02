package com.mediaPlayer.link.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.Window
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mediaPlayer.link.BuildConfig
import com.mediaPlayer.link.R
import com.mediaPlayer.link.databinding.DialogInstallAppBinding
import java.io.File
import java.io.FileOutputStream


object Utils {

    /**
     * 1.getGlideRequest
     * 2.circularProgressBar
     * Used For Glide Request
     * Created By Custom
     * */
    fun getGlideRequest(context: Context): RequestOptions {
        return RequestOptions()
            .placeholder(circularProgressBar(context))
            .error(R.drawable.ic_not_found)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .dontAnimate()
            .dontTransform()
    }

    private fun circularProgressBar(context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.apply {
            strokeWidth = 8f
            centerRadius = 30f
            setColorSchemeColors(Color.WHITE)
            start()
        }

        return circularProgressDrawable
    }

    /**
     *1.openVideoPlayer
     *2.showInMarket
     * Used For Open Over MB Player App If not Installed Then Go to ShowInMarket To Install app
     **/

    fun openVideoPlayer(context: Context, uri: String) {

        try {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "video/*"
            intent.type = "text/plain"
            intent.setPackage(Constants.VIDEO_PLAYER)
            intent.putExtra("link", uri)
            context.startActivity(intent)
        } catch (e: Exception) {
            showInMarket(context)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showInMarket(context: Context) {
        val mBinding = DialogInstallAppBinding.inflate(LayoutInflater.from(context))
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(mBinding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mBinding.txtMessage.text =
            "Please Install This Video Player To Play Movies.(This is one time process)"
        mBinding.txtDoItLatter.setOnClickListener { dialog.dismiss() }
        mBinding.txtInstall.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${Constants.VIDEO_PLAYER}"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        dialog.show()
    }


    /**
     * Share Intent To Another App Using Cache */

    fun shareImageAndText(bitmap: Bitmap, context: Context, EXTRA_TEXT: String = "") {
        val uri = getImageToShare(bitmap, context)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_TEXT, "Free download and watch all movies from \n $EXTRA_TEXT")
        intent.type = "image/png"
        context.startActivity(Intent.createChooser(intent, "Share Via"))
    }

    private fun getImageToShare(bitmap: Bitmap, context: Context): Uri? {
        val imagefolder = File(context.cacheDir, "images")
        var uri: Uri? = null
        try {
            imagefolder.mkdirs()
            val file = File(imagefolder, "shared_image.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            uri =
                FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
        } catch (e: Exception) {
            Toast.makeText(context, "" + e.message, Toast.LENGTH_LONG).show()
        }
        return uri
    }


}