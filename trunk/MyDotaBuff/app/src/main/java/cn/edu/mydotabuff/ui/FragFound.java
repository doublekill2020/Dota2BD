package cn.edu.mydotabuff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;

public class FragFound extends BaseFragment implements OnClickListener {

	private View view;

	@Override
	protected View initViewAndData(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.frag_found_base, null);
		return view;
	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub
		view.findViewById(R.id.board).setOnClickListener(this);
		view.findViewById(R.id.news).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.board:
			startActivity(new Intent(getActivity(), ActBoard.class));
			break;
		case R.id.news:
			startActivity(new Intent(getActivity(), ActNewsList.class));
			break;
		default:
			break;
		}
	}

}
