package cn.edu.mydotabuff.ui.game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.R;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

public class ActInvokerGame extends Activity implements OnClickListener {

	private Map<Integer, String> skillsMap;
	private ImageLoader loader;
	private ImageView skillView, wBtn, qBtn, eBtn;
	private Random r = new Random();
	private int index = 1;
	private TextView timeView;
	private int pressedNum = 0;
	private String skillStr = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		skillsMap = new HashMap<Integer, String>();
		// z 灵动迅捷
		skillsMap.put(1, "assets://abilities_images/invoker_alacrity_hp1.jpg");
		// d 混沌陨石
		skillsMap.put(2,
				"assets://abilities_images/invoker_chaos_meteor_hp1.jpg");
		// y 急速冷却
		skillsMap.put(3, "assets://abilities_images/invoker_cold_snap_hp1.jpg");
		// b 超震声波
		skillsMap.put(4,
				"assets://abilities_images/invoker_deafening_blast_hp1.jpg");
		// c 电磁脉冲
		skillsMap.put(5, "assets://abilities_images/invoker_emp_hp1.jpg");
		// f 熔炉精灵
		skillsMap.put(6,
				"assets://abilities_images/invoker_forge_spirit_hp1.jpg");
		// v 幽灵漫步
		skillsMap
				.put(7, "assets://abilities_images/invoker_ghost_walk_hp1.jpg");
		// g 寒冰之墙
		skillsMap.put(8, "assets://abilities_images/invoker_ice_wall_hp1.jpg");
		// t 阳炎冲击
		skillsMap
				.put(9, "assets://abilities_images/invoker_sun_strike_hp1.jpg");
		// x 强袭飓风
		skillsMap.put(10, "assets://abilities_images/invoker_tornado_hp1.jpg");
		loader = ImageLoader.getInstance();
		initView();
		initEvent();
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.act_invoker_game);
		skillView = (ImageView) findViewById(R.id.skill);
		wBtn = (ImageView) findViewById(R.id.wBtn);
		qBtn = (ImageView) findViewById(R.id.qBtn);
		eBtn = (ImageView) findViewById(R.id.eBtn);
		timeView = (TextView) findViewById(R.id.time);
		loader.displayImage(getSkillImg(), skillView);
		new CountDownTimer(30000, 1000) {
			public void onTick(long millisUntilFinished) {
				timeView.setText("倒计时：" + millisUntilFinished / 1000 + "s");
			}

			public void onFinish() {
				timeView.setText("时间到啦！");
			}

		}.start();
	}

	private void initEvent() {
		wBtn.setOnClickListener(this);
		qBtn.setOnClickListener(this);
		eBtn.setOnClickListener(this);
	}

	String getSkillImg() {
		int num = r.nextInt(11);
		if (num == 0) {
			num = 1;
		}
		return skillsMap.get(num);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.qBtn:
			if (skillStr.length() < 3) {
				skillStr += "q";
			} else {

			}
			break;
		case R.id.wBtn:

			break;
		case R.id.eBtn:

			break;
		default:
			break;
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
}
