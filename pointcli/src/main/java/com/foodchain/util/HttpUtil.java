package com.foodchain.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: yuanZ
 * @Date: 2018/8/16 23:27
 * @Description: Http请求工具类
**/
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * @param: [url: 请求路径, params: 请求参数]
     * @return: com.alibaba.fastjson.JSONObject
     * @Description: Post请求
     */
    public static JSONObject post(String url, Map<String, String> params) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = getHttpPost(url, params);

        JSONObject jsonObject = null;
        CloseableHttpResponse response = null;
        try {
            response = client.execute(post);
            HttpEntity entity = response.getEntity();
            jsonObject = JSONObject.parseObject(EntityUtils.toString(entity, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[HttpUtil->post()]Http post 请求异常:" + e.getMessage());
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("[HttpUtil->post()]Http response 关闭异常:" + e.getMessage());
            }
        }

        return jsonObject;
    }

    /**
     * @param: [url：请求地址]
     * @return: com.alibaba.fastjson.JSONObject
     * @Description: Get请求
     */
    public static JSONObject get(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);

        JSONObject jsonObject = null;
        CloseableHttpResponse response = null;
        try {
            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            jsonObject = JSONObject.parseObject(EntityUtils.toString(entity, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[HttpUtil->get()]Http get 请求异常:" + e.getMessage());
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("[HttpUtil->get()]Http response 关闭异常:" + e.getMessage());
            }
        }

        return jsonObject;
    }

    private static HttpPost getHttpPost(String url, Map<String, String> paramsMap) {
        HttpPost post = new HttpPost(url);

        try {
            ArrayList<NameValuePair> paramList = new ArrayList<>();
            for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                paramList.add(pair);
            }
            post.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return post;
    }

}
