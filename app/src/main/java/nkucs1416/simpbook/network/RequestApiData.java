package nkucs1416.simpbook.network;

import java.util.HashMap;
import android.content.Context;
import nkucs1416.simpbook.util.*;
import java.util.ArrayList;


import org.json.JSONArray;  
import org.json.JSONException;  
import org.json.JSONObject;  
 
/**
 * 网络接口类
 */

public class RequestApiData {
	private static RequestApiData instance = null;
	private HttpResponeCallBack mCallBack = null;

	/**
 	 * 创建接口对象
 	 */
	public static RequestApiData getInstance() {
		if (instance == null) {
			instance = new RequestApiData();
		}
		return instance;
	}

	/**
	 * 注册用户接口
	 * @param nickname  昵称
	 * @param email 邮箱
	 * @param password 密码
	 * @param callback 回调
	 * @param context 调用者运行环境
	 * 请求方式：POST
	 */
	public void getRegistData(String nickname,String email
			,String password, HttpResponeCallBack callback, Context context) {
		 mCallBack = callback;
		 //这是每一个接口的唯一标示
		 String tagUrl = UrlConstance.KEY_REGIST_INFO;//注册接口
		 //将注册的信息保存在map中（须和服务器端一致）
		 JSONObject parameter = new JSONObject();
		try {
			parameter.put("username", nickname);
		 	parameter.put("mail",email);
		 	parameter.put("password",password);
			//拼接参数信息，昵称，邮箱，密码，公钥，并用md5进行加密
        	StringBuilder builder = new StringBuilder();
        	builder.append(nickname);
        	builder.append(email);
        	builder.append(password);
        	builder.append(UrlConstance.PUBLIC_KEY);

        	parameter.put(UrlConstance.ACCESSTOKEN_KEY, MD5Util.getMD5Str(builder.toString()));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		 //请求数据接口
		 RequestManager.post(UrlConstance.APP_URL, tagUrl, parameter, callback, context);

	}


	/**
	 * 登录用户接口
	 * @param email 邮箱
	 * @param password 密码
	 * @param callback 回调
	 * @param context 调用者运行环境
	 * 请求方式：POST
	 */
	public void getLoginData(String email ,String password,
		   HttpResponeCallBack callback, Context context) {
		 mCallBack = callback;
		 //这是每一个接口的唯一标示
		 String tagUrl = UrlConstance.KEY_LOGIN_INFO;//登录接口

		JSONObject parameter = new JSONObject();
		try {
			parameter.put("email", email);
		 	parameter.put("password", password);

			//拼接参数信息，邮箱，密码，公钥，并用md5进行加密
			StringBuilder builder = new StringBuilder();
			builder.append(email);
			builder.append(password);
			builder.append(UrlConstance.PUBLIC_KEY);

		 	parameter.put(UrlConstance.ACCESSTOKEN_KEY, MD5Util.getMD5Str(builder.toString()));
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		 //请求数据接口
		 RequestManager.post(UrlConstance.APP_URL,tagUrl, parameter, callback, context);

	}


	/**
	 * 更新account接口
	 * @param accountArray account数据
	 * @param token 身份验证
	 * @param callback 回调
	 * @param context 调用者运行环境
	 * 请求方式：POST
	 */
	public void getUpdateAccountData( ArrayList<Account> accountArray, String token,
		   HttpResponeCallBack callback, Context context) {
		 mCallBack = callback;
		 //这是每一个接口的唯一标示
		 String tagUrl = UrlConstance.KEY_UPDATE_ACCOUNT_INFO;//登录接口
		//  HashMap<String, Arraylist> parameter = new HashMap<String, Arraylist>();
		//  parameter.put("email", email);
		//  parameter.put("password", password);
		JSONObject parameter = new JSONObject();
		try {
			parameter.put("token", token);
			JSONArray accountList = new JSONArray();//实例一个JSON数组
			JSONObject[] account = new JSONObject[accountArray.size()];
			for(int i = 0;i < accountArray.size();i++) {
				Account a = accountArray.get(i);
				account[i] = new JSONObject();
				account[i].put("account_name", a.getName());
				account[i].put("account_id", a.getId());
				account[i].put("account_color", a.getColor());
				account[i].put("account_money", a.getMoney());
				account[i].put("status", a.getStatus());
				accountList.put(i, account[i]);
			}
			parameter.put("accountList",accountList);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		 //请求数据接口
		 RequestManager.post(UrlConstance.APP_URL,tagUrl, parameter, callback, context);

	}


	/**
	 * 更新category接口
	 * @param categoryArray category数据
	 * @param token 身份验证
	 * @param callback 回调
	 * @param context 调用者运行环境
	 * 请求方式：POST
	 */
	public void getUpdateCategoryData( ArrayList<Class1> categoryArray, String token,
									  HttpResponeCallBack callback, Context context) {
		mCallBack = callback;
		//这是每一个接口的唯一标示
		String tagUrl = UrlConstance.KEY_UPDATE_CATEGORY_INFO;
		JSONObject parameter = new JSONObject();
		try {
			parameter.put("token", token);
			JSONArray categoryList = new JSONArray();//实例一个JSON数组
			JSONObject[] category = new JSONObject[categoryArray.size()];
			for(int i = 0;i < categoryArray.size();i++) {
				Class1 a = categoryArray.get(i);
				category[i] = new JSONObject();
				category[i].put("category_name", a.getName());
				category[i].put("category_id", a.getId());
				category[i].put("category_color", a.getColor());
				category[i].put("status", a.getStatus());
				categoryList.put(i, category[i]);
			}
			parameter.put("categoryList", categoryList);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		//请求数据接口
		RequestManager.post(UrlConstance.APP_URL,tagUrl, parameter, callback, context);

	}


	/**
	 * 更新subcategory接口
	 * @param subcategoryArray subcategory数据
	 * @param token 身份验证
	 * @param callback 回调
	 * @param context 调用者运行环境
	 * 请求方式：POST
	 */
	public void getUpdateSubcategoryData( ArrayList<Class2> subcategoryArray, String token,
									   HttpResponeCallBack callback, Context context) {
		mCallBack = callback;
		//这是每一个接口的唯一标示
		String tagUrl = UrlConstance.KEY_UPDATE_SUBCATEGORY_INFO;

		JSONObject parameter = new JSONObject();
		try {
			parameter.put("token", token);
			JSONArray subcategoryList = new JSONArray();//实例一个JSON数组
			JSONObject[] subcategory = new JSONObject[subcategoryArray.size()];
			for(int i = 0;i < subcategoryArray.size();i++) {
				Class2 a = subcategoryArray.get(i);
				subcategory[i] = new JSONObject();
				subcategory[i].put("subcategory_name", a.getName());
				subcategory[i].put("subcategory_id", a.getId());
				subcategory[i].put("subcategory_fatherId", a.getFatherId());
				subcategory[i].put("subcategory_color", a.getColor());
				subcategory[i].put("status", a.getStatus());
				subcategoryList.put(i, subcategory[i]);
			}
			parameter.put("subcategoryList",subcategoryList);
		} catch (JSONException e) {
			e.printStackTrace();
		}


		//请求数据接口
		RequestManager.post(UrlConstance.APP_URL,tagUrl, parameter, callback, context);

	}


	/**
	 * 更新record接口
	 * @param recordArray record数据
	 * @param token 身份验证
	 * @param callback 回调
	 * @param context 调用者运行环境
	 * 请求方式：POST
	 */
	public void getUpdateRecordData( ArrayList<StatementRecord> recordArray, String token,
										  HttpResponeCallBack callback, Context context) {
		mCallBack = callback;
		//这是每一个接口的唯一标示
		String tagUrl = UrlConstance.KEY_UPDATE_RECORD_INFO;

		JSONObject parameter = new JSONObject();
		try {
			parameter.put("token", token);
			JSONArray recordList = new JSONArray();//实例一个JSON数组
			JSONObject[] record = new JSONObject[recordArray.size()];
			for(int i = 0;i < recordArray.size();i++) {
				StatementRecord a = recordArray.get(i);
				record[i] = new JSONObject();
				record[i].put("record_type", a.getType());
				record[i].put("record_id", a.getId());
				record[i].put("record_money", a.getMoney());
				record[i].put("record_accountId", a.getAccountId());
				record[i].put("record_categoryId", a.getClass1Id());
				record[i].put("record_subcategoryId", a.getClass2Id());
				record[i].put("record_accountToId", a.getToAccountId());
				record[i].put("record_remark", a.getRemark());

				Utils util = new Utils();
				record[i].put("record_time", util.switchDatetoTime(a.getDate()));
				record[i].put("status", a.getStatus());
				recordList.put(i, record[i]);
			}
			parameter.put("recordList",recordList);
		} catch (JSONException e) {
			e.printStackTrace();
		}


		//请求数据接口
		RequestManager.post(UrlConstance.APP_URL,tagUrl, parameter, callback, context);

	}

	/**
	 * 更新template接口
	 * @param templateArray template数据
	 * @param token 身份验证
	 * @param callback 回调
	 * @param context 调用者运行环境
	 * 请求方式：POST
	 */
	public void getUpdateTemplateData( ArrayList<Collection> templateArray, String token,
									 HttpResponeCallBack callback, Context context) {
		mCallBack = callback;
		//这是每一个接口的唯一标示
		String tagUrl = UrlConstance.KEY_UPDATE_TEMPLATE_INFO;

		JSONObject parameter = new JSONObject();
		try {
			parameter.put("token", token);
			JSONArray templateList = new JSONArray();//实例一个JSON数组
			JSONObject[] template = new JSONObject[templateArray.size()];
			for(int i = 0;i < templateArray.size();i++) {
				Collection a = templateArray.get(i);
				template[i] = new JSONObject();
				template[i].put("template_type", a.getType());
				template[i].put("template_id", a.getId());
				template[i].put("template_money", a.getMoney());
				template[i].put("template_accountId", a.getAccountId());
				template[i].put("template_categoryId", a.getClass1Id());
				template[i].put("template_subcategoryId", a.getClass2Id());
				template[i].put("template_accountToId", a.getToAccountId());
				template[i].put("template_remark", a.getRemark());

				Utils util = new Utils();


				template[i].put("template_time", util.switchDatetoTime(a.getDate()));
				template[i].put("status", a.getStatus());
				templateList.put(i, template[i]);
			}
			parameter.put("templateList",templateList);
		} catch (JSONException e) {
			e.printStackTrace();
		}


		//请求数据接口
		RequestManager.post(UrlConstance.APP_URL,tagUrl, parameter, callback, context);

	}

	/**
	 * 更新客户端全部数据接口
	 * @param token 身份验证
	 * @param callback 回调
	 * @param context 调用者运行环境
	 * 请求方式：POST
	 */
	public void getUpdateAllData(  String token, HttpResponeCallBack callback, Context context) {
		mCallBack = callback;
		//这是每一个接口的唯一标示
		String tagUrl = UrlConstance.KEY_UPDATE_TEMPLATE_INFO;

		JSONObject parameter = new JSONObject();
		try {
			parameter.put("token", token);
		} catch (JSONException e) {
			e.printStackTrace();
		}


		//请求数据接口
		RequestManager.post(UrlConstance.APP_URL,tagUrl, parameter, callback, context);

	}

}
