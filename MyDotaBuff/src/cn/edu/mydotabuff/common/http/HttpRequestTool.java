package cn.edu.mydotabuff.common.http;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;

import cn.edu.mydotabuff.common.http.IInfoReceive.ReceiveMsgType;
import cn.edu.mydotabuff.common.http.IInfoReceive.ResponseObj;
import cn.edu.mydotabuff.view.LoadingDialog;

import android.app.Activity;

public class HttpRequestTool {
	String tag = getClass().getSimpleName();
	IInfoReceive mIInfoReceive = null;

	// Activity activity = null;

	WeakReference<Activity> weakActivity = null;

	LoadingDialog mProgressDialog = null;

	public LoadingDialog getmProgressDialog() {
		return mProgressDialog;
	}

	int default_timeout = 10 * 1000;
	int timeout = default_timeout;

	boolean isRequesting = false;

	boolean isAutoMode = true;

	String DialogTitle = "Loading...";

	/**
	 * 
	 * @param mIInfoReceive
	 */
	public HttpRequestTool(IInfoReceive mIInfoReceive) {
		this.mIInfoReceive = mIInfoReceive;
	}

	public void setIInfoReceive(IInfoReceive mIInfoReceive) {
		this.mIInfoReceive = mIInfoReceive;
	}

	class TimeoutTask extends TimerTask {
		boolean isTimeout = false;

		public TimeoutTask() {
			isTimeout = false;
		}

		@Override
		public void run() {
			isTimeout = true;
			String temp = "{\"data\":\"\",\"info\":\"连接服务器超时!\",\"status\":503}";
			ResponseObj mResponseObj;
			try {
				mResponseObj = getJsonResponse(temp);
				if (mIInfoReceive != null) {
					mIInfoReceive.onMsgReceiver(mResponseObj);
				}
				if (isAutoMode) {
					hideProgressDialog();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		public boolean IsTimeout() {
			return isTimeout;
		}
	};

	public boolean isAutoMode() {
		return isAutoMode;
	}

	public void setAutoMode(boolean isAutoMode) {
		this.isAutoMode = isAutoMode;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		if (timeout > default_timeout) {
			this.timeout = timeout;
		} else {
			this.timeout = default_timeout;
		}
	}

	public void setProgressDialog(Activity activity) {
		// this.activity = activity;
		if (this.weakActivity == null) {
			weakActivity = new WeakReference<Activity>(activity);
		}
	}

	public void setProgressDialogTitle(String title) {
		this.DialogTitle = title;
	}

	public void showProgressDialog() {
		showProgressDialog(DialogTitle);
	}

	public void showProgressDialog(final String msg) {
		// if (activity != null) {
		// activity.runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		// TipsToast.dismiss();
		// // log("showProgressDialog msg=" + msg + " timestamp="
		// // + TimeHelper.getStringTime());
		// mProgressDialog = new LoadingDialog(activity, msg);
		// mProgressDialog.show();
		// }
		// });
		// }
		// if (activity != null) {
		if (weakActivity != null && weakActivity.get() != null) {
			weakActivity.get().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// log("showProgressDialog msg=" + msg + " timestamp="
					// + TimeHelper.getStringTime());
					mProgressDialog = new LoadingDialog(weakActivity.get(), msg);
					mProgressDialog.show();
				}
			});
		}
	}

	public void hideProgressDialog() {
		// if (activity != null) {
		// activity.runOnUiThread(new Runnable() {
		// @Override
		// public void run() {
		// // log("hideProgressDialog  timestamp="
		// // + TimeHelper.getStringTime());
		// if (mProgressDialog != null) {
		// try {
		// mProgressDialog.dismiss();
		// mProgressDialog=null;
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// });
		// }
		if (weakActivity != null && weakActivity.get() != null) {
			weakActivity.get().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (mProgressDialog != null) {
						try {
							mProgressDialog.dismiss();
							mProgressDialog = null;
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
	}

	/**
	 * Post不传文件方式
	 * 
	 * @param url
	 * @param map
	 *            <key,value>
	 * @throws Exception
	 */
	public void doPost(String url, Map<String, String> map) throws Exception {
		Timer timer = new Timer();
		TimeoutTask timeoutTask = new TimeoutTask();
		timer.schedule(timeoutTask, timeout);

		showProgressDialog();
		String jsonStr = "";
		ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
		mByteArrayOutputStream.write(HttpRequestImpl.httpPostFromServer(url,
				map));
		mByteArrayOutputStream.flush();
		jsonStr = mByteArrayOutputStream.toString();
		mByteArrayOutputStream.close();

		ResponseObj mResponseObj = getJsonResponse(jsonStr);
		if (mIInfoReceive != null) {
			timer.cancel();
			if (!timeoutTask.IsTimeout()) {
				mIInfoReceive.onMsgReceiver(mResponseObj);
				hideProgressDialog();
			}
		}
	}

	/**
	 * Post多个文件
	 * 
	 * @param url
	 * @param map
	 * @param contentType
	 *            for example {@link}HttpRequestImpl.CONTENT_TYPE_FORMDATA
	 * @throws Exception
	 */
	public void doPost(String url, Map<String, ? extends Object> map,
			List<Map<String, Object>> files, String contentType)
			throws Exception {
		Timer timer = new Timer();
		TimeoutTask timeoutTask = new TimeoutTask();
		timer.schedule(timeoutTask, timeout);

		showProgressDialog();
		String jsonStr = "";
		String temp_url = url;
		// log("" + temp_url);
		jsonStr = HttpRequestImpl.doPost(url, map, files, contentType);
		ResponseObj mResponseObj = getJsonResponse(jsonStr);
		if (mIInfoReceive != null) {
			timer.cancel();
			if (!timeoutTask.IsTimeout()) {
				mIInfoReceive.onMsgReceiver(mResponseObj);
				hideProgressDialog();
			}
		}
	}

	/**
	 * Post 上传单个文件，以字节流方式上传
	 * 
	 * @param url
	 * @param params
	 * @param image
	 * @param fileName
	 *            例如 123.jpg
	 * @param contentType
	 *            for example {@link}HttpRequestImpl.CONTENT_TYPE_FORMDATA
	 * @throws Exception
	 */
	public void doPost(String url, Map<String, String> params, byte[] image,
			String fileName, String contentType) throws Exception {
		Timer timer = new Timer();
		TimeoutTask timeoutTask = new TimeoutTask();
		timer.schedule(timeoutTask, timeout);

		showProgressDialog();
		String jsonStr = "";
		String temp_url = url;
		// log("" + temp_url);
		jsonStr = HttpRequestImpl.doPost(url, params, image, fileName,
				contentType);
		ResponseObj mResponseObj = getJsonResponse(jsonStr);
		if (mIInfoReceive != null) {
			timer.cancel();
			if (!timeoutTask.IsTimeout()) {
				mIInfoReceive.onMsgReceiver(mResponseObj);
				hideProgressDialog();
			}
		}
	}

	/**
	 * Post 上传单个文件，以Uri path方式上传
	 * 
	 * @param url
	 * @param params
	 * @param fileUri
	 *            例如 sdcard/aa/bc.jpg
	 * @param fileName
	 * @param contentType
	 *            for example {@link}HttpRequestImpl.CONTENT_TYPE_FORMDATA
	 * @throws Exception
	 */
	public void doPost(String url, Map<String, String> params, String fileUri,
			String contentType) throws Exception {
		Timer timer = new Timer();
		TimeoutTask timeoutTask = new TimeoutTask();
		timer.schedule(timeoutTask, timeout);

		showProgressDialog();
		String jsonStr = "";
		String temp_url = url;
		// log("" + temp_url);
		jsonStr = HttpRequestImpl.doPost(url, params, fileUri, contentType);
		ResponseObj mResponseObj = getJsonResponse(jsonStr);
		if (mIInfoReceive != null) {
			timer.cancel();
			if (!timeoutTask.IsTimeout()) {
				mIInfoReceive.onMsgReceiver(mResponseObj);
				hideProgressDialog();
			}
		}
	}

	// 上传单纯文字给服务器dopost
	public void doPost(String urlString, String mobiles) {
		Timer timer = new Timer();
		TimeoutTask timeoutTask = new TimeoutTask();
		timer.schedule(timeoutTask, timeout);

		showProgressDialog();
		String jsonStr = "";
		String temp_url = urlString;
		// log("" + temp_url);
		jsonStr = HttpRequestImpl.doPost(urlString, mobiles);
		ResponseObj mResponseObj;
		try {
			mResponseObj = getJsonResponse(jsonStr);
			if (mIInfoReceive != null) {
				timer.cancel();
				if (!timeoutTask.IsTimeout()) {
					mIInfoReceive.onMsgReceiver(mResponseObj);
					hideProgressDialog();
				}
			}

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * @param url
	 * @throws IOException
	 * @throws Exception
	 */
	public void doGet(String url) {
		if (isAutoMode) {
			showProgressDialog();
		}
		Timer timer = new Timer();
		TimeoutTask timeoutTask = new TimeoutTask();
		timer.schedule(timeoutTask, timeout);

		String jsonStr = "";
		String temp_url = url;
		// log("" + temp_url);

		ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
		ResponseObj mResponseObj = new ResponseObj();
		try {
			mByteArrayOutputStream.write(HttpRequestImpl
					.httpGetFromServer(temp_url));
			mByteArrayOutputStream.flush();
			jsonStr = mByteArrayOutputStream.toString();
			mByteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				mResponseObj = getJsonResponse(jsonStr);
			} catch (JSONException e) {
				String temp = "{\"data\":\"\",\"info\":\"连接服务器超时!\",\"status\":503}";
				mResponseObj = new ResponseObj();
				mResponseObj.setJsonStr(temp);
				try {
					mResponseObj = getJsonResponse(temp);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			timer.cancel();
			if (!timeoutTask.IsTimeout()) {
				// log("mResponseObj="+mResponseObj);
				if (mIInfoReceive != null)
					mIInfoReceive.onMsgReceiver(mResponseObj);
				if (isAutoMode == true) {
					hideProgressDialog();
				}
			}
		}
	}

	/**
	 * @param jsonStr
	 * @return
	 * @throws JSONException
	 */
	ResponseObj getJsonResponse(String jsonStr) throws JSONException {
		if (jsonStr != null && !"".equals(jsonStr)) {
			int a = jsonStr.charAt(0);
			if (a == 65279) {
				jsonStr = jsonStr.substring(1, jsonStr.length());
			}
			// jsonStr = jsonStr.replaceAll("\\xef\\xbb\\xbf", "");
		}
		String status = "1";
		ResponseObj mResponseObj = new ResponseObj();
		if (status.equals("0")) {
			mResponseObj.setMsgType(ReceiveMsgType.FAILED);
		} else if (status.equals("1")) {
			mResponseObj.setMsgType(ReceiveMsgType.OK);
		} else if (status.equals("503")) {
			mResponseObj.setMsgType(ReceiveMsgType.TIMEOUT);
		} else if (status.equals("404")) {
			mResponseObj.setMsgType(ReceiveMsgType.NOT_FOUND);
		} else {
			mResponseObj.setMsgType(ReceiveMsgType.FAILED);
		}
		mResponseObj.setJsonStr(jsonStr);
		return mResponseObj;
	}

	public void Release() {
		hideProgressDialog();
		weakActivity = null;
		mIInfoReceive = null;
		System.gc();
	}

	void log(String msg) {
		// / Log.d(tag, msg);
	}
}
