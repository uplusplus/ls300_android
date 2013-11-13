package com.hd.base;

import java.util.List;

import android.app.Activity;

public class BaseApplication {
	public static BaseApplication ins;
	public static List<Activity> acts;

	public BaseApplication get() {
		return ins == null ? new BaseApplication() : ins;
	}
}
