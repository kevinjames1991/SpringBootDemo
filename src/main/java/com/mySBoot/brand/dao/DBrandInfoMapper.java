package com.mySBoot.brand.dao;

import com.mySBoot.brand.pojo.DBrandInfo;

public interface DBrandInfoMapper {
    int insert(DBrandInfo record);

    int insertSelective(DBrandInfo record);
}