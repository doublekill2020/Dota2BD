package cn.edu.mydotabuff.ui;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.R.layout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragLeftMenu extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.frag_left_menu, container,
				false);
	}
}