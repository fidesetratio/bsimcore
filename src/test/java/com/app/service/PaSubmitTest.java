package com.app.service;

import java.io.File;
import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;

import com.app.model.gadget.prod.Cmdeditbac;
import com.app.services.PaSubmit;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
public class PaSubmitTest {

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Test
	public void testSubmitPa(){
		Cmdeditbac edit = loadObject();
		PaSubmit paSubmit = new PaSubmit(transactionManager, sqlSession);
		try {
			edit = paSubmit.save(edit);
			System.out.println("reg spaj:"+edit.getPemegang().getReg_spaj());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(edit.getPemegang().getReg_spaj()!=null);
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
