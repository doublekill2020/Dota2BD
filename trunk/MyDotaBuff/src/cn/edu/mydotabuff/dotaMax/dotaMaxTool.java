package cn.edu.mydotabuff.dotaMax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.mydotabuff.bean.BestRecord;
import cn.edu.mydotabuff.bean.HerosSatistics;
import cn.edu.mydotabuff.bean.MacthStatistics;

public class dotaMaxTool {

	private final static String MAIN_URI = "http://www.dotamax.com";
	private final static String HERO_SATISTICS_Uri = "http://dotamax.com/player/hero/";
	private final static String ALL_DATA = "http://dotamax.com/player/detail/";

	private static String WINING_STREAK;
	private static String FILING_STREAK;

	/**
	 * 得到所有英雄统计
	 * 
	 * @param Pid
	 *            帐号id
	 * @param timeOut
	 *            延迟时间 单位ms
	 * @return
	 */
	public static List<HerosSatistics> getHerosSatistics(String Pid, int timeOut) {

		try {
			HerosSatistics heroSatisticsBeans;
			List<HerosSatistics> heroSatisticsList = new ArrayList<HerosSatistics>();
			Document doc = Jsoup.connect(HERO_SATISTICS_Uri + Pid)
					.timeout(timeOut).get();
			Elements trs = doc.select("tbody").select("tr");
			for (int i = 0; i < trs.size(); i++) {
				heroSatisticsBeans = new HerosSatistics();
				Elements tds = trs.get(i).select("td");
				heroSatisticsBeans.setThisHeroData(tds.get(0)
						.getElementsByTag("a").first().attr("href").toString());
				for (int j = 0; j < tds.size(); j++) {
					String text = tds.get(j).text();
					switch (j) {
					case 0:
						heroSatisticsBeans.setHeroName(text);
						break;
					case 1:
						heroSatisticsBeans.setUseTimes(Integer.valueOf(text));
						break;
					case 2:
						heroSatisticsBeans.setWinning(Double.valueOf((text
								.split("%"))[0]));
						break;
					case 3:

						/*
						 * 原始数据 2.71 (10.9 / 9.4 / 14.4) 替换所有空格, ( , ) 为 /
						 * 以"/"分割 得到 kad 以及 K, A , D
						 */

						String replaceString = text.replace(" ", "")
								.replaceAll("[\\s()]", "/");
						String[] replaceStrings = replaceString.split("/");
						heroSatisticsBeans.setKDA(Double
								.valueOf(replaceStrings[0]));
						heroSatisticsBeans.setKill(Double
								.valueOf(replaceStrings[1]));
						heroSatisticsBeans.setDeath(Double
								.valueOf(replaceStrings[2]));
						heroSatisticsBeans.setAssists(Double
								.valueOf(replaceStrings[3]));
						break;
					case 4:
						heroSatisticsBeans.setAllKAD(Double.valueOf(text));
						break;
					case 5:
						heroSatisticsBeans.setGold_PerMin(Double.valueOf(text));
						break;
					case 6:
						heroSatisticsBeans.setXp_PerMin(Double.valueOf(text));
						break;

					default:
						break;
					}

				}
				heroSatisticsList.add(heroSatisticsBeans);

			}

			return heroSatisticsList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<HerosSatistics>();
	}

	/**
	 * 得到最高记录
	 * 
	 * @param Pid
	 * @param timeOut
	 * @return
	 */
	public static List<BestRecord> getBestRecord(String Pid, int timeOut) {
		try {
			BestRecord bestRecordBeans;
			List<BestRecord> bestRecodList = new ArrayList<BestRecord>();
			Document doc = Jsoup.connect(ALL_DATA + Pid).timeout(timeOut).get();

			Element bestRecordDiv = doc.select("div.flat-grey-box").get(2);
			Elements trs = bestRecordDiv.select("tbody").select("tr");
			for (int i = 0; i < trs.size(); i++) {
				bestRecordBeans = new BestRecord();
				Elements tds = trs.get(i).select("td");
				for (int j = 0; j < tds.size(); j++) {
					switch (j) {
					case 0:
						bestRecordBeans.setRecordType(tds.get(j).text());
						break;
					case 1:
						bestRecordBeans.setMmatchID(tds.get(j).text());
						break;
					case 2:
						bestRecordBeans.setResult(tds.get(j).text());
						break;
					case 3:
						bestRecordBeans.setHeroName(tds.get(j).text());
						break;
					case 4:
						bestRecordBeans.setRecordNum(tds.get(j).text());
						break;
					default:
						break;
					}
					bestRecodList.add(bestRecordBeans);

				}
				return bestRecodList;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 连胜/连败
	 * 
	 * @param Pid
	 * @param timeOut
	 */
	public static void getBestStreak(String Pid, int timeOut) {

		try {
			Document doc = Jsoup.connect(ALL_DATA + Pid).timeout(timeOut).get();
			Elements trs = doc
					.select("div.container.xuning-box")
					.select("table.table.table-hover.table-striped.table-sfield")
					.get(2).select("tr");
			for (int i = 0; i < trs.size(); i++) {
				String resString = trs.get(i).select("td").text();
				resString = resString.replace(" ", "").trim()
						.substring(resString.length() - 2);
				switch (i) {
				case 1:
					WINING_STREAK = resString;
					break;
				case 2:
					FILING_STREAK = resString;
					break;

				default:
					break;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 比赛统计
	 * 
	 * @param Pid
	 * @param timeOut
	 * @param type 0 比赛统计 1 天梯
	 * @return
	 */
	public static List<MacthStatistics> getMacthStatistics(String Pid,
			int timeOut, int type) {

		try {
			MacthStatistics macthStatisticsBeans;
			List<MacthStatistics> macthStatisticsList = new ArrayList<MacthStatistics>();
			Document doc = Jsoup.connect(ALL_DATA + Pid).timeout(timeOut).get();

			Element table = doc
					.select("div.container.xuning-box")
					.select("table.table.table-hover.table-striped.table-sfield")
					.get(type);

			String test = table.toString().replaceAll("<span(.*)span>", "");
			Elements trs = Jsoup.parse(test).select("tr");
			for (int k = 1; k < trs.size() + 1; k = k + 2) {
				macthStatisticsBeans = new MacthStatistics();

				for (int i = k - 1; i < k + 1; i++) {
					int m = i + 1;
					if (m % 2 == 0) {
						Elements divs = trs.get(i).select("td").select("div");
						for (int j = 0; j < divs.size(); j++) {
							switch (j) {
							case 0:
								macthStatisticsBeans.setPlayTimes(divs.get(j)
										.text().trim().replace(" ", "")
										.substring(3));
								break;
							case 1:
								macthStatisticsBeans.setWinning(divs.get(j)
										.text().trim().replace(" ", "")
										.substring(3, 8));
								break;
							case 2:
								macthStatisticsBeans.setKAD(divs.get(j).text()
										.trim().replace(" ", "").substring(4));
								break;

							default:
								break;
							}
						}
					} else {
						macthStatisticsBeans.setType(trs.get(i).select("td")
								.text().trim().replace(" ", ""));

					}
					macthStatisticsList.add(macthStatisticsBeans);
				}

			}
			if(type==0){
				
			}else if (type==1) {
				
			} 
			
			return macthStatisticsList;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
