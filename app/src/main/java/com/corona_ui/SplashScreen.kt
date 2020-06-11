package com.corona_ui

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.common.GenericPublicVariable
import com.common.GenericPublicVariable.Companion.permission
import com.common.GenericPublicVariable.Companion.remainingPermissions
import com.common.GenericUserFunction.Companion.showApiError
import com.common.GenericUserFunction.Companion.showInternetNegativePopUpSplash
import com.common.GenericUserFunction.Companion.showPerMissNegativePopUp
import com.common.GenericUserFunction.Companion.showUpdateNotification
import com.common.InternetConnection
import com.common.SharedPreference
import com.corona_ui.bluetoothlocator.R
import com.corona_ui.bluetoothlocator.remote.ApiClientPhp
import com.corona_ui.bluetoothlocator.remote.PhpApiInterface
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess



class SplashScreen : AppCompatActivity() {
    var sharedPreference: SharedPreference? = null

    var runnable = Runnable {
        starActivity1()
    }

//    private lateinit var myCustomFont: Typeface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        sharedPreference = SharedPreference(this)
//        myCustomFont =
//            Typeface.createFromAsset(assets, "candyroundbtnbold.ttf")

        if (InternetConnection.checkConnection(this@SplashScreen)) {
            try {
                val phpApiInterface: PhpApiInterface = ApiClientPhp.getClient().create(
                    PhpApiInterface::class.java
                )
                val call3: Call<JsonObject> =
                    phpApiInterface.GetAndroidVersion("SosCubs", "CheckVersion")

                call3.enqueue(object : Callback<JsonObject> {
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        if (!isFinishing) {
                            println("error >>> "+t.localizedMessage)
//                            showInternetNegativePopUpSplash(
//                                this@SplashScreen,
//                                getString(R.string.failureSSApiVerErr)
//                            )
                        }
                    }

                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        try {
                            val result: JsonObject? = response.body()
                            val apiStatus = result!!.get("status")
                            val responseData = result.get("response")
                            if (apiStatus.toString() == "200") {
                                val appVersion =
                                    responseData.asJsonObject.get("app_version").asString
                                val pinfo = packageManager.getPackageInfo(packageName, 0)
                                val versionNumber = pinfo.versionCode
                                if (versionNumber.toString() != appVersion.toString()) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        if (arePermissionsEnabled()) {
                                            val handler = Handler()
                                            handler.postDelayed(runnable, 800)
                                        } else {
                                            requestMultiplePermissions()
                                        }
                                    } else {
                                        val handler = Handler()
                                        handler.postDelayed(runnable, 800)
                                    }
                                } else {
                                    sharedPreference!!.clearSharedPreference()
                                    GenericPublicVariable.CustDialog = Dialog(this@SplashScreen)
                                    GenericPublicVariable.CustDialog.setContentView(R.layout.newfeature_custom_popup)
                                    var btnOk: Button =
                                        GenericPublicVariable.CustDialog.findViewById(R.id.btnCustomDialogAccept) as Button
                                    var tvMsg: TextView = GenericPublicVariable.CustDialog.findViewById(R.id.tvMsgCustomDialog) as TextView
                                    tvMsg.text = appVersion
                                    GenericPublicVariable.CustDialog.setCancelable(false)
                                    btnOk.setOnClickListener {
                                        GenericPublicVariable.CustDialog.dismiss()
                                        var intent =  Intent(android.content.Intent.ACTION_VIEW);

                                        //Copy App URL from Google Play Store.
                                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.dmims.dmims"))
//                                        intent.setData(Uri.parse(response.body()!!.app_url))

                                        startActivity(intent);
                                        exitProcess(-1)
                                    }
                                    GenericPublicVariable.CustDialog.window!!.setBackgroundDrawable(
                                        ColorDrawable(
                                            Color.TRANSPARENT
                                        )
                                    )
                                    GenericPublicVariable.CustDialog.show()
                                }
                            }
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            if (!isFinishing) {
                                showApiError(
                                    this@SplashScreen,
                                    "Sorry for inconvenience\nServer seems to be busy,\nPlease try after some time."
                                )
                            }
                        }
                    }
                })
            } catch (ex: Exception) {
                if (!isFinishing) {
                    ex.printStackTrace()
                    showApiError(
                        this,
                        "Sorry for inconvenience\nServer seems to be busy,\nPlease try after some time."
                    )
                }
            }
        } else {
            if (!isFinishing) {
                showInternetNegativePopUpSplash(
                    this@SplashScreen,
                    getString(R.string.failureNoInternetErr)
                )
            }
        }
    }

    private fun starActivity1() {

//       var mypref = getSharedPreferences("mypref", Context.MODE_PRIVATE)
//
//
//        var UserRole =
//            mypref!!.getString("UserRole", null)
//
//        println("mypref " + mypref.toString())



        var sharedEmail=sharedPreference?.getValueString("Email")

        if (sharedEmail!=null) {
            val intent = Intent(
                this@SplashScreen,
                DashboardActivity::class.java
            )
            startActivity(intent)

        } else {
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
//            var intent = Intent(this@SplashScreen,ActivityContacts::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun arePermissionsEnabled(): Boolean {
        for (permission: String in permission) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun requestMultiplePermissions() {
        for (permission: String in permission) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                remainingPermissions.add(permission)
        }
        requestPermissions(
            remainingPermissions.toArray(arrayOf("" + remainingPermissions.size)),
            101
        )
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(permissions[i])) {
                        if (!isFinishing) {
                            showPerMissNegativePopUp(
                                this@SplashScreen,
                                getString(R.string.failureStorageErr)
                            )
                        }
                    }
                    return
                } else {
                    val handler = Handler()
                    handler.postDelayed(runnable, 1300)
                }
            }

        }
    }
}
