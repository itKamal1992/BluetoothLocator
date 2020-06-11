package com.corona_ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.api_request.LoginRequest
import com.api_request.RegisterRequest
import com.api_response.LoginResponse
import com.api_response.RegisterResponse
import com.common.DialogLoader
import com.common.GenericUserFunction
import com.common.SharedPreference
import com.corona_ui.bluetoothlocator.R
import com.corona_ui.bluetoothlocator.remote.ApiClientPhp
import com.corona_ui.bluetoothlocator.remote.PhpApiInterface
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.et_email
import kotlinx.android.synthetic.main.activity_signup.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var callbackManager: CallbackManager? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    var RC_SIGN_IN = 200
    var TAG = "TAG"
    private val EMAIL = "email"
    private var dialog: DialogLoader? = null
    val phpApiInterface: PhpApiInterface = ApiClientPhp.getClient().create(
        PhpApiInterface::class.java
    )
    private var auth: FirebaseAuth? = null
    val channelId = "01"
    val channel_Name="AppName"//getString(R.string.app_name)
    val channel_Desc="In App Notification for Alerts"//getString(R.string.app_name)+" Alert"
    var sharedPreference: SharedPreference? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreference = SharedPreference(this)
//if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
//{
//    var notificationChannel=NotificationChannel(channelId,channel_Name,NotificationManager.IMPORTANCE_DEFAULT)
//    notificationChannel.description=channel_Desc
//    var notificationManager=getSystemService(NotificationManager::class.java)
//    notificationManager.createNotificationChannel(notificationChannel)
//}
//        displayNotification()
        auth = FirebaseAuth.getInstance()

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result!!.token
                //saveToken(token)
//                TOKEN_ID1 = token

                println("Token :--> $token")
                //textView.setText("Token : "+token);*/
            } else {
                println("Token :--> failed")
            }
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btn_login.setOnClickListener(this)
        google_sign_in_button.setOnClickListener(this)
        facebook_sign_in_button.setOnClickListener(this)
//        fl_GSignIn.setOnClickListener(this)
//        fl_fbSignIn.setOnClickListener(this)
        ln_fbSignIn.setOnClickListener(this)
//        tv_link_signup.setOnClickListener(this)

        google_sign_in_button.isFocusable=true
        google_sign_in_button.isEnabled=true
//        google_sign_in_button.performClick()
//        var ab=facebook_sign_in_button.performClick()
//        google_sign_in_button.setPressed(true);
//        google_sign_in_button.invalidate();
//        println("ab >>> "+ab)


        callbackManager = CallbackManager.Factory.create();

        facebook_sign_in_button.setPermissions(emptyList())
        facebook_sign_in_button.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    result
                    Profile.getCurrentProfile().firstName
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException?) {
                }

            })

        dialog = DialogLoader(
            this,
            "Please wait,\nwhile we verify your email"
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//        LoginManager.getInstance().registerCallback(callbackManager,
//            object : FacebookCallback<LoginResult?> {
//                override fun onSuccess(loginResult: LoginResult?) {
//                    // App code
//                }
//
//                override fun onCancel() {
//                    // App code
//                }
//
//                override fun onError(exception: FacebookException) {
//                    // App code
//                }
//            })


    }


    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btn_login -> {
                if (et_email.text.toString().length < 1) {
                    et_email.error = "please enter valid E-mail"
                    return
                } else {
                    try {
                        dialog = DialogLoader(
                            this,
                            "Please wait,\nwhile we verify your details"
                        )
                        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog?.show()

                        val callLogin: Call<LoginResponse> =
                            phpApiInterface.UserLogin(LoginRequest(et_email.text.toString()) )
                        callLogin.enqueue(object : Callback<LoginResponse> {
                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                dialog?.dismiss()
                                GenericUserFunction.DisplayToast(
                                    this@MainActivity,
                                    t.message.toString()
                                )
                                println(">>> "+t.message)
                            }
                            override fun onResponse(
                                call: Call<LoginResponse>,
                                response: Response<LoginResponse>
                            ) {
                                dialog?.dismiss()
                                if (response.isSuccessful && response.body()?.status.equals("Login Successfully")) {

//                                    GenericUserFunction.DisplayToast(
//                                        this@MainActivity,
//                                        response.body()?.status!!
//                                    )
                                    response.body()?.email
                                    response.body()?.name
//                                    response.body()?.profile_url


                                    sharedPreference?.save("Email",response.body()?.email!!)
                                    sharedPreference?.save("Name",response.body()?.name!!)
                                    var intent = Intent(this@MainActivity, DashboardActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    println(">>> " + response.message())
                                    GenericUserFunction.DisplayToast(
                                        this@MainActivity,
                                        response.body()?.status!!
                                    )
                                }
                            }

                        })
                    } catch (ex: Exception) {
                        dialog?.dismiss()
                        GenericUserFunction.DisplayToast(
                            this@MainActivity,
                            ex.message.toString()
                        )
                    }

                }

            }
            R.id.img_SignUp -> {
                callSignUp()
            }
            R.id.btn_SignUp_txt -> {
                callSignUp()
            }

            R.id.google_sign_in_button -> {
                println("g btn click >>> ")
                signIn()
            }
//            R.id.facebook_sign_in_button-> {
//            }
            R.id.img_G_logo -> {
                println("g lay click >>> ")
                google_sign_in_button.performClick()
                signIn()
            } R.id.btn_G_txt -> {
                println("g lay click >>> ")
                google_sign_in_button.performClick()
            signIn()
            } R.id.ln_GSignIn -> {
                println("g lay click >>> ")
            signIn()
//                google_sign_in_button
            }


            R.id.img_fblogo -> {
                println("f lay click >>> ")
                facebook_sign_in_button.performClick()
            }
            R.id.btn_fbtxt -> {
                println("f lay click >>> ")
                facebook_sign_in_button.performClick()
            }
            R.id.ln_fbSignIn -> {
                println("f lay click >>> ")
                facebook_sign_in_button.performClick()
            }
        }
    }

    private fun callSignUp() {
        var intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    fun signIn() {
        var signInIntent = mGoogleSignInClient?.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode === RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            handleSignInResult(task)
        } else {
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {

            val account =
                completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
//          updateUI(account)
            account?.displayName
            account?.email

            "" + account?.photoUrl
            "" + account?.account
            "" + account?.familyName
            "" + account?.givenName
            "" + account?.grantedScopes
            "" + account?.idToken
            "" + account?.serverAuthCode
            tvUserDetails.text = account?.displayName

            var img_url="no_url"
                    if (account?.photoUrl!=null){
                        img_url=account?.photoUrl.toString()
            }
            ResgisterUser(account?.email.toString(),account?.displayName.toString(),"",img_url)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
//            updateUI(null)
        }
    }

    fun ResgisterUser(email:String,name:String,fcm_token:String,profile_url:String){
        val callRegister: Call<RegisterResponse> =
            phpApiInterface.RegisterUser(
                RegisterRequest(
                    email,
                    name,
                    fcm_token,
                    profile_url
                )
            )

        try {
            dialog = DialogLoader(
                this,
                "Please wait,\nwhile we register yourself"
            )
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.show()

            var registerResult = callRegister.enqueue(object : Callback<RegisterResponse> {
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    dialog?.dismiss()
                    GenericUserFunction.DisplayToast(
                        this@MainActivity,
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

                        sharedPreference?.save("Email",email)
                        sharedPreference?.save("Name",name)
                        var intent = Intent(this@MainActivity, DashboardActivity::class.java)
                        startActivity(intent)

//                        GenericUserFunction.DisplayToast(
//                            this@MainActivity,
//                            response.body()?.response.toString()
//                        )
                    } else {
                        println(">>> " + response.message())
                        GenericUserFunction.DisplayToast(
                            this@MainActivity,
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

    fun displayNotification(){
        val notificationCompat= NotificationCompat.Builder(this,channelId)
            .setSmallIcon(R.drawable.ic_notify)
            .setContentTitle("zcontent title")
            .setContentText("Content Text")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        var notificationManagerCompat= NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(1,notificationCompat.build())
    }
}
