package com.hellokoding.auth.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


	
	@Entity
	@Table(name = "Weather")
	public class Weather {
	    private Long id;
	    private String place;
	    private String weather;
	    private String temperature;
	    private String mintemp;
	    private String maxtemp;
	    private String humidity;
	    
	   
	    public Weather(String place, String weather, String temperature, String mintemp, String maxtemp,
				String humidity) {
			super();
			this.place = place;
			this.weather = weather;
			this.temperature = temperature;
			this.mintemp = mintemp;
			this.maxtemp = maxtemp;
			this.humidity = humidity;
		}

		public Weather() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }
	    public String getPlace() {
			return place;
		}

		public void setPlace(String place) {
			this.place = place;
		}

		public String getWeather() {
			return weather;
		}

		public void setWeather(String weather) {
			this.weather = weather;
		}

		public String getTemperature() {
			return temperature;
		}

		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}

		public String getMintemp() {
			return mintemp;
		}

		public void setMintemp(String mintemp) {
			this.mintemp = mintemp;
		}

		public String getMaxtemp() {
			return maxtemp;
		}

		public void setMaxtemp(String maxtemp) {
			this.maxtemp = maxtemp;
		}

		public String getHumidity() {
			return humidity;
		}

		public void setHumidity(String humidity) {
			this.humidity = humidity;
		}

		
}
