package cn.edu.mydotabuff.http;

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpRequestImpl {

	/**
	 * 默认编码utf-8"
	 */
	public static final String DEFAULT_ENCODING = "utf-8";
	private static String encoding = DEFAULT_ENCODING;

	public static final String CONTENT_TYPE_FORMDATA = "application/x-www-form-urlencoded;charset=UTF-8";
	public static final String CONTENT_TYPE_JPEG = "image/jpeg\r\n\r\n";
	public static final String CONTENT_TYPE_AUDIO = "audio/amr\r\n\r\n";

	public static String getEncoding() {
		return encoding;
	}

	public static void setEncoding(String encoding) {
		HttpRequestImpl.encoding = encoding;
	}

	private static final int DEFAULT_CONNECTION_TIMEOUT = 10 * 1000;
	private static final int DEFAULT_READ_TIMEOUT = 10 * 1000;

	/**
	 * 连接超时
	 */
	public static int CONNECTION_TIMEOUT = DEFAULT_CONNECTION_TIMEOUT;
	/**
	 * 读取超时
	 */
	public static int READ_TIMEOUT = DEFAULT_READ_TIMEOUT;

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
		conn.setConnectTimeout(CONNECTION_TIMEOUT);// 5000
		conn.setReadTimeout(READ_TIMEOUT); // 3000
		conn.setRequestMethod("GET");
		conn.connect();
		int code = conn.getResponseCode();
		switch (code) {
		case 200: {// 服务器成功返回网�?
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
		// HttpGet httpRequest = new HttpGet(path);
		// String strResult = "";
		// try {
		// HttpClient httpClient = new DefaultHttpClient();
		// HttpResponse httpResponse = httpClient.execute(httpRequest);
		// if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		// {
		// strResult = EntityUtils.toString(httpResponse.getEntity());
		// }
		// } catch (ClientProtocolException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		return result;
	}

	/**
	 * 供外部调用的POST接口
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
	 * post 表单方式请求服务器资�?
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
		conn.setConnectTimeout(CONNECTION_TIMEOUT);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", CONTENT_TYPE_FORMDATA);
		conn.setRequestProperty("Content-Length", data.length + "");
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		conn.connect();
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

	// 上传单纯文字给服务器dopost
	public static String doPost(String urlString, String mobiles) {
		String result = "";
		HttpPost httpRequest = new HttpPost(urlString);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mobiles", mobiles));
		// 发出HTTP request
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			switch (httpResponse.getStatusLine().getStatusCode()) {

			case 200: {

				result = EntityUtils.toString(httpResponse.getEntity());
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

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "post error";
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "post error";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "post error";
		}
		// 取得HTTP response
		return result;

	}

	/**
	 * post 上传文件以字节流方式
	 * 
	 * @param urlString
	 *            对应的Php 页面
	 * @param params
	 *            �?��发�?的相关数�?包括调用的方�?
	 * @param image
	 *            图片字节数组或�?文件字节数组
	 * @param filename
	 *            给服务的文件名称
	 * @param contentType
	 *            for example {@link}HttpRequestImpl.CONTENT_TYPE_FORMDATA
	 * @return Json
	 */
	public static String doPost(String urlString,
			Map<String, ? extends Object> params, byte[] image,
			String filename, String contentType) {
		String result = "";

		String end = "\r\n";
		String uploadUrl = "";// new BingoApp().URLIN 是我定义的上传URL
		String MULTIPART_FORM_DATA = "multipart/form-data";
		String BOUNDARY = "---------7d4a6d158c9"; // 数据分隔�?
		Random random = new Random();
		int temp = random.nextInt();
		String img = "file" + temp;
		if (!urlString.equals("")) {
			uploadUrl = uploadUrl + urlString;

			try {
				URL url = new URL(uploadUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoInput(true);// 允许输入
				conn.setDoOutput(true);// 允许输出
				conn.setUseCaches(false);// 不使用Cache
				conn.setConnectTimeout(CONNECTION_TIMEOUT);// 6秒钟连接超时
				conn.setReadTimeout(READ_TIMEOUT);// 6秒钟读数据超�?
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

				if (!filename.equals("") && !filename.equals(null)) {
					dos.writeBytes("Content-Disposition: form-data; name=\""
							+ img + "\"; filename=\"" + filename + "\""
							+ "\r\n" + "Content-Type: " + contentType);

					dos.write(image, 0, image.length);

					dos.writeBytes(end);

					dos.writeBytes("--" + BOUNDARY + "--\r\n");
					dos.flush();

					int code = conn.getResponseCode();
					switch (code) {
					case 200: {
						InputStream is = conn.getInputStream();
						// InputStreamReader isr = new
						// InputStreamReader(is,"utf-8");
						// BufferedReader br = new BufferedReader(isr);
						// result = br.readLine();
						int len = 0;
						byte[] buff = new byte[1024];
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						while ((len = is.read(buff)) != -1) {
							baos.write(buff, 0, len);
							baos.flush();
						}
						result = baos.toString();
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
				// Log.e("cc", "exception:"+e.getMessage());
				result = "{\"ret\":\"post error\"}";
			}
		}
		return result;

	}

	/**
	 * post 本地文件以Uri方式
	 * 
	 * @param urlString
	 *            对应的Php 页面
	 * @param params
	 *            �?��发�?的相关数�?包括调用的方�?
	 * @param imageuri
	 *            图片或文件手机上的地�?�?sdcard/photo/123.jpg
	 * @param contentType
	 *            for example {@link}HttpRequestImpl.CONTENT_TYPE_FORMDATA
	 * @return Json
	 */
	public static String doPost(String urlString,
			Map<String, ? extends Object> params, String imageuri,
			String contentType) {
		String result = "";
		String end = "\r\n";
		String uploadUrl = "";// new BingoApp().URLIN 是我定义的上传URL
		String MULTIPART_FORM_DATA = "multipart/form-data";
		String BOUNDARY = "---------7d4a6d158c9"; // 数据分隔
		String imguri = "";
		Random random = new Random();
		int temp = random.nextInt();
		String img = "file" + temp;
		if (!imageuri.equals("")) {
			imguri = imageuri.substring(imageuri.lastIndexOf("/") + 1);// 获得图片或文件名�?
			if (imguri.contains(".cach")) {
				imguri = imguri.replace(".cach", "");
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
				conn.setConnectTimeout(CONNECTION_TIMEOUT);// 6秒钟连接超时
				conn.setReadTimeout(READ_TIMEOUT);// 6秒钟读数据超
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charset", "UTF-8");
				conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
						+ "; boundary=" + BOUNDARY);

				StringBuilder sb = new StringBuilder();

				// 上传的表单参数部分，格式请参考文�?
				for (Entry<String, ? extends Object> entry : params.entrySet()) {// 构建表单字段内容
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
							+ "Content-Type: " + contentType);
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
				// InputStreamReader isr = new InputStreamReader(is, "utf-8");
				// BufferedReader br = new BufferedReader(isr);
				// result = br.readLine();
				int len = 0;
				byte[] buff = new byte[1024];
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((len = is.read(buff)) != -1) {
					baos.write(buff, 0, len);
					baos.flush();
				}
				result = baos.toString();

			} catch (Exception e) {
				result = "{\"ret\":\"post error\"}";
			}
		}
		return result;
	}

	/**
	 * post 上传多个以文件路径方�?
	 * 
	 * @param urlString
	 * @param params
	 * @param files
	 *            图片或文件手机上的地�?sdcard/photo/123.jpg
	 * @param contentType
	 *            for example {@link}HttpRequestImpl.CONTENT_TYPE_FORMDATA
	 * @return
	 */
	public static String doPost(String urlString,
			Map<String, ? extends Object> params,
			List<Map<String, Object>> files, String contentType) {
		String result = "";
		String PREFIX = "--";
		String LINE_END = "\r\n";
		String uploadUrl = "";// new BingoApp().URLIN 是我定义的上传URL
		String MULTIPART_FORM_DATA = "multipart/form-data";
		String BOUNDARY = "---------7d4a6d158c9"; // 数据分隔�?
		List<String> imgname = new ArrayList<String>();// 图片名称
		final int size = files.size(); // 取得多张图片
		byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义�?��数据分隔�?

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
				conn.setConnectTimeout(CONNECTION_TIMEOUT);// 6秒钟连接超时
				conn.setReadTimeout(READ_TIMEOUT);// 6秒钟读数据超�?
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Charset", "UTF-8");
				conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
						+ "; boundary=" + BOUNDARY);

				StringBuilder sb = new StringBuilder();
				if (params != null) {
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
				}
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());

				dos.write(sb.toString().getBytes());

				for (int i = 0; i < size; i++) {
					String imageuri = (String) files.get(i).get("path");
					if (!imageuri.equals("") && !imageuri.equals(null)) {
						StringBuilder sb1 = new StringBuilder();
						sb1.append(PREFIX);
						sb1.append(BOUNDARY);
						sb1.append(LINE_END);
						sb1.append("Content-Disposition: form-data;name=\"file\";filename=\""
								+ imgname.get(i).replace(".cach", "")
								+ "\"\r\n");
						sb1.append("Content-Type:" + contentType);
						// sb1.append(LINE_END);
						byte[] data = sb1.toString().getBytes();
						dos.write(data);
						FileInputStream fis = new FileInputStream(imageuri);
						byte[] buffer = new byte[1024]; // 8k
						int count = 0;
						while ((count = fis.read(buffer)) != -1) {
							dos.write(buffer, 0, count);
						}
						fis.close();
						dos.writeBytes(LINE_END);// 多个文件时，二个文件之间加入这个
					}
				}
				dos.write(end_data);
				dos.flush();
				dos.close();
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
				result = "{\"ret\":\"post error\"}";
			}
		}
		return result;

	}
}
