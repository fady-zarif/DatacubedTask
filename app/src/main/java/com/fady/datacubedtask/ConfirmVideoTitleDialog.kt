package com.fady.datacubedtask

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.confirm_title_dialog.*

class ConfirmVideoTitleDialog(
    context: Context,
    private val click: (view: View) -> Unit
) : AlertDialog(context, R.style.CustomDialogStyle) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirm_title_dialog)
        btnCancel.setOnClickListener {
            this.dismiss()
        }
        btnReplace.setOnClickListener(click)
    }
}