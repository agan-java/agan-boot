package com.agan.boot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * http 工具类 获取请求中的参数
 */
@Slf4j
public class HttpDataUtil {
    /**
     * post请求处理：获取 Body 参数，转换为SortedMap
     *
     * @param request
     */
    public static SortedMap<String, String> getBodyParams(final HttpServletRequest request) throws IOException {
        byte[] requestBody = StreamUtils.copyToByteArray(request.getInputStream());
        String body=new String(requestBody);
        return JsonUtil.json2Object(body, SortedMap.class);
    }

    /**
     * get请求处理：将URL请求参数转换成SortedMap
     *
     * @param request
     */
    public static SortedMap<String, String> getUrlParams(HttpServletRequest request) {
        String param = "";
        SortedMap<String, String> result = new TreeMap<>();
        if (StringUtils.isEmpty(request.getQueryString())) {
            return result;
        }

        try {
            param = URLDecoder.decode(request.getQueryString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String[] params = param.split("&");
        for (String s : params) {
            String[] array=s.split("=");
            result.put(array[0], array[1]);
        }
        return result;
    }
}
