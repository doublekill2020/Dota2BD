package cn.edu.mydotabuff.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.CommViewHolder;
import cn.edu.mydotabuff.common.Common;
import cn.edu.mydotabuff.common.http.IInfoReceive;
import cn.edu.mydotabuff.model.PlayerInfoBean;
import cn.edu.mydotabuff.util.Debug;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.util.TimeHelper;
import cn.edu.mydotabuff.view.CircleImageView;
import cn.edu.mydotabuff.view.TipsToast;
import cn.edu.mydotabuff.view.TipsToast.DialogType;
import cn.edu.mydotabuff.view.XListView;
import cn.edu.mydotabuff.view.XListView.IXListViewListener;

public class ActFriendList extends BaseActivity {

    private String steamid = "";
    private StringBuilder steamids;
    private XListView list;
    private static final int GET_FRIEND_LIST = 1;
    private static final int GET_USERS_INFO = 2;
    private ArrayList<PlayerInfoBean> infoBeans = new ArrayList<PlayerInfoBean>();
    private CommAdapter<PlayerInfoBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        setContentView(R.layout.act_friend_list_base);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("好友列表");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        steamid = getIntent().getStringExtra("steamid");
        list = (XListView) findViewById(R.id.list);
        list.setPullLoadEnable(false);
        list.setPullRefreshEnable(true);
        steamids = new StringBuilder();
        fetchData(GET_FRIEND_LIST);
        list.setXListViewListener(new IXListViewListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub

                infoBeans.clear();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                if (steamids.length() > 0) {
                    fetchData(GET_USERS_INFO);
                } else {
                    fetchData(GET_FRIEND_LIST);
                }
            }

            @Override
            public void onLoadMore() {
                // TODO Auto-generated method stub

            }
        });
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                PlayerInfoBean bean = infoBeans.get(position - 1);
                if (bean.getSteamid().equals("76561202255233023")) {
                    TipsToast.showToast(ActFriendList.this, "该玩家未开启比赛数据共享！",
                            Toast.LENGTH_SHORT, DialogType.LOAD_FAILURE);
                } else {
                    //TODO domax网页发生变化 暂不可用
                    //					Intent i = new Intent();
                    //					i.setClass(ActFriendList.this, ActPlayerDetail.class);
                    //					i.putExtra("data", infoBeans.get(position - 1));
                    //					startActivity(i);
                }
            }
        });
    }

    private Handler h = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case BaseActivity.OK:
                    if (msg.arg1 == GET_FRIEND_LIST) {
                        fetchData(GET_USERS_INFO);
                    } else if (msg.arg1 == GET_USERS_INFO) {
                        list.stopRefresh();
                        adapter = new CommAdapter<PlayerInfoBean>(
                                ActFriendList.this, infoBeans,
                                R.layout.act_friend_list_item) {

                            @Override
                            public void convert(CommViewHolder helper,
                                                PlayerInfoBean item) {
                                // TODO Auto-generated method stub
                                helper.setText(R.id.name, item.getName());
                                helper.setText(R.id.last_login, String.format(
                                        getString(R.string.last_login_time),
                                        TimeHelper.TimeStamp2Date(
                                                item.getLastlogooff(),
                                                "yyyy-MM-dd HH:mm:ss")));
                                helper.setText(R.id.status,
                                        Common.getPersonState(item.getState()));
                                if (item.getState() == 1) {
                                    helper.setBackgroundColor(
                                            R.id.status,
                                            getResources().getColor(
                                                    R.color.my_green));
                                } else {
                                    helper.setBackgroundColor(
                                            R.id.status,
                                            getResources().getColor(
                                                    R.color.my_orange));
                                }
                                CircleImageView icon = helper.getView(R.id.icon);
                                Glide.with(ActFriendList.this)
                                        .load(item.getMediumIcon())
                                        .into(icon);
                            }
                        };
                        list.setAdapter(adapter);
                    }
                    break;
                case BaseActivity.FAILED:
                    showTip("网络超时，下拉重试~~", DialogType.LOAD_FAILURE);
                    break;
                case BaseActivity.JSON_ERROR:
                    showTip("网络超时，下拉重试~~~", DialogType.LOAD_FAILURE);
                    break;
                default:
                    break;
            }
        }

        ;
    };

    private void fetchData(final int type) {
        PersonalRequestImpl request = new PersonalRequestImpl(
                new IInfoReceive() {

                    @Override
                    public void onMsgReceiver(ResponseObj receiveInfo) {
                        // TODO Auto-generated method stub
                        Message msg = h.obtainMessage();
                        try {
                            JSONObject obj = new JSONObject(receiveInfo
                                    .getJsonStr());
                            msg.what = BaseActivity.OK;
                            switch (type) {
                                case GET_FRIEND_LIST:
                                    if (obj.has("friendslist")) {
                                        JSONArray array = obj.getJSONObject(
                                                "friendslist").getJSONArray(
                                                "friends");
                                        for (int i = 0; i < array.length(); i++) {
                                            steamids.append(array.getJSONObject(i)
                                                    .getString("steamid") + ",");
                                        }
                                    } else {
                                        msg.what = BaseActivity.FAILED;
                                    }
                                    break;
                                case GET_USERS_INFO:
                                    infoBeans = new ArrayList<PlayerInfoBean>();
                                    try {
                                        JSONArray array2 = new JSONObject(
                                                receiveInfo.getJsonStr())
                                                .getJSONObject("response")
                                                .getJSONArray("players");
                                        if (array2.length() > 0) {
                                            for (int i = 0; i < array2.length(); i++) {
                                                JSONObject obj2 = array2
                                                        .getJSONObject(i);
                                                PlayerInfoBean bean = new PlayerInfoBean();
                                                bean.setCommunityState(obj2
                                                        .getInt("communityvisibilitystate"));
                                                bean.setLastlogooff(obj2
                                                        .getString("lastlogoff"));
                                                if (bean.getLastlogooff() == null) {
                                                    bean.setLastlogooff("1417140906");
                                                }
                                                bean.setMediumIcon(obj2
                                                        .getString("avatarmedium"));
                                                bean.setName(obj2
                                                        .getString("personaname"));
                                                bean.setState(obj2
                                                        .getInt("personastate"));
                                                if (obj2.has("timecreated")) {
                                                    bean.setTimecreated(obj2
                                                            .getString("timecreated"));
                                                } else {
                                                    bean.setTimecreated("1417140906");
                                                }

                                                bean.setSteamid(obj2
                                                        .getString("steamid"));
                                                // 如果是电脑
                                                if (bean.getSteamid().equals(
                                                        "76561197960265728")) {
                                                    bean.setMediumIcon("http://media.steampowered" +
                                                            ".com/steamcommunity/public/images/avatars/fe/fef49e7fa7e1997310d705b2a6158ff8dc1cdfeb_medium.jpg");
                                                    bean.setName("电脑");
                                                }
                                                infoBeans.add(bean);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        msg.what = BaseActivity.JSON_ERROR;
                                    }
                                    break;
                                default:
                                    break;
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            msg.what = BaseActivity.JSON_ERROR;
                            Debug.d("hao", e.getMessage());
                        }
                        msg.arg1 = type;
                        h.sendMessage(msg);
                    }
                });
        request.setActivity(this);
        switch (type) {
            case GET_FRIEND_LIST:
                request.getFriendList(steamid);
                break;
            case GET_USERS_INFO:
                request.getPlayerDetail(steamids.toString(), true);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
