package com.WeatherApp.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.WeatherApp.model.User;
import com.WeatherApp.repository.UserRepository;
import com.WeatherApp.service.UserService;
import com.WeatherApp.service.YahooService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import com.WeatherApp.test.AbstractTest;



public class YahooServiceTest extends AbstractTest{
	
	@Autowired
	private YahooService yahooService;
	
	
	@Test
    public void testgetForecast() throws Exception {

       
        String hid="727232";
        Channel ch = yahooService.getForecast(hid, DegreeUnit.CELSIUS);
Assert.assertNotNull("failure- expected entitiy", ch);
//Assert.assertEquals("expected attribute string doesnot match","swapnika", entity.getUsername());
   
}

	

}
