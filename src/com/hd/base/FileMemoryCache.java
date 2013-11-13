package com.hd.base;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.support.v4.util.LruCache;
import android.util.Log;

public abstract class FileMemoryCache<T> {
	// ����8MӲ����ռ�
	private final int hardCachedSize = 8 * 1024 * 1024;
	// hard cache
	private final LruCache<String, T> sHardTCache = new LruCache<String, T>(
			hardCachedSize) {
		@Override
		public int sizeOf(String key, T value) {
			int size = getElementSize(key, value);
			return size;
		}

		@Override
		protected void entryRemoved(boolean evicted, String key, T oldValue,
				T newValue) {
			Log.v("tag", "hard cache is full , push to soft cache");
			// Ӳ���û�������һ�������ʹ�õ�oldvalue���뵽�����û�����
			sSoftTCache.put(key, new SoftReference<T>(oldValue));
		}
	};

	public abstract int getElementSize(String key, T value);

	// ������
	private static final int SOFT_CACHE_CAPACITY = 40;
	private final LinkedHashMap<String, SoftReference<T>> sSoftTCache = new LinkedHashMap<String, SoftReference<T>>(
			SOFT_CACHE_CAPACITY, 0.75f, true) {
		private static final long serialVersionUID = -4807846157938714970L;

		@Override
		public SoftReference<T> put(String key, SoftReference<T> value) {
			return super.put(key, value);
		}

		@Override
		protected boolean removeEldestEntry(
				LinkedHashMap.Entry<String, SoftReference<T>> eldest) {
			Log.v("sSoftTCache", "Soft Reference =" + size());
			if (size() > SOFT_CACHE_CAPACITY) {
				Log.v("tag", "Soft Reference limit , purge one");
				return true;
			}
			return false;
		}
	};

	// ����T
	public boolean put(String key, T t) {
		if (t != null) {
			synchronized (sHardTCache) {
				sHardTCache.put(key, t);
			}
			return true;
		}
		return false;
	}

	// �ӻ����л�ȡT
	public T get(String key) {
		synchronized (sHardTCache) {
			final T t = sHardTCache.get(key);
			if (t != null)
				return t;
		}
		// Ӳ���û�������ж�ȡʧ�ܣ��������û�������ȡ
		synchronized (sSoftTCache) {
			SoftReference<T> TReference = sSoftTCache.get(key);
			if (TReference != null) {
				final T t = TReference.get();
				if (t != null)
					return t;
				else {
					Log.v("tag", "soft reference �Ѿ�������");
					sSoftTCache.remove(key);
				}
			}
		}
		return null;
	}
}