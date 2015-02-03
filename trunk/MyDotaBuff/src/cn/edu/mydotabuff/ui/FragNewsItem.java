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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseFragment;

/**
 * @ClassName: FragNewsItem
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 袁浩 1006401052yh@gmail.com
 * @date 2015-2-3 下午3:06:46
 * 
 */
public class FragNewsItem extends BaseFragment {

	@Override
	protected View initViewAndData(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View contextView = inflater.inflate(R.layout.frag_news_item, container,
				false);
		TextView mTextView = (TextView) contextView.findViewById(R.id.textview);

		// 获取Activity传递过来的参数
		Bundle mBundle = getArguments();
		String title = mBundle.getString("arg");

		mTextView.setText(title);

		return contextView;
	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub

	}

}
