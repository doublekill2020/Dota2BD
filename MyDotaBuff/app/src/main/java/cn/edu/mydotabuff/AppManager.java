package cn.edu.mydotabuff;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

import org.json.JSONObject;

import cn.edu.mydotabuff.util.Debug;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AppManager {

	private static Stack<WeakReference<Activity>> activityStack;
	private static WeakReference<Activity> curractivity = null;
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
			activityStack = new Stack<WeakReference<Activity>>();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<WeakReference<Activity>>();
		}
		curractivity = new WeakReference<Activity>(activity);
		activityStack.add(curractivity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		if (activityStack.size() > 0) {
			curractivity = activityStack.lastElement();
			return curractivity.get();
		}
		return null;
	}

	/**
	 * 查找是否已经存在某个activity
	 * 
	 */
	public boolean isExists(Activity activity) {
		String className = activity.getComponentName().getClassName();
		if (activityStack.size() > 0)
			for (WeakReference<Activity> ay : activityStack) {
				if (ay.get() != null) {
					if (ay.get().getComponentName().getClassName()
							.equals(className))
						return true;
				}
			}
		return false;
	}

	public boolean isExists(Class<?> cls) {
		if (activityStack.size() > 0)
			for (WeakReference<Activity> ay : activityStack) {
				if (ay.get() != null) {
					if (ay.get().getClass().equals(cls))
						return true;
				}
			}
		return false;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public static void finishActivity() {
		curractivity = activityStack.lastElement();
		if (curractivity.get() != null) {
			curractivity.get().finish();
		}
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			Iterator<WeakReference<Activity>> iterator = activityStack
					.iterator();
			while (iterator.hasNext()) {
				WeakReference<Activity> tmp = iterator.next();
				if (tmp.get() != null
						&& activity.getClass().getSimpleName()
								.equals(tmp.get().getClass().getSimpleName())) {
					iterator.remove();
					break;
				}
			}
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (WeakReference<Activity> weak : activityStack) {
			if (weak.get() != null && weak.get().getClass().equals(cls)) {
				finishActivity(weak.get());
				break;
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				curractivity = activityStack.get(i);
				if (curractivity.get() != null) {
					curractivity.get().finish();
				}
			}
		}
		activityStack.clear();
	}

	/**
	 * 结束除传入class外的Activity
	 */
	public void finishAllActivity(Class<?> cls) {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				curractivity = activityStack.get(i);
				Activity activity = curractivity.get();
				if (activity != null) {
					if (!activity.getClass().equals(cls)) {
						activity.finish();
					}
				}
			}
		}
		activityStack.clear();
	}

	/**
	 * 
	 * @return return currently stack size
	 */
	public int getCurrentStackSize() {
		if (activityStack != null) {
			synchronized (activityStack) {
				return activityStack.size();
			}
		}
		return -1;
	}

	/**
	 * 重启App
	 * 
	 * @param context
	 */
	public static void restartApp(Context context) {
		Intent i = context.getPackageManager().getLaunchIntentForPackage(
				context.getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(i);
	}
}
