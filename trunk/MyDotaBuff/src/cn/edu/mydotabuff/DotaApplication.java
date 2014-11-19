package cn.edu.mydotabuff;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.ConnectCallback.ErrorCode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
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
				//.writeDebugLogs() // Remove for release app
				.defaultDisplayImageOptions(options).build();// 开始构建
		ImageLoader.getInstance().init(config);
		
//		RongIM.init(this, "25wehl3uw6q5w", R.drawable.ic_launcher);
//        // 此处直接 hardcode 给 token 赋值，请替换为您自己的 Token。
//        String token = "YMuGi5vs2EGG700+TnBYCe4ojYjBMJnJrmW5Rq87UVkSiih43YSR2cSnP9/CIgmvAGhIw1l9CIEB/S6hlnHkHG9JEbovp9fL";
//        // 连接融云服务器。
//        RongIM.connect(token, new RongIMClient.ConnectCallback() {
//
//            @Override
//            public void onSuccess(String s) {
//                // 此处处理连接成功。
//                Log.d("Connect:", "Login successfully.");
//            }
//
//            @Override
//            public void onError(ErrorCode errorCode) {
//                // 此处处理连接错误。
//                Log.d("Connect:", "Login failed.");
//                Log.d("Connect:", errorCode.getMessage());
//            }
//        });
//        // 设置用户信息提供者。
//        RongIM.setGetUserInfoProvider(new RongIM.GetUserInfoProvider() {
//            // App 返回指定的用户信息给 IMKit 界面组件。
//            // 原则上 App 应该将用户信息和头像在移动设备上进行缓存，每次获取用户信息的时候，就不用再通过网络获取，提高加载速度，提升用户体验。我们后续将提供用户信息缓存功能，方便您开发。
//            @Override
//            public RongIMClient.UserInfo getUserInfo(String userId) {
//                if (userId.equals("1")) {
//                    RongIMClient.UserInfo user = new RongIMClient.UserInfo("1", "zhangsan", "http://www.baidu.com/img/bdlogo.png");
//
//                    return user;
//                }
//                else if(userId.equals("2")) {
//                    RongIMClient.UserInfo user = new RongIMClient.UserInfo("2", "lisi", "http://2.su.bdimg.com/star_skin/1001_t.png");
//
//                    return user;
//                }
//
//                return null;
//            }
//        }, false);
//
//        // 设置好友信息提供者。
//        RongIM.setGetFriendsProvider(new RongIM.GetFriendsProvider() {
//            @Override
//            public List<RongIMClient.UserInfo> getFriends() {
//                // 返回 App 的好友列表给 IMKit 界面组件，供会话列表页中选择好友时使用。
//                List<RongIMClient.UserInfo> list = new ArrayList<RongIMClient.UserInfo>();
//
//                RongIMClient.UserInfo user1 = new RongIMClient.UserInfo("1", "zhangsan", "http://www.baidu.com/img/bdlogo.png");
//
//                list.add(user1);
//
//                RongIMClient.UserInfo user2 = new RongIMClient.UserInfo("2", "lisi", "http://2.su.bdimg.com/star_skin/1001_t.png");
//
//                list.add(user2);
//
//                return list;
//            }
//        });
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
		String name = "";
		switch (type) {
		case MATCHES:
			name = "matches";
			key = "matches";
			break;
		case HERO_USED_LIST:
			name = "hero_used_list";
			key = "hero_used_list";
			break;
		case PLAYER_INFO:
			name = "player_info";
			key = "player_info";
			break;
		case PLAYER_DETAIL_INFO:
			name = "player_detail_info";
			key = "player_detail_info";
			break;
		case BOARDS:
			name = "boards";
			key = "boards";
			break;
		default:
			break;
		}
		SharedPreferences mSharedPreferences = getSharedPreferences(name,
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
		String name = "";
		switch (type) {
		case MATCHES:
			name = "matches";
			key = "matches";
			break;
		case HERO_USED_LIST:
			name = "hero_used_list";
			key = "hero_used_list";
			break;
		case PLAYER_INFO:
			name = "player_info";
			key = "player_info";
			break;
		case PLAYER_DETAIL_INFO:
			name = "player_detail_info";
			key = "player_detail_info";
			break;
		case BOARDS:
			name = "boards";
			key = "boards";
			break;
		default:
			break;
		}
		T data = null;
		try {
			SharedPreferences mSharedPreferences = getSharedPreferences(name,
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
		String name = "";
		switch (type) {
		case MATCHES:
			name = "matches";
			key = "matches";
			break;
		case HERO_USED_LIST:
			name = "hero_used_list";
			key = "hero_used_list";
			break;
		case PLAYER_INFO:
			name = "player_info";
			key = "player_info";
			break;
		case PLAYER_DETAIL_INFO:
			name = "player_detail_info";
			key = "player_detail_info";
			break;
		case BOARDS:
			name = "boards";
			key = "boards";
			break;
		default:
			break;
		}
		SharedPreferences mSharedPreferences = getSharedPreferences(name,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putString(key, "");
		editor.commit();
	}

	public enum LocalDataType {
		MATCHES, HERO_USED_LIST, PLAYER_INFO, BOARDS, PLAYER_DETAIL_INFO;
	}
}
