package com.moroz.phonebookservice;

import com.moroz.phonebookservice.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class PhonebookserviceApplicationTests {

	@Autowired
	private UserController userController;

	@Test
	void contextLoads() {
        Assert.notNull(userController, "userController should not be null");
	}

}
