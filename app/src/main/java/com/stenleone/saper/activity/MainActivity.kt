package com.stenleone.saper.activity

import android.graphics.Color
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.stenleone.saper.R
import com.stenleone.saper.adapter.PlayLayAdapter
import com.stenleone.saper.activity.base.BaseActivity
import com.stenleone.saper.interfaces.CallBackFromAdapter
import com.stenleone.saper.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.sqrt

class MainActivity : BaseActivity(R.layout.activity_main), CallBackFromAdapter {

    companion object {
        var isHelmet: Boolean = true
    }

    private var cellCount: Int = 0

    override fun setup() {

        cellCount = intent.extras?.get("params").toString().toInt()
        recycler.layoutManager = GridLayoutManager(
            applicationContext,
            sqrt(cellCount.toDouble()).toInt()
        )
        recycler.adapter = PlayLayAdapter(cellCount, this)
        startTimer()
    }

    override fun setupClicks() {

        helmetButton.setOnClickListener {
            isHelmet = true
            helmetButton.setCardBackgroundColor(Color.RED)
            flagButton.setCardBackgroundColor(ContextCompat.getColor(this, R.color.buttons))
        }

        flagButton.setOnClickListener {
            isHelmet = false
            flagButton.setCardBackgroundColor(Color.RED)
            helmetButton.setCardBackgroundColor(ContextCompat.getColor(this, R.color.buttons))
        }

        bombCountText.text = "Всього бомб: ${(sqrt(intent.extras?.get("params").toString().toDouble())).toInt()}"
    }

    private fun startTimer() {
        Thread {
            Thread.sleep(1000)
            runOnUiThread {
                timerTime.text = (timerTime.text.toString().toInt() + 1).toString()
            }
            startTimer()
        }.start()
    }

    override fun win() {
        showToast("Ви перемогли!")
    }

    override fun clickOnBomb() {
        showToast("Ви програли!")
    }
}