package com.aaagame.proframework.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.aaagame.proframework.dialog.AALoadingDialog;
import com.aaagame.proframework.dialog.T_Bluetooth_Dialog;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class T_BluetoothService {
    private Context context = null;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    ;
    public ArrayList<BluetoothDevice> unbondDevices = null; // 用于存放未配对蓝牙设备
    public ArrayList<BluetoothDevice> bondDevices = null;// 用于存放已配对蓝牙设备

    private static T_BluetoothService instance;

    // 获取instance
    public static T_BluetoothService getBluetoothService(Context context) {
        if (instance == null) {
            instance = new T_BluetoothService(context);
        }
        return instance;
    }

    public void connect(BluetoothDevice bluetoothDevice) {

        try {
            Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
            createBondMethod.invoke(bluetoothDevice);
            // 将绑定好的设备添加的已绑定list集合
            bondDevices.add(bluetoothDevice);
            // 将绑定好的设备从未绑定list集合中移除
            unbondDevices.remove(bluetoothDevice);
        } catch (Exception e) {
            Toast.makeText(activity, "配对失败！", Toast.LENGTH_SHORT).show();
        }

    }

    public T_BluetoothService(Context context) {
        this.context = context;
        // this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.unbondDevices = new ArrayList<BluetoothDevice>();
        this.bondDevices = new ArrayList<BluetoothDevice>();
        this.initIntentFilter();

    }

    private void initIntentFilter() {
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        // 注册广播接收器，接收并处理搜索结果
        context.registerReceiver(receiver, intentFilter);

    }

    /**
     * 打开蓝牙
     */
    public void openBluetooth(Activity activity) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, 1);

    }

    /**
     * 关闭蓝牙
     */
    public void closeBluetooth() {
        this.bluetoothAdapter.disable();
    }

    /**
     * 判断蓝牙是否打开
     *
     * @return boolean
     */
    public boolean isOpen() {
        return this.bluetoothAdapter.isEnabled();

    }

    private AALoadingDialog progressDialog;
    private boolean isHasget;
    private Activity activity;

    /**
     * 搜索蓝牙设备
     */
    public void searchDevices(AALoadingDialog progressDialog, Activity activity) {
        isSuccess = true;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.activity = activity;
        isHasget = false;
        this.progressDialog = progressDialog;
        try {
            // 任务启动，可以在这里显示一个对话框，这里简单处理
            this.progressDialog.setMsg("正在搜索打印机...");
            this.progressDialog.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.bondDevices.clear();
        this.unbondDevices.clear();

        // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
        this.bluetoothAdapter.startDiscovery();
    }

    /**
     * 添加未绑定蓝牙设备到list集合
     *
     * @param device
     */
    public void addUnbondDevices(BluetoothDevice device) {
        System.out.println("未绑定设备名称：" + device.getName());
        if (!this.unbondDevices.contains(device)) {
            this.unbondDevices.add(device);
        }
    }

    /**
     * 添加已绑定蓝牙设备到list集合
     *
     * @param device
     */
    public void addBandDevices(BluetoothDevice device) {
        System.out.println("已绑定设备名称：" + device.getName());
        if (!this.bondDevices.contains(device)) {
            this.bondDevices.add(device);
        }
    }

    public BluetoothDevice successBluetoothDevice;
    private T_PrintDataService printDataService;
    public String sendData;
    public boolean isSuccess = true;

    public void print(AALoadingDialog progressDialog, Activity activity) {
        if (activity != null) {
            this.activity = activity;
        }
        if (progressDialog != null) {
            this.progressDialog = progressDialog;
        }
        if (isSuccess) {

            if (printDataService == null) {
                printDataService = new T_PrintDataService(this.context, successBluetoothDevice.getAddress());
            }
            if (sendData != null) {
            }
            isSuccess = printDataService.send(sendData);
            // isSuccess = printDataService.printImage(bitmap);
            if (!isSuccess) {
                searchDevices(this.progressDialog, activity);
            }

        } else {
            searchDevices(this.progressDialog, activity);
        }

    }

    public void connect(BluetoothDevice bluetoothDevice, boolean isbound) {
        if (isbound) {
            connectPrint(bluetoothDevice);
        } else {
            try {
                Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
                createBondMethod.invoke(bluetoothDevice);
                // 将绑定好的设备添加的已绑定list集合
                bondDevices.add(bluetoothDevice);
                // 将绑定好的设备从未绑定list集合中移除
                unbondDevices.remove(bluetoothDevice);
                connectPrint(bluetoothDevice);
            } catch (Exception e) {
                Toast.makeText(this.context, "配对失败！", Toast.LENGTH_SHORT).show();
                Toast.makeText(this.context, "设备未连接，请重新连接！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void connectPrint(BluetoothDevice bluetoothDevice) {
        printDataService = new T_PrintDataService(this.context, bluetoothDevice.getAddress());
        // 一上来就先连接蓝牙设备
        boolean flag = printDataService.connect();
        if (flag == false) {
            // 连接失败
            // CommonMethod_1.toastShow(context, "连接失败！");
        } else {
            successBluetoothDevice = bluetoothDevice;
            // 连接成功
            // CommonMethod_1.toastShow(context, "连接成功！");
            print(null, null);
        }
    }

    /**
     * 蓝牙广播接收器
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                        System.out.println("已配对设备" + device.getName());
                        if (device.getName().equals("BM9000-II")) {
                            bluetoothAdapter.cancelDiscovery();
                            isHasget = true;
                            connect(device, true);
                            try {
                                progressDialog.dismiss();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        addBandDevices(device);
                    } else {
                        System.out.println("未配对设备" + device.getName());
                        if (device.getName().equals("BM9000-II")) {
                            bluetoothAdapter.cancelDiscovery();
                            if (!isHasget) {
                                if (activity != null) {
                                    T_Bluetooth_Dialog bluetooth_Dialog = new T_Bluetooth_Dialog(activity, "");
                                    bluetooth_Dialog.show();
                                }

                            }
                        }
                        addUnbondDevices(device);
                    }
                } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {

                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    System.out.println("设备搜索完毕");
                    if (!isHasget) {
                        if (activity != null) {
                            T_Bluetooth_Dialog bluetooth_Dialog = new T_Bluetooth_Dialog(activity, "");
                            bluetooth_Dialog.show();
                        }

                    }
                    try {
                        progressDialog.dismiss();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    // bluetoothAdapter.cancelDiscovery();
                }
                if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                    if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                        System.out.println("--------打开蓝牙-----------");
                    } else if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
                        System.out.println("--------关闭蓝牙-----------");
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    };

}