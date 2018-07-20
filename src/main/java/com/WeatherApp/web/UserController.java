package com.WeatherApp.web;

import com.WeatherApp.model.User;
import com.WeatherApp.model.Weather;
import com.WeatherApp.service.IWeatherService;
import com.WeatherApp.service.SecurityService;
import com.WeatherApp.service.UserService;
import com.WeatherApp.validator.UserValidator;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Condition;
import com.github.fedy2.weather.data.Forecast;

import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class to handle all the endpoints
 * @author Swapnika
 *
 */
@Controller
public class UserController {
	
	private static final Logger Log = LoggerFactory.getLogger(UserController.class);	
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    @Autowired
    private IWeatherService<Channel> yahooService;
    
    /**
     * This method maps the request for registration
     * @param model is the user entity that takes the details of the user
     * @return a jsp page for registration with a model attribute to take user details
     */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
    	Log.debug("Registration called");
        model.addAttribute("userForm", new User());
        return "registration";
    }
    /**This method maps the registration request after user has submitted all the 
     * details for registration and saves them in the repository in case there are 
     * no errors else redirects to registration page.
     * 
     * @param userForm is the model sent with the registration jsp page to collect user details
     * @param bindingResult has the errors during registration if exists
     * @param model is the placeholder for model object
     * @return redirects to welcome page after successful registration
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
    	Log.debug("Registration with post called with userform");
    	userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        Log.debug("autologin called");
        return "redirect:/welcome";
    }
/**The method handles login request
 * 
 * @param model holds the model object
 * @param error holds the errors if any after validation 
 * @param logout to check if login page is called after logout is clicked
 * @return the jsp view for login
 */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
    	Log.debug("Login (get )");
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
/**This method handles the request to display welcome page where the user can request 
 * for weather forecast
 * 
 * @param model holds the model object
 * @param p principal object to store the details of currently authenticated user
 * @returns the welcome page
 * @throws Exception
 */
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model,Principal p) throws Exception {
    	Log.debug("user logged in successfully");
    	String s=p.getName();
    	User u=userService.findByUsername(s);
    	model.addAttribute("defaultloc",u.getLocation());
	    Channel yahooResponse = yahooService.getForecast(u.getLocation(),0);
	    Log.info(yahooResponse.getItem().getCondition()+" "+ yahooResponse.getItem().getForecasts());
	    Log.info(yahooResponse.getItem().getCondition().getCode()+" "+ yahooResponse.getItem().getForecasts());
	    Condition wc=yahooResponse.getItem().getCondition();
	    Object[] o=yahooResponse.getItem().getForecasts().toArray();
	    Weather w=new Weather(u.getLocation(),"C",wc.getText(),wc.getTemp(),((Forecast) o[0]).getHigh(),((Forecast) o[0]).getLow());
	    model.addAttribute("Weather",w);
	 /*   model.addAttribute("temperature",wc.getTemp());
        model.addAttribute("mintemp",((Forecast) o[0]).getLow());
        model.addAttribute("maxtemp",((Forecast) o[0]).getHigh());
       */ return "welcome";
    }
    
/**
 * This method handles the request for weather forecast for the selected place
 * and makes a request to an external API sending the required parameters
 * 
 * @param loc is the string that holds the value for the location
 * @param days is the days for which weather forecast is required
 * @param degree is the unit for degree whether celsius or fahrenheit 
 * @param model holds the model object
 * @return the view where the weather forecast data is displayed
 * @throws Exception
 */
    @RequestMapping(value = "/getforecast/place", method = RequestMethod.GET)
    public String getforecast(@RequestParam("place")String loc,@RequestParam("days")String days,@RequestParam("type")String degree,Model model) throws Exception  {
    	Log.debug("Getting weather forecast for {}",loc);
    	int deg=Integer.parseInt(degree);
    	 model.addAttribute("place",loc);
    	 Channel yahooResponse = yahooService.getForecast(loc,deg);
      	 Log.info(loc+" "+yahooResponse.getItem().getCondition()+" "+ yahooResponse.getItem().getForecasts());
    	 Log.info(loc+" "+yahooResponse.getItem().getCondition().getCode()+" "+ yahooResponse.getItem().getForecasts());
         Condition wc=yahooResponse.getItem().getCondition();
         Object[] o=yahooResponse.getItem().getForecasts().toArray();
    	 String c=" C";
         if(deg==1)
	           c=" F";
         Weather w=new Weather(loc,c,wc.getText(),wc.getTemp(),((Forecast) o[0]).getHigh(),((Forecast) o[0]).getLow());
        System.out.println(w.toString());
        model.addAttribute("Weather",w); 
		int day=Integer.parseInt(days);
    	if(day>0)
    	{
    	for(int i=0;i<day;i++)
    	{
    		model.addAttribute("day"+i,(Forecast)o[i]);
    	}
    	model.addAttribute("days",days);
    	}
        return "welcome";
    }

   
}
