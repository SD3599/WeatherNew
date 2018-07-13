package com.hellokoding.auth.test;



import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
   
 
      @Test
      public void invalidLoginDenied() throws Exception {
        String loginErrorUrl = "/login?error";
        mockMvc
                .perform(formLogin().password("invalid"))
                .andExpect(redirectedUrl(loginErrorUrl))
                .andExpect(unauthenticated());

     
    } 
    
      @Test
    	public void testLoginSuccess() throws Exception {
    	  mockMvc
      .perform(formLogin().user("swapnika").password("swapnika"))
      .andExpect(authenticated());
      
      }


}