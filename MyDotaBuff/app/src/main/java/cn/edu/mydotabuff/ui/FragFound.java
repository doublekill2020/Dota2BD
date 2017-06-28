package cn.edu.mydotabuff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;

public class FragFound extends BaseFragment implements OnClickListener {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_found_base, null);
        view.findViewById(R.id.board).setOnClickListener(this);
        view.findViewById(R.id.news).setOnClickListener(this);
        view.findViewById(R.id.community).setOnClickListener(this);
        view.findViewById(R.id.live).setOnClickListener(this);
        return view;
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
            case R.id.live:
                startActivity(new Intent(getActivity(), ActTrack.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

}
