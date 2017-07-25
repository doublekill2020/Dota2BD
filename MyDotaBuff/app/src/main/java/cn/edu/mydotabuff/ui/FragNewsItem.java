package cn.edu.mydotabuff.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json2.JSONArray;
import org.json2.JSONException;
import org.json2.JSONObject;

import java.util.ArrayList;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.base.BaseListAdapter;
import cn.edu.mydotabuff.base.BaseListHolder;
import cn.edu.mydotabuff.common.http.IInfoReceive;
import cn.edu.mydotabuff.model.NewsBean;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.view.SwipeRefreshRecycleView;
import cn.edu.mydotabuff.view.TipsToast.DialogType;


public class FragNewsItem extends BaseFragment {

    private int index;
    private SwipeRefreshRecycleView rvContent;
    private BaseListAdapter<NewsBean> adapter;
    private Activity act;
    private ArrayList<NewsBean> beans = new ArrayList<NewsBean>();
    private View view;
    private int currentPage = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_news_item_base, container,
                false);
        index = getArguments().getInt("index");
        act = getActivity();
        rvContent = (SwipeRefreshRecycleView) view.findViewById(R.id.rvContent);
        rvContent.setRefreshLoadMoreListener(new SwipeRefreshRecycleView.RefreshLoadMoreListener() {
            @Override
            public void onRefresh() {
                currentPage = 0;
                fetchData(index, currentPage);
            }

            @Override
            public void onLoadMore() {
                currentPage++;
                fetchData(index, currentPage);
            }
        });
        adapter = new BaseListAdapter<NewsBean>(beans, R.layout.frag_news_item_list_item) {
            private View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Object tag = view.getTag();
                    if (tag!=null && tag instanceof NewsBean){
                        NewsBean bean = (NewsBean) tag;
                        Intent i = new Intent();
                        i.setClass(act, ActNews.class);
                        i.putExtra("url", bean.getUrl());
                        act.startActivity(i);

                    }
                }
            };
            @Override
            public void getView(BaseListHolder holder, NewsBean item, int pos) {
                holder.setImageURI(R.id.pic, item.getPic());
                holder.setText(R.id.title, item.getTitle());
                holder.setText(R.id.content, item.getContent());
                holder.setText(R.id.time, item.getTime());
                holder.setTag(R.id.cardview,item);
                holder.setOnClickListener(R.id.cardview,onClickListener);
            }
        };
        rvContent.setAdapter(adapter);
        fetchData(index, currentPage);


        return view;
    }

    public static FragNewsItem newInstance(int position) {
        final FragNewsItem f = new FragNewsItem();

        final Bundle args = new Bundle();
        args.putInt("index", position);
        f.setArguments(args);
        return f;
    }

    private Handler h = new Handler() {
        public void handleMessage(android.os.Message msg) {

            rvContent.setRefreshCompleted();
            rvContent.setLoadMoreCompleted();
            switch (msg.what) {
                case BaseActivity.OK:
                    if (currentPage == 0) {
                        beans.clear();
                    }
                    beans.addAll((ArrayList<NewsBean>) msg.obj);

                    adapter.notifyDataSetChanged();

                    break;
                case BaseActivity.FAILED:
                    ((BaseActivity) act).showTip("网络超时~~", DialogType.LOAD_FAILURE);
                    break;
                case BaseActivity.JSON_ERROR:
                    break;
                default:
                    break;
            }
        }

        ;
    };

    private void fetchData(final int type, final int page) {
        PersonalRequestImpl request = new PersonalRequestImpl(
                new IInfoReceive() {

                    @Override
                    public void onMsgReceiver(ResponseObj receiveInfo) {
                        Message msg = h.obtainMessage();
                        try {
                            JSONObject obj = new JSONObject(
                                    receiveInfo.getJsonStr());
                            ArrayList<NewsBean> beans = new ArrayList<NewsBean>();
                            if (receiveInfo.getMsgType() == ReceiveMsgType.OK) {
                                msg.what = BaseActivity.OK;
                                JSONArray array = obj.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject info = array.getJSONObject(i);
                                    NewsBean bean = new NewsBean(
                                            info.getString("pic"),
                                            info.getString("title"),
                                            info.getString("desc"),
                                            info.getString("date"),
                                            info.getString("url"),
                                            info.getString("isVideo"));
                                    beans.add(bean);
                                }
                                msg.obj = beans;
                            } else {
                                msg.what = BaseActivity.FAILED;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            msg.what = BaseActivity.JSON_ERROR;
                        }
                        h.sendMessage(msg);
                    }
                });
        request.setActivity(getActivity());
        request.setIsCancelAble(false);
        request.getDota2News(type, page);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
}
