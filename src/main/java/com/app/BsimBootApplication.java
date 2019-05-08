package com.app;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.app.services.BsimService;

@SpringBootApplication
@Configuration
@ImportResource("classpath:boot.xml")
public class BsimBootApplication  implements CommandLineRunner {

	@Autowired
	private BsimService service;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BsimBootApplication.class, args);
	}

	public void run(String... args) throws Exception {
		HashMap<String,Object> map = service.selectMstKartuPas("75000");
		System.out.println(map.get("NO_KARTU"));
		Date date= service.selectSysdate();
		System.out.println("date="+date);
	}

}
