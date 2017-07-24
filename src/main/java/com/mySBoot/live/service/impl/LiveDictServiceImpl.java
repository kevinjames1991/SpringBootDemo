package com.mySBoot.live.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mySBoot.live.pojo.LiveDict;
import com.google.common.collect.Lists;
import com.mySBoot.live.dao.LiveDictMapper;
import com.mySBoot.live.service.LiveDictService;

/**
* created on 2017-7-24 14:43:17
*/
@Service
public class LiveDictServiceImpl implements LiveDictService {

    @Autowired
    private LiveDictMapper liveDictMapper;

	@Override
	public List<LiveDict> getLiveDict(String value) {
		List<LiveDict> list = Lists.newArrayList();
		Map<Object, Object> map = new HashMap<>();
		map.put("value", value);
		list = liveDictMapper.selectDictTree(map);
		return list;
	}

}