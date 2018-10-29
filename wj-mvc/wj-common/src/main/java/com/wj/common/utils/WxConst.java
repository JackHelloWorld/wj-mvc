package com.wj.common.utils;

/**
 * 微信常亮值配置
 * @createTime 2018年1月16日
 * @author Jack
 *
 */
public class WxConst {
	
	/**
	 * url请求相关
	 * @createTime 2018年1月16日
	 * @author Jack
	 *
	 */
	public static class URL{
		
		/**获得access_token*/
		public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
		
		/**创建菜单*/
		public static final String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create";
		
		/**微信支付,统一下单*/
		public static final String PAY_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		
		/**获得用户基础信息<br>
		 * access_token<br>
		 * openid<br>
		 * lang=zh_CN//中文
		 * 
		 * */
		public static final String GET_OPEN_ID_URL = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	}
	
	/**
	 * 配置
	 * @createTime 2018年1月16日
	 * @author Jack
	 *
	 */
	public static class Config{

		public static final String token = "waterjack123";
		
		public static final String appId = "wxf732c1a7f56df805";
		
		/**商户号*/
		public static final String mchId = "1499774832";
		
		/**开发者密码*/
		public static final String AppSecret = "5d7ea7ef5df965a3abcff540322e3348";
		
		/**通知地址*/
		public static final String NOTIFY_URL = "http://test.young520.net/pay.action";
		
		/**微信支付api密码*/
		public static final String keyPass = "water123weChatPass11345342353455";
		
	}

}
