package cn.edu.mydotabuff.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.api.ApiManager;
import cn.edu.mydotabuff.api.NewsHttpRespone;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.base.BaseListAdapter;
import cn.edu.mydotabuff.base.BaseListHolder;
import cn.edu.mydotabuff.model.NewsBean;
import cn.edu.mydotabuff.view.SwipeRefreshRecycleView;
import cn.edu.mydotabuff.view.TipsToast.DialogType;
import rx.functions.Action1;


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
                rvContent.setPullLoadMoreEnable(true);
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
                    if (tag != null && tag instanceof NewsBean) {
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
                holder.setTag(R.id.cardview, item);
                holder.setOnClickListener(R.id.cardview, onClickListener);
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


    Action1<NewsHttpRespone> responeAction = new Action1<NewsHttpRespone>() {
        @Override
        public void call(NewsHttpRespone respone) {
            dismissLoadingDialog();
            if (respone != null && respone.getData() != null && !respone.getData().isEmpty()) {
                int totalPage = respone.getTotalPages();
                if (currentPage >= totalPage - 1) {
                    rvContent.setPullLoadMoreEnable(false);
                } else {
                    rvContent.setPullLoadMoreEnable(true);
                }
                if (currentPage == 0) {
                    beans.clear();
                }
                beans.addAll(respone.getData());
                adapter.notifyDataSetChanged();
            } else {
                ((BaseActivity) act).showTip("新闻加载失败，请稍候再试", DialogType.LOAD_FAILURE);
            }
        }
    };
    Action1<Throwable> throwableAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            dismissLoadingDialog();
            throwable.printStackTrace();
            ((BaseActivity) act).showTip("新闻加载失败，请稍候再试", DialogType.LOAD_FAILURE);
        }
    };

    private void fetchData(final int type, final int page) {

        switch (type) {
            case 0:
                showLoadingDialog();
                ApiManager.getManager().requestHotNews(page,responeAction,throwableAction);
                break;
            case 1:
                showLoadingDialog();
                ApiManager.getManager().requestGovNews(page,responeAction,throwableAction);
                break;
            case 2:
                showLoadingDialog();
                ApiManager.getManager().requestMatchNews(page,responeAction,throwableAction);
                break;
            case 3:
                showLoadingDialog();
                ApiManager.getManager().requestVerNews(page,responeAction,throwableAction);
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }
}
