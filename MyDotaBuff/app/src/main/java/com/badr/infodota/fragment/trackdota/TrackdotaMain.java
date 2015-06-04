package com.badr.infodota.fragment.trackdota;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.activity.ListHolderActivity;
import com.badr.infodota.activity.TrackdotaGameInfoActivity;
import com.badr.infodota.adapter.pager.TrackdotaPagerAdapter;
import com.badr.infodota.api.trackdota.game.GamesResult;
import com.badr.infodota.service.trackdota.TrackdotaService;
import com.badr.infodota.util.Refresher;
import com.badr.infodota.util.retrofit.TaskRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.ui.recently.ActMatchDetail;
import cn.edu.mydotabuff.view.SlidingTabLayout;

/**
 * Created by ABadretdinov
 * 14.04.2015
 * 11:28
 */
public class TrackdotaMain extends Fragment implements RequestListener<GamesResult>,Refresher {
    private SpiceManager spiceManager=new SpiceManager(UncachedSpiceService.class);
    private TrackdotaPagerAdapter adapter;
    private View progressBar;
    private Handler updateHandler=new Handler();
    private Runnable updateTask;
    private static final long DELAY_20_SEC = 20000;
    public static final int SEARCH_MATCH = 322;
    private boolean initialized=false;

    @Override
    public void onStart() {
        if(!spiceManager.isStarted()) {
            spiceManager.start(getActivity());
            if(!initialized){
                onRefresh();
            }
            else {
                startDelayedUpdate();
            }
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if(spiceManager.isStarted()){
            spiceManager.shouldStop();
        }
        cancelDelayedUpdate();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        initialized=false;
        super.onDestroy();
    }

    private void cancelDelayedUpdate() {
        if(updateTask!=null) {
            updateHandler.removeCallbacks(updateTask);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.trackdota,container,false);
        progressBar=view.findViewById(R.id.progressBar);
        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//
//        inflater.inflate(R.menu.frag_recently_menu, menu);
//        final SearchView searchView = (SearchView) menu.findItem(
//                R.id.action_search).getActionView();
//        searchView.setQueryHint("输入比赛ID");
//        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String arg0) {
//                // TODO Auto-generated method stub
////                if (arg0.length() == 10) {
////                    Intent intent = new Intent(activity, ActMatchDetail.class);
////                    intent.putExtra("matchId", arg0);
////                    startActivity(intent);
////                } else {
////                    Toast.makeText(activity, "比赛ID有误，请重新输入~",
////                            Toast.LENGTH_SHORT).show();
////                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String arg0) {
//                // TODO Auto-generated method stub
//                return false;
//            }
//        });
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == SEARCH_MATCH) {
//            Activity activity=getActivity();
//            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//            final EditText textView = new EditText(activity);
//            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            textView.setLayoutParams(lp);
//            textView.setHint(R.string.match_id);
//            textView.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
//            builder.setTitle(R.string.search_match);
//            builder.setView(textView);
//            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            builder.setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    String matchId = textView.getText().toString();
//                    if (!TextUtils.isEmpty(matchId)) {
//                        Intent intent = new Intent(getActivity(), TrackdotaGameInfoActivity.class);
//                        intent.putExtra("id", Long.valueOf(matchId));
//                        startActivity(intent);
//                    }
//                    dialog.dismiss();
//                }
//            });
//            builder.show();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        initPager();
    }

    private void initPager() {
        View root=getView();
        Activity activity=getActivity();
        if(activity!=null&&root!=null) {
            adapter = new TrackdotaPagerAdapter(activity,getChildFragmentManager(),this);

            ViewPager pager = (ViewPager) root.findViewById(R.id.pager);
            pager.setAdapter(adapter);
            pager.setOffscreenPageLimit(3);

            SlidingTabLayout indicator = (SlidingTabLayout) root.findViewById(R.id.indicator);
            indicator.setViewPager(pager);
        }
    }

    @Override
    public void onRefresh(){
        cancelDelayedUpdate();
        progressBar.setVisibility(View.VISIBLE);
        spiceManager.execute(new GamesResultLoadRequest(),this);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        initialized=true;
        progressBar.setVisibility(View.GONE);
        adapter.update(null);
        Toast.makeText(getActivity(), spiceException.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestSuccess(GamesResult gamesResult) {
        initialized=true;
        progressBar.setVisibility(View.GONE);
        adapter.update(gamesResult);
        if(gamesResult!=null&&gamesResult.getApiDowntime()>0){
            Toast.makeText(progressBar.getContext(),R.string.api_is_down,Toast.LENGTH_LONG).show();
        }
        startDelayedUpdate();
    }

    private void startDelayedUpdate() {
        cancelDelayedUpdate();
        updateTask=new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        };
        updateHandler.postDelayed(updateTask,DELAY_20_SEC);
    }

    public class GamesResultLoadRequest extends TaskRequest<GamesResult> {
        private BeanContainer container=BeanContainer.getInstance();
        private TrackdotaService trackdotaService=container.getTrackdotaService();
        public GamesResultLoadRequest() {
            super(GamesResult.class);
        }

        @Override
        public GamesResult loadData() throws Exception {
            return trackdotaService.getGames();
        }
    }

}
