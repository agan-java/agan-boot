##一、本课程目标：
1. 弄懂为什么springboot需要《全局异常处理器》？
2. 编码实战一个springboot《全局异常处理器》
3. 封装一个自定义异常 ，并集成进《局异常处理器》
4. 把《全局异常处理器》集成进《接口返回值统一标准格式》


## 二、springboot为什么需要全局异常处理器？
1. 先讲下什么是全局异常处理器？<br>
全局异常处理器就是把整个系统的异常统一自动处理，程序员可以做到不用写try...catch
2. 那为什么需要全局异常呢？
- 第一个原因：不用强制写try-catch,由全局异常处理器统一捕获处理
``` 
    @PostMapping(value="/error1")
    public void  error1(  ){
        int i=9/0;
    }

```
如果不用try-catch捕获的话，客户端就会怎么样？
``` 
{
  "timestamp": "2019-10-02T02:15:26.591+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "/ by zero",
  "path": "/user/error1"
}
```
这种格式对于客户端来说，不友好，而一般程序员的try-catch
``` 
    @PostMapping(value="/error11")
    public String  error11(  ){
        try{
            int i=9/0;
        }catch (Exception ex){
            log.error("异常：{}",ex);
            return "no";
        }
        return "ok";
    }
```
但是还要一直自动化处理的，就是不用谢try-catch，由全局异常处理器来处理。

- 第二个原因：自定义异常，只能用全局异常来捕获。
``` 
    @PostMapping(value="/error4")
    public void  error4(  ){
        throw new RuntimeException("用户已存在！！");
    }

```

``` 
{
  "timestamp": "2019-10-02T02:18:26.843+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "用户已存在！！",
  "path": "/user/error4"
}
```
不可能这样直接返回给客户端，客户端是看不懂的，而且需要接入进《接口返回值统一标准格式》

- 第三个原因：JSR303规范的Validator参数校验器，参数校验不通过会抛异常，是无法使用try-catch语句直接捕获，
只能使用全局异常处理器了，JSR303规范的Validator参数校验器的异常处理后面课程会单独讲解，本节课暂不讲解。

## 三、案例实战：编码实现一个springboot《全局异常处理器》
### 步骤1：封装异常内容，统一存储在枚举类中
把所有的未知运行是异常都，用SYSTEM_ERROR(10000, "系统异常，请稍后重试")来提示
``` 
public enum ResultCode  {

	/* 成功状态码 */
	SUCCESS(0, "成功"),

	/* 系统500错误*/
	SYSTEM_ERROR(10000, "系统异常，请稍后重试"),
	UNAUTHORIZED(10401, "签名验证失败"),

	/* 参数错误：10001-19999 */
	PARAM_IS_INVALID(10001, "参数无效"),

	/* 用户错误：20001-29999*/
	USER_HAS_EXISTED(20001, "用户名已存在"),
	USER_NOT_FIND(20002, "用户名不存在");
	private Integer code;

	private String message;

	ResultCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}

}
```
### 步骤2：封装Controller的异常结果
最终目标格式：
``` 
{
  "status": 10000,
  "message": "系统异常，请稍后重试",
  "exception": "java.lang.ArithmeticException"
}
```

``` 
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
```

### 步骤3：加个全局异常处理器，对异常进行处理
``` 
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
}

```
handleThrowable方法的作用是：捕获运行时异常，并把异常统一封装为ErrorResult对象。
以上有几个细节点我们要单独讲解：
1. @RestControllerAdvice在《接口返回值统一标准格式》课程的时候，我们就讲解过它是增强Controller的扩展功能。而全局异常处理器，就是扩展功能之一。
2. @ExceptionHandler统一处理某一类异常，从而能够减少代码重复率和复杂度，@ExceptionHandler(Throwable.class)指处理Throwable的异常。
3. @ResponseStatus指定客户端收到的http状态码，这里配置500错误，客户端就显示500错误，

### 步骤4：体验效果
``` 
    @PostMapping(value="/error1")
    public void  error1(  ){
        int i=9/0;
    }
```
结果
``` 
{
  "status": 10000,
  "message": "系统异常，请稍后重试",
  "exception": "java.lang.ArithmeticException"
}
```

## 三、案例实战：把自定义异常 集成 进《全局异常处理器》

### 步骤1：封装一个自定义异常

自定义异常通常是集成RuntimeException
``` 
@Data
public class BusinessException extends RuntimeException {

	protected Integer code;

	protected String message;


	public BusinessException(ResultCode resultCode) {
		this.code = resultCode.code();
		this.message = resultCode.message();
	}

}
```

### 步骤2：把自定义异常 集成 进全局异常处理器
全局异常处理器只要在上节课的基础上，添加一个自定义异常处理即可。
``` 
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
```
### 步骤3：体验效果
``` 
    @PostMapping(value="/error3")
    public void  error3(  ){
        throw new BusinessException(ResultCode.USER_HAS_EXISTED);
    }
```
结果
``` 
{
  "status": 20001,
  "message": "用户名已存在",
  "exception": "com.agan.boot.exceptions.BusinessException"
}
```

## 四、案例实战：把《全局异常处理器》集成进《接口返回值统一标准格式》
目标：把《全局异常处理器》的json格式转换为《接口返回值统一标准格式》格式
``` 
{
  "status": 20001,
  "message": "用户名已存在",
  "exception": "com.agan.boot.exceptions.BusinessException"
}
```
转换
``` 
{
   "status":20001,
   "desc":"用户名已存在",
   "data":null
}
```
### 步骤1：改造ResponseHandler
``` 

@ControllerAdvice(basePackages = "com.agan.boot")
public class ResponseHandler implements ResponseBodyAdvice<Object> {

    /**
     * 是否支持advice功能
     * treu=支持，false=不支持
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     *
     * 处理response的具体业务方法
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof ErrorResult) {
            ErrorResult errorResult = (ErrorResult) o;
            return Result.fail(errorResult.getStatus(),errorResult.getMessage());
        } else if (o instanceof String) {
            return JsonUtil.object2Json(Result.suc(o));
        }
        return Result.suc(o);
    }
}

```
在 《接口返回值统一标准格式》的基础上
``` 
 if (o instanceof ErrorResult) {
            ErrorResult errorResult = (ErrorResult) o;
            return Result.fail(errorResult.getStatus(),errorResult.getMessage());
}
```

### 步骤2：体验效果
``` 
    @PostMapping(value="/error3")
    public void  error3(  ){
        throw new BusinessException(ResultCode.USER_HAS_EXISTED);
    }
```
结果
``` 
{
  "status": 20001,
  "desc": "用户名已存在",
  "data": null
}
```

##六：课后练习题
自己手写一个《全局异常处理器》和《接口返回值统一标准格式》<br>
1.模拟一个空指针异常，然后返回以下接口返回值统一标准格式：
``` 
{
  "code": 10000,
  "msg": "系统异常请稍后...",
  "data": null
}
```
2.模拟用户登录，自定义异常，提示以下内容
``` 
{
  "code": 20003,
  "msg": "用户名或密码错误，请重试",
  "data": null
}
```
