package com.badr.infodota.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import cn.edu.mydotabuff.R;


/**
 * Created by ABadretdinov
 * 29.12.2014
 * 16:04
 */
public abstract class ListFragment extends Fragment {

    final private AdapterView.OnItemClickListener mOnClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            onListItemClick((ListView) parent, v, position, id);
        }
    };
    final private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            ListFragment.this.onRefresh();
        }
    };
    protected View mProgressBar;
    protected SwipeRefreshLayout mListContainer;
    protected int layoutId = R.layout.list_content;
    int INTERNAL_EMPTY_ID = 0x00ff0001;
    private ListAdapter mAdapter;
    private ListView mList;

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public ListView getListView() {
        ensureList();
        return mList;
    }

    private void ensureList() {
        if (mList != null) {
            return;
        }
        View root = getView();
        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        if (root instanceof ListView) {
            mList = (ListView) root;
        } else {
            mList = (ListView) root.findViewById(android.R.id.list);
            mList.setOnItemClickListener(mOnClickListener);
            if (mAdapter != null) {
                ListAdapter adapter = mAdapter;
                mAdapter = null;
                setListAdapter(adapter);
            }
        }
        mList.setOnItemClickListener(mOnClickListener);
        if (mAdapter != null) {
            ListAdapter adapter = mAdapter;
            mAdapter = null;
            setListAdapter(adapter);
        }
    }

    public void setSelection(int position) {
        ensureList();
        mList.setSelection(position);
    }

    public ListAdapter getListAdapter() {
        return mAdapter;
    }

    public void setListAdapter(ListAdapter adapter) {
        mAdapter = adapter;
        if (mList != null) {
            mList.setAdapter(adapter);
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(layoutId, container, false);
        (root.findViewById(R.id.internalEmpty)).setId(INTERNAL_EMPTY_ID);
        mList = (ListView) root.findViewById(android.R.id.list);

        mList.setOnItemClickListener(mOnClickListener);

        mListContainer = (SwipeRefreshLayout) root.findViewById(R.id.listContainer);
        initListContainer();

        mProgressBar = root.findViewById(R.id.progressBar);

        if (mAdapter != null) {
            ListAdapter adapter = mAdapter;
            mAdapter = null;
            setListAdapter(adapter);
        }
        return root;
    }

    protected void initListContainer() {
        mListContainer.setOnRefreshListener(mOnRefreshListener);
        mListContainer.setColorSchemeResources(R.color.primary);
    }

    public abstract void onRefresh();

    public void setRefreshing(boolean refreshing) {
        if (!refreshing) {
            mProgressBar.setVisibility(View.GONE);
        }
        mListContainer.setRefreshing(refreshing);
    }

    /**
     * Attach to list view once the view hierarchy has been created.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureList();
    }

}
