package com.common

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import com.corona_ui.bluetoothlocator.R
import kotlinx.android.synthetic.main.dialog_custom_loader.*

class DialogLoader (context: Context,var description: String) :
    AppCompatDialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_custom_loader)
        setCancelable(false)
        tv_desc.text=description

    }

}