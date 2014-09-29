package cn.edu.mydotabuff.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import android.os.Environment;

public class FileUtils {
	private int hasRead = 0;
	private static int index;
	private String SDPATH = null;

	public String getSDPATH() {
		return SDPATH;
	}

	public FileUtils() {
		// 获得当前外部存储设备SD卡的目录
		SDPATH = Environment.getExternalStorageDirectory() + "/";
	}

	// 创建文件
	public File createFile(String fileName) {
		File file = new File(SDPATH + fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("createFile fileName==" + fileName);
			System.out.println("createFile 报错了==" + e);
			// createFile(fileName);
		}
		return file;
	}

	// 创建目录
	public File createDir(String fileName) throws IOException {
		File dir = new File(SDPATH + fileName);
		dir.mkdirs();
		return dir;
	}

	// 判断文件是否存在
	public boolean isExist(String fileName) {
		File file = new File(SDPATH + fileName);
		return file.exists();
	}

	// 把输入流写入文件里
	public File writeToSDPATHFromInput(String path, String fileName,
			InputStream inputstream, Long length) {
		File file = null;
		// 已读出流作为参数创建一个带有缓冲的输出流
		BufferedInputStream bis = new BufferedInputStream(inputstream);
		FileOutputStream outputstream = null;
		try {
			File dir = createDir(path);
			System.out.println("writeToSDPATHFromInput  dir==" + dir);
			file = createFile(path + fileName);
			outputstream = new FileOutputStream(file);
			// 已写入流作为参数创建一个带有缓冲的写入流
			BufferedOutputStream bos = new BufferedOutputStream(outputstream);
			int read;
			// long count = 0;
			// int precent = 0;
			byte[] buffer = new byte[1024];
			while ((read = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, read);
				// count += read;
				// precent = (int) (((double) count / length) * 100);
				// 每下载完成1%就通知任务栏进行修改下载进度
				// if (precent - download_precent >= 1) {
				// download_precent = precent;
				// Message message = myHandler.obtainMessage(3, precent);
				// myHandler.sendMessage(message);
				// }
			}
			bos.flush();
			bos.close();
			outputstream.flush();
			outputstream.close();
			inputstream.close();
			bis.close();
		} catch (Exception e) {
		} finally {
			try {
				outputstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 调用此方法自动计算指定文件或指定文件夹的大小
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 计算好的带B、KB、MB、GB的字符串
	 */
	public static String getAutoFileOrFilesSize(String filePath) {
		File file = new File(Environment.getExternalStorageDirectory() + "/"
				+ filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFileSizes(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FormetFileSize(blockSize);
	}

	/**
	 * 获取指定文件大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
		}
		return size;
	}

	/**
	 * 获取指定文件夹
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private static long getFileSizes(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSizes(flist[i]);
			} else {
				size = size + getFileSize(flist[i]);
			}
		}
		return size;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return
	 */
	private static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		String wrongSize = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	/**
	 * 递归删除文件和文件夹
	 * 
	 * @param file
	 *            要删除的根目录
	 */
	public static String DeletePackAndFile(File file) {
		if (file.exists() == false) {
			return "failed";
		} else {
			if (file.isFile()) {
				final File to = new File(file.getAbsolutePath()
						+ System.currentTimeMillis());
				file.renameTo(to);
				to.delete();
			}
			if (file.isDirectory()) {
				File[] childFile = file.listFiles();
				if (childFile == null || childFile.length == 0) {
					file.delete();
				}
				for (File f : childFile) {
					DeletePackAndFile(f);
				}
				// file.delete();
			}
			return getAutoFileOrFilesSize(file.getAbsolutePath());
		}
	}
}