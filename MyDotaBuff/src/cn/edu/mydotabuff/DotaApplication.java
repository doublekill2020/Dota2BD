package cn.edu.mydotabuff;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.codec.binary.Base64;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import cn.edu.mydotabuff.bean.BoardBean;
import cn.edu.mydotabuff.bean.MatchBean;
import cn.edu.mydotabuff.bean.PlayerDetailBean;
import cn.edu.mydotabuff.bean.PlayerInfoBean;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class DotaApplication extends Application {
	private static DotaApplication mInstance = null;
	private Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = DotaApplication.this;
		context = mInstance.getApplicationContext();
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "mydotabuff/imageloader");
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.myinfro_up_img_btn) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.myinfro_up_img_btn)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.myinfro_up_img_btn) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的下载前的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
				.build();// 构建完成
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(320, 480)
				// max width, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				// 缓存的文件数量
				.discCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
																				// (5
																				// s),
																				// readTimeout
																				// (30
																				// s)超时时间
				.writeDebugLogs() // Remove for release app
				.defaultDisplayImageOptions(options).build();// 开始构建
		ImageLoader.getInstance().init(config);
	}

	public static DotaApplication getApplication() {
		return mInstance;
	}

	/**
	 * 检测网络连接
	 */
	public boolean isNetworkAvailabe() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			return true;
		}
		return false;
	}

	public <T> void saveData(T data, LocalDataType type) {
		String key = "";
		switch (type) {
		case MATCHES:
			key = "matches";
			break;
		case HERO_USED_LIST:
			key = "hero_used_list";
			break;
		case PLAYER_INFO:
			key = "player_info";
			break;
		case BOARDS:
			key = "boards";
			break;
		default:
			break;
		}
		SharedPreferences mSharedPreferences = getSharedPreferences(key,
				Context.MODE_PRIVATE);
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(data);
			String personBase64 = new String(Base64.encodeBase64(baos
					.toByteArray()));
			SharedPreferences.Editor editor = mSharedPreferences.edit();
			editor.putString(key, personBase64);
			editor.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getData(LocalDataType type) {
		String key = "";
		switch (type) {
		case MATCHES:
			key = "matches";
			break;
		case HERO_USED_LIST:
			key = "hero_used_list";
			break;
		case PLAYER_INFO:
			key = "player_info";
			break;
		case BOARDS:
			key = "boards";
			break;
		default:
			break;
		}
		T data = null;
		try {
			SharedPreferences mSharedPreferences = getSharedPreferences(key,
					Context.MODE_PRIVATE);
			String personBase64 = mSharedPreferences.getString(key, "");
			byte[] base64Bytes = Base64.decodeBase64(personBase64.getBytes());
			ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			data = (T) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public void destoryData(LocalDataType type) {
		String key = "";
		switch (type) {
		case MATCHES:
			key = "matches";
			break;
		case HERO_USED_LIST:
			key = "hero_used_list";
			break;
		case PLAYER_INFO:
			key = "player_info";
			break;
		case BOARDS:
			key = "boards";
			break;
		default:
			break;
		}
		SharedPreferences mSharedPreferences = getSharedPreferences(key,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(key, "");
		editor.commit();
	}

	public enum LocalDataType {
		MATCHES, HERO_USED_LIST, PLAYER_INFO, BOARDS;
	}
}
