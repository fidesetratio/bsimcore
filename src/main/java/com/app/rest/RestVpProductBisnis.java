package com.app.rest;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.request.ProductRequest;
import com.app.request.ProductResponse;
import com.app.services.CommonDaoService;
import com.app.services.SetupProductService;

@RestController
@RequestMapping("api")
public class RestVpProductBisnis {

	
	@Autowired
	private SetupProductService setupProductService;
	
	@Autowired
	private CommonDaoService commonDaoService;
	
	@RequestMapping(value="/checking", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponse> checking(@RequestBody ProductRequest request ) throws ParseException{
		 ProductResponse bss = new ProductResponse();
		 System.out.println("lsbs_id"+request.getLsbs_id());
		 System.out.println("lsbs_number"+request.getLsdbs_number());

		 System.out.println("paymode"+request.getPaymode());
		 
		 
		 List<HashMap<String,Object>> list = setupProductService.selectVpProductBisnis(request.getLsbs_id(), request.getLsdbs_number(),Integer.parseInt(request.getPaymode()), "01");
		 
		 if(list.size()>0){
			 HashMap<String,Object> map = list.get(0);
			 String content = (String)map.get(request.getFunction().toUpperCase().trim());
			 
			 HashMap<String,Object> params = request.getParams();
			 
			 for(String k:params.keySet()){
				 String key= "$P{"+k+"}";
				 String replace = (String) params.get(k);
				 content = content.replace(key,replace);
			 }
			 
			 System.out.println("params size:"+params.size());
			 if(content != null)
			 {
				 bss.setMessage(content);
				 HashMap object = commonDaoService.selectCommon(content);
				 bss.setResult(object);
			 }
			 else
			 bss.setMessage(null);
						 
		 }else{
			 bss.setMessage("error tuh");
		 }
		 
		 
		 
		 
		 
		 ResponseEntity<ProductResponse> response  = new ResponseEntity<ProductResponse>(bss,HttpStatus.OK);
		 return response;
			
	}

	
	
}
