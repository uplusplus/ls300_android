package com.hd.base;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.util.Log;

public class ResLoader {

	/**
	 * @param res
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	public static void unpackResources(int id, Context C, String destFolder)
			throws FileNotFoundException, IOException {
		// Open the ZipInputStream
		ZipInputStream inputStream = new ZipInputStream(C.getResources()
				.openRawResource(id));

		// Loop through all the files and folders
		for (ZipEntry entry = inputStream.getNextEntry(); entry != null; entry = inputStream
				.getNextEntry()) {
			Log.i("ResLoader", "Extracting: " + entry.getName() + "...");

			String innerFileName = destFolder + File.separator
					+ entry.getName();
			File innerFile = new File(innerFileName);
			if (innerFile.exists()) {
				return;
			}

			// Check if it is a folder
			if (entry.isDirectory()) {
				// Its a folder, create that folder
				innerFile.mkdirs();
			} else {
				innerFile.getParentFile().mkdirs();
				// Create a file output stream
				FileOutputStream outputStream = new FileOutputStream(
						innerFileName);
				final int BUFFER = 2048;

				// Buffer the ouput to the file
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
						outputStream, BUFFER);

				// Write the contents
				int count = 0;
				byte[] data = new byte[BUFFER];
				while ((count = inputStream.read(data, 0, BUFFER)) != -1) {
					bufferedOutputStream.write(data, 0, count);
				}

				// Flush and close the buffers
				bufferedOutputStream.flush();
				bufferedOutputStream.close();
			}

			// Close the current entry
			inputStream.closeEntry();
		}
		inputStream.close();
	}

}