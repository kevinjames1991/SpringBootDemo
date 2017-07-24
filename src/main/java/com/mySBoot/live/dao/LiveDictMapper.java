package com.mySBoot.live.dao;

import java.util.List;
import java.util.Map;

import com.mySBoot.live.pojo.LiveDict;

/**
* created on 2017-7-24 14:43:17
*/
public interface LiveDictMapper {

    int insertSelective(LiveDict liveDict);

    int deleteByPrimaryKey(Long id);

    LiveDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LiveDict liveDict);

    int countByExample(LiveDict liveDict);

    List<LiveDict> selectByExample(LiveDict liveDict);
    
    List<LiveDict> selectDictTree(Map<Object, Object> map);

}

