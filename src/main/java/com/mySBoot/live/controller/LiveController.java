package com.mySBoot.live.controller;  
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mySBoot.common.ResponseBase;
import com.mySBoot.live.service.LiveDictService;
  
@RestController  
@EnableAutoConfiguration  
@RequestMapping("/live")
public class LiveController {
      
	protected static Logger logger=LoggerFactory.getLogger(LiveController.class); 
	
	@Autowired
	private LiveDictService liveDictService;
	
	@RequestMapping("/getLiveDict")
    public ResponseBase getLiveDict(@RequestParam(name="value") String value) {
		ResponseBase res = new ResponseBase();
		res.setData(liveDictService.getLiveDict(value));
        return res;  
    }  
      
}  