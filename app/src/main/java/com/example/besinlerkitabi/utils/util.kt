package com.example.besinlerkitabi.utils

import android.content.Context
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.besinlerkitabi.R

fun ImageView.gorselIndir(placeHolder : CircularProgressDrawable, url : String){

    val options = RequestOptions().placeholder(placeHolder).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}
fun createPlaceHolder(context : Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 35f
        start()
    }

}