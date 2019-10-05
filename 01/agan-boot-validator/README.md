##一、课程目标
1. 什么是Validator参数校验器？ 为什么要用？
2. 编码实现对用户名、密码、邮箱、身份证的Validator校验
3. 实现一个手机号码的自定义校验
4. 把validator异常加入《全局异常处理器》

##二、 为什么要用Validator参数校验器，它解决了什么问题？
背景：在日常的接口开发中，经常要对接口的参数做校验，例如，登录的时候要校验用户名 密码是否为空。但是这种日常的接口参数校验太烦锁了，代码繁琐又多。

Validator框架就是为了解决开发人员在开发的时候少写代码，提升开发效率的；它专门用来做接口参数的校验的，例如 email校验、用户名长度必须位于6到12之间 等等。

原理：spring 的validator校验框架遵循了JSR-303验证规范（参数校验规范）,JSR是Java Specification Requests的缩写。

在默认情况下，Spring Boot会引入Hibernate Validator机制来支持JSR-303验证规范。

spring boot的validator校验框架有3个特性：
1. JSR303特性： JSR303是一项标准,只提供规范不提供实现，规定一些校验规范即校验注解，如@Null，@NotNull，@Pattern，位于javax.validation.constraints包下。
2. hibernate validation特性：hibernate validation是对JSR303规范的实现，并增加了一些其他校验注解，如@Email，@Length，@Range等等
3. spring validation：spring validation对hibernate validation进行了二次封装，在springmvc模块中添加了自动校验，并将校验信息封装进了特定的类中。

##三、案例实战：实现一个SpringBoot的参数校验功能
### 步骤1：pom文件加入依赖包
springboot天然支持validator数据校验
``` 
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### 步骤2：创建一个VO类
``` 

@Data
public class UserVO {

    private Integer id;

    @NotEmpty(message="用户名不能为空")
    @Length(min=6,max = 12,message="用户名长度必须位于6到12之间")
    private String username;

    @NotEmpty(message="密码不能为空")
    @Length(min=6,message="密码长度不能小于6位")
    private String password;

    @Email(message="请输入正确的邮箱")
    private String email;

//    @Phone
//    private String phone;

    @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$", message = "身份证格式错误")
    private String idCard;

    private Byte sex;
    private Byte deleted;
    private Date updateTime;
    private Date createTime;
}
```

### 步骤3：加一个体验类
``` 
@Api(description = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping(value = "/user/create", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public void crreteUser(@RequestBody @Validated UserVO userVO) {

    }

}
```
注意：以上代码一定要加上@Validated注解，它的作用是用来校验UserVO的参数是否正确

执行结果：

``` 
{
  "timestamp": "2019-10-03T02:34:54.545+0000",
  "status": 400,
  "error": "Bad Request",
  "errors": [
    {
      "codes": [
        "Pattern.userVO.idCard",
        "Pattern.idCard",
        "Pattern.java.lang.String",
        "Pattern"
      ],
      "arguments": [
        {
          "codes": [
            "userVO.idCard",
            "idCard"
          ],
          "arguments": null,
          "defaultMessage": "idCard",
          "code": "idCard"
        },
        [],
        {
          "defaultMessage": "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$",
          "arguments": null,
          "codes": [
            "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$"
          ]
        }
      ],
      "defaultMessage": "身份证格式错误",
      "objectName": "userVO",
      "field": "idCard",
      "rejectedValue": "string",
      "bindingFailure": false,
      "code": "Pattern"
    },
    {
      "codes": [
        "Email.userVO.email",
        "Email.email",
        "Email.java.lang.String",
        "Email"
      ],
      "arguments": [
        {
          "codes": [
            "userVO.email",
            "email"
          ],
          "arguments": null,
          "defaultMessage": "email",
          "code": "email"
        },
        [],
        {
          "defaultMessage": ".*",
          "arguments": null,
          "codes": [
            ".*"
          ]
        }
      ],
      "defaultMessage": "请输入正确的邮箱",
      "objectName": "userVO",
      "field": "email",
      "rejectedValue": "string",
      "bindingFailure": false,
      "code": "Email"
    }
  ],
  "message": "Validation failed for object='userVO'. Error count: 2",
  "path": "/user/user/create"
}
```


## 四、Validation常用注解

- @Null 限制只能为null
- @NotNull 限制必须不为null
- @AssertFalse 限制必须为false
- @AssertTrue 限制必须为true
- @DecimalMax(value) 限制必须为一个不大于指定值的数字
- @DecimalMin(value) 限制必须为一个不小于指定值的数字
- @Digits(integer,fraction) 限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction
- @Future 限制必须是一个将来的日期
- @Max(value) 限制必须为一个不大于指定值的数字
- @Min(value) 限制必须为一个不小于指定值的数字
- @Past 限制必须是一个过去的日期
- @Pattern(value) 限制必须符合指定的正则表达式
- @Size(max,min) 限制字符长度必须在min到max之间
- @Past 验证注解的元素值（日期类型）比当前时间早
- @NotEmpty 验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）
- @NotBlank 验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
- @Email 验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式


##自定义validator注解
为什么要自定义validator注解呢？
因为validator框架支持的注解有限，不可能方方面面都支持，故需要我们自定义注解。
我们就以手机号码为例子，教大家如何写一个对手机号码校验的validator注解。
### 步骤1：创建一个@interface的手机校验注解
``` 
@Documented
// 指定注解的实现类
@Constraint(validatedBy = PhoneValidator.class)
@Target( { METHOD, FIELD })
@Retention(RUNTIME)
public @interface Phone {
    String message() default "请输入正确的手机号码";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Phone[] value();
    }
}

```

### 步骤2：手机号码校验注解实现类
``` 

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$"
    );

    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    /**
     *
     * 校验的实现逻辑
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ( value == null || value.length() == 0 ) {
            return true;
        }
        Matcher m = PHONE_PATTERN.matcher(value);
        return m.matches();
    }
}
```

### 步骤3：给UserVO类，加上手机号码校验注解
``` 

@Data
public class UserVO {

    private Integer id;

    @NotEmpty(message="用户名不能为空")
    @Length(min=6,max = 12,message="用户名长度必须位于6到12之间")
    private String username;

    @NotEmpty(message="密码不能为空")
    @Length(min=6,message="密码长度不能小于6位")
    private String password;

    @Email(message="请输入正确的邮箱")
    private String email;

    @Phone
    private String phone;

    @Pattern(regexp = "^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$", message = "身份证格式错误")
    private String idCard;

    private Byte sex;
    private Byte deleted;
    private Date updateTime;
    private Date createTime;
}
```
### 步骤4：体验效果
``` 
{
  "timestamp": "2019-10-03T03:42:10.928+0000",
  "status": 400,
  "error": "Bad Request",
  "errors": [
    {
      "codes": [
        "Phone.userVO.phone",
        "Phone.phone",
        "Phone.java.lang.String",
        "Phone"
      ],
      "arguments": [
        {
          "codes": [
            "userVO.phone",
            "phone"
          ],
          "arguments": null,
          "defaultMessage": "phone",
          "code": "phone"
        }
      ],
      "defaultMessage": "请输入正确的手机号码",
      "objectName": "userVO",
      "field": "phone",
      "rejectedValue": "string",
      "bindingFailure": false,
      "code": "Phone"
    }
  ],
  "message": "Validation failed for object='userVO'. Error count: 1",
  "path": "/user/user/create"
}
```


## 六、把validator异常加入《全局异常处理器》
那为什么要把validator异常加入《全局异常处理器》呢？
因为validator异常返回的内容是json，而且json数据结构（例如上文的json）特别复杂.
不利于客户端联调，而且也不友好提示，故，必须加入《全局异常处理器》

### 步骤1：《全局异常处理器》加入validator异常处理

``` 
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
```
### 步骤2：结果
``` 
{
  "status": 10001,
  "desc": "idCard=[身份证格式错误]  email=[请输入正确的邮箱]  phone=[请输入正确的手机号码]  ",
  "data": null
}
```
### 七：课后练习题
本课程的UserVO,还遗留了一个sex没有校验，请为该字段设计一个自定义注解；
注解的校验规则：0=女，1=男，输入错误提示：性别输入错误！
