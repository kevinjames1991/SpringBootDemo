package com.mySBoot.hello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mySBoot.common.util.RedisUtil;

@RestController
@EnableAutoConfiguration
@RequestMapping("/testRedis")
public class TestRedisController {

	private static final Logger logger = LoggerFactory.getLogger(TestRedisController.class);

	@RequestMapping("/set")
	@ResponseBody
	boolean set(@RequestParam(name = "key") String key, @RequestParam(name = "value") String value) {
		return RedisUtil.set(key, value);
	}

	@RequestMapping("get")
	@ResponseBody
	String index(@RequestParam(name = "key") String key) {
		return RedisUtil.getString(key);
	}

}