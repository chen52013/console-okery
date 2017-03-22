package com.yxqm.console.web.action.inter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.taobao.tair.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxqm.console.utils.SendMail;
import com.yxqm.console.web.bean.FootballMatchBean;
import com.yxqm.console.web.bean.InterBean;
import com.yxqm.console.web.bean.MatchBean;
import com.yxqm.console.web.bussiness.IInterService;
import com.yxqm.console.web.context.CustomPropertyPlaceholderConfigurer;

/**
 * 抓取彩球数据推荐
 */
@Controller
public class MatchAction {

	private static final Logger LOG = LoggerFactory.getLogger(MatchAction.class);

	@Autowired
	@Qualifier("interService")
	IInterService interService;

	/**
	 * 彩球数据抓取
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toJsoupList.do", method = RequestMethod.GET)
	public String toJsoupList(Model model) {
		return "caiball/jsoup";
	}

	@RequestMapping(value = "/toJsoupMatch.do", method = RequestMethod.GET)
	public String toJsoupMatch(Model model) {
		return "caiball/jsoupMatch";
	}

	/**
	 * 赛事数据推送
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toMatchPush.do", method = RequestMethod.GET)
	public String toMatchPush(Model model) {
		return "match/matchPush";
	}

	@RequestMapping(value = "goJsoup.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> goJsoup(@ModelAttribute InterBean interBean, HttpServletRequest request) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String inter_url = request.getParameter("inter_url");
		String unsafe = "不安全网站地址";
		String safe = Jsoup.clean(unsafe, Whitelist.basic());
		Document doc = null;
		int count = 0;
		InterBean intBean = new InterBean();
//		List<InterBean> lst = interService.queryInterList(interBean);
		try {
			//从一个网站获取和解析一个HTML文档 html源代码
			doc = Jsoup.connect(inter_url).get();
			//查找超链接a
			Elements links = doc.select("a[href]");
			print("\nlinks: (%d)个", links.size());
			for (Element link : links) {
				if (StringUtils.isNotBlank(link.attr("abs:href"))) {
					InterBean bean = new InterBean();
					bean.setInter_url(link.attr("abs:href"));
					int listRows = interService.queryInterListRows(bean);
					if (listRows <= 0) {
						intBean.setInter_url(link.attr("abs:href"));
						intBean.setInter_name(trim(link.text(), 35));
						intBean.setInter_desc(trim(link.text(), 100));
						count = interService.addInter(intBean);
					}
				}
				print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (count >= 1) {
			resMap.put("res_code", "1");
			resMap.put("res_msg", "抓取成功！");
		} else {
			resMap.put("res_code", "0");
			resMap.put("res_msg", "抓取失败！");
		}
		return resMap;
	}

	@RequestMapping(value = "jsoupMatch.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> jsoupMatch(@ModelAttribute MatchBean matchBean, HttpServletRequest request) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String jsoup_url = request.getParameter("jsoup_url");
		String unsafe = "不安全网站地址";
		String safe = Jsoup.clean(unsafe, Whitelist.basic());
		Document doc = null;
		int count = 0;
		MatchBean mBean = new MatchBean();
		List<MatchBean> matchList = interService.queryMatchList(matchBean);
		try {
			doc = Jsoup.connect(jsoup_url).data("query", "Java").userAgent("Mozilla").cookie("auth", "token").timeout(3000).post();
			Elements pages = doc.getElementsByClass("AspNetPager");
			String page_href = pages.get(pages.size() - 1).attr("abs:href");
			//http://www.310win.com/jingcaizuqiu/info_t1sub2page2.html
			//http://www.310win.com/tag/jingcailanqiutuijian/P1/
			String sumPage = "";
			if (page_href.contains(".html")) {
				String[] split = page_href.split("/");
				String split_string = split[split.length - 1];
				//info_t1sub2page2.html
				int page_count = split_string.indexOf("page");
				int _count = split_string.indexOf(".");
				sumPage = split_string.substring(page_count + 4, _count);
			} else {
				String[] split = page_href.split("/");
				sumPage = split[split.length - 1].substring(1);
			}
			for (int k = 1; k <= Integer.parseInt(sumPage); k++) {
				if (page_href.contains(".html")) {
					jsoup_url = "http://www.310win.com/jingcaizuqiu/info_t1sub2page";
					doc = Jsoup.connect(jsoup_url + k + ".html").data("query", "Java").userAgent("Mozilla").cookie("auth", "token").timeout(3000).post();
				} else {
					doc = Jsoup.connect(jsoup_url + "P" + k + "/").data("query", "Java").userAgent("Mozilla").cookie("auth", "token").timeout(3000).post();
				}
				//获取table
				Elements htbList = doc.getElementsByClass("htbList");
				//获取所有tr 20行
				Elements tr = htbList.select("tr");
				boolean is_true = false;
				for (int i = 0; i < tr.size(); i++) {
					if (i % 2 == 0) {
						Elements element = tr.get(i).select("a");
						//String match_name = element.getElementsByClass("olp").text();
						if (element.size() == 2) {
							//推荐联赛
							String match_name = element.get(0).text();
							if (StringUtils.isNotBlank(match_name)) {
								mBean.setMatch_name(match_name);
							}
							String match_title = element.get(1).text();
							//比赛标题去重
							for (int j = 0; j < matchList.size(); j++) {
								if (StringUtils.isNotBlank(match_title)) {
									if (!match_title.contains(matchList.get(j).getMatch_title()) && !matchList.get(j).getMatch_title().contains(match_title)) {
										mBean.setMatch_title(match_title);
									} else {
										is_true = true;
										break;
									}
								}
							}
							if (is_true) {
								continue;
							}
							Elements select = element.get(1).select("[href]");
							////比赛超链接
							String href_url = select.attr("abs:href");
							Document document = Jsoup.connect(href_url).data("query", "Java").userAgent("Mozilla").cookie("auth", "token").timeout(3000).post();
							String match_summary = document.getElementsByClass("aBrief").text();
							if (StringUtils.isNotBlank(match_summary)) {
								mBean.setMatch_summary(match_summary);
							}
							Elements articleContent = null;
							int p_size = document.getElementsByClass("articleContent").select("p").size();
							int div_size = document.getElementsByClass("articleContent").select("div").size();
							if (p_size > div_size) {
								articleContent = document.getElementsByClass("articleContent").select("p");
							} else {
								articleContent = document.getElementsByClass("articleContent").select("div");
							}
							String match_time = null;
							if (articleContent.get(2).text().split("：").length > 0) {
								match_time = articleContent.get(2).text().split("：")[articleContent.get(2).text().split("：").length - 1];
							}
							if (StringUtils.isNotBlank(match_time)) {
								mBean.setMatch_time(match_time);
							}
							String push_result = null;
							if (articleContent.get(3).text().split("：").length > 0) {
								push_result = articleContent.get(3).text().split("：")[articleContent.get(3).text().split("：").length - 1];
							}
							if (StringUtils.isNotBlank(push_result)) {
								mBean.setPush_result(push_result);
							}
							int size = articleContent.size();
							String match_desc = null;
							for (int j = 4; j < size - 1; j++) {
								match_desc += articleContent.get(j).text();
							}
							if (StringUtils.isNotBlank(match_desc)) {
								if (match_desc.length() >= 2000) {
									for (int j = 0; j < matchList.size(); j++) {
										if (!match_desc.contains(matchList.get(j).getMatch_desc()) && !matchList.get(j).getMatch_desc().contains(match_desc)) {
											match_desc = match_desc.substring(0, 2000);
										} else {
											is_true = true;
											break;
										}
									}
								}
								mBean.setMatch_desc(match_desc);
							}
							if (is_true) {
								continue;
							}
							count = interService.addMatch(mBean);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (count >= 1) {
			resMap.put("res_code", "1");
			resMap.put("res_msg", "抓取成功！");
		} else {
			resMap.put("res_code", "0");
			resMap.put("res_msg", "抓取失败！");
		}
		return resMap;
	}

	@RequestMapping(value = "sendEmail.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> sendEmail(@ModelAttribute InterBean interBean, HttpServletRequest request) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		String mailName = CustomPropertyPlaceholderConfigurer.getProperty("mail.configuration.name");
		String mailPassWord = CustomPropertyPlaceholderConfigurer.getProperty("mail.configuration.password");
		String mailSmtpHost = CustomPropertyPlaceholderConfigurer.getProperty("mail.smtp.host");
		String mailSmtpAuth = CustomPropertyPlaceholderConfigurer.getProperty("mail.smtp.auth");
		String mailSmtpSocketFactoryClass = CustomPropertyPlaceholderConfigurer.getProperty("mail.smtp.socketFactory.class");
		String mailSmtpPort = CustomPropertyPlaceholderConfigurer.getProperty("mail.smtp.port");
		String mailSmtpSocketFactoryPort = CustomPropertyPlaceholderConfigurer.getProperty("mail.smtp.socketFactory.port");
		String mailContent = "";
		String mailTitle = "";
		String mailFrom = CustomPropertyPlaceholderConfigurer.getProperty("mail.configuration.from");
		mailTitle = request.getParameter("match_title");
		mailContent = request.getParameter("match_desc");
		if (StringUtils.isNotBlank(mailContent)) {
			Map<String, String> map = new HashMap<String, String>();
			SendMail mail = new SendMail(mailName, mailPassWord);
			map.put("mail.smtp.host", mailSmtpHost);
			map.put("mail.smtp.auth", mailSmtpAuth);
			map.put("mail.smtp.socketFactory.class", mailSmtpSocketFactoryClass);
			map.put("mail.smtp.port", mailSmtpPort);
			map.put("mail.smtp.socketFactory.port", mailSmtpSocketFactoryPort);
			mail.setPros(map);
			mail.initMessage();
			List<String> list = new ArrayList<String>();
			String target_url = CustomPropertyPlaceholderConfigurer.getProperty("target.email.url");
			String[] split = target_url.split(",");
			for (int i = 0; i < split.length; i++) {
				list.add(split[i]);
			}
			try {
				mail.setRecipients(list);
				mail.setSubject(mailTitle);
				mail.setDate(new Date());
				try {
					mail.setFrom(mailFrom);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				mail.setContent(mailContent, "text/html; charset=UTF-8");
				System.out.println(mail.sendMessage());
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			resMap.put("res_code", "1");
			resMap.put("res_msg", "推送邮件成功");
		} else {
			resMap.put("res_code", "0");
			resMap.put("res_msg", "推送邮件失败,数据为空！");
		}
		return resMap;
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

	@RequestMapping(value = "queryMatchList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> queryMatchList(@ModelAttribute MatchBean matchBean, HttpServletRequest request) {
		int totalRows = interService.queryMatchListRows(matchBean);
		List<MatchBean> lst = interService.queryMatchList(matchBean);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("totalRows", totalRows);
		resMap.put("curPage", matchBean.getCurPage());
		resMap.put("data", lst);
		return resMap;
	}

	@RequestMapping(value = "queryFootballMatchList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> queryFootballMatchList(@ModelAttribute FootballMatchBean matchBean, HttpServletRequest request) {
		int endPage = 0;
		String curPage = request.getParameter("curPage");
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isEmpty(pageSize)) {
			pageSize = "10";
		}
		endPage = Integer.parseInt(pageSize);
		int beginPage = 0;
		if (StringUtils.isNotEmpty(curPage)) {
			beginPage = ((Integer.parseInt(curPage) - 1)) * Integer.parseInt(pageSize);
		}
		matchBean.setPageSize(endPage);
		matchBean.setCurPage(beginPage);
		int totalRows = interService.queryFootballMatchListRows(matchBean);
		List<FootballMatchBean> lst = interService.queryFootballMatchList(matchBean);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("totalRows", totalRows);
		resMap.put("curPage", matchBean.getCurPage());
		resMap.put("data", lst);
		return resMap;
	}





	@RequestMapping(value = "queryInterList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> queryInterList(@ModelAttribute InterBean interBean, HttpServletRequest request) {
		int totalRows = interService.queryInterListRows(interBean);
		List<InterBean> lst = interService.queryInterList(interBean);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("totalRows", totalRows);
		resMap.put("curPage", interBean.getCurPage());
		resMap.put("data", lst);
		return resMap;
	}

	@RequestMapping(value = "queryMatchScoreList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> queryMatchScoreList(@ModelAttribute FootballMatchBean matchBean, HttpServletRequest request) {
		List<FootballMatchBean> lst = interService.queryMatchScoreList(matchBean);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("data", lst);
		return resMap;
	}

	@RequestMapping(value = "queryMatchTimeList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> queryMatchTimeList(@ModelAttribute FootballMatchBean matchBean, HttpServletRequest request) {
		List<FootballMatchBean> lst = interService.queryMatchTimeList(matchBean);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("data", lst);
		return resMap;
	}

	@RequestMapping(value = "queryMatchScoreTimeList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> queryMatchScoreTimeList(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		String start_time = request.getParameter("start_time");
		if (StringUtils.isNotBlank(start_time)) {
			param.put("start_time", start_time);
		}
		String end_time = request.getParameter("end_time");
		if (StringUtils.isNotBlank(end_time)) {
			param.put("end_time", end_time);
		}
		List<Map<String, Object>> lst = interService.queryMatchScoreTimeList(param);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("data", lst);
		return resMap;
	}

	@RequestMapping(value = "queryMatchKeliList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> queryMatchKeliList(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		String match_id = request.getParameter("match_id");
		if (StringUtils.isNotBlank(match_id)) {
			param.put("match_id", match_id);
		}
		List<Map<String, Object>> lst = interService.queryMatchKeliList(param);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("data", lst);
		return resMap;
	}

	@RequestMapping(value = "toMatchScore.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String toMatchScore(Model model) {
		return "match/matchScore";
	}

	@RequestMapping(value = "toMatchKeli.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String toMatchKeli(Model model) {
		return "match/matchKeli";
	}

	@RequestMapping(value = "toFootballMatch.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String toFootballMatch(Model model) {
		return "match/footballMatch";
	}





	@RequestMapping(value = "addInter.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> addInter(@ModelAttribute InterBean interBean) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		int res = interService.addInter(interBean);
		if (res == 0) {
			resMap.put("res_code", "0");
			resMap.put("res_msg", "添加收藏失败");

		} else {
			resMap.put("res_code", "1");
			resMap.put("res_msg", "添加收藏成功");
		}

		return resMap;
	}
}