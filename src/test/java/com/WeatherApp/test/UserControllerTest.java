package com.WeatherApp.test;


import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.WeatherApp.model.User;
import com.WeatherApp.service.IWeatherService;
import com.WeatherApp.service.UserService;
import com.WeatherApp.service.UserServiceImpl;
import com.github.fedy2.weather.data.Channel;

import javax.servlet.Filter;


@WithMockUser
@Transactional
public class UserControllerTest extends AbstractControllerTest {
	
    @Autowired
    private UserService userService;
    
    @Spy
    @InjectMocks
    private UserServiceImpl service;

    @Autowired
    private IWeatherService<Channel> yahooService;

    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mockMvc;
    
    @Autowired
    private Filter springSecurityFilterChain;
    
    
    @Mock
    private Principal principal;
    @Mock
    private User u;

    @Before
    public void setUp() {
      mockMvc = MockMvcBuilders
              .webAppContextSetup(context)
              .addFilters(springSecurityFilterChain)
              .defaultRequest(get("/").with(testSecurityContext()))
              .build();
      MockitoAnnotations.initMocks(this);
      //this.authentication = new UsernamePasswordAuthenticationToken("swapnika", "swapnika");
      //  this.p= authentication.getPrincipal();
    }
    @Test
    public void testLoginPageLoading() throws Exception {
      mockMvc.perform(MockMvcRequestBuilders.get("/login"))
        		            .andExpect(status().isOk())
        		            .andExpect(view().name("login"));
        
    }
    
    @Test
	public void testRegistrationPageLoading() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders
    			          .get("/registration"))
		                  .andExpect(status().isOk())
		                  .andExpect(view().name("registration"));
	}
  /*  Struck near binding result
    @Test
	public void testRegistrationPage() throws Exception {
    	User u=new User();
    	mockMvc.perform(MockMvcRequestBuilders
    			          .post("/registration")
    			          .flashAttr("userForm", u))
		                  .andExpect(status().isOk())
		                  .andExpect(view().name("registration"));
	}
	*/
    
    
    @Test
    public void testUserLoginFailure() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders
	    		            .post("/login")
	    				    .param("username", "swapn")
	    		            .param("password", "swapnika"))
	            .andExpect(redirectedUrl("/login?error"));
	            
     }
    
    
    @Test
  	public void testUserLoginSuccess() throws Exception {
    	User u=new User("testuser");
    	u.setPassword("testpassword");
    	userService.save(u);
    	mockMvc.perform(MockMvcRequestBuilders
    			             .post("/login")
  	                         .param("username", "testuser")
  	                         .param("password", "testpassword"))
    	//      .andDo(print())
    	        .andExpect(redirectedUrl("/"));	
  	    		 
      }
 //   will work only if user with username swapnika is in table
      @Test
  	  public void testJPAUserLoginSuccess() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.post("/login")
  	            .param("username", "swapnika")
  	            .param("password", "swapnika"))
  		        .andExpect(redirectedUrl("/"));	
  	    		 
      }
  /*    @Test
      @WithMockUser(username = "akinpaws",password="swapnika" )
  
  	  public void cestJPAUserLoginSuccess() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.post("/login")
  	            .param("username", "akinpaws")
  	            .param("password", "swapnika"))
    	.andDo(print())
  		        .andExpect(redirectedUrl("/"));	
  	    		 
      }
    */  @Test
      public void invalidLoginDenied() throws Exception {
        String loginErrorUrl = "/login?error";
        mockMvc
                .perform(formLogin().password("invalid"))
                .andExpect(redirectedUrl(loginErrorUrl))
                .andExpect(unauthenticated());

     
    } 
    
      @Test
    	public void testLoginSuccess() throws Exception 
      {
    	  mockMvc
      .perform(formLogin().user("swapnika").password("swapnika"))
      .andExpect(authenticated());
      
      }
      
     @Test //(expected=org.springframework.web.util.NestedServletException.class)
     @WithMockUser(username = "swapnika",password="swapnika" )
  	 public void testgetwelcomeSuccess() throws Exception {
    	  u=userService.findByUsername("swapnika");
    	  when(principal.getName()).thenReturn("swapnika");
    	 // when(service.findByUsername("swapnika")).thenReturn(u);
    	      	 
    	  String s=u.getLocation();
    	  String name=principal.getName();
    	  Assert.assertEquals("doesnt match","swapnika",name);
    	  Assert.assertNotNull("failure- expected entitiy", s);
    	  Assert.assertEquals("doesnt match","Hyderabad",s);
  mockMvc.perform(get("/"))
   .andExpect(status().isOk())
   .andExpect(model().attribute("defaultloc", "Hyderabad"))
   .andDo(print())
   .andExpect(view().name("welcome"));
  	  
  	String hid="727232";
      Channel ch = yahooService.getForecast(hid, 0);
      Assert.assertNotNull("failure- expected entitiy", ch);
  
    }  
      @Test
      @WithMockUser
   	public void testwelcomeSuccess() throws Exception {
   	  mockMvc.perform(get("/getforecast/place")
   			 .param("place", "Hyderabad")
             .param("days", "3")   
             .param("type", "0"))
            .andExpect(status().isOk())
         // .andDo(print())
            .andExpect(view().name("welcome"));
   	   String hid="727232";
       Channel ch = yahooService.getForecast(hid, 0);
       Assert.assertNotNull("failure- expected entitiy", ch);
     }  
      @Test
  	public void Logout() throws Exception {
    	  mockMvc
             .perform(logout());
      }
    
     
    
    
    
   

}