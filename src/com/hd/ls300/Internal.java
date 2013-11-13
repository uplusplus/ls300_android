package com.hd.ls300;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hd.base.Utils;
import com.hd.internal.WebServer;

public class Internal {
	// 数据目录结构
	/*
	 * /sdcard/ls300/ 
	 * 		- data 
	 * 			- point_cloud 
	 * 				- 2013030303030303.pcd 
	 * 			- image 
	 * 				- 2013030303030303.jpg 
	 * 			- meta
	 * 				- 2013030303030303.meta
	 * 			- picture //暂时不考虑这个，这个数据在相机里 
	 * 				- 2013030303030303.jpg
	 * 		- config 
	 * 			- panoramic.cfg 
	 * 		- settings (或直接用系统的API作配置保存) 
	 * 			- db.sqlite 
	 * 		- tmp (临时数据存储)
	 */
	// 全局配置参数
	public static final String data_dir = "data",
			point_cloud_dir = "point_cloud", meta_dir = "meta",
			config_dir = "config", image_dir = "image",
			setting_dir = "settings", picture_dir = "picture", tmp_dir = "tmp",
			sql_dir = "sql";

	public static final class file_endwith {
		public static final String point_cloud[] = { "pcl", "pcd", "hls" },
				meta[] = { "meta" }, config[] = { "cfg" },
				setting[] = { "set" }, image[] = { "jpg" }, tmp[] = { "tmp" },
				sql[] = { "sqlite" };
	}

	// 默认参数配置
	public static final String default_root_dir = "ls300", sdcard = Utils
			.getSDPath();
	public static String root_path, data_path, point_cloud_path, meta_path,
			image_path, config_path, setting_file;

	public static ConfigManager.Manager config_mgr;
	public static DataManager.Manager data_mgr;
	public static DeviceSetting.Setting setting;
	public static Context app;

	public static void Init(Context ctx) {
		if(app != null) return;
		
		app = ctx;
		Init(ctx,default_root_dir);
		if (setting.services.webservice)
			WebServer.StartWebServer(ctx);
	}

	public static boolean CreateDirs() {
		boolean ret = true;
		File f;

		f = new File(point_cloud_path);
		if (f.exists())
			return true;

		ret = f.mkdirs();
		f = new File(image_path);
		ret = f.mkdirs();
		f = new File(meta_path);
		ret = f.mkdirs();
		f = new File(config_path);
		ret = f.mkdirs();
		f = new File(setting_file);
		ret = f.getParentFile().mkdirs();
		return ret;
	}

	public static void Init(Context ctx,String root) {

		//路径处理
		root_path = Utils.joinPath(sdcard, root);
		data_path = Utils.joinPath(root_path, data_dir);
		point_cloud_path = Utils.joinPath(data_path, point_cloud_dir);
		meta_path = Utils.joinPath(data_path, meta_dir);
		image_path = Utils.joinPath(data_path, image_dir);
		config_path = Utils.joinPath(root_path, config_dir);
		setting_file = Utils.joinPath(root_path, setting_dir) + "."
				+ file_endwith.setting[0];

		CreateDirs();

		//加载两个管理器
		config_mgr = new ConfigManager.Manager();
		data_mgr = new DataManager.Manager();
		setting = DeviceSetting.get(ctx);
	}

	public void leave() {
	}

	static {
		System.loadLibrary("ecore");
		System.loadLibrary("ecorejni");
	}

	public static final com.hd.base.ImageCache imageCache = new com.hd.base.ImageCache();

	public static Bitmap GetBitmap(String url, boolean scaled) {
		Bitmap ret;
		if (scaled)
			ret = imageCache.Get(url, 300, 300);
		else
			ret = imageCache.Get(url);
		if (ret == null)
			ret = BitmapFactory.decodeResource(app.getResources(),
					android.R.drawable.picture_frame);
		return ret;
	}

}
