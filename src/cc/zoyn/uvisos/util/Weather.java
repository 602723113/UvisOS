package cc.zoyn.uvisos.util;

public class Weather {

    private String date;
    private String weatherType;
    private String temp;
    private String humidity;
    private String wind;
    private String windForce;

	public Weather(String date, String weatherType, String temp, String humidity, String wind, String windForce) {
		super();
		this.date = date;
		this.weatherType = weatherType;
		this.temp = temp;
		this.humidity = humidity;
		this.wind = wind;
		this.windForce = windForce;
	}

	public String getDate() {
        return date;
    }

    public String getWeatherType() {
        return weatherType;
    }

	public String getTemp() {
		return temp;
	}

	public String getHumidity() {
		return humidity;
	}

	public String getWind() {
		return wind;
	}

	public String getWindForce() {
		return windForce;
	}
	
	
    
    
}
