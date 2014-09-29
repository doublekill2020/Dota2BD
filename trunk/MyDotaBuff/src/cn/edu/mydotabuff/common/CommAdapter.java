package cn.edu.mydotabuff.common;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommAdapter<T> extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	protected final int mItemLayoutId;

	public CommAdapter(Context context, List<T> mDatas, int itemLayoutId) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void resetData(List<T> more) {
		mDatas = more;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final CommViewHolder viewHolder = getViewHolder(position, convertView,
				parent);
		convert(viewHolder, getItem(position));
		return viewHolder.getConvertView();

	}

	public abstract void convert(CommViewHolder helper, T item);

	private CommViewHolder getViewHolder(int position, View convertView,
			ViewGroup parent) {
		return CommViewHolder.get(mContext, convertView, parent, mItemLayoutId,
				position);
	}

}
