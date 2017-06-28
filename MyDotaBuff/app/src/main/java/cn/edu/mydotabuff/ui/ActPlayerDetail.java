/**
 * @Title: ActPlayerDetail.java
 * @ProjectName MyDotaBuff
 * @Package cn.edu.mydotabuff.ui
 * @author 袁浩 1006401052yh@gmail.com
 * @date 2015-1-29 下午1:54:11
 * @version V1.4
 * Copyright 2013-2015 深圳市点滴互联科技有限公司  版权所有
 */
package cn.edu.mydotabuff.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import cn.edu.mydotabuff.AppManager;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.common.bean.PlayerInfoBean;
import cn.edu.mydotabuff.common.http.OnWebDataGetListener;
import cn.edu.mydotabuff.common.http.WebDataHelper;
import cn.edu.mydotabuff.ui.mydetail.ActUserStatistics;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.view.CircleImageView;
import cn.edu.mydotabuff.view.LoadingDialog;
import cn.edu.mydotabuff.view.TipsToast;
import cn.edu.mydotabuff.view.TipsToast.DialogType;

/**
 * @ClassName: ActPlayerDetail
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 袁浩 1006401052yh@gmail.com
 * @date 2015-1-29 下午1:54:11
 *
 */
public class ActPlayerDetail extends BaseActivity implements
        OnWebDataGetListener {
    private TextView nameView, statusView, timeView, loginView;
    private ImageLoader loader;
    private PlayerInfoBean bean;
    private WebDataHelper helper;
    private LoadingDialog dialog;
    private TextView winNum, loseNum;
    private CircleImageView iconView;

    @Override
    protected void initViewAndData() {
        // TODO Auto-generated method stub
        setContentView(R.layout.act_player_detail_base);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("个人资料");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameView = (TextView) findViewById(R.id.name);
        statusView = (TextView) findViewById(R.id.status);
        timeView = (TextView) findViewById(R.id.regist_time);
        loginView = (TextView) findViewById(R.id.last_login);
        winNum = (TextView) findViewById(R.id.most_win_num);
        loseNum = (TextView) findViewById(R.id.most_lose_num);
        iconView = (CircleImageView) findViewById(R.id.myinfrom_up_img);
        loader = ImageLoader.getInstance();
        dialog = new LoadingDialog(this);
        bean = (PlayerInfoBean) getIntent().getSerializableExtra("data");

        helper = new WebDataHelper(this);
        helper.setDataGetListener(this);
        helper.getWebData(bean);
    }

    @Override
    protected void initEvent() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStartGetData() {
        // TODO Auto-generated method stub
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public <T> void onGetFinished(T data) {
        // TODO Auto-generated method stub
        bean = (PlayerInfoBean) data;
        nameView.setText(bean.getName());
        statusView.setText(Common.getPersonState(bean.getState()));
        timeView.setText(TimeHelper.TimeStamp2Date(bean.getTimecreated(),
                "yyyy-MM-dd HH:mm"));
        loginView.setText(TimeHelper.TimeStamp2Date(bean.getLastlogooff(),
                "yyyy-MM-dd HH:mm"));
        loader.displayImage(bean.getMediumIcon(), iconView);
        winNum.setText(bean.getWinStreak() + "场");
        loseNum.setText(bean.getLoseStreak() + "场");
        dialog.dismiss();
    }

    @Override
    public void onGetFailed(String failMsg) {
        // TODO Auto-generated method stub
        dialog.dismiss();
        TipsToast.showToast(this, failMsg, Toast.LENGTH_SHORT,
                DialogType.LOAD_FAILURE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.act_player_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.more_detail:
                if (bean == null) {
                    TipsToast.showToast(ActPlayerDetail.this, "暂无数据",
                            Toast.LENGTH_SHORT, DialogType.LOAD_FAILURE);
                } else {
                    Intent intent = new Intent(ActPlayerDetail.this,
                            ActUserStatistics.class);
                    intent.putExtra("type", "other");
                    intent.putExtra("data", bean);
                    startActivity(intent);
                }
                break;
            case R.id.friend_list:
                if (bean == null) {
                    TipsToast.showToast(ActPlayerDetail.this, "暂无数据",
                            Toast.LENGTH_SHORT, DialogType.LOAD_FAILURE);
                } else {
                    Intent intent = new Intent(this, ActFriendList.class);
                    intent.putExtra("steamid", bean.getSteamid());
                    startActivity(intent);
                }
                break;
            case R.id.back_to_main:
                AppManager.getAppManager().finishAllActivity(MainActivity.class);
                finish();
                break;
            case R.id.go_to_star:
                try {
                    Uri uri = Uri.parse("market://details?id="
                            + getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    TipsToast.showToast(this, "抱歉，您还未安装相应的应用市场",
                            Toast.LENGTH_SHORT, DialogType.LOAD_FAILURE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
