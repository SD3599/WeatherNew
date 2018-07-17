package com.hellokoding.auth.test;



import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.service.IWeatherService;
import com.hellokoding.auth.service.UserDetailsServiceImpl;
import com.hellokoding.auth.service.UserService;
import com.hellokoding.auth.service.UserServiceImpl;

import javax.servlet.Filter;
import javax.servlet.*;

@WithMockUser
@Transactional
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc
public class UserControllerTest extends AbstractControllerTest {
	
    @Autowired
    private UserService userService;
    
    @Spy
    @InjectMocks
    
    //@Autowired
    private UserServiceImpl service;
  //  @Autowired
  // private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private IWeatherService<Channel> yahooService;

    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mockMvc;
    
    @Autowired
    private Filter springSecurityFilterChain;
    
    private Authentication authentication;

	
    private Object p;
    
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
    public void testUserLoginFailure() throws Exception {
	    RequestBuilder requestBuilder = post("/login")
	            .param("username", "swapn")
	            .param("password", "swapnika");
	    		 mockMvc.perform(requestBuilder)
	            .andExpect(redirectedUrl("/login?error"))
	            ;
	            
}
    
    @Test
  	public void testUserLoginSuccess() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.post("/login")
  	            .param("username", "swapnika")
  	            .param("password", "swapnika"))
    	   .andDo(print())
  		        .andExpect(redirectedUrl("/"));	
  	    		 
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
    	public void testLoginSuccess() throws Exception 
      {
    	  mockMvc
      .perform(formLogin().user("swapnika").password("swapnika"))
      .andExpect(authenticated());
      
      }
      
      @Test//(expected=org.springframework.web.util.NestedServletException.class)
     @WithMockUser(username = "swapnika",password="swapnika" )
  	public void testgetwelcomeSuccess() throws Exception {
    	 // User u=userService.findByUsername("swapnika");
    	  u=userService.findByUsername("swapnika");
    	  when(principal.getName()).thenReturn("swapnika");
    	 // when(service.findByUsername("swapnika")).thenReturn(u);
    	  
    	 
    	  String s=u.getLocation();
    	  String name=principal.getName();
    	  Assert.assertEquals("doesnt match","swapnika",name);
    	  Assert.assertNotNull("failure- expected entitiy", s);
    	  Assert.assertEquals("doesnt match","Hyderabad",s);
  //	  String obj = "hyderabad";
	mockMvc.perform(get("/"))
  //.perform(get("/").with(user("swapnika").password("swapnika")))
  	// .perform(get("/").with(SecurityMockMvcRequestPostProcessors.authentication(authentication)))
  	// .perform(get("/").with(httpBasic("user","password")))
    //.perform(formLogin().user("swapnika").password("swapnika"))
  	  // .perform(formLogin().user("swapnika").password("swapnika"))
   .andExpect(status().isOk())
   .andExpect(model().attribute("defaultloc", "Hyderabad"))
   .andDo(print())
   .andExpect(view().name("welcome"));
  	  
 /* 	String hid="727232";
      Channel ch = yahooService.getForecast(hid, 0);
      Assert.assertNotNull("failure- expected entitiy", ch);*/
  	// Channel yahooResponse = yahooService.getForecast("Hyderabad",0);
    }  
      @Test
      @WithMockUser//(username = "user",password="swapnik" )
   	public void testwelcomeSuccess() throws Exception {
   	  mockMvc.perform(get("/getforecast/place")
   			
            .param("place", "Hyderabad")
            .param("days", "3")   
            .param("type", "0")
    )
   //.perform(get("/").with(user("swapnika").password("swapnika")))
   	// .perform(get("/").with(SecurityMockMvcRequestPostProcessors.authentication(authentication)))
   	// .perform(get("/").with(httpBasic("user","password")))
     //.perform(formLogin().user("swapnika").password("swapnika"))
   	  // .perform(formLogin().user("swapnika").password("swapnika"))
     .andExpect(status().isOk())
     .andDo(print())
     //.andExpect(forwardedurl("/welcome"));
     .andExpect(view().name("welcome")
    //.andExpect(content().toString())		 
    		 );
   	String hid="727232";
       Channel ch = yahooService.getForecast(hid, 0);
       Assert.assertNotNull("failure- expected entitiy", ch);
  //     Assert.assertNotNull("test",((Principal) p).getName());
   //    Assert.assertEquals("testing",((Principal) p).getName(),"swapnika");
   	// Channel yahooResponse = yahooService.getForecast("Hyderabad",0);
     }  
      @Test
  	public void Logout() throws Exception {
    	  mockMvc
      
      .perform(logout());
      }
    
      /*
      @Test
      public void test() throws Exception
      {
    	   User user = new User("swapnika");
           TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user,null);
           SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    
           mockMvc.perform(get("/"))
                   //.principal(testingAuthenticationToken))
                   .andExpect(status().isOk())
                   .andDo(print());
      }
      */
      
      
      /*
      @Test
     	public void testWelcomePageLoading() throws Exception 
     	{
         	mockMvc.perform(MockMvcRequestBuilders.get("/welcome"))//.principal(authentication.getPrincipal()))
     		                  .andExpect(status().isOk())
     		               .andDo(print())
     		               
     		                  .andExpect(view().name("welcome"));
     	}
     
      
      @Test
      public void requestProtectedUrlWithAdmin() throws Exception {
  mockMvc
     .perform(get("/").with(user("swapnika").password("swapnika")))
              // Ensure we got past Security
              .andExpect(status().isOk())
     // .perform(get("/").accept(mediaTypes))//formLogin().user("swapnika").password("swapnika"))
              // Ensure it appears we are authenticated with admin
    //          .andExpect(authenticated().withUsername("swapnika"))
                  
              .andDo(print())
              .andExpect(view().name("welcome"));
  }
    
      
      @Test
      public void requestProtectedUrlWithUserDetails() throws Exception {
          UserDetails userDetails = userDetailsService.loadUserByUsername("swapnika");
          mockMvc
              .perform(get("/welcome").with(user(userDetails)))
              // Ensure we got past Security
              .andExpect(status().isNotFound())
              // Ensure it appears we are authenticated with user
              .andExpect(authenticated().withAuthenticationPrincipal(userDetails));
  }
      


 
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