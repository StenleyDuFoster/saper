package com.stenleone.saper.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.stenleone.saper.R

@SuppressLint("InflateParams")
fun Context.showToast(
    text: String,
    customView: View = LayoutInflater.from(this).inflate(R.layout.custom_toast_lay, null),
    duration: Int = Toast.LENGTH_SHORT) {

    val toast = Toast(this)
    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
    toast.duration = duration
    customView.findViewById<TextView>(R.id.text)?.text = text
    toast.view = customView
    toast.show()
}