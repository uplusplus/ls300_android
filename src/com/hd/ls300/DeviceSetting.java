package com.hd.ls300;

import java.io.Serializable;

import android.content.Context;
import android.os.Environment;

import com.hd.base.Utils;

public class DeviceSetting {
	public static enum CONNECT_TYPE {
		CONNECT_TCP, CONNECT_SERIAL, CONNECT_PIPE
	}

	public static Setting get(Context ctx) {
		Setting ret;
		ret = (Setting) Utils.ReadSerializable(Internal.setting_file);
		if (ret == null) {
			ret = Setting.create(ctx);
			ret.Save();
		}
		return ret;
	}

	public static class Setting implements Serializable {
		static final long serialVersionUID = 7932287852182643449L;

		public void Save() {
			Utils.WriteSerializable(Internal.setting_file, this);
		}

		public static class Vendor implements Serializable {
			static final long serialVersionUID = 7932287852182643448L;
			// 不要在这里直接硬编码，在ＸＭＬ中写
			public String corp_name; // 公司名称="海达数云"
			public String corp_address; // 地址/联系方式="武汉市东湖高新技术开发区武大科技园慧业楼7F"
			public String phone_number; // 电话
			public String introduction;// 简介

			public Vendor() {
				corp_name = "海达数云";
				corp_address = "武汉市东湖高新技术开发区武大科技园慧业楼7F";
				phone_number = "15327150110";
				introduction = "三维激光扫描仪";
			}
		}

		public static class Device implements Serializable {
			static final long serialVersionUID = 7932287852182643447L;
			public String name;// 设备名 ＬＳ３００
			public String software_version; // V2.0
			public Vendor vendor = new Vendor();

			public Device() {
				name = "LS300";
				software_version = "2.0";
			}
		}

		public static class Connect implements Serializable {
			static final long serialVersionUID = 7932287852182643446L;
			public CONNECT_TYPE type;
			// TCP
			public String address;
			public int port;
			// SERIAL
			public String dev;
			public int bandrate;
			// PIPE
			public String pipe;
		}

		public static class Connections implements Serializable {
			static final long serialVersionUID = 7932287852182643445L;
			public Connect control = new Connect(); // 连接中位机
			public Connect sick = new Connect(); // 连接扫描仪

			public Connect remote = new Connect(); // 远程监听连接
			public Connect web = new Connect(); // 远程监听连接

			public Connections() {
				control.type = CONNECT_TYPE.CONNECT_SERIAL;
				control.dev = "/dev/ttyUSB0";
				control.bandrate = 38400;

				sick.type = CONNECT_TYPE.CONNECT_TCP;
				sick.address = "192.168.1.10";
				sick.port = 49152;

				remote.type = CONNECT_TYPE.CONNECT_PIPE;
				control.pipe = "/data/data/com.hd.ls300/cache/test.sprite";

				web.type = CONNECT_TYPE.CONNECT_TCP;
				web.address = "127.0.0.1";
				web.port = 8081;

			}
		}

		public static class Services {
			public boolean webservice;

			public Services() {
				webservice = true;
			}
		}

		public static class Paths {
			public String data_dir;
			public String files_dir;

			public static Paths create(Context ctx) {
				Paths pts = new Paths();
				pts.data_dir = Environment.getExternalStorageDirectory()
						.getPath() + "/ls300";
				pts.files_dir = ctx.getFilesDir().getAbsolutePath();
				return pts;
			}
		}

		public static Setting create(Context ctx) {
			Setting s = new Setting();
			s.paths = Paths.create(ctx);
			return s;
		}

		public Device device = new Device();
		public Connections connections = new Connections();
		public Services services = new Services();
		public Paths paths;
	}

}
