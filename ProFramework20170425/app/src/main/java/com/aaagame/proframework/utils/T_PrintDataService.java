package com.aaagame.proframework.utils;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class T_PrintDataService {
	private Context context = null;
	private String deviceAddress = null;
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	private BluetoothDevice device = null;
	private static BluetoothSocket bluetoothSocket = null;
	private static OutputStream outputStream = null;
	private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private boolean isConnection = false;
	final String[] items = { "复位打印机", "标准ASCII字体", "压缩ASCII字体", "字体不放大", "宽高加倍", "取消加粗模式", "选择加粗模式", "取消倒置打印", "选择倒置打印",
			"取消黑白反显", "选择黑白反显", "取消顺时针旋转90°", "选择顺时针旋转90°" };
	final byte[][] byteCommands = { { 0x1b, 0x40 }, // 复位打印机
			{ 0x1b, 0x4d, 0x00 }, // 标准ASCII字体
			{ 0x1b, 0x4d, 0x01 }, // 压缩ASCII字体
			{ 0x1d, 0x21, 0x00 }, // 字体不放大
			{ 0x1d, 0x21, 0x11 }, // 宽高加倍
			{ 0x1b, 0x45, 0x00 }, // 取消加粗模式
			{ 0x1b, 0x45, 0x01 }, // 选择加粗模式
			{ 0x1b, 0x7b, 0x00 }, // 取消倒置打印
			{ 0x1b, 0x7b, 0x01 }, // 选择倒置打印
			{ 0x1d, 0x42, 0x00 }, // 取消黑白反显
			{ 0x1d, 0x42, 0x01 }, // 选择黑白反显
			{ 0x1b, 0x56, 0x00 }, // 取消顺时针旋转90°
			{ 0x1b, 0x56, 0x01 },// 选择顺时针旋转90°
	};

	public T_PrintDataService(Context context, String deviceAddress) {
		super();
		this.context = context;
		this.deviceAddress = deviceAddress;
		this.device = this.bluetoothAdapter.getRemoteDevice(this.deviceAddress);
	}

	/**
	 * 获取设备名称
	 * 
	 * @return String
	 */
	public String getDeviceName() {
		return this.device.getName();
	}

	/**
	 * 连接蓝牙设备
	 */
	public boolean connect() {
		if (!this.isConnection) {
			try {
				bluetoothSocket = this.device.createRfcommSocketToServiceRecord(uuid);
				bluetoothSocket.connect();
				outputStream = bluetoothSocket.getOutputStream();
				this.isConnection = true;
				if (this.bluetoothAdapter.isDiscovering()) {
					System.out.println("关闭适配器！");
					this.bluetoothAdapter.isDiscovering();
				}
			} catch (Exception e) {
				Toast.makeText(this.context, "连接失败！", Toast.LENGTH_SHORT).show();
				return false;
			}
			Toast.makeText(this.context, this.device.getName() + "连接成功！", Toast.LENGTH_SHORT).show();
			return true;
		} else {
			return true;
		}
	}

	/**
	 * 断开蓝牙设备连接
	 */
	public static void disconnect() {
		System.out.println("断开蓝牙设备连接");
		try {
			bluetoothSocket.close();
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 选择指令
	 */
	public void selectCommand() {
		new AlertDialog.Builder(context).setTitle("请选择指令").setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (isConnection) {
					try {
						outputStream.write(byteCommands[which]);

					} catch (IOException e) {
						Toast.makeText(context, "设置指令失败！", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(context, "设备未连接，请重新连接！", Toast.LENGTH_SHORT).show();
				}
			}
		}).create().show();
	}

	/**
	 * 发送数据
	 */
	public boolean send(String sendData) {
		if (this.isConnection) {
			System.out.println("开始打印！！");
			try {
				byte[] data = sendData.getBytes("gbk");
				outputStream.write(byteCommands[4]);
				outputStream.write("\n     销售单\n\n".getBytes("gbk"));
				outputStream.flush();
				outputStream.write(byteCommands[3]);
				outputStream.write(data, 0, data.length);
				// 字体横向纵向均放大一倍

				outputStream.flush();
				outputStream.write(byteCommands[6]);
				outputStream.write("\n\n     绿业元农业科技有限公司\n".getBytes("gbk"));
				outputStream.flush();
				outputStream.write(byteCommands[5]);
				outputStream.write(" ------------------------------\n\n".getBytes("gbk"));
				outputStream.flush();
				return true;
			} catch (IOException e) {
				Toast.makeText(this.context, "发送失败！", Toast.LENGTH_SHORT).show();
				return false;
			}
		} else {
			Toast.makeText(this.context, "设备未连接，请重新连接！", Toast.LENGTH_SHORT).show();
			return false;

		}
	}

	public boolean printImage(Bitmap bitmap) {
		if (this.isConnection) {
			System.out.println("开始打印！！");
			try {
				// 发送打印图片前导指令
				byte[] start = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1B, 0x40, 0x1B, 0x33, 0x00 };
				outputStream.write(start);

				/** 获取打印图片的数据 **/
				byte[] draw2PxPoint = T_PicFromPrintUtils.draw2PxPoint(bitmap);

				outputStream.write(draw2PxPoint);
				// 发送结束指令
				byte[] end = { 0x1d, 0x4c, 0x1f, 0x00 };
				outputStream.write(end);
				outputStream.flush();
				return true;
			} catch (IOException e) {
				Toast.makeText(this.context, "发送失败！", Toast.LENGTH_SHORT).show();
				return false;
			}
		} else {
			Toast.makeText(this.context, "设备未连接，请重新连接！", Toast.LENGTH_SHORT).show();
			return false;

		}
	}

}