package cn.edu.mydotabuff.hero;

import java.util.ArrayList;
import java.util.List;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.R.layout;
import cn.edu.mydotabuff.bean.HerosSatistics;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.custom.LoadingDialog;
import cn.edu.mydotabuff.http.OnWebDataGetListener;
import cn.edu.mydotabuff.http.WebDataHelper;
import cn.edu.mydotabuff.http.WebDataHelper.DataType;
import cn.edu.mydotabuff.view.XListView;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FragHeroList extends Fragment implements OnWebDataGetListener {

	private LoadingDialog dialog;
	private List<HerosSatistics> heroSatisticsList = new ArrayList<HerosSatistics>();
	private String userID = "";
	private XListView listView;
	private HeroListAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_hero_used_list,
				container, false);
		listView = (XListView) view.findViewById(R.id.hero_used_list);
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(false);
		userID = getActivity().getIntent().getStringExtra("userID");
		dialog = new LoadingDialog(getActivity());
		WebDataHelper helper = new WebDataHelper(getActivity());
		helper.setDataGetListener(this);
		helper.getWebData(DataType.HERO, userID);
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
		for (HerosSatistics beans : heroSatisticsList) {
			System.out.println(beans.toString());
		}
	}

	@Override
	public void onGetFailed() {
		// TODO Auto-generated method stub
		dialog.dismiss();
	}
	class HeroListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
