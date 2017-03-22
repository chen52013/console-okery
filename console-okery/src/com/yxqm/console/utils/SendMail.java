package com.yxqm.console.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendMail {
	private String username = null;
	private String password = null;
	private Authenticator auth = null;
	private MimeMessage mimeMessage = null;
	private Properties pros = null;
	private Multipart multipart = null;
	private BodyPart bodypart = null;

	public SendMail(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void initMessage() {
		this.auth = new Email_Autherticator();
		Session session = Session.getDefaultInstance(this.pros, this.auth);
		session.setDebug(true);
		this.mimeMessage = new MimeMessage(session);
	}

	public void setPros(Map<String, String> map) {
		this.pros = new Properties();
		for (Map.Entry entry : map.entrySet())
			this.pros.setProperty((String) entry.getKey(), (String) entry.getValue());
	}

	public void setDefaultMessagePros(String sub, String text, String rec)
			throws MessagingException, UnsupportedEncodingException {
		this.mimeMessage.setSubject(sub);
		this.mimeMessage.setText(text);
		this.mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(rec));
		this.mimeMessage.setSentDate(new Date());
		this.mimeMessage.setFrom(new InternetAddress(this.username, this.username));
	}

	public void setSubject(String subject) throws MessagingException {
		this.mimeMessage.setSubject(subject);
	}

	public void setDate(Date date) throws MessagingException {
		this.mimeMessage.setSentDate(new Date());
	}

	public void setText(String text) throws MessagingException {
		this.mimeMessage.setText(text);
	}

	public void setHeader(String arg0, String arg1) throws MessagingException {
		this.mimeMessage.setHeader(arg0, arg1);
	}

	public void setRecipient(String recipient) throws MessagingException {
		this.mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
	}

	public String setRecipients(List<String> recs) throws AddressException, MessagingException {
		if (recs.isEmpty()) {
			return "接收人地址为空!";
		}
		for (String str : recs) {
			this.mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(str));
		}
		return "加入接收人地址成功!";
	}

	public String setRecipients(StringBuffer sb) throws AddressException, MessagingException {
		if ((sb == null) || ("".equals(sb))) {
			return "字符串数据为空!";
		}
		new InternetAddress();
		Address[] address = InternetAddress.parse(sb.toString());
		this.mimeMessage.addRecipients(Message.RecipientType.TO, address);
		return "收件人加入成功";
	}

	public void setFrom(String from) throws UnsupportedEncodingException, MessagingException {
		this.mimeMessage.setFrom(new InternetAddress(this.username, from));
	}

	public String sendMessage() throws MessagingException {
		Transport.send(this.mimeMessage);
		return "success";
	}

	public void setMultipart(String file) throws MessagingException, IOException {
		if (this.multipart == null) {
			this.multipart = new MimeMultipart();
		}
		this.multipart.addBodyPart(writeFiles(file));
		this.mimeMessage.setContent(this.multipart);
	}

	public void setMultiparts(List<String> fileList) throws MessagingException, IOException {
		if (this.multipart == null) {
			this.multipart = new MimeMultipart();
		}
		for (String s : fileList) {
			this.multipart.addBodyPart(writeFiles(s));
		}
		this.mimeMessage.setContent(this.multipart);
	}

	public void setContent(String s, String type) throws MessagingException {
		if (this.multipart == null) {
			this.multipart = new MimeMultipart();
		}
		this.bodypart = new MimeBodyPart();
		this.bodypart.setContent(s, type);
		this.multipart.addBodyPart(this.bodypart);
		this.mimeMessage.setContent(this.multipart);
		this.mimeMessage.saveChanges();
	}

	public BodyPart writeFiles(String filePath) throws IOException, MessagingException {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new IOException("文件不存在!请确定文件路径是否正确");
		}
		this.bodypart = new MimeBodyPart();
		DataSource dataSource = new FileDataSource(file);
		this.bodypart.setDataHandler(new DataHandler(dataSource));

		this.bodypart.setFileName(MimeUtility.encodeText(file.getName()));
		return this.bodypart;
	}

	public class Email_Autherticator extends Authenticator {
		public Email_Autherticator() {
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(SendMail.this.username, SendMail.this.password);
		}
	}
}