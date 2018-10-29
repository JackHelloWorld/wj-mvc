package com.wj.common.utils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;  

/**
 * 利用HttpClient进行http/https请求的工具类
 * @createTime 2018年1月16日
 * @author Jack
 *
 */
public class HttpClientUtils {

	private static final String charset = "utf-8";

	public static String doGet(String url,Map<String, String> param,String... charsets){  
		HttpClient httpClient = null;  
		HttpGet httpGet = null;  
		String result = null;  
		try{  
			StringBuffer para = new StringBuffer();
			//设置参数
			if(param!=null){
				for (String key : param.keySet()) {
					if(para.length()==0) para.append("?");
					else para.append("&");
					para.append(key).append("=").append(param.get(key));
				}
			}
			httpClient = new SSLClient();  
			httpGet = new HttpGet(url.concat(para.toString()));
			HttpResponse response = httpClient.execute(httpGet);  
			if(response != null){  
				HttpEntity resEntity = response.getEntity();  
				if(resEntity != null){  
					result = EntityUtils.toString(resEntity,(charsets != null && charsets.length >0) ? charsets[0] : charset);
				}  
			}  
		}catch(Exception ex){  
			ex.printStackTrace();  
		}  
		return result;  
	}


	public static String doPost(String url,Map<String, String> param){  
		HttpClient httpClient = null;  
		HttpPost httpPost = null;  
		String result = null;  
		try{  
			httpClient = new SSLClient();  
			httpPost = new HttpPost(url);  
			//设置参数  
			List<NameValuePair> list = new ArrayList<NameValuePair>();  
			Iterator<Entry<String, String>> iterator = param.entrySet().iterator();  
			while(iterator.hasNext()){  
				Entry<String,String> elem = (Entry<String, String>) iterator.next();  
				list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
			}  
			if(list.size() > 0){  
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
				httpPost.setEntity(entity);  
			}  
			HttpResponse response = httpClient.execute(httpPost);  
			if(response != null){  
				HttpEntity resEntity = response.getEntity();  
				if(resEntity != null){  
					result = EntityUtils.toString(resEntity,charset);
				}  
			}  
		}catch(Exception ex){  
			ex.printStackTrace();  
		}  
		return result;  
	}  
	

	public static String doAction(String uri,String body,String method){
		StringBuffer buffer=null;  
		try{  
			URL url=new URL(uri);  
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();  
			conn.setDoOutput(true);  
			conn.setDoInput(true);  
			conn.setRequestMethod(method.toUpperCase());  
			conn.connect();  
			//往服务器端写内容 也就是发起http请求需要带的参数  
			OutputStream os=conn.getOutputStream();  
			os.write(body.getBytes(charset));  
			os.close();  

			//读取服务器端返回的内容  
			InputStream is=conn.getInputStream();  
			InputStreamReader isr=new InputStreamReader(is,charset);  
			BufferedReader br=new BufferedReader(isr);  
			buffer=new StringBuffer();  
			String line=null;  
			while((line=br.readLine())!=null){  
				buffer.append(line);  
			}  
		}catch(Exception e){  
			e.printStackTrace();  
		}  
		return buffer.toString();
	}


}
