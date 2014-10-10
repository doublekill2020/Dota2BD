package cn.edu.mydotabuff.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.mydotabuff.R;

public class LoadingDialog extends AlertDialog {
	private Context mContext;
	private TextView tvDialogLoading;
	private ImageView ivDialogLoading;
	private String message = null;

	public LoadingDialog(Context context) {
		super(context);
		message = "loading...";
		this.mContext = context;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}

	public LoadingDialog(Context context, String message) {
		super(context);
		this.message = message;
		this.mContext = context;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}

	public LoadingDialog(Context context, int theme, String message) {
		super(context, theme);
		this.message = message;
		this.mContext = context;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_loading);
		tvDialogLoading = (TextView) findViewById(R.id.tvDialogLoading);
		tvDialogLoading.setText(this.message);
		ivDialogLoading = (ImageView) findViewById(R.id.ivDialogLoading);
		startAnimation();
	}

	@Override
	public void show() {
		try {
			super.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (ivDialogLoading != null) {
			startAnimation();
		}

	}

	public void startAnimation() {
		// 鍔犺浇鍔ㄧ敾
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				mContext, R.anim.dialog_loading_animation);
		// 浣跨敤ImageView鏄剧ず鍔ㄧ敾
		ivDialogLoading.startAnimation(hyperspaceJumpAnimation);
	}

	public void setText(String message) {

		tvDialogLoading.setText(message);
	}

	public void setText(int resId) {
		setText(getContext().getResources().getString(resId));
	}

	// private TextView tips_loading_msg;
	//
	// private String message = null;
	//
	// public LoadingDialog(Context context) {
	// super(context);
	// message = "鍔犺浇涓�..";
	// this.setCancelable(false);
	// }
	//
	// public LoadingDialog(Context context, String message) {
	// super(context);
	// this.message = message;
	// this.setCancelable(false);
	// }
	//
	// public LoadingDialog(Context context, int theme, String message) {
	// super(context, theme);
	// this.message = message;
	// this.setCancelable(false);
	// }
	//
	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// this.setContentView(R.layout.view_tips_loading);
	// tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
	// tips_loading_msg.setText(this.message);
	// }
	//
	// public void setText(String message) {
	// this.message = message;
	// tips_loading_msg.setText(this.message);
	// }
	//
	// public void setText(int resId) {
	// setText(getContext().getResources().getString(resId));
	// }

}
