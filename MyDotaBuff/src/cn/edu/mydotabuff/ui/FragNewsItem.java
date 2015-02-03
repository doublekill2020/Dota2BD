/**   
 * @Title: FragNewsItem.java
 * @ProjectName MyDotaBuff 
 * @Package cn.edu.mydotabuff.ui 
 * @author 袁浩 1006401052yh@gmail.com
 * @date 2015-2-3 下午3:06:46 
 * @version V1.4  
 * Copyright 2013-2015 深圳市点滴互联科技有限公司  版权所有
 */
package cn.edu.mydotabuff.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.base.BaseFragment;
import cn.edu.mydotabuff.common.CommAdapter;
import cn.edu.mydotabuff.common.CommViewHolder;
import cn.edu.mydotabuff.common.http.IInfoReceive;
import cn.edu.mydotabuff.util.Debug;
import cn.edu.mydotabuff.util.PersonalRequestImpl;
import cn.edu.mydotabuff.view.TipsToast.DialogType;
import cn.edu.mydotabuff.view.XListView;
import cn.edu.mydotabuff.view.XListView.IXListViewListener;

import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @ClassName: FragNewsItem
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 袁浩 1006401052yh@gmail.com
 * @date 2015-2-3 下午3:06:46
 * 
 */
public class FragNewsItem extends BaseFragment {

	private int index;
	private XListView list;
	private CommAdapter<NewsBean> adapter;
	private ScaleInAnimationAdapter animationAdapter;
	private Activity act;
	private ArrayList<NewsBean> beans = new ArrayList<NewsBean>();
	private View view;
	private int currentPage = 0;

	@Override
	protected View initViewAndData(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.frag_news_item_base, container,
					false);
			index = getArguments().getInt("index");
			act = getActivity();
			list = (XListView) view.findViewById(R.id.list);
			list.setPullLoadEnable(true);
			list.setPullRefreshEnable(true);
			fetchData(index, currentPage);
		}
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

			list.stopLoadMore();
			list.stopRefresh();
			switch (msg.what) {
			case BaseActivity.OK:
				if (adapter == null) {
					adapter = new CommAdapter<NewsBean>(act, beans,
							R.layout.frag_news_item_list_item) {

						@Override
						public void convert(CommViewHolder helper, NewsBean item) {
							// TODO Auto-generated method stub
							helper.setText(R.id.title, item.getTitle());
							helper.setText(R.id.content, item.getContent());
							helper.setText(R.id.time, item.getTime());
							ImageView icon = helper.getView(R.id.pic);
							ImageLoader.getInstance().displayImage(
									item.getPic(), icon);
						}
					};
					animationAdapter = new ScaleInAnimationAdapter(adapter);
					animationAdapter.setAbsListView(list);
					list.setAdapter(animationAdapter);
				} else {
					adapter.notifyDataSetChanged();
				}
				break;
			case BaseActivity.FAILED:
				((BaseActivity) act).showTip("网络超时~~", DialogType.LOAD_FAILURE);
				break;
			case BaseActivity.JSON_ERROR:
				break;
			default:
				break;
			}
		};
	};

	private void fetchData(final int type, final int page) {
		PersonalRequestImpl request = new PersonalRequestImpl(
				new IInfoReceive() {

					@Override
					public void onMsgReceiver(ResponseObj receiveInfo) {
						// TODO Auto-generated method stub
						Message msg = h.obtainMessage();
						try {
							JSONObject obj = new JSONObject(
									receiveInfo.getJsonStr());
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
							} else {
								msg.what = BaseActivity.FAILED;
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
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
	protected void initEvent() {
		// TODO Auto-generated method stub
		list.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				beans.clear();
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
				currentPage = 0;
				fetchData(index, currentPage);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				currentPage++;
				fetchData(index, currentPage);
			}
		});
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				NewsBean bean = beans.get(position - 1);
				if (bean.getIsVideo().equals("true")) {

				} else {
					Intent i = new Intent();
					i.setClass(act, cls);
					i.putExtra("url", bean.getUrl());
					act.startActivity(i);
				}
			}
		});
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		if (null != view) {
			((ViewGroup) view.getParent()).removeView(view);
		}
	}
}

class NewsBean {
	private String pic;
	private String title;
	private String content;
	private String time;
	private String url;
	private String isVideo;

	public String getIsVideo() {
		return isVideo;
	}

	public void setIsVideo(String isVideo) {
		this.isVideo = isVideo;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public NewsBean(String pic, String title, String content, String time,
			String url, String isVideo) {
		super();
		this.pic = pic;
		this.title = title;
		this.content = content;
		this.time = time;
		this.url = url;
		this.isVideo = isVideo;
	}

}