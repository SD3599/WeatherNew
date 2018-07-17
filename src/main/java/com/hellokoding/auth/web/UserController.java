package com.hellokoding.auth.web;

import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Condition;
import com.github.fedy2.weather.data.Forecast;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.model.Weather;
import com.hellokoding.auth.service.SecurityService;
import com.hellokoding.auth.service.UserService;
import com.hellokoding.auth.validator.UserValidator;
import com.hellokoding.auth.service.IWeatherService;
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
    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
    	Log.debug("Registration called");
        model.addAttribute("userForm", new User());
        return "registration";
    }
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
    	Log.debug("Login (get )");
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

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
