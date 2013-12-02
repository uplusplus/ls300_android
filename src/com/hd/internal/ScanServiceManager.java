package com.hd.internal;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.hd.base.TextOutput;
import com.hd.internal.ScanWork.WorkState;
import com.hd.ls300.Internal;

public class ScanServiceManager {
	static ScanWork worker;
	static StatusMonitor monitor;
	static List<WeakReference<TextOutput>> ps = new ArrayList<WeakReference<TextOutput>>();
	static ScanServiceManager instance = null;

	private ScanServiceManager() {
		instance = this;
	}

	public static ScanServiceManager Instance() {
		if (instance != null)
			return instance;
		else
			return new ScanServiceManager();
	}

	public static void Output(TextOutput out) {
			ps.add(new WeakReference<TextOutput>(out));
	}

	static void print(String s) {
		if (ps.size() > 0) {
			for (WeakReference<TextOutput> o : ps) {
				TextOutput to = o.get();
				if (to != null) {
					to.print(s);
				}
			}
		} else
			Log.i("ScanServiceManager", s);
	}

	public static void Connect() {

		if (worker != null) {
			if (monitor != null) {
				monitor.stop();
			}
			worker.cancel();
			worker.destroy();
		}

		print("连接设备:");
		worker = WorkFactory.Create();
		if (worker == null) {
			print("连接失败。");
			return;
		}

		print("连接成功。");
		monitor = new StatusMonitor(worker);
		monitor.setDelay(2000).start();
	}

	public static void requestMonitor(Monitor m) {
		if (monitor != null) {
			print("开启监控:");
			monitor.addMonitor(m);
			monitor.resume();
		} else {
			print("开启监控异常: 未连接设备。");
		}
	}

	public static void removeMonitor(Monitor m) {
		if (monitor == null)
			monitor.removeMonitor(m);
	}

	public static void ScanCloud() {
		print("扫描点云:");
		if (worker != null
				&& WorkFactory.ProcuctWork(worker,
						Internal.config_mgr.GetSelect()) && worker.scan_point())
			print("启动点云扫描。");
		else
			print("启动点云扫描失败。");
	}

	public static void ScanPhoto() {
		print("全景拍照:");
		if (worker != null && worker.scan_photo())
			print("启动全景拍照。");
		else
			print("启动全景拍照失败。");
	}

	public static void Cancel() {
		print("终止任务!");
		if (worker != null && worker.cancel())
			print("取消任务。");
		else
			print("取消任务失败。");
	}

	public static interface Monitor {

		public boolean on_state(WorkState s);

		// 获取当前水平转台的角度
		public boolean on_angle(double a);

		// 获取温度
		public boolean on_temperature(double t);

		// 获取倾斜度
		public boolean on_tilt(double[] angle);

		// 获取状态
		public boolean on_battery(double b);
	}

	public static class BaseMonitor implements Monitor {

		public boolean on_state(WorkState s) {
			return false;
		}

		// 获取当前水平转台的角度
		public boolean on_angle(double a) {
			return false;
		}

		// 获取温度
		public boolean on_temperature(double t) {
			return false;
		}

		// 获取倾斜度
		public boolean on_tilt(double[] angle) {
			return false;
		}

		// 获取状态
		public boolean on_battery(double b) {
			return false;
		}
	}

	static class StatusMonitor implements Runnable {
		private static final String TAG = "com.hd.internal.StatusMonitor";
		List<WeakReference<Monitor>> ws = new ArrayList<WeakReference<Monitor>>();
		Thread myThread;

		Handler mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				for (WeakReference<Monitor> m : ws) {
					Monitor monitor = m.get();
					if (monitor == null) {
						ws.remove(m);
						return;
					}
					switch (msg.what) {
					case 1:
						monitor.on_state(state);
						break;
					case 2:
						monitor.on_angle(angle);
						break;
					case 3:
						monitor.on_temperature(temperature);
						break;
					case 4:
						monitor.on_tilt(tilt);
						break;
					case 5:
						monitor.on_battery(battery);
						break;
					case 6:
						monitor.on_state(WorkState.STATE_IDLE);
						break;

					}
				}
			}
		};

		public void addMonitor(Monitor m) {
			ws.add(new WeakReference<Monitor>(m));
		}

		public void removeMonitor(Monitor m) {
			for (WeakReference<Monitor> mon : ws) {
				if (mon.get() == m) {
					ws.remove(mon);
					break;
				}
			}
		}

		public StatusMonitor setDelay(int mi) {
			this.delay = mi;
			return this;
		}

		public void start() {
			myThread = new Thread(this);
			myThread.start();
		}

		public void pause() {
			this.pause = true;
		}

		public void resume() {
			this.pause = false;
		}

		public void stop() {
			this.stop = true;
			resume();
			try {
				if (myThread != null) {
					myThread.join();
					myThread = null;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private boolean[] disable = new boolean[6];
		private int delay = 1000;
		private boolean stop = false;
		private boolean pause = true;

		private WeakReference<ScanWork> scanwork = null;

		private WorkState state;
		private double angle;
		private double temperature;
		private double[] tilt = new double[2];
		private double battery;

		public StatusMonitor(ScanWork scan) {
			this.scanwork = new WeakReference<ScanWork>(scan);
		}

		@Override
		public void run() {
			WorkState _state = WorkState.STATE_IDLE;
			double _angle = -1;
			double _temperature = -1;
			double[] _tilt = new double[2];
			double _battery = -1;

			while (!stop) {
				while (pause) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

				Message msg;

				ScanWork scan = scanwork.get();
				if (scan == null) {
					msg = mHandler.obtainMessage();
					msg.what = 6;
					msg.sendToTarget();
					break;
				}

				if (!disable[1]) {
					msg = mHandler.obtainMessage();
					msg.what = 1;
					state = scan.get_state();
					if (state == WorkState.STATE_NONE) {
						Log.e(TAG, "Devices status error. Exit Monitor.");
						return;
					}
					if (_state != state) {
						msg.sendToTarget();
						_state = state;
					}
				}

				if (!disable[2]) {
					msg = mHandler.obtainMessage();
					msg.what = 2;
					angle = scan.get_angle();
					if (angle != _angle) {
						msg.sendToTarget();
						_angle = angle;
					}
				}
				if (!disable[3]) {
					msg = mHandler.obtainMessage();
					msg.what = 3;
					temperature = scan.get_temperature();
					if (temperature != _temperature) {
						msg.sendToTarget();
						_temperature = temperature;
					}
				}
				if (!disable[4]) {
					msg = mHandler.obtainMessage();
					msg.what = 4;
					scan.get_tilt(tilt);
					if (tilt[0] != _tilt[0] || tilt[1] != _tilt[1]) {
						msg.sendToTarget();
						_tilt[0] = tilt[0];
						_tilt[1] = tilt[1];
					}
				}
				if (!disable[5]) {
					msg = mHandler.obtainMessage();
					msg.what = 5;
					battery = scan.get_battery();
					if (battery != _battery) {
						msg.sendToTarget();
						_battery = battery;
					}
				}
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
