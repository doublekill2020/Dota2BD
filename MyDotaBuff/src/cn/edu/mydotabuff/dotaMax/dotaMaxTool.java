package cn.edu.mydotabuff.dotaMax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import u.aly.m;

import cn.edu.mydotabuff.bean.BestRecord;
import cn.edu.mydotabuff.bean.HeroMatchStatistics;
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
			Pattern p = null ;
			Matcher m1 = null;
			HerosSatistics heroSatisticsBeans;
			List<HerosSatistics> heroSatisticsList = new ArrayList<HerosSatistics>();
			Document doc = Jsoup.connect(HERO_SATISTICS_Uri + Pid)
					.timeout(timeOut).get();
			Elements trs = doc.select("tbody").select("tr");
			for (int i = 0; i < trs.size(); i++) {
				heroSatisticsBeans = new HerosSatistics();
				Elements tds = trs.get(i).select("td");
				String uri	= tds.get(0) .getElementsByTag("a").first().attr("href").toString();
				heroSatisticsBeans.setThisHeroDataUri(uri);
			    p = Pattern.compile("hero=(.*)" );
			    m1 = p.matcher(uri); 
			    m1.find();
			    String result =   m1.group().substring("hero=".length());
			    heroSatisticsBeans.setHeroID(Integer.valueOf(result));
				
				
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

		return null;
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
						bestRecordBeans.setImageUri(tds.get(j).getElementsByTag("img").first().attr("src"));
						bestRecordBeans.setHeroName(tds.get(j).text());
						break;
					case 4:
						bestRecordBeans.setRecordNum(tds.get(j).text());
						break;
					default:
						break;
					}

				}
				bestRecodList.add(bestRecordBeans);
			}
			return bestRecodList;
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
				case 0:
					WINING_STREAK = resString;
					break;
				case 1:
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
	 * @param type
	 *            0 全部统计 1 天梯
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
				}
				macthStatisticsList.add(macthStatisticsBeans);
			}

			return macthStatisticsList;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 获取英雄数据合集
	 * 
	 * @param heroDataUri
	 *            heroSatisticsBeans.getThisHeroDataUri();
	 * @param timeOut
	 *            延迟 单位ms
	 * @return
	 */
	public static List<HeroMatchStatistics> getHeroMatchStatistics(
			String heroDataUri, int timeOut) {
		try {
			Document doc = Jsoup.connect(MAIN_URI + heroDataUri).timeout(timeOut)
					.get();
			Elements trs = doc
					.select("table.table.table-hover.table-striped.sortable.table-list.table-thead-left")
					.select("tbody").select("tr");
			HeroMatchStatistics heroMatchStatisticsBeans;
			List<HeroMatchStatistics> heroMatchStatisticsList = new ArrayList<HeroMatchStatistics>();
			for (int i = 0; i < trs.size(); i++) {
				heroMatchStatisticsBeans = new HeroMatchStatistics();
				Elements tds = trs.get(i).select("td");
				for (int j = 0; j < tds.size(); j++) {
					if (j == 6) {
						Elements imgs = tds.get(j).select("img");
						List<String> tmpList = new ArrayList<String>();
						for (int k = 0; k < imgs.size(); k++) {
							tmpList.add(imgs.get(k).attr("src"));
						}
						heroMatchStatisticsBeans.setItemsImgURI(tmpList);
					} else {
						switch (j) {
						case 0:
							heroMatchStatisticsBeans.setHeroName(tds.get(j)
									.text());
							break;
						case 1:
							String newString = tds.get(j).text()
									.replace(" ", "");
							String tmp = newString;
							newString = newString.substring(0,
									newString.length() - 4);
							tmp = tmp.substring(tmp.length() - 4);
							heroMatchStatisticsBeans.setMatchID(newString);
							heroMatchStatisticsBeans.setMatchType(tmp);
							break;
						case 2:
							heroMatchStatisticsBeans.setWhatTime(tds.get(j)
									.text());
							break;
						case 3:

							heroMatchStatisticsBeans.setResult(tds.get(j)
									.text());
							break;
						case 4:
							/*
							 * 原始数据 2.71 (10.9 / 9.4 / 14.4) 替换所有空格, ( , ) 为 /
							 * 以"/"分割 得到 KDA 以及 K, D , A
							 */

							String replaceString = tds.get(j).text()
									.replace(" ", "")
									.replaceAll("[\\s()]", "/");
							String[] replaceStrings = replaceString.split("/");
							heroMatchStatisticsBeans.setKDA(Double
									.valueOf(replaceStrings[0]));
							heroMatchStatisticsBeans.setKill(Double
									.valueOf(replaceStrings[1]));
							heroMatchStatisticsBeans.setDeath(Double
									.valueOf(replaceStrings[2]));
							heroMatchStatisticsBeans.setAssists(Double
									.valueOf(replaceStrings[3]));
							break;
						case 5:
							heroMatchStatisticsBeans
									.setLevel(tds.get(j).text());
							break;

						default:
							break;
						}
					}

				}
				heroMatchStatisticsList.add(heroMatchStatisticsBeans);
			}
			return heroMatchStatisticsList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
