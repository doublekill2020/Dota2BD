package cn.edu.mydotabuff.ui.hero;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.DotaApplication.LocalDataType;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.CommViewHolder;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.model.HeroMatchStatistics;
import cn.edu.mydotabuff.model.HerosSatistics;
import cn.edu.mydotabuff.common.http.OnWebDataGetListener;
import cn.edu.mydotabuff.common.http.WebDataHelper;
import cn.edu.mydotabuff.common.http.WebDataHelper.DataType;
import cn.edu.mydotabuff.ui.recently.ActMatchDetail;
import cn.edu.mydotabuff.util.Utils;
import cn.edu.mydotabuff.view.LoadingDialog;
import cn.edu.mydotabuff.view.RoundAngleImageView;
import cn.edu.mydotabuff.view.TipsToast;
import cn.edu.mydotabuff.view.TipsToast.DialogType;
import cn.edu.mydotabuff.view.XListView;

import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FragHeroList extends BaseFragment implements OnWebDataGetListener {

	private LoadingDialog dialog;
	private List<HerosSatistics> heroSatisticsList = new ArrayList<HerosSatistics>();
	private String userID = "";
	private XListView listView;
	private Activity activity;
	private Drawable icon;
	private ScaleInAnimationAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_hero_used_list, container,
				false);
		activity = getActivity();
		listView = (XListView) view.findViewById(R.id.hero_used_list);

		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(false);
		SharedPreferences myPreferences = activity.getSharedPreferences(
				"user_info", Activity.MODE_PRIVATE);
		userID = myPreferences.getString("userID", "");
		dialog = new LoadingDialog(activity);
		if (myPreferences.getString("isNeedUpdate", "").equals("true")) {
			WebDataHelper helper = new WebDataHelper(activity);
			helper.setDataGetListener(this);
			helper.getWebData(DataType.HERO, userID);
		} else {
			heroSatisticsList = (List<HerosSatistics>) DotaApplication
					.getApplication().getData(LocalDataType.HERO_USED_LIST);
			if (heroSatisticsList == null) {
				WebDataHelper helper = new WebDataHelper(activity);
				helper.setDataGetListener(this);
				helper.getWebData(DataType.HERO, userID);
			} else {
				adapter = new ScaleInAnimationAdapter(new HeroListAdapter(
						this.activity, heroSatisticsList));
				adapter.setAbsListView(listView);
				listView.setAdapter(adapter);
			}
		}
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub
				if (heroSatisticsList.size() > 0) {

					String userId = heroSatisticsList.get(position - 1)
							.getThisHeroDataUri();

					final int heroId = heroSatisticsList.get(position - 1)
							.getHeroID();
					final String heroName = heroSatisticsList.get(position - 1)
							.getHeroName();
					WebDataHelper helper = new WebDataHelper(activity);
					helper.setDataGetListener(new OnWebDataGetListener() {

						@Override
						public void onStartGetData() {
							// TODO Auto-generated method stub
							dialog.show();
						}

						@Override
						public <T> void onGetFinished(T data) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							final ArrayList<HeroMatchStatistics> beans = (ArrayList<HeroMatchStatistics>) data;

							View dlgView = activity.getLayoutInflater()
									.inflate(R.layout.dlg_hms_list, null);

							ListView list = (ListView) dlgView
									.findViewById(R.id.list_hms);
							list.setAdapter(new CommAdapter<HeroMatchStatistics>(
									activity, beans,
									R.layout.act_hero_match_list_item) {

								@Override
								public void convert(CommViewHolder helper,
													HeroMatchStatistics item) {

									// 设置 K D A textView内容
									helper.setText(R.id.tv_kill,
											(int) item.getKill() + "");
									helper.setText(R.id.tv_death,
											(int) item.getDeath() + "");
									helper.setText(R.id.tv_assist,
											(int) item.getAssists() + "");
									// 根据结果改变textColor backgroundColor
									String result = item.getResult();
									helper.setText(R.id.tv_status, result);
									if (result.equals("胜利")) {
										// 胜利 绿色
										helper.setBackgroundColor(
												R.id.tv_status,
												getResources().getColor(
														R.color.my_green));
										helper.setTextColor(
												R.id.tv_kill,
												getResources().getColor(
														R.color.my_green));
										helper.setTextColor(
												R.id.tv_death,
												getResources().getColor(
														R.color.my_green));
										helper.setTextColor(
												R.id.tv_assist,
												getResources().getColor(
														R.color.my_green));

										helper.setImageResource(R.id.img_kill,
												R.drawable.battle_kill_icon_win);
										helper.setImageResource(
												R.id.img_death,
												R.drawable.battle_death_icon_win);
										helper.setImageResource(
												R.id.img_assists,
												R.drawable.battle_assist_icon_win);

									} else {
										// 失败 橘色
										helper.setBackgroundColor(
												R.id.tv_status,
												getResources().getColor(
														R.color.my_orange));

										helper.setTextColor(
												R.id.tv_kill,
												getResources().getColor(
														R.color.my_orange));
										helper.setTextColor(
												R.id.tv_death,
												getResources().getColor(
														R.color.my_orange));
										helper.setTextColor(
												R.id.tv_assist,
												getResources().getColor(
														R.color.my_orange));

										helper.setImageResource(
												R.id.img_kill,
												R.drawable.battle_kill_icon_not_win);
										helper.setImageResource(
												R.id.img_death,
												R.drawable.battle_death_icon_not_win);
										helper.setImageResource(
												R.id.img_assists,
												R.drawable.battle_assist_icon_not_win);
									}

									helper.setText(R.id.tv_whatTime,
											item.getWhatTime());
									helper.setText(R.id.tv_level,
											item.getLevel());
									if (item.getLevel().equals("Very High")) {
										helper.setTextColor(
												R.id.tv_level,
												getResources().getColor(
														R.color.my_orange));
									} else {
										helper.setTextColor(
												R.id.tv_level,
												getResources().getColor(
														R.color.black));
									}
									helper.setText(R.id.tv_matchID, "比赛ID: "
											+ item.getMatchID());
									helper.setText(R.id.tv_matchType,
											item.getMatchType());

								}

							});

							list.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
														View view, int position, long id) {

									String matchID = beans.get(position)
											.getMatchID();
									Intent intent = new Intent();
									intent.setClass(FragHeroList.this.activity,
											ActMatchDetail.class);
									intent.putExtra("matchId", matchID);

									startActivity(intent);
								}

							});
							icon = Utils.getHeroImage(activity,
									Common.getHeroName(heroId));
							AlertDialog mDialog = new AlertDialog.Builder(
									activity)
									.setTitle(heroName)
									.setIcon(icon)
									.setView(dlgView)
									.setNegativeButton(
											"关闭",
											new DialogInterface.OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method stub

													dialog.cancel();

												}
											}).create();
							mDialog.setCanceledOnTouchOutside(false);

							mDialog.show();

						}

						@Override
						public void onGetFailed(String failMsg) {
							dialog.dismiss();
							TipsToast
									.showToast(activity, "不如去看个片放松下再来重新尝试~~ ",
											Toast.LENGTH_SHORT,
											DialogType.LOAD_FAILURE);

						}
					});
					helper.getWebData(DataType.MATCH, userId);

				}

			}

		});
		return view;
	}
	@Override
	public void onStartGetData() {
		// TODO Auto-generated method stub
		dialog.show();
	}

	@Override
	public <T> void onGetFinished(T data) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		heroSatisticsList = (List<HerosSatistics>) data;
		DotaApplication.getApplication().saveData(data,
				LocalDataType.HERO_USED_LIST);
		adapter = new ScaleInAnimationAdapter(new HeroListAdapter(
				this.activity, heroSatisticsList));
		adapter.setAbsListView(listView);
		listView.setAdapter(adapter);
	}

	@Override
	public void onGetFailed(String failMsg) {
		dialog.dismiss();
		TipsToast.showToast(activity, "不如去看个片放松下再来重新尝试~~ ", Toast.LENGTH_SHORT,
				DialogType.LOAD_FAILURE);
	}

	class HeroListAdapter extends BaseAdapter {

		private List<HerosSatistics> beans; // 数据
		private Activity context;
		private ImageLoader loader;

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
		public HerosSatistics getItem(int position) {
			return beans.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = context.getLayoutInflater().inflate(
						R.layout.frag_hero_used_list_item, null);
				ViewHolder holder = new ViewHolder();
				holder.tv_heroName = (TextView) convertView
						.findViewById(R.id.tv_heroName);
				holder.tv_allKAD = (TextView) convertView
						.findViewById(R.id.tv_allKAD);
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
			String format = getResources().getString(
					R.string.text_hero_item_used);

			holder.tv_heroName
					.setText(String.format(format, bean.getHeroName()));

			format = getResources().getString(R.string.text_hero_item_kda);

			holder.tv_allKAD.setText(String.format(format, bean.getAllKAD()));

			holder.tv_usesTimes.setText("使用次数: " + bean.getUseTimes() + "");
			holder.tv_KDA.setText("KDA:       " + bean.getKDA() + "");
			holder.tv_perCoin
					.setText("gold/min: " + bean.getGold_PerMin() + "");
			holder.tv_perXp.setText("xp/min:  " + bean.getXp_PerMin() + "");
			holder.pb_winRate.setProgress((int) bean.getWinning());
			holder.tv_wining.setText(bean.getWinning() + "%");
			loader.displayImage(
					Utils.getHeroImageUri(Common.getHeroName(bean.getHeroID())),
					holder.icon);
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

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 释放Drawable 先转成bitmap 然后释放
		if (icon != null) {
			BitmapDrawable bd = (BitmapDrawable) icon;
			if (bd != null) {
				bd.getBitmap().recycle();
			}
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		super.onCreateOptionsMenu(menu, inflater);
	}
}
