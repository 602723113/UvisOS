package cc.zoyn.uvisos.timer;

import cc.zoyn.uvisos.util.Weather;
import cc.zoyn.uvisos.util.WeatherUtils;

import java.util.Calendar;

import static cc.zoyn.uvisos.handler.Handler.CQ;

public class SixClockTask implements Task {

    @Override
    public void run() {
        Weather weather = WeatherUtils.request("南宁");
        if (weather != null) {
//            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

            CQ.sendPrivateMsg(602723113L, "早上好! Zoyn!\n" +
                    "今天是: " + weather.getDate() + "\n" +
                    "今日天气: " + weather.getWeatherType() + " " + weather.getTemp() + "°C");

////            CQ.sendGroupMsg(766342327L, "早上好!\n" +
//                    "今天是: " + month + "月" + weather.getDate() + "\n" +
//                    "今日天气: " + weather.getWeatherType() + " " + weather.getLow() + "°C" + "-" + weather.getHigh() + "°C");
        }
    }
}
