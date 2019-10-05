Spring Boot 异步框架
### 一、课程目标
熟悉spring的异步框架，学会使用异步@Async注解


### 二、为什么要用异步框架，它解决什么问题？

在SpringBoot的日常开发中，一般都是同步调用的。但经常有特殊业务需要做异步来处理，例如：注册新用户，送100个积分，或下单成功，发送push消息等等。
就拿注册新用户为什么要异步处理？
- 第一个原因：容错性、健壮性，如果送积分出现异常，不能因为送积分而导致用户注册失败；
因为用户注册是主要功能，送积分是次要功能，即使送积分异常也要提示用户注册成功，然后后面在针对积分异常做补偿处理。
- 第二个原因：提升性能，例如注册用户花了20毫秒，送积分花费50毫秒，如果用同步的话，总耗时70毫秒，用异步的话，无需等待积分，故耗时20毫秒。
故，异步能解决2个问题，性能和容错性。

### 三、SpringBoot异步调用
在SpringBoot中使用异步调用是很简单的，只需要使用@Async注解即可实现方法的异步调用。

### 四、@Async异步调用例子
#### 步骤1：开启异步任务
采用@EnableAsync来开启异步任务支持，另外需要加入@Configuration来把当前类加入springIOC容器中。
``` 
@Configuration
@EnableAsync
public class SyncConfiguration {

}
```
#### 步骤2：在方法上标记异步调用
增加一个service类，用来做积分处理。
@Async添加在方法上，代表该方法为异步处理。
``` 
public class ScoreService {

    private static final Logger logger = LoggerFactory.getLogger(ScoreService.class);

    @Async
    public void addScore(){
        //TODO 模拟睡5秒，用于赠送积分处理
        try {
            Thread.sleep(1000*5);
            logger.info("--------------处理积分--------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
```

### 五、为什么要给@Async自定义线程池？
@Async注解，在默认情况下用的是SimpleAsyncTaskExecutor线程池，该线程池不是真正意义上的线程池，因为线程不重用，每次调用都会新建一条线程。
可以通过控制台日志输出查看，每次打印的线程名都是[task-1]、[task-2]、[task-3]、[task-4].....递增的。
@Async注解异步框架提供多种线程
SimpleAsyncTaskExecutor：不是真的线程池，这个类不重用线程，每次调用都会创建一个新的线程。
SyncTaskExecutor：这个类没有实现异步调用，只是一个同步操作。只适用于不需要多线程的地方
ConcurrentTaskExecutor：Executor的适配类，不推荐使用。如果ThreadPoolTaskExecutor不满足要求时，才用考虑使用这个类
ThreadPoolTaskScheduler：可以使用cron表达式
ThreadPoolTaskExecutor ：最常使用，推荐。 其实质是对java.util.concurrent.ThreadPoolExecutor的包装

### 六、为@Async实现一个自定义线程池
#### 步骤1：配置线程池
```
@Configuration
@EnableAsync
public class SyncConfiguration {
    @Bean(name = "scorePoolTaskExecutor")
    public ThreadPoolTaskExecutor getScorePoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        taskExecutor.setCorePoolSize(10);
        //线程池维护线程的最大数量,只有在缓冲队列满了之后才会申请超过核心线程数的线程
        taskExecutor.setMaxPoolSize(100);
        //缓存队列
        taskExecutor.setQueueCapacity(50);
        //许的空闲时间,当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        taskExecutor.setKeepAliveSeconds(200);
        //异步方法内部线程名称
        taskExecutor.setThreadNamePrefix("score-");
        /**
         * 当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略
         * 通常有以下四种策略：
         * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
         * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
         * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
         * ThreadPoolExecutor.CallerRunsPolicy：重试添加当前的任务，自动重复调用 execute() 方法，直到成功
         */
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }
}

```


#### 步骤2： 为@Async指定线程池名字
``` 
    @Async("scorePoolTaskExecutor")
    public void addScore2(){
        //TODO 模拟睡5秒，用于赠送积分处理
        try {
            Thread.sleep(1000*5);
            log.info("--------------处理积分--------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
```

### 七、课后练习
在现实的互联网项目开发中，针对高并发的请求，一般的做法是高并发接口单独线程池隔离处理。
假设现在2个高并发接口：
一个是修改用户信息接口，刷新用户redis缓存.
一个是下订单接口，发送app push信息.
请参考本课程内容，设计2个线程池，分别用于[刷新用户redis缓存]和[发送app push信息]














