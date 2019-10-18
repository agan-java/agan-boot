package com.agan.boot;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class HelloworldApplicationTests {


	public void contextLoads() {
	}

	@Test
	public void testSmoothBursty() {
		RateLimiter r = RateLimiter.create(1);
		while (true) {
			try {
				Thread.sleep(1000*10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("get 1 tokens: " + r.acquire() + "s");
			System.out.println("end");

		}
	}

	@Test
	public void testSmoothwarmingUp() {
		RateLimiter r = RateLimiter.create(1, 5, TimeUnit.SECONDS);
		while (true)
		{
			System.out.println("get 1 tokens: " + r.acquire(1) + "s");
			System.out.println("get 1 tokens: " + r.acquire(1) + "s");
			System.out.println("get 1 tokens: " + r.acquire(1) + "s");
			System.out.println("get 1 tokens: " + r.acquire(1) + "s");
			System.out.println("end");
			/**
			 * output:
			 * get 1 tokens: 0.0s
			 * get 1 tokens: 1.329289s
			 * get 1 tokens: 0.994375s
			 * get 1 tokens: 0.662888s  上边三次获取的时间相加正好为3秒
			 * end
			 * get 1 tokens: 0.49764s  正常速率0.5秒一个令牌
			 * get 1 tokens: 0.497828s
			 * get 1 tokens: 0.49449s
			 * get 1 tokens: 0.497522s
			 */
		}
	}
}
