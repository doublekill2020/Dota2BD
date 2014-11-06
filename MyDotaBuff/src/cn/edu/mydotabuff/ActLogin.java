package cn.edu.mydotabuff;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import cn.edu.mydotabuff.bean.UserInfo;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.CommViewHolder;
import cn.edu.mydotabuff.custom.LoadingDialog;
import cn.edu.mydotabuff.custom.TipsToast;
import cn.edu.mydotabuff.custom.TipsToast.DialogType;
import cn.edu.mydotabuff.http.OnWebDataGetListener;
import cn.edu.mydotabuff.http.WebDataHelper;
import cn.edu.mydotabuff.http.WebDataHelper.DataType;

import com.google.pm.service.Occultation;
import com.umeng.analytics.MobclickAgent;

public class ActLogin extends Activity implements OnClickListener,
		OnWebDataGetListener {
	private EditText editText;
	private Button submitBtn, typeBtn;
	private String type = "ID";
	private LoadingDialog dialog;
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		dialog = new LoadingDialog(this);
		initView();
		Occultation.getInstance(this).oponeData();
	}

	public void initView() {
		setContentView(R.layout.act_login);

		editText = (EditText) findViewById(R.id.editText);
		submitBtn = (Button) findViewById(R.id.submit);
		typeBtn = (Button) findViewById(R.id.btn);
		submitBtn.setOnClickListener(this);
		typeBtn.setOnClickListener(this);
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
			if (type.equals("ID")) {
				String ID = editText.getText().toString();
				if (ID != null && (ID.length() == 9 || ID.length() == 8)) {
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
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO 自动生成的方法存根
											editText.setText("");
										}
									}).show();
				}
			} else if (type.equals("NAME")) {
				String text = editText.getText().toString();
				if (text != null || text.length() > 0) {
					WebDataHelper helper = new WebDataHelper(this);
					helper.setDataGetListener(this);
					helper.getWebData(DataType.USER, text);
				} else {
					new AlertDialog.Builder(this)
							.setTitle("提示:")
							.setMessage("输入有误，请重新输入！")
							.setCancelable(false)
							.setPositiveButton("关闭",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO 自动生成的方法存根
											editText.setText("");
										}
									}).show();
				}
			}
		} else if (v == typeBtn) {
			String text = typeBtn.getText().toString().trim();
			if (text.equals("按ID")) {
				type = "NAME";
				typeBtn.setText("按昵称");
				editText.setText(null);
				editText.setHint("请输入要查询的昵称：");
				editText.setInputType(InputType.TYPE_CLASS_TEXT);
			} else if (text.equals("按昵称")) {
				type = "ID";
				typeBtn.setText("按ID");
				editText.setText(null);
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				editText.setHint("请输入要查询的数字ID：");
			}
		}
	}

	@Override
	public void onStartGetData() {
		// TODO Auto-generated method stub
		dialog.show();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void onGetFinished(List<T> data) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		// size=1 只有一条数据 直接跳转
		if (data.size() == 1) {
			System.out.println((ArrayList<UserInfo>) data);
			UserInfo info = (UserInfo) data.get(0);
			String ID = info.getUserID().trim();
			Intent intent = new Intent();
			intent.setClass(ActLogin.this, MainActivity.class);
			intent.putExtra("userID", ID);
			startActivity(intent);
		} else {
			View dlgView = getLayoutInflater().inflate(R.layout.dlg_user_list,
					null);
			list = (ListView) dlgView.findViewById(R.id.list);
			AlertDialog dialog = new AlertDialog.Builder(this)
					.setTitle("搜索结果:")
					.setView(dlgView)
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							}).create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			final ArrayList<UserInfo> beans = (ArrayList<UserInfo>) data;
			list.setAdapter(new CommAdapter<UserInfo>(this, beans,
					R.layout.dlg_user_list_item) {

				@Override
				public void convert(CommViewHolder helper, UserInfo item) {
					// TODO Auto-generated method stub
					helper.setText(R.id.name, item.getUserName());
					helper.setText(R.id.id, item.getUserID());
					helper.setImageFromWeb(R.id.icon, item.getImgUrl(), 2);
				}
			});
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					String ID = beans.get(position).getUserID();
					// 除去ID：
					ID = ID.substring(3, ID.length());
					Intent intent = new Intent();
					intent.setClass(ActLogin.this, MainActivity.class);
					intent.putExtra("userID", ID);
					startActivity(intent);
				}
			});
		}
	}

	@Override
	public void onGetFailed(String failMsg) {
		// TODO Auto-generated method stub
		if (failMsg.equals("无匹配结果")) {
			TipsToast.showToast(this, failMsg, Toast.LENGTH_SHORT,
					DialogType.LOAD_FAILURE);
		}
		dialog.dismiss();
	}
}
