package com.corona_ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.common.SharedPreference;
import com.corona_ui.bluetoothlocator.R;
import com.service.BluetoothSearchService;

import java.util.ArrayList;


public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";
    BluetoothAdapter mBluetoothAdapter;
    Button btnEnableDisable_Discoverable;
    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DeviceListAdapter mDeviceListAdapter;
    ListView lvNewDevices;

    private TextView tv_loggedInUser;
    private SharedPreference sharedPreference;
    // Create a BroadcastReceiver for ACTION_FOUND


    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */


    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");

            assert action != null;
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                //distance calculate from rssi
                double res = (Double.valueOf((-69 - (rssi))) / Double.valueOf((10 * 2)));
                double distance_meter = Math.pow(10, res);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                mBTDevices.add(device);
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices, rssi, distance_meter);
                lvNewDevices.setAdapter(mDeviceListAdapter);

                try {
                    if (distance_meter > 0) {
                        if (distance_meter < 0.90) {
                            final MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.ke);
                            mp.start();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
//        unregisterReceiver(mBroadcastReceiver1);
//        unregisterReceiver(mBroadcastReceiver2);
//        unregisterReceiver(mBroadcastReceiver3);
        //mBluetoothAdapter.cancelDiscovery();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sharedPreference= new SharedPreference(this);

        Intent intent=new Intent(this, BluetoothSearchService.class);
        startService(intent);
//        Button btnONOFF = (Button) findViewById(R.id.btnONOFF);
//        btnEnableDisable_Discoverable = (Button) findViewById(R.id.btnDiscoverable_on_off);

//        sharedPreference.getValueString("Name");

        tv_loggedInUser=(TextView)findViewById(R.id.tv_loggedInUser);
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        tv_loggedInUser.setText("Logged in user : "+sharedPreference.getValueString("Name"));

//        btnONOFF.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: enabling/disabling bluetooth.");
//                enableDisableBT();
//            }
//        });

        if (! mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
//        displayData();

    }

    void displayData() {
        Log.d(TAG, "btnDiscover: Looking for unpaired devices.");
        mBTDevices.clear();
        if (mBluetoothAdapter.isDiscovering()) {
            // mBluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "btnDiscover: Canceling discovery.");
            //check BT permissions in manifest
            checkBTPermissions();
            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if (!mBluetoothAdapter.isDiscovering()) {
            //check BT permissions in manifest
            checkBTPermissions();
            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }



//    public void btnDiscover(View view) {
//
//    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     * <p>
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    @SuppressLint("NewApi")
    private void checkBTPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }
}
