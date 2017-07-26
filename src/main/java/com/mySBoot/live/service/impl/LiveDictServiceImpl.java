package com.mySBoot.live.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mySBoot.live.controller.LiveController;
import com.mySBoot.live.dao.LiveDictMapper;
import com.mySBoot.live.pojo.LiveDict;
import com.mySBoot.live.service.LiveDictService;

/**
 * 
 * @author chenzhangwei
 * @time 2017年7月26日上午10:18:38
 */
@Service
public class LiveDictServiceImpl implements LiveDictService {

	
    @Autowired
    private LiveDictMapper liveDictMapper;
    
    private static Logger logger = LoggerFactory.getLogger(LiveDictServiceImpl.class);

	@Override
	public List<LiveDict> getLiveDict(String value) {
		List<LiveDict> list = Lists.newArrayList();
		Map<Object, Object> map = new HashMap<>();
		map.put("value", value);
		list = liveDictMapper.selectDictTree(map);
		logger.info("hhh");
		logger = LoggerFactory.getLogger(LiveController.class);
		try {
			Thread.sleep(1000*5);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

}