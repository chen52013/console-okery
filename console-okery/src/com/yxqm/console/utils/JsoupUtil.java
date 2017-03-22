package com.yxqm.console.utils;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtil {
	private static final int MAX_TIMES_TO_TRY = 3;
	private static final ThreadLocal<Connection> connBean = new ThreadLocal() {
		protected Connection initialValue() {
			Connection conn = Jsoup.connect("http://zq.win007.com/");
			conn.header("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
			conn.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.header("accept-language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			conn.header("connection", "keep-alive");
			conn.header("referer", "http://live3.win007.com/");
			conn.header("host", "live3.win007.com");
			conn.header("Cookie",
					"Bet007live_hiddenID=_; Bet007live_concernId=_1147424_1231608_1218512_; Cookie=2^0^1^1^1^1^1^0^0^0^0^0^1^2^1^1^1; detailCookie=null");
			conn.ignoreContentType(true);
			conn.timeout(30000);
			return conn;
		}
	};

	private static final ThreadLocal<Connection> egoBetConnBean = new ThreadLocal() {
		protected Connection initialValue() {
			Connection conn = Jsoup.connect("http://103.24.227.83/zh/sport-live-bets.html")
					.header("Host", "103.24.227.83").referrer("http://103.24.227.83/zh/sport-live-bets.html")
					.header("Host", "http://103.24.227.83").header("X-Requested-With", "XMLHttpRequest")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")
					.header("Accept-Encoding", "gzip, deflate, sdch")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
					.header("accept", "application/json, text/javascript, */*; q=0.01").ignoreContentType(true)
					.timeout(30000);

			return conn;
		}
	};

	private static final ThreadLocal<Connection> live1ConnBean = new ThreadLocal() {
		protected Connection initialValue() {
			Connection conn = Jsoup.connect("http://live.13322.com/").header("Host", "http://live.13322.com/")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")
					.header("Accept-Encoding", "gzip, deflate, sdch")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
					.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					.ignoreContentType(true).timeout(30000);

			return conn;
		}
	};

	private static final ThreadLocal<Connection> live1ScoreConnBean = new ThreadLocal() {
		protected Connection initialValue() {
			Connection conn = Jsoup.connect("http://live.13322.com/").header("Host", "http://live.13322.com/")
					.header("Origin", "http://live.13322.com").header("Referer", "http://live.13322.com/")
					.header("X-Requested-With", "XMLHttpRequest")
					.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")
					.header("Accept-Encoding", "gzip, deflate, sdch")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
					.header("accept", "application/json, text/javascript, */*; q=0.01").ignoreContentType(true)
					.timeout(30000);

			return conn;
		}
	};

	private static final ThreadLocal<Connection> jinBBConnBean = new ThreadLocal() {
		protected Connection initialValue() {
			Connection conn = Jsoup.connect("http://103.24.227.83/zh/sport-live-bets.html")
					.header("Host", "sports.1eighty8.com")
					.referrer(
							"http://sports.1eighty8.com/zh-cn/sports/all/in-play/full-time-asian-handicap-and-over-under?theme=black&q=&country=CN&currency=RMB&tzoff=-240")
					.header("X-Requested-With", "XMLHttpRequest")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36")
					.header("Accept-Encoding", "gzip, deflate").header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
					.header("accept", "*/*").header("Connection", "keep-alive")
					.header("Origin", "http://sports.1eighty8.com")
					.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
					.header("Cookie",
							"ASP.NET_SessionId=5rylnuccziyxrhfrq1xsnj0t; sb2188cash=35654410.20480.0000; settingProfile=OddsType=2&NoOfLinePerEvent=1&SortBy=1&AutoRefreshBetslip=True; CCDefaultBgPlay=eventId%3D1417844%26lsId%3D%26aTeamName%3D%E9%98%BF%E9%A9%AC%E5%8D%A1%26hTeamName%3D%E5%AE%89%E8%8A%9D%26sportId%3D1%26lang%3Dzh-cn%26vidoProvider%3Dp; CCEnlargeStatus=true; CCCurrentMbPlay=eventId%3D1417844%26lsId%3D%26aTeamName%3D%E9%98%BF%E9%A9%AC%E5%8D%A1%26hTeamName%3D%E5%AE%89%E8%8A%9D%26sportId%3D1%26lang%3Dzh-cn%26vidoProvider%3Di; mc=; HighlightedSport=ALL|False; timeZone=480; BS@Cookies=%23%23%23%23%23%23%23%23%23%23%23; fav3=; favByBetType=; selAllComps=false")
					.ignoreContentType(true).timeout(30000);

			return conn;
		}
	};

	public static Connection getJinBBConn() {
		return (Connection) jinBBConnBean.get();
	}

	public static void setJinBBConn(Connection connection) {
		jinBBConnBean.set(connection);
	}

	public static Connection getEgoBetConn() {
		return (Connection) egoBetConnBean.get();
	}

	public static void setEgoBetConn(Connection connection) {
		egoBetConnBean.set(connection);
	}

	public static Connection getLive1Conn() {
		return (Connection) live1ConnBean.get();
	}

	public static void setLive1Conn(Connection connection) {
		live1ConnBean.set(connection);
	}

	public static Connection getLive1ScoreConn() {
		return (Connection) live1ScoreConnBean.get();
	}

	public static void setLive1ScoreConn(Connection connection) {
		live1ScoreConnBean.set(connection);
	}

	public static Document crawlPage(String url) throws IOException {
		int times = 0;
		while (times++ < 3) {
			try {
				return getConn().url(url).get();
			} catch (IOException e) {
				if (times == 3) {
					throw e;
				}
			}
		}
		return null;
	}

	public static Connection getConn() {
		return (Connection) connBean.get();
	}

	public static void setConn(Connection connection) {
		connBean.set(connection);
	}

	public static void remove() {
		connBean.remove();
	}
}