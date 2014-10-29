package cn.edu.mydotabuff.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.mydotabuff.view.RoundAngleImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class CommViewHolder {
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private ImageLoader loader;

	private CommViewHolder(Context context, ViewGroup parent, int layoutId,
			int position) {
		loader = ImageLoader.getInstance();
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static CommViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new CommViewHolder(context, parent, layoutId, position);
		}
		return (CommViewHolder) convertView.getTag();
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public CommViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public CommViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	public CommViewHolder setImageResource(ImageView imageView, int drawableId) {
		// TODO Auto-generated method stub
		imageView.setImageResource(drawableId);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public CommViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	public int getPosition() {
		return mPosition;
	}

	public CommViewHolder setTextColor(int viewId, int color) {
		// TODO Auto-generated method stub
		TextView view = getView(viewId);
		view.setTextColor(color);
		return this;
	}

	public CommViewHolder setBackgroundColor(int viewId, int color) {
		// TODO Auto-generated method stub
		TextView view = getView(viewId);
		view.setBackgroundColor(color);
		return this;
	}

	//  for RoundAngleImageView
	public CommViewHolder setImageFromWeb(int viewId, String url) {
		RoundAngleImageView view = getView(viewId);
		loader.displayImage(url, view);
		return this;
	}
}
