package cn.edu.mydotabuff.mydetail;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewpagerAdapter extends PagerAdapter {
	private List<View> requirementViewList;
	private int mChildCount = 0;

	public ViewpagerAdapter(List<View> viewList) {
		this.requirementViewList = viewList;
	}

	@Override
	public int getCount() {
		if (requirementViewList != null) {
			return requirementViewList.size();
		}
		return 0;
	}

	public void setNewList(List<View> list) {
		this.requirementViewList = list;
		notifyDataSetChanged();
	}

	@Override
	public void notifyDataSetChanged() {
		mChildCount = getCount();
		super.notifyDataSetChanged();

	}

	@Override
	public int getItemPosition(Object object) {
		if (mChildCount > 0) {
			mChildCount--;
			return POSITION_NONE;// 这样才能刷新viewpaper
		}
		return super.getItemPosition(object);
	}

	@Override
	public Object instantiateItem(final View container, final int position) {
		((ViewGroup) container).addView(requirementViewList.get(position));
		return requirementViewList.get(position);
	}

	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		return view == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(requirementViewList.get(position));
	}
}
