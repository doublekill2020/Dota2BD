package cn.edu.mydotabuff.hero;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.bean.HeroMatchStatistics;
import cn.edu.mydotabuff.bean.HerosSatistics;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.CommViewHolder;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.custom.LoadingDialog;
import cn.edu.mydotabuff.http.OnWebDataGetListener;
import cn.edu.mydotabuff.http.WebDataHelper;
import cn.edu.mydotabuff.http.WebDataHelper.DataType;
import cn.edu.mydotabuff.util.Utils;
import cn.edu.mydotabuff.view.RoundAngleImageView;
import cn.edu.mydotabuff.view.XListView;

import com.nostra13.universalimageloader.core.ImageLoader;

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
		adapter = new HeroListAdapter(this.getActivity(), heroSatisticsList);
		listView.setAdapter(adapter);
	}

	@Override
	public void onGetFailed(String failMsg) {
		dialog.dismiss();
	}

	class HeroListAdapter extends BaseAdapter {

		private List<HerosSatistics> beans; // 数据
		private Activity context;
		private WeakHashMap<Integer, View> map;
		private ImageLoader loader;

		public HeroListAdapter(Activity context, List<HerosSatistics> beans) {
			this.context = context;
			this.beans = beans;
			loader = ImageLoader.getInstance();
			map = new WeakHashMap<Integer, View>();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return beans.size();
		}

		@Override
		public HerosSatistics getItem(int position) {
			return beans.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = map.get(position);
			if (convertView == null) {
				convertView = context.getLayoutInflater().inflate(
						R.layout.frag_hero_used_list_item, null);
				ViewHolder holder = new ViewHolder();
				holder.tv_allKAD = (TextView) convertView
						.findViewById(R.id.tv_allKAD);
				holder.tv_heroName = (TextView) convertView
						.findViewById(R.id.tv_heroName);
				holder.tv_usesTimes = (TextView) convertView
						.findViewById(R.id.usesTimes);
				holder.tv_KDA = (TextView) convertView
						.findViewById(R.id.tv_KDA);
				holder.tv_perCoin = (TextView) convertView
						.findViewById(R.id.tv_perCoin);
				holder.tv_perXp = (TextView) convertView
						.findViewById(R.id.tv_perXP);
				holder.icon = (RoundAngleImageView) convertView
						.findViewById(R.id.icon);
				holder.tv_wining = (TextView) convertView
						.findViewById(R.id.pb_winRatesText);
				holder.pb_winRate = (ProgressBar) convertView
						.findViewById(R.id.pb_winRate);
				convertView.setTag(holder);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			HerosSatistics bean = beans.get(position);
			holder.tv_allKAD.setText(bean.getAllKAD() + "");
			holder.tv_heroName.setText(bean.getHeroName());
			holder.tv_usesTimes.setText(bean.getUseTimes() + "");
			holder.tv_KDA.setText(bean.getKDA() + "");
			holder.tv_perCoin.setText(bean.getGold_PerMin() + "");
			holder.tv_perXp.setText(bean.getXp_PerMin() + "");
			holder.pb_winRate.setProgress((int) bean.getWinning());
			holder.tv_wining.setText(bean.getWinning() + "%");
			loader.displayImage(
					Utils.getHeroImageUri(Common.getHeroName(bean.getHeroID())),
					holder.icon);
			map.put(position, convertView);
			return convertView;
		}

	}

	private static class ViewHolder {
		private TextView tv_allKAD;
		private TextView tv_heroName;
		private TextView tv_usesTimes;
		private TextView tv_KDA;
		private TextView tv_perCoin;
		private TextView tv_perXp;
		private RoundAngleImageView icon;
		private TextView tv_wining;
		private ProgressBar pb_winRate;
	}

	private void initEvents() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (heroSatisticsList.size() > 0) {

					String userId = heroSatisticsList.get(position - 1)
							.getThisHeroDataUri();
					WebDataHelper helper = new WebDataHelper(getActivity());
					helper.setDataGetListener(new OnWebDataGetListener() {

						@Override
						public void onStartGetData() {
							// TODO Auto-generated method stub
							dialog.show();
						}

						@Override
						public <T> void onGetFinished(List<T> data) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							final ArrayList<HeroMatchStatistics> beans = (ArrayList<HeroMatchStatistics>) data;
							
							for (HeroMatchStatistics heroMatchStatistics : beans) {
								System.out.println(heroMatchStatistics.toString());
							}
							
						/*	View dlgView = getActivity().getLayoutInflater()
									.inflate(R.layout.dlg_user_list, null);

							ListView list = (ListView) dlgView.findViewById(R.id.list_hms);
							 list.setAdapter(new CommAdapter<HeroMatchStatistics>(getActivity(),beans ,R.layout.act_hero_match_list_item) {

									@Override
									public void convert(CommViewHolder helper,
											HeroMatchStatistics item) {
										helper.setText(R.id.tv_whatTime, item.getWhatTime());
										helper.setText(R.id.tv_level, item.getLevel());
										helper.setText(R.id.tv_kill, item.getKill()+"");
										helper.setText(R.id.tv_death,item.getDeath()+"");
										helper.setText(R.id.tv_death, item.getDeath()+"");
										String result = item.getResult();
										helper.setText(R.id.tv_status	, result);
										if (result.equals("胜利")) {
											helper.setBackgroundColor(R.id.tv_status, getResources().getColor(R.color.my_green));
										}else {
											helper.setBackgroundColor(R.id.tv_status, getResources().getColor(R.color.my_orange));
										}
										
									}
									 
									 
								});
								 
								 list.setOnItemClickListener(new OnItemClickListener(){

									@Override
									public void onItemClick(AdapterView<?> parent,
											View view, int position, long id) {
										String matchID = beans.get(position-1).getMatchID();
										System.out.println(matchID);
									}
									 
								 });
							 AlertDialog mDialog = new AlertDialog.Builder(
									getActivity()).setTitle("英雄比赛统计")
									.setView(dlgView)
									.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											dialog.cancel();
										}
									})
									.create();
							 mDialog.setCanceledOnTouchOutside(false);
							 mDialog.show();*/
							
						}

						@Override
						public void onGetFailed(String failMsg) {
							// TODO Auto-generated method stub

						}
					});
					helper.getWebData(DataType.MATCH, userId);

				}

			}

		});

	}

}
