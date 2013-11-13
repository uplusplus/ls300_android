package com.hd.base;

import android.graphics.Bitmap;

public class ImageCache extends FileMemoryCache<Bitmap> {
	public Bitmap Get(String path) {
		Bitmap ret = get(path);
		if (ret == null) {
			ret = Utils.decodeBitmap(path);
			if (ret == null)
				return null;
			put(path, ret);
		}
		return ret;
	}

	public Bitmap Get(String path, int w, int h) {
		String key = path + w + "x" + h;
		Bitmap ret = get(key);
		if (ret == null) {
			ret = Utils.decodeBitmap(path, w, h);
			if (ret == null)
				return null;
			put(key, ret);
		}
		return ret;
	}

	@Override
	public int getElementSize(String key, Bitmap bitmap) {
		int ret = bitmap.getRowBytes() * bitmap.getHeight();
		return ret;
	}
}