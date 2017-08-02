package com.mySBoot.thread.controller;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestDeadLock implements Runnable {
	public int flag = 1;
	static Object o1 = new Object(), o2 = new Object();
	private final Lock lock = new ReentrantLock();

	public boolean checkLock() {
		return lock.tryLock();
	}

	public void run() {
		if (checkLock()) {
			try {
				System.out.println("flag=" + flag);
				if (flag == 1) {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}

					System.out.println("1");
				}
				if (flag == 0) {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("0");
				}
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		TestDeadLock td1 = new TestDeadLock();
		TestDeadLock td2 = new TestDeadLock();
		td1.flag = 1;
		td2.flag = 0;
		Thread t1 = new Thread(td1);
		Thread t2 = new Thread(td2);
		t1.start();
		t2.start();
	}
}