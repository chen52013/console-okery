package com.yxqm.console.utils;

import com.yxqm.console.utils.bean.InitMatchBean;
import com.yxqm.console.utils.bean.InitOddsItem;
import com.yxqm.console.web.bean.FootballMatchBean;
import com.yxqm.console.web.dao.IInterDao;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sun.org.mozilla.javascript.internal.NativeArray;

@Service("win007Keli")
public class Win007Keli {

	@Autowired
	@Qualifier("interDao")
	IInterDao interDao;

//	public void runNBA() {
//		List<FootballMatchBean> nbaMatchList = interDao.queryBasketballMatchList(null);
//		if(nbaMatchList != null && nbaMatchList.size() > 0){
//			for (FootballMatchBean initMatchBean : nbaMatchList) {
//				String match_id = initMatchBean.getMatch_id();
//				if(StringUtils.isNotBlank(match_id)){
//					//http://1x2.nowscore.com/1251519.js
//					//http://nba.win007.com/1x2/data1x2/2/57/257220.js
//					String url = "http://1x2.nowscore.com/"+match_id+".js";
//					try {
//						Document doc = JsoupUtil.crawlPage(url);
//						ScriptEngineManager manager = new ScriptEngineManager();
//						ScriptEngine engine = manager.getEngineByName("js");
//						try {
//							engine.eval(doc.text());
//						} catch (ScriptException e1) {
//							e1.printStackTrace();
//						}
//						NativeArray arrArea = (NativeArray) engine.get("game");
//						Object[] items = arrArea.toArray();
//						for (Object item : items)
//							try {
//								String[] i = item.toString().split("\\|");
//								System.err.println(i);
//								InitOddsItem initOddsItem = new InitOddsItem();
//								initOddsItem.setCompany_id(i[0]);
//								initOddsItem.setMatch_id(match_id);
//								initOddsItem.setOdds_id(i[1]);
//								initOddsItem.setCompany_name(i[2]);
//								initOddsItem.setWin_odd(i[10]);
//								initOddsItem.setDraw_odd(i[11]);
//								initOddsItem.setLost_odd(i[12]);
//								initOddsItem.setWin_rate(i[13]);
//								initOddsItem.setDraw_rate(i[14]);
//								initOddsItem.setLost_rate(i[15]);
//								initOddsItem.setReturn_rate(i[16]);
//								initOddsItem.setWin_keli(i[17]);
//								initOddsItem.setDraw_keli(i[18]);
//								initOddsItem.setLost_keli(i[19]);
//								List<InitOddsItem> odds = interDao.queryOdds(initOddsItem);
//
//								if (odds.size() <= 0)
//									interDao.insertNBAOdds(initOddsItem);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//				}
//			}
//		}
//	}
	
	public void runFootball() {
		List<FootballMatchBean> footballMatchList = interDao.queryFootballMatchList(null);
		if(footballMatchList != null && footballMatchList.size() > 0){
			for (FootballMatchBean initMatchBean : footballMatchList) {
				String match_id = initMatchBean.getMatch_id();
				if(StringUtils.isNotBlank(match_id)){
					String url = "http://1x2.nowscore.com/"+match_id+".js";
					try {
						Document doc = JsoupUtil.crawlPage(url);
						ScriptEngineManager manager = new ScriptEngineManager();
						ScriptEngine engine = manager.getEngineByName("js");
						try {
							engine.eval(doc.text());
						} catch (ScriptException e1) {
							e1.printStackTrace();
						}
						NativeArray arrArea = (NativeArray) engine.get("game");
						Object[] items = arrArea.toArray();
						for (Object item : items)
							try {
								String[] i = item.toString().split("\\|");
								System.err.println(i);
								InitOddsItem initOddsItem = new InitOddsItem();
								initOddsItem.setCompany_id(i[0]);
								initOddsItem.setMatch_id(match_id);
								initOddsItem.setOdds_id(i[1]);
								initOddsItem.setCompany_name(i[2]);
								initOddsItem.setWin_odd(i[10]);
								initOddsItem.setDraw_odd(i[11]);
								initOddsItem.setLost_odd(i[12]);
								initOddsItem.setWin_rate(i[13]);
								initOddsItem.setDraw_rate(i[14]);
								initOddsItem.setLost_rate(i[15]);
								initOddsItem.setReturn_rate(i[16]);
								initOddsItem.setWin_keli(i[17]);
								initOddsItem.setDraw_keli(i[18]);
								initOddsItem.setLost_keli(i[19]);
								List<InitOddsItem> odds = interDao.queryOdds(initOddsItem);
								if (odds.size() <= 0){
									interDao.insertInitOdds(initOddsItem);
								}else{
									continue;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

//	public static void main(String[] args) {
//		new Win007Keli().runNBA();
//	}

}