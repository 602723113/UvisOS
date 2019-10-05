package cc.zoyn.uvisos.util;

public class Weather {

    private String date;
    private String city;
    private String weatherType;
    private String high;
    private String low;

    public Weather(String date, String city, String weatherType, String high, String low) {
        this.date = date;
        this.city = city;
        this.weatherType = weatherType;
        this.high = high;
        this.low = low;
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }
}
