package com.demand.server.well_family_house.main.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.demand.server.well_family_house.common.dto.Category;
import com.demand.server.well_family_house.common.dto.User;
import com.demand.server.well_family_house.common.flag.LogFlag;
import com.demand.server.well_family_house.common.util.JwtUtil;
import com.demand.server.well_family_house.main.service.MainService;
import com.demand.server.well_family_house.user.web.USERController;

@Service
public class MainServiceImpl implements MainService {

	@Autowired
	private MainMapper mainMapper;

	@Autowired
	private JwtUtil jwtUtil;

	private static final Logger logger = LoggerFactory.getLogger(USERController.class);

	@Autowired
	private JavaMailSender mailSender;

	public static void log(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();
		String className = ste[0].getClassName();
		String methodName = ste[0].getMethodName();
		int lineNumber = ste[0].getLineNumber();
		String fileName = ste[0].getFileName();

		if (LogFlag.printFlag) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception: " + e.getMessage());
				logger.info(className + "." + methodName + " " + fileName + " " + lineNumber + " " + "line");
			}
		}
	}

	@Override
	public int selectEmailCheck(String email) throws Exception {
		return mainMapper.selectEmailCheck(email);
	}

	@Override
	public User selectLogin(String email, String password) throws Exception {
		User user = mainMapper.selectLogin(email, password);

		if (user != null) {
			user.setAccess_token(jwtUtil.generateToken(user.getLevel()));
			return user;
		} else {
			return null;
		}
	}

	@Override
	public void insertJoin(String email, String password, String name, String birth, String phone,
			int login_category_id) throws Exception {
		mainMapper.insertJoin(email, password, name, birth, phone, login_category_id);
	}

	@Override
	public ArrayList<Category> selectReportCategoryList() throws Exception {
		return mainMapper.selectReportCategoryList();
	}

	@Override
	public void updateTemperaryPassword(int user_id, String user_email, String user_name) throws Exception {
		boolean update_flag = false;
		String pwd = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
		String sha256_pwd = encrypt_SHA1(pwd);
		
		try {
			mainMapper.updateTemperaryPassword(user_id, sha256_pwd);
			update_flag = true;
		} catch (Exception e) {
			update_flag = false;
		}
		
		if (update_flag) {
			try {
				MimeMessage message = mailSender.createMimeMessage();

				Address email = new InternetAddress(user_email);

				message.addRecipient(Message.RecipientType.TO, email);
				message.setFrom("demand.svc@gmail.com");
				message.setSubject("웰 패밀리 하우스, 임시 비밀번호가 발급되었습니다.");

				String content = "<div style=\"max-width: 595px; margin: 0 auto\">"
						+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width: 595px; width: 100%; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif; background-color: #fff; -webkit-text-size-adjust: 100%; text-align: left"
						+ "align=\"center\"><tbody><tr><td height=\"30\"></td></tr><tr><td style=\"padding-right: 27px; padding-left: 15px\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
						+ "<tbody><tr><td style=\" width=\"61\"\"><img  src=\"http://d1mluvx5hwpxr3.cloudfront.net/apps/well_family_house/images/banner/logo.png\" alt=\"Logo\" width=\"180\"></td></tr></tbody></table></td></tr>"
						+ "<tr><td height=\"13\"></td></tr>"
						+ "<tr><td style=\"padding-right: 27px; padding-left: 18px; line-height: 34px; font-size: 29px; color: #424240; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">"
						+ "회원님의 <br><span style=\"color: #542920\">임시 비밀번호</span>가 발급되었습니다. </td></tr>"
						+ "<tr><td height=\"22\"></td></tr><tr>   <td height=\"1\" style=\"background-color: #e5e5e5;\"></td></tr>"

						+ "<tr><td style=\"padding-top: 24px; padding-right: 27px; padding-bottom: 32px; padding-left: 20px\">"

						+ "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\" width=\"100%\"> <tbody> "
						+ "<tr><td height=\"24\"></td></tr>"
						+ "<tr><td style=\"font-size: 14px; color: #696969; line-height: 24px; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">"

						+ "<table cellpadding=\"0\" cellspacing=\"0\"    style=\"width: 100%; margin: 0; padding: 0\"> <tbody>"
						+ "<tr><td height=\"23\"    style=\"font-weight: bold; color: #000; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">발급 정보</td></tr>"

						+ "<tr><td height=\"2\" style=\"background: #424240\"></td> </tr> <tr> <td height=\"20\"></td></tr>"
						+ "<tr><td>"
						+ "<table cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; margin: 0; padding: 0\"> <tbody> <tr> <td width=\"110\" style=\"padding-bottom: 5px; color: #696969; line-height: 23px; font-size: 10pt; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">"
						+ "이메일</td><td style=\"padding-bottom: 5px; color: #000; line-height: 23px; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\"><span style=\"color: #542920\">"
						+ email + "</span>" + "</td></tr>"

						+ "<tr><td width=\"110\"style=\"padding-bottom: 5px; color: #696969; line-height: 23px; font-size: 10pt; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">임시 비밀번호</td>"
						+ "<td style=\"padding-bottom: 5px; color: #000; line-height: 23px; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\"><span style=\"color: #542920\">"
						+ pwd + "</span></td>" + "</tr><tr>"

						+ "<td width=\110\" style=\"padding-bottom: 5px; color: #696969; line-height: 23px; font-size: 10pt; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">이름</td>"
						+ "<td style=\"padding-bottom: 5px; color: #000; line-height: 23px; vertical-align: top; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\"><span style=\"color: #542920\">"
						+ user_name + "</span></td></tr>" + "</tbody></table>" + "</td></tr>"
						+ "<tr><td height=\"20\"></td></tr>"
						+ "<tr><td height=\"1\" style=\"background: #d5d5d5\"></td></tr>" + "</tbody></table>"

						+ "</td></tr>" + "<tr><td height=\"24\"></td></tr>"
						+ "<tr><td   style=\"font-size: 14px; color: #696969; line-height: 24px; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">"
						+ "   <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\" width=\"100%\"><tbody>"
						+ "<tr><td style=\"width: 2px; padding-top: 2px; padding-right: 5px; vertical-align: top;\">-</td>"
						+ "<td style=\"text-align: left; vertical-align: middle\">로그인   후 새로운 비밀번호로 변경하여 이용하세요.</td></tr>   <tr><td    style=\"width: 2px; padding-top: 2px; padding-right: 5px; vertical-align: top;\">-</td>   <td style=\"text-align: left; vertical-align: middle\">발급된 비밀번호는 웰패밀리하우스 관리자도 알 수 없도록 암호화 되어 있습니다.</td>"
						+ "</tr></tbody></table></td></tr>" + "<tr><td height=\"24\"></td>" + "</tr>"
						+ "<tr><td   style=\"font-size: 14px; color: #696969; line-height: 24px; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif;\">"
						+ "웰 패밀리 하우스를 이용해 주셔서 감사합니다.<br> 더욱 편리한 서비스를 제공하기 위해 항상 최선을 다하겠습니다.</td></tr>"
						+ "</tbody></table>" + "</td></tr>"
						+ "<tr><td style=\"padding-top: 26px; padding-left: 21px; padding-right: 21px; padding-bottom: 13px; background: #f9f9f9; font-size: 12px; font-family: '나눔고딕', NanumGothic, '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif; color: #696969; line-height: 17px\">"
						+ "본 메일은 발신전용 입니다</td></tr>"
						+ "<tr><td    style=\"padding-left: 21px; padding-right: 21px; padding-bottom: 57px; background: #f9f9f9; font-size: 12px; font-family: Helvetica; color: #696969; line-height: 17px\"> Copyright ⓒ <strong><a target=\"_blank\" href=\"http://www.demand.co.kr\"   style=\"color: #542920; text-decoration: none;\">Demand</a></strong> Corp.All Rights Reserved.</td></tr>"
						+ "</tbody></table></div>";

				message.setText(content, "utf-8", "html");
				mailSender.send(message);

			} catch (AddressException e) {
				log(e);
			} catch (MessagingException e) {
				log(e);
			}
		}
	}

	public String encrypt_SHA1(String pwd) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(pwd.getBytes(), 0, pwd.length());

		} catch (NoSuchAlgorithmException e) {
			log(e);
		}

		return String.format("%064x", new java.math.BigInteger(1, messageDigest.digest()));
	}

	@Override
	public User selectUserInfoFromEmail(String email) throws Exception {
		return mainMapper.selectUserInfoFromEmail(email);
	}

}
