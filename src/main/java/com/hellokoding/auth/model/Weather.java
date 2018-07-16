package com.hellokoding.auth.model;



import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



	

	public class Weather {
	    private Long id;
	    private String place;
	    private String weather;
	    private int temperature;
	    private int mintemp;
	    private int maxtemp;
	    private String unit;
	    
	    public Weather( String weather, String unit, int mintemp, int maxtemp) {
			super();
			
			this.weather = weather;
			this.mintemp = mintemp;
			this.maxtemp = maxtemp;
			this.unit = unit;
			
		}
	   
	    public Weather(String place,  String unit,String weather, int temperature, int mintemp, int maxtemp) {
			super();
			this.place = place;
			this.weather = weather;
			this.temperature = temperature;
			this.mintemp = mintemp;
			this.maxtemp = maxtemp;
			this.unit = unit;
			
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

		public int getTemperature() {
			return temperature;
		}

		public void setTemperature(int temperature) {
			this.temperature = temperature;
		}

		public int getMintemp() {
			return mintemp;
		}

		public void setMintemp(int mintemp) {
			this.mintemp = mintemp;
		}

		public int getMaxtemp() {
			return maxtemp;
		}

		public void setMaxtemp(int maxtemp) {
			this.maxtemp = maxtemp;
		}

		public String getUnit() {
			return unit;
		}

		public void setUnit(String unit) {
			this.unit = unit;
		}

		@Override
		public String toString() {
			return "Weather [id=" + id + ", place=" + place + ", weather=" + weather + ", temperature=" + temperature
					+ ", mintemp=" + mintemp + ", maxtemp=" + maxtemp + ", unit=" + unit + "]";
		}

	

		
}
	
