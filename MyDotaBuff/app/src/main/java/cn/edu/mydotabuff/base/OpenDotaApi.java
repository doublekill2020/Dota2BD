package cn.edu.mydotabuff.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.common.http.APIConstants;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by tinker on 2017/6/26.
 */

public class OpenDotaApi {

    private static OpenDotaApi mOpenDotaApi = new OpenDotaApi();
    private static Retrofit mRetrofit;
    private static final long HTTP_DISK_CACHE_MAX_SIZE = 1024 * 1024 * 10; // 10 MB

    public static OpenDotaApi getInstance() {
        return mOpenDotaApi;
    }

    private OpenDotaApi() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(APIConstants.OPEN_DOTA_API)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory
                        .createWithScheduler(Schedulers.io()))
                .build();
    }

    private static Gson createGson() {
        return new GsonBuilder().create();
    }

    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .cache(getCache())
                .build();
    }

    private static Cache getCache() {
        File appCacheDir = DotaApplication.getApplication().getCacheDir();
        File httpCacheFile = new File(appCacheDir, "HttpCache");
        return new Cache(httpCacheFile, HTTP_DISK_CACHE_MAX_SIZE);
    }
}
