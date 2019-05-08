package com.app.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.BsimBootApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BsimBootApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class MemberServiceTest {
	

}
