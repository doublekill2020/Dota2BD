package cn.edu.mydotabuff.hero;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.b;

import u.aly.be;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.R.layout;
import cn.edu.mydotabuff.bean.HerosSatistics;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.custom.LoadingDialog;
import cn.edu.mydotabuff.http.OnWebDataGetListener;
import cn.edu.mydotabuff.http.WebDataHelper;
import cn.edu.mydotabuff.http.WebDataHelper.DataType;
import cn.edu.mydotabuff.util.Utils;
import cn.edu.mydotabuff.view.RoundAngleImageView;
import cn.edu.mydotabuff.view.XListView;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragHeroList extends Fragment implements OnWebDataGetListener {

	private LoadingDialog dialog;
	private List<HerosSatistics> heroSatisticsList = new ArrayList<HerosSatistics>();
	private String userID = "";
	private XListView listView;
	private HeroListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_hero_used_list, container,
				false);
		listView = (XListView) view.findViewById(R.id.hero_used_list);

		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(false);
		userID = getActivity().getIntent().getStringExtra("userID");
		dialog = new LoadingDialog(getActivity());
		WebDataHelper helper = new WebDataHelper(getActivity());
		helper.setDataGetListener(this);
		helper.getWebData(DataType.HERO, userID);
		initEvents();
		
		return view;
	}

	@Override
	public void onStartGetData() {
		// TODO Auto-generated method stub
		dialog.show();
	}

	@Override
	public <T> void onGetFinished(List<T> data) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		heroSatisticsList = (List<HerosSatistics>) data;
		System.out.println(heroSatisticsList.size());
		for (HerosSatistics beans : heroSatisticsList) {
			System.out.println(beans.toString());
		}
		adapter = new HeroListAdapter(this.getActivity(), heroSatisticsList);
		listView.setAdapter(adapter);
		
	}

	@Override
	public void onGetFailed() {
		dialog.dismiss();
	}

	class HeroListAdapter extends BaseAdapter {

		private List<HerosSatistics> beans; // 数据
		private Activity context;
		private TextView tv_allKAD;
		private TextView tv_heroName;
		private TextView tv_usesTimes;
		private TextView tv_KDA;
		private TextView tv_perCoin;
		private TextView tv_perXp;
		private RoundAngleImageView icon;
		private ImageLoader loader;
		private TextView tv_wining;
		private ProgressBar pb_winRate;

		public HeroListAdapter(Activity context, List<HerosSatistics> beans) {
			this.context = context;
			this.beans = beans;
			loader = ImageLoader.getInstance();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return beans.size();
		}

		@Override
		public Object getItem(int position) {
			return beans.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = context.getLayoutInflater().inflate(
					R.layout.frag_hero_used_list_item, null);
			tv_allKAD = (TextView) convertView.findViewById(R.id.tv_allKAD);
			tv_heroName = (TextView) convertView.findViewById(R.id.tv_heroName);
			tv_usesTimes = (TextView) convertView.findViewById(R.id.usesTimes);
			tv_KDA = (TextView) convertView.findViewById(R.id.tv_KDA);
			tv_perCoin = (TextView) convertView.findViewById(R.id.tv_perCoin);
			tv_perXp = (TextView) convertView.findViewById(R.id.tv_perXP);
			icon = (RoundAngleImageView) convertView.findViewById(R.id.icon);
			tv_wining =(TextView) convertView.findViewById(R.id.pb_winRatesText);
			pb_winRate = (ProgressBar) convertView.findViewById(R.id.pb_winRate);
			HerosSatistics bean = beans.get(position);
			tv_allKAD.setText(bean.getAllKAD() + "");
			tv_heroName.setText(bean.getHeroName());
			tv_usesTimes.setText(bean.getUseTimes() + "");
			tv_KDA.setText(bean.getKDA() + "");
			tv_perCoin.setText(bean.getGold_PerMin() + "");
			tv_perXp.setText(bean.getXp_PerMin() + "");
			pb_winRate.setProgress( (int)bean.getWinning());
			tv_wining.setText(bean.getWinning()+"%");
			loader.displayImage(Utils.getHeroImageUri(Common.getHeroName(bean
					.getHeroID())), icon);
			return convertView;
		}

	}
	
	private void initEvents(){
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (heroSatisticsList.size()>0) {
					System.out.println(heroSatisticsList.get(position-1).getThisHeroDataUri());
				}
				
			}
			
			
		});
		
	} 

}
