package com.agan.boot.exceptions;

import com.agan.boot.enums.ResultCode;
import com.agan.boot.response.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 阿甘
 * @see 课程地址：https://study.163.com/course/courseMain.htm?courseId=1004348001&share=2&shareId=1016671292
 * @version 1.0
 * 注：如有任何疑问欢迎加入QQ群977438372 进行讨论
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {
    /**
     * 处理运行时异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResult handleThrowable(Throwable e, HttpServletRequest request) {
        //TODO 运行是异常，可以在这里记录，用于发异常邮件通知
        ErrorResult error =ErrorResult.fail(ResultCode.SYSTEM_ERROR, e);
        log.error("URL:{} ,系统异常: ",request.getRequestURI(), e);
        return error;
    }

	/**
     * 处理自定义异常
     */
	@ExceptionHandler(BusinessException.class)
	public ErrorResult handleBusinessException(BusinessException e, HttpServletRequest request) {
        ErrorResult error = ErrorResult.builder().status(e.code)
                .message(e.message)
                .exception(e.getClass().getName())
                .build();
        log.warn("URL:{} ,业务异常:{}", request.getRequestURI(),error);
        return error;
	}

    /**
     * validator 统一异常封装
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String msgs = this.handle(e.getBindingResult().getFieldErrors());
        ErrorResult error = ErrorResult.fail(ResultCode.PARAM_IS_INVALID, e,  msgs);
        log.warn("URL:{} ,参数校验异常:{}", request.getRequestURI(),msgs);
        return error;
    }

    private String handle(List<FieldError> fieldErrors) {
        StringBuilder sb = new StringBuilder();
        for (FieldError obj : fieldErrors) {
            sb.append(obj.getField());
            sb.append("=[");
            sb.append(obj.getDefaultMessage());
            sb.append("]  ");
        }
		return sb.toString();
    }

    /**
     * Assert的异常统一封装
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        ErrorResult error = ErrorResult.builder().status(4000)
                .message(e.getMessage())
                .exception(e.getClass().getName())
                .build();
        log.warn("URL:{} ,业务校验异常:{}", request.getRequestURI(),e);
        return error;
    }

}
