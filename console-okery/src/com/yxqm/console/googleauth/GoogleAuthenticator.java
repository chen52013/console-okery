package com.yxqm.console.googleauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.utils.URIBuilder;

public class GoogleAuthenticator {
	public static final int SECRET_SIZE = 10;
	public static final String SEED = "g8GjEvTbW5oVSV7avLBdwIHqGlUYNzKFI7izOF8GwLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx";
	public static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";
	int window_size = 3;

	public void setWindowSize(int s) {
		if ((s >= 1) && (s <= 17))
			this.window_size = s;
	}

	public static String generateSecretKey() {
		SecureRandom sr = null;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
			sr.setSeed(Base64
					.decodeBase64("g8GjEvTbW5oVSV7avLBdwIHqGlUYNzKFI7izOF8GwLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx"));
			byte[] buffer = sr.generateSeed(10);
			Base32 codec = new Base32();
			byte[] bEncodedKey = codec.encode(buffer);
			return new String(bEncodedKey);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String formatLabel(String issuer) {
		StringBuilder sb = new StringBuilder();
		if (issuer != null) {
			if (issuer.contains(":")) {
				throw new IllegalArgumentException("Issuer cannot contain the ':' character.");
			}
			sb.append(issuer);
			sb.append(":");
		}
		return sb.toString();
	}

	public static String getOtpAuthTotpURL(String issuer, String credentials) {
		URIBuilder uri = new URIBuilder().setScheme("otpauth").setHost("totp")
				.setPath(new StringBuilder().append("/").append(formatLabel(issuer)).toString())
				.setParameter("secret", credentials);

		if (issuer != null) {
			if (issuer.contains(":")) {
				throw new IllegalArgumentException("Issuer cannot contain the ':' character.");
			}

			uri.setParameter("issuer", issuer);
		}

		return uri.toString();
	}

	public static String getQRBarcodeURL(String user, String secret) {
		return String.format("https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=%s",
				new Object[] { internalURLEncode(getOtpAuthTotpURL(user, secret)) });
	}

	private static String internalURLEncode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 encoding is not supported by URLEncoder.", e);
		}
	}

	public boolean check_code(String secret, long code, long timeMsec) {
		Base32 codec = new Base32();
		byte[] decodedKey = codec.decode(secret);
		long t = timeMsec / 1000L / 30L;
		for (int i = -this.window_size; i <= this.window_size; i++) {
			long hash;
			try {
				hash = verify_code(decodedKey, t + i);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
			if (hash == code) {
				return true;
			}
		}
		return false;
	}

	private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] data = new byte[8];
		long value = t;
		for (int i = 8; i-- > 0; value >>>= 8) {
			data[i] = ((byte) (int) value);
		}
		SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signKey);
		byte[] hash = mac.doFinal(data);
		int offset = hash[19] & 0xF;
		long truncatedHash = 0L;
		for (int i = 0; i < 4; i++) {
			truncatedHash <<= 8;
			truncatedHash |= hash[(offset + i)] & 0xFF;
		}
		truncatedHash &= 2147483647L;
		truncatedHash %= 1000000L;
		return (int) truncatedHash;
	}
}