/**
 * 
 */
package com.hd.egl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.hd.base.Utils;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * @author uplusplus
 * 
 */

class EglRenderer implements GLSurfaceView.Renderer {
	Context ctx;

	public EglRenderer(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		String filesDir = ctx.getFilesDir().getAbsolutePath();
		nativeInit(Utils.joinPath(filesDir,"egl.sprite"));
	}

	public void onSurfaceDestoryed(GL10 gl, EGLConfig config) {
		nativeDone();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		int tmp;
/*		if (w < h) {
			tmp = w;
			w = h;
			h = tmp;
		}*/
		nativeResize(w, h);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		nativeRender();
	}

	private static native void nativeInit(String tmp_dir);

	private static native void nativeResize(int w, int h);

	private static native void nativeRender();

	private static native void nativeDone();

}