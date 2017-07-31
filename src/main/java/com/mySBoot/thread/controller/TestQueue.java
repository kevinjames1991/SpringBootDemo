package com.mySBoot.thread.controller;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class TestQueue {
	
	public static void main(String[] args) {
		BlockingQueue<String> queue = new ArrayBlockingQueue<>(5000);
		Producer producer = new Producer(queue);
		Consumer consumer = new Consumer(queue);
		Thread pThread = new Thread(producer);
		Thread cThread = new Thread(consumer);
		pThread.start();
		cThread.start();
	}
}

class Producer implements Runnable{
	private BlockingQueue<String> queue;
	private static final ExecutorService executor = Executors.newFixedThreadPool(4);
	
	public Producer(BlockingQueue<String> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		String baseDir = "E:\\51haody\\诛仙青云志\\新建文件夹";
		Stream.of(baseDir).map(url -> {
				File file = new File(url);
				Collection<File> files = null;
				if (file.isDirectory()) {
					files = FileUtils.listFiles(file, null, true);
				} else {
					files = new LinkedList<>();
					files.add(file);
				}
				return files;
			}).flatMap(p -> p.stream()).forEach(file -> {
				String fileName = file.getName();
				executor.submit(() -> {
						try {
							queue.put(fileName);
//							System.out.println(System.currentTimeMillis() + " put -->> "+fileName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
			});
	}
}

class Consumer implements Runnable {
	private BlockingQueue<String> queue;
	private static final ExecutorService executor = Executors.newFixedThreadPool(4);
	
	public Consumer(BlockingQueue<String> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				String name = queue.take();
				executor.submit(() -> {
					try {
						System.out.println(System.currentTimeMillis() + " take -- >> "+ name);
						Thread.sleep(100);//模拟处理过程
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
