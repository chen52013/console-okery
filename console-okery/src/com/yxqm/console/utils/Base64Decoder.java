package com.yxqm.console.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Base64Decoder {
	public static String encodeImgageToBase64(File imageFile, String imgType) throws IOException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		ByteArrayOutputStream outputStream = null;
		BufferedImage bufferedImage = ImageIO.read(imageFile);
		outputStream = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, imgType, outputStream);
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
	}

	public static byte[] decodeBase64ToImage(String base64) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decoderBytes = decoder.decodeBuffer(base64);
		return decoderBytes;
	}
}
