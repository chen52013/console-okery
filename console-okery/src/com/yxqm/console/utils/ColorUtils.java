package com.yxqm.console.utils;

import org.apache.commons.lang3.StringUtils;

public class ColorUtils {
	public static String getColorByLeagueId(String leagueId) {
		String color = "#518ed2";
		try {
			String[] colors = { "#006666", "#518ed2", "#e8811a", "#949720", "#8f6dd6", "#53ac98", "#ff9966", "#ca00ca",
					"#8d8abd", "#996733", "#8c8a64", "#999012", "#ff6633", "#a2e76f", "#1ba570", "#990099" };

			if (!StringUtils.isEmpty(leagueId)) {
				int arrIndex = Integer.parseInt(leagueId) % 7;
				color = colors[(arrIndex + 1)];
			}
		} catch (Exception e) {
		}
		return color;
	}
}