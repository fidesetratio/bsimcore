package com.app.rest;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
public class RestGroovyTesting {
	
	@RequestMapping(value="/groovytesting", method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> groovyTesting( ) throws ParseException{
		Binding binding = new Binding();
		GroovyShell shell = new GroovyShell(binding);
		binding.setVariable("PREMIUM", 10000);
		binding.setVariable("min_up1", 0);
		
		shell.evaluate("min_up1 = 5 * PREMIUM / 100");
		
		
/*		System.out.println("result:"+binding.getVariable("result"));
*/		ResponseEntity<String> entity = new ResponseEntity<>(""+binding.getVariable("min_up1"),HttpStatus.OK);
		return entity;
		
		
		
	}


}
