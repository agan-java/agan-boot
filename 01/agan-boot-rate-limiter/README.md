
### 本课程目标：
1.弄懂为什么要限流，学会限流的技巧。
2.了解限流的令牌桶算法
3.编码实现guava的SpringBoot限流
4.为了提升开发效率，降低代码耦合度，采用自定义注解来实现接口限流

### 互联网系统为什么要限流？
因为互联网系统通常都要面对大并发大流量的请求，在突发情况下，系统是抗不住的，例如，抢票系统 12306,在面对高并发的情况下，就是采用了限流。
例如：12306限流后，就进入了服务降级，在流量高峰期间经常会出现,这的提示语；"当前排队人数较多，请稍后再试！"
故，针对高并发流量来说，限流是最好的保护机制。

### 那什么是限流？如何实现？
限流是对某一时间窗口内的请求数进行限制，保持系统的可用性和稳定性，防止因流量暴增而导致的系统运行缓慢或宕机。
Google开源工具包Guava提供了限流工具类RateLimiter，该类基于令牌桶算法实现流量限制，使用十分方便，而且十分高效。


### 重点讲解令牌桶算法
令牌算法：
假设有一个木桶，这个木桶干了2件事：
1.系统按恒定1/QPS的时间顺序，往桶里放入令牌token;如果桶加满了就不加。
2.当新请求过来时，会在桶里拿走一个token,如果没有token可以拿，就阻塞或拒绝服务。
![image](https://github.com/agan-java/images/blob/master/limiter/01.png?raw=true)
![image](https://github.com/agan-java/images/blob/master/limiter/02.jpg?raw=true)
### 采用guava实现SpringBoot限流
#### 步骤1：pom文件加入guava依赖包
```
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>28.1-jre</version>
</dependency>
```
#### 步骤2：加入限流逻辑
```
@RestController
@Slf4j
public class TestController {

    //限流，1秒钟2个
    private RateLimiter limiter = RateLimiter.create(2);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/limiter")
    public String testLimiter() {
        //500毫秒内，没拿到令牌，就直接进入服务降级
        boolean tryAcquire = limiter.tryAcquire(500, TimeUnit.MILLISECONDS);
        if (!tryAcquire) {
            log.warn("进入服务降级，时间{}", simpleDateFormat.format(new Date()));
            return "当前排队人数较多，请稍后再试！";
        }
        log.info("获取令牌成功，时间{}", simpleDateFormat.format(new Date()));
        return "success";
    }
}
```
以上用到了RateLimiter的2个核心方法：create()、tryAcquire()，以下详细说明
- acquire()	             获取一个令牌, 改方法会阻塞直到获取到这一个令牌, 返回值为获取到这个令牌花费的时间
- acquire(int permits)	 获取指定数量的令牌, 该方法也会阻塞, 返回值为获取到这 N 个令牌花费的时间
- tryAcquire()	         判断时候能获取到令牌, 如果不能获取立即返回 false
- tryAcquire(int permits)	获取指定数量的令牌, 如果不能获取立即返回 false
- tryAcquire(long timeout, TimeUnit unit)	判断能否在指定时间内获取到令牌, 如果不能获取立即返回 false
- tryAcquire(int permits, long timeout, TimeUnit unit)	同上

#### 步骤3：体验效果
浏览器反复刷新，此测试地址： http://127.0.0.1:9090/limiter
```
 进入服务降级，时间2019-09-24 18:17:59
 获取令牌成功，时间2019-09-24 18:17:59
 进入服务降级，时间2019-09-24 18:17:59
 进入服务降级，时间2019-09-24 18:17:59
 获取令牌成功，时间2019-09-24 18:17:59
 进入服务降级，时间2019-09-24 18:17:59

 进入服务降级，时间2019-09-24 18:18:00
 获取令牌成功，时间2019-09-24 18:18:00
 进入服务降级，时间2019-09-24 18:18:00
 进入服务降级，时间2019-09-24 18:18:00
 获取令牌成功，时间2019-09-24 18:18:00
```
从以上日志可以看出，1秒钟内只有2次成功，其他都失败降级了。

### 启用自定义注解实现接口限流
为什么要自定义注解来实现接口限流？
1. 业务代码和限流代码解耦，开发人员只要一个注解，不用关心限流的实现逻辑。
1. 采用实现自定义注解，减少代码冗余。
#### 步骤1：pom文件加入aop依赖包
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
#### 步骤2：实现一个限流自定义注解
```

```
#### 步骤3：用切面拦截限流注解
```

```
#### 步骤3：实现限流接口
```
@GetMapping("/limiter2")
@Limiter(key = "limiter2", permitsPerSecond = 1, timeout = 500, timeunit = TimeUnit.MILLISECONDS,msg = "当前排队人数较多，请稍后再试！")
public String limiter2() {
    log.debug("令牌桶=limiter2，获取令牌成功");
    return "ok";
}

@GetMapping("/limiter3")
@Limiter(key = "limiter3", permitsPerSecond = 2, timeout = 500, timeunit = TimeUnit.MILLISECONDS,msg = "当前排队人数较多，请稍后再试！")
public String limiter3() {
    log.debug("令牌桶=limiter3，获取令牌成功");
    return "ok";
}
```
##：课后练习
guava 的RateLimiter提供了2个方法tryAcquire()和acquire()来实现限流；
我们本课程采用的是tryAcquire()来实现限流，如果采用 acquire()，会怎么样？ 请编码实现。