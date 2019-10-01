//package com.agan.boot.utils;
//
//
//import com.agan.boot.enums.ResultCode;
//import com.agan.boot.response.ResResult;
//
//public final class ResultUtil {
//
//    /**
//     * 设置返回的数据
//     */
//    public static ResResult suc(String data) {
//        return result(ResultCode.SUCCESS , data);
//    }
//
//    /**
//     * 返回对象类型或者基本类型
//     *
//     * @param data
//     * @param <T>
//     * @return
//     */
//    public static <T> ResResult<T> data(T data) {
//        return result(ResultCode.SUCCESS, data);
//    }
//
//    /**
//     * {@link #suc(String)}
//     *
//     * @return
//     */
//    public static ResResult suc() {
//        return suc(null);
//    }
//
//    /**
//     * 提示消费方消费失败，并且给出消费失败的原因
//     */
//    public static ResResult fail(String desc) {
//        return fail(FAIL, desc);
//    }
//
//    /**
//     * 提示消费方消费失败，并且给出消费失败的原因和状态码
//     */
//    public static ResResult fail(int status, String desc) {
//        return result(status, desc, null);
//    }
//
//    private static <T> ResResult<T> result(ResultCode resultCode, T data) {
//        return new ResResult<>(resultCode.code(), resultCode.message(), data);
//    }
//}