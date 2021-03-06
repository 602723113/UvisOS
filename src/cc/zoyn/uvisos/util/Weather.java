package cc.zoyn.uvisos.util;

public class Weather {

    private String date;
    private String weatherType;
    private String temp;
    private String humidity;
    private String stp;
    private String wind;
    private String windForce;
    
	public Weather(String date, String weatherType, String temp, String humidity, String stp, String wind,
			String windForce) {
		super();
		this.date = date;
		this.weatherType = weatherType;
		this.temp = temp;
		this.humidity = humidity;
		this.stp = stp;
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
	
	public String getStp() {
		return stp;
	}

	public String getWind() {
		return wind;
	}

	public String getWindForce() {
		return windForce;
	}
	
	
    
    
}
