package com.WeatherApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * This class serves as the starting point for the application.
 * 
 * @author Swapnika
 *
 */
@SpringBootApplication
public class WebApplication extends SpringBootServletInitializer {
	private static final Logger Log = LoggerFactory.getLogger(WebApplication.class);
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }
	/**
	 * Main Method  
	 * @param args A string array containing the command line arguments if any.
	 * 
	 */
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
        Log.debug("--Application Started--");
    }
}


