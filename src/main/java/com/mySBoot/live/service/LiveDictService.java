package com.mySBoot.live.service;

import java.util.List;

import com.mySBoot.live.pojo.LiveDict;

/**
* created on 2017-7-24 14:43:17
*/
public interface LiveDictService{

	List<LiveDict> getLiveDict(String value);
}