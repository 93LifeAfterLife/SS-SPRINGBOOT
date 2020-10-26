package com.ss.pj.test.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试线程并发量, 并自定义threadFactory和自定义输出线程名称
 * @author td
 */
public class ThreadTest01 {
	// 阻塞队列, 内含任务Runnable
	static	BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);

	// 自定义线程工厂
	static ThreadFactory factory = new ThreadFactory() {
		// 前缀
		static final String PREFIX = "MyThread";
		// 自增后缀, 开始是1
		AtomicInteger at = new AtomicInteger(1);
		@Override
		public Thread newThread(Runnable r) {
			// 自定义线程名
			return new Thread(r, PREFIX+"-"+at.getAndIncrement());
		}
	};

	static ThreadPoolExecutor tPool = new ThreadPoolExecutor(
			2, 					// corePoolSize: 核心线程数
			3, 					// maximumPoolSize: 最大线程数
			10, 				// keepAliveTime: 线程空闲时存活时间
			TimeUnit.SECONDS, 	// unit: 时间单位
			workQueue,			// workQueue: 工作队列
			factory				// threadFactory: 线程工厂
			);		

	static void doTask(int taskNo) {
		// 执行任务
		tPool.execute(new Runnable() {
			@Override
			public void run() {
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName + " execute task-" + taskNo);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		doTask(1);
		doTask(2);
		doTask(3);
		doTask(4);
		doTask(5);
		// doTask(6);
	}
}
