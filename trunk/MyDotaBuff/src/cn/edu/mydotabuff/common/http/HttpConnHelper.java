package cn.edu.mydotabuff.common.http;

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class HttpConnHelper {

	/**
	 * 默认编码utf-8"
	 */
	public static final String DEFAULT_ENCODING = "utf-8";
	private static String encoding = DEFAULT_ENCODING;

	public static String getEncoding() {
		return encoding;
	}

	public static void setEncoding(String encoding) {
		HttpConnHelper.encoding = encoding;
	}

	/**
	 * 供外部调用的GET接口
	 */
	public static String httpGetFromServerStr(String path) throws Exception {
		byte[] temp = httpGetFromServer(path, encoding);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(temp);
		return baos.toString();
	}

	/**
	 * 供外部调用的GET接口
	 */
	public static byte[] httpGetFromServer(String path) throws Exception {
		return httpGetFromServer(path, encoding);
	}

	/**
	 * HTTP get方式请求服务器的资源
	 * 
	 * @param path
	 *            :服务器URL地址
	 * @return byte[]
	 */
	private static byte[] httpGetFromServer(String path, String encoding)
			throws Exception {
		byte[] result = null;
		InputStream is = null;
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(3000);
		conn.setRequestMethod("GET");
		conn.connect();
		int code = conn.getResponseCode();//连接服务器状态
		switch (code) {
		case 200: {// 连接服务器成功返回的数据
			is = conn.getInputStream();
			int len = 0;
			byte[] buff = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = is.read(buff)) != -1) {
				baos.write(buff, 0, len);
				baos.flush();
			}
			// String temp = new String(baos.toByteArray(), encoding);
			// result = temp.getBytes();
			result = baos.toByteArray();
			baos.close();
			is.close();

		}
			break;
		case 404: {// 请求的网页不存在
			String temp = "{\"data\":\"\",\"info\":\"网络问题!\",\"status\":404}";
			result = temp.getBytes();
			break;
		}
		case 503: {// 服务器超�?
			String temp = "{\"data\":\"\",\"info\":\"连接服务器超�?\",\"status\":503}";
			result = temp.getBytes();
			break;
		}
		}
		conn.disconnect();
		return result;
	}

	/**
	 * 供外部调用的POST接口。。。。。递交
	 */
	public static String httpPostFromServerStr(String path,
			Map<String, String> params) throws Exception {
		byte[] temp = httpPostFromServer(path, params, encoding);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(temp);
		return baos.toString();
	}

	/**
	 * 供外部调用的POST接口
	 */
	public static byte[] httpPostFromServer(String path,
			Map<String, String> params) throws Exception {
		return httpPostFromServer(path, params, encoding);
	}

	/**
	 * HTTP post方式请求服务器的资源
	 * 
	 * @param path
	 *            :服务器URL地址
	 * @param params
	 *            :要传入的请求参数
	 * @param encoding
	 *            :请求URL中使用的编码
	 * @return byte[]
	 */
	private static byte[] httpPostFromServer(String path,
			Map<String, String> params, String encoding) throws Exception {
		byte[] result = null;
		InputStream is = null;
		StringBuilder sb = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey()).append("=");
				sb.append(URLEncoder.encode(entry.getValue(), encoding));
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		byte[] data = sb.toString().getBytes();
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		conn.setRequestProperty("Content-Length", data.length + "");
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		conn.connect();//递交之后再连接
		int code = conn.getResponseCode();
		switch (code) {
		case 200: {
			is = conn.getInputStream();
			int len = 0;
			byte[] buff = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = is.read(buff)) != -1) {
				baos.write(buff, 0, len);
				baos.flush();
			}
			String temp = new String(baos.toByteArray(), encoding);
			result = temp.getBytes();
			baos.close();
			is.close();
		}
			break;
		case 404: {// 请求的网页不存在
			String temp = "{\"data\":\"\",\"info\":\"网络问题!\",\"status\":404}";
			result = temp.getBytes();

		}
			break;
		case 503: {// 服务器超�?
			String temp = "{\"data\":\"\",\"info\":\"连接服务器超�?\",\"status\":503}";
			result = temp.getBytes();
		}
			break;
		case 500: // 服务器内部错�?
			String temp = "{\"data\":\"\",\"info\":\"网络问题!\",\"status\":500}";
			result = temp.getBytes();
			break;
		}
		conn.disconnect();
		return result;
	}

	/**
	 * @param 只发送普通数
	 *            �? ,调用此方�?
	 * @param urlString
	 *            对应的Php 页面
	 * @param params
	 *            �?��发�?的相关数�?包括调用的方�?
	 * @paramimage 图片字节数组或�?文件字节数组
	 * @paramimg 图片名称
	 * @return Json
	 */
	public static String doPost(String urlString,
			Map<String, ? extends Object> params, byte[] image, String img) {
		String result = "";

		String end = "\r\n";
		String uploadUrl = "";// new BingoApp().URLIN 是我定义的上传URL
		String MULTIPART_FORM_DATA = "multipart/form-data";
		String BOUNDARY = "---------7d4a6d158c9"; // 数据分隔�?
		String imguri = "";
		Random random = new Random();
		int temp = random.nextInt();
		imguri = temp + "sdfse" + ".jpg";
		if (!urlString.equals("")) {
			uploadUrl = uploadUrl + urlString;

			try {
				URL url = new URL(uploadUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoInput(true);// 允许输入
				conn.setDoOutput(true);// 允许输出
				conn.setUseCaches(false);// 不使用Cache
				conn.setConnectTimeout(6000);// 6秒钟连接超时
				conn.setReadTimeout(6000);// 6秒钟读数据超�?
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charset", "UTF-8");
				conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
						+ "; boundary=" + BOUNDARY);

				StringBuilder sb = new StringBuilder();

				// 上传的表单参数部分，格式请参考文�?
				for (Map.Entry<String, ? extends Object> entry : params
						.entrySet()) {// 构建表单字段内容
					sb.append("--");
					sb.append(BOUNDARY);
					sb.append("\r\n");
					sb.append("Content-Disposition: form-data; name=\""
							+ entry.getKey() + "\"\r\n\r\n");
					sb.append(entry.getValue());
					sb.append("\r\n");
				}

				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");

				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				dos.write(sb.toString().getBytes());

				if (!imguri.equals("") && !imguri.equals(null)) {
					dos.writeBytes("Content-Disposition: form-data; name=\""
							+ img + "\"; filename=\"" + imguri + "\"" + "\r\n"
							+ "Content-Type: image/jpeg\r\n\r\n");

					dos.write(image, 0, image.length);

					dos.writeBytes(end);

					dos.writeBytes("--" + BOUNDARY + "--\r\n");
					dos.flush();

					int code = conn.getResponseCode();
					switch (code) {
					case 200: {
						InputStream is = conn.getInputStream();
						InputStreamReader isr = new InputStreamReader(is,
								"utf-8");
						BufferedReader br = new BufferedReader(isr);
						result = br.readLine();
					}
						break;
					case 404: {// 请求的网页不存在
						String failed = "{\"data\":\"\",\"info\":\"网络问题!\",\"status\":404}";
						result = failed;
					}
						break;
					case 503: {// 服务器超�?
						String failed = "{\"data\":\"\",\"info\":\"连接服务器超�?\",\"status\":503}";
						result = failed;
					}
						break;
					}
				}
			} catch (Exception e) {
				result = "{\"ret\":\"898\"}";
			}
		}
		return result;

	}

	/**
	 * @param 只发送普通数
	 *            �? ,调用此方�?
	 * @param urlString
	 *            对应的Php 页面
	 * @param params
	 *            �?��发�?的相关数�?包括调用的方�?
	 * @param imageuri
	 *            图片或文件手机上的地�?�?sdcard/photo/123.jpg
	 * @param img
	 *            图片名称
	 * @return Json
	 */
	public static String doPost(String urlString, Map<String, Object> params,
			String imageuri, String img) {
		String result = "";

		String end = "\r\n";
		String uploadUrl = "";// new BingoApp().URLIN 是我定义的上传URL
		String MULTIPART_FORM_DATA = "multipart/form-data";
		String BOUNDARY = "---------7d4a6d158c9"; // 数据分隔�?
		String imguri = "";
		if (!imageuri.equals("")) {
			imguri = imageuri.substring(imageuri.lastIndexOf("/") + 1);// 获得图片或文件名�?
		}

		if (!urlString.equals("")) {
			uploadUrl = uploadUrl + urlString;

			try {
				URL url = new URL(uploadUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoInput(true);// 允许输入
				conn.setDoOutput(true);// 允许输出
				conn.setUseCaches(false);// 不使用Cache
				conn.setConnectTimeout(6000);// 6秒钟连接超时
				conn.setReadTimeout(6000);// 6秒钟读数据超�?
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charset", "UTF-8");
				conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
						+ "; boundary=" + BOUNDARY);

				StringBuilder sb = new StringBuilder();

				// 上传的表单参数部分，格式请参考文�?
				for (Map.Entry<String, Object> entry : params.entrySet()) {// 构建表单字段内容
					sb.append("--");
					sb.append(BOUNDARY);
					sb.append("\r\n");
					sb.append("Content-Disposition: form-data; name=\""
							+ entry.getKey() + "\"\r\n\r\n");
					sb.append(entry.getValue());
					sb.append("\r\n");
				}

				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");

				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				dos.write(sb.toString().getBytes());

				if (!imageuri.equals("") && !imageuri.equals(null)) {
					dos.writeBytes("Content-Disposition: form-data; name=\""
							+ img + "\"; filename=\"" + imguri + "\"" + "\r\n"
							+ "Content-Type: image/jpeg\r\n\r\n");
					FileInputStream fis = new FileInputStream(imageuri);
					byte[] buffer = new byte[1024]; // 8k
					int count = 0;
					while ((count = fis.read(buffer)) != -1) {
						dos.write(buffer, 0, count);
					}
					dos.writeBytes(end);
					fis.close();
				}
				dos.writeBytes("--" + BOUNDARY + "--\r\n");
				dos.flush();

				InputStream is = conn.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(isr);
				result = br.readLine();

			} catch (Exception e) {
				result = "{\"ret\":\"898\"}";
			}
		}
		return result;
	}

	/**
	 * 
	 * @param urlString
	 * @param params
	 * @param files
	 *            图片或文件手机上的地�?sdcard/photo/123.jpg
	 * @return
	 */
	public static String doPost(String urlString, Map<String, Object> params,
			List<Map<String, Object>> files) {
		String result = "";
		String end = "\r\n";
		String uploadUrl = "";// new BingoApp().URLIN 是我定义的上传URL
		String MULTIPART_FORM_DATA = "multipart/form-data";
		String BOUNDARY = "---------7d4a6d158c9"; // 数据分隔�?
		List<String> imgname = new ArrayList<String>();// 图片名称
		final int size = files.size(); // 取得多张图片

		for (int i = 0; i < size; i++) {
			String imageuri = (String) files.get(i).get("path");
			if (!imageuri.equals("")) {
				imgname.add(imageuri.substring(imageuri.lastIndexOf("/") + 1));// 获得图片或文件名�?
			}
		}
		if (!urlString.equals("")) {
			uploadUrl = uploadUrl + urlString;

			try {
				URL url = new URL(uploadUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoInput(true);// 允许输入
				conn.setDoOutput(true);// 允许输出
				conn.setUseCaches(false);// 不使用Cache
				conn.setConnectTimeout(10000);// 6秒钟连接超时
				conn.setReadTimeout(10000);// 6秒钟读数据超�?
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charset", "UTF-8");
				conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
						+ "; boundary=" + BOUNDARY);

				StringBuilder sb = new StringBuilder();
				if (params != null) {
					// 上传的表单参数部分，格式请参考文�?
					for (Map.Entry<String, Object> entry : params.entrySet()) {// 构建表单字段内容
						sb.append("--");
						sb.append(BOUNDARY);
						sb.append("\r\n");
						sb.append("Content-Disposition: form-data; name=\""
								+ entry.getKey() + "\"\r\n\r\n");
						sb.append(entry.getValue());
						sb.append("\r\n");
					}
				}
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());

				for (int i = 0; i < size; i++) {
					String imageuri = (String) files.get(i).get("path");
					if (!imageuri.equals("") && !imageuri.equals(null)) {
						sb.append("--");
						sb.append(BOUNDARY);
						sb.append("\r\n");
						dos.write(sb.toString().getBytes());

						// String contentStr =
						// "Content-Disposition: form-data; name=\"image[" + i +
						// "]\"; filename=\"" + imgname.get(i) + "\"" + "\r\n" +
						// "Content-Type: image/jpeg\r\n\r\n";
						String contentStr = "Content-Disposition: form-data; name=\"image\"; filename=\""
								+ imgname.get(i).replace(".cach", "")
								+ "\""
								+ "\r\n" + "Content-Type: image/jpeg\r\n\r\n";
						dos.writeBytes(contentStr);
						FileInputStream fis = new FileInputStream(imageuri);
						byte[] buffer = new byte[1024]; // 8k
						int count = 0;
						while ((count = fis.read(buffer)) != -1) {
							dos.write(buffer, 0, count);
						}
						dos.writeBytes(end);
						fis.close();
						dos.writeBytes("--" + BOUNDARY + "--\r\n");
					}
				}

				dos.flush();

				int code = conn.getResponseCode();
				switch (code) {
				case 200: {
					InputStream is = conn.getInputStream();
					InputStreamReader isr = new InputStreamReader(is, "utf-8");
					BufferedReader br = new BufferedReader(isr);
					result = br.readLine();
				}
					break;
				case 404: {// 请求的网页不存在
					String temp = "{\"data\":\"\",\"info\":\"网络问题!\",\"status\":404}";
					result = temp;
				}
					break;
				case 503: {// 服务器超�?
					String temp = "{\"data\":\"\",\"info\":\"连接服务器超�?\",\"status\":503}";
					result = temp;
				}
					break;
				}
			} catch (Exception e) {
				result = "{\"ret\":\"898\"}";
			}
		}
		return result;

	}
}
