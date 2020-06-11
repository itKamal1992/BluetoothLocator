package com.service

import android.annotation.SuppressLint
import android.app.IntentService
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import com.corona_ui.bluetoothlocator.R
import java.util.*


class BluetoothSearchService : IntentService("BluetoothSearchService") {
    var mBluetoothAdapter: BluetoothAdapter? = null
    var mBTDevices = ArrayList<BluetoothDevice>()
    var mBT_RSSI = ArrayList<Int>()
    var mp: MediaPlayer?=null
    val TAG="BluetoothSearchService"
    var count=0
//    lateinit var mainHandler: Handler

    fun showLog(msg:String){
        Log.d(TAG,msg)

    }

    override fun onHandleIntent(intent: Intent?) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (!mBluetoothAdapter?.isEnabled()!!) {
            mBluetoothAdapter?.enable()
        }

        val timer = Timer()
// Set the schedule function
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    showLog(">>> "+count)
                    displayData()
                count++
                }
            },
            0, 15000
        ) // 1000 Millisecond  = 1 second
//10 Sec --- 02 (id1 id2) - distance (0.30 0.45) =
        //10 sec - 02 (id1 id2) - distance (0.35 0.50)
        // acc_distance = d1 0.30+0.35/2 d2 - 0.45+0.50/2
    }

    fun displayData() {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.")
        mBTDevices.clear()
        mBT_RSSI.clear()
        if (mBluetoothAdapter!!.isDiscovering) {
            // mBluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnDiscover: Canceling discovery.")

            //check BT permissions in manifest
            checkBTPermissions()
//            mBluetoothAdapter!!.startDiscovery()
            val discoverDevicesIntent = IntentFilter(BluetoothDevice.ACTION_FOUND)
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent)
        }
        if (!mBluetoothAdapter!!.isDiscovering) {

            //check BT permissions in manifest
            checkBTPermissions()
            mBluetoothAdapter!!.startDiscovery()
            val discoverDevicesIntent = IntentFilter(BluetoothDevice.ACTION_FOUND)
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent)
        }
    }

    private var  mBroadcastReceiver3 =
    object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            Log.d(TAG, "onReceive: ACTION FOUND.")
            assert(action != null)
            if (action == BluetoothDevice.ACTION_FOUND) {
                val device =
                    intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE)
                //distance calculate from rssi
                val res =
                    java.lang.Double.valueOf((-69 - rssi).toDouble()) / java.lang.Double.valueOf(
                        (10 * 2).toDouble()
                    )
                val distance_meter = Math.pow(10.0, res)
                Log.d(TAG,
                    "onReceive: " + device.name + ": " + device.address
                )

//                mBTDevices.add(device)
//                mBT_RSSI.add(rssi)

//                mDeviceListAdapter = DeviceListAdapter(
//                    context,
//                    R.layout.device_adapter_view,
//                    mBTDevices,
//                    rssi,
//                    distance_meter
//                )
//                lvNewDevices.setAdapter(mDeviceListAdapter)
                try {
                    if (distance_meter > 0) {
                        if (distance_meter < 0.90) {
                            mBTDevices.add(device)
//                            val mp =
//                                MediaPlayer.create(getApplicationContext(), R.raw.ke)
//                            mp.start()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    //    public void btnDiscover(View view) {
    //
    //    }
    @SuppressLint("NewApi")
    private fun checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            var permissionCheck =
                checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION")
            permissionCheck += checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION")
            if (permissionCheck != 0) {
//                this.requestPermissions(
//                    arrayOf(
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ), 1001
//                ) //Any number
            }
        } else {
            Log.d(TAG,
                "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP."
            )
        }
    }

}