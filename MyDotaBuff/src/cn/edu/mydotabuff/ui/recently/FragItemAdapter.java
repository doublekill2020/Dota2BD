package cn.edu.mydotabuff.ui.recently;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.common.bean.MatchBean;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.util.Utils;

public class FragItemAdapter extends BaseAdapter {
	private Activity _caller;
	private final ArrayList<MatchBean> _beans;
	private ImageLoader loader;

	public FragItemAdapter(Activity caller, ArrayList<MatchBean> beans) {
		_caller = caller;
		_beans = beans;
		loader = ImageLoader.getInstance();
	}

	public int getCount() {
		return _beans.size();
	}

	@Override
	public MatchBean getItem(int position) {
		return _beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addMoreData(ArrayList<MatchBean> more) {
		_beans.addAll(more);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = _caller.getLayoutInflater().inflate(
					R.layout.frag_recently_item, null);
			ViewHolder holder = new ViewHolder();
			holder.game_mode = (TextView) convertView
					.findViewById(R.id.game_mode);
			holder.game_num = (TextView) convertView
					.findViewById(R.id.game_num);
			holder.game_time = (TextView) convertView
					.findViewById(R.id.game_time);
			holder.h[1] = (ImageView) convertView.findViewById(R.id.hero_1);
			holder.h[2] = (ImageView) convertView.findViewById(R.id.hero_2);
			holder.h[3] = (ImageView) convertView.findViewById(R.id.hero_3);
			holder.h[4] = (ImageView) convertView.findViewById(R.id.hero_4);
			holder.h[5] = (ImageView) convertView.findViewById(R.id.hero_5);
			holder.h[6] = (ImageView) convertView.findViewById(R.id.hero_6);
			holder.h[7] = (ImageView) convertView.findViewById(R.id.hero_7);
			holder.h[8] = (ImageView) convertView.findViewById(R.id.hero_8);
			holder.h[9] = (ImageView) convertView.findViewById(R.id.hero_9);
			holder.h[10] = (ImageView) convertView.findViewById(R.id.hero_10);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		MatchBean bean = getItem(position);

		/*
		 * holder.hero_name.setText(bean.getHero_id() + "");
		 * holder.itemBg.setImageDrawable(getDrawable());
		 */

		holder.game_mode.setText(Common.getGameMode(bean.getLobbyType()));
		if (bean.getLobbyType() == 7) {
			holder.game_mode.setTextColor(android.graphics.Color.RED);
		} else {
			holder.game_mode.setTextColor(android.graphics.Color.BLACK);
		}
		holder.game_num.setText("比赛ID:" + bean.getMatchId());
		holder.game_time.setText(TimeHelper.TimeStamp2Date(bean.getStartTime(),
				"MM-dd HH:mm"));
		for (int i = 1; i <= bean.getPlayers().size(); i++) {
			// Common.setHeroIcon(convertView.getContext(),
			// bean.getPlayers().get(i - 1).getHeroId(), holder.h[i]);
			loader.displayImage(
					Utils.getHeroImageUri(Common.getHeroName(bean.getPlayers()
							.get(i - 1).getHeroId())), holder.h[i]);
		}
		return convertView;
	}

	class ViewHolder {
		private ImageView h[] = new ImageView[11];
		private TextView game_mode, game_num, game_time;
	}
}
