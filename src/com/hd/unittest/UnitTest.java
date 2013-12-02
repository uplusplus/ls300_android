package com.hd.unittest;

import android.app.Activity;
import android.os.Bundle;

public class UnitTest extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		native_main();
	}
	
	static {
		System.loadLibrary("ecore");
		System.loadLibrary("ecorejni");
	}

	public static native void native_main();
}
