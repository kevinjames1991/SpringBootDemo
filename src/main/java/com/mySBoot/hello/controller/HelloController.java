package com.mySBoot.hello.controller;  
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
  
@RestController  
@EnableAutoConfiguration  
@RequestMapping("/helloC")
public class HelloController {
      
	private static final Logger logger=LoggerFactory.getLogger(HelloController.class);  
	
	@Value("${hello.prop:noProp}")
	private String helloProp;
	
	@RequestMapping("/home")
//    @ResponseBody
    String home(@RequestParam(name="name") String name) { 
        return "Hello " + name+" "+helloProp;  
    }  
      
    @RequestMapping("/hello/{myName}")  
    @ResponseBody
    String index(@PathVariable String myName) {  
        return "Hello "+myName+"!!!";  
    }  
    
    public static void main(String[] args) {
		try {
			int i = 1/0;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
    
}  