package cc.zoyn.uvisos.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherUtils {

	public synchronized static Weather request(String cityId) {
		String result = null;
		String url = "http://api.help.bj.cn/apis/weather/";// 请求接口地址
		Map<String, String> params = new HashMap();// 请求参数
		params.put("id", cityId);

		try {
			result = NetUtils.net(url, params, "GET");
			// 处理json
			JsonParser parser = new JsonParser();
			JsonElement root = parser.parse(result);
			JsonObject object = root.getAsJsonObject();
			System.out.println(result);
			System.out.println(object.get("status").getAsInt());
			if (object.get("status").getAsInt() == 0) {
				String date = object.get("today").getAsString();
				String type = object.get("weather").getAsString();
				String temp = object.get("temp").getAsString();
				String humidity = object.get("humidity").getAsString();
				String wind = object.get("wd").getAsString();
				String windForce = object.get("wdforce").getAsString();
				return new Weather(date, type, temp, humidity, wind, windForce);
			}
		} catch (Exception ignored) {
		}
		return null;
	}

}
