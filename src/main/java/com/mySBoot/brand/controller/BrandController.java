package com.mySBoot.brand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mySBoot.brand.service.BrandService;
import com.mySBoot.common.vo.ResponseBase;

@RestController
@RequestMapping("/brand")
public class BrandController {
	
	@Autowired
	private BrandService brandService;

	@RequestMapping("/getList")
	public ResponseBase getBrandList(){
		ResponseBase res = new ResponseBase();
		res.setData(brandService.getSolrBrandList());
		return res;
	}
}
