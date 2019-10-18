package com.agan.boot.filter;


import com.agan.boot.enums.ResultCode;
import com.agan.boot.response.Result;
import com.agan.boot.utils.HttpDataUtil;
import com.agan.boot.utils.JsonUtil;
import com.agan.boot.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedMap;


/**
 * 防篡改、防重放攻击过滤器
 */
@Slf4j
public class SignFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {
        log.info("初始化 signfilter.............");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        log.debug("过滤URL:{}", httpRequest.getRequestURI());

        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(httpRequest);
        String sign = httpRequest.getHeader("sign");
        //验证sign不能为空
        if (StringUtils.isEmpty(sign)) {
            responseFail(httpResponse);
            return;
        }
        //验证timestamp是否为空
        String time = httpRequest.getHeader("timestamp");
        if (StringUtils.isEmpty(time)) {
            responseFail(httpResponse);
            return;
        }
        /*
         * 重放处理
         * 判断timestamp时间戳与当前时间是否操过60s（过期时间根据业务情况设置）,如果超过了就提示签名过期。
         */
        long timestamp = Long.valueOf(time);
        long now = System.currentTimeMillis() / 1000;
        //为了方便测试这里改成1小时
        long n = 60 * 60;
        if (now - timestamp > n) {
            responseFail(httpResponse);
            return;
        }

        boolean accept = true;
        SortedMap<String, String> paramMap;
        switch (httpRequest.getMethod()) {
            case "GET":
                paramMap = HttpDataUtil.getUrlParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, sign, timestamp);
                break;
            case "POST":
            case "PUT":
            case "DELETE":
                paramMap = HttpDataUtil.getBodyParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, sign, timestamp);
                break;
            default:
                accept = true;
                break;
        }

        if (accept) {
            filter.doFilter(requestWrapper, response);
        } else {
            responseFail(httpResponse);
            return;
        }

    }

    /**
     * 异常返回
     */
    private void responseFail(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        //抛出异常401 未授权状态码
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter out = response.getWriter();
        //统一提示：签名验证失败
        Result fail = Result.fail(ResultCode.UNAUTHORIZED);
        String result = JsonUtil.object2Json(fail);
        out.println(result);
        out.flush();
        out.close();
    }

}