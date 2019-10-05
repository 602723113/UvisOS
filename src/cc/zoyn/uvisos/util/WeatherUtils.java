package cc.zoyn.uvisos.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class WeatherUtils {

    public synchronized static Weather request(String city) {
        String result = null;
        String url = "https://www.apiopen.top/weatherApi";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("city", city);

        try {
            result = NetUtils.net(url, params, "GET");
            // 处理json
            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(result);
            JsonObject object = root.getAsJsonObject();
            if (object.get("code").getAsInt() == 200) {
                JsonObject todayWeather = object.get("data").getAsJsonObject().get("forecast").getAsJsonArray().get(0).getAsJsonObject();
                String date = todayWeather.get("date").getAsString();
                String high = todayWeather.get("high").getAsString().replaceAll("[^0-9]", "");
                String low = todayWeather.get("low").getAsString().replaceAll("[^0-9]", "");
                String type = todayWeather.get("type").getAsString();
//                CQ.sendPrivateMsg(fromQQ, type + " " + low + "°C" + "-" + high + "°C");
                return new Weather(date, city, type, high, low);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

}
