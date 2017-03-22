package com.yxqm.console.web.action.inter;

import com.yxqm.console.utils.JsoupUtil;
import com.yxqm.console.utils.WordUtil;
import com.yxqm.console.web.bean.KeywordBean;
import com.yxqm.console.web.bussiness.IKeywordService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dell on 2017/2/27.
 */
@Controller
public class KeywordAction {
	private static final Logger LOG = LoggerFactory.getLogger(MatchAction.class);

	@Autowired
	@Qualifier("keywordService")
	IKeywordService keywordService;

	// 获取关键字个数
	private final static Integer NUM=5;

	//截取关键字在几个单词以上的数量
	private final static Integer QUANTITY=1;

	/**
	 * 跳转到网页大全页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toKeyword.do", method = RequestMethod.GET)
	public String toKeyword(Model model) {
		return "keyword/keyword";
	}

	/**
	 * 查询网页大全收藏地址
	 * @param keywordBean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryKeyWordList.do", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	Map<String,Object> queryKeyWordList(@ModelAttribute KeywordBean keywordBean, HttpServletRequest request){
		List<KeywordBean> lst = keywordService.queryKeyWordList(keywordBean);
		int totalRows = keywordService.queryKeyWordListRows(keywordBean);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("totalRows", totalRows);
		resMap.put("curPage", keywordBean.getCurPage());
		resMap.put("data", lst);
		return resMap;
	}

	/**
	 * 添加网页收藏地址
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toAddKeyword.do", method = RequestMethod.GET)
	public String toAddKeyword(Model model) {
		return "keyword/addKeyword";
	}

	/**
	 * 获取网页关键字
	 * @param keywordBean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getKeyWordList.do", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	Map<String,Object> getKeyWordList(@ModelAttribute KeywordBean keywordBean, HttpServletRequest request){
		String keyword_url = request.getParameter("keyword_url");
		Map<String, Object> resMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(keyword_url)) {
			try {
				Document doc = JsoupUtil.crawlPage(keyword_url);
				String title = doc.select("title").get(0).html();
				if(StringUtils.isNotBlank(title)){
					resMap.put("title",title);
					String [] keywords = WordUtil.getKeyWords(title);
					String keyword = "";
					for(int i=0; i<keywords.length; i++){
						keyword = keyword + keywords[i] + ",";
					}
					resMap.put("keyword",keyword);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else{
			LOG.debug("url地址为空！");
		}
		resMap.put("success", "true");
		return resMap;
	}

	@RequestMapping(value = "addKeyword.do", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody
	Map<String,Object> addKeyword(@ModelAttribute KeywordBean keywordBean, HttpServletRequest request){
		Map<String, Object> resMap = new HashMap<String, Object>();
		request.getParameter("type_name");
		int res = keywordService.addKeyword(keywordBean);
		if(res == 0 ){
			resMap.put("res_code", "0");
			resMap.put("res_msg", "添加收藏失败");

		}else{
			resMap.put("res_code", "1");
			resMap.put("res_msg", "添加收藏成功");
		}
		return resMap;
	}

}
