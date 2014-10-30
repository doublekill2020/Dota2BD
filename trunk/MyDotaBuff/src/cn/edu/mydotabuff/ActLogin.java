package cn.edu.mydotabuff;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.umeng.analytics.MobclickAgent;

public class ActLogin extends Activity implements OnClickListener {
	private EditText editText;
	private Button submitBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initView();
	}

	public void initView() {

		setContentView(R.layout.act_login);

		editText = (EditText) findViewById(R.id.editText);
		submitBtn = (Button) findViewById(R.id.submit);

		submitBtn.setOnClickListener(this);

		SharedPreferences myPreferences = getSharedPreferences("userID",
				Activity.MODE_PRIVATE);
		String userID = myPreferences.getString("userID", "");
		if (userID != null) {
			editText.setText(userID);
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if (v == submitBtn) {
			String ID = editText.getText().toString();
			if (ID != null && ID.length() == 9) {
				Intent intent = new Intent();
				intent.setClass(ActLogin.this, MainActivity.class);
				intent.putExtra("userID", ID);
				startActivity(intent);
			} else {
				new AlertDialog.Builder(this)
						.setTitle("提示:")
						.setMessage("输入有误，请重新输入！")
						.setCancelable(false)
						.setPositiveButton("关闭",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO 自动生成的方法存根
										editText.setText("");
									}
								}).show();
			}
		}
	}
}
