package cn.edu.mydotabuff.custom;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.mydotabuff.R;

public class TipsToast extends Toast {

	private TipsToast(Context context) {
		super(context);
	}

	private static Toast mToast=null;

	/*
	 * private static Handler mHandler = new Handler(); private static Runnable
	 * r = new Runnable() { public void run() { Log.d("toast", "is cancel");
	 * mToast.cancel(); } };
	 */

	/**
	 * @param context
	 * @param text
	 * @param duration
	 * @return
	 */
	private static TipsToast makeText(Context context, CharSequence text,
			int duration, DialogType type) {
		TipsToast result = new TipsToast(context);
		setContent(context, result, text, duration, type);
		return result;
	}

	private static void setContent(Context context, Toast toast,
			CharSequence text, int duration, DialogType type) {
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.view_tips_toast_loading, null);
		TextView loading_msg = (TextView) v.findViewById(R.id.loading_msg);
		ImageView loading_img = (ImageView) v.findViewById(R.id.loadImage);
		
		loading_msg.setText(text);
		toast.setView(v);
		// setGravity方法用于设置位置，此处为垂直居中
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(duration);
		setType(type, loading_img);
	}

	/**
	 * @param context
	 * @param text
	 * @param duration
	 * @param type
	 */
	public static void showToast(Context context, CharSequence text,
			int duration, DialogType type) {
		if (mToast != null) {
			setContent(context, mToast, text, duration, type);
//			Log.d("toast", "is not null");
		} else {
//			Log.d("toast", "is null");
			mToast = makeText(context, text, duration, type);
		}
		mToast.show();
	}

	public static void dismiss() {
		if (mToast != null) {
			// mToast.getView().setVisibility(View.GONE);
			mToast.cancel();
			mToast=null;
		}
	}

	/**
	 * @param type
	 * @param loading_img
	 */
	private static void setType(DialogType type, ImageView loading_img) {
		switch (type) {
		/*
		 * case NO_NETWORK:
		 * loading_img.setBackgroundResource(R.drawable.unknown); break;
		 */
		case LOAD_SUCCESS:
			loading_img.setBackgroundResource(R.drawable.success);
			break;
		case LOAD_FAILURE:
			loading_img.setBackgroundResource(R.drawable.failure);
			break;
		default:
			break;
		}
	}

	public enum DialogType {
		LOAD_SUCCESS, LOAD_FAILURE
	}
}
