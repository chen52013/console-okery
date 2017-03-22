package com.yxqm.console.utils;

import com.yxqm.console.utils.bean.InitMatchBean;
import com.yxqm.console.utils.bean.InitOddsItem;
import com.yxqm.console.web.dao.IInterDao;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import sun.org.mozilla.javascript.internal.NativeArray;

import static javafx.scene.input.KeyCode.M;

/**
 * 抓取篮球比赛
 * @author Dell
 *
 */
@Service("win007JsoupBasketballball")
public class win007JsoupBasketballball {

	@Autowired
	@Qualifier("interDao")
	IInterDao interDao;

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
			String url = "http://www.365rich.com/BKJ/" + time + "/";
			Calendar calen = Calendar.getInstance();
			calendar.add(5, k);
			Date newDate = calen.getTime();
			String nowTime = df.format(newDate);
			try {
				String last_url = "http://www.365rich.com/handle/JcResult.aspx?t=3&date=" + time;
				Document last_doc = JsoupUtil.crawlPage(last_url);
				Document doc = JsoupUtil.crawlPage(url);
				int sum_count = doc.select("tr[gameid]").size();
				for (int i = 0; i < sum_count; i++) {
					//2026301^2.00,1.55^2.50,1.75,1.75^4.45,4.70,9.25,19.00,35.00,46.00,3.85,4.00,7.00,12.50,23.00,29.00^216.50,1.81,1.70
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
					if (StringUtils.isNotBlank(match_time))
						initOddsItem.setMatch_time(match_time);
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
								String score[] = match_score.split("-");
								if(home_status == "完" || home_status.equals("完")){
									initOddsItem.setHome_score(score[0]);
									initOddsItem.setGuest_score(score[1]);
								}else if(home_status == "取消" || home_status.equals("取消")){
									initOddsItem.setHome_score("0");
									initOddsItem.setGuest_score("0");
								}
							}
							String guest_team = split[6];
							if (StringUtils.isNotBlank(guest_team)) {
								initOddsItem.setGuest_team(guest_team);
							}
							String guess = split[7];
							String guess_result = doc.select("tr[gameid]").get(i).getElementsByClass("selectBox_on").text();
							if (StringUtils.isNotBlank(guess) && StringUtils.isNotBlank(guess_result)) {
								initOddsItem.setGuess(guess+guess_result);
							}
							if (split[8].length() >= 2) {
								String win = split[8].substring(2);
								if (StringUtils.isNotBlank(win)) {
									initOddsItem.setWin(win);
								}
							}
							if (split[9].length() >= 2) {
								String lost = split[9].substring(2);
								if (StringUtils.isNotBlank(lost))
									initOddsItem.setLost(lost);
							}
							String winStr[] = "胜,负".split(",");
							String scoreShowName[] = "胜1-5分,胜6-10分,胜11-15分,胜16-20分,胜21-25分,胜26分以上,负1-5分,负6-10分,负11-15分,负16-20分,负21-25分,负26分以上".split(",");
							if(StringUtils.isNotBlank(odds)){
								String arr[] = odds.split("\\^");
								String arrWinLost[] = arr[1].split(",");
								String arrGoalWinLost[] = arr[2].split(",");
								String arrScores[] = arr[3].split(",");
								String arrOverDown[] = arr[4].split(",");
								String homeScore = "0";
								String guestScore = "0";
								if (StringUtils.isNotBlank(match_score)) {
									String score[] = match_score.split("-");
									if(score != null && score.length > 0){
										homeScore = score[0];
										guestScore = score[1];
									}
								}
								if (homeScore == guestScore){
									initOddsItem.setOutcome("");
									initOddsItem.setOutcome_odds("");
								}else{
									int result = Integer.parseInt(homeScore) > Integer.parseInt(guestScore) ? 0 : 1;
									//胜负：outcome
									//胜负对应的赔率：outcome_odds
									initOddsItem.setOutcome(winStr[result]);
									if(arrWinLost != null && arrWinLost.length > 0 ){
										initOddsItem.setOutcome_odds(arrWinLost[result]);
									}
								}
								Double goalHomeScore = Double.parseDouble(homeScore) + Double.parseDouble(arrGoalWinLost[0]);
								int goalResult = goalHomeScore > Double.parseDouble(guestScore) ? 0 :1;
								//让分：let_points
								//让分赔率：let_points_odds
								initOddsItem.setLet_points(winStr[goalResult]);
								if(arrGoalWinLost != null && arrGoalWinLost.length > 0 ){
									initOddsItem.setLet_points_odds(arrGoalWinLost[goalResult + 1]);
								}
								//分差
								int diff = Integer.parseInt(homeScore) - Integer.parseInt(guestScore);
								if (diff != 0) {
									int score = Math.abs(diff);
									int num = -1;
									for (int j = 0; j < 5; j++) {
										if (score >= 1 + 5 * j && score <= 5 + 5 * j) {
											num = j;
											break;
										}
									}
									if (num == -1) {
										num = diff > 0 ? 5 : 11;
									}else {
										num = diff > 0 ? num : num + 6;
									}
									int diffNum = num;
									//胜负差：score_diff
									//胜负差赔率：score_diff_odds
									initOddsItem.setScore_diff(scoreShowName[diffNum]);
									if(arrScores != null && arrScores.length > 0 ){
										initOddsItem.setScore_diff_odds(arrScores[diffNum]);
									}
								}else {
									//胜负差：score_diff
									//胜负差赔率：score_diff_odds
									initOddsItem.setScore_diff("");
									initOddsItem.setScore_diff_odds("");
								}
								//大小分
								boolean isBig = (Integer.parseInt(homeScore) + Integer.parseInt(guestScore)) > Double.parseDouble(arrOverDown[0]);
								//大小分：big_small_score
								//大小分赔率：big_small_score_odds
								if(arrOverDown != null && arrOverDown.length > 0 ){
									initOddsItem.setBig_small_score("(" + arrOverDown[0] + ")" + (isBig ? "大" : "小"));
									initOddsItem.setBig_small_score_odds(isBig ? arrOverDown[1] : arrOverDown[2]);
								}
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
								initOddsItem.setGuess(guess+guess_result);
							}
							if (split[7].length() >= 2) {
								String win = split[7].substring(2);
								if (StringUtils.isNotBlank(win)) {
									initOddsItem.setWin(win);
								}
							}
							if (split[8].length() >= 2) {
								String lost = split[8].substring(2);
								if (StringUtils.isNotBlank(lost))
									initOddsItem.setLost(lost);
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String match_id = "";
					int addMatchList = 0;
					if (doc.getElementsByClass("home").select("span").size() > i * 3) {
						match_id = doc.getElementsByClass("home").select("span").get(i * 3).id().split("_")[1];
						if (StringUtils.isNotBlank(match_id)) {
							initOddsItem.setMatch_id(match_id);
							List<InitMatchBean> selectMatchList = interDao.selectBasketballList(initOddsItem);
							if (selectMatchList.size() > 0) {
								if (!StringUtils.isNotBlank(((InitMatchBean) selectMatchList.get(0)).getHome_score())) {
									if (StringUtils.isNotBlank(initOddsItem.getHome_score())) {
										interDao.updateNBAMatchList(initOddsItem);
										System.err.println("成功更新比赛ID为[" + match_id + "]的比赛比分！");
									}
								}
							} else {
								addMatchList = interDao.addBasketballList(initOddsItem);
							}
						}
					}
					if (addMatchList > 0)
						System.err.println("成功插入比赛ID为[" + match_id + "]的比赛数据！");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new win007JsoupBasketballball().run();
	}
}