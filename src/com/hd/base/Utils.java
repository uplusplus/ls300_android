package com.hd.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.thoughtworks.xstream.XStream;

public class Utils {
	private static final String TAG = "com.hd.base.Utils";

	public static class RectI {
		int xmin, ymin, xmax, ymax;

		public RectI(int xmin, int xmax, int ymin, int ymax) {
			this.xmin = xmin;
			this.ymin = ymin;
			this.xmax = xmax;
			this.ymax = ymax;
		}
	}

	public static class AreaF implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public float left, bottom, right, top;

		public float width() {
			return right - left;
		}

		public float width1() {// 实际使用的区域
			if (left < 180 && right > 180)
				return 360;
			return right - left;
		}

		public float height() {
			return top - bottom;
		}

		public AreaF(float xmin, float xmax, float ymin, float ymax) {
			this.left = xmin;
			this.bottom = ymin;
			this.right = xmax;
			this.top = ymax;
		}

		@Override
		public String toString() {
			return "[" + left + "," + right + "," + bottom + "," + top + "]";
		}

		public static AreaF fromString(String s) {
			String v[] = s.replace('[', ' ').replace(']', ' ').split(",");
			return new AreaF(Float.parseFloat(v[0]), Float.parseFloat(v[1]),
					Float.parseFloat(v[2]), Float.parseFloat(v[3]));
		}
	}

	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		} else {
			Log.e(TAG, "sd卡检测失败，检查是否有sd卡及程序是否开启了sd卡读写权限。");
			return null;
		}
		return sdDir.toString();

	}

	private static final String FormatDirPath(String dirPath) {
		if (!dirPath.endsWith(File.pathSeparator)) {
			dirPath += File.pathSeparator;
		}
		return dirPath;
	}

	private static String FormatSubDirPath(String dirPath) {
		if (dirPath.startsWith(File.pathSeparator)) {
			dirPath = dirPath.substring(1);
		}
		return dirPath;
	}

	public static final String PathCat(String p1, String sub_dir) {
		return FormatDirPath(p1) + FormatSubDirPath(sub_dir);
	}

	public static final String joinPath(String path1, String path2) {
		return new File(path1, path2).toString();
	}

	public static final String getFileName(String path) {
		String name = new File(path).getName();
		return name.substring(0, name.lastIndexOf("."));
	}

	public static final String getFileName(File f) {
		String name = f.getName();
		int spidx = name.lastIndexOf(".");
		if (spidx == -1) {
			return name;
		}
		return name.substring(0, spidx);
	}

	/**
	 * @param fName
	 * @return
	 */
	private static final boolean isImageFile(String fName) {
		boolean re;
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();

		/**
		 * 依据文件扩展名判断是否为图像文件
		 */
		if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}

	/**
	 * @param fName
	 * @return
	 */
	private static final boolean isTextFile(String fName) {
		boolean re;
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();

		/**
		 * 依据文件扩展名判断是否为文本文件
		 */
		if (end.equals("txt") || end.equals("log") || end.equals("text")
				|| end.equals("info")) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}

	public static final List<String> getImages(String srcDir) {
		List<String> imageList = new ArrayList<String>();

		File f = new File(srcDir);
		File[] files = f.listFiles();

		if (files == null || files.length == 0)
			return imageList;
		/**
		 * 将所有图像文件的路径存入ArrayList列表
		 */
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (isImageFile(file.getPath()))
				imageList.add(file.getPath());
		}
		return imageList;
	}

	/**
	 * @param fName
	 * @return
	 */
	private static final boolean isSuchFile(String fName, String[] endwith) {
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length());
		for (String ew : endwith) {
			if (end.equalsIgnoreCase(ew)) {
				return true;
			}
		}
		return false;
	}

	public static final List<String> getFiles(String srcDir, String[] endwith) {
		List<String> fList = new ArrayList<String>();

		File f = new File(srcDir);
		File[] files = f.listFiles();

		if (files == null || files.length == 0)
			return fList;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (isSuchFile(file.getPath(), endwith))
				fList.add(file.getPath());
		}
		return fList;
	}

	public static final String getFiles(String srcDir, String name,
			String[] endwith) {
		String ret = null;

		File f = new File(srcDir);
		File[] files = f.listFiles();

		if (files == null || files.length == 0)
			return null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (getFileName(file).equalsIgnoreCase(name)
					&& isSuchFile(file.getPath(), endwith))
				return file.getPath();
		}
		return ret;
	}

	public static final List<String> getTexts(String srcDir) {
		List<String> textList = new ArrayList<String>();

		File f = new File(srcDir);
		File[] files = f.listFiles();

		if (files == null || files.length == 0)
			return textList;
		/**
		 * 将所有文本文件的路径存入ArrayList列表
		 */
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (isTextFile(file.getPath()))
				textList.add(file.getPath());
		}
		return textList;
	}

	/**
	 * 从path中获取图片信息
	 * 
	 * @param path
	 * @return
	 */
	public static final Bitmap decodeBitmap(String path, int w, int h) {
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(path, op); // 获取尺寸信息
		// 获取比例大小
		int wRatio = (int) Math.ceil(op.outWidth / w);
		int hRatio = (int) Math.ceil(op.outHeight / h);
		// 如果超出指定大小，则缩小相应的比例
		if (wRatio > 1 && hRatio > 1) {
			if (wRatio > hRatio) {
				op.inSampleSize = wRatio;
			} else {
				op.inSampleSize = hRatio;
			}
		}
		op.inJustDecodeBounds = false;
		bmp = BitmapFactory.decodeFile(path, op);
		return bmp;
	}

	public static final Bitmap decodeBitmap(String path) {
		return BitmapFactory.decodeFile(path);
	}

	public static final String readToString(String fileName) {
		String encoding = "GB2312";// "ISO-8859-1";
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			Log.e("readToString", "The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}

	private static class ChineseFileComparator implements Comparator<File> {
		Collator cmp = Collator.getInstance(java.util.Locale.CHINA);

		@Override
		public int compare(File f1, File f2) {
			return cmp.compare(f1.getName(), f1.getName());
		}
	}

	private static ChineseFileComparator cfc = new ChineseFileComparator();

	public static File[] ListFiles(File f) {
		String[] args = { "一", "二", "三", "四", "五" };
		Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
		Arrays.sort(args, cmp);

		File[] subfiles = f.listFiles();
		Arrays.sort(subfiles, cfc);
		return subfiles;
	}

	private static XStream xs = new XStream();

	public static boolean WriteSerializable(String fpath, Serializable obj) {

		// Write to a file in the file system
		try {
			FileOutputStream fs = new FileOutputStream(fpath);
			xs.toXML(obj, fs);
			fs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}

		// ObjectOutputStream o;
		// try {
		// o = new ObjectOutputStream(new FileOutputStream(fpath));
		// o.writeObject(obj);
		// o.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }

		return true;
	}

	public static Serializable ReadSerializable(String fpath) {
		Serializable ret = null;
		try {
			FileInputStream fis = new FileInputStream(fpath);
			ret = (Serializable) xs.fromXML(fis);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}

		/*
		 * ObjectInputStream in; try { in = new ObjectInputStream(new
		 * FileInputStream(fpath)); ret = (Serializable) in.readObject();
		 * in.close(); } catch (Exception e) { Log.e(TAG, "打开" + fpath + "失败:" +
		 * e.getMessage()); }
		 */
		return ret;
	}

	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date d = new Date();
		String dd = format.format(d);
		return dd;
	}

}
