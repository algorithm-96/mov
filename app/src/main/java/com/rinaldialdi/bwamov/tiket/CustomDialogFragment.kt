package com.rinaldialdi.bwamov.tiket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.rinaldialdi.bwamov.R
import kotlinx.android.synthetic.main.custom_dialog.view.*

class CustomDialogFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.custom_dialog, container, false)

        rootView.btn_close.setOnClickListener {
            dismiss()
        }
        return rootView
    }
}