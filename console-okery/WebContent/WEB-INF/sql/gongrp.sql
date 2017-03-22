-- --------------------------------------------------------
-- 主机:                           localhost
-- 服务器版本:                        5.7.14-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 grp 的数据库结构
CREATE DATABASE IF NOT EXISTS `grp` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `grp`;


-- 导出  表 grp.t_account 结构
CREATE TABLE IF NOT EXISTS `t_account` (
  `id` int(11) NOT NULL,
  `channel_no` int(11) NOT NULL COMMENT '渠道id',
  `money` double DEFAULT NULL COMMENT '交易金额',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户流水信息';

-- 正在导出表  grp.t_account 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_account` ENABLE KEYS */;


-- 导出  表 grp.t_channel 结构
CREATE TABLE IF NOT EXISTS `t_channel` (
  `id` int(11) NOT NULL,
  `channel_name` varchar(50) NOT NULL COMMENT '渠道名',
  `crt_time` datetime DEFAULT NULL COMMENT '创建时间',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道表';

-- 正在导出表  grp.t_channel 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_channel` DISABLE KEYS */;
INSERT INTO `t_channel` (`id`, `channel_name`, `crt_time`, `mod_time`) VALUES
	(1, '新闻', '2016-11-07 10:08:27', '2016-11-07 10:08:28');
/*!40000 ALTER TABLE `t_channel` ENABLE KEYS */;


-- 导出  表 grp.t_inter 结构
CREATE TABLE IF NOT EXISTS `t_inter` (
  `inter_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inter_status` int(11) DEFAULT '10',
  `inter_url` varchar(100) NOT NULL,
  `inter_desc` varchar(500) DEFAULT NULL,
  `inter_crt_time` datetime DEFAULT NULL,
  `inter_mod_time` datetime DEFAULT NULL,
  `inter_name` varchar(50) DEFAULT NULL,
  `inter_type` int(11) DEFAULT '1',
  PRIMARY KEY (`inter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

-- 正在导出表  grp.t_inter 的数据：~61 rows (大约)
/*!40000 ALTER TABLE `t_inter` DISABLE KEYS */;
INSERT INTO `t_inter` (`inter_id`, `inter_status`, `inter_url`, `inter_desc`, `inter_crt_time`, `inter_mod_time`, `inter_name`, `inter_type`) VALUES
	(1, 10, 'http://3g.caiqr.com/football_index', '彩球网', '2016-11-07 10:08:49', '2016-11-07 10:08:51', '彩球网', 1),
	(14, 10, 'http://m.caiqr.com/activity/receiveTemplate/index.html', NULL, '2016-11-07 10:20:02', '2016-11-07 10:20:02', NULL, 1),
	(15, 10, 'http://m.caiqr.com/share.html', NULL, '2016-11-07 10:20:03', '2016-11-07 10:20:03', NULL, 1),
	(16, 10, 'http://3g.caiqr.com/sporterymatches/?sporttery_id=2016-11-07', '竞彩推荐', '2016-11-07 10:20:03', '2016-11-07 10:20:03', '竞彩推荐', 1),
	(17, 10, 'http://3g.caiqr.com/allmatches/?football_all_id=2016-11-07', '全部预测', '2016-11-07 10:20:03', '2016-11-07 10:20:03', '全部预测', 1),
	(18, 10, 'http://3g.caiqr.com/recommendmatches/?football_recommend_id=2016_11_07', '精选比赛', '2016-11-07 10:20:03', '2016-11-07 10:20:03', '精选比赛', 1),
	(19, 10, 'http://3g.caiqr.com/list/living.html', '比分直播', '2016-11-07 10:20:03', '2016-11-07 10:20:03', '比分直播', 1),
	(20, 10, 'http://3g.caiqr.com/endmatches/?end_match_id=2016-11-07', '预测战绩 历史战绩最先知', '2016-11-07 10:20:03', '2016-11-07 10:20:03', '预测战绩 历史战绩最先知', 1),
	(21, 10, 'http://3g.caiqr.com/hotmatches/?football_hot_id=2016_11_07', '焦点比赛 维尔茨对阵圣保利', '2016-11-07 10:20:03', '2016-11-07 10:20:03', '焦点比赛 维尔茨对阵圣保利', 1),
	(22, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_596579.html', '德乙第12轮 明日03:15 维尔茨堡踢球者    圣保利 92% 主胜1.96平局3.00主负3.50', '2016-11-07 10:20:03', '2016-11-07 10:20:03', '德乙第12轮 明日03:15 维尔茨堡踢球者    圣保利 92% .', 1),
	(23, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_574493.html', '法乙第14轮 明日03:30 朗斯    阿雅克肖 89% 主胜1.65平局2.94主负5.50', '2016-11-07 10:20:03', '2016-11-07 10:20:03', '法乙第14轮 明日03:30 朗斯    阿雅克肖 89% 主胜1..', 1),
	(24, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_627229.html', '英足总杯第一圈 明日03:45 南港    法利特伍德 99% 主胜6.00平局4.30主负1.37', '2016-11-07 10:20:03', '2016-11-07 10:20:03', '英足总杯第一圈 明日03:45 南港    法利特伍德 99% 主胜.', 1),
	(25, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_569053.html', '巴甲第34轮 明日06:00 格雷米奥    累西腓体育 98% 主胜1.43平局3.80主负6.00', '2016-11-07 10:20:03', '2016-11-07 10:20:03', '巴甲第34轮 明日06:00 格雷米奥    累西腓体育 98% 主.', 1),
	(26, 10, 'http://3g.caiqr.com/allmatches/?football_all_id=2016-11-07', '彩球网推荐预测', '2016-11-07 10:23:21', '2016-11-07 10:23:22', '彩球网推荐预测', 1),
	(27, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_573894.html', '法甲第12轮 00:00 梅斯 圣埃蒂安 0 0 76% 主胜3.50平局3.15主负1.90', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '法甲第12轮 00:00 梅斯 圣埃蒂安 0 0 76% 主胜3.5.', 1),
	(28, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_609679.html', '葡超第10轮 00:00 莫拉伦塞 塞图巴尔 1 2 85% 主胜2.05平局2.80主负3.55', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '葡超第10轮 00:00 莫拉伦塞 塞图巴尔 1 2 85% 主胜2.', 1),
	(29, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_612638.html', '俄超第13轮 00:00 莫斯科中央陆军 阿姆卡尔 2 2 79% 主胜1.50平局3.35主负6.10', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '俄超第13轮 00:00 莫斯科中央陆军 阿姆卡尔 2 2 79% .', 1),
	(30, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_572829.html', '英超第11轮 00:30 莱切斯特城 西布罗姆维奇 1 2 94% 主胜1.52平局3.55主负5.30', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '英超第11轮 00:30 莱切斯特城 西布罗姆维奇 1 2 94% .', 1),
	(31, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_596255.html', '德甲第10轮 00:30 沙尔克04 云达不莱梅 3 1 76% 主胜1.42平局4.25主负5.30', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '德甲第10轮 00:30 沙尔克04 云达不莱梅 3 1 76% 主.', 1),
	(32, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_612640.html', '俄超第13轮 00:30 克拉斯诺达尔 奥伦堡加索维克 3 3 83% 主胜1.43平局3.55主负6.75', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '俄超第13轮 00:30 克拉斯诺达尔 奥伦堡加索维克 3 3 83.', 1),
	(33, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_610488.html', '意甲第12轮 01:00 国际米兰 克罗托内 3 0 95% 主胜1.14平局5.95主负12.00', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '意甲第12轮 01:00 国际米兰 克罗托内 3 0 95% 主胜1.', 1),
	(34, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_610486.html', '意甲第12轮 01:00 佛罗伦萨 桑普多利亚 1 1 95% 主胜1.39平局4.05主负6.15', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '意甲第12轮 01:00 佛罗伦萨 桑普多利亚 1 1 95% 主胜.', 1),
	(35, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_561812.html', '挪超第30轮 01:00 海于格松 斯特罗姆加斯特 1 1 60% 主胜1.90平局3.75主负3.00', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '挪超第30轮 01:00 海于格松 斯特罗姆加斯特 1 1 60% .', 1),
	(36, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_561813.html', '挪超第30轮 01:00 利勒斯特罗姆 莫尔德 1 0 80% 主胜2.80平局3.55主负2.04', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '挪超第30轮 01:00 利勒斯特罗姆 莫尔德 1 0 80% 主胜.', 1),
	(37, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_561814.html', '挪超第30轮 01:00 罗森博格 博德闪耀 2 1 95% 主胜1.32平局4.80主负6.10', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '挪超第30轮 01:00 罗森博格 博德闪耀 2 1 95% 主胜1.', 1),
	(38, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_561811.html', '挪超第30轮 01:00 布兰 萨尔普斯堡08 2 1 91% 主胜1.67平局3.70主负3.85', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '挪超第30轮 01:00 布兰 萨尔普斯堡08 2 1 91% 主胜.', 1),
	(39, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_561815.html', '挪超第30轮 01:00 松达尔 奥勒松 2 4 75% 主胜1.87平局3.65主负3.15', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '挪超第30轮 01:00 松达尔 奥勒松 2 4 75% 主胜1.8.', 1),
	(40, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_561816.html', '挪超第30轮 01:00 斯塔贝克 斯达 3 0 92% 主胜1.35平局4.55主负5.95', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '挪超第30轮 01:00 斯塔贝克 斯达 3 0 92% 主胜1.3.', 1),
	(41, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_561817.html', '挪超第30轮 01:00 特罗姆瑟 奥德格陵兰 3 1 84% 主胜2.25平局3.38主负2.58', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '挪超第30轮 01:00 特罗姆瑟 奥德格陵兰 3 1 84% 主胜.', 1),
	(42, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_561818.html', '挪超第30轮 01:00 维京 瓦勒伦加 0 2 95% 主胜1.83平局3.60主负3.30', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '挪超第30轮 01:00 维京 瓦勒伦加 0 2 95% 主胜1.8.', 1),
	(43, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_573211.html', '比甲第14轮 01:00 安德莱赫特 奥斯坦德 1 1 95% 主胜1.37平局4.30主负6.00', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '比甲第14轮 01:00 安德莱赫特 奥斯坦德 1 1 95% 主胜.', 1),
	(44, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_618627.html', '阿甲第9轮 01:00 班菲尔德 拉斐拉竞技 1 1 95% 主胜1.92平局2.95主负3.72', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '阿甲第9轮 01:00 班菲尔德 拉斐拉竞技 1 1 95% 主胜1.', 1),
	(45, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_606946.html', '西甲第11轮 01:30 比利亚雷亚尔 皇家贝蒂斯 2 0 83% 主胜1.40平局3.90主负6.35', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '西甲第11轮 01:30 比利亚雷亚尔 皇家贝蒂斯 2 0 83% .', 1),
	(46, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_609682.html', '葡超第10轮 02:00 波尔图 本菲卡 1 1 78% 主胜1.94平局3.00主负3.58', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '葡超第10轮 02:00 波尔图 本菲卡 1 1 78% 主胜1.9.', 1),
	(47, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_581198.html', '墨西联第16轮 02:00 美洲狮 莫雷利亚 1 1 84% 主胜1.44平局4.20主负5.10', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '墨西联第16轮 02:00 美洲狮 莫雷利亚 1 1 84% 主胜1.', 1),
	(48, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_612492.html', '智甲第12轮 02:30 帕勒斯蒂诺 特木科 2 0 93% 主胜1.80平局3.50主负3.50', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '智甲第12轮 02:30 帕勒斯蒂诺 特木科 2 0 93% 主胜1.', 1),
	(49, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_573214.html', '比甲第14轮 03:00 根特 华斯兰德 2 0 98% 主胜1.22平局5.00主负9.20', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '比甲第14轮 03:00 根特 华斯兰德 2 0 98% 主胜1.2.', 1),
	(50, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_573217.html', '比甲第14轮 03:00 标准列日 匹卢维兹 2 1 88% 主胜1.29平局4.45主负7.70', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '比甲第14轮 03:00 标准列日 匹卢维兹 2 1 88% 主胜1.', 1),
	(51, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_569052.html', '巴甲第34轮 03:00 克鲁塞罗 弗鲁米嫩塞 4 2 90% 主胜1.71平局3.30主负4.15', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '巴甲第34轮 03:00 克鲁塞罗 弗鲁米嫩塞 4 2 90% 主胜.', 1),
	(52, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_569051.html', '巴甲第34轮 03:00 帕尔梅拉斯 巴西国际 1 0 98% 主胜1.41平局3.70主负6.65', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '巴甲第34轮 03:00 帕尔梅拉斯 巴西国际 1 0 98% 主胜.', 1),
	(53, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_569057.html', '巴甲第34轮 03:00 圣克鲁斯 米内罗美洲 1 0 89% 主胜1.65平局3.50主负4.25', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '巴甲第34轮 03:00 圣克鲁斯 米内罗美洲 1 0 89% 主胜.', 1),
	(54, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_569048.html', '巴甲第34轮 03:00 维多利亚 巴拉纳竞技 3 2 99% 主胜1.72平局3.25主负4.20', '2016-11-07 10:23:47', '2016-11-07 10:23:47', '巴甲第34轮 03:00 维多利亚 巴拉纳竞技 3 2 99% 主胜.', 1),
	(55, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_618639.html', '阿甲第9轮 03:00 泰格雷 贝尔格雷诺 2 1 77% 主胜2.09平局2.86主负3.32', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '阿甲第9轮 03:00 泰格雷 贝尔格雷诺 2 1 77% 主胜2..', 1),
	(56, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_618634.html', '阿甲第9轮 03:00 圣洛伦索 飓风队 2 0 89% 主胜1.58平局3.35主负5.05', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '阿甲第9轮 03:00 圣洛伦索 飓风队 2 0 89% 主胜1.5.', 1),
	(57, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_627082.html', '美职联半准决赛 03:00 科罗拉多急流 洛杉矶银河 1 0 75% 主胜1.88平局3.15主负3.58', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '美职联半准决赛 03:00 科罗拉多急流 洛杉矶银河 1 0 75%.', 1),
	(58, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_610492.html', '意甲第12轮 03:45 罗马 博洛尼亚 3 0 97% 主胜1.14平局5.80主负12.50', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '意甲第12轮 03:45 罗马 博洛尼亚 3 0 97% 主胜1.1.', 1),
	(59, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_606945.html', '西甲第11轮 03:45 塞维利亚 巴萨 1 2 99% 主胜4.70平局4.15主负1.48', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '西甲第11轮 03:45 塞维利亚 巴萨 1 2 99% 主胜4.7.', 1),
	(60, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_573899.html', '法甲第12轮 03:45 巴黎圣日尔曼 雷恩 4 0 96% 主胜1.13平局5.95主负13.00', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '法甲第12轮 03:45 巴黎圣日尔曼 雷恩 4 0 96% 主胜1.', 1),
	(61, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_609680.html', '葡超第10轮 04:15 里斯本竞技 艾路卡 3 0 97% 主胜1.11平局6.20主负15.00', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '葡超第10轮 04:15 里斯本竞技 艾路卡 3 0 97% 主胜1.', 1),
	(62, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_609684.html', '葡超第10轮 04:30 马里迪莫 布拉加 1 0 87% 主胜3.16平局3.05主负2.06', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '葡超第10轮 04:30 马里迪莫 布拉加 1 0 87% 主胜3..', 1),
	(63, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_618637.html', '阿甲第9轮 05:00 铁路工场 天主教青年会 1 0 96% 主胜1.90平局2.88主负3.90', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '阿甲第9轮 05:00 铁路工场 天主教青年会 1 0 96% 主胜.', 1),
	(64, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_612491.html', '智甲第12轮 05:00 基约塔 康塞普西翁大学 2 0 88% 主胜1.83平局3.35主负3.55', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '智甲第12轮 05:00 基约塔 康塞普西翁大学 2 0 88% 主.', 1),
	(65, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_627081.html', '美职联半准决赛 05:00 纽约红牛 蒙特利尔冲击 1 2 79% 主胜1.33平局4.65主负6.15', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '美职联半准决赛 05:00 纽约红牛 蒙特利尔冲击 1 2 79% .', 1),
	(66, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_618633.html', '阿甲第9轮 05:15 罗萨里奥中央 阿根廷独立 0 0 77% 主胜2.19平局2.78主负3.20', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '阿甲第9轮 05:15 罗萨里奥中央 阿根廷独立 0 0 77% 主.', 1),
	(67, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_569055.html', '巴甲第34轮 05:30 沙佩科恩斯 费古埃伦斯 1 0 98% 主胜1.66平局3.30主负4.50', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '巴甲第34轮 05:30 沙佩科恩斯 费古埃伦斯 1 0 98% 主.', 1),
	(68, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_569054.html', '巴甲第34轮 05:30 科里蒂巴 米内罗竞技 2 0 78% 主胜2.37平局3.10主负2.60', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '巴甲第34轮 05:30 科里蒂巴 米内罗竞技 2 0 78% 主胜.', 1),
	(69, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_618629.html', '阿甲第9轮 07:00 拉普拉塔体操 博卡青年 0 3 80% 主胜3.20平局2.82主负2.17', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '阿甲第9轮 07:00 拉普拉塔体操 博卡青年 0 3 80% 主胜.', 1),
	(70, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_612488.html', '智甲第12轮 07:30 瓦奇巴托 伊瓦顿 2 2 94% 主胜1.95平局3.50主负3.05', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '智甲第12轮 07:30 瓦奇巴托 伊瓦顿 2 2 94% 主胜1..', 1),
	(71, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_627080.html', '美职联半准决赛 07:30 纽约城FC 多伦多FC 0 5 80% 主胜2.17平局3.45主负2.65', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '美职联半准决赛 07:30 纽约城FC 多伦多FC 0 5 80% .', 1),
	(72, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_581199.html', '墨西联第16轮 08:00 桑托斯拉古纳 恰帕斯美洲狮 2 0 83% 主胜1.47平局3.85主负5.30', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '墨西联第16轮 08:00 桑托斯拉古纳 恰帕斯美洲狮 2 0 83.', 1),
	(73, 10, 'http://3g.caiqr.com/matchesdetail/football_match_bottom_page_627083.html', '美职联半准决赛 10:00 FC达拉斯    西雅图海湾人 94% 主胜1.50平局3.85主负4.95', '2016-11-07 10:23:48', '2016-11-07 10:23:48', '美职联半准决赛 10:00 FC达拉斯    西雅图海湾人 94% .', 1);
/*!40000 ALTER TABLE `t_inter` ENABLE KEYS */;


-- 导出  表 grp.t_privilege_resource_ref 结构
CREATE TABLE IF NOT EXISTS `t_privilege_resource_ref` (
  `ref_id` bigint(19) NOT NULL AUTO_INCREMENT,
  `privilege_code` varchar(50) NOT NULL,
  `resource_code` varchar(50) NOT NULL,
  PRIMARY KEY (`ref_id`)
) ENGINE=InnoDB AUTO_INCREMENT=394997 DEFAULT CHARSET=utf8;

-- 正在导出表  grp.t_privilege_resource_ref 的数据：~100 rows (大约)
/*!40000 ALTER TABLE `t_privilege_resource_ref` DISABLE KEYS */;
INSERT INTO `t_privilege_resource_ref` (`ref_id`, `privilege_code`, `resource_code`) VALUES
	(394897, 'ROLE_SUPERSYSTEM', 'resource_index'),
	(394898, 'ROLE_SUPERSYSTEM', 'resource_toAdminListPage'),
	(394899, 'ROLE_SUPERSYSTEM', 'resource_queryAdminList'),
	(394900, 'ROLE_SUPERSYSTEM', 'resource_queryLeagueSelectList'),
	(394901, 'ROLE_SUPERSYSTEM', 'resource_toRoleList'),
	(394902, 'ROLE_SUPERSYSTEM', 'resource_queryRoleList'),
	(394903, 'ROLE_SUPERSYSTEM', 'resource_toEditRolePage'),
	(394904, 'ROLE_SUPERSYSTEM', 'resource_queryRoleById'),
	(394905, 'ROLE_SUPERSYSTEM', 'resource_editRole'),
	(394906, 'ROLE_SUPERSYSTEM', 'resource_deleteRole'),
	(394907, 'ROLE_SUPERSYSTEM', 'resource_toPrivilegeList'),
	(394908, 'ROLE_SUPERSYSTEM', 'resource_2e43a486bf8b479785b349c0dfc45161'),
	(394909, 'ROLE_SUPERSYSTEM', 'resource_toAddPrivilege'),
	(394910, 'ROLE_SUPERSYSTEM', 'resource_toEditPrivilege'),
	(394911, 'ROLE_SUPERSYSTEM', 'resource_queryPrivilegeList'),
	(394912, 'ROLE_SUPERSYSTEM', 'resource_toUserListPage'),
	(394913, 'ROLE_SUPERSYSTEM', 'resource_toAddUserPage'),
	(394914, 'ROLE_SUPERSYSTEM', 'resource_toUpdateUserPage'),
	(394915, 'ROLE_SUPERSYSTEM', 'resource_queryUserList'),
	(394916, 'ROLE_SUPERSYSTEM', 'resource_addUser'),
	(394917, 'ROLE_SUPERSYSTEM', 'resource_updateUser'),
	(394918, 'ROLE_SUPERSYSTEM', 'resource_deleteUser'),
	(394919, 'ROLE_SUPERSYSTEM', 'resource_toResourcesListPage'),
	(394920, 'ROLE_SUPERSYSTEM', 'resource_toAddResourcePage'),
	(394921, 'ROLE_SUPERSYSTEM', 'resource_toUpdateResourcePage'),
	(394922, 'ROLE_SUPERSYSTEM', 'resource_queryResourceList'),
	(394923, 'ROLE_SUPERSYSTEM', 'resource_resourceDataChecked'),
	(394924, 'ROLE_SUPERSYSTEM', 'resource_addResource'),
	(394925, 'ROLE_SUPERSYSTEM', 'resource_updateResource'),
	(394926, 'ROLE_SUPERSYSTEM', 'resource_deleteResource'),
	(394927, 'ROLE_SUPERSYSTEM', 'resource_userDataChecked'),
	(394928, 'ROLE_SUPERSYSTEM', 'resource_queryResourceContent'),
	(394929, 'ROLE_SUPERSYSTEM', 'resource_addPrivilege'),
	(394930, 'ROLE_SUPERSYSTEM', 'resource_deletePrivilege'),
	(394931, 'ROLE_SUPERSYSTEM', 'resource_editPrivilege'),
	(394932, 'ROLE_SUPERSYSTEM', 'resource_baseInfoManageMenu'),
	(394933, 'ROLE_SUPERSYSTEM', 'resource_privilegeManageMenu'),
	(394934, 'ROLE_SUPERSYSTEM', 'resource_systemManageMenu'),
	(394935, 'ROLE_SUPERSYSTEM', 'resource_queryMenuSelectList'),
	(394936, 'ROLE_SUPERSYSTEM', 'resource_toAddRolePage'),
	(394937, 'ROLE_SUPERSYSTEM', 'resource_addRole'),
	(394938, 'ROLE_SUPERSYSTEM', 'resource_toMenuListPage'),
	(394939, 'ROLE_SUPERSYSTEM', 'resource_toAddMenuPage'),
	(394940, 'ROLE_SUPERSYSTEM', 'resource_toUpdateMenuPage'),
	(394941, 'ROLE_SUPERSYSTEM', 'resource_queryParentMenuSelectList'),
	(394942, 'ROLE_SUPERSYSTEM', 'resource_addMenu'),
	(394943, 'ROLE_SUPERSYSTEM', 'resource_queryMenuList'),
	(394944, 'ROLE_SUPERSYSTEM', 'resource_updateMenu'),
	(394945, 'ROLE_SUPERSYSTEM', 'resource_deleteMenu'),
	(394946, 'ROLE_SUPERSYSTEM', 'resource_toAssignPrivilegePage'),
	(394947, 'ROLE_SUPERSYSTEM', 'resource_queryRolePrivilege'),
	(394948, 'ROLE_SUPERSYSTEM', 'resource_assignPrivilege'),
	(394949, 'ROLE_SUPERSYSTEM', 'resource_5d2cb4b58ab3458e82f0a64882a2e782'),
	(394950, 'ROLE_SUPERSYSTEM', 'resource_9534a48563b84015871e17412fc8f9cb'),
	(394951, 'ROLE_SUPERSYSTEM', 'resource_6aca1885fb0f4ae0b697f745461b966a'),
	(394952, 'ROLE_SUPERSYSTEM', 'resource_05a74ecd311f4ada9658fe7d07ee2937'),
	(394953, 'ROLE_SUPERSYSTEM', 'resource_478ffda6957e4759b0249023a0e34d16'),
	(394954, 'ROLE_SUPERSYSTEM', 'resource_cac0c898b6cc4759a6ac31829f6361e2'),
	(394955, 'ROLE_SUPERSYSTEM', 'resource_4c7c98e674914113b1a3ccd1ddf9c191'),
	(394956, 'ROLE_SUPERSYSTEM', 'resource_2d852b8737044439b8833d1feeafc45a'),
	(394957, 'ROLE_SUPERSYSTEM', 'resource_2bd2dda75f3144ccafbea972460e9a05'),
	(394958, 'ROLE_SUPERSYSTEM', 'resource_45bcdb08ec7a4a39a4f995201234e88d'),
	(394959, 'ROLE_SUPERSYSTEM', 'resource_03885b341f6a45939d6e730d47568c95'),
	(394960, 'ROLE_SUPERSYSTEM', 'resource_304c050f975a45a9814c5356fcead88c'),
	(394961, 'ROLE_SUPERSYSTEM', 'resource_44b85da9cdf74d2c9842566d94ac3db7'),
	(394962, 'ROLE_SUPERSYSTEM', 'resource_e24c4068dc6c40e5a8ab16ea51081769'),
	(394963, 'ROLE_SUPERSYSTEM', 'resource_73a5d75c6d23431691984fafce33bfa0'),
	(394964, 'ROLE_SUPERSYSTEM', 'resource_745681a5272245faab6c1e8491c805f6'),
	(394965, 'ROLE_SUPERSYSTEM', 'resource_c3c3536b09e442f5b42dfa8d107cd9df'),
	(394966, 'ROLE_SUPERSYSTEM', 'resource_86a61ef41a044d3fbba4d818acb214a1'),
	(394967, 'ROLE_SUPERSYSTEM', 'resource_9e5b7a0c70d24a41a767d603141e4205'),
	(394968, 'ROLE_SUPERSYSTEM', 'resource_adaaef4e21344c7ca17225ac967e213e'),
	(394969, 'ROLE_SUPERSYSTEM', 'resource_866b48be04b64b349d36ca55f99a2bf0'),
	(394970, 'ROLE_SUPERSYSTEM', 'resource_c43ef9cff5734e84b5a0a968af408031'),
	(394971, 'ROLE_SUPERSYSTEM', 'resource_78ccac72f0e444f4ac27ad79a1b5ee8f'),
	(394972, 'ROLE_SUPERSYSTEM', 'resource_398255d65acb4aceb562449b44901254'),
	(394973, 'ROLE_SUPERSYSTEM', 'resource_27ae8510808c4d0582ddc9b23d484e16'),
	(394974, 'ROLE_SUPERSYSTEM', 'resource_ae06a45dcb6844ac9b3ba904a8d40bfd'),
	(394975, 'ROLE_SUPERSYSTEM', 'resource_fecd3d2c031f4c7bbff0f1f702a77b5a'),
	(394976, 'ROLE_SUPERSYSTEM', 'resource_00f52a99019b471f9555dcc27821bd6c'),
	(394977, 'ROLE_SUPERSYSTEM', 'resource_63dea3b991ca4ae4a200d37425ed4a97'),
	(394978, 'ROLE_SUPERSYSTEM', 'resource_3275cd8ca6024a74a64f0fd22ad1b854'),
	(394979, 'ROLE_SUPERSYSTEM', 'resource_93e555fbb80b4970936713f014459046'),
	(394980, 'ROLE_SUPERSYSTEM', 'resource_b96410a2309e4d52b84b8ee1af31ea77'),
	(394981, 'ROLE_SUPERSYSTEM', 'resource_f6da0b924ba84f759358a92b32492abc'),
	(394982, 'ROLE_SUPERSYSTEM', 'resource_d1a63460ec4141998ba22a69127dba87'),
	(394983, 'ROLE_SUPERSYSTEM', 'resource_cfe1d0263cbe4c21810e2084740bcce4'),
	(394984, 'ROLE_SUPERSYSTEM', 'resource_0a57cbfc54dd42ff99f64aa6d83b4bbc'),
	(394985, 'ROLE_SUPERSYSTEM', 'resource_c0b413ac8cee48ed980b18a72e735072'),
	(394986, 'ROLE_SUPERSYSTEM', 'resource_7fe876cd2988452a88ca8fcd66295794'),
	(394987, 'ROLE_SUPERSYSTEM', 'resource_e5acba52977f48399758def08c5c642d'),
	(394988, 'ROLE_SUPERSYSTEM', 'resource_c33e3bb16694424099677e8f9ff50d5c'),
	(394989, 'ROLE_SUPERSYSTEM', 'resource_e5acba52977f48399758def08c5c642c'),
	(394990, 'ROLE_SUPERSYSTEM', 'resource_e5acba52977f48399758def08c5c642b'),
	(394991, 'ROLE_SUPERSYSTEM', 'resource_00d991a63dff45c9ab5e9e8ca2ff0a3f'),
	(394992, 'ROLE_SUPERSYSTEM', 'resource_7eee915394ad418fb36dbc20bc235cb6'),
	(394993, 'ROLE_SUPERSYSTEM', 'resource_d253d4e4b09c47f1ad3e2d67b0598900'),
	(394994, 'ROLE_SUPERSYSTEM', 'resource_e04bfb11a4b7492f80de57d4b773ee5b'),
	(394995, 'ROLE_SUPERSYSTEM', 'resource_e5b0169062fb405e807d3888232a63e7'),
	(394996, 'ROLE_SUPERSYSTEM', 'resource_0da7bee0592f4d6a9168cc5bf1140ab7');
/*!40000 ALTER TABLE `t_privilege_resource_ref` ENABLE KEYS */;


-- 导出  表 grp.t_sys_log 结构
CREATE TABLE IF NOT EXISTS `t_sys_log` (
  `LOG_ID` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '日志标识',
  `OPT_USER` varchar(50) NOT NULL COMMENT '操作者',
  `OPT_FUN` varchar(100) NOT NULL COMMENT '操作功能',
  `OPT_FUN_DESC` varchar(1000) DEFAULT NULL COMMENT '功能描述',
  `OPT_TIME` datetime NOT NULL COMMENT '操作时间',
  `OPT_RES` int(2) NOT NULL COMMENT '操作结果:1：成功; -1 ：失败',
  `OPT_TYPE` varchar(20) NOT NULL COMMENT '操作类型,QUERY:查询,ADD添加,DELETE删除,MODIFY修改;VIEW查看;',
  `OPT_USER_CODE` varchar(100) NOT NULL,
  `OPT_BEFORE` text,
  `OPT_AFTER` longtext,
  `OPT_PARAM` text,
  PRIMARY KEY (`LOG_ID`),
  KEY `OPT_USER` (`OPT_USER`),
  KEY `OPT_FUN` (`OPT_FUN`),
  KEY `OPT_TIME` (`OPT_TIME`),
  KEY `OPT_TYPE` (`OPT_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  grp.t_sys_log 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_sys_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_log` ENABLE KEYS */;


-- 导出  表 grp.t_sys_menu 结构
CREATE TABLE IF NOT EXISTS `t_sys_menu` (
  `menu_id` bigint(19) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(50) DEFAULT NULL,
  `menu_url` varchar(80) DEFAULT NULL,
  `menu_index` int(3) DEFAULT NULL,
  `parent_id` bigint(19) DEFAULT NULL,
  `home_page_id` bigint(19) DEFAULT NULL,
  `menu_level` int(1) DEFAULT NULL,
  `menu_code` varchar(255) NOT NULL,
  `module_name` varchar(50) DEFAULT NULL COMMENT '模块分支',
  `class_name` varchar(50) DEFAULT NULL COMMENT '图标class',
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `menu_code` (`menu_code`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- 正在导出表  grp.t_sys_menu 的数据：~10 rows (大约)
/*!40000 ALTER TABLE `t_sys_menu` DISABLE KEYS */;
INSERT INTO `t_sys_menu` (`menu_id`, `menu_name`, `menu_url`, `menu_index`, `parent_id`, `home_page_id`, `menu_level`, `menu_code`, `module_name`, `class_name`) VALUES
	(2, '基本信息管理', '#', 1, -1, -1, 0, '6b538cd530c54fedbbd7392918d5a1a7', 'console-okery', 'fa fa-cutlery'),
	(3, '用户管理', 'toUserListPage.do', 1, 2, -1, 1, 'da8b61f402f24b68a5fed6bf65cbdc42', 'console-okery', 'fa fa fa-bar-chart-o'),
	(4, '角色管理', 'toRoleList.do', 4, 2, -1, 1, 'da8b61f402f24b68a5fed6bf65cbdc43', 'console-okery', 'fa fa-envelope'),
	(5, '授权管理', '#', 2, -1, -1, 0, 'd2a33f67f4c5455f9eda520287daac90', 'console-okery', 'fa fa-edit'),
	(6, '权限管理', 'toPrivilegeList.do', 1, 5, -1, 1, 'd2a33f67f4c5455f9eda520287daac91', 'console-okery', 'fa fa-desktop'),
	(7, '资源管理', '#', 3, -1, -1, 0, 'cfc7e629d2d346e88387a8d8713acbec', 'console-okery', 'fa arrow'),
	(8, '菜单管理', 'toMenuListPage.do', 1, 7, -1, 1, 'da8b61f402f24b68a5fed6bf65cbdc41', 'console-okery', 'fa fa-table'),
	(9, '资源管理', 'toResourcesListPage.do', 2, 7, -1, 1, 'd2a33f67f4c5455f9eda520287daac96', 'console-okery', 'fa fa-flask'),
	(11, '我的爬虫', '#', 4, -1, -1, 0, 'f30c7f951a4f42f5872111cb06d0a7a9', 'console-okery', 'fa fa-picture-o'),
	(12, 'Jsoup数据抓取', 'toJsoupList.do', 1, 11, -1, 1, '5432760c8bf64e01b432d8ff0c715293', 'console-okery', 'fa fa-flask');
/*!40000 ALTER TABLE `t_sys_menu` ENABLE KEYS */;


-- 导出  表 grp.t_sys_privilege 结构
CREATE TABLE IF NOT EXISTS `t_sys_privilege` (
  `privilege_id` bigint(19) NOT NULL AUTO_INCREMENT,
  `privilege_name` varchar(50) NOT NULL,
  `privilege_code` varchar(100) NOT NULL,
  `privilege_desc` varchar(255) DEFAULT NULL,
  `crt_date` datetime NOT NULL,
  `mod_date` datetime DEFAULT NULL,
  PRIMARY KEY (`privilege_id`),
  UNIQUE KEY `idx_privilege_code` (`privilege_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 正在导出表  grp.t_sys_privilege 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `t_sys_privilege` DISABLE KEYS */;
INSERT INTO `t_sys_privilege` (`privilege_id`, `privilege_name`, `privilege_code`, `privilege_desc`, `crt_date`, `mod_date`) VALUES
	(1, '超级管理员权限', 'ROLE_SUPERSYSTEM', '超级管理员权限', '2015-07-22 15:52:12', '2016-11-07 17:36:46');
/*!40000 ALTER TABLE `t_sys_privilege` ENABLE KEYS */;


-- 导出  表 grp.t_sys_resources 结构
CREATE TABLE IF NOT EXISTS `t_sys_resources` (
  `resource_id` bigint(19) NOT NULL AUTO_INCREMENT,
  `resource_code` varchar(255) DEFAULT NULL,
  `resource_name` varchar(255) DEFAULT NULL,
  `resource_desc` varchar(255) DEFAULT NULL,
  `resource_url` varchar(255) DEFAULT NULL,
  `resource_type` int(1) DEFAULT '0' COMMENT '0:功能,1:菜单,2:界面',
  `menu_code` varchar(50) DEFAULT NULL,
  `parent_resource` int(11) DEFAULT '-1',
  `is_enable` varchar(50) DEFAULT 'enable' COMMENT 'enable:资源当前有效中; disable资源被废弃',
  PRIMARY KEY (`resource_id`),
  UNIQUE KEY `idx_f_code` (`resource_code`) USING HASH,
  UNIQUE KEY `resource_url` (`resource_url`)
) ENGINE=InnoDB AUTO_INCREMENT=1077 DEFAULT CHARSET=utf8;

-- 正在导出表  grp.t_sys_resources 的数据：~90 rows (大约)
/*!40000 ALTER TABLE `t_sys_resources` DISABLE KEYS */;
INSERT INTO `t_sys_resources` (`resource_id`, `resource_code`, `resource_name`, `resource_desc`, `resource_url`, `resource_type`, `menu_code`, `parent_resource`, `is_enable`) VALUES
	(2, 'resource_index', '系统首页', '系统首页', '/index.do', 2, NULL, -1, 'enable'),
	(44, 'resource_toRoleList', '查询角色列表页面', '查询角色列表页面', '/toRoleList.do', 2, 'da8b61f402f24b68a5fed6bf65cbdc43', 108, 'enable'),
	(45, 'resource_queryRoleList', '查询角色', '查询角色', '/queryRoleList.do', 0, NULL, 44, 'enable'),
	(46, 'resource_toEditRolePage', '修改角色页面', '修改角色页面', '/toEditRolePage.do', 2, NULL, 44, 'enable'),
	(47, 'resource_queryRoleById', '根据角色ID查询', '根据角色ID查询', '/queryRoleById.do', 0, NULL, 44, 'enable'),
	(48, 'resource_editRole', '修改角色', '修改角色', '/editRole.do', 0, NULL, 44, 'enable'),
	(49, 'resource_deleteRole', '删除角色', '删除角色', '/deleteRole.do', 0, NULL, 44, 'enable'),
	(52, 'resource_toPrivilegeList', '权限列表页面', '权限列表页面', '/toPrivilegeList.do', 0, 'd2a33f67f4c5455f9eda520287daac91', 109, 'enable'),
	(53, 'resource_toAddPrivilege', '添加权限页面', '添加权限页面', '/toAddPrivilege.do', 0, NULL, 52, 'enable'),
	(54, 'resource_toEditPrivilege', '编辑权限页面', '编辑权限页面', '/toEditPrivilege.do', 0, NULL, 52, 'enable'),
	(55, 'resource_queryPrivilegeList', '查询权限记录', '查询权限记录', '/queryPrivilegeList.do', 0, NULL, 52, 'enable'),
	(62, 'resource_toUserListPage', '系统用户管理页面', '系统用户管理页面', '/toUserListPage.do', 2, 'da8b61f402f24b68a5fed6bf65cbdc42', 108, 'enable'),
	(63, 'resource_toAddUserPage', '添加系统用户页面', '添加系统用户页面', '/toAddUserPage.do', 2, NULL, 62, 'enable'),
	(64, 'resource_toUpdateUserPage', '修改系统用户页面', '修改系统用户页面', '/toUpdateUserPage.do', 2, NULL, 62, 'enable'),
	(65, 'resource_queryUserList', '查询系统用户列表', '查询系统用户列表', '/queryUserList.do', 0, NULL, 62, 'enable'),
	(66, 'resource_addUser', '添加系统用户', '添加系统用户', '/addUser.do', 0, NULL, 62, 'enable'),
	(67, 'resource_updateUser', '修改系统用户', '修改系统用户', '/updateUser.do', 0, NULL, 62, 'enable'),
	(68, 'resource_deleteUser', '删除系统用户', '删除系统用户', '/deleteUser.do', 0, NULL, 62, 'enable'),
	(75, 'resource_toResourcesListPage', '资源管理页面', '资源管理页面', '/toResourcesListPage.do', 2, 'd2a33f67f4c5455f9eda520287daac96', 1067, 'enable'),
	(77, 'resource_toAddResourcePage', '添加资源页面', '添加资源页面', '/toAddResourcePage.do', 2, NULL, 75, 'enable'),
	(78, 'resource_toUpdateResourcePage', '修改资源页面', '修改资源页面', '/toUpdateResourcePage.do', 2, NULL, 75, 'enable'),
	(79, 'resource_queryResourceList', '查询资源列表', '查询资源列表', '/queryResourceList.do', 0, NULL, 75, 'enable'),
	(80, 'resource_resourceDataChecked', '资源数据检查', '资源数据检查', '/resourceDataChecked.do', 0, NULL, 75, 'enable'),
	(81, 'resource_addResource', '添加资源信息', '添加资源信息', '/addResource.do', 0, NULL, 75, 'enable'),
	(82, 'resource_updateResource', '修改资源信息', '修改资源信息', '/updateResource.do', 0, NULL, 75, 'enable'),
	(83, 'resource_deleteResource', '删除资源信息', '删除资源信息', '/deleteResource.do', 0, NULL, 75, 'enable'),
	(84, 'resource_userDataChecked', '用户数据检查', '用户数据检查', '/userDataChecked.do', 0, NULL, 62, 'enable'),
	(89, 'resource_queryResourceContent', '查询资源内容', '查询资源内容', '/queryResourceContent.do', 0, NULL, 75, 'enable'),
	(90, 'resource_addPrivilege', '添加权限', '添加权限', '/addPrivilege.do', 0, NULL, 52, 'enable'),
	(91, 'resource_deletePrivilege', '删除权限', '删除权限', '/deletePrivilege.do', 0, NULL, 52, 'enable'),
	(92, 'resource_editPrivilege', '更新权限', '更新权限', '/editPrivilege.do', 0, NULL, 52, 'enable'),
	(93, 'resource_toAddRolePage', '添加角色页面', '添加角色页面', '/toAddRolePage.do', 2, NULL, 44, 'enable'),
	(94, 'resource_addRole', '添加角色', '添加角色', '/addRole.do', 0, NULL, 44, 'enable'),
	(95, 'resource_toMenuListPage', '菜单管理页面', '菜单管理页面', '/toMenuListPage.do', 2, 'da8b61f402f24b68a5fed6bf65cbdc41', 1067, 'enable'),
	(96, 'resource_toAddMenuPage', '添加菜单页面', '添加菜单页面', '/toAddMenuPage.do', 2, NULL, 95, 'enable'),
	(97, 'resource_toUpdateMenuPage', '修改菜单页面', '修改菜单页面', '/toUpdateMenuPage.do', 2, NULL, 95, 'enable'),
	(98, 'resource_queryParentMenuSelectList', '父菜单下拉列表', '父菜单下拉列表', '/queryParentMenuSelectList.do', 0, NULL, 95, 'enable'),
	(99, 'resource_queryMenuList', '查询菜单列表', '查询菜单列表', '/queryMenuList.do', 0, NULL, 95, 'enable'),
	(100, 'resource_addMenu', '添加菜单', '添加菜单', '/addMenu.do', 0, NULL, 95, 'enable'),
	(101, 'resource_updateMenu', '修改菜单', '修改菜单', '/updateMenu.do', 0, NULL, 95, 'enable'),
	(102, 'resource_deleteMenu', '删除菜单', '删除菜单', '/deleteMenu.do', 0, NULL, 95, 'enable'),
	(108, 'resource_baseInfoManageMenu', '基本信息管理菜单', '基本信息管理菜单', '6b538cd530c54fedbbd7392918d5a1a7', 0, '6b538cd530c54fedbbd7392918d5a1a7', -1, 'enable'),
	(109, 'resource_privilegeManageMenu', '授权管理菜单', '授权管理菜单', 'd2a33f67f4c5455f9eda520287daac90', 0, 'd2a33f67f4c5455f9eda520287daac90', -1, 'enable'),
	(111, 'resource_queryMenuSelectList', '菜单下拉列表', '菜单下拉列表', '/queryMenuSelectList.do', 0, NULL, 95, 'enable'),
	(113, 'resource_toAssignPrivilegePage', '分配权限页面', '分配权限页面', '/toAssignPrivilegePage.do', 2, NULL, 52, 'enable'),
	(114, 'resource_queryRolePrivilege', '查询角色权限', '查询角色权限', '/queryRolePrivilege.do', 0, NULL, 44, 'enable'),
	(115, 'resource_assignPrivilege', '给角色分配权限', '给角色分配权限', '/assignPrivilege.do', 0, NULL, 44, 'enable'),
	(147, 'resource_5d2cb4b58ab3458e82f0a64882a2e782', '用户管理角色配置页面', '用户管理角色配置页面', '/toUserRoleConfigPage.do', 2, NULL, 62, 'disabd'),
	(148, 'resource_9534a48563b84015871e17412fc8f9cb', '用户管理查询用户角色功能', '用户管理查询用户角色功能', '/queryUserRole.do', 0, NULL, 62, 'enable'),
	(149, 'resource_6aca1885fb0f4ae0b697f745461b966a', '用户管理添加用户角色功能', '用户管理添加用户角色功能', '/addUserRole.do', 0, NULL, 62, 'enable'),
	(150, 'resource_05a74ecd311f4ada9658fe7d07ee2937', '用户管理外部系统用户下拉列表', '用户管理外部系统用户下拉列表', '/queryExtSysUserSelectList.do', 0, NULL, 62, 'enable'),
	(160, 'resource_478ffda6957e4759b0249023a0e34d16', '菜单管理添加菜单按钮', '菜单管理添加菜单按钮', 'toAddMenuPage.do', 0, NULL, 95, 'enable'),
	(161, 'resource_cac0c898b6cc4759a6ac31829f6361e2', '菜单管理删除菜单按钮', '菜单管理删除菜单按钮', 'deleteMenu.do', 0, NULL, 95, 'enable'),
	(162, 'resource_4c7c98e674914113b1a3ccd1ddf9c191', '用户管理添加用户按钮', '用户管理添加用户按钮', 'toAddUserPage.do', 0, NULL, 62, 'enable'),
	(163, 'resource_2d852b8737044439b8833d1feeafc45a', '用户管理删除用户按钮', '用户管理删除用户按钮', 'deleteUser.do', 0, NULL, 62, 'enable'),
	(164, 'resource_2bd2dda75f3144ccafbea972460e9a05', '角色管理添加角色按钮', '角色管理添加角色按钮', 'toAddRole.do', 0, NULL, 44, 'enable'),
	(165, 'resource_45bcdb08ec7a4a39a4f995201234e88d', '角色管理删除角色按钮', '角色管理删除角色按钮', 'deleteRole.do', 0, NULL, 44, 'enable'),
	(166, 'resource_03885b341f6a45939d6e730d47568c95', '资源管理添加资源按钮', '资源管理添加资源按钮', 'toAddResourcePage.do', 0, NULL, 75, 'enable'),
	(167, 'resource_304c050f975a45a9814c5356fcead88c', '资源管理删除资源按钮', '资源管理删除资源按钮', 'deleteResource.do', 0, NULL, 75, 'enable'),
	(168, 'resource_44b85da9cdf74d2c9842566d94ac3db7', '权限管理添加权限按钮', '权限管理添加权限按钮', 'toAddPrivilege.do', 0, NULL, 52, 'enable'),
	(169, 'resource_e24c4068dc6c40e5a8ab16ea51081769', '权限管理删除权限按钮', '权限管理删除权限按钮', 'deletePrivilege.do', 0, NULL, 52, 'enable'),
	(170, 'resource_73a5d75c6d23431691984fafce33bfa0', '权限管理编辑权限按钮', '权限管理编辑权限按钮', 'toEditPrivilege.do', 0, NULL, 52, 'enable'),
	(171, 'resource_745681a5272245faab6c1e8491c805f6', '资源管理编辑资源按钮', '资源管理编辑资源按钮', 'toUpdateResourcePage.do', 0, NULL, 75, 'enable'),
	(172, 'resource_c3c3536b09e442f5b42dfa8d107cd9df', '角色管理编辑角色按钮', '角色管理编辑角色按钮', 'toEditRole.do', 0, NULL, 44, 'enable'),
	(173, 'resource_86a61ef41a044d3fbba4d818acb214a1', '角色管理分配权限按钮', '角色管理分配权限按钮', 'toAssignPrivilege.do', 0, NULL, 44, 'enable'),
	(174, 'resource_9e5b7a0c70d24a41a767d603141e4205', '用户管理分配角色按钮', '用户管理分配角色按钮', 'toUserRoleConfigPage.do', 0, NULL, 62, 'enable'),
	(175, 'resource_adaaef4e21344c7ca17225ac967e213e', '用户管理编辑用户按钮', '用户管理编辑用户按钮', 'toUpdateUserPage.do', 0, NULL, 62, 'enable'),
	(176, 'resource_866b48be04b64b349d36ca55f99a2bf0', '菜单管理编辑菜单按钮', '菜单管理编辑菜单按钮', 'toUpdateMenuPage.do', 0, NULL, 95, 'enable'),
	(185, 'resource_c43ef9cff5734e84b5a0a968af408031', '角色数据检查', '角色数据检查', '/roleDataChecked.do', 0, NULL, 44, 'enable'),
	(186, 'resource_78ccac72f0e444f4ac27ad79a1b5ee8f', '权限数据检查', '权限数据检查', '/privilegeDataChecked.do', 0, NULL, 52, 'enable'),
	(673, 'resource_0a57cbfc54dd42ff99f64aa6d83b4bbc', '圈子系统-系统管理-查询权限配置', '圈子系统-系统管理-查询权限配置', '/queryPrivilegeConf.do', 0, NULL, 52, 'enable'),
	(675, 'resource_3275cd8ca6024a74a64f0fd22ad1b854', '圈子-系统管理-跳转到设置权限页面', '圈子-系统管理-跳转到设置权限页面', '/toAddPrivilegePage.do', 2, NULL, 52, 'enable'),
	(677, 'resource_93e555fbb80b4970936713f014459046', '圈子-系统管理-跳转到编辑权限页面', '圈子-系统管理-跳转到编辑权限页面', '/toEditPrivilegePage.do', 0, NULL, 52, 'enable'),
	(679, 'resource_f6da0b924ba84f759358a92b32492abc', '圈子-系统管理-添加权限', '圈子-系统管理-添加权限', '/addForumPrivilege.do', 0, NULL, 52, 'enable'),
	(681, 'resource_63dea3b991ca4ae4a200d37425ed4a97', '圈子-系统管理-修改权限', '圈子-系统管理-修改权限', '/editForumPrivilege.do', 0, NULL, 52, 'enable'),
	(683, 'resource_d1a63460ec4141998ba22a69127dba87', '圈子-系统管理-删除权限', '圈子-系统管理-删除权限', '/deleteForumPrivilege.do', 0, NULL, 52, 'enable'),
	(685, 'resource_ae06a45dcb6844ac9b3ba904a8d40bfd', '圈子系统-用户管理分配权限页面', '圈子系统-系统管理-用户管理-分配权限页面', '/toAssignPrivilegeToUserPage.do', 2, NULL, 62, 'enable'),
	(687, 'resource_fecd3d2c031f4c7bbff0f1f702a77b5a', '圈子系统-用户管理查询用户权限功能', '圈子系统-系统管理-用户管理-查询用户权限功能', '/queryUserPrivilege.do', 0, NULL, 62, 'enable'),
	(689, 'resource_27ae8510808c4d0582ddc9b23d484e16', '圈子系统-用户管理分配权限按钮', '圈子系统-系统管理-用户管理-分配权限按钮', 'toAssignPrivilegeToUserPage.do', 0, NULL, 62, 'enable'),
	(691, 'resource_00f52a99019b471f9555dcc27821bd6c', '圈子系统-用户管理分配权限功能', '圈子系统-系统管理-用户管理-分配权限功能', '/addUserPrivilege.do', 0, NULL, 62, 'enable'),
	(980, 'resource_e5acba52977f48399758def08c5c642d', '系统管理-个人修改密码', '个人修改密码', '/changeUserPwd.do', 0, NULL, 2, 'enable'),
	(1067, 'resource_c33e3bb16694424099677e8f9ff50d5c', '系统管理--资源管理二级菜单', '系统管理--资源管理二级菜单', 'cfc7e629d2d346e88387a8d8713acbec', 1, 'cfc7e629d2d346e88387a8d8713acbec', -1, 'enable'),
	(1068, 'resource_e5acba52977f48399758def08c5c642c', '验证用户是否授权', '验证用户是否授权', '/checkIsAuth.do', 0, NULL, 2, 'enable'),
	(1069, 'resource_e5acba52977f48399758def08c5c642b', '创建用户动态二维码', '创建用户动态二维码', '/createVerificationCode.do', 0, NULL, 2, 'enable'),
	(1070, 'resource_2e43a486bf8b479785b349c0dfc45161', '系统管理-授权管理-权限管理-查看权限资源列表', '系统管理-授权管理-权限管理-查看权限资源列表', '/getResourceTree.do', 0, NULL, 52, 'enable'),
	(1071, 'resource_00d991a63dff45c9ab5e9e8ca2ff0a3f', 'Jsoup数据抓取', 'Jsoup数据抓取', '/toJsoupList.do', 1, '5432760c8bf64e01b432d8ff0c715293', 1073, 'enable'),
	(1073, 'resource_d253d4e4b09c47f1ad3e2d67b0598900', '数据抓取-我的爬虫', '数据抓取-我的爬虫', 'f30c7f951a4f42f5872111cb06d0a7a9', 1, 'f30c7f951a4f42f5872111cb06d0a7a9', -1, 'enable'),
	(1074, 'resource_e04bfb11a4b7492f80de57d4b773ee5b', '查询收藏网址列表', '查询收藏网址列表', '/queryInterList.do', 0, NULL, 1071, 'enable'),
	(1075, 'resource_e5b0169062fb405e807d3888232a63e7', '抓取网址数据', '抓取网址数据', '/goJsoup.do', 0, NULL, 1071, 'enable'),
	(1076, 'resource_0da7bee0592f4d6a9168cc5bf1140ab7', '发送邮件', '发送邮件', '/sendEmail.do', 0, NULL, 1071, 'enable');
/*!40000 ALTER TABLE `t_sys_resources` ENABLE KEYS */;


-- 导出  表 grp.t_sys_role 结构
CREATE TABLE IF NOT EXISTS `t_sys_role` (
  `role_id` bigint(19) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `role_code` varchar(255) DEFAULT NULL,
  `role_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `idx_role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  grp.t_sys_role 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` (`role_id`, `role_name`, `role_code`, `role_desc`) VALUES
	(1, '超级管理员', 'ROLE_SUPERSYSTEM', '超级管理员'),
	(2, '普通管理员', 'ROLE_SYSTEM', '普通管理员');
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;


-- 导出  表 grp.t_sys_role_privilege_ref 结构
CREATE TABLE IF NOT EXISTS `t_sys_role_privilege_ref` (
  `ref_id` bigint(19) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(50) NOT NULL,
  `privilege_code` varchar(50) NOT NULL,
  PRIMARY KEY (`ref_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- 正在导出表  grp.t_sys_role_privilege_ref 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `t_sys_role_privilege_ref` DISABLE KEYS */;
INSERT INTO `t_sys_role_privilege_ref` (`ref_id`, `role_code`, `privilege_code`) VALUES
	(45, 'ROLE_SUPERSYSTEM', 'ROLE_SUPERSYSTEM');
/*!40000 ALTER TABLE `t_sys_role_privilege_ref` ENABLE KEYS */;


-- 导出  表 grp.t_sys_user 结构
CREATE TABLE IF NOT EXISTS `t_sys_user` (
  `u_user_id` bigint(19) NOT NULL AUTO_INCREMENT,
  `u_user_name` varchar(50) DEFAULT NULL,
  `u_login_name` varchar(50) DEFAULT NULL,
  `u_login_pwd` varchar(80) DEFAULT NULL,
  `u_status` int(1) DEFAULT '1' COMMENT '1:不可用,0:可用',
  `crt_date` datetime DEFAULT NULL,
  `mod_date` datetime DEFAULT NULL,
  `u_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `u_user_code` varchar(255) DEFAULT NULL,
  `u_mobile` varchar(30) DEFAULT NULL,
  `ext_sys_id` bigint(19) DEFAULT '0' COMMENT '外部系统ID',
  `ext_sys_user_id` bigint(19) DEFAULT '0' COMMENT '外部系统用户ID',
  `ext_sys_object_id` int(19) DEFAULT '0' COMMENT '外部系统对象ID',
  `ext_sys_object_value` varchar(50) DEFAULT NULL COMMENT '外部系统对象值',
  `is_internal` varchar(50) DEFAULT NULL COMMENT 'internal 内部  external 外部',
  `mail_box` varchar(100) DEFAULT NULL COMMENT '邮箱号码，内部用户时必填',
  `secret_key` varchar(100) DEFAULT NULL COMMENT '用户秘钥',
  `is_auth` int(1) DEFAULT '0' COMMENT '0：未授权 1：已授权',
  PRIMARY KEY (`u_user_id`),
  UNIQUE KEY `idx_u_code` (`u_user_code`),
  UNIQUE KEY `idx_u_login_name` (`u_login_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

-- 正在导出表  grp.t_sys_user 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` (`u_user_id`, `u_user_name`, `u_login_name`, `u_login_pwd`, `u_status`, `crt_date`, `mod_date`, `u_remark`, `u_user_code`, `u_mobile`, `ext_sys_id`, `ext_sys_user_id`, `ext_sys_object_id`, `ext_sys_object_value`, `is_internal`, `mail_box`, `secret_key`, `is_auth`) VALUES
	(1, '龙晨', 'chen', '5f3187aa1a371fe008cdce6eadd6d412', 0, '2015-07-18 11:58:40', '2016-11-04 12:10:31', 'chen', 'admin', '13012345678', 0, 0, 0, NULL, NULL, NULL, 'PJWC57GSL7WMQZ27', 0),
	(81, '龚荣平', 'gongrp', '12eac10487113127b8e93da6aae0f0c1', 0, '2016-03-30 13:42:58', '2016-11-04 12:02:05', 'gongrp', 'gongrp', '18679176130', 1, 0, 60237, '18679176130', NULL, NULL, 'NGEOOB6JQX6GVQTW', 1);
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;


-- 导出  表 grp.t_sys_user_profile_history 结构
CREATE TABLE IF NOT EXISTS `t_sys_user_profile_history` (
  `AUTO_ID` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增长主键列，没有业务含义',
  `USER_ID` int(11) DEFAULT NULL,
  `CHANGE_ITEM` varchar(50) DEFAULT NULL COMMENT 'password:改密码',
  `ODD_VALUE` varchar(50) DEFAULT NULL,
  `NEW_VALUE` varchar(50) DEFAULT NULL,
  `ADD_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`AUTO_ID`),
  KEY `USER_ID_ADD_TIME` (`USER_ID`,`ADD_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户资料变更历史';

-- 正在导出表  grp.t_sys_user_profile_history 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `t_sys_user_profile_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_sys_user_profile_history` ENABLE KEYS */;


-- 导出  表 grp.t_sys_user_role_ref 结构
CREATE TABLE IF NOT EXISTS `t_sys_user_role_ref` (
  `ref_id` bigint(19) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(255) DEFAULT NULL,
  `role_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ref_id`)
) ENGINE=InnoDB AUTO_INCREMENT=441 DEFAULT CHARSET=utf8;

-- 正在导出表  grp.t_sys_user_role_ref 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `t_sys_user_role_ref` DISABLE KEYS */;
INSERT INTO `t_sys_user_role_ref` (`ref_id`, `user_code`, `role_code`) VALUES
	(271, 'chen', 'ROLE_SUPERSYSTEM'),
	(434, 'gongrp', 'ROLE_SUPERSYSTEM'),
	(435, 'gongrp', 'ROLE_ADMIN'),
	(436, 'gongrp', 'ROLE_MODERATOR'),
	(437, 'gongrp', 'ROLE_GAMEADMIN'),
	(438, 'gongrp', 'ROLE_UserReward'),
	(439, 'gongrp', 'ROLE_USERINFO'),
	(440, 'gongrp', 'ROLE_SYSADMIN');
/*!40000 ALTER TABLE `t_sys_user_role_ref` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
