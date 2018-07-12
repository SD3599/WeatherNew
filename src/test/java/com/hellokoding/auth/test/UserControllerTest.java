package com.hellokoding.auth.test;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.hellokoding.auth.service.UserService;
import javax.servlet.Filter;
import javax.servlet.*;


@Transactional
public class UserControllerTest extends AbstractControllerTest {
	
    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private Filter springSecurityFilterChain;
    @Before
    public void setUp() {
      mockMvc = MockMvcBuilders
              .webAppContextSetup(context)
              .addFilters(springSecurityFilterChain)
              .build();
    }

    @Test
    public void testLogin() throws Exception {

        String uri = "/login";
     
        
        
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
        		            .andExpect(status().isOk())
        		            .andExpect(view().name("login"));
        


    }
    @Test
	public void testRegistrationPageLoading() throws Exception 
	{
    	mockMvc.perform(MockMvcRequestBuilders.get("/registration"))
		                  .andExpect(status().isOk())
		                 
		                  .andExpect(view().name("registration"));
	}
	
/*
    @Test
    public void adminCanLog() throws Exception {
      mockMvc
              .perform(formLogin().user(AppSecurityAdmin.ADMIN_USERNAME).password(AppSecurityAdmin.ADMIN_PASSWORD))
              .andExpect(status().isFound())
              .andExpect(redirectedUrl("/"))
              .andExpect(authenticated().withUsername(AppSecurityAdmin.ADMIN_USERNAME));

      mockMvc
              .perform(logout())
              .andExpect(status().isFound())
              .andExpect(redirectedUrl("/"));
    }


    @Test
    public void invalidLoginDenied() throws Exception {
      String loginErrorUrl = "/login?error";
      mockMvc
              .perform(formLogin().password("invalid"))
              .andExpect(status().isFound())
              .andExpect(redirectedUrl(loginErrorUrl))
              .andExpect(unauthenticated());

      mockMvc
              .perform(get(loginErrorUrl))
              .andExpect(content().string(containsString("Invalid username and password")));
  } 
    
    
    
    /*
	@Test
	public void testUserLoginSuccess() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("http://localhost:8090/login")
	            .param("username", "swapnika")
	            .param("password", "swapnika"))
		        .andDo(print())
	            .andExpect(redirectedUrl("/welcome"));	
	    		 

	            
	            
	}
	/*@Test
	public void testUserLoginFailure() throws Exception {
	    RequestBuilder requestBuilder = post("http://localhost:8080/login")
	            .param("username", "vishnu@gmail.com")
	            .param("password", "vishnu");
	    		 mockMvc.perform(requestBuilder)
	            .andDo(print())
	            .andExpect(redirectedUrl("/login?error"))
	            ;
	            
}
    /*

    @Test
    public void testGetGreeting() throws Exception {

        String uri = "/api/greetings/{id}";
        Long id = new Long(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

    }

    @Test
    public void testGetGreetingNotFound() throws Exception {

        String uri = "/api/greetings/{id}";
        Long id = Long.MAX_VALUE;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

    }

    @Test
    public void testCreateGreeting() throws Exception {

        String uri = "/api/greetings";
        Greeting greeting = new Greeting();
        greeting.setText("test");
        String inputJson = super.mapToJson(greeting);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Greeting createdGreeting = super.mapFromJson(content, Greeting.class);

        Assert.assertNotNull("failure - expected greeting not null",
                createdGreeting);
        Assert.assertNotNull("failure - expected greeting.id not null",
                createdGreeting.getId());
        Assert.assertEquals("failure - expected greeting.text match", "test",
                createdGreeting.getText());

    }

    @Test
    public void testUpdateGreeting() throws Exception {

        String uri = "/api/greetings/{id}";
        Long id = new Long(1);
        Greeting greeting = greetingService.findOne(id);
        String updatedText = greeting.getText() + " test";
        greeting.setText(updatedText);
        String inputJson = super.mapToJson(greeting);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.put(uri, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        Greeting updatedGreeting = super.mapFromJson(content, Greeting.class);

        Assert.assertNotNull("failure - expected greeting not null",
                updatedGreeting);
        Assert.assertEquals("failure - expected greeting.id unchanged",
                greeting.getId(), updatedGreeting.getId());
        Assert.assertEquals("failure - expected updated greeting text match",
                updatedText, updatedGreeting.getText());

    }

    @Test
    public void testDeleteGreeting() throws Exception {

        String uri = "/api/greetings/{id}";
        Long id = new Long(1);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

        Greeting deletedGreeting = greetingService.findOne(id);

        Assert.assertNull("failure - expected greeting to be null",
                deletedGreeting);

    }
    */

}