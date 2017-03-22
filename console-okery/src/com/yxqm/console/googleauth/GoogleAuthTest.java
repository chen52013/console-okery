package com.yxqm.console.googleauth;

import org.junit.Test;

import java.io.PrintStream;

public class GoogleAuthTest {
	static String savedSecret = "NDHE3INRLOKG2P5U";

	@Test
	public void genSecretTest() {
		String secret = GoogleAuthenticator.generateSecretKey();
		String url = GoogleAuthenticator.getQRBarcodeURL("kobe", secret);
		System.out.println("Please register " + url);
		System.out.println("Secret key is " + secret);
	}

	@Test
	public void authTest() {
		long code = 950630L;
		long t = System.currentTimeMillis();
		GoogleAuthenticator ga = new GoogleAuthenticator();
		ga.setWindowSize(5);
		boolean is_true = ga.check_code(savedSecret, code, t);
		if (is_true)
			System.err.println("��֤�ɹ�");
		else
			System.err.println("��֤ʧ��");
	}
}