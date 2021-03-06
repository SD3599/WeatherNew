package com.WeatherApp.service;
import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;



import org.springframework.stereotype.Service;


import javax.xml.bind.JAXBException;
import java.util.HashMap;

/**
 * Implements Iweather Service and extends yahooweather service 
 * @author Swapnika
 *
 */
@Service
public class YahooService extends YahooWeatherService implements IWeatherService<Channel> {

    private static final HashMap<String, String> woeIds;
    static {

        woeIds = new HashMap<>();
        woeIds.put("Vienna", "551801");
        woeIds.put("Newyork", "2459115");
        woeIds.put("Sydney", "1105779");
        woeIds.put("Hyderabad", "2295414");
        woeIds.put("Amsterdam", "727232");
        woeIds.put("Los Angeles", "2356920");
        woeIds.put("Kolkata", "55999983");
        woeIds.put("Delhi", "28743736");
        woeIds.put("Mumbai", "2295411");
        woeIds.put("Chennai", "90883322");
        woeIds.put("Pune", "2295412");

    }

    public YahooService() throws JAXBException {
        super();
    }
    /**
     * Method to handle getforecast request from controller
     * @param city a string which represents the city
     * @param integer for degree 
     * @return getforecast method which takes woeid(Where on Earth Id) which are unique ids 
     * provided by yahoo weather API and a degree unit which is either celsius or fahrenheit as input
     */

    @Override
    public Channel getForecast(String city,int c) throws Exception {
    	String id=woeIds.get(city);
    	if(id==null)
    		id="23424848";
    	if(c==0)
    	  	
    	        return getForecast(id, DegreeUnit.CELSIUS);
    	
    	else
    		return getForecast(id, DegreeUnit.FAHRENHEIT);
        
    }
}
