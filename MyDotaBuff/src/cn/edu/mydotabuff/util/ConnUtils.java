package cn.edu.mydotabuff.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ConnUtils {
    // 用给定的url获取json数据
    public static String connServerForResult(final String url) {
	HttpGet httpRequest = new HttpGet(url);
	String strResult = "";
	try {
	    HttpClient httpClient = new DefaultHttpClient();
	    HttpResponse httpResponse = httpClient.execute(httpRequest);
	    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		strResult = EntityUtils.toString(httpResponse.getEntity());
	    }
	} catch (ClientProtocolException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return strResult;
    }
}
