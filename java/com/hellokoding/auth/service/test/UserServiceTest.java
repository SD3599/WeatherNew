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
import com.WeatherApp.test.AbstractTest;

import junit.framework.Assert;
@Transactional
public class UserServiceTest extends AbstractTest {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepo;
	@SuppressWarnings("deprecation")
	@Test
    public void testGetByUsername() throws Exception {

        String username="swapnika";
        User entity = userService.findByUsername(username);
Assert.assertNotNull("failure- expected entitiy", entity);
Assert.assertEquals("expected attribute string doesnot match","swapnika", entity.getUsername());
   
}
	@Test
    public void saveUser() throws Exception {
		User user=new User("testuser");
		
        userRepo.save(user);
		
Assert.assertNotNull("failure- expected entitiy", userService.findByUsername("testuser"));
Assert.assertEquals("expected attribute string doesnot match","testuser", user.getUsername());
   
}
	

}
