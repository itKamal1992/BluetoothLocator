package com.corona_ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.api_request.RegisterRequest
import com.api_response.RegisterResponse
import com.common.DialogLoader
import com.common.GenericUserFunction
import com.corona_ui.bluetoothlocator.R
import com.corona_ui.bluetoothlocator.remote.ApiClientPhp
import com.corona_ui.bluetoothlocator.remote.PhpApiInterface
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class SignupActivity : AppCompatActivity() {
    private var dialog: DialogLoader? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        dialog = DialogLoader(
            this,
            "Please wait,\nwhile we register yourself"
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        btn_reg.setOnClickListener {
            if (et_name.text.toString().length < 1) {
                et_name.error = "please enter Your Name"
                return@setOnClickListener
            }

            if (et_email.text.toString().length < 5) {
                et_email.error = "please enter valid E-mail"
                return@setOnClickListener
            }

            val phpApiInterface: PhpApiInterface = ApiClientPhp.getClient().create(
                PhpApiInterface::class.java
            )
            val callRegister: Call<RegisterResponse> =
                phpApiInterface.RegisterUser(
                    RegisterRequest(
                        et_email.text.toString(),
                        et_name.text.toString(),
                        "",
                        ""
                    )
                )

            try {
                dialog?.show()

                var registerResult = callRegister.enqueue(object : Callback<RegisterResponse> {
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        dialog?.dismiss()
                        GenericUserFunction.DisplayToast(
                            this@SignupActivity,
                            t.message.toString()
                        )
                        println(">>> " + t.message)
                    }

                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        dialog?.dismiss()
                        if (response.isSuccessful) {
                            println(">>> " + response.body()?.response)
                            GenericUserFunction.DisplayToast(
                                this@SignupActivity,
                                response.body()?.response.toString()
                            )
                        } else {
                            println(">>> " + response.message())
                            GenericUserFunction.DisplayToast(
                                this@SignupActivity,
                                response.message().toString()
                            )
                        }
                    }

                })
            } catch (ex: Exception) {
                GenericUserFunction.DisplayToast(this, ex.message.toString())
                dialog?.dismiss()
            }


        }
    }
}
