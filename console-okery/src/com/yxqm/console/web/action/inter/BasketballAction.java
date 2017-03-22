package com.yxqm.console.web.action.inter;

import com.alibaba.fastjson.JSONObject;
import com.taobao.tair.json.JSONArray;
import com.yxqm.console.web.bean.BasketballMatchBean;
import com.yxqm.console.web.bussiness.IBasketballService;
import org.apache.commons.lang3.StringUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 篮球先锋
 * Created by Dell on 2017/3/16.
 */
@Controller
public class BasketballAction {

	private static final Logger LOG = LoggerFactory.getLogger(BasketballAction.class);

	@Autowired
	@Qualifier("basketballService")
	IBasketballService basketballService;

	@RequestMapping(value = "toBasketballMatch.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String toBasketballMatch(Model model) {
		return "basketball/basketballMatch";
	}

	@RequestMapping(value = "toEditBasketballMatch.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String toEditBasketballMatch(Model model) {
		return "basketball/editBasketballMatch";
	}

	@RequestMapping(value = "toPkBasketballMatch.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String toPkBasketballMatch(Model model) {
		return "basketball/pkBasketballMatch";
	}

	/**
	 * 查询联赛列表
	 * @param matchBean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryBasketballLeagueList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody
	Map<String, Object> queryBasketballLeagueList(@ModelAttribute BasketballMatchBean matchBean, HttpServletRequest request) {
		List<BasketballMatchBean> lst = basketballService.queryBasketBallLeagueList(matchBean);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("data", lst);
		return resMap;
	}

	/**
	 * 查询篮球先锋列表
	 * @param matchBean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryBasketballMatchList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> queryBasketballMatchList(@ModelAttribute BasketballMatchBean matchBean, HttpServletRequest request) {
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
		int totalRows = basketballService.queryBasketballMatchListRows(matchBean);
		List<BasketballMatchBean> lst = basketballService.queryBasketballMatchList(matchBean);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("totalRows", totalRows);
		resMap.put("curPage", curPage);
		resMap.put("data", lst);
		return resMap;
	}

	@RequestMapping(value = "editBasketballMatch.do", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Map<String, Object> editBasketballMatch(@ModelAttribute BasketballMatchBean basketballMatchBean,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		int affectedRows = basketballService.editBasketballMatch(basketballMatchBean);
		if (affectedRows == 0) {
			resMap.put("res_code", "0");
			resMap.put("res_msg", "比赛推荐修改失败");
		} else {
			resMap.put("res_code", "1");
			resMap.put("res_msg", "比赛推荐修改成功");
		}
		return resMap;
	}

	@RequestMapping(value = "queryTeamDetailList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> queryTeamDetailList(@ModelAttribute BasketballMatchBean matchBean, HttpServletRequest request) {
		String home_id = request.getParameter("home_id");
		if(StringUtils.isNotBlank(home_id)){
			matchBean.setHome_id(home_id);
		}
		String guest_id = request.getParameter("guest_id");
		if(StringUtils.isNotBlank(guest_id)){
			matchBean.setGuest_id(guest_id);
		}
		String league_name = request.getParameter("league_name");
		if(StringUtils.isNotBlank(league_name)){
			matchBean.setLeague_name(league_name);
		}
		String start_time = request.getParameter("start_time");
		if(StringUtils.isNotBlank(start_time)){
			matchBean.setStart_time(start_time);
		}
		String end_time = request.getParameter("end_time");
		if(StringUtils.isNotBlank(end_time)){
			matchBean.setEnd_time(end_time);
		}
		String count = request.getParameter("count");
		if(StringUtils.isNotBlank(count)){
			matchBean.setCount(Integer.parseInt(count));
		}
		List<BasketballMatchBean> lst = basketballService.queryTeamDetailList(matchBean);
		JSONArray jsonA = new JSONArray();
		for(BasketballMatchBean fBean:lst){
			JSONObject jo = new JSONObject();
			/**
			 * 若home_id不为空  则说明是主队 存储主队得分 反之亦然
			 * 若let_points为胜 则说明主队胜
			 */
			if(StringUtils.isNotBlank(home_id)){
				jo.put("match_score",fBean.getHome_score());
				if(fBean.getLet_points() == "胜" || fBean.getLet_points().equals("胜")){
					jo.put("let_points_num",100);
				}else{
					jo.put("let_points_num",50);
				}
				if(fBean.getOutcome() == "胜" || fBean.getLet_points().equals("胜")){
					jo.put("out_com_num",100);
				}else{
					jo.put("out_com_num",50);
				}
			}else{
				jo.put("match_score",fBean.getGuest_score());
				if(fBean.getLet_points() == "胜" || fBean.getLet_points().equals("胜")){
					jo.put("let_points_num",50);
				}else{
					jo.put("let_points_num",100);
				}
				if(fBean.getOutcome() == "胜" || fBean.getLet_points().equals("胜")){
					jo.put("out_com_num",50);
				}else{
					jo.put("out_com_num",100);
				}
			}
			if(fBean.getBig_small_score().contains("大")){
				jo.put("big_small_score",200);
			}else if(fBean.getBig_small_score().contains("小")){
				jo.put("big_small_score",100);
			}
			//match_score:'球队得分',outcome:'比赛胜负',let_points:'让分胜负',big_small_score:'大小分'--(192.50)小,result_score:'比赛总分'
			jo.put("let_points",fBean.getLet_points());
			jo.put("out_come",fBean.getOutcome());
			jo.put("result_score",fBean.getBig_small_score().substring(1,7));

			jo.put("match_time",fBean.getMatch_time());
			jsonA.add(jo);
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("data", jsonA);
		return resMap;
	}

	/**
	 *
	 * @param matchBean
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryBasketballTeamList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> queryBasketballTeamList(@ModelAttribute BasketballMatchBean matchBean, HttpServletRequest request) {
		List<BasketballMatchBean> lst = basketballService.queryBasketballTeamList(matchBean);
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("data", lst);
		return resMap;
	}

	/**
	 * 比赛走势图页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toGoTeamDetail.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String toGoTeamDetail(Model model ,HttpServletRequest request) {
		BasketballMatchBean matchBean = new BasketballMatchBean();
		String home_id = request.getParameter("home_id");
		if(StringUtils.isNotBlank(home_id)){
			matchBean.setHome_id(home_id);
			request.setAttribute("home_id", home_id);
		}
		String guest_id = request.getParameter("guest_id");
		if(StringUtils.isNotBlank(guest_id)){
			matchBean.setGuest_id(guest_id);
			request.setAttribute("guest_id", guest_id);
		}
		String league_name = request.getParameter("league_name");
		if(StringUtils.isNotBlank(league_name)){
			matchBean.setLeague_name(league_name);
			request.setAttribute("league_name", league_name);
		}
		matchBean.setCurPage(0);
		matchBean.setPageSize(1);
		List<BasketballMatchBean> lst = basketballService.queryBasketballMatchList(matchBean);
		if(lst.size() > 0 && lst != null){
			if(lst.get(0).getGuest_id() == guest_id || lst.get(0).getGuest_id().equals(guest_id)){
				request.setAttribute("guest_team", lst.get(0).getGuest_team());
			}
			if(lst.get(0).getHome_id() == home_id || lst.get(0).getHome_id().equals(home_id)){
				request.setAttribute("home_team", lst.get(0).getHome_team());
			}
		}
		return "match/teamDetail";
	}

	@RequestMapping(value = "queryBasketballMatchDetailList.do", method = {RequestMethod.GET, RequestMethod.POST})
	public
	@ResponseBody
	Map<String, Object> queryBasketballMatchDetailList(@ModelAttribute BasketballMatchBean matchBean, HttpServletRequest request) {
		String is_true = request.getParameter("is_true");
		String team_id = "",home_id = "",guest_id = "";
		if(StringUtils.isNotBlank(is_true) && (is_true == "true" || is_true.equals("true"))){
			home_id = request.getParameter("home_id");
			if(StringUtils.isNotBlank(home_id)){
				matchBean.setHome_id(home_id);
			}
			guest_id = request.getParameter("guest_id");
			if(StringUtils.isNotBlank(guest_id)){
				matchBean.setGuest_id(guest_id);
			}
		}else{
			team_id = request.getParameter("team_id");
			if(StringUtils.isNotBlank(team_id)){
				matchBean.setHome_id(team_id);
			}
		}
		String league_name = request.getParameter("league_name");
		if(StringUtils.isNotBlank(league_name)){
			matchBean.setLeague_name(league_name);
		}
		String match_time = request.getParameter("match_time");
		if(StringUtils.isNotBlank(match_time)){
			matchBean.setMatch_time(match_time);
		}
		//显示多少条 近10场 20场 全部
		String count = request.getParameter("count");
		if(StringUtils.isNotBlank(count)){
			matchBean.setCount(Integer.parseInt(count));
		}
		List<BasketballMatchBean> lst = basketballService.queryTeamDetailList(matchBean);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(is_true) && (is_true == "true" || is_true.equals("true"))){
			int sum_count = 0;			//比赛场次
			int h_win_count = 0;		//主胜场次
			int h_lost_count = 0;		//主负场次
			int g_win_count = 0;		//主胜场次
			int g_lost_count = 0;		//客负场次
			int h_let_win_count = 0;	//主胜场次
			int h_let_lost_count = 0;	//主负场次
			int g_let_win_count = 0;	//主胜场次
			int g_let_lost_count = 0;	//客负场次
			int big_count = 0;			//主胜场次
			int small_count = 0;			//主负场次
			if(lst.size() > 0 && lst != null) {
				sum_count = lst.size();
				for (int i = 0; i < lst.size(); i++) {
					BasketballMatchBean mBean = lst.get(i);
					if(mBean.getHome_id() == home_id || mBean.getHome_id().equals(home_id)){
						if(mBean.getOutcome() == "胜" || mBean.getOutcome().equals("胜")){
							h_win_count++;
							g_lost_count++;
						}else if(mBean.getOutcome() == "负" || mBean.getOutcome().equals("负")){
							h_lost_count++;
							g_win_count++;
						}
						if(mBean.getLet_points() == "胜" || mBean.getLet_points().equals("胜")){
							h_let_win_count++;
							g_let_lost_count++;
						}else if(mBean.getLet_points() == "负" || mBean.getLet_points().equals("负")){
							h_let_lost_count++;
							g_let_win_count++;
						}
					}else if(mBean.getGuest_id() == home_id || mBean.getGuest_id().equals(home_id)){
						if(mBean.getOutcome() == "胜" || mBean.getOutcome().equals("胜")){
							h_lost_count++;
							g_win_count++;
						}else if(mBean.getOutcome() == "负" || mBean.getOutcome().equals("负")){
							h_win_count++;
							g_lost_count++;
						}
						if(mBean.getLet_points() == "胜" || mBean.getLet_points().equals("胜")){
							h_let_lost_count++;
							g_let_win_count++;
						}else if(mBean.getLet_points() == "负" || mBean.getLet_points().equals("负")){
							h_let_win_count++;
							g_let_lost_count++;
						}
					}
					if(mBean.getBig_small_score().contains("大")){
						big_count++;
					}else if(mBean.getBig_small_score().contains("小")){
						small_count++;
					}
				}
				paramMap.put("h_win_count",h_win_count);
				paramMap.put("h_lost_count",h_lost_count);
				paramMap.put("g_win_count",g_win_count);
				paramMap.put("g_lost_count",g_lost_count);
				paramMap.put("h_let_win_count",h_let_win_count);
				paramMap.put("h_let_lost_count",h_let_lost_count);
				paramMap.put("g_let_win_count",g_let_win_count);
				paramMap.put("g_let_lost_count",g_let_lost_count);
				paramMap.put("big_count",big_count);
				paramMap.put("small_count",small_count);
			}
		}else{
			int sum_count = 0;		//比赛场次
			int sum_score = 0;		//总得分
			int avg_score = 0;		//平均分
			int win_count = 0;		//胜场次
			int lost_count = 0;		//负场次
			int let_win_count = 0;	//让胜场次
			int let_lost_count = 0;	//让负场次
			int big_count = 0;		//大分场次
			int small_count = 0;	//小分场次
			if(lst.size() > 0 && lst != null){
				sum_count = lst.size();
				for(int i=0;i<lst.size();i++){
					BasketballMatchBean mBean = lst.get(i);
					//该场比赛是客场
					if(mBean.getGuest_id() == team_id || mBean.getGuest_id().equals(team_id)){
						sum_score += Integer.parseInt(mBean.getGuest_score());
						if(mBean.getOutcome() == "胜" || mBean.getOutcome().equals("胜")){
							lost_count++;
						}else if(mBean.getOutcome() == "负" || mBean.getOutcome().equals("负")){
							win_count++;
						}
						if(mBean.getLet_points() == "胜" || mBean.getLet_points().equals("胜")){
							let_lost_count++;
						}else if(mBean.getLet_points() == "负" || mBean.getLet_points().equals("负")){
							let_win_count++;
						}
					}
					//该场比赛是主场
					if(mBean.getHome_id() == team_id || mBean.getHome_id().equals(team_id)){
						sum_score += Integer.parseInt(mBean.getHome_score());
						if(mBean.getOutcome() == "胜" || mBean.getOutcome().equals("胜")){
							win_count++;
						}else if(mBean.getOutcome() == "负" || mBean.getOutcome().equals("负")){
							lost_count++;
						}
						if(mBean.getLet_points() == "胜" || mBean.getLet_points().equals("胜")){
							let_win_count++;
						}else if(mBean.getLet_points() == "负" || mBean.getLet_points().equals("负")){
							let_lost_count++;
						}
					}
					if(mBean.getBig_small_score().contains("大")){
						big_count++;
					}else if(mBean.getBig_small_score().contains("小")){
						small_count++;
					}
				}
				avg_score = sum_score / sum_count;
				paramMap.put("avg_score",avg_score);
				paramMap.put("win_count",win_count);
				paramMap.put("lost_count",lost_count);
				paramMap.put("let_win_count",let_win_count);
				paramMap.put("let_lost_count",let_lost_count);
				paramMap.put("big_count",big_count);
				paramMap.put("small_count",small_count);
			}
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("success", "true");
		resMap.put("data", paramMap);
		return resMap;
	}
}
