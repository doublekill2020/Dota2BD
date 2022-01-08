
package cn.edu.mydotabuff.ui;

import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.MenuItem;

import cn.edu.mydotabuff.R;
import cn.edu.mydotabuff.base.BaseActivity;


public class ActNewsList extends BaseActivity {


    TabLayout mTabLayout;
    ViewPager mVp;

    CoordinatorLayout cd;

    private static final String[] TITLE = new String[]{"全部", "刀塔新闻", "赛事资讯",
            "版本公告"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init() {
        setContentView(R.layout.act_news_base);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("全部");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(
                getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.vp);
        pager.setAdapter(adapter);

        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mToolbar.setTitle("全部");
                        break;
                    case 1:
                        mToolbar.setTitle("刀塔新闻");
                        break;
                    case 2:
                        mToolbar.setTitle("赛事资讯");
                        break;
                    case 3:
                        mToolbar.setTitle("版本公告");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(pager);
        //mTabLayout.setTabMode(TabLayout.MODE_FIXED);

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
