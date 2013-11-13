package com.hd.internal;

public class ScanWork {
	private long ptr;

	public enum WorkState {
		STATE_UNKOWN, STATE_NONE, STATE_IDLE, 
		STATE_WORK, STATE_CANCEL
	};

	public ScanWork() {
		ptr = 0;
	}

	@Override
	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
	}

	/************************************************************************/
	/* 与扫描仪建立连接，并初始化扫描参数信息                                                                    */
	/************************************************************************/
	public boolean create() {
		ptr = sj_create(null, 0, null, 0);
		return ptr != 0;
	}

	public boolean create(String dev, int baudrate, String ip, int port) {
		ptr = sj_create(dev, baudrate, ip, port);
		return ptr != 0;
	}

	/************************************************************************/
	/* 与扫描仪断开连接                                                        */
	/************************************************************************/
	public int destroy() {
		int ret = 1;
		if (ptr != 0)
			sj_destroy(ptr);
		return ret;
	}

	public boolean scan_photo() {
		return sj_scan_photo(ptr) == 1;
	}

	public boolean scan_point() {
		return sj_scan_point(ptr) == 1;
	}

	/************************************************************************
	 * 停止扫描
	 ************************************************************************/
	public boolean cancel() {
		return sj_cancel(ptr) == 1;
	}

	public boolean check_devices() {
		return sj_check_devices(ptr) == 1;
	}

	/************************************************************************
	 *\brief 设置扫描速度范围参数
	 *\param scan_job 定义了扫描任务。
	 *\param speed_h 定义了水平速度。
	 *\param start_angle_h 定义了水平开始角度。
	 *\param end_angle_h 定义了水平结束角度。
	 *\param speed_v 定义了垂直速度。
	 *\param resolution_v 定义了垂直分辨率。
	 *\param active_sector_start_angles 定义了扫描区域开始角度。
	 *\param active_sector_stop_angles 定义了扫描区域结束角度。
	 *\retval E_OK 表示成功。
	 ************************************************************************/
	public boolean config(int speed_h_delay, double start_angle_h,
			double end_angle_h, float speed_v_hz, double resolution_v,
			double start_angle_v, double end_angle_v) {
		return sj_config(ptr, speed_h_delay, start_angle_h, end_angle_h,
				speed_v_hz, resolution_v, start_angle_v, end_angle_v) == 1;
	}

	/************************************************************************
	 * 设置点云数据存储目录,灰度图存储目录
	 ************************************************************************/
	public boolean set_data_dir(String ptDir, String grayDir,String filesDir) {
		return sj_set_data_dir(ptr, ptDir, grayDir,filesDir) == 1;
	}

	/************************************************************************/
	/* 与扫描仪建立连接，并初始化扫描参数信息                                                                    */
	/************************************************************************/
	private native static long sj_create(String dev, int baudrate, String ip,
			int port);

	/************************************************************************/
	/* 与扫描仪断开连接                                                        */
	/************************************************************************/
	private native static int sj_destroy(long ptr);

	/************************************************************************
	 * 停止扫描
	 ************************************************************************/
	private native static int sj_cancel(long ptr);

	private native static int sj_check_devices(long ptr);

	private native static int sj_scan_photo(long ptr);

	private native static int sj_scan_point(long ptr);

	/************************************************************************
	 *\brief 设置扫描速度范围参数
	 *\param scan_job 定义了扫描任务。
	 *\param speed_h 定义了水平速度。
	 *\param start_angle_h 定义了水平开始角度。
	 *\param end_angle_h 定义了水平结束角度。
	 *\param speed_v 定义了垂直速度。
	 *\param resolution_v 定义了垂直分辨率。
	 *\param active_sector_start_angles 定义了扫描区域开始角度。
	 *\param active_sector_stop_angles 定义了扫描区域结束角度。
	 *\retval E_OK 表示成功。
	 ************************************************************************/
	private native static int sj_config(long ptr, int speed_h_delay,
			double start_angle_h, double end_angle_h, float speed_v_hz,
			double resolution_v, double start_angle_v, double end_angle_v);

	/************************************************************************
	 * 设置点云数据存储目录,灰度图存储目录
	 ************************************************************************/
	private native static int sj_set_data_dir(long ptr, String ptDir,
			String grayDir,String filesDir);

	public WorkState get_state() {
		int ret = sj_get_state(ptr);
		switch (ret) {
			case 0:
				return WorkState.STATE_NONE;
			case 1:
				return WorkState.STATE_IDLE;
			case 2:
				return WorkState.STATE_WORK;
			case 3:
				return WorkState.STATE_CANCEL;
		}

		return WorkState.STATE_NONE;
	}

	//获取当前水平转台的角度
	public double get_angle() {
		return sj_get_angle(ptr);
	}

	//获取温度
	public double get_temperature() {
		return sj_get_temperature(ptr);
	}

	//获取倾斜度
	public int get_tilt(double[] angle) {
		return sj_get_tilt(ptr, angle);
	}

	//获取状态
	public double get_battery() {
		return sj_get_battery(ptr);
	}

	//亮红灯
	public boolean led_switch(int state) {
		return 1 == sj_led(ptr, state);
	}

	//搜索零点
	public boolean search_zero() {
		return 1 == sj_search_zero(ptr);
	}

	//硬件信息
	public String get_info(int idx, int blen) {
		return sj_get_info(ptr, idx, blen);
	}

	private native static int sj_get_state(long lc);

	//获取当前水平转台的角度
	private native static double sj_get_angle(long lc);

	//获取温度
	private native static double sj_get_temperature(long lc);

	//获取倾斜度
	private native static int sj_get_tilt(long lc, double[] jda);

	//获取状态
	private native static double sj_get_battery(long lc);

	//LED灯
	private native static int sj_led(long lc, int status);

	//搜索零点
	private native static int sj_search_zero(long lc);

	//硬件信息
	private native static String sj_get_info(long lc, int idx, int blen);

}
