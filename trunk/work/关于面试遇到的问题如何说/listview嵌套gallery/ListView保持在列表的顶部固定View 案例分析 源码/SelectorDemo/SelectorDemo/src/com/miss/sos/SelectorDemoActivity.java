package com.miss.sos;

import java.util.List;
 
import com.miss.sos.widget.SosUniversalAdapter;
import com.miss.sos.widget.SosUniversalListView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Main View
 * @author Miss
 *
 */
public class SelectorDemoActivity extends Activity {
	SosUniversalListView lsComposer;  //Diy ListView
	SectionComposerAdapter adapter;   //  ≈‰∆˜
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_section_demo);
		
		lsComposer = (SosUniversalListView) findViewById(R.id.lsComposer);
		lsComposer.setPinnedHeaderView(LayoutInflater.from(this).inflate(R.layout.item_composer_header, lsComposer, false));
		lsComposer.setAdapter(adapter = new SectionComposerAdapter());
	}
	
	class SectionComposerAdapter extends SosUniversalAdapter {
		List<Pair<String, List<Composer>>> all = Data.getAllData();

		
		public int getCount() {
			int res = 0;
			for (int i = 0; i < all.size(); i++) {
				res += all.get(i).second.size();
			}
			return res;
		}

		
		public Composer getItem(int position) {
			int c = 0;
			for (int i = 0; i < all.size(); i++) {
				if (position >= c && position < c + all.get(i).second.size()) {
					return all.get(i).second.get(position - c);
				}
				c += all.get(i).second.size();
			}
			return null;
		}

		
		public long getItemId(int position) {
			return position;
		}

		
		protected void onNextPageRequested(int page) {
		}

		
		protected void bindSectionHeader(View view, int position, boolean displaySectionHeader) {
			if (displaySectionHeader) {
				view.findViewById(R.id.header).setVisibility(View.VISIBLE);
				TextView lSectionTitle = (TextView) view.findViewById(R.id.header);
				lSectionTitle.setText(getSections()[getSectionForPosition(position)]);
			} else {
				view.findViewById(R.id.header).setVisibility(View.GONE);
			}
		}

		
		public View getAmazingView(int position, View convertView, ViewGroup parent) {
			View res = convertView;
			if (res == null) res = getLayoutInflater().inflate(R.layout.item_composer, null);
			
			TextView lName = (TextView) res.findViewById(R.id.lName);
			TextView lYear = (TextView) res.findViewById(R.id.lYear);
			
			Composer composer = getItem(position);
			lName.setText(composer.name);
			lYear.setText(composer.year);
			
			return res;
		}

		
		public void configurePinnedHeader(View header, int position, int alpha) {
			TextView lSectionHeader = (TextView)header;
			lSectionHeader.setText(getSections()[getSectionForPosition(position)]);
			lSectionHeader.setBackgroundColor(alpha << 24 | (0xbbffbb));
			lSectionHeader.setTextColor(alpha << 24 | (0x000000));
		}

		
		public int getPositionForSection(int section) {
			if (section < 0) section = 0;
			if (section >= all.size()) section = all.size() - 1;
			int c = 0;
			for (int i = 0; i < all.size(); i++) {
				if (section == i) { 
					return c;
				}
				c += all.get(i).second.size();
			}
			return 0;
		}

		public int getSectionForPosition(int position) {
			int c = 0;
			for (int i = 0; i < all.size(); i++) {
				if (position >= c && position < c + all.get(i).second.size()) {
					return i;
				}
				c += all.get(i).second.size();
			}
			return -1;
		}

		public String[] getSections() {
			String[] res = new String[all.size()];
			for (int i = 0; i < all.size(); i++) {
				res[i] = all.get(i).first;
			}
			return res;
		}
		
	}
}