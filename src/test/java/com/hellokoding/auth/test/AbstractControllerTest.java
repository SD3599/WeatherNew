package com.hellokoding.auth.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hellokoding.auth.web.UserController;

@WebAppConfiguration
public abstract class AbstractControllerTest extends AbstractTest {
	
	protected MockMvc mvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
}
    protected void setUp(UserController controller) {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    
}
