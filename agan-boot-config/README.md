## 一、本课程目标：
 在前面的《SpringBoot的入门例子》的课程基础上，我们继续来讲解springboot的常用配置，
   即讲解resources包下的application.properties如何使用。
   
## 二、最常用的配置1：改端口
``` 
server.port=9090
```

## 三、最常用的配置2：改随机端口
思考问题：固定端口为什么不能用？为什么要改随机端口？
1.如果在用一台服务器上，多个服务如果用同一个端口会造成端口冲突。
2.在现实的微服务(springcloud、dubbo)开发中，开发人员是不用记住ip和端口的.
故，我们一般在真实的开发环境下，是设置一个随机端口，就不用去管理端口了，也不会造成端口冲突。
``` 
server.port=${random.int[1024,9999]}
```

## 四、自定义属性配置
讲自定义属性配置，就必须讲解@value注解。<br>
@value的作用是：为了简化读取properties文件中的配置值，spring支持@value注解的方式来获取，这种方式大大简化了项目配置，提高业务中的灵活性。<br>
在application.properties的文件下，加入如下配置
``` 
agan.msg=hi,hello world!!
```

``` 

@RestController
public class HelloController {
    
    @Value("${agan.msg}")
    private  String msg;

    @GetMapping("msg")
    public String getMsg() {
        return msg;
    }
}
```

## 五、课后练习
自己搭建一个springboot项目,实现以下2个功能：<br>
1.springboot的启动端口，设置为2048到8096的随机端口。<br>
2.在application.properties的文件下，加入如下配置<br>
``` 
boot.msg=hi,spring boot!
```
然后在浏览器中打印boot.msg的配置内容。
