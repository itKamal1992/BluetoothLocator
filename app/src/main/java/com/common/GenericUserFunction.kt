package com.common

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.corona_ui.bluetoothlocator.R
import kotlin.system.exitProcess

class GenericUserFunction {
    var arrayList:ArrayList<String>?=null
    lateinit var tv_new: TextView
    companion object {
        fun DisplayToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }


        fun showPositivePopUp(
            context: Context,
            msg: String
        ) {


            GenericPublicVariable.CustDialog = Dialog(context)
            GenericPublicVariable.CustDialog.setContentView(R.layout.positive_custom_popup)
            var ivPosClose1: ImageView =
                GenericPublicVariable.CustDialog.findViewById(R.id.ivCustomDialogPosClose) as ImageView
            var btnOk: Button = GenericPublicVariable.CustDialog.findViewById(R.id.btnCustomDialogAccept) as Button
            var tvMsg: TextView = GenericPublicVariable.CustDialog.findViewById(R.id.tvMsgCustomDialog) as TextView
            tvMsg.text = msg
            GenericPublicVariable.CustDialog.setCancelable(false)
            btnOk.setOnClickListener {
                GenericPublicVariable.CustDialog.dismiss()

            }
            ivPosClose1.setOnClickListener {
                GenericPublicVariable.CustDialog.dismiss()

            }

            GenericPublicVariable.CustDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            GenericPublicVariable.CustDialog.show()



        }

        fun showNegativePopUp(
            context: Context,
            msg: String
        ) {
            GenericPublicVariable.CustDialog = Dialog(context)
            GenericPublicVariable.CustDialog.setContentView(R.layout.negative_custom_popup)
//            var ivNegClose1: ImageView =
//                GenericPublicVariable.CustDialog.findViewById(R.id.ivCustomDialogNegClose) as ImageView
            var btnOk: Button = GenericPublicVariable.CustDialog.findViewById(R.id.btnCustomDialogAccept) as Button
            var tvMsg: TextView = GenericPublicVariable.CustDialog.findViewById(R.id.tvMsgCustomDialog) as TextView
            tvMsg.text = msg
//            GenericPublicVariable.CustDialog.setCancelable(false)
            btnOk.setOnClickListener {
                GenericPublicVariable.CustDialog.dismiss()
            }
//            ivNegClose1.setOnClickListener {
//                GenericPublicVariable.CustDialog.dismiss()
//            }
            GenericPublicVariable.CustDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            GenericPublicVariable.CustDialog.show()
        }

        fun showPerMissNegativePopUp(
            context: Context,
            msg: String
        ) {
            GenericPublicVariable.CustDialog = Dialog(context)
            GenericPublicVariable.CustDialog.setContentView(R.layout.negative_custom_popup)
            var btnOk: Button = GenericPublicVariable.CustDialog.findViewById(R.id.btnCustomDialogAccept) as Button
            var tvMsg: TextView = GenericPublicVariable.CustDialog.findViewById(R.id.tvMsgCustomDialog) as TextView
            tvMsg.text = msg
            GenericPublicVariable.CustDialog.setCancelable(false)
            btnOk.setOnClickListener {
                exitProcess(-1)
                // GenericPublicVariable.CustDialog.dismiss()
            }
            GenericPublicVariable.CustDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            GenericPublicVariable.CustDialog.show()
        }

        fun showInternetNegativePopUp(
            context: Context,
            msg: String
        ) {
            GenericPublicVariable.CustDialog = Dialog(context)
            GenericPublicVariable.CustDialog.setContentView(R.layout.negative_custom_popup)
            var btnOk: Button = GenericPublicVariable.CustDialog.findViewById(R.id.btnCustomDialogAccept) as Button
            var tvMsg: TextView = GenericPublicVariable.CustDialog.findViewById(R.id.tvMsgCustomDialog) as TextView
            tvMsg.text = msg
            GenericPublicVariable.CustDialog.setCancelable(false)
            btnOk.setOnClickListener {
                //                exitProcess(-1)
                GenericPublicVariable.CustDialog.dismiss()
                // GenericPublicVariable.CustDialog.dismiss()
            }
            GenericPublicVariable.CustDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            GenericPublicVariable.CustDialog.show()
        }

        fun showInternetNegativePopUpSplash(
            context: Context,
            msg: String
        ) {
            GenericPublicVariable.CustDialog = Dialog(context)
            GenericPublicVariable.CustDialog.setContentView(R.layout.negative_custom_popup)
//            var ivNegClose1: ImageView =
//                GenericPublicVariable.CustDialog.findViewById(R.id.ivCustomDialogNegClose) as ImageView
            var btnOk: Button = GenericPublicVariable.CustDialog.findViewById(R.id.btnCustomDialogAccept) as Button
            var tvMsg: TextView = GenericPublicVariable.CustDialog.findViewById(R.id.tvMsgCustomDialog) as TextView
            tvMsg.text = msg
            GenericPublicVariable.CustDialog.setCancelable(false)
//            ivNegClose1.setOnClickListener {
//                exitProcess(-1)
////                GenericPublicVariable.CustDialog.dismiss()
//                // GenericPublicVariable.CustDialog.dismiss()
//            }
            btnOk.setOnClickListener {
                exitProcess(-1)
//                GenericPublicVariable.CustDialog.dismiss()
                // GenericPublicVariable.CustDialog.dismiss()
            }

            GenericPublicVariable.CustDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            GenericPublicVariable.CustDialog.show()
        }

        fun showUpdateNotification(
            context: Context,
            msg: String
        ) {


            GenericPublicVariable.CustDialog = Dialog(context)
            GenericPublicVariable.CustDialog.setContentView(R.layout.newfeature_custom_popup)
            var btnOk: Button = GenericPublicVariable.CustDialog.findViewById(R.id.btnCustomDialogAccept) as Button
            var tvMsg: TextView = GenericPublicVariable.CustDialog.findViewById(R.id.tvMsgCustomDialog) as TextView
            tvMsg.text = msg
            /*     var sizeint=aarray.size
                 for (i in 1..sizeint - 1) {


                     tvMsg.text = aarray[i]
                 }*/
            GenericPublicVariable.CustDialog.setCancelable(false)
            btnOk.setOnClickListener {
                exitProcess(-1)
//                GenericPublicVariable.CustDialog.dismiss()
                // GenericPublicVariable.CustDialog.dismiss()

            }
            GenericPublicVariable.CustDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            GenericPublicVariable.CustDialog.show()
        }

        fun showApiError(
            context: Context,
            msg: String
        ) {
            GenericPublicVariable.CustDialog = Dialog(context)
            GenericPublicVariable.CustDialog.setContentView(R.layout.negative_custom_popup)
            var btnOk: Button = GenericPublicVariable.CustDialog.findViewById(R.id.btnCustomDialogAccept) as Button
            var tvMsg: TextView = GenericPublicVariable.CustDialog.findViewById(R.id.tvMsgCustomDialog) as TextView
            tvMsg.text = msg
            GenericPublicVariable.CustDialog.setCancelable(false)
            btnOk.setOnClickListener {
                GenericPublicVariable.CustDialog.dismiss()
            }
            GenericPublicVariable.CustDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            GenericPublicVariable.CustDialog.show()
        }

        fun showOopsError(
            context: Context,
            msg: String
        ) {
            GenericPublicVariable.CustDialog = Dialog(context)
            GenericPublicVariable.CustDialog.setContentView(R.layout.api_oops_custom_popup)

            var btnOk: Button = GenericPublicVariable.CustDialog.findViewById(R.id.btnCustomDialogAccept) as Button
            var tvMsg: TextView = GenericPublicVariable.CustDialog.findViewById(R.id.tvMsgCustomDialog) as TextView
            tvMsg.text = msg
            GenericPublicVariable.CustDialog.setCancelable(false)
            btnOk.setOnClickListener {
                GenericPublicVariable.CustDialog.dismiss()

            }

            GenericPublicVariable.CustDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            GenericPublicVariable.CustDialog.show()


        }






    }
}