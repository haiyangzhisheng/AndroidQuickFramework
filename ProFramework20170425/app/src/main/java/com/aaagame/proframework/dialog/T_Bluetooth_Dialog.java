package com.aaagame.proframework.dialog;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.aaagame.proframework.R;
import com.aaagame.proframework.utils.T_BluetoothService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class T_Bluetooth_Dialog extends Dialog {
    private Context context;
    private LayoutInflater inflat;

    // 构造方法中获取context
    public T_Bluetooth_Dialog(Context context, String printData) {
        super(context);
        this.context = context;
        inflat = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    class DeviceItem {
        public Button btn_device;
        public BluetoothDevice bluetoothDevice;
    }

    private Button btn_cpxz_cancle;
    private LinearLayout line_wpd, line_ypd;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.t_bluetooth_dialog);
        this.setCanceledOnTouchOutside(false);
        gson = new Gson();
        initLayout();
        initListener();
        initData();
    }

    private void initLayout() {
        btn_cpxz_cancle = (Button) findViewById(R.id.btn_cpxz_cancle);
        line_wpd = (LinearLayout) findViewById(R.id.line_wpd);
        line_ypd = (LinearLayout) findViewById(R.id.line_ypd);
    }


    public void initListener() {
        btn_cpxz_cancle
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        try {
                            dismiss();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
    }

    private List<DeviceItem> edlist = new ArrayList<DeviceItem>();
    private T_BluetoothService bluetoothService;

    public void initData() {
        edlist.clear();
        bluetoothService = T_BluetoothService.getBluetoothService(context);
        for (BluetoothDevice bluetoothDevice : bluetoothService.unbondDevices) {
            View v = inflat.inflate(R.layout.device_item, null);
            final DeviceItem deviceItem = new DeviceItem();
            deviceItem.btn_device = (Button) v.findViewById(R.id.btn_device);
            deviceItem.btn_device.setText(bluetoothDevice.getName());
            deviceItem.bluetoothDevice = bluetoothDevice;
            deviceItem.btn_device
                    .setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            bluetoothService.connect(
                                    deviceItem.bluetoothDevice, false);
                            dismiss();
                        }
                    });
            line_wpd.addView(v);
        }
        for (BluetoothDevice bluetoothDevice : bluetoothService.bondDevices) {
            View v = inflat.inflate(R.layout.device_item, null);
            final DeviceItem deviceItem = new DeviceItem();
            deviceItem.btn_device = (Button) v.findViewById(R.id.btn_device);
            deviceItem.btn_device.setText(bluetoothDevice.getName());
            deviceItem.bluetoothDevice = bluetoothDevice;
            deviceItem.btn_device
                    .setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            bluetoothService.connect(
                                    deviceItem.bluetoothDevice, true);
                            dismiss();
                        }
                    });
            line_ypd.addView(v);
        }
    }
}
