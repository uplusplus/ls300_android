package com.hd.ls300;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;

import com.hd.base.Selectable;
import com.hd.base.Utils;
import com.hd.base.Utils.AreaF;
import com.hd.ls300.ConfigManager.Precisions.Precision;
import com.hd.ls300.ConfigManager.Precisions.precision;

public class ConfigManager {
	private static final String TAG = "ScanConfig";

	public static class Manager implements Manage {
		private List<ScanConfig> list = null;

		public Manager() {
			Load(Internal.config_path);
		}

		public void Reload() {
			Load(Internal.config_path);
		}

		private static List<ScanConfig> MakeDefaultConfig() {
			List<ScanConfig> ret = new ArrayList<ScanConfig>();

			ret.add(new ScanConfig("Panoramic-Low", -45, 90, 0, 360,
					Precision.PRECISION_LEVEL_LOW));
			ret.add(new ScanConfig("Panoramic-Normal", -45, 90, 0, 360,
					Precision.PRECISION_LEVEL_NORMAL));
			ret.add(new ScanConfig("Panoramic-Middle", -45, 90, 0, 360,
					Precision.PRECISION_LEVEL_MIDDLE));
			ret.add(new ScanConfig("Panoramic-High", -45, 90, 0, 360,
					Precision.PRECISION_LEVEL_HIGH));
			ret.add(new ScanConfig("Panoramic-Extra", -45, 90, 0, 360,
					Precision.PRECISION_LEVEL_EXTRA));

			return ret;
		}

		private static List<ScanConfig> EnumConfig(String path) {
			List<ScanConfig> ret = new ArrayList<ScanConfig>();
			List<String> fileList = Utils.getFiles(path,
					Internal.file_endwith.config);

			for (String file : fileList) {
				ScanConfig cfg = (ScanConfig) Utils.ReadSerializable(file);
				ret.add(cfg);
			}
			return ret;
		}

		@Override
		public void Load(String root) {
			String myDir = root == null ? Internal.config_path : root;
			list = EnumConfig(myDir);
			if (list.size() <= 0) {
				list = MakeDefaultConfig();
				Save(null);
			}
		}

		@Override
		public void Save(String root) {
			String myDir = root == null ? Internal.config_path : root;
			String fpath;
			for (ScanConfig cfg : list) {
				fpath = Utils.joinPath(myDir, cfg.name);
				fpath += "." + Internal.file_endwith.config[0];
				Utils.WriteSerializable(fpath, cfg);
			}
		}

		@Override
		public ScanConfig Get(String name) {
			for (ScanConfig cfg : list)
				if (cfg.name.equalsIgnoreCase(name)) {
					return cfg;
				}

			return null;
		}

		@Override
		public ScanConfig Get(int idx) {
			return list.get(idx);
		}

		@Override
		public boolean Contain(String name) {
			for (ScanConfig cfg : list)
				if (cfg.name.equalsIgnoreCase(name)) {
					return true;
				}

			return false;
		}

		@Override
		public boolean Put(ScanConfig config) {
			if (Contain(config.name))
				return false;

			return list.add(config);
		}

		@Override
		public boolean Remove(String name) {
			for (ScanConfig cfg : list)
				if (cfg.name.equalsIgnoreCase(name)) {
					return list.remove(cfg);
				}
			return false;
		}

		@Override
		public boolean Remove(int idx) {
			list.remove(idx);
			return true;
		}

		@Override
		public void Clear() {
			list.clear();
		}

		@Override
		public int Count() {
			return list.size();
		}

		private int cur = -1;

		@Override
		public void Select(int idx) {
			cur = idx % Count();
		}

		@Override
		public void Select(String name) {
			int len = list.size();
			for (int i = 0; i < len; i++)
				if (list.get(i).name == name) {
					cur = i;
					break;
				}
		}

		@Override
		public void Select(Object obj) {
			int len = list.size();
			for (int i = 0; i < len; i++)
				if (list.get(i) == obj) {
					cur = i;
					break;
				}
		}

		@Override
		public ScanConfig GetSelect() {
			return list.get(cur);
		}

		@Override
		public int Select() {
			return cur;
		}

		@Override
		public int SelectPriv() {
			cur = cur <= 0 ? Count() - 1 : (cur - 1) % Count();
			return cur;
		}

		@Override
		public int SelectNext() {
			cur = cur < 0 ? 0 : (cur + 1) % Count();
			return cur;
		}

		@Override
		public String[] toStringArray() {
			int len = Count();
			String[] ret = new String[len];
			for (int i = 0; i < len; i++)
				ret[i] = list.get(i).name;
			return ret;
		}

	}

	public static class Precisions {
		public static enum Precision {
			PRECISION_LEVEL_1, PRECISION_LEVEL_2, PRECISION_LEVEL_3, PRECISION_LEVEL_4, PRECISION_LEVEL_5,
			// /别名
			PRECISION_LEVEL_LOW, PRECISION_LEVEL_NORMAL, PRECISION_LEVEL_MIDDLE, PRECISION_LEVEL_HIGH, PRECISION_LEVEL_EXTRA,
		}

		public static String[] aviablePrecises = { "PRECISION_LEVEL_LOW",
				"PRECISION_LEVEL_NORMAL", "PRECISION_LEVEL_MIDDLE",
				"PRECISION_LEVEL_HIGH", "PRECISION_LEVEL_EXTRA",};

		public static String[] aviablePlusDelay = { "20", "50", "100", "150",
				"200", "250", "400", "700", "850", "1250", "2500", "5000" };

		public static String[] aviableFrequency = { "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20" };

		public static String[] aviableResolution = { "0.125", "0.25", "0.375",
				"0.5", "0.625", "0.75", "0.875", "1", "1.125", "1.25", "1.375",
				"1.5", "1.625", "1.75", "1.875", "2.0" };

		public static class precision implements Serializable {
			static final long serialVersionUID = 7932287852182643442L;
			// 前三个参数为决定性参数，后面的为计算出来的值。
			public float frequency; // 垂直扫描频率（Hz） //2.5 5 10 15 Hz
			public float resolution;// 垂直扫描精度（度） // 1.0 0.5 0.25 0.125 0.0625
			public int plus_delay;// 水平步进的脉冲延时 // 50 100 200 400 ns
			// extra 全景扫描数据的，宽度，高度，需要的时间（S）。
			transient int max_width, max_height, max_time;
			transient float angle_resolution; // 水平角分辨率

			public precision(float fre, float res, int delay, int w, int h,
					int time) {
				frequency = fre;
				resolution = res;
				plus_delay = delay;
				max_width = w;
				max_height = h;
				max_time = time;
				angle_resolution = (360 / time) * (1 / fre);
			}

			public precision(float fre, float res, int delay) {
				frequency = fre;
				resolution = res;
				plus_delay = delay;
				int time = (int) (180 * 100 * 80 * delay / 1e6);
				int w = (int) (time * fre);
				int h = (int) (360 / res);
				angle_resolution = (360 / time) * (1 / fre);
				max_width = w;
				max_height = h;
				max_time = time;
			}

			@Override
			public String toString() {
				return "[" + frequency + "," + resolution + "," + plus_delay
						+ "]";
			}

			public static precision fromString(String s) {
				String v[] = s.replace('[', ' ').replace(']', ' ').split(",");
				return new precision(Float.parseFloat(v[0]),
						Float.parseFloat(v[1]), Integer.parseInt(v[2]));
			}
		}

		static precision[] configs = new precision[] {
				new precision(5f, 0.5f, 50, 361, 271, 72),
				new precision(7f, 0.375f, 50, 505, 361, 72),
				new precision(5f, 0.25f, 100, 721, 541, 144),
				new precision(5f, 0.125f, 200, 1441, 1081, 288),
				new precision(7f, 0.0625f, 1250, 2884, 2164, 1800),
		// new precision(10f, 0.0625f, 850, 2884, 2164, 1224),
				};

		public static precision get(Precision p) {
			int idx = 0;
			switch (p) {
			case PRECISION_LEVEL_1:
			case PRECISION_LEVEL_LOW:
				idx = 0;
				break;
			case PRECISION_LEVEL_2:
			case PRECISION_LEVEL_NORMAL:
				idx = 1;
				break;
			case PRECISION_LEVEL_3:
			case PRECISION_LEVEL_MIDDLE:
				idx = 2;
				break;
			case PRECISION_LEVEL_4:
			case PRECISION_LEVEL_HIGH:
				idx = 3;
				break;
			case PRECISION_LEVEL_5:
			case PRECISION_LEVEL_EXTRA:
				idx = 4;
				break;
			default:
				Log.e(TAG, "Unrecognized precision.");
				return null;

			}
			return configs[idx];
		}

		public static precision get(int p) {
			return configs[p];
		}
	}

	public static class ScanConfig implements Serializable {
		static final long serialVersionUID = 7932287852182643441L;
		public String name;// 全景，粗扫，精扫
		public AreaF area;// {0,360, -45,90}
		public precision preci;

		public Bitmap Draw(int w, int h) {
			Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			// 图象大小要根据文字大小算下,以和文本长度对应
			Canvas ca = new Canvas(bmp);
			ca.drawColor(Color.WHITE);

			Paint p = new Paint();
			p.setAntiAlias(true);
			p.setStyle(Paint.Style.FILL_AND_STROKE);
			p.setColor(0x88FF0000);
			ca.drawArc(new RectF(0, 0, w, h), area.left, area.right, true, p);
			p = new Paint(p);
			p.setColor(0x8800FF00);
			p.setXfermode(new PorterDuffXfermode(Mode.DARKEN));
			ca.drawRect(0, h * (90 - area.top) / 180, w, h * (90 - area.bottom)
					/ 180, p);

			// String familyName = "宋体";
			// Typeface font = Typeface.create(familyName, Typeface.BOLD);
			// p.setColor(Color.RED);
			// p.setTypeface(font);
			// p.setTextSize(22);
			// canvasTemp.drawText(mstrTitle, 0, 100, p);

			return bmp;

		}

		// 直接创建
		public ScanConfig(String name, float start_angle_v, float end_angle_v,
				float start_angle_h, float end_angle_h, int speed_h,
				float speed_v, float resolution_v) {
			this.name = name;
			this.area = new AreaF(start_angle_h, end_angle_h, start_angle_v,
					end_angle_v);
			preci = new precision(speed_v, resolution_v, speed_h);
		}

		// 使用推荐精度配置创建
		public ScanConfig(String name, float start_angle_v, float end_angle_v,
				float start_angle_h, float end_angle_h, Precision prec) {
			this.name = name;
			this.area = new AreaF(start_angle_h, end_angle_h, start_angle_v,
					end_angle_v);
			this.preci = Precisions.get(prec);
		}

		// 使用推荐精度配置创建
		public ScanConfig(String name, float start_angle_v, float end_angle_v,
				float start_angle_h, float end_angle_h, int prec) {
			this.name = name;
			this.area = new AreaF(start_angle_h, end_angle_h, start_angle_v,
					end_angle_v);
			this.preci = Precisions.get(prec);
		}

		public ScanConfig(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			String ret;
			ret = String.format("Name:%s\nArea:%s\nPrecise:%s", name,
					area.toString(), preci.toString());
			return ret;
		}

		// 获取完成需要时间（秒）
		public int getTime() {
			return (int) ((area.width1() / 2) * 100 * 80 * preci.plus_delay / 1e6);
		}

		// 获取水平转一度需要时间（秒）
		public int getTimePerDegree() {
			return (int) (100 * 80 * preci.plus_delay / 1e6);
		}

		// TODO:获取其它信息

	}

	public static interface Manage extends Selectable {
		public void Load(String root);

		public void Save(String root);

		public ScanConfig Get(String name);

		public ScanConfig Get(int idx);

		public String[] toStringArray();

		public boolean Contain(String name);

		public boolean Put(ScanConfig config);

		public boolean Remove(String name);

		public boolean Remove(int idx);

		public int Count();

		public void Clear();

		public ScanConfig GetSelect();
	}
}
