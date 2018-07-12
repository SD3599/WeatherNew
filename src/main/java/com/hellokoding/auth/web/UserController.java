package com.hellokoding.auth.web;

import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Condition;
import com.github.fedy2.weather.data.Forecast;
import com.hellokoding.auth.model.User;
import com.hellokoding.auth.model.Weather;

import com.hellokoding.auth.service.SecurityService;
import com.hellokoding.auth.service.UserService;
import com.hellokoding.auth.service.WeatherService;
import com.hellokoding.auth.validator.UserValidator;

import com.hellokoding.auth.service.IWeatherService;

import java.awt.List;

/*import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.model.DailyWeatherForecast;*/

import java.security.Principal;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);	
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;
@Autowired
private WeatherService weatherService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private IWeatherService<Channel> yahooService;
    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
    	logger.debug("Registration called");
        model.addAttribute("userForm", new User());

        return "registration";
    }
    /*
    @RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
    public String forgotpassword(Model model ,Principal p) {
      String s=p.getName();
    User u=userService.findByUsername(s);//String username);
    	model.addAttribute("question",u.getSecurityquestion());
        model.addAttribute("answer","");
        return "login";
    }
    @RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
    public String checkforgotpassword(@RequestParam("secanswer")String secanswer, Model model,Principal p) {
    	String s=p.getName();
    	User u=userService.findByUsername(s);//String username);
    	String a=u.getSecurityanswer();
    //	System.out.println("Answer "+answer+" SecANswer "+secanswer);
    	//String aaa=("secanswer");
    	if(a.equals(secanswer))
    	{
    	securityService.autologin(u.getUsername(), u.getPassword());

        return "redirect:/welcome";
        }
    	else //to add a line highlighting wrong password
    		return "redirect:/login";
    }
*/
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
    	logger.debug("Registration with post called with userform");
    	userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        logger.debug("autologin called");
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
    	logger.debug("Login (get )");
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model,Principal p) throws Exception {
    	logger.debug("user logged in successfully");
    	String s=p.getName();
    	User u=userService.findByUsername(s);//String username);
    	model.addAttribute("defaultloc",u.getLocation());
    	 Channel yahooResponse = yahooService.getForecast(u.getLocation(),0);
         //    model.addAttribute("cityKey", loc);
         //    model.addAttribute("currentWeather", yahooResponse.getItem().getCondition());
          //   model.addAttribute("forecasts", yahooResponse.getItem().getForecasts());
     System.out.println("............"+yahooResponse.getItem().getCondition()+"..........."+ yahooResponse.getItem().getForecasts()+"...................");
     System.out.println("............"+yahooResponse.getItem().getCondition().getCode()+"..........."+ yahooResponse.getItem().getForecasts()+"...................");
       //hyderabad............Condition [text=Cloudy, code=26, temp=23, date=Wed Jul 11 00:30:00 IST 2018]...........[Forecast [day=WED, date=Wed Jul 11 00:00:00 IST 2018, low=22, high=28, text=Scattered Thunderstorms, code=47], Forecast [day=THU, date=Thu Jul 12 00:00:00 IST 2018, low=22, high=27, text=Breezy, code=23], Forecast [day=FRI, date=Fri Jul 13 00:00:00 IST 2018, low=22, high=28, text=Scattered Thunderstorms, code=47], Forecast [day=SAT, date=Sat Jul 14 00:00:00 IST 2018, low=22, high=27, text=Scattered Thunderstorms, code=47], Forecast [day=SUN, date=Sun Jul 15 00:00:00 IST 2018, low=22, high=28, text=Scattered Thunderstorms, code=47], Forecast [day=MON, date=Mon Jul 16 00:00:00 IST 2018, low=22, high=25, text=Scattered Thunderstorms, code=47], Forecast [day=TUE, date=Tue Jul 17 00:00:00 IST 2018, low=22, high=27, text=Scattered Thunderstorms, code=47], Forecast [day=WED, date=Wed Jul 18 00:00:00 IST 2018, low=22, high=28, text=Scattered Thunderstorms, code=47], Forecast [day=THU, date=Thu Jul 19 00:00:00 IST 2018, low=22, high=28, text=Mostly Cloudy, code=28], Forecast [day=FRI, date=Fri Jul 20 00:00:00 IST 2018, low=22, high=28, text=Cloudy, code=26]]...................
     Condition wc=yahooResponse.getItem().getCondition();
     
    // Forecast[] f=null;
     //List f=(List) yahooResponse.getItem().getForecasts();
     Object[] o=yahooResponse.getItem().getForecasts().toArray();
        	//Weather w= weatherService.findByPlace(loc);
        	//if(wc==null)

        		
        	//{wc=new Weather(loc,"sunny","28","24","29","89%");}
        	model.addAttribute("temperature",wc.getTemp());
       // 	model.addAttribute("place",u.getLocation());
        	model.addAttribute("mintemp",((Forecast) o[0]).getLow());
        	model.addAttribute("maxtemp",((Forecast) o[0]).getHigh());
        return "welcome";
    }
    

    @RequestMapping(value = "/getforecast/place", method = RequestMethod.GET)
    public String getforecast(@RequestParam("place")String loc,@RequestParam("days")String days,@RequestParam("type")String degree,Model model) throws Exception  {
    	logger.debug("Getting weather forecast for "+loc);
    	 Channel yahooResponse = yahooService.getForecast(loc,Integer.parseInt(degree));
     //    model.addAttribute("cityKey", loc);
     //    model.addAttribute("currentWeather", yahooResponse.getItem().getCondition());
      //   model.addAttribute("forecasts", yahooResponse.getItem().getForecasts());
 System.out.println(loc+"............"+yahooResponse.getItem().getCondition()+"..........."+ yahooResponse.getItem().getForecasts()+"...................");
 System.out.println(loc+"............"+yahooResponse.getItem().getCondition().getCode()+"..........."+ yahooResponse.getItem().getForecasts()+"...................");
   //hyderabad............Condition [text=Cloudy, code=26, temp=23, date=Wed Jul 11 00:30:00 IST 2018]...........[Forecast [day=WED, date=Wed Jul 11 00:00:00 IST 2018, low=22, high=28, text=Scattered Thunderstorms, code=47], Forecast [day=THU, date=Thu Jul 12 00:00:00 IST 2018, low=22, high=27, text=Breezy, code=23], Forecast [day=FRI, date=Fri Jul 13 00:00:00 IST 2018, low=22, high=28, text=Scattered Thunderstorms, code=47], Forecast [day=SAT, date=Sat Jul 14 00:00:00 IST 2018, low=22, high=27, text=Scattered Thunderstorms, code=47], Forecast [day=SUN, date=Sun Jul 15 00:00:00 IST 2018, low=22, high=28, text=Scattered Thunderstorms, code=47], Forecast [day=MON, date=Mon Jul 16 00:00:00 IST 2018, low=22, high=25, text=Scattered Thunderstorms, code=47], Forecast [day=TUE, date=Tue Jul 17 00:00:00 IST 2018, low=22, high=27, text=Scattered Thunderstorms, code=47], Forecast [day=WED, date=Wed Jul 18 00:00:00 IST 2018, low=22, high=28, text=Scattered Thunderstorms, code=47], Forecast [day=THU, date=Thu Jul 19 00:00:00 IST 2018, low=22, high=28, text=Mostly Cloudy, code=28], Forecast [day=FRI, date=Fri Jul 20 00:00:00 IST 2018, low=22, high=28, text=Cloudy, code=26]]...................
 Condition wc=yahooResponse.getItem().getCondition();
 
// Forecast[] f=null;
 //List f=(List) yahooResponse.getItem().getForecasts();
 Object[] o=yahooResponse.getItem().getForecasts().toArray();
    	//Weather w= weatherService.findByPlace(loc);
    	//if(wc==null)

    		
    	//{wc=new Weather(loc,"sunny","28","24","29","89%");}
    	model.addAttribute("temperature",wc.getTemp());
    	model.addAttribute("place",loc);
    	model.addAttribute("mintemp",((Forecast) o[0]).getLow());
    	model.addAttribute("maxtemp",((Forecast) o[0]).getHigh());
    	//model.addAttribute("humidity",w.getHumidity());
    	model.addAttribute("weather",wc.getText());
    	//if(day)days=2;
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
        /*INSERT INTO `weather` (`id` ,`humidity`,`maxtemp`,`mintemp`,`place`,`temperature`,`weather`)
VALUES
(1,'45%','32','23','hyderabad','25','sunny');*/
    }
     
}
