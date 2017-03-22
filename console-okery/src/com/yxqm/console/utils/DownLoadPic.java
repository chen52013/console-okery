package com.yxqm.console.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownLoadPic {
	public void getDoc() throws IOException {
		File f = new File("E://imgs");
		if (!f.exists()) {
			f.mkdirs();
		}

		Document doc = Jsoup.connect("http://china.nba.com/?gr=www").get();

		Elements pngs = doc.select("img[src~=(?i)\\.(png|jpe?g)]");

		for (Element e : pngs) {
			String src = e.attr("src");
			System.err.println(src);

			String imageName = src.substring(src.lastIndexOf("/") + 1, src.length());

			URL url = new URL(src);
			URLConnection uri = url.openConnection();

			InputStream is = uri.getInputStream();

			OutputStream os = new FileOutputStream(new File("E://imgs", imageName));
			byte[] buf = new byte[1024];
			int l = 0;
			while ((l = is.read(buf)) != -1)
				os.write(buf, 0, l);
		}
	}

	public static void main(String[] args) throws IOException {
		new DownLoadPic().getDoc();
	}
}