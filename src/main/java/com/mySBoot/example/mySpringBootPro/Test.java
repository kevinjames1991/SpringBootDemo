package com.mySBoot.example.mySpringBootPro;  
  
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mySBoot.common.util.JsonUtil;
import com.mySBoot.common.vo.JsonResult;

import oracle.net.aso.i;
  
@RestController  
@EnableAutoConfiguration  
@RequestMapping("/test")
public class Test {  
      
	protected static ThreadLocal<Logger> logger= new ThreadLocal<Logger>() {
		public Logger initialValue(){
			return LoggerFactory.getLogger(Test.class);  
		}
	} ;
	private static int i = 0;
	
    @RequestMapping("/homeTest")
    String homeTest(@RequestParam(name="name") String name) { 
    	logger.set(LoggerFactory.getLogger(Example.class));
    	logger.get().info("home"+i);
    	i=1;
        return name + " homeTest";  
    }  
      
    @RequestMapping("/hello/{myName}")  
    String index(@PathVariable String myName) throws Exception {  
    	Thread.sleep(1000*5);
    	logger.get().info("index"+i);
        return "Hello "+myName+"!!!";  
    }  
    
    @RequestMapping("/test")  
    String test(){
		CloseableHttpClient httpClient = HttpClients.createDefault();  
		CloseableHttpResponse response = null;
//		HttpPost post = new HttpPost("http://114.55.231.24:9001/imp/upload/imageUpload");
		HttpPost post = new HttpPost("http://127.0.0.1:9001/imp/upload/imageUpload");
		File file = new File("D:"+File.separator+"temp"+ File.separator+"123.jpg");//123
		FileBody bin = new FileBody(file);
		String result = "result===";
		ContentType contentType = ContentType.create("text/plain",Charset.forName("UTF-8"));
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();      
		builder.addPart("file", bin);
		String key = "img/pictures/B_2/100005645211/yi89.jpg";
		builder.addTextBody("key", key,contentType);
		builder.addTextBody("sizes", "1000x400!,!x50");
//		builder.addTextBody("quality", "90,50");
		builder.addTextBody("isPersist", "true");
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		try {
			System.out.println("=============");
			response = httpClient.execute(post);
			HttpEntity entity2 = response.getEntity();
			result=EntityUtils.toString(entity2);
			System.out.println(result);
			JsonResult jsonResult = JsonUtil.fromJson(result, JsonResult.class);
			System.out.println(jsonResult.isSuccess());
			EntityUtils.consume(entity2);
			System.out.println(response.getStatusLine());
			System.out.println(response.getHeaders("Content-Type")[0].getValue().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		return response.getStatusLine().toString()+result;
    }
    
    @RequestMapping("/test2")  
    String test2(){
    	
    	//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        String imgFile = "D:"+File.separator+"temp"+ File.separator+"1123123.jpg";//待处理的图片  
        InputStream in = null;  
        byte[] data = null;  
        //读取图片字节数组  
        try   
        {  
            in = new FileInputStream(imgFile);          
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
        //对字节数组Base64编码  
//        BASE64Encoder encoder = new BASE64Encoder();  
        return Base64.encode(data);//返回Base64编码过的字节数组字符串  
        /*
    	CloseableHttpClient httpClient = HttpClients.createDefault();  
    	CloseableHttpResponse response = null;
    	HttpPost post = new HttpPost("http://127.0.0.1:9001/imp/upload/imageUpload");
//		HttpPost post = new HttpPost("http://172.31.80.86:9001/imp/upload/imageUpload");
    	File file = new File("D:"+File.separator+"temp"+ File.separator+"1123123.jpg");//M_5096_100358063
    	
    	FileBody bin = new FileBody(file);
    	StringBody comment = new StringBody("A binary file of some kind", ContentType.TEXT_PLAIN);
    	
    	MultipartEntityBuilder builder = MultipartEntityBuilder.create();      
    	builder.addPart("file", bin);
    	builder.addPart("comment", comment);
//		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//		builder.addBinaryBody("file", file);
    	String key = "img/pictures/B_2/10000564521/t1.jpg";
    	String sizes[] = "200x200!,400x400!,!x50p".split(",");
    	builder.addTextBody("key", key);
    	builder.addTextBody("sizes", "200x200!,400x400!,!x50p");
    	HttpEntity entity = builder.build();
    	post.setEntity(entity);
    	try {
//			post = new HttpPost("http://172.31.80.86:9001/imp/upload/imageUpload?file="+file+"&key="+key);
    		response = httpClient.execute(post);
    		if (entity != null) {  
    			System.out.println("Response content length: " + entity.getContentLength());  
    		}  
    		System.out.println("=============");
    		System.out.println(response.getStatusLine());
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}  
    	return response.getStatusLine().toString();*/
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