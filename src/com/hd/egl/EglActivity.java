package com.hd.egl;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class EglActivity extends Activity {

	private GLSurfaceView mGLView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		mGLView = new EGLSurfaceView(this);
		mGLView.setKeepScreenOn(true);
		setContentView(mGLView);
	}

//	@Override
//	protected void onResume() {
//		/**
//		 * 设置为横屏
//		 */
//		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		}
//		super.onResume();
//	}

}
