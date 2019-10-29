package com.app.rest;

import groovy.lang.GroovyShell;

import java.text.ParseException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.product.Product;
import com.app.request.ProductRequest;
import com.app.request.ProductResponse;
import com.app.services.SetupProductService;

@RestController
@RequestMapping("api")
public class RestNProduct {
	@Autowired
	private SetupProductService setupProductService;
	
	
	@RequestMapping(value="/product", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponse> product(@RequestBody ProductRequest request) throws ParseException{
		ProductResponse productResponse = new ProductResponse();
		int product = request.getLsbs_id();
		int sub_product = request.getLsdbs_number();
		int cb = Integer.parseInt(request.getPaymode());
		String kurs = "01";		
		HashMap<String,Object> params = request.getParams();
		GroovyShell sheel = new GroovyShell();
		
		//SetupProductService setupProductService,GroovyShell sheel, int product, int sub_product, int cb, String kurs
		Product prod = new Product(setupProductService, sheel, product, sub_product, cb, kurs,params);
		prod.proses(1);		
		productResponse.setResult(prod.getRets());
		ResponseEntity<ProductResponse> response  = new ResponseEntity<ProductResponse>(productResponse,HttpStatus.OK);
	    return response;
	}
	

}
