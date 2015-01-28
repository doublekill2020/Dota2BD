package cn.edu.mydotabuff.view;

import cn.edu.mydotabuff.R;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


@SuppressWarnings("FieldCanBeLocal")
public class SamplePagerAdapter extends PagerAdapter {

    private final String [] TITLES = {"SPEED DIAL", "RECENTS", "CONTACTS"};
    private final int FRAGMENT_COUNT = 3;
    private Context context;

    public SamplePagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(
            R.layout.frag_dialer_sample_page,
            container, false);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}