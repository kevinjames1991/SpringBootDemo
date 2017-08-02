package com.mySBoot.brand.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mySBoot.brand.pojo.DBrandInfo;
import com.mySBoot.brand.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService{

	@Autowired
	private BrandServiceSolr brandServiceSolr;
	
	@Override
	public List<DBrandInfo> getSolrBrandList() {
		List<DBrandInfo> list = Lists.newArrayList();
		SolrQuery query = new SolrQuery("*:*");
		query.setQuery("brandName:*1*");
		query.setFields("id","name","status");
		query.setRows(1000);
		list = brandServiceSolr.queryList(query, DBrandInfo.class);
		
		return list;
	}
	
	
}
