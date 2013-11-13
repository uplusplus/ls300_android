package com.hd.internal;

class Control {

	private long ptr;

	public long Ref() {
		return ptr;
	}

	public Control() {
		ptr = hl_Constructor();
	}

	@Override
	protected void finalize() throws Throwable {
		hl_Destructor(ptr);
		super.finalize();
	}

	//初始化
	public int open(String com_name, int baudrate) {
		return hl_open(ptr, com_name, baudrate);
	}

	public int open_socket(String ip, int port) {
		return hl_open_socket(ptr, ip, port);
	}

	//关闭串口
	public int close() {
		return hl_close(ptr);
	}

	//准备工作
	public int turntable_prepare(int pre_start_angle) {
		return hl_turntable_prepare(ptr, pre_start_angle);
	}

	//开始工作
	public int turntable_start() {
		return hl_turntable_start(ptr);
	}

	//停止工作
	public int turntable_stop() {
		return hl_turntable_stop(ptr);
	}

	//是否停止工作
	public int turntable_is_stopped() {
		return hl_turntable_is_stopped(ptr);
	}

	//获取当前水平转台的角度
	public double turntable_get_angle() {
		return hl_turntable_get_angle(ptr);
	}
	
	public boolean check() {
		return hl_turntable_check(ptr)==1;
	}

	//转台工作
	public int turntable_config(int step_delay, double start, double end) {
		return hl_turntable_config(ptr, step_delay, start, end);
	}

	//根据实际传过来的水平旋转角度，调整水平台，以较快速度转到实际水平台的起始角度/转台回到起始原点
	public int turntable_turn(double angle) {
		return hl_turntable_turn(ptr, angle);
	}

	//相机拍照
	public int camera_take_photo() {
		return hl_camera_take_photo(ptr);
	}

	//获取温度
	public double get_temperature() {
		return hl_get_temperature(ptr);
	}

	//获取倾斜度
	public int get_tilt(double[] angle) {
		return hl_get_tilt(ptr,angle);
	}

	//获取状态
	public double get_battery() {
		return hl_get_battery(ptr);
	}

	//亮红灯
	public int led_red() {
		return hl_led_red(ptr);
	}

	//亮绿灯
	public int led_green() {
		return hl_led_green(ptr);
	}

	//LED熄灭
	public int led_off() {
		return hl_led_off(ptr);
	}

	//搜索零点
	public int search_zero() {
		return hl_search_zero(ptr);
	}

	//硬件信息
	public String get_info(int idx, int blen) {
		return hl_get_info(ptr, idx, blen);
	}

	private native static long hl_Constructor();

	private native static void hl_Destructor(long p);

	//初始化
	private native static int hl_open(long lc, String com_name, int baudrate);

	private native static int hl_open_socket(long lc, String ip, int port);

	//关闭串口
	private native static int hl_close(long lc);

	//准备工作
	private native static int hl_turntable_prepare(long lc, int pre_start_angle);

	//开始工作
	private native static int hl_turntable_start(long lc);

	//停止工作
	private native static int hl_turntable_stop(long lc);

	//是否停止工作
	private native static int hl_turntable_is_stopped(long lc);

	//获取当前水平转台的角度
	private native static double hl_turntable_get_angle(long lc);

	//转台工作
	private native static int hl_turntable_config(long lc, int step_delay,
			double start, double end);

	//根据实际传过来的水平旋转角度，调整水平台，以较快速度转到实际水平台的起始角度/转台回到起始原点
	private native static int hl_turntable_turn(long lc, double angle);
	//相机拍照
	private native static int hl_camera_take_photo(long lc);

	//获取温度
	private native static double hl_get_temperature(long lc);

	//获取倾斜度
	private native static int hl_get_tilt(long lc,double[] jda);

	//获取状态
	private native static double hl_get_battery(long lc);

	//亮红灯
	private native static int hl_led_red(long lc);

	//亮绿灯
	private native static int hl_led_green(long lc);

	//LED熄灭
	private native static int hl_led_off(long lc);

	//搜索零点
	private native static int hl_search_zero(long lc);

	//硬件信息
	private native static String hl_get_info(long lc, int idx, int blen);
	
	private native static int hl_turntable_check(long lc);
}
