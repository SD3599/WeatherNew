package com.WeatherApp.service.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.WeatherApp.model.User;
import com.WeatherApp.repository.UserRepository;
import com.WeatherApp.service.UserService;
import com.WeatherApp.service.UserServiceImpl;
import com.WeatherApp.test.AbstractTest;
import org.junit.Assert;

@Transactional
public class UserServiceTest extends AbstractTest {
	
	@Autowired
	private UserServiceImpl userService;
	
	

	String username="testuser";
	@Test
    public void testGetByUsername() throws Exception {
		User u=new User(username);
		u.setPassword("testpassword");
		userService.save(u);
        
        User entity = userService.findByUsername(username);
		Assert.assertNotNull("failure- expected entitiy", entity);
		Assert.assertEquals("expected attribute string doesnot match",username, entity.getUsername());
		   
}
	

	@Test
    public void saveUser() throws Exception {
		User user=new User(username);
		user.setPassword("testpassword");
		userService.save(user);
		
		Assert.assertNotNull("failure- expected entitiy", userService.findByUsername(username));
		Assert.assertEquals("expected attribute string doesnot match",username, user.getUsername());
   
}
	/*//Testing username from db, will pass only if username is in table
	@Test
    public void testByUsername() throws Exception {

        String username="swapnika";
        User entity = userService.findByUsername(username);
        Assert.assertNotNull("failure- expected entitiy", entity);
        Assert.assertEquals("expected attribute string doesnot match",username, entity.getUsername());
 
}*/
	
}
