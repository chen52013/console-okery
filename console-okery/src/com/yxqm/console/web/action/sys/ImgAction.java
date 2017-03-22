package com.yxqm.console.web.action.sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yxqm.console.web.context.CustomPropertyPlaceholderConfigurer;

@Controller
public class ImgAction {

	private static final Logger LOG = LoggerFactory.getLogger(ImgAction.class);
	
	@RequestMapping(value = "queryImg.do", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void queryImg(HttpServletRequest request,
			HttpServletResponse response) {
		String imgRelativePath = request.getParameter("imgRelativePath");
		String img_suffix = imgRelativePath.substring(imgRelativePath
				.lastIndexOf(".") + 1);
		String imgRootPath = CustomPropertyPlaceholderConfigurer.getProperty("img.root.path");
		String fullPath = String.format("%s%s%s", imgRootPath,
				System.getProperty("file.separator"), imgRelativePath);
		LOG.info("图片路径:{}",fullPath);
		OutputStream output = null;
		InputStream imageIn =null;
		try {
			output = response.getOutputStream();
			// 得到图片的文件流
			File file = new File(fullPath);
			if(file.exists()){
				imageIn = new FileInputStream(file);
				ImageIO.write(ImageIO.read(imageIn), img_suffix, output);
			}else{
				InputStream inputStream =request.getServletContext().getResourceAsStream("/resources/Images/null.png");
				ImageIO.write(ImageIO.read(inputStream), "png", output);
			}
			
		} catch (IOException e) {
			LOG.error("查看图片异常", e);
			return;
		} finally {
			if (null != output) {
				try {
					output.flush();
					output.close();
				} catch (IOException e) {
					LOG.error("查看图片关闭流异常", e);
					return;
				}

			}
		}

	}
}
