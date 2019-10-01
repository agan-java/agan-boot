#response统一格式
##一、本课程目标：
1. 弄清楚为什么要对springboot,所有Controller的response做统一格式封装？
1. 学会用ResponseBodyAdvice接口 和 @ControllerAdvice注解

##二、为什么要对springboot的接口返回值统一标准格式?
我们先来看下，springboot默认情况下的response是什么格式的
###第一种格式：response为String 
``` 
@GetMapping(value="/getStr")
public String  getStr(  ){
    return  "test";
}
```
以上springboot的返回值为
``` 
test
```
###第二种格式：response为Objct 
``` 
@GetMapping(value="/getObject")
public UserVO  getObject(  ){
    UserVO vo=new UserVO();
    vo.setUsername("agan");
    return  vo;
}
```
以上springboot的返回值为
``` 
{
  "id": null,
  "username": "agan",
  "password": null,
  "email": null,
  "phone": null,
  "idCard": null,
  "sex": null,
  "deleted": null,
  "updateTime": null,
  "createTime": null
}
```
###第三种格式：response为void 
``` 
@GetMapping(value="/empty")
public void  empty(  ){

}
```
以上springboot的返回值为空

###第四种格式：response为异常 
``` 
@GetMapping(value="/error")
public void  error(  ){
    int i=9/0;
}
```
以上springboot的返回值为空
``` 
{
  "timestamp": "2019-09-07T10:35:56.658+0000",
  "status": 500,
  "error": "Internal Server Error",
  "message": "/ by zero",
  "path": "/user/error"
} 
```
以上3种，情况，如果你和客户端（app h5）开发人联调接口，他们会很懵逼，因为你给他们的接口没有一个统一的格式，客户端开发人员，不知道如何处理返回值。
故，我们应该统一response的标准格式。

##三、定义response的标准格式
一般的response的标准格式包含3部分：
1.status状态值：代表本次请求response的状态结果。
2.response描述：对本次状态码的描述。
3.data数据：本次返回的数据。
``` 
{
   "status":0,
   "desc":"成功",
   "data":"test"
}
```
##四、初级程序员对response代码封装
对response的统一封装，是有一定的技术含量的，我们先来看下，初级程序员的封装，网上很多教程都是这么写的。
### 步骤1:把标准格式转换为代码
``` 
{
   "status":0,
   "desc":"成功",
   "data":"test"
}
```
把以上格式转换为Result代码

``` 

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    /**
     * 1.status状态值：代表本次请求response的状态结果。
     */
    private Integer status;
    /**
     * 2.response描述：对本次状态码的描述。
     */
    private String desc;
    /**
     * 3.data数据：本次返回的数据。
     */
    private T data;

    /**
     * 成功，创建ResResult：没data数据
     */
    public static Result suc() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 成功，创建ResResult：有data数据
     */
    public static Result suc(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 失败，指定status、desc
     */
    public static Result fail(Integer status, String desc) {
        Result result = new Result();
        result.setStatus(status);
        result.setDesc(desc);
        return result;
    }

    /**
     * 失败，指定ResultCode枚举
     */
    public static Result fail(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    /**
     * 把ResultCode枚举转换为ResResult
     */
    private void setResultCode(ResultCode code) {
        this.status = code.code();
        this.desc = code.message();
    }
}
```
### 步骤2:把状态码存在枚举类里面
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

### 步骤3:加一个体验类
``` 
@Api(description = "用户接口")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping(value="/getResult")
    public Result getResult(  ){
        return Result.suc("test");
    }
}

```
结论：看到这里，应该有很多同学都知道这样封装代码有很大弊端。
因为今后你每写一个接口，都要手工指定Result.suc(）这行代码，多累啊？？
如果你写这种代码推广给你整个公司用，然后硬性规定代码必须这么写！！所有程序都会吐槽鄙视！！！！



##五、高级程序员对response代码封装
如果你在公司推广你的编码规范，为了避免被公司其他程序员吐槽和鄙视，我们必须优化代码。
优化的目标：不要每个接口都手工指定Result返回值。

###步骤1：采用ResponseBodyAdvice技术来实现response的统一格式
springboot提供了ResponseBodyAdvice来帮我们处理
ResponseBodyAdvice的作用：拦截Controller方法的返回值，统一处理返回值/响应体，一般用来做response的统一格式、加解密、签名等等。
先看下ResponseBodyAdvice这个接口的源码。

``` 
public interface ResponseBodyAdvice<T> {
    /**
     * 是否支持advice功能
     * treu=支持，false=不支持
     */
    boolean supports(MethodParameter var1, Class<? extends HttpMessageConverter<?>> var2);
    /**
     *
     * 处理response的具体业务方法
     */
    @Nullable
    T beforeBodyWrite(@Nullable T var1, MethodParameter var2, MediaType var3, Class<? extends HttpMessageConverter<?>> var4, ServerHttpRequest var5, ServerHttpResponse var6);
}

```
###步骤2：写一个ResponseBodyAdvice实现类
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
        if (o instanceof String) {
            return JsonUtil.object2Json(Result.suc(o));
        }
        return Result.suc(o);
    }
}

```

以上代码，有2个地方需要重点讲解：
#### 第1个地方：@ControllerAdvice 注解：
@ControllerAdvice这是一个非常有用的注解，它的作用是增强Controller的扩展功能类。
那@ControllerAdvice对Controller增强了哪些扩展功能呢？主要体现在2方面：
1. 对Controller全局数据统一处理，例如，我们这节课就是对response统一封装。
1. 对Controller全局异常统一处理，这个后面的课程会详细讲解。

在使用@ControllerAdvice时，还要特别注意，加上basePackages,
@ControllerAdvice(basePackages = "com.agan.boot"),因为如果不加的话，它可是对整个系统的Controller做了扩展功能，
它会对某些特殊功能产生冲突，例如 不加的话，在使用swagger时会出现空白页异常。


#### 第2个地方：beforeBodyWrite方法体的response类型判断
``` 
if (o instanceof String) {
            return JsonUtil.object2Json(ResResult.suc(o));
}
```
以上代码一定要加，因为Controller的返回值为String的时候，它是直接返回String,不是json，
故我们要手工做下json转换处理



##六：课后练习题
本节课，我们没有对Controller的异常做处理，是会出问题的。
在本节课的基础上执行以下代码：
``` 
@GetMapping(value="/error")
public void  error(){
    int i=9/0;
}
```
自己体验下，会出什么问题？ 然后思考怎么解决？

