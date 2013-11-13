/**
 * 
 */
package com.hd.egl;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * @author uplusplus
 * 
 */

public class EGLSurfaceView extends GLSurfaceView {

	EglRenderer mRenderer;
	Timer t;
	TimerTask task;
	
	private void init(Context ctx){
		this.buildDrawingCache(true);
		mRenderer = new EglRenderer(ctx);
		setRenderer(mRenderer);
		setRenderMode(RENDERMODE_WHEN_DIRTY);
	}

	private void autoUpdate(){
		t = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				EGLSurfaceView.this.requestRender();
			}

		};
		t.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	private void stopUpdate(){
		t.cancel();
	}
	
	public EGLSurfaceView(Context context) {
		super(context);
		init(context);
	}

	public EGLSurfaceView(Context context, AttributeSet attrs) {
		super(context,attrs);
		init(context);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		autoUpdate();
	}
	
	@Override
	public void onPause(){
		stopUpdate();
		super.onPause();
	}
	
	static {
		System.loadLibrary("egl");
	}
}