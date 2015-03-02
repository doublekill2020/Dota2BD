package com.mylist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	/** Called when the activity is first created. */

	private MyAdapter adapter = null;
	private ArrayList<Map<String, Object>> array;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		array = getData();
		adapter = new MyAdapter();
		setListAdapter(adapter);
		ListViewInterceptor tlv = (ListViewInterceptor) getListView();

		tlv.setDropListener(onDrop);

		tlv.getAdapter();
	}

	private ListViewInterceptor.DropListener onDrop = new ListViewInterceptor.DropListener() {
		@Override
		public void drop(int from, int to) {
			Map item = adapter.getItem(from);

			adapter.remove(item);
			adapter.insert(item, to);

		}
	};

	class MyAdapter extends ArrayAdapter<Map<String, Object>> {

		MyAdapter() {
			super(MainActivity.this, R.layout.mylist, array);
		}

		public ArrayList<Map<String, Object>> getList() {
			return array;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.mylist, parent, false);
			}
			TextView label = (TextView) row.findViewById(R.id.title);
			label.setText(array.get(position).get("title").toString());
			TextView info = (TextView) row.findViewById(R.id.info);
			info.setText(array.get(position).get("info").toString());
			ImageView imageView = (ImageView) row.findViewById(R.id.img);
			imageView.setImageResource(Integer.valueOf(array.get(position)
					.get("img").toString()));
			return (row);
		}
	}

	private ArrayList<Map<String, Object>> getData() {
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "题目");
		map.put("info", "内容");
		map.put("img", R.drawable.fan);

		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "题目");
		map.put("info", "内容");
		map.put("img", R.drawable.xue);

		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "题目");
		map.put("info", "内容");
		map.put("img", R.drawable.shi);

		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "题目");
		map.put("info", "内容");
		map.put("img", R.drawable.tie);

		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "题目");
		map.put("info", "内容");
		map.put("img", R.drawable.ai);

		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "题目");
		map.put("info", "内容");
		map.put("img", R.drawable.bei);
		list.add(map);
		return list;
	}
}