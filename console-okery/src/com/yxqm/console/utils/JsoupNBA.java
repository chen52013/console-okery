package com.yxqm.console.utils;

import com.yxqm.console.web.bean.NBABean;
import com.yxqm.console.web.bussiness.IInterService;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class JsoupNBA {

	@Autowired
	@Qualifier("interService")
	IInterService interService;

	public int NBAjsoup() {
		Document startdoc = null;
		int rows = 0;
		try {
			startdoc = JsoupUtil.crawlPage("http://www.nbazww.com/h/rank/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements rankList = startdoc.getElementsByClass("rank_list");
		System.err.println(rankList);
		Elements table = rankList.select("table");
		for (int i = 0; i < table.size(); i++) {
			Elements tr = table.get(i).select("tr");
			String east_west = null;
			NBABean bean = new NBABean();
			if (tr.size() > 0) {
				east_west = tr.get(0).text();
				bean.setEast_west(east_west);
			}
			for (int j = 2; j < tr.size(); j++) {
				Elements td = tr.get(j).select("td");

				String count = td.get(0).text();
				if (StringUtils.isNotBlank(count)) {
					bean.setCount(count);
				}

				String team_name = td.get(1).text();
				if (StringUtils.isNotBlank(team_name)) {
					bean.setTeam_name(team_name);
				}

				String win_count = td.get(2).text();
				if (StringUtils.isNotBlank(win_count)) {
					bean.setWin_count(win_count);
				}

				String lose_count = td.get(3).text();
				if (StringUtils.isNotBlank(lose_count)) {
					bean.setLose_count(lose_count);
				}

				String win_persent = td.get(4).text();
				if (StringUtils.isNotBlank(win_persent)) {
					bean.setWin_persent(win_persent);
				}

				String win_lose_count = td.get(5).text();
				if (StringUtils.isNotBlank(win_lose_count)) {
					bean.setWin_lose_count(win_lose_count);
				}

				String win_point = td.get(6).text();
				if (StringUtils.isNotBlank(win_point)) {
					bean.setWin_point(win_point);
				}

				String lose_point = td.get(7).text();
				if (StringUtils.isNotBlank(lose_point)) {
					bean.setLose_point(lose_point);
				}

				String home = td.get(9).text();
				if (StringUtils.isNotBlank(home)) {
					bean.setHome(home);
				}

				String away = td.get(10).text();
				if (StringUtils.isNotBlank(away)) {
					bean.setAway(away);
				}

				String first10 = td.get(13).text();
				if (StringUtils.isNotBlank(first10)) {
					bean.setFirst10(first10);
				}

				String double_win = td.get(14).text();
				if (StringUtils.isNotBlank(away)) {
					bean.setDouble_win(double_win);
				}
				rows = this.interService.addNBA(bean);
			}
		}
		return rows;
	}

	public static void main(String[] args) {
		new JsoupNBA().NBAjsoup();
	}
}