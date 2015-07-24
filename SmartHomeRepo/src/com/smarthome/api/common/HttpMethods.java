package com.smarthome.api.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import android.util.Log;

import com.linktop.oauth.MiscUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class HttpMethods {
	
	protected static final String CONTROL_SUCCESS = "success";
	protected static final String CONTROL_FAIL = "fail";
	
	
	/**
	 * 
	 * @param url
	 * @param dict   鏍煎紡锟�?  a=123&b=456&c=4343
 	 * @param treeMap
	 * @return
	 */
	public static String[] httpGet(String url, HashMap<String,String> dict, TreeMap<String, String> treeMap) {
		Log.e(""+url, dict+"");
		
		String[] strResp = new String[2];
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(8000, TimeUnit.MILLISECONDS);
		client.setReadTimeout(8000, TimeUnit.MILLISECONDS);
		client.setWriteTimeout(8000, TimeUnit.MILLISECONDS);
		
		String queryParam = MiscUtil.joinString(dict, true);
		if(queryParam != null && !queryParam.equals(""))
		{
			url = url+"?"+queryParam;
		}
		Log.e("", url+ "  " +queryParam);
		Builder reqBuilder = new Request.Builder();
		if(treeMap != null){
			for(String key : treeMap.keySet())
			{
				reqBuilder.header(key, treeMap.get(key));
			}
		}
		
		Request req = reqBuilder.url(url).build();
		try {
			Response response = client.newCall(req).execute();
			strResp[0] = String.valueOf(response.code());
			if (response.code() == 200) {
				strResp[1] = response.body().string();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			strResp[0] = String.valueOf(-1);
		}
		Log.e("", strResp[0]+"  " + strResp[1]);
		return strResp;
	}
	
	/**
	 * 
	 * @param url
	 * @param dict   鏍煎紡锟�?  a=123&b=456&c=4343
 	 * @param treeMap
	 * @return
	 */
	public static String[] httpPost(String url, HashMap<String,String> dict, TreeMap<String, String> treeMap)
	{
		Log.e("httpPost ", url + " " + dict);
		String[] resp = new String[2];
		resp[0] = String.valueOf(-1);
		resp[1] = CONTROL_FAIL;
		
		OkHttpClient client = new OkHttpClient();
		client.setConnectTimeout(8000, TimeUnit.MILLISECONDS);
		client.setReadTimeout(8000, TimeUnit.MILLISECONDS);
		client.setWriteTimeout(8000, TimeUnit.MILLISECONDS);
		
		RequestBody formBody = okHttpPostBody(dict);
		Builder reqBuilder = new Request.Builder();
		if(treeMap != null){
			for(String key : treeMap.keySet())
			{
				reqBuilder.header(key, treeMap.get(key));
			}
		}
		Request request = reqBuilder.url(url)
	      .post(formBody)
	      .build();
		try {
			Response response = client.newCall(request).execute();
			resp[0] = String.valueOf(response.code());
			if (response.code() == 200) {
				resp[1] = response.body().string();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			resp[0] = String.valueOf(-1);
			resp[1] = CONTROL_FAIL;
		}
		
        return resp;
	}

	public static byte[] httpGet(String url)
	{
		Log.e("", url );
		URL urlPath;
		byte[] dataBuffer = null;
		try {
			
			urlPath = new URL(url);
			OkHttpClient httpClient = new OkHttpClient();
			OkUrlFactory okUrlFactory = new OkUrlFactory(httpClient);
			HttpURLConnection urlConnection = okUrlFactory.open(urlPath);;
			urlConnection.setConnectTimeout(8000); // 璁剧疆杩炴帴瓒呮椂鏃堕棿
			urlConnection.setReadTimeout(8000);
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();
			int responseCode = urlConnection.getResponseCode();
			Log.e("responseCode ", responseCode+"  responseCode  ");
			if(responseCode != 200)
				return dataBuffer;
			InputStream inputStream = urlConnection.getInputStream();
			int len = urlConnection.getContentLength();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        while( (len=inputStream.read(buffer)) != -1){
	            outStream.write(buffer, 0, len);
	        }
	        outStream.close();
	        inputStream.close();
	        urlConnection.disconnect();
	        return outStream.toByteArray();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return dataBuffer;
	}
	
	public static String[] httpPost(String url, byte[] file)
	{
		Log.e("", url +" " + file);
		String[] resp = new String[2];
		try {
			URL urlPath = new URL(url);
			OkHttpClient httpClient = new OkHttpClient();
			OkUrlFactory okUrlFactory = new OkUrlFactory(httpClient);
			HttpURLConnection urlConnection = okUrlFactory.open(urlPath);
			urlConnection.setConnectTimeout(8000); // 璁剧疆杩炴帴瓒呮椂鏃堕棿
			urlConnection.setReadTimeout(8000);
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);  
			urlConnection.setDoOutput(true);  
			urlConnection.setRequestProperty("Content-Length", file.length+"");  
			urlConnection.connect();
			OutputStream out = urlConnection.getOutputStream();  
			out.write(file);  
			out.close();
			InputStream inputStream = urlConnection.getInputStream();
			int responseCode = urlConnection.getResponseCode();  
			resp[0] = String.valueOf(responseCode);
			BufferedReader reader = null;  
			Log.e("responseCode ", responseCode+"  responseCode");
		    if (responseCode == 200) {  
		    	reader = new BufferedReader(new InputStreamReader(inputStream));  
		    } else {  
		    	reader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));  
		    }  
		    StringBuffer result = new StringBuffer();
		    String s = "";
		    while((s = reader.readLine()) != null)
		    	result.append(s);
		    
		    resp[1] = result.toString();
		    urlConnection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        return resp;
	}
	
	private static RequestBody okHttpPostBody(HashMap<String, String> queryParam) {
		FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
		//upload data
		if(queryParam != null){
			//鏋勶拷?锟借姹傚弬锟�?
			for(Entry<String, String> entry : queryParam.entrySet()){
				//娣诲姞锟�?瀵瑰弬鏁発ey鍜寁alue鍒發ist
				formEncodingBuilder.add(entry.getKey(), entry.getValue());  
			}
			//璁剧疆璇锋眰鐨刡ody鍐呭
		}
		
		return formEncodingBuilder.build();
	}

	

}
