package com.smartPackaging.selvawa.game

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.smartPackaging.selvawa.game.R

class AvisoDialogFragment(
    private val mensaje: String,
    private val lottieRes: Int = R.raw.celebration
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_popup, null)
        view.findViewById<TextView>(R.id.textViewMensaje).text = mensaje
        view.findViewById<LottieAnimationView>(R.id.lottieIcon).setAnimation(lottieRes)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }
}