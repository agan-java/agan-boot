package com.agan.boot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.SortedMap;

/**
 * 签名工具类
 */
@Slf4j
public class SignUtil {

    /**
     * 验证签名
     * 验证算法：把timestamp + JsonUtil.object2Json(SortedMap)合成字符串，然后MD5
     */
    public static boolean verifySign(SortedMap<String, String> map, String sign, Long timestamp) {
        String params = timestamp + JsonUtil.object2Json(map);
        return verifySign(params, sign);
    }

    /**
     * 验证签名
     */
    public static boolean verifySign(String params, String sign) {
        log.debug("客户端签名: {}", sign);
        if (StringUtils.isEmpty(params)) {
            return false;
        }
        log.info("客户端上传内容: {}", params);
        String paramsSign =DigestUtils.md5DigestAsHex(params.getBytes()).toUpperCase();
        log.info("客户端上传内容加密后的签名结果: {}", paramsSign);
        return sign.equals(paramsSign);
    }

}
