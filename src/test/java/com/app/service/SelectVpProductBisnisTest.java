package com.app.service;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.services.SetupProductService;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
public class SelectVpProductBisnisTest {
	@Autowired
	private SetupProductService setupProductService;
	@Test
	public void testSetupProductService(){
		
		List<HashMap<String,Object>> list = setupProductService.selectVpProductBisnis(190, 4, 6, "01");
		HashMap<String,Object> m = list.get(0);
		for(String k:m.keySet()){
			System.out.println(k+"="+m.get(k));
		}
		Assert.assertTrue(list.size()>0);
		
	}
}
