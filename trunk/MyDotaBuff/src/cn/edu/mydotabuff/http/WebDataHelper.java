package cn.edu.mydotabuff.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;

import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.bean.BestRecord;
import cn.edu.mydotabuff.bean.HerosSatistics;
import cn.edu.mydotabuff.bean.PlayerInfoBean;

/**
 * JSONUP数据获取类（使用需实现OnWebDataGetListener）
 * 
 * @author hao
 * 
 */
public class WebDataHelper {

	private OnWebDataGetListener listener = null;
	private Activity activity;
	private static final int DEFAULT_TIMEOUT = 5000; // 默认连接超时 5s
	private int timeout = DEFAULT_TIMEOUT;

	public WebDataHelper(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

	public enum DataType {
		HERO("hero"), DETAIL("detail"), MATCH("match");
		private String dataType;

		private DataType(String dataType) {
			this.dataType = dataType;
		}

		@Override
		public String toString() {
			return dataType;
		}
	}

	public void setDataGetListener(OnWebDataGetListener listener) {
		this.listener = listener;
	}

	public void setTimeOut(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * 根据不同DataType返回不同数据Bean
	 * 
	 * @param type
	 *            请求的数据类型
	 * @param userId
	 *            用户id
	 */
	public <T> void getWebData(final DataType type, final String userId) {
		if (listener != null) {
			listener.onStartGetData();
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String url = "";
				final List<T> data = new ArrayList<T>();
				switch (type) {
				case HERO:
					url = "http://dotamax.com/player/hero/" + userId;
					try {
						// TODO Auto-generated method stub

						Document doc = Jsoup.connect(url).timeout(timeout)
								.get();
						Elements trs = doc.select("tbody").select("tr");
						for (int i = 0; i < trs.size(); i++) {
							HerosSatistics heroSatisticsBeans = new HerosSatistics();
							Elements tds = trs.get(i).select("td");
							for (int j = 0; j < tds.size(); j++) {
								String text = tds.get(j).text();
								switch (j) {
								case 0:
									heroSatisticsBeans.setHeroName(text);
									break;
								case 1:
									heroSatisticsBeans.setUseTimes(Integer
											.valueOf(text));
									break;
								case 2:
									heroSatisticsBeans.setWinning(Double
											.valueOf((text.split("%"))[0]));
									break;
								case 3:
									/*
									 * 原始数据 2.71 (10.9 / 9.4 / 14.4) 替换所有空格, ( ,
									 * ) 为 / 以"/"分割 得到 kad 以及 K, A , D
									 */
									String replaceString = text
											.replace(" ", "").replaceAll(
													"[\\s()]", "/");
									String[] replaceStrings = replaceString
											.split("/");
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
									heroSatisticsBeans.setAllKAD(Double
											.valueOf(text));
									break;
								case 5:
									heroSatisticsBeans.setGold_PerMin(Double
											.valueOf(text));
									break;
								case 6:
									heroSatisticsBeans.setXp_PerMin(Double
											.valueOf(text));
									break;

								default:
									break;
								}

							}
							data.add((T) heroSatisticsBeans);
						}
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFinished(data);
								}
							}
						});
					} catch (Exception e) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFailed();
								}
							}
						});
						e.printStackTrace();
					}
					break;
				case DETAIL:
					url = "http://dotamax.com/player/detail/" + userId;
					PlayerInfoBean bean = DotaApplication.getApplication().getPlayerInfo();
					try {
						Document doc = Jsoup.connect(url).timeout(timeout).get();
						
						//获取连胜连败
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
								bean.setWinStreak(resString);
								break;
							case 1:
								bean.setLoseStreak(resString);
								break;
							default:
								break;
							}
						}
						
						//获取最高记录
						ArrayList<BestRecord> beans = new ArrayList<BestRecord>();
						Element bestRecordDiv = doc.select("div.flat-grey-box").get(2);
						Elements trs2 = bestRecordDiv.select("tbody").select("tr");
						for (int i = 0; i < trs2.size(); i++) {
							BestRecord bestRecordBeans = new BestRecord();
							Elements tds = trs2.get(i).select("td");
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

							}
							beans.add(bestRecordBeans);
						}
						bean.setBeans(beans);
						bean.setLoadWebData(true);
						DotaApplication.getApplication().setPlayerInfo(bean);
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									//详细资料获取成功 存本地sharepreference了 无须回调
									listener.onGetFinished(null);
								}
							}
						});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFailed();
								}
							}
						});
						e.printStackTrace();
					}
					break;
				case MATCH:

					break;
				default:
					break;
				}
			}
		}).start();
	}
}
