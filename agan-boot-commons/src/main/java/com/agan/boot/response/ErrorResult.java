package com.agan.boot.response;



import com.agan.boot.enums.ResultCode;
import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResult {

	/**
	 * 异常状态码
	 */
	private Integer status;

	/**
	 * 用户看得见的异常，例如 用户名重复！！,
	 */
	private String message;

	/**
	 * 异常的名字
	 */
	private String exception;

	/**
	 * 异常堆栈信息
	 */
	//private String errors;

	/**
	 * 对异常提示语进行封装
	 */
	public static ErrorResult fail(ResultCode resultCode, Throwable e,String message) {
		ErrorResult result = ErrorResult.fail(resultCode, e);
		result.setMessage(message);
		return result;
	}

	/**
	 * 对异常枚举进行封装
	 */
	public static ErrorResult fail(ResultCode resultCode, Throwable e) {

		ErrorResult result = new ErrorResult();
		result.setMessage(resultCode.message());
		result.setStatus(resultCode.code());
		result.setException(e.getClass().getName());
		//result.setErrors(Throwables.getStackTraceAsString(e));
		return result;
	}
}
