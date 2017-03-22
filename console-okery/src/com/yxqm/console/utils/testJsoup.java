package com.yxqm.console.utils;

import java.io.IOException;
import java.io.PrintStream;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class testJsoup {
	public static void main(String[] args) throws IOException {
		String unsafe = "不安全网站地址";
		String safe = Jsoup.clean(unsafe, Whitelist.basic());

		Document doc = Jsoup.connect("http://www.310win.com/tag/jingcailanqiutuijian/P3/").data("query", "Java")
				.userAgent("Mozilla").cookie("auth", "token").timeout(3000).post();

		String url = "http://www.310win.com/jingcaizuqiu/info_t1sub2page2.html";
		String[] split = url.split("/");
		String split_string = split[(split.length - 1)];

		int page_count = split_string.lastIndexOf("page");
		int count = split_string.indexOf(".");
		String substring = split_string.substring(page_count + 4, count);

		Elements htbList = doc.getElementsByClass("htbList");

		Elements tr = htbList.select("tr");
		for (int i = 0; i < tr.size(); i++)
			if (i % 2 == 0) {
				Elements element = tr.get(i).select("a");

				if (element.size() == 2) {
					String match_name = element.get(0).text();
					System.err.println(match_name);

					String match_title = element.get(1).text();
					System.err.println(match_title);
					Elements select = element.get(1).select("[href]");

					String href_url = select.attr("abs:href");
					Document document = Jsoup.connect(href_url).data("query", "Java").userAgent("Mozilla")
							.cookie("auth", "token").timeout(3000).post();
					String text = document.getElementsByClass("articleContent").text();
					System.err.println("摘要：" + text);
					System.err.println(document);
					Elements articleContent = document.getElementsByClass("articleContent").select("p");
					String match_time = articleContent.get(2).text().split("：")[1];
					String push_result = articleContent.get(3).text().split("：")[1];
					String text4 = articleContent.get(4).text();
					String text5 = articleContent.get(5).text();
					String text6 = articleContent.get(6).text();
					String text7 = articleContent.get(7).text();
					String text8 = articleContent.get(8).text();
					String match_desc = text4 + text5 + text6 + text7 + text8;
					System.err.println(match_time);
					System.err.println(push_result);
					System.err.println(match_desc);
				}
			}
	}

	private static Element getElementsByClass(String string) {
		return null;
	}

	private static void print(String msg, Object[] args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width) {
			return s.substring(0, width - 1) + ".";
		}
		return s;
	}
}