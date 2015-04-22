package cn.edu.mydotabuff.common.http;

import java.util.List;

public interface OnWebDataGetListener {

	void onStartGetData();

	<T> void onGetFinished(T data);

	void onGetFailed(String failMsg);
}
