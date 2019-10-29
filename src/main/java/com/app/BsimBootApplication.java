package com.app;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.app.dao.CommonDao;
import com.app.model.gadget.prod.Agen;
import com.app.model.gadget.prod.Cmdeditbac;
import com.app.services.BsimService;
import com.app.services.MobileSubmit;
import com.app.services.SubmitServices;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@Configuration
@ImportResource("classpath:boot.xml")
public class BsimBootApplication  implements CommandLineRunner {

	@Autowired
	private BsimService service;
	
	@Autowired
	private SubmitServices submitService;
	
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	

	
	public static void main(String[] args) {
		SpringApplication.run(BsimBootApplication.class, args);
	}

	public void run(String... args) throws Exception {
/*		Cmdeditbac edit = loadObject();
		submitService.save(edit);*/

	/*	Cmdeditbac edit = loadObject("pa.json");
		MobileSubmit paSubmit = new MobileSubmit(transactionManager, sqlSession);
		try {
			edit = paSubmit.save(edit);
			System.out.println("reg spaj:"+edit.getPemegang().getReg_spaj());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*Assert.assertTrue(edit.getPemegang().getReg_spaj()!=null);*/
	}
	
	public Cmdeditbac loadObject(String jsonFile){
		Cmdeditbac edit = new Cmdeditbac();
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			edit = mapper.readValue(new File("data/"+jsonFile), Cmdeditbac.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		System.out.println("data is fine");
		System.out.println("firstname:"+edit.getPemegang().getMcl_first());
		
		
		
		return edit;
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
