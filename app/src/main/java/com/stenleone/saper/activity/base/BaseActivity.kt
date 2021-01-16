package com.stenleone.saper.activity.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(private val layId: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layId)
        setup()
        setupClicks()
    }

    protected open fun setup() {}
    protected abstract fun setupClicks()

    fun <T> loadActivity(activityClass: Class<T>, intentParams: Int = 0, finishThisActivity: Boolean = false) {

        val intent = Intent(applicationContext, activityClass)
        if (intentParams != 0) {
            intent.putExtra("params", intentParams)
        }

        startActivity(intent)

        if (finishThisActivity) {
            finish()
        }

    }
}