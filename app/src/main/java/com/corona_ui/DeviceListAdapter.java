package com.corona_ui;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.corona_ui.bluetoothlocator.R;

import java.util.ArrayList;


public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

    private LayoutInflater mLayoutInflater;
    private ArrayList<BluetoothDevice> mDevices;
    private int  mViewResourceId;
    private int  rf;
    private  double meter_dist;
    public DeviceListAdapter(Context context, int tvResourceId, ArrayList<BluetoothDevice> devices, int rft, double distance_meter){
        super(context, tvResourceId,devices);
        this.mDevices = devices;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = tvResourceId;
        rf = rft;
        meter_dist=distance_meter;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(mViewResourceId, null);

        BluetoothDevice device = mDevices.get(position);

        if (device != null) {
            TextView deviceName = (TextView) convertView.findViewById(R.id.tvDeviceName);
            TextView deviceAdress = (TextView) convertView.findViewById(R.id.tvDeviceAddress);
            TextView tvDevicerssi = (TextView) convertView.findViewById(R.id.tvDevicerssi);
            TextView meterd = (TextView) convertView.findViewById(R.id.meterdist);

            if (deviceName != null) {
                deviceName.setText("Name :"+device.getName());
            }
            if (deviceAdress != null) {
                deviceAdress.setText("Address :" +device.getAddress());
            }
            if (tvDevicerssi != null) {
                tvDevicerssi.setText("RSSI :"+String.valueOf(rf));
            }
            if (meterd != null) {
                meterd.setText("Distance in Meter :"+String.valueOf(meter_dist));
            }
        }

        return convertView;
    }

}
