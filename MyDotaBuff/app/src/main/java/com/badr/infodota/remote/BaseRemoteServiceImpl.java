package com.badr.infodota.remote;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.tencent.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.edu.mydotabuff.R;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 10:56
 */
public abstract class BaseRemoteServiceImpl implements BaseRemoteService {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private static String readStream(InputStream in) throws IOException {
        if (in == null) {
            return "";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        try {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append('\n');
            }
            return result.toString();
        } finally {
            reader.close();
        }
    }

    protected <T> Pair<T, String> basicRequestSend(Context context, String url, Type resultType) throws Exception {
        if (!isNetworkAvailable(context)) {
            throw new HttpUtils.NetworkUnavailableException(context.getString(R.string.network_unavailable));
        }
        T result;
        String message;
        HttpURLConnection urlConnection;
        message = null;
        URL urlObj = new URL(url);
        urlConnection = (HttpURLConnection) urlObj.openConnection();
        try {
            InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
            JsonReader reader = new JsonReader(isr);
            result = new Gson().fromJson(reader, resultType);
            reader.close();
            urlConnection.disconnect();
        } catch (Exception e) {
            Log.w(getClass().getName(), "Warn. Error message from server: " + e.getMessage(), e);
            result = null;
            String errorMsg = readStream(urlConnection.getErrorStream());
            if (TextUtils.isEmpty(errorMsg)) {
                message = context.getString(R.string.error_during_load);
            } else {
                message = errorMsg;
            }
        }
        return Pair.create(result, message);
    }

    protected Pair<String, String> basicRequestSend(Context context, String url) throws Exception {
        if (!isNetworkAvailable(context)) {
            throw new HttpUtils.NetworkUnavailableException(context.getString(R.string.network_unavailable));
        }
        String result;
        String message;
        HttpURLConnection urlConnection;
        message = null;
        URL urlObj = new URL(url);
        urlConnection = (HttpURLConnection) urlObj.openConnection();
        try {
            result = readStream(urlConnection.getInputStream());
            urlConnection.disconnect();
        } catch (Exception e) {
            Log.w(getClass().getName(), "Warn. Error message from server: " + e.getMessage(), e);
            result = null;
            String errorMsg = readStream(urlConnection.getErrorStream());
            if (TextUtils.isEmpty(errorMsg)) {
                message = context.getString(R.string.error_during_load);
            } else {
                message = errorMsg;
            }
        }
        return Pair.create(result, message);
    }
}
