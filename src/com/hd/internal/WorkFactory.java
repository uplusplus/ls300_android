package com.hd.internal;

import android.util.Log;

import com.hd.ls300.ConfigManager.ScanConfig;
import com.hd.ls300.DeviceSetting.Setting.Connect;
import com.hd.ls300.Internal;

public class WorkFactory {
	private static final String TAG = "com.hd.internal.WorkFactory";

	public static ScanWork Create() {
		ScanWork scan = null;
		Connect connect1, connect2;
		connect1 = Internal.setting.connections.control;
		connect2 = Internal.setting.connections.sick;
		scan = new ScanWork();
		if (!scan.create(connect1.dev, connect1.bandrate, connect2.address,
				connect2.port)) {
			Log.e(TAG, "连接设备失败。");
			scan.destroy();
			return null;
		}

		if (!scan.check_devices()) {
			Log.e(TAG, "设备状态异常。");
			scan.destroy();
			return null;
		}

		return scan;
	}

	public static boolean ProcuctWork(ScanWork scan, ScanConfig sc) {
		if (!scan.set_data_dir(Internal.point_cloud_path, Internal.image_path)) {
			Log.e(TAG, "配置数据目录异常。");
			scan.destroy();
			return false;
		}

		if (!scan.config(sc.preci.plus_delay, sc.area.left, sc.area.right,
				sc.preci.frequency, sc.preci.resolution, sc.area.bottom,
				sc.area.top)) {
			Log.e(TAG, "发送配置参数失败。");
			scan.destroy();
			return false;
		}

		return true;
	}

}
