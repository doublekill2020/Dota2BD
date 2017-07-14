package cn.edu.mydotabuff.base;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.List;

import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.base.realm.RealmInt;
import cn.edu.mydotabuff.base.realm.RealmIntAdapter;
import cn.edu.mydotabuff.common.http.APIConstants;
import cn.edu.mydotabuff.model.Hero;
import cn.edu.mydotabuff.model.Match;
import cn.edu.mydotabuff.model.MatchDetail;
import cn.edu.mydotabuff.model.PlayerInfo;
import cn.edu.mydotabuff.model.Rating;
import cn.edu.mydotabuff.model.SearchPlayerResult;
import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by tinker on 2017/6/26.
 */

public class OpenDotaApi {

    private static OpenDotaApi mOpenDotaApi = new OpenDotaApi();
    private static Retrofit mRetrofit;
    private static OpenDotaService mService;
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
        mService = mRetrofit.create(OpenDotaService.class);
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaredClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(new TypeToken<RealmList<RealmInt>>() {
                }.getType(), new RealmIntAdapter())
                .create();
    }

    public static OpenDotaService getService() {
        return mService;
    }

    private static OkHttpClient getOkHttpClient() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .cache(getCache())
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    private static Cache getCache() {
        File appCacheDir = DotaApplication.getApplication().getCacheDir();
        File httpCacheFile = new File(appCacheDir, "HttpCache");
        return new Cache(httpCacheFile, HTTP_DISK_CACHE_MAX_SIZE);
    }

    public interface OpenDotaService {

        //        @GET("players/{account_id}/wl")
        //        Observable<PlayerWL> getPlayerWL(@Path("account_id") String accountId);

        @GET("players/{account_id}/recentMatches")
        Observable<List<Match>> getRecentMatch(@Path("account_id") String accountId);

        @GET("search")
        Observable<List<SearchPlayerResult>> searchAccountId(@Query(value = "q", encoded = true)
                                                                     String nickName, @Query
                                                                     ("similarity") float
                                                                     similarity);

        @GET("players/{account_id}")
        Observable<PlayerInfo> getPlayerInfo(@Path("account_id") String accountId);

        @GET("players/{account_id}/ratings")
        Observable<List<Rating>> getPlayerRating(@Path("account_id") String accountId);

        @GET("matches/{match_id}")
        Observable<MatchDetail> getMatchDetail(@Path("match_id") String accountId);

        @GET("players/{account_id}/heroes")
        Observable<List<Hero>> getHeroUsed(@Path("match_id") String accountId);

    }
}
