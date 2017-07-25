package cn.edu.mydotabuff.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.json2.JSONArray;
import org.json2.JSONException;
import org.json2.JSONObject;

import java.util.ArrayList;

import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.DotaApplication.LocalDataType;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.CommViewHolder;
import cn.edu.mydotabuff.common.http.IInfoReceive;
import cn.edu.mydotabuff.model.BoardBean;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.util.TimeHelper;

public class ActBoard extends BaseActivity {

    private static final int FETCH_BOARD = 1;
    private static final int FETCH_FAILED = 2;
    private ArrayList<BoardBean> beans;
    private ListView lv;
    private CommAdapter<BoardBean> adapter;
    private TextView tx;
    private MyHandler myHandler;
    private String defaultPage = "china";
    private int defaultItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        setContentView(R.layout.act_board);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("国服天梯");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView) findViewById(R.id.frag_board_list);
        tx = (TextView) findViewById(R.id.update_time);
        beans = DotaApplication.getApplication().getData(LocalDataType.BOARDS);
        myHandler = new MyHandler();
        if (beans == null) {
            fetchData(FETCH_BOARD);
        } else {
            mToolbar.setTitle("国服天梯");
            lv.setAdapter(adapter = new CommAdapter<BoardBean>(this, beans,
                    R.layout.frag_board_item) {

                @Override
                public void convert(CommViewHolder helper, BoardBean item) {
                    // TODO Auto-generated method stub
                    helper.setText(R.id.name, item.getName());
                    helper.setText(R.id.rank, item.getRank() + "");
                    helper.setText(R.id.solo_mmr, item.getSolo_mmr() + "");
                }
            });
            if (beans.size() > 0) {
                tx.setText("上次更新时间："
                        + TimeHelper.TimeStamp2Date(beans.get(0)
                        .getUpdateTime(), "MM-dd HH:mm"));
            }
        }
    }

    void fetchData(final int type) {
        PersonalRequestImpl request = new PersonalRequestImpl(
                new IInfoReceive() {

                    @Override
                    public void onMsgReceiver(ResponseObj receiveInfo) {
                        // TODO Auto-generated method stub
                        switch (type) {
                            case FETCH_BOARD:
                                ArrayList<BoardBean> beans = new ArrayList<BoardBean>();
                                try {
                                    JSONObject jsonObj = new JSONObject(receiveInfo
                                            .getJsonStr());
                                    String time = jsonObj.getString("time_posted");
                                    JSONArray array = jsonObj
                                            .getJSONArray("leaderboard");
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject obj = array.getJSONObject(i);
                                        int rank = obj.getInt("rank");
                                        String name = "";
                                        if (obj.has("team_tag")) {
                                            name = obj.getString("team_tag") + ".";
                                        }
                                        name += obj.getString("name");
                                        int solo_mmr = obj.getInt("solo_mmr");
                                        beans.add(new BoardBean(time, rank, name,
                                                solo_mmr));
                                    }
                                    Message msg = myHandler.obtainMessage();
                                    msg.arg1 = type;
                                    msg.obj = beans;
                                    myHandler.sendMessage(msg);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Message msg = myHandler.obtainMessage();
                                    msg.arg1 = FETCH_FAILED;
                                    myHandler.sendMessage(msg);
                                }
                                break;
                            default:
                                break;
                        }
                    }

                });
        request.setActivity(this);
        switch (type) {
            case FETCH_BOARD:
                request.getBoard(defaultPage);
                break;
            default:
                break;
        }
    }

    class MyHandler extends Handler {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case FETCH_BOARD:
                    setTitle();
                    ArrayList<BoardBean> beans = (ArrayList<BoardBean>) msg.obj;
                    if (beans != null) {
                        if (defaultPage.equals("china")) {
                            DotaApplication.getApplication().saveData(beans,
                                    LocalDataType.BOARDS);
                        }
                        lv.setAdapter(adapter = new CommAdapter<BoardBean>(
                                ActBoard.this, beans, R.layout.frag_board_item) {

                            @Override
                            public void convert(CommViewHolder helper,
                                                BoardBean item) {
                                // TODO Auto-generated method stub
                                helper.setText(R.id.name, item.getName());
                                helper.setText(R.id.rank, item.getRank() + "");
                                helper.setText(R.id.solo_mmr, item.getSolo_mmr()
                                        + "");
                            }
                        });
                        if (beans.size() > 0) {
                            tx.setText("上次更新时间："
                                    + TimeHelper.TimeStamp2Date(beans.get(0)
                                    .getUpdateTime(), "MM-dd HH:mm"));
                        }
                    }
                    break;
                case FETCH_FAILED:
                    showToast("steam被墙了，你懂得");
                    break;
                default:
                    break;
            }
        }
    }

    private void setTitle() {
        if (defaultPage.equals("china")) {
            mToolbar.setTitle("国服天梯");
        } else if (defaultPage.equals("americas")) {
            mToolbar.setTitle("美服天梯");
        } else if (defaultPage.equals("se_asia")) {
            mToolbar.setTitle("东南亚天梯");
        } else if (defaultPage.equals("europe")) {
            mToolbar.setTitle("欧服天梯");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.frag_board_menu, menu);
        defaultItem = ((MenuItem) menu.findItem(R.id.china)).getItemId();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (defaultItem != item.getItemId()) {
            defaultItem = item.getItemId();
            switch (item.getItemId()) {
                case R.id.china:
                    defaultPage = "china";
                    break;
                case R.id.americas:
                    defaultPage = "americas";
                    break;
                case R.id.se_asia:
                    defaultPage = "se_asia";
                    break;
                case R.id.europe:
                    defaultPage = "europe";
                    break;
                default:
                    break;
            }
            fetchData(FETCH_BOARD);
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
