package cn.edu.mydotabuff.ui.recently;

import java.io.IOException;
import java.util.ArrayList;
import java.util.WeakHashMap;

import javax.crypto.spec.PSource;

import org.json2.JSONException;

import cn.edu.mydotabuff.util.Utils;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.mydotabuff.DataManager;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.common.bean.ItemsItem;
import cn.edu.mydotabuff.common.bean.MatchBean;
import cn.edu.mydotabuff.common.bean.PlayerDetailBean;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.view.CircleImageView;
import cn.edu.mydotabuff.view.TipsToast;

public class ActMatchDetailItemAdapter extends BaseAdapter {
	private Activity _caller;
	private final ArrayList<PlayerDetailBean> _beans;
	private ImageLoader loader;
	private int num[] = new int[8];

	private ArrayList<String> moreList = new ArrayList<String>();

	public ActMatchDetailItemAdapter(Activity caller,
			ArrayList<PlayerDetailBean> beans, int num[]) {
		_caller = caller;
		_beans = beans;
		loader = ImageLoader.getInstance();
		this.num = num;
	}

	public int getCount() {
		return _beans.size();
	}

	@Override
	public PlayerDetailBean getItem(int position) {
		return _beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// public void addMoreData(ArrayList<PlayerDetailBean> more) {
	// _beans.addAll(more);
	// notifyDataSetChanged();
	// }

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = _caller.getLayoutInflater().inflate(
					R.layout.act_match_detail_item, null);
			holder = new ViewHolder();
			holder.tipView = convertView.findViewById(R.id.tipView);
			holder.label = (TextView) convertView.findViewById(R.id.label);
			holder.level = (TextView) convertView.findViewById(R.id.level);
			holder.kill = (TextView) convertView.findViewById(R.id.kill);
			holder.death = (TextView) convertView.findViewById(R.id.death);
			holder.assist = (TextView) convertView.findViewById(R.id.assist);
			holder.h[1] = (ImageView) convertView.findViewById(R.id.item_1);
			holder.h[2] = (ImageView) convertView.findViewById(R.id.item_2);
			holder.h[3] = (ImageView) convertView.findViewById(R.id.item_3);
			holder.h[4] = (ImageView) convertView.findViewById(R.id.item_4);
			holder.h[5] = (ImageView) convertView.findViewById(R.id.item_5);
			holder.h[6] = (ImageView) convertView.findViewById(R.id.item_6);
			holder.icon = (CircleImageView) convertView.findViewById(R.id.icon);
			holder.total_kill = (TextView) convertView
					.findViewById(R.id.total_kill);
			holder.total_death = (TextView) convertView
					.findViewById(R.id.total_death);
			holder.total_assist = (TextView) convertView
					.findViewById(R.id.total_assist);
			holder.total_money = (TextView) convertView
					.findViewById(R.id.total_money);
			holder.itemView = convertView.findViewById(R.id.item_view);
			holder.moreView = convertView.findViewById(R.id.more_view);
			holder.arrowView = (ImageView) convertView.findViewById(R.id.arrow);
			holder.txView[0] = (TextView) convertView.findViewById(R.id.tag1);
			holder.txView[1] = (TextView) convertView.findViewById(R.id.tag2);
			holder.txView[2] = (TextView) convertView.findViewById(R.id.tag3);
			holder.txView[3] = (TextView) convertView.findViewById(R.id.tag4);
			holder.txView[4] = (TextView) convertView.findViewById(R.id.tag5);
			holder.txView[5] = (TextView) convertView.findViewById(R.id.tag6);
			holder.heroIcon = (CircleImageView) convertView
					.findViewById(R.id.hero_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final PlayerDetailBean bean = getItem(position);

		if (moreList.contains(position + "")) {
			showMoreView(holder, bean, false);
		} else {
			hideMoreView(holder);
		}

		holder.level.setText("Lv" + bean.getLevel() + " "
				+ bean.getPlayerInfoBeans().getName());
		holder.kill.setText(" " + bean.getKills());
		holder.death.setText(" " + bean.getDeaths());
		holder.assist.setText(" " + bean.getAssists());
		loader.displayImage(
				Utils.getItemsImageUri(Common.getItemName(bean.getItem_0())),
				holder.h[1]);
		loader.displayImage(
				Utils.getItemsImageUri(Common.getItemName(bean.getItem_1())),
				holder.h[2]);
		loader.displayImage(
				Utils.getItemsImageUri(Common.getItemName(bean.getItem_2())),
				holder.h[3]);
		loader.displayImage(
				Utils.getItemsImageUri(Common.getItemName(bean.getItem_3())),
				holder.h[4]);
		loader.displayImage(
				Utils.getItemsImageUri(Common.getItemName(bean.getItem_4())),
				holder.h[5]);
		loader.displayImage(
				Utils.getItemsImageUri(Common.getItemName(bean.getItem_5())),
				holder.h[6]);
		// Common.setItemIcon(convertView.getContext(), bean.getItem_0(),
		// holder.h[1]);
		// Common.setItemIcon(convertView.getContext(), bean.getItem_1(),
		// holder.h[2]);
		// Common.setItemIcon(convertView.getContext(), bean.getItem_2(),
		// holder.h[3]);
		// Common.setItemIcon(convertView.getContext(), bean.getItem_3(),
		// holder.h[4]);
		// Common.setItemIcon(convertView.getContext(), bean.getItem_4(),
		// holder.h[5]);
		// Common.setItemIcon(convertView.getContext(), bean.getItem_5(),
		// holder.h[6]);
		holder.h[1].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Utils.startItemsDetailActivity(
							_caller,
							DataManager.getItemsItem(_caller,
									Common.getItemName(bean.getItem_0())));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		holder.h[2].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Utils.startItemsDetailActivity(
							_caller,
							DataManager.getItemsItem(_caller,
									Common.getItemName(bean.getItem_1())));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		holder.h[3].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Utils.startItemsDetailActivity(
							_caller,
							DataManager.getItemsItem(_caller,
									Common.getItemName(bean.getItem_2())));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		holder.h[4].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Utils.startItemsDetailActivity(
							_caller,
							DataManager.getItemsItem(_caller,
									Common.getItemName(bean.getItem_3())));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		holder.h[5].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Utils.startItemsDetailActivity(
							_caller,
							DataManager.getItemsItem(_caller,
									Common.getItemName(bean.getItem_4())));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		holder.h[6].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Utils.startItemsDetailActivity(
							_caller,
							DataManager.getItemsItem(_caller,
									Common.getItemName(bean.getItem_5())));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		loader.displayImage(bean.getPlayerInfoBeans().getMediumIcon(),
				holder.icon);
		if (position == 0) {
			holder.tipView.setVisibility(View.VISIBLE);
			holder.label.setBackgroundDrawable(_caller.getResources()
					.getDrawable(R.drawable.battle_win_lable));
			holder.label.setText("胜利方");
			Drawable drawable = _caller.getResources().getDrawable(
					R.drawable.battle_kill_icon_win);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			holder.total_kill.setText(num[0] + "");
			holder.total_kill.setCompoundDrawables(drawable, null, null, null);
			drawable = _caller.getResources().getDrawable(
					R.drawable.battle_death_icon_win);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			holder.total_death.setText(num[1] + "");
			holder.total_death.setCompoundDrawables(drawable, null, null, null);
			drawable = _caller.getResources().getDrawable(
					R.drawable.battle_assist_icon_win);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			holder.total_assist.setText(num[2] + "");
			holder.total_assist
					.setCompoundDrawables(drawable, null, null, null);
			drawable = _caller.getResources().getDrawable(
					R.drawable.battle_money_icon_win);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			holder.total_money.setText(num[3] + "");
			holder.total_money.setCompoundDrawables(drawable, null, null, null);
		} else if (position == 5) {
			holder.tipView.setVisibility(View.VISIBLE);
			holder.label.setBackgroundDrawable(_caller.getResources()
					.getDrawable(R.drawable.battle_lose_lable));
			holder.label.setText("失败方");
			Drawable drawable = _caller.getResources().getDrawable(
					R.drawable.battle_kill_icon_not_win);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			holder.total_kill.setText(num[4] + "");
			holder.total_kill.setCompoundDrawables(drawable, null, null, null);
			drawable = _caller.getResources().getDrawable(
					R.drawable.battle_death_icon_not_win);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			holder.total_death.setText(num[5] + "");
			holder.total_death.setCompoundDrawables(drawable, null, null, null);
			drawable = _caller.getResources().getDrawable(
					R.drawable.battle_assist_icon_not_win);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			holder.total_assist.setText(num[6] + "");
			holder.total_assist
					.setCompoundDrawables(drawable, null, null, null);
			drawable = _caller.getResources().getDrawable(
					R.drawable.battle_money_icon_not_win);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			holder.total_money.setText(num[7] + "");
			holder.total_money.setCompoundDrawables(drawable, null, null, null);
		} else {
			holder.tipView.setVisibility(View.GONE);
		}
		holder.itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (holder.moreView.getVisibility() == View.GONE) {
					showMoreView(holder, bean, true);
					if (!moreList.contains(position + "")) {
						moreList.add(position + "");
					}
				} else if (holder.moreView.getVisibility() == View.VISIBLE) {
					hideMoreView(holder);
					moreList.remove(position + "");
				}
			}
		});
		return convertView;
	}

	private void showMoreView(ViewHolder holder, final PlayerDetailBean bean,
			boolean isShowAnimation) {
		holder.arrowView.setImageResource(R.drawable.arrow_up);
		holder.moreView.setVisibility(View.VISIBLE);
		holder.txView[0].setText("总经济:"
				+ (bean.getGold() + bean.getGold_spent()));
		holder.txView[2].setText("gold/min:" + bean.getGold_per_min());
		holder.txView[1].setText("英雄伤害:" + bean.getHero_damage());
		holder.txView[3].setText("英雄治疗:" + bean.getHero_healing());
		holder.txView[4].setText("对建筑伤害:" + bean.getTower_damage());
		holder.txView[5].setText("xp/min:" + bean.getXp_per_min());
		loader.displayImage(
				Utils.getHeroImageUri(Common.getHeroName(bean.getHero_id())),
				holder.heroIcon);
		if (isShowAnimation) {
			Animation animation = AnimationUtils.loadAnimation(_caller,
					R.anim.listview_moreview_show);
			holder.moreView.setAnimation(animation);
		}
		holder.heroIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utils.startHeroDetailActivity(_caller,
						Common.getHeroName(bean.getHero_id()));
			}
		});
	}

	private void hideMoreView(ViewHolder holder) {
		holder.arrowView.setImageResource(R.drawable.arrow_down);
		holder.moreView.setVisibility(View.GONE);
	}

	class ViewHolder {
		private ImageView h[] = new ImageView[7];
		private CircleImageView icon, heroIcon;
		private TextView level, kill, death, assist, total_money, total_kill,
				total_death, total_assist, label;
		private View tipView;
		private View itemView, moreView;
		private TextView txView[] = new TextView[6];
		private ImageView arrowView;
	}
}
