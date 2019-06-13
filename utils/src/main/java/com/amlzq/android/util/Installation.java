package com.amlzq.android.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * Created by Administrator on 2015/9/14. <br>
 * 在程序安装后第一次运行时生成一个ID，该方式和设备唯一标识不一样， <br>
 * 不同的应用程序会产生不同的ID，同一个程序重新安装也会不同。所以这不是设备的唯一ID， <br>
 * 但是可以保证每个用户的ID是不同的。 <br>
 * 可以说是用来标识每一份应用程序的唯一ID（即InstalltionID），可以用来跟踪应用的安装数量等（其实就是UUID）。 <br>
 */
public class Installation {
	private static String sID = null;
	private static final String INSTALLATION = "INSTALLATION";

	public synchronized static String id(Context context) {
		if (sID == null) {
			File installation = new File(context.getFilesDir(), INSTALLATION);
			try {
				if (!installation.exists()) {
					writeInstallationFile(installation);
					sID = readInstallationFile(installation);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return sID;
	}

	private static String readInstallationFile(File installation) throws IOException {
		RandomAccessFile f = new RandomAccessFile(installation, "r");
		byte[] bytes = new byte[(int) f.length()];
		f.readFully(bytes);
		f.close();
		return new String(bytes);
	}

	private static void writeInstallationFile(File installation) throws IOException {
		FileOutputStream out = new FileOutputStream(installation);
		String id = UUID.randomUUID().toString();
		out.write(id.getBytes());
		out.close();
	}
}
