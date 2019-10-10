package com.agan.boot.exceptions;

import com.agan.boot.enums.ResultCode;
import lombok.Data;

/**
 * @author 阿甘
 * @see https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@Data
public class BusinessException extends RuntimeException {

	protected Integer code;

	protected String message;


	public BusinessException(ResultCode resultCode) {
		this.code = resultCode.code();
		this.message = resultCode.message();
	}

}
