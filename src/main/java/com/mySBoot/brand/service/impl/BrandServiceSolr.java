package com.mySBoot.brand.service.impl;

import org.springframework.stereotype.Service;

import com.mySBoot.brand.pojo.DBrandInfo;
import com.mySBoot.common.service.AbstractSolrClient;

@Service
public class BrandServiceSolr extends AbstractSolrClient<DBrandInfo>{

	@Override
	protected String getCollectionName() {
		
		return "brand";
	}

	
	
	
}
