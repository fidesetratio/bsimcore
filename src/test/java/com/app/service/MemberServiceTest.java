package com.app.service;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.model.gadget.prod.Cmdeditbac;
import com.app.services.SubmitServices;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
public class MemberServiceTest {
	
	
	@Autowired
	private SubmitServices submitService;
	
	@Test
	public void testSubmitPA() throws Exception{
		Cmdeditbac edit = loadObject();
		submitService.save(edit);
		Assert.assertTrue(true);
	}

	public Cmdeditbac loadObject(){
		Cmdeditbac edit = new Cmdeditbac();
		ObjectMapper mapper = new ObjectMapper();
		try {
			edit = mapper.readValue(new File("data/output.json"), Cmdeditbac.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("data is fine");
		System.out.println("firstname:"+edit.getPemegang().getMcl_first());
		
		
		
		return edit;
	}
	
	
}
