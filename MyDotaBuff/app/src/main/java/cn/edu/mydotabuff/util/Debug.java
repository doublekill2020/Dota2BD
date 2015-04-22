package cn.edu.mydotabuff.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

/**
 * @ClassName: Debug	
 * @Description: TODO 日志输入打印类
 * @author 
 * @date 2014年12月15日 下午4:37:43
 * 
 */
public class Debug {

	/**
	 * @Fields ENABLE_DEBUG : TODO 日志打印开关------------------------传市场时关闭
	 */
	public static boolean ENABLE_DEBUG = true;

	/**
	 * @Fields MYLOG_PATH_SDCARD_DIR : TODO 日志保存目录
	 */
	public static final String MYLOG_PATH_SDCARD_DIR = "";

	/**
	 * @Fields SDCARD_LOG_FILE_SAVE_DAYS : TODO 日志保存天数
	 */
	private static int SDCARD_LOG_FILE_SAVE_DAYS = 0;

	/**
	 * @Fields MYLOG_NAME : TODO 日志文件名称
	 */
	private static final String MYLOG_NAME = "log.txt";

	public static void w(String tag, Object msg) {
		log(tag, msg.toString(), 'w');
	}

	public static void e(String tag, Object msg) {
		log(tag, msg.toString(), 'e');
	}

	public static void d(String tag, Object msg) {
		log(tag, msg.toString(), 'd');
	}

	public static void i(String tag, Object msg) {
		log(tag, msg.toString(), 'i');
	}

	public static void v(String tag, Object msg) {
		log(tag, msg.toString(), 'v');
	}

	public static void w(String tag, String text) {
		log(tag, text, 'w');
	}

	public static void e(String tag, String text) {
		log(tag, text, 'e');
	}

	public static void d(String tag, String text) {
		log(tag, text, 'd');
	}

	public static void i(String tag, String text) {
		log(tag, text, 'i');
	}

	public static void v(String tag, String text) {
		log(tag, text, 'v');
	}

	/** 
	* @Title: log 
	* @Description: TODO 日志打印实现
	* @param tag
	* @param msg
	* @param level     
	* @return void    
	* @throws 
	*/
	private static void log(String tag, String msg, char level) {
		// log print switcher
		if (ENABLE_DEBUG) {
			if ('e' == level) {
				Log.e(tag, msg);
			} else if ('w' == level) {
				Log.w(tag, msg);
			} else if ('d' == level) {
				Log.d(tag, msg);
			} else if ('i' == level) {
				Log.i(tag, msg);
			} else {
				Log.v(tag, msg);
			}
		}
	}

	/** 
	* @Title: writeLogtoFile 
	* @Description: TODO 写入日志到文件中
	* @param mylogtype
	* @param tag
	* @param text     
	* @return void    
	* @throws 
	*/
	private static void writeLogtoFile(String mylogtype, String tag, String text) {
		Date nowtime = new Date();
		FileWriter filerWriter = null;
		BufferedWriter bufWriter = null;
		String needWriteMessage = getFormatter().format(nowtime) + "    "
				+ mylogtype + "    " + tag + "    " + text;
		String rootDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		File dirfile = new File(rootDir + MYLOG_PATH_SDCARD_DIR);
		try {
			if (!dirfile.exists()) {
				Log.d("Log", "create Log Dir:" + dirfile.getAbsolutePath());
				dirfile.mkdir();
			}
			if (ENABLE_DEBUG) {
				File logFile = new File(dirfile, MYLOG_NAME);
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				filerWriter = new FileWriter(logFile, true);
				bufWriter = new BufferedWriter(filerWriter);
				bufWriter.write(needWriteMessage);
				bufWriter.newLine();
				bufWriter.flush();
				filerWriter.close();
				bufWriter.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} finally {
			filerWriter = null;
			bufWriter = null;
		}

	}

	/**
	 * @Title: delFile
	 * @Description: TODO 删除具体日志的日志文件
	 * @return void
	 * @throws
	 */
	public static void delFile() {
		String needDelFiel = getFormatter().format(getDateBefore());
		String rootDir = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		File file = new File(rootDir + MYLOG_PATH_SDCARD_DIR, needDelFiel
				+ MYLOG_NAME);
		if (file.exists()) {
			file.delete();
		}
	}

	/** 
	* @Title: getDateBefore 
	* @Description: TODO 获取几天以前的日志文件时间
	* @return     
	* @return Date    
	* @throws 
	*/
	private static Date getDateBefore() {
		Date nowtime = new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(nowtime);
		now.set(Calendar.DATE, now.get(Calendar.DATE)
				- SDCARD_LOG_FILE_SAVE_DAYS);
		return now.getTime();
	}

	/**
	 * @Title: getFormatter
	 * @Description: TODO 获取打印日志Formatter
	 * @return
	 * @return SimpleDateFormat
	 * @throws
	 */
	private static SimpleDateFormat getFormatter() {
		SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");
		return logfile;
	}
}
