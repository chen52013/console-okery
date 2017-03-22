package com.yxqm.console.utils;

import com.yxqm.console.utils.bean.InitMatchBean;
import com.yxqm.console.utils.bean.InitOddsItem;
import com.yxqm.console.web.dao.IInterDao;

import sun.org.mozilla.javascript.internal.NativeArray;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("win007JsoupFootball")
public class Win007JsoupFootball {

	@Autowired
	@Qualifier("interDao")
	IInterDao interDao;

//	public void run() {
//		List<InitMatchBean> maxInsertTime = interDao.selectMaxFootballMatchTime();
//
//		int times = 0;
//		if (maxInsertTime.size() > 0) {
//			if (!StringUtils.isNotBlank(((InitMatchBean) maxInsertTime.get(0)).getMatch_time())) {
//				times = -365;
//			} else {
//				Calendar calendar3 = Calendar.getInstance();
//				Date date3 = calendar3.getTime();
//				DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");
//				String time3 = df3.format(date3);
//				int time_cha = timeUtil.time_cha(((InitMatchBean) maxInsertTime.get(0)).getMatch_time(), time3);
//				times = -time_cha;
//			}
//		}
//
//		for (int k = 0; k >= times; k--) {
//			Calendar calendar = Calendar.getInstance();
//			calendar.add(5, k);
//			Date date = calendar.getTime();
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			String time = df.format(date);
//			String url = "http://www.365rich.com/KJ/" + time + "/";
//			Calendar calen = Calendar.getInstance();
//			calendar.add(5, k);
//			Date newDate = calen.getTime();
//			String nowTime = df.format(newDate);
//			try {
//				Document doc = JsoupUtil.crawlPage(url);
//
//				int sum_count = doc.select("tr[gameid]").size();
//				for (int i = 0; i < sum_count; i++) {
//					InitMatchBean initOddsItem = new InitMatchBean();
//					String[] split = doc.select("tr[gameid]").select("tr").get(i).text().split(" ");
//
//					String match_num = split[0];
//					if (StringUtils.isNotBlank(match_num)) {
//						initOddsItem.setMatch_num(match_num);
//					}
//					String league_name = split[1];
//					if (StringUtils.isNotBlank(league_name)) {
//						initOddsItem.setLeague_name(league_name);
//					}
//					Calendar c = Calendar.getInstance();
//					Date date2 = null;
//					try {
//						date2 = new SimpleDateFormat("yy-MM-dd").parse(time);
//					} catch (ParseException e1) {
//						e1.printStackTrace();
//					}
//					c.setTime(date2);
//					int day = c.get(5);
//					c.set(5, day + 1);
//					String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
//
//					String match_time = dayAfter + " " + split[2];
//					if (StringUtils.isNotBlank(match_time))
//						initOddsItem.setMatch_time(match_time);
//					try {
//						if (df.parse(nowTime).getTime() > df.parse(time).getTime()) {
//							String home_status = split[3];
//							if (StringUtils.isNotBlank(home_status)) {
//								initOddsItem.setMatch_status(home_status);
//							}
//							String home_team = split[4];
//							if (StringUtils.isNotBlank(home_team)) {
//								initOddsItem.setHome_team(home_team);
//							}
//							String match_score = split[5];
//							if (StringUtils.isNotBlank(match_score)) {
//								initOddsItem.setMatch_score(match_score);
//							}
//							String guest_team = split[6];
//							if (StringUtils.isNotBlank(guest_team)) {
//								initOddsItem.setGuest_team(guest_team);
//							}
//							String guess = split[7];
//							String guess_result = doc.select("tr[gameid]").get(i).getElementsByClass("selectBox_on").text();
//							if (StringUtils.isNotBlank(guess) && StringUtils.isNotBlank(guess_result)) {
//								initOddsItem.setGuess(guess+guess_result);
//							}
//							if (split[8].length() >= 2) {
//								String win = split[8].substring(2);
//								if (StringUtils.isNotBlank(win)) {
//									initOddsItem.setWin(win);
//								}
//							}
//							if (split[9].length() >= 2) {
//								String draw = split[9].substring(2);
//								if (StringUtils.isNotBlank(draw)) {
//									initOddsItem.setDraw(draw);
//								}
//							}
//							if (split[10].length() >= 2) {
//								String lost = split[10].substring(2);
//								if (StringUtils.isNotBlank(lost))
//									initOddsItem.setLost(lost);
//							}
//						} else {
//							String home_team = split[3];
//							if (StringUtils.isNotBlank(home_team)) {
//								initOddsItem.setHome_team(home_team);
//							}
//							String guest_team = split[5];
//							if (StringUtils.isNotBlank(guest_team)) {
//								initOddsItem.setGuest_team(guest_team);
//							}
//							String guess = split[6];
//							String guess_result = doc.select("tr[gameid]").get(i).getElementsByClass("selectBox_on").text();
//							if (StringUtils.isNotBlank(guess) && StringUtils.isNotBlank(guess_result)) {
//								initOddsItem.setGuess(guess+guess_result);
//							}
//							if (split[7].length() >= 2) {
//								String win = split[7].substring(2);
//								if (StringUtils.isNotBlank(win)) {
//									initOddsItem.setWin(win);
//								}
//							}
//							if (split[8].length() >= 2) {
//								String draw = split[8].substring(2);
//								if (StringUtils.isNotBlank(draw)) {
//									initOddsItem.setDraw(draw);
//								}
//							}
//							if (split[9].length() >= 2) {
//								String lost = split[9].substring(2);
//								if (StringUtils.isNotBlank(lost))
//									initOddsItem.setLost(lost);
//							}
//						}
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					String match_id = doc.getElementsByClass("guest").select("span").get(i * 3).id().split("_")[1];
//					int addMatchList = 0;
//					if (StringUtils.isNotBlank(match_id)) {
//						initOddsItem.setMatch_id(match_id);
//						List<InitMatchBean> selectMatchList = interDao.selectMatchList(initOddsItem);
//						if (selectMatchList.size() > 0) {
//							interDao.updateMatchList(initOddsItem);
//						} else{
//							addMatchList = interDao.addMatchList(initOddsItem);
//							if(addMatchList > 0){
//								if(StringUtils.isNotBlank(match_id)){
//									String football_url = "http://1x2.nowscore.com/"+match_id+".js";
//									try {
//										Document football_doc = JsoupUtil.crawlPage(football_url);
//										ScriptEngineManager manager = new ScriptEngineManager();
//										ScriptEngine engine = manager.getEngineByName("js");
//										try {
//											engine.eval(football_doc.text());
//										} catch (ScriptException e1) {
//											e1.printStackTrace();
//										}
//										NativeArray arrArea = (NativeArray) engine.get("game");
//										Object[] items = arrArea.toArray();
//										for (Object item : items)
//											try {
//												String[] it = item.toString().split("\\|");
//												System.err.println(i);
//												InitOddsItem initOdds = new InitOddsItem();
//												initOdds.setCompany_id(it[0]);
//												initOdds.setMatch_id(match_id);
//												initOdds.setOdds_id(it[1]);
//												initOdds.setCompany_name(it[2]);
//												initOdds.setWin_odd(it[10]);
//												initOdds.setDraw_odd(it[11]);
//												initOdds.setLost_odd(it[12]);
//												initOdds.setWin_rate(it[13]);
//												initOdds.setDraw_rate(it[14]);
//												initOdds.setLost_rate(it[15]);
//												initOdds.setReturn_rate(it[16]);
//												initOdds.setWin_keli(it[17]);
//												initOdds.setDraw_keli(it[18]);
//												initOdds.setLost_keli(it[19]);
//												List<InitOddsItem> odds = interDao.queryOdds(initOdds);
//												if (odds.size() <= 0){
//													interDao.insertInitOdds(initOdds);
//												}else{
//													continue;
//												}
//											} catch (Exception e) {
//												e.printStackTrace();
//											}
//									} catch (IOException e1) {
//										e1.printStackTrace();
//									}
//								}
//							}
//						}
//					}
//					if (addMatchList > 0){
//						System.err.println("成功插入id为[" + match_id + "]的比赛数据！");
//					}else{
//						System.err.println("未成功插入数据！");
//					}
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	public void run() {
		List<InitMatchBean> maxBasketballTime = interDao.selectMaxBasketballMatchTime();
		int times = 0;
		if (maxBasketballTime.size() > 0) {
			if (!StringUtils.isNotBlank(((InitMatchBean) maxBasketballTime.get(0)).getMatch_time())) {
				times = -3650;
			} else {
				Calendar calendar3 = Calendar.getInstance();
				Date date3 = calendar3.getTime();
				DateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");
				String time3 = df3.format(date3);
				int time_cha = timeUtil.time_cha(((InitMatchBean) maxBasketballTime.get(0)).getMatch_time(), time3);
				times = -time_cha;
			}
		}
		times = -3650;
		for (int k = 0; k >= times; k--) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(5, k);
			Date date = calendar.getTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String time = df.format(date);
			String url = "http://www.365rich.com/KJ/" + time + "/";
			Calendar calen = Calendar.getInstance();
			calendar.add(5, k);
			Date newDate = calen.getTime();
			String nowTime = df.format(newDate);
			try {
				String last_url = "http://www.365rich.com/handle/JcResult.aspx?t=1&date=" + time;
				Document last_doc = JsoupUtil.crawlPage(last_url);
				Document doc = JsoupUtil.crawlPage(url);
				String script_html = doc.select("script").get(2).html();
				String script[] = script_html.split(";");
				int sum_count = doc.select("tr[gameid]").size();
				for (int i = 0; i < sum_count; i++) {
//					//2025001^1.95,2.85,3.76^4.55,3.30,1.65^6.50,3.45,3.00,3.70,7.00,14.00,30.00,60.00^4.05,16.00,50.00,4.20,3.65,5.00,38.00,16.00,5.50^6.50,7.00,15.00,45.00,5.20,5.25,11.50,35.00,8.20,8.00,15.00,45.00,18.00,19.00,29.00,80.00,50.00,50.00,70.00,120.00,100.00,150.00,175.00,150.00,250.00,400.00,300.00,400.00,80.00,500.00,175.00
					String odds = last_doc.select("i").get(i).text();
					InitMatchBean initOddsItem = new InitMatchBean();
					String[] split = doc.select("tr[gameid]").select("tr").get(i).text().split(" ");
					String match_num = split[0];
					if (StringUtils.isNotBlank(match_num)) {
						initOddsItem.setMatch_num(match_num);
					}
					String league_name = split[1];
					if (StringUtils.isNotBlank(league_name)) {
						initOddsItem.setLeague_name(league_name);
					}
					Calendar c = Calendar.getInstance();
					Date date2 = null;
					try {
						date2 = new SimpleDateFormat("yy-MM-dd").parse(time);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					c.setTime(date2);
					int day = c.get(5);
					c.set(5, day + 1);
					String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

					String match_time = dayAfter + " " + split[2];
					if (StringUtils.isNotBlank(match_time)) {
						initOddsItem.setMatch_time(match_time);
					}
					try {
						if (df.parse(nowTime).getTime() > df.parse(time).getTime()) {
							String home_status = split[3];
							if (StringUtils.isNotBlank(home_status)) {
								initOddsItem.setMatch_status(home_status);
							}
							String home_team = split[4];
							if (StringUtils.isNotBlank(home_team)) {
								initOddsItem.setHome_team(home_team);
							}
							String match_score = split[5];
							if (StringUtils.isNotBlank(match_score)) {
								initOddsItem.setMatch_score(match_score);
							}
							String guest_team = split[6];
							if (StringUtils.isNotBlank(guest_team)) {
								initOddsItem.setGuest_team(guest_team);
							}
							String guess = split[7];
							String guess_result = doc.select("tr[gameid]").get(i).getElementsByClass("selectBox_on").text();
							if (StringUtils.isNotBlank(guess) && StringUtils.isNotBlank(guess_result)) {
								initOddsItem.setGuess(guess + guess_result);
							}
							if (split[8].length() >= 2) {
								String win = split[8].substring(2);
								if (StringUtils.isNotBlank(win)) {
									initOddsItem.setWin(win);
								}
							}
							if (split[9].length() >= 2) {
								String draw = split[9].substring(2);
								if (StringUtils.isNotBlank(draw)) {
									initOddsItem.setDraw(draw);
								}
							}
							if (split[10].length() >= 2) {
								String lost = split[10].substring(2);
								if (StringUtils.isNotBlank(lost))
									initOddsItem.setLost(lost);
							}
							if(StringUtils.isNotBlank(split[11])){
								initOddsItem.setHalf_mstch_score(split[11]);
							}
							String winStr[] = "胜,平,负".split(",");
							String scoreShowName[] = "0:0,0:1,0:2,0:3,1:0,1:1,1:2,1:3,2:0,2:1,2:2,2:3,3:0,3:1,3:2,3:3,4:0,4:1,4:2,0:4,1:4,2:4,5:0,5:1,5:2,0:5,1:5,2:5,胜其他,平其他,负其他".split(",");
							if (StringUtils.isNotBlank(odds)) {
								String arr[] = odds.split("\\^");
								String arrWinLost[] = arr[1].split(",");
								String arrGoalWinLost[] = arr[2].split(",");
								String arrGoals[] = arr[3].split(",");
								String arrHalfAll[] = arr[4].split(",");
								String arrScores[] = arr[5].split(",");
								String homeScore = "";
								String guestScore = "";
								if (StringUtils.isNotBlank(match_score)) {
									String score[] = match_score.split("-");
									if (score != null && score.length > 0) {
										homeScore = score[0];
										initOddsItem.setHome_score(homeScore);
										guestScore = score[1];
										initOddsItem.setGuest_score(guestScore);
									}
								}
								int result = Integer.parseInt(homeScore) > Integer.parseInt(guestScore) ? 0 : Integer.parseInt(homeScore) == Integer.parseInt(guestScore) ? 1 : 2;
								if (homeScore == guestScore) {
									initOddsItem.setOutcome("");
									initOddsItem.setOutcome_odds("");
								} else {
									//胜负：outcome
									//胜负对应的赔率：outcome_odds
									initOddsItem.setOutcome(winStr[result]);
									if (arrWinLost != null && arrWinLost.length > 0) {
										initOddsItem.setOutcome_odds(arrWinLost[result]);
									}
								}

								String m_24 = "";
								String state = "";
								String homeHalfScore = "";
								String guestHalfScore = "";
								String hScore = "";
								String gScore = "";
								for(int l = 0;l<script.length;l++){
									int m_key = i+1;
									if(script[l].contains("M["+m_key+"][24]")){
										m_24 = script[l].split("=")[1].replaceAll("\"", "").replaceAll(" ", "");
									}else if(script[l].contains("M["+m_key+"][3]")){
										state = script[l].split("=")[1].replaceAll("\"", "").replaceAll(" ", "");
									}else if(script[l].contains("M["+m_key+"][4]")){
										hScore = script[l].split("=")[1].replaceAll("\"", "").replaceAll(" ", "");
									}else if(script[l].contains("M["+m_key+"][5]")){
										gScore = script[l].split("=")[1].replaceAll("\"", "").replaceAll(" ", "");
									}else if(script[l].contains("M["+m_key+"][6]")){
										homeHalfScore = script[l].split("=")[1].replaceAll("\"", "").replaceAll(" ", "");
									}else if(script[l].contains("M["+m_key+"][7]")){
										guestHalfScore = script[l].split("=")[1].replaceAll("\"", "").replaceAll(" ", "");
									}
								}
								int goalHomeScore = Integer.parseInt(homeScore) + Integer.parseInt(m_24);
								int goalResult = goalHomeScore > Integer.parseInt(guestScore) ? 0 : goalHomeScore == Integer.parseInt(guestScore) ? 1 : 2;

								initOddsItem.setLet_points("(" + m_24 + ")" + winStr[goalResult]);
								initOddsItem.setLet_points_odds(arrGoalWinLost[goalResult]);

								int goals = Integer.parseInt(homeScore) + Integer.parseInt(guestScore);
								String _goals = "";
								if(goals >= 7){
									_goals = "7+";
								}else{
									_goals = String.valueOf(goals);
								}
								initOddsItem.setGoals(_goals);
								goals = goals >= 7 ? 7 : goals;
								initOddsItem.setGoals_odds(arrGoals[goals]);

								if (state == "-1" || state.equals("-1")) {
									int resultHalf = Integer.parseInt(homeHalfScore) > Integer.parseInt(guestHalfScore) ? 0 : Integer.parseInt(homeHalfScore) == Integer.parseInt(guestHalfScore) ? 1 : 2;
									initOddsItem.setHalf_result(winStr[resultHalf] + winStr[result]);
									//33,31,30,13,11,10,03,01,00
									initOddsItem.setHalf_result_odds(arrHalfAll[resultHalf * 3 + result]);
								}else if(Integer.parseInt(state) > 1) {
									int resultHalf = Integer.parseInt(homeHalfScore) > Integer.parseInt(guestHalfScore) ? 0 : Integer.parseInt(homeHalfScore) == Integer.parseInt(guestHalfScore) ? 1 : 2;
									initOddsItem.setHalf_result(winStr[resultHalf] + winStr[result]);
									//33,31,30,13,11,10,03,01,00
									initOddsItem.setHalf_result_odds(arrHalfAll[resultHalf * 3 + result]);
								}else {
									initOddsItem.setHalf_result("");
									initOddsItem.setHalf_result_odds("");
								}

								String scoreList[] = "00,01,02,03,10,11,12,13,20,21,22,23,30,31,32,33,40,41,42,04,14,24,50,51,52,05,15,25".split(",");
								int num = -1;
								for (int j = 0; j < scoreList.length; j++) {
									if (String.valueOf(hScore) + String.valueOf(gScore) == scoreList[j]) {
										num = j;
										break;
									}
								}
								if (num == -1){
									boolean is_big = false;
									boolean is_equal = false;
									if(hScore == "0" || hScore.equals("0")){
										if(gScore == "0" || gScore.equals("0")){
											is_equal = true;
										}else{
											is_equal = false;
											is_big = false;
										}
									}else {
										if(gScore == "0" || gScore.equals("0")){
											is_big = true;
										}else{
											if(Integer.parseInt(hScore) > Integer.parseInt(gScore)){
												is_big = true;
											}else if(Integer.parseInt(hScore) == Integer.parseInt(gScore)){
												is_equal = true;
											}
										}
									}
									num = is_big ? 28 : is_equal ? 29 : 30;
								}
								initOddsItem.setScore(scoreShowName[num]);
								initOddsItem.setScore_odds(arrScores[num]);
							}
						} else {
							String home_team = split[3];
							if (StringUtils.isNotBlank(home_team)) {
								initOddsItem.setHome_team(home_team);
							}
							String guest_team = split[5];
							if (StringUtils.isNotBlank(guest_team)) {
								initOddsItem.setGuest_team(guest_team);
							}
							String guess = split[6];
							String guess_result = doc.select("tr[gameid]").get(i).getElementsByClass("selectBox_on").text();
							if (StringUtils.isNotBlank(guess) && StringUtils.isNotBlank(guess_result)) {
								initOddsItem.setGuess(guess + guess_result);
							}
							if (split[7].length() >= 2) {
								String win = split[7].substring(2);
								if (StringUtils.isNotBlank(win)) {
									initOddsItem.setWin(win);
								}
							}
							if (split[8].length() >= 2) {
								String draw = split[8].substring(2);
								if (StringUtils.isNotBlank(draw)) {
									initOddsItem.setDraw(draw);
								}
							}
							if (split[9].length() >= 2) {
								String lost = split[9].substring(2);
								if (StringUtils.isNotBlank(lost))
									initOddsItem.setLost(lost);
							}
						}
						} catch(Exception e){
							e.printStackTrace();
						}
						String match_id = "";
						int addMatchList = 0;
						if (doc.getElementsByClass("home").select("span").size() > i * 3) {
							match_id = doc.getElementsByClass("home").select("span").get(i * 3).id().split("_")[1];
							if (StringUtils.isNotBlank(match_id)) {
								initOddsItem.setMatch_id(match_id);
								List<InitMatchBean> selectMatchList = interDao.selectMatchList(initOddsItem);
								if (selectMatchList.size() > 0) {
									if (!StringUtils.isNotBlank(((InitMatchBean) selectMatchList.get(0)).getHalf_mstch_score())) {
										if (StringUtils.isNotBlank(initOddsItem.getHalf_mstch_score())) {
											interDao.updateMatchList(initOddsItem);
											System.err.println("成功更新比赛ID为[" + match_id + "]的比赛比分！");
										}
									}
								} else {
									addMatchList = interDao.addMatchList(initOddsItem);
								}
							}
						}
						if (addMatchList > 0) {
							System.err.println("成功插入比赛ID为[" + match_id + "]的比赛数据！");
						}
					}
				} catch(IOException e){
					e.printStackTrace();
				}
			}

	}

	public static void main(String[] args) {
		new Win007JsoupFootball().run();
	}
}