# SpringBoot集成swagger实战

### 一、本课程目标：
1. 弄清楚，为什么要用swagger，它解决了什么问题？
2. 编码实现2个springboot接口，让swagger自动生成接口文档


### 二、为什么要用swagger,它解决了什么问题？
随着sprnigboot、springcloud等微服务的流行，在微服务的设计下，小公司微服务小的几十，大公司大的几百上万的微服务。
这么多的微服务必定产生了大量的接口调用。而接口的调用就必定要写接口文档。
在微服务的盛行下，成千上万的接口文档编写，不可能靠人力来编写，故swagger就产生了，它采用自动化实现并解决了人力编写接口文档的问题；
它通过在接口及实体上添加几个注解的方式就能在项目启动后自动化生成接口文档，

Swagger 提供了一个全新的维护 API 文档的方式，有4大优点：
1.自动生成文档：只需要少量的注解，Swagger 就可以根据代码自动生成 API 文档，很好的保证了文档的时效性。
2.跨语言性，支持 40 多种语言。
3.Swagger UI 呈现出来的是一份可交互式的 API 文档，我们可以直接在文档页面尝试 API 的调用，省去了准备复杂的调用参数的过程。
4.还可以将文档规范导入相关的工具（例如 SoapUI）, 这些工具将会为我们自动地创建自动化测试。


### 三、案例实战：把springboot的接口，自动生成接口文档

#### 步骤1： pom文件加入依赖包
``` 

<!--swagger-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<!--swagger-ui-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```
#### 步骤2：修改配置文件
1. application.properties 加入配置
``` 
#表示是否开启 Swagger，一般线上环境是关闭的
spring.swagger2.enabled=true
```
2.增加一个swagger配置类
``` 
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value(value = "${spring.swagger2.enabled}")
    private Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(swaggerEnabled)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.agan.boot"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("阿甘讲解 Spring Boot")
                .termsOfServiceUrl("https://study.163.com/provider/1016671292/index.htm")
                .version("1.0")
                .build();
    }
}
```
以上注意点：
1.createRestApi() 这个方法一定要写上你的包名名，代表需要生成接口文档的目录包

体验地址：http://127.0.0.1:9090/swagger-ui.html


















