/**   
 * @Title: ActNews.java
 * @ProjectName MyDotaBuff 
 * @Package cn.edu.mydotabuff.ui 
 * @author 袁浩 1006401052yh@gmail.com
 * @date 2015-2-3 下午2:57:30 
 * @version V1.4  
 * Copyright 2013-2015 深圳市点滴互联科技有限公司  版权所有
 */
package cn.edu.mydotabuff.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;
import cn.edu.mydotabuff.view.PagerSlidingTabStrip;

/**
 * @ClassName: ActNews
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 袁浩 1006401052yh@gmail.com
 * @date 2015-2-3 下午2:57:30
 * 
 */
public class ActNewsList extends BaseActivity {
	private static final String[] TITLE = new String[] { "全部", "刀塔新闻", "赛事资讯",
			"版本公告" };
	private PagerSlidingTabStrip indicator;
	private Toolbar toolbar;

	@Override
	protected void initViewAndData() {
		// TODO Auto-generated method stub
		setContentView(R.layout.act_news_base);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("全部");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(
				getSupportFragmentManager());
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		indicator = (PagerSlidingTabStrip) findViewById(R.id.indicator);
		indicator.setViewPager(pager);

	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					toolbar.setTitle("全部");
					break;
				case 1:
					toolbar.setTitle("刀塔新闻");
					break;
				case 2:
					toolbar.setTitle("赛事资讯");
					break;
				case 3:
					toolbar.setTitle("版本公告");
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	class TabPageIndicatorAdapter extends FragmentPagerAdapter {
		public TabPageIndicatorAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return FragNewsItem.newInstance(position);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLE[position % TITLE.length];
		}

		@Override
		public int getCount() {
			return TITLE.length;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
