/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.mydotabuff;

import cn.edu.mydotabuff.view.SamplePagerAdapter;
import cn.edu.mydotabuff.view.SlidingTabLayout;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SlidingTabFragment extends Fragment {

	static final String LOG_TAG = "SlidingTabsBasicFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_dialer_sliding, container,
				false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		mViewPager.setAdapter(new SamplePagerAdapter(getActivity()));

		SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) view
				.findViewById(R.id.sliding_tabs);
		mSlidingTabLayout.setViewPager(mViewPager);
	}
}
