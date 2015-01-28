package cn.edu.mydotabuff.base;

import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import cn.edu.mydotabuff.common.http.APIConstants;
import cn.edu.mydotabuff.common.http.HttpRequestTool;
import cn.edu.mydotabuff.common.http.IInfoReceive;

public class BaseRequestInterface {

	private String url;

	private Map<String, String> params;

	private boolean showDialog = false;

	private IInfoReceive receive;

	// private Activity activity;
	WeakReference<Activity> weakActivity = null;

	private HttpThreadTask httpTaskThread = null;

	private String title;

	private int timeout;
	ArrayList<String> matchIds;
	private boolean cancelAble = true;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public IInfoReceive getReceive() {
		return receive;
	}

	public void setReceive(IInfoReceive receive) {
		this.receive = receive;
	}

	public Activity getActivity() {
		if (weakActivity != null) {
			return weakActivity.get();
		}
		return null;
	}

	public void setActivity(Activity activity) {
		// this.activity = activity;
		if (weakActivity == null && activity != null) {
			weakActivity = new WeakReference<Activity>(activity);
		}
	}

	public HttpRequestTool getTool() {
		return tool;
	}

	public void setTool(HttpRequestTool tool) {
		this.tool = tool;
	}

	public void setDialogTitle(String title) {
		this.title = title;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	HttpRequestTool tool;

	public BaseRequestInterface() {

	}

	public BaseRequestInterface(IInfoReceive receive) {
		this.receive = receive;
		if (receive instanceof Activity) {
			// activity = (Activity) receive;
			weakActivity = new WeakReference<Activity>((Activity) receive);
		}
		this.timeout = 10000;
	}

	public void setIsCancelAble(boolean flag) {
		// TODO Auto-generated method stub
		this.cancelAble = flag;
	}

	protected void request(String url, Map<String, String> params,
			boolean showDialog) {
		this.url = url;
		this.params = params;
		this.showDialog = showDialog;
		get();
	}

	protected void requestDetails(String url, ArrayList<String> matchIds,
			boolean showDialog) {
		this.url = url;
		this.matchIds = matchIds;
		this.showDialog = showDialog;
		httpTaskThread = new HttpThreadTask(null, null, null, null, null, null,
				getParams());
		httpTaskThread.setFormType(HttpThreadTask.MULTI_HTTP_FORM_TYPE_GET);
		httpTaskThread.setDaemon(true);
		httpTaskThread.start();
	}

	protected void multiRequest(String url, Map<String, String> params,
			boolean showDialog) {
		httpTaskThread = new HttpThreadTask(null, null, null, null, null, null,
				null);
		httpTaskThread.setFormType(HttpThreadTask.MULTI_HTTP_FORM_TYPE_GET);
		httpTaskThread.setDaemon(true);
		httpTaskThread.start();
	}

	/**
	 * 普通GET请求
	 */
	private synchronized void get() {
		httpTaskThread = new HttpThreadTask(null, null, null, null, null, null,
				getParams());
		httpTaskThread.setFormType(HttpThreadTask.HTTP_FORM_TYPE_GET);
		httpTaskThread.setDaemon(true);
		httpTaskThread.start();
	}

	protected void simplepost(final String url, final String mobiles) {
		setUrl(url);
		httpTaskThread = new HttpThreadTask(null, null, null, null, mobiles,
				null, null);
		httpTaskThread.setFormType(HttpThreadTask.HTTP_FORM_TYPE_POST_NORMAL);
		httpTaskThread.setDaemon(true);
		httpTaskThread.start();
	}

	public void stop() {
		if (tool != null) {
			tool.Release();
			tool = null;
		}
	}

	private class HttpThreadTask extends Thread {

		public static final int HTTP_FORM_TYPE_GET = 0; // GET
		public static final int HTTP_FORM_TYPE_POST_NORMAL = 1; // POST
		public static final int HTTP_FORM_TYPE_POST_BYTE = 2; // POST
		public static final int HTTP_FORM_TYPE_POST_URI = 3; // POST
		public static final int HTTP_FORM_TYPE_POST_MULFILE = 4; // POST
		public static final int MULTI_HTTP_FORM_TYPE_GET = 5; // GET
		private int formType = 0;
		private List<Map<String, Object>> files = null;
		private String contentType = null;
		private String fileUri = null;
		private byte[] bytes;
		private String fileName;
		private Map<String, String> params;
		private String path;

		public HttpThreadTask(List<Map<String, Object>> files,
				String contentType, String fileUri, byte[] bytes,
				String fileName, String path, Map<String, String> params) {
			this.files = files;
			this.contentType = contentType;
			this.fileUri = fileUri;
			this.bytes = bytes;
			this.fileName = fileName;
			this.path = path;
			this.params = params;
		}

		public void setFormType(int type) {
			this.formType = type;
		}

		public int getFormType() {
			return formType;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			switch (formType) {
			case HTTP_FORM_TYPE_GET:
				request();
				break;
			case HTTP_FORM_TYPE_POST_NORMAL:
				//simplepost();
				break;
			case HTTP_FORM_TYPE_POST_BYTE:
				//postHeadIcon();
				break;
			// case HTTP_FORM_TYPE_POST_URI:
			// postrequest();
			// break;
			case HTTP_FORM_TYPE_POST_MULFILE:

				break;
			case MULTI_HTTP_FORM_TYPE_GET:
				for (String id : matchIds) {
					Map<String, String> param = new HashMap<String, String>();
					param.put("key", APIConstants.API_KEY);
					param.put("match_id", id);
					try {
						tool = new HttpRequestTool(receive);
						tool.setTimeout(timeout);
						if (showDialog) {
							if (title != null) {
								tool.setProgressDialogTitle(title);
							}
							if (weakActivity != null
									&& weakActivity.get() != null) {
								tool.setProgressDialog(weakActivity.get());
							}
						} else {
							tool.setProgressDialog(null);
						}
						String http = url;
						StringBuilder sb = new StringBuilder(http);
						if (param != null && !param.isEmpty()) {
							sb.append("?");
							if (param != null && !param.isEmpty()) {
								for (Map.Entry<String, String> entry : param
										.entrySet()) {
									sb.append(entry.getKey())
											.append('=')
											.append(URLEncoder.encode(
													entry.getValue(), "utf-8"))
											.append('&');
								}
								sb.deleteCharAt(sb.length() - 1);
							}
						}

						http = sb.toString();
						tool.doGet(http, cancelAble);
					} catch (Exception e) {
						e.printStackTrace();
						tool.hideProgressDialog();
					}
				}
				break;
			default:
				break;
			}
		}

		private synchronized void request() {
			try {
				tool = new HttpRequestTool(receive);
				tool.setTimeout(timeout);
				if (showDialog) {
					if (title != null) {
						tool.setProgressDialogTitle(title);
					}
					if (weakActivity != null && weakActivity.get() != null) {
						tool.setProgressDialog(weakActivity.get());
					}
				} else {
					tool.setProgressDialog(null);
				}
				String http = url;
				StringBuilder sb = new StringBuilder(http);
				if (params != null && !params.isEmpty()) {
					sb.append("?");
					if (params != null && !params.isEmpty()) {
						for (Map.Entry<String, String> entry : params
								.entrySet()) {
							sb.append(entry.getKey())
									.append('=')
									.append(URLEncoder.encode(entry.getValue(),
											"utf-8")).append('&');
						}
						sb.deleteCharAt(sb.length() - 1);
					}
				}

				http = sb.toString();
				tool.doGet(http, cancelAble);
			} catch (Exception e) {
				e.printStackTrace();
				tool.hideProgressDialog();
			}
		}

		// /**
		// * 涓婁紶鏂囦欢浠ュ瓧鑺傛祦鏂瑰紡
		// *
		// * @param url
		// * @param fileName
		// * @param bytes
		// * @param contentType
		// * for example {@link}HttpRequestImpl.CONTENT_TYPE_FORMDATA
		// */
		//
		// protected synchronized void postrequest() {
		// showDialog = true;
		// try {
		// // byte[] bytes = BitmapCompress.compressForOther(path);
		// // Log.v("size", "send bytes---" + bytes.length);
		// tool = new HttpRequestTool(receive);
		// tool.setTimeout(timeout);
		// if (showDialog) {
		// if (title != null) {
		// tool.setProgressDialogTitle(title);
		// }
		// if(weakActivity!=null&&weakActivity.get()!=null){
		// tool.setProgressDialog(weakActivity.get());
		// }
		// } else {
		// tool.setProgressDialog(null);
		// }
		// tool.doPost(getUrl(), params, bytes, fileName, contentType);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

		// 传图片
//		protected synchronized void postHeadIcon() {
//			showDialog = true;
//			try {
//				// byte[] bytes = BitmapCompress.compress(path, 25, 128, 128);
//				tool = new HttpRequestTool(receive);
//				tool.setTimeout(timeout);
//				if (showDialog) {
//					if (title != null) {
//						tool.setProgressDialogTitle(title);
//					}
//					if (weakActivity != null && weakActivity.get() != null) {
//						tool.setProgressDialog(weakActivity.get());
//					}
//				} else {
//					tool.setProgressDialog(null);
//				}
//				tool.doPost(getUrl(), params, bytes, fileName, contentType);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

//		protected synchronized void simplepost() {
//			try {
//				tool = new HttpRequestTool(receive);
//				tool.setTimeout(timeout);
//				if (showDialog) {
//					if (title != null) {
//						tool.setProgressDialogTitle(title);
//					}
//					if (weakActivity != null && weakActivity.get() != null) {
//						tool.setProgressDialog(weakActivity.get());
//					}
//				} else {
//					tool.setProgressDialog(null);
//				}
//				tool.doPost(getUrl(), fileName);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}

	// /**
	// * 涓婁紶鏂囦欢浠ri瀛楃涓叉柟寮�
	// *
	// * @param url
	// * @param fileName
	// * @param bytes
	// * @param contentType
	// * for example {@link}HttpRequestImpl.CONTENT_TYPE_FORMDATA
	// */
	// protected void postrequest(final String url, final String fileName,
	// final String fileUri, final String contentType) {
	// setUrl(url);
	// new Thread() {
	// @Override
	// public void run() {
	// try {
	// tool = new HttpRequestTool(receive);
	// tool.setTimeout(timeout);
	// if (showDialog) {
	// if (title != null) {
	// tool.setProgressDialogTitle(title);
	// }
	// if(weakActivity!=null&&weakActivity.get()!=null){
	// tool.setProgressDialog(weakActivity.get());
	// }
	// } else {
	// tool.setProgressDialog(null);
	// }
	// tool.doPost(getUrl(), getParams(), fileUri, contentType);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }.start();
	// }

	// /**
	// * post 涓婁紶澶氫釜浠ilepath鏂瑰紡
	// *
	// * @param url
	// * @param files
	// * Map<String,Object> --key="path" value=filepath
	// * @param contentType
	// * for example {@link}HttpRequestImpl.CONTENT_TYPE_JPEG
	// */
	// public void postMulireqeust(final String url,
	// final List<Map<String, Object>> files, final String contentType) {
	// setUrl(url);
	// new Thread() {
	// @Override
	// public void run() {
	// try {
	// tool = new HttpRequestTool(receive);
	// tool.setTimeout(timeout);
	// if (showDialog) {
	// if (title != null) {
	// tool.setProgressDialogTitle(title);
	// }
	// if(weakActivity!=null&&weakActivity.get()!=null){
	// tool.setProgressDialog(weakActivity.get());
	// }
	// } else {
	// tool.setProgressDialog(null);
	// }
	// tool.doPost(getUrl(), getParams(), files, contentType);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }.start();
	// }

}
