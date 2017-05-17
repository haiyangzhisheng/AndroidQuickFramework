package com.aaagame.proframework.utils;

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

/**
 * @description 公共�?-方法
 * @author zh
 * @version 1.0
 */
public class Check_Format {
	/**
	 * 是否是手机号
	 *
	 * @param mobiles
	 * @return boolean(true为手机号)
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				// .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
				.compile("^((13[0-9])|(15[^4,\\D])|(17[0,6-8])|(18[0-9])|(14[5,7]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 密码格式验证
	 * 6-12位非中文
	 * @return true
	 */
	public static boolean passwordFormat(String password){
//		 String regex = "(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,12}" ;
//		String regex = "^[^\u4e00-\u9fa5]{6,20}$" ;
		String regex = "^\\w{6,20}$";
		return match( regex ,password );
	}


	/**
	 * 确定正则匹配正确与否
	 */
	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find() && matcher.groupCount() >= 0) {
			String chinaStr = matcher.group(0);
			if (str.length() == chinaStr.length()) {
				return true;
			}
		}
		return false;
	}
	// 加密
	public static String encodeBase64(String str) {
		String encodedString = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
		return encodedString;
	}
	// 解密
	public static String decodeBase64(String str) {
		String decodedString =new String(Base64.decode(str, Base64.DEFAULT));
		return decodedString;
	}



	//使用BASE64做转码。
	// 加密
	public static String Encrypt(String sSrc)  {
		 String sKey = Contants.sKey;
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 32) {
			System.out.print("Key长度不是16位");
			return null;
		}

		String key = sKey.substring(0,16);
		String ivstr = sKey.substring(16);
//        String md5Pass = getMd5(sSrc);

		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		IvParameterSpec iv = new IvParameterSpec(ivstr.getBytes()); // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		try {
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		byte[] encrypted = new byte[0];
		try {
			encrypted = cipher.doFinal(sSrc.getBytes());
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码。
	}
	/**
	 * 保留两位小数
	 * @param d
	 * @return
	 */
	public static String priceFormat(double d) {
		DecimalFormat df   = new DecimalFormat("######0.00");
		String s = df.format(d);
		return s;
	}

	/**
	 * 获取显示日期
	 *
	 * @return
	 */
	public static String getTimeString(Long time) {
		try {
			Date date = new Date(time);
			SimpleDateFormat sdf;
			sdf = new SimpleDateFormat("yyyy.MM.dd");
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
	/**
	 * 获取显示日期
	 *
	 * @return
	 */
	public static String getTimeString1(Long time) {
		try {
			Date date = new Date(time);
			SimpleDateFormat sdf;
			sdf = new SimpleDateFormat("yyyy/MM/dd");
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}
	/**
	 * 将秒格式化成字符串
	 * @param time 秒
	 * @return
	 */
	public static String getTime(long time, String fromat){
		String s = "";
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(time );
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(fromat);
			s =  format.format(gc.getTime());
		} catch (Exception e) {
			return s;
		}
		return s ;

	}


	/**
	 * 验证身份证
	 * @param IDStr
	 * @return
	 */
	public static String IDCardValidate(String IDStr) {
		String errorInfo = "ok";// 记录错误信息

		String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] ValCodeArr1 = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return errorInfo;
		}
		// =======================(end)========================
		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return errorInfo;
		}
		// =======================(end)========================
		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return errorInfo;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
					strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "身份证生日不在有效范围。";
				return errorInfo;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return errorInfo;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return errorInfo;
		}
		// =====================(end)=====================
		// ================ 地区码时候有效 ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return errorInfo;
		}
		// ==============================================
		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		String strVerifyCode1 = ValCodeArr1[modValue];
		String A = Ai + strVerifyCode;
		String A1 = Ai + strVerifyCode1;
		if (IDStr.length() == 18) {
			if ((A.equals(IDStr)==false && A1.equals(IDStr)== false)) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return errorInfo;
			}
		} else {
			errorInfo = "ok";
			return errorInfo;
		}

		// =====================(end)=====================
		return errorInfo;
	}

	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	public static boolean isDataFormat(String str) {
		boolean flag = false;
		// String
		// regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1 = Pattern.compile(regxStr);
		Matcher isNo = pattern1.matcher(str);
		if (isNo.matches()) {
			flag = true;
		}
		return flag;
	}

}
