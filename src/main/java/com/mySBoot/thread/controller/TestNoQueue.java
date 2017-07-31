package com.mySBoot.thread.controller;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class TestNoQueue {
	private static final ExecutorService executor = Executors.newFixedThreadPool(4);
	
	public static void main(String[] args) {
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
							System.out.println(System.currentTimeMillis() + " put -- >> "+ fileName);
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
			});
	}
}
