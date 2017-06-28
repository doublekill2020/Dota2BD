package cn.edu.mydotabuff.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import cn.edu.mydotabuff.R;

public class SwipeRefreshRecycleView extends LinearLayout {

    static final int VERTICAL = LinearLayoutManager.VERTICAL;

    static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRfl;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener;

    private RecyclerView.OnScrollListener mScrollListener;
    private OnScrollListener mSelfScrollListener;

    private RecyclerView.Adapter mAdapter;
    private RefreshLoadMoreListener mRefreshLoadMoreListner;
    //是否可以加载更多
    private boolean mHasMore = true;
    //是否正在刷新
    private boolean mIsRefresh = false;
    //是否正在加载更多
    private boolean mIsLoadMore = false;

    public SwipeRefreshRecycleView(Context context) {
        super(context);
        init(context);
    }

    @SuppressWarnings("deprecation")
    public SwipeRefreshRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        // 导入布局
        LayoutInflater.from(context).inflate(
                R.layout.swipe_refresh_recycleview, this, true);
        mSwipeRfl = (SwipeRefreshLayout) findViewById(R.id.all_swipe);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSwipeRfl.setColorSchemeResources(R.color.main_color);
        mScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //最后显示的项
                int lastVisibleItem = -1;
                int firstVisibleItem = -1;
                if (mLayoutManager instanceof LinearLayoutManager) {
                    lastVisibleItem = ((LinearLayoutManager) mLayoutManager)
                            .findLastVisibleItemPosition();
                    firstVisibleItem = ((LinearLayoutManager) mLayoutManager)
                            .findFirstVisibleItemPosition();
                } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                    int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager)
                            .findLastCompletelyVisibleItemPositions(null);
                    int[] firstVisibleItemPositions = ((StaggeredGridLayoutManager)
                            mLayoutManager).findFirstVisibleItemPositions(null);
                    int lastIndex = lastVisibleItemPositions.length - 1;
                    lastVisibleItem =
                            lastVisibleItemPositions[lastIndex] > lastVisibleItemPositions[0] ?
                                    lastVisibleItemPositions[lastIndex] :
                                    lastVisibleItemPositions[0];
                    firstVisibleItem = firstVisibleItemPositions[0];
                }
/*
                if (firstVisibleItem == 0)
                    mSwipeRfl.setEnabled(true);
                else
                    mSwipeRfl.setEnabled(false);*/
                int totalItemCount = mAdapter.getItemCount();
                /**
                 * 无论水平还是垂直
                 */
                if (mHasMore && (lastVisibleItem >= totalItemCount - 1)
                        && !mIsLoadMore && !mIsRefresh && dy > 0) {
                    loadMore();
                }
                if (mSelfScrollListener != null) {
                    mSelfScrollListener.onScrolled(recyclerView, dx, dy);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mSelfScrollListener != null) {
                    mSelfScrollListener.onScrollStateChanged(recyclerView, newState);
                }
            }
        };
        /**
         * 下拉至顶部刷新监听
         */
        mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!mIsRefresh && !mIsLoadMore) {
                    refresh();
                }
            }
        };
        mSwipeRfl.setOnRefreshListener(mRefreshListener);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mScrollListener);

        //default
        setPullLoadMoreEnable(false);
        setPullRefreshEnable(true);

    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setPullLoadMoreEnable(boolean enable) {
        this.mHasMore = enable;
    }

    public boolean getPullLoadMoreEnable() {
        return mHasMore;
    }

    public void setPullRefreshEnable(boolean enable) {
        mSwipeRfl.setEnabled(enable);
    }

    public boolean getPullRefreshEnable() {
        return mSwipeRfl.isEnabled();
    }

    public void setLoadMoreListener() {
        mRecyclerView.setOnScrollListener(mScrollListener);
    }

    public void setRefreshLoadMoreListener(RefreshLoadMoreListener listener) {
        mRefreshLoadMoreListner = listener;
    }

    /**
     * 触发刷新
     */
    public void refresh() {
        mIsRefresh = true;
        if (mRefreshLoadMoreListner != null) {
            mSwipeRfl.post(new Runnable() {
                @Override
                public void run() {
                    if (!mSwipeRfl.isRefreshing()) {
                        mSwipeRfl.setRefreshing(true);
                    }
                }
            });
            mRefreshLoadMoreListner.onRefresh();
        }
    }

    /**
     * 触发加载更多
     */
    public void loadMore() {
        mIsLoadMore = true;
        if (mRefreshLoadMoreListner != null) {
            mRefreshLoadMoreListner.onLoadMore();
        }
    }

    /**
     * 刷新完成时调用
     */
    public void setRefreshCompleted() {
        mIsRefresh = false;
        mSwipeRfl.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSwipeRfl.isRefreshing()) {
                    mSwipeRfl.setRefreshing(false);
                }
            }
        }, 500);
    }

    /**
     * 加载更多完毕时调用
     */
    public void setLoadMoreCompleted() {
        mIsLoadMore = false;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            mRecyclerView.setAdapter(adapter);
        }
    }

    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        mRecyclerView.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
    }

    public void setHorizontalFadingEdgeEnabled(boolean horizontalFadingEdgeEnabled) {
        mRecyclerView.setHorizontalFadingEdgeEnabled(horizontalFadingEdgeEnabled);
    }

    public interface RefreshLoadMoreListener {

        void onRefresh();

        void onLoadMore();
    }

    public interface OnScrollListener {

        void onScrolled(RecyclerView recyclerView, int dx, int dy);

        void onScrollStateChanged(RecyclerView recyclerView, int newState);
    }

    public void setOnScrolllistener(OnScrollListener listener) {
        mSelfScrollListener = listener;
    }

    public RecyclerView getRecycleView() {
        return mRecyclerView;
    }
}