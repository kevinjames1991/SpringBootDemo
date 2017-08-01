package com.mySBoot.thread.controller;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class TestJoin implements Runnable {

	public static void main(String[] sure) throws InterruptedException {
		Thread t = new Thread(new TestJoin());
		long start = System.currentTimeMillis();
		t.start();
		t.join(1010);// 等待线程t执行1010毫秒后再执行主线程接下来的代码
		System.out.println(System.currentTimeMillis() - start);// 打印出时间间隔
		System.out.println("Main finished");// 打印主线程结束
	}

	@Override
	public void run() {
		System.out.println("start");
//		 synchronized (currentThread()) {
		for (int i = 1; i <= 5; i++) {
			try {
				sleep(1000);// 睡眠5秒，循环是为了方便输出信息
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("睡眠" + i);
		}
		System.out.println("TestJoin finished");// t线程结束
	}
//	 }
}