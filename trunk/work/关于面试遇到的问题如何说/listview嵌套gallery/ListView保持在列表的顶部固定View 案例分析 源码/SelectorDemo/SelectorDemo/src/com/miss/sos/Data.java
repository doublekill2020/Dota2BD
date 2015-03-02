package com.miss.sos;

import android.os.*;
import android.util.*;

import java.util.*;

public class Data {
	public static final String TAG = Data.class.getSimpleName();
	
	public static List<Pair<String, List<Composer>>> getAllData() {
		List<Pair<String, List<Composer>>> res = new ArrayList<Pair<String, List<Composer>>>();
		
		for (int i = 0; i < 4; i++) {
			res.add(getOneSection(i));
		}
		
		return res;
	}
	
	public static List<Composer> getFlattenedData() {
		 List<Composer> res = new ArrayList<Composer>();
		 
		 for (int i = 0; i < 4; i++) {
			 res.addAll(getOneSection(i).second);
		 }
		 
		 return res;
	}
	
	public static Pair<Boolean, List<Composer>> getRows(int page) {
		List<Composer> flattenedData = getFlattenedData();
		if (page == 1) {
			return new Pair<Boolean, List<Composer>>(true, flattenedData.subList(0, 5));
		} else {
			SystemClock.sleep(2000); // simulate loading
			return new Pair<Boolean, List<Composer>>(page * 5 < flattenedData.size(), flattenedData.subList((page - 1) * 5, Math.min(page * 5, flattenedData.size())));
		}
	}
	
	public static Pair<String, List<Composer>> getOneSection(int index) {
		String[] titles = {"少女", "少妇", "老女人", "老干妈"};
		Composer[][] composerss = {
			{
				new Composer("Leen", "13458886553"),
				new Composer("Gop Prez", "13458886553"),
				new Composer("Dy lala", "13458886553"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549")
			},
			{
				new Composer("Sebastian Bach", "13458886553"),
				new Composer("Gen Handel", "13458886553"),
				new Composer("Antonio Vivaldi", "13458886553"),
				new Composer("George Telemann", "13458886553"),
			},
			{
				new Composer("Franz Josepuh", "13458886553"),
				new Composer("Wolfgang Mozart", "13458886553"),
				new Composer("Barbara Fd", "13458886553"),
				new Composer("Frederick Len", "13458886553"),
				new Composer("John Stanley", "13458886553"),
				new Composer("Luise Adelgunda", "13458886553"),
				new Composer("Johann", "13458886553"),
				new Composer("Carl Philipp", "13458886558"),
				new Composer("Christoph", "13458886553"),
				new Composer("Gottfried", "13458886553"),
			},
			{
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549"),
				new Composer("Ludwig ebn", "13458886553"),
				new Composer("Fernando S", "13458886553"),
				new Composer("Johann Nicolas", "13458886549")
				
			},
		};
		return new Pair<String, List<Composer>>(titles[index], Arrays.asList(composerss[index]));
	}
}
