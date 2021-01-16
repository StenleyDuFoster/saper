package com.stenleone.saper.activity

import android.content.Intent
import android.net.Uri
import com.stenleone.saper.R
import com.stenleone.saper.activity.base.BaseActivity
import com.stenleone.saper.fragment.GameCreateDialog
import com.stenleone.saper.interfaces.CallBackFromDialogFragment
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : BaseActivity(R.layout.activity_menu), CallBackFromDialogFragment {

    override fun setupClicks() {

        playButton.setOnClickListener {
            val dialog = GameCreateDialog(this)
            dialog.show(supportFragmentManager, "GameCreateDialog")
        }

        instButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/lean_andriy/"))
            startActivity(browserIntent)
        }

        exitButton.setOnClickListener {
            finish()
        }
    }

    override fun loadMainActivity(cellsCount: Int) {
        loadActivity(MainActivity::class.java, intentParams = cellsCount)
    }
}