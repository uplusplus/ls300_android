package com.hd.internal;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.util.Log;

import com.hd.base.ResLoader;
import com.hd.R;

public class WebServer {
	private static String filesdir = null;

	private static String extractHTML(Context ctx) {
		if (filesdir == null) {
			filesdir = ctx.getFilesDir().getAbsolutePath();

			try {
				ResLoader.unpackResources(R.raw.html,
						ctx.getApplicationContext(), filesdir);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return filesdir;
	}

	private native static int start_webserver(String root_dir);

	private native static int stop_webserver();

	public static boolean StartWebServer(Context ctx) {
		Log.i("WebServer", "Start");
		return start_webserver(extractHTML(ctx)) == 1 ? true : false;
	}

	public static boolean StopWebServer() {
		Log.i("WebServer", "Stop");
		return stop_webserver() == 1 ? true : false;
	}

}
