package cn.edu.mydotabuff.common.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



import android.app.Activity;


import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.DotaApplication.LocalDataType;
import cn.edu.mydotabuff.common.bean.BestRecord;
import cn.edu.mydotabuff.common.bean.HeroMatchStatistics;
import cn.edu.mydotabuff.common.bean.HerosSatistics;
import cn.edu.mydotabuff.common.bean.MacthStatistics;
import cn.edu.mydotabuff.common.bean.PlayerInfoBean;
import cn.edu.mydotabuff.common.bean.UserInfo;

/**
 * JSONUP数据获取类（使用需实现OnWebDataGetListener）
 * 
 * @author haoJJ
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
		HERO("hero"), DETAIL("detail"), MATCH("match"), USER("user"), ;
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
				// 1 成功 0 JSON解析出错 -1无匹配结果 -2 DOC == null,连接失败
				int status = 1;
				final List<T> data = new ArrayList<T>();
				final ArrayList<HeroMatchStatistics> HMSBeans = new ArrayList<HeroMatchStatistics>();
				final ArrayList<UserInfo> infos = new ArrayList<UserInfo>();
				switch (type) {
				case HERO:
					url = "http://dotamax.com/player/hero/" + userId;
					try {
						// TODO Auto-generated method stub
						Pattern p = null;
						Matcher m1 = null;
						Document doc = Jsoup.connect(url).timeout(timeout)
								.get();
						Elements trs = doc.select("tbody").select("tr");
						for (int i = 0; i < trs.size(); i++) {
							HerosSatistics heroSatisticsBeans = new HerosSatistics();
							Elements tds = trs.get(i).select("td");
							String uri = tds.get(0).getElementsByTag("a")
									.first().attr("href").toString();
							heroSatisticsBeans.setThisHeroDataUri(uri);
							p = Pattern.compile("hero=(.*)");
							m1 = p.matcher(uri);
							m1.find();
							String result = m1.group().substring(
									"hero=".length());
							heroSatisticsBeans.setHeroID(Integer
									.valueOf(result));
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
						status = 1;
					} catch (Exception e) {
						status = 0;
						e.printStackTrace();
					}
					if (status == 1) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFinished(data);
								}
							}
						});
					} else if (status == 0) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFailed("JSON解析出错");
								}
							}
						});
					}
					break;

				case DETAIL:
					url = "http://dotamax.com/player/detail/" + userId;
					PlayerInfoBean bean = DotaApplication.getApplication()
							.getData(LocalDataType.PLAYER_INFO);
					if(bean == null){
						bean = new PlayerInfoBean();
					}
					Document doc = null;
					try {
						doc = Jsoup.connect(url).timeout(timeout).get();
						if (doc != null) {
							// 获取连胜连败
							if (doc.select("div.container.xuning-box")
									.select("table.table.table-hover.table-striped.table-sfield")
									.size() > 0) {
								Elements trs = doc
										.select("div.container.xuning-box")
										.select("table.table.table-hover.table-striped.table-sfield")
										.get(2).select("tr");
								for (int i = 0; i < trs.size(); i++) {
									String resString = trs.get(i).select("td")
											.text();
									resString = resString.replace(" ", "");
									resString = resString.trim().substring(7,
											resString.length());
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
								// 获取最高记录
								ArrayList<BestRecord> beans = new ArrayList<BestRecord>();
								Element bestRecordDiv = doc.select(
										"div.flat-grey-box").get(2);
								Elements trs2 = bestRecordDiv.select("tbody")
										.select("tr");
								for (int i = 0; i < trs2.size(); i++) {
									BestRecord bestRecordBeans = new BestRecord();
									Elements tds = trs2.get(i).select("td");
									for (int j = 0; j < tds.size(); j++) {
										switch (j) {
										case 0:
											bestRecordBeans.setRecordType(tds
													.get(j).text());
											break;
										case 1:
											bestRecordBeans.setMmatchID(tds
													.get(j).text());
											break;
										case 2:
											bestRecordBeans.setResult(tds
													.get(j).text());
											break;
										case 3:
											bestRecordBeans.setImageUri(tds
													.get(j)
													.getElementsByTag("img")
													.first().attr("src"));
											bestRecordBeans.setHeroName(tds
													.get(j).text());
											break;
										case 4:
											bestRecordBeans.setRecordNum(tds
													.get(j).text());
											break;
										default:
											break;
										}

									}
									beans.add(bestRecordBeans);
								}
								bean.setBeans(beans);
								bean.setLoadWebData(true);

								ArrayList<MacthStatistics> list = new ArrayList<MacthStatistics>();
								for (int type = 0; type <= 1; type++) {
									Element table = doc
											.select("div.container.xuning-box")
											.select("table.table.table-hover.table-striped.table-sfield")
											.get(type);
									String test = table.toString().replaceAll(
											"<span(.*)span>", "");
									Elements _trs = Jsoup.parse(test).select(
											"tr");
									for (int k = 1; k < _trs.size() + 1; k = k + 2) {
										MacthStatistics macthStatisticsBeans = new MacthStatistics();
										// int n = 0;
										// if (type == 0) {
										// n = 1;
										// } else {
										// n = -3;
										// }
										for (int i = k - 1; i < k + 1; i++) {
											int m = i + 1;
											if (m % 2 == 0) {
												Elements divs = _trs.get(i)
														.select("td")
														.select("div");
												for (int j = 0; j < divs.size(); j++) {
													switch (j) {
													case 0:
														macthStatisticsBeans
																.setPlayTimes(divs
																		.get(j)
																		.text()
																		.trim()
																		.replace(
																				" ",
																				"")
																		.substring(
																				3));
														break;
													case 1:
														String rate = divs
																.get(j)
																.text()
																.trim()
																.replace(" ",
																		"")
																.substring(3, 8);
														if (rate.contains("%")) {
															rate = rate
																	.substring(
																			0,
																			rate.length() - 1);
														}
														macthStatisticsBeans
																.setWinning(rate);
														break;
													case 2:
														macthStatisticsBeans
																.setKAD(divs
																		.get(j)
																		.text()
																		.trim()
																		.replace(
																				" ",
																				"")
																		.substring(
																				4));
														break;
													default:
														break;
													}
												}
											} else {
												macthStatisticsBeans
														.setType(_trs
																.get(i)
																.select("td")
																.text()
																.trim()
																.replace(" ",
																		""));
											}

										}
										list.add(macthStatisticsBeans);
									}
								}
								bean.setList(list);
								bean.setLoadMap(true);
								DotaApplication.getApplication().saveData(bean,
										LocalDataType.PLAYER_DETAIL_INFO);
								status = 1;
							}else{
								status = -2;
							}
						} else {
							status = -2;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						status = 0;
						e.printStackTrace();
					}
					if (status == 1) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									// 详细资料获取成功 存本地sharepreference了 无须回调
									listener.onGetFinished(null);
								}
							}
						});
					} else if (status == 0) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFailed("JSON解析出错");
								}
							}
						});
					} else if (status == -2) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFailed("DOC == null,连接失败");
								}
							}
						});
					}
					break;
				case MATCH:
					try {
						url = "http://www.dotamax.com" + userId;
						doc = Jsoup.connect(url).timeout(timeout).get();
						if (doc != null) {
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
										Elements imgs = tds.get(j)
												.select("img");
										List<String> tmpList = new ArrayList<String>();
										for (int k = 0; k < imgs.size(); k++) {
											tmpList.add(imgs.get(k).attr("src"));
										}
										heroMatchStatisticsBeans
												.setItemsImgURI(tmpList);
									} else {
										switch (j) {
										case 0:
											heroMatchStatisticsBeans
													.setHeroName(tds.get(j)
															.text());
											break;
										case 1:
											String newString = tds.get(j)
													.text().replace(" ", "");
											String tmp = newString;
											newString = newString.substring(0,
													newString.length() - 4);
											tmp = tmp.substring(tmp.length() - 4);
											heroMatchStatisticsBeans
													.setMatchID(newString);
											heroMatchStatisticsBeans
													.setMatchType(tmp);
											break;
										case 2:
											heroMatchStatisticsBeans
													.setWhatTime(tds.get(j)
															.text());
											break;
										case 3:

											heroMatchStatisticsBeans
													.setResult(tds.get(j)
															.text());
											break;
										case 4:
											/*
											 * 原始数据 2.71 (10.9 / 9.4 / 14.4)
											 * 替换所有空格, ( , ) 为 / 以"/"分割 得到 KDA
											 * 以及 K, D , A
											 */

											String replaceString = tds.get(j)
													.text().replace(" ", "")
													.replaceAll("[\\s()]", "/");
											String[] replaceStrings = replaceString
													.split("/");
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
								HMSBeans.add(heroMatchStatisticsBeans);
							}
							status = 1;
						} else {
							status = -2;
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						status = 0;
					}

					if (status == 1) {

						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								if (listener != null) {
									listener.onGetFinished(HMSBeans);
								}
							}
						});

					} else if (status == 0) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFailed("解析出错,请重新尝试");
								}
							}
						});
					} else if (status == -2) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFailed("请改善你的网络环境,并重新尝试");
								}
							}
						});
					}
					break;

				case USER:
					try {
						boolean saveFlag = false;
						String newUserId = URLEncoder.encode(userId, "UTF-8");
						url = "http://dotamax.com/search/?q=" + newUserId;
						doc = Jsoup.connect(url).timeout(timeout).get();
						if (doc != null) {

							String flag = doc.toString();
							// 查询结果为多个
							if (flag.indexOf("主页  Dotamax -") >= 0) {
								Elements trs = doc
										.select("table.table.table-hover.table-striped.table-list.table-search-result")
										.select("tbody").select("tr");
								for (int i = 1; i < trs.size(); i++) {
									UserInfo caijj = new UserInfo();
									String imgUrl = trs.get(i)
											.getElementsByTag("img").first()
											.attr("src").toString();
									caijj.setImgUrl(imgUrl);
									String tmp = trs.get(i).select("td")
											.first().text().replace(" ", "");
									int num = tmp.indexOf("ID:");
									caijj.setUserName(tmp.substring(0, num));
									caijj.setUserID(tmp.substring(num,
											tmp.length()));
									infos.add(caijj);
								}
								saveFlag = true;
							}
							// 查询结果为空
							else if (flag.indexOf("公开比赛数据") >= 0) {
								saveFlag = false;
							}
							// 查询结果唯一
							else {
								UserInfo haojj = new UserInfo();
								String result = doc.getElementById(
										"ibackground").toString();
								result = result.trim().replace(" ", "");
								String imgUrl = (result.substring(
										result.indexOf("pic:\"") + 5,
										result.indexOf("});"))).trim().replace(
										"\"", "");
								String name = (result.substring(
										result.indexOf("title:\"") + 7,
										result.indexOf("-DOTA2个人主页\""))).trim();
								String Id = result.substring(
										result.indexOf("ID:") + 3,
										result.indexOf("ID:") + 3 + 9);

								haojj.setImgUrl(imgUrl);
								haojj.setUserName(name);
								haojj.setUserID(Id);
								infos.add(haojj);
								saveFlag = true;
							}
							if (saveFlag) {
								status = 1;
							} else {
								status = -1;
							}

						} else {
							status = -2;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						status = 0;
					}
					if (status == 1) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFinished(infos);
								}
							}
						});
					} else if (status == 0) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFailed("JSON解析出错");
								}
							}
						});
					} else if (status == -2) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFailed("DOC == null,连接失败");
								}
							}
						});
					} else if (status == -1) {
						activity.runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (listener != null) {
									listener.onGetFailed("无匹配结果");
								}
							}
						});
					}
					break;
				default:
					break;
				}
			}
		}).start();
	}
}
