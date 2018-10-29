package com.wj.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;



/**
 * 工具类
 * @author liuJack
 *
 */
public final class Tools{


	public static final BigDecimal getTimeToDay(Date date,BigDecimal lv,Integer state) {
		if(state==0){
			BigDecimal d = new BigDecimal(getDayByDate(date))
					.multiply(lv.divide(new BigDecimal(100)));
			return d;
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * object数组集合转为map集合
	 * @param list 需要转换的集合对象
	 * @param nameMapper 名称映射数组
	 * @return 结果集合对象
	 */
	public static final List<Map<String, Object>> objArr2Map(List<Object[]> list,String... nameMapper){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(list==null)return result;
		if(nameMapper==null||nameMapper.length==0)throw new RuntimeException("映射的名称不能为空");
		for (Object[] objects : list) {
			if(objects.length<nameMapper.length) throw new RuntimeException("映射名称与指定的数组不对应!");
			Map<String, Object> value = new HashMap<String,Object>();
			for(int i = 0;i<nameMapper.length;i++){
				value.put(nameMapper[i], objects[i]);
			}
			result.add(value);
		}
		return result;
	}

	/**
	 * 加密
	 * @param data 需要加密的字符
	 * @return 已加密的结果
	 * @throws Exception 
	 */
	public static final String rsaEncrypt(String data) throws Exception{
		return RSAUtils.encrypt(data);
	}

	/**
	 * 解密
	 * @param data 需要解密密的字符
	 * @return 已解密的结果
	 * @throws Exception 
	 */
	public static final String rsaDecode(String data) throws Exception{
		return RSAUtils.decrypt(data);
	}


	/**
	 * MD5加密
	 * @param s
	 * @param encry 加密基数
	 * @return
	 * @throws Exception 
	 */
	public static final String MD5(String str,String encry) throws Exception{
		StringBuffer stringBuffer = new StringBuffer(str);
		if (encry!=null) {
			stringBuffer.append(encry);
		}
		return MD5_GO(stringBuffer.toString());
	}
	

	/**
	 * MD5加密
	 * @param s
	 * @return
	 * @throws Exception 
	 */
	public static final String MD5(byte[] bs) throws Exception{
		return MD5_GO(bs);
	}
	
	public static final boolean isInteger(Object object){
		if(isNull(object)) return false;
		try {
			Integer.parseInt(object.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static final boolean isLong(Object object){
		if(isNull(object)) return false;
		try {
			Long.parseLong(object.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static final boolean isDouble(Object object){
		if(isNull(object)) return false;
		try {
			Double.parseDouble(object.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 将byte数组转为对象
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T byteArrToObj(byte[] bytes){
		if(bytes==null) return null;
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			Object object = objectInputStream.readObject();
			if(objectInputStream!=null) objectInputStream.close();
			if(inputStream!=null) inputStream.close();
			return (T) object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对象转为byte数组
	 * @param object
	 * @return
	 */
	public static final byte[] ObjToByteArr(Object object){
		if(object==null) return null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(object);
			return outputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Record> listObjArr2Record(List<Object[]> list,String... keys){
		if(list==null) return null;
		List<Record> records = new ArrayList<>();
		for (Object[] objects : list) {
			Record record = new Record();
			for (int i = 0; i < keys.length; i++) {
				record.put(keys[i], objects[i]);
			}
			records.add(record);
		}
		return records;
	}
	
	public static <T> List<T> listObjArr2Entity(List<Object[]> list,Class<T> clazz,String... keys){
		List<Record> records = listObjArr2Record(list, keys);
		Field[] fields = clazz.getDeclaredFields();
		List<T> ts = new ArrayList<>();
		for (Record record : records) {
			try {
				T t = clazz.newInstance();
				for (Field field : fields) {
					field.setAccessible(true);
					if(record.get(field.getName())!=null){
						Object object = record.get(field.getName());
						if(field.getType() == Long.class && object instanceof BigInteger){
							if(object != null)
								object = ((BigInteger)object).longValue();
						}
						field.set(t, object);
					}
				}
				ts.add(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ts;
	}

	public static Date addDay(Date date, Integer day) {
		Date result = new Date(date.getTime()+day*24*60*60*1000);
		return result;
	}
	

	/**
	 * 检查一个对象是否不为null 或空的字符串
	 * @param object
	 * @return
	 */
	public static final boolean notNull(Object object){
		if(object==null) return false;
		if("".equals(object.toString())) return false;
		return true;
	}
	
	/**
	 * 检查一个对象是否为null 或空的字符串
	 * @param object
	 * @return
	 */
	public static final boolean isNull(Object object){
		if(object==null) return true;
		if("".equals(object.toString())) return true;
		return false;
	}
	
	
	/**
	 * 检查一个对象是否为null 或空的字符串或为"null"
	 * @param object
	 * @return
	 */
	public static final boolean isNullOrNullStr(Object object){
		if(object==null) return true;
		if("".equals(object.toString())) return true;
		if(object.toString().trim().equalsIgnoreCase("null")) return true;
		return false;
	}
	
	/**
	 * 检查字符串是否为空的字符串(去除空格后的结果),如果对象为null也返回true
	 * @param obj 字符串对象
	 * @return
	 */
	public static final boolean isEmpty(String obj){
		if(obj==null) return true;
		if("".equals(obj.trim())) return true;
		return false;
	}
	
	/**
	 * 检查字符串是否不为空的字符串(去除空格后的结果)
	 * @param obj 字符串对象
	 * @return
	 */
	public static final boolean notEmpty(String obj){
		if(obj==null) return false;
		if("".equals(obj.trim())) return false;
		return true;
	}

	private static final String MD5_GO(String s) {
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static final String MD5_GO(byte[] btInput) throws Exception {
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		mdInst.update(btInput);
		byte[] md = mdInst.digest();
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}
	
	
	/**
	 * 时间转换函数,将时间格式的字符串转为util时间
	 * @param dateStr 时间格式的字符串
	 * @param pattern 字符串格式
	 * @return
	 */
	public static final Date strToUtilDate(String dateStr,DatePattern pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern.getValue());
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 时间转换函数,将时间格式的字符串转为util时间
	 * @param dateStr 时间格式的字符串
	 * @param pattern 字符串格式
	 * @return
	 */
	public static final boolean strIsUtilDate(String dateStr,DatePattern pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern.getValue());
		try {
			sdf.parse(dateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 时间转换函数,将时间格式的字符串转为util时间(格式为yyyy-MM-dd)
	 * @param dateStr 时间格式的字符串
	 * @return
	 */
	public static final Date strToUtilDate(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat(DatePattern.YYYYMMDD.getValue());
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将sql时间转为util时间
	 * @param date sql时间
	 * @return
	 */
	public static final Date sqlDate2UtilDate(java.sql.Date date){
		return new Date(date.getTime());
	}
	
	/**
	 * 将util时间转为sql时间
	 * @param date util时间
	 * @return
	 */
	public static final java.sql.Date utilDate2SqlDate(Date date){
		return new java.sql.Date(date.getTime());
	}
	
	/**
	 * 检查对象数组不为null并且不为空的字符串
	 * @param objects
	 * @return
	 */
	public static final boolean notNulls(Object...objects){
		for (Object object : objects) {
			if(Tools.isNull(object)) return false;
			if(Tools.isEmpty(object.toString())) return false;
		}
		return true;
	}
	
	/**
	 * 处理字符串 ,如果为空,则返回空的字符串,否则返回字符串值
	 * @param object
	 * @return
	 */
	public static final String handleStr(Object object){
		if (Tools.isNull(object)) {
			return "";
		}else {
			return object.toString();
		}
	}
	
	/**
	 * 将字符串转为二维数组
	 * @param str
	 * @return
	 */
	public static Object[][] toArray(String str){
		Object[] ids=str.split(",");
		Object[][] objs=new Object[ids.length][];
		for (int i = 0; i < ids.length; i++) {
			objs[i]=new Object[]{ids[i]};
		}
		return objs;
	}
	
	/**
	 * 根据开始日期和结束日期获得抄表的定时字符串
	 * @param begin
	 * @param end
	 * @return
	 */
	public static final String getChaoBiaoStr(Integer begin,Integer end){
		StringBuffer sb = new StringBuffer("0 0 0 ");
		sb.append(begin);
		sb.append("-");
		sb.append(end);
		sb.append(" * ?");
		return sb.toString();
	}
	/**
	 * 根据日期获得计费的定时字符串
	 * @param day
	 * @return
	 */
	public static final String getJiFeiStr(Integer day){
		StringBuffer sb = new StringBuffer("0 0 0 ");
		sb.append(day);
		sb.append(" * ?");
		return sb.toString();
	}
	
	/**
	 * 根据抄表定时字符获得显示字符
	 * @param formatStr
	 * @return
	 */
	public static final String getStrByFormatChaoBiao(String formatStr){
		String strs = formatStr.split(" ")[3];
		String result = "每月";
		result += strs.split("-")[0];
		result += "至";
		result += strs.split("-")[1];
		result += "日抄表";
		return result;
	}
	
	/**
	 * 根据计费定时字符获得显示字符
	 * @param formatStr
	 * @return
	 */
	public static final String getStrByFormatJiFei(String formatStr){
		String strs = formatStr.split(" ")[3];
		String result = "每月";
		result += strs.split("-")[0];
		result += "日计费";
		return result;
	}
	
	public static final String[] getBeginAndEndByFormat(String chaobiao){
		return chaobiao.split(" ")[3].split("-");
	}
	public static final String getJiFeiByFormat(String jifei){
		return jifei.split(" ")[3];
	}
	
	/**
	 * 根据当前时间获得指定格式的字符串
	 * @param dp 时间格式
	 * @return 转换后的字符串
	 */
	public static final String getDateToStr(DatePattern dp){
		SimpleDateFormat sdf = new SimpleDateFormat(dp.getValue());
		Date date = new Date();
		String result = sdf.format(date);
		return result;
	}
	
	/**
	 * 根据指定时间获得指定格式的字符串
	 * @param date 时间
	 * @param dp 时间格式
	 * @return 转换后的字符串
	 */
	public static final String getDateToStr(Date date, DatePattern dp){
		SimpleDateFormat sdf = new SimpleDateFormat(dp.getValue());
		String result = sdf.format(date);
		return result;
	}
	
	/**
	 * 将object转为Integer
	 * @param object
	 * @return
	 */
	public static final Integer ObjToInteger(Object object){
		return Integer.parseInt(object.toString());
	}
	
	/**
	 * 将object转为Double
	 * @param object
	 * @return
	 */
	public static final Double ObjToDouble(Object object){
		return Double.parseDouble(object.toString());
	}
	
	/**
	 * 检查对象是否为null,如果为null则返回0
	 * @param object
	 * @return
	 */
	public static final Object checkNullReturn0(Object object){
		if(Tools.isNull(object)) return 0;
		return object;
	}

	/**
	 * 判断字符串是否为数字
	 * @param replace
	 * @return
	 */
	public static boolean isNumber(String replace) {
		try {
			Long.parseLong(replace);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static final Integer getDayByDate(String dateString,DatePattern pattern){
		long l = System.currentTimeMillis() - Tools.strToUtilDate(dateString,pattern).getTime();
		Long i = l/1000/60/60/24;
		return i.intValue();
	}
	
	public static final Integer getDayByDate(Date date){
		long l = System.currentTimeMillis() - date.getTime();
		Long i = l/1000/60/60/24;
		return i.intValue();
	}
	
	public static final boolean isNull(Object... objects){
		if(objects==null) return true;
		for (int i = 0; i < objects.length; i++) {
			if(Tools.notNull(objects[i])) return false;
		}
		return true;
	}


	
	
	public static final String sqlAppend(String... strings){
		StringBuffer sql = new StringBuffer();
		for (String string : strings) {
			sql.append(string).append(" ");
		}
		return sql.toString();
	}

	public static boolean isBigDecimal(String val) {
		if(Tools.isNullOrNullStr(val)) return false;
		try {
			new BigDecimal(val);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 将金额数字转为大写中文金额
	 * @Title 
	 * @Desc 
	 * @param money 需要转换的金额
	 * @return 转换后结果
	 */
	public static final String numberToChineseMoney(BigDecimal money){
		//汉字的数字
		  String[] cnNums = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
		  //基本单位
		  String[] cnIntRadice = new String[]{"", "拾", "佰", "仟"};
		  //对应整数部分扩展单位
		  String[] cnIntUnits = new String[]{"", "万", "亿", "兆"};
		  //对应小数部分单位
		  String[] cnDecUnits = new String[]{"角", "分", "毫", "厘"};
		  //整数金额时后面跟的字符
		  String cnInteger = "整";
		  //整型完以后的单位
		  String cnIntLast = "元";
		  //最大处理的数字
		  BigDecimal maxNum = new BigDecimal("999999999999999.9999");
		  //金额整数部分
		  String integerNum;
		  //金额小数部分
		  String decimalNum;
		  //输出的中文金额字符串
		  String chineseStr = "";
		  if (money == null) 
			  return ""; 
		  if (money.compareTo(maxNum)>=0) {
		    //超出最大处理数字
		    return "";
		  }
		  if (money.compareTo(BigDecimal.ZERO)==0) {
		    chineseStr = cnNums[0].concat(cnIntLast).concat(cnInteger);
		    return chineseStr;
		  }
		  //转换为字符串
		  if (money.toString().indexOf('.') == -1) {
		    integerNum = money.toString();
		    decimalNum = "";
		  } else {
		    integerNum = money.toString().substring(0, money.toString().indexOf('.'));
		    decimalNum = money.toString().substring(money.toString().indexOf('.')+1);
		  }
		  //获取整型部分转换
		  if (Integer.parseInt(integerNum) > 0) {
		    Integer zeroCount = 0;
		    int IntLen = integerNum.toString().length();
		    for (int i = 0; i < IntLen; i++) {
		      String n = integerNum.substring(i, i+1);
		      int p = IntLen - i - 1;
		      int q = p / 4;
		      int m = p % 4;
		      if (n.equals("0")) {
		        zeroCount++;
		      } else {
		        if (zeroCount > 0) {
		          chineseStr += cnNums[0];
		        }
		        //归零
		        zeroCount = 0;
		        chineseStr += cnNums[Integer.parseInt(n)] + cnIntRadice[m];
		      }
		      if (m == 0 && zeroCount < 4) {
		        chineseStr += cnIntUnits[q];
		      }
		    }
		    chineseStr += cnIntLast;
		  }
		  //小数部分
		  if (decimalNum.trim().length()>0) {
		    int decLen = decimalNum.length();
		    for (int i = 0; i < decLen; i++) {
		      String n = decimalNum.substring(i, i+1);
		      if (!n.trim().equals("0")) {
		        chineseStr += cnNums[Integer.parseInt(n)] + cnDecUnits[i];
		      }
		    }
		  }
		  if (chineseStr.trim().length()==0) {
		    chineseStr += cnNums[0] + cnIntLast + cnInteger;
		  } else if (decimalNum.trim().length() == 0) {
		    chineseStr += cnInteger;
		  }
		  return chineseStr;
	}
	
    /** 
     *校验手机号
     */  
    public static boolean checkPhone(String phone){  
    	if(Tools.isEmpty(phone)) return false;
        String regExp = "^[1][3,4,5,7,8][0-9]{9}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(phone);  
        return m.matches();  
    }  
    
    /** 
     *校验邮箱
     */  
    public static boolean checkEmail(String email){  
    	if(Tools.isEmpty(email)) return false;
    	String regExp = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+";  
    	Pattern p = Pattern.compile(regExp);  
    	Matcher m = p.matcher(email);  
    	return m.matches();  
    }  
    
    public static final String entityToJson(Serializable entity){
    	Class<?> clazz = entity.getClass();
    	Method[] methods = clazz.getDeclaredMethods();
    	StringBuffer json = new StringBuffer("{");
    	int i = 0;
    	for (Method method : methods) {
			method.setAccessible(true);
			String methodName = method.getName();
			if(methodName.startsWith("get") && methodName.length() > 3 && method.getParameterTypes().length == 0){
				i++;
				StringBuffer key = new StringBuffer(methodName.substring(3, 4).toLowerCase());
				if(methodName.length() > 4){
					key.append(methodName.substring(4));
				}
				json.append("\"").append(key).append("\"").append(":");
				try {
					Object value = method.invoke(entity);
					if(value != null){
						if(value instanceof Number){
							json.append(value.toString());
						}else{
							json.append("\"").append(value.toString()).append("\"");
							
						}
					}else{
						json.append("null");
					}
					json.append(",");
				} catch (Exception e) {
					
				}
			}
		}
    	if(i > 0){
    		json = new StringBuffer(json.substring(0, json.length()-1));
    	}
    	json.append("}");
    	return json.toString();
    }
    
    @SuppressWarnings("unchecked")
	public static final Map<String, Object> dom2Map(Document document){
    	Map<String, Object> map = new HashMap<>();
    	Element element = document.getRootElement();
		Iterator<Element> iterator = element.elementIterator();
		while (iterator.hasNext()) {
			Element et = iterator.next();
			String name = et.getQName().getName();
			map.put(name.trim(), et.getData());
		}
		return map;
    }
    
    public static final String LogInfoAction(String log,Object...objects){
    	if(objects==null || objects.length==0)
    		return log;
    	for (int i = 0; i < objects.length; i++) {
			log = log.replace("{"+i+"}", objects[i].toString());
		}
    	return log;
    }
    
}
