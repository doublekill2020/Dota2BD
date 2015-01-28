package cn.edu.mydotabuff.view;

import cn.edu.mydotabuff.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName: LoadingDialog
 * @Description: TODO 请求网络弹出的加载Dialog
 * @author hepengcheng
 * @date 2014-10-31 下午12:11:27
 * 
 */
public class LoadingDialog extends AlertDialog {
	private TextView tvDialogLoading;
	private ImageView ivDialogLoading;
	private String message = null;
	private ProgressWheel progressWheel;
	private Activity _context;

	public LoadingDialog(Activity context) {
		super(context);
		_context = context;
		message = "努力加载中...";
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}

	public LoadingDialog(Activity context, String message) {
		super(context);
		_context = context;
		this.message = message;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}

	public LoadingDialog(Activity context, int theme, String message) {
		super(context, theme);
		_context = context;
		this.message = message;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dlg_loading);
		tvDialogLoading = (TextView) findViewById(R.id.tvDialogLoading);
		tvDialogLoading.setText(this.message);
		progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
		// 自动旋转
		progressWheel.spin();
		// 设置边缘Bar条颜色
		progressWheel.setBarColor(_context.getResources().getColor(
				R.color.my_blue));

		// 设置wheel颜色
		progressWheel.setRimColor(Color.LTGRAY);
	}

	@Override
	public void show() {
		if (!_context.isFinishing()) {
			super.show();
		}
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		if (!_context.isFinishing()) {
			super.dismiss();
			if (progressWheel != null) {
				progressWheel.stopSpinning();
				progressWheel = null;
			}
		}
	}

	public void setText(String message) {

		if (message != null)
			tvDialogLoading.setText(message);
	}

	public void setText(int resId) {
		setText(getContext().getResources().getString(resId));
	}

}
