#在springboot中使用lombok

##一、本课程目标：
1.学会安装lombok插件，并学会用lombok。
2.掌握lombok的核心@Data注解
3.掌握lombok的核心@Slf4j注解


##二、为什么要使用lombok,它解决了什么问题？
Lombok 是一个 IDEA 插件,也是一个依赖jar 包。
它解决了开发人员少写代码，提升开发效率。
它使开发人员不要去写javabean的getter/setter方法，写构造器、equals等方法；最方便的是你对javabean的属性增删改，
你不用再重新生成getter/setter方法。省去一大麻烦事。

##三、idea安装lombok插件
###步骤1：idea搜索lombok插件
打开IDEA的Settings面板，并选择Plugins选项，然后点击 “Browse repositories..”
![image](https://github.com/agan-java/images/blob/master/lombok/14.png?raw=true)

###步骤2：安装并重启idea
点击安装，然后安装提示重启IDEA，安装成功;
![image](https://github.com/agan-java/images/blob/master/lombok/15.png?raw=true)
记得重启IDEA，不然不生效。


### 四、体验lombok核心注解@data
#### 步骤1： 什么是@data注解
@Data 注解在实体类上，自动生成javabean的getter/setter方法，写构造器、equals等方法；

#### 步骤2：pom文件添加依赖包
``` 
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.8</version>
</dependency>
```
### 五、体验lombok第二核心注解@Slf4j
注解@Slf4j的作用就是代替一下代码
``` 
private static final Logger logger = LoggerFactory.getLogger(UserController.class);
```
让你不用每次都写重复的代码



##六：课后练习题
```
public class City {

    private Long id;

    private Long provinceId;

    private String cityName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
```
请把以上City类，转换为lombok对象。
然后建个测试类，执行以下代码
```
City city=new City();
city.setId(100);
city.setCityName("深圳");
city.setProvinceId(200);
log.error("-----------error-------------"+city.toString());
```
















