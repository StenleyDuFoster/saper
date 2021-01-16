package com.stenleone.saper.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.stenleone.saper.R
import com.stenleone.saper.interfaces.CallBackFromDialogFragment
import kotlinx.android.synthetic.main.game_creater_dialog.*


class GameCreateDialog(private val callBack: CallBackFromDialogFragment) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.game_creater_dialog, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        removeBackgroundWindowColor()
        setupClick()
    }

    private fun removeBackgroundWindowColor() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setupClick() {

        buttonAccept.setOnClickListener {
            val inputValue = textInputEditText.text.toString().toInt()
            callBack.loadMainActivity(inputValue)
        }

        buttonCancel.setOnClickListener {
            dialog?.hide()
        }
    }
}