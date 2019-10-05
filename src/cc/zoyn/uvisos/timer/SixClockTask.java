package cc.zoyn.uvisos.timer;

import cc.zoyn.uvisos.util.Weather;
import cc.zoyn.uvisos.util.WeatherUtils;


import static cc.zoyn.uvisos.handler.Handler.CQ;

public class SixClockTask implements Task {

    @Override
    public void run() {
        Weather weather = WeatherUtils.request("南宁");
        if (weather != null) {
            CQ.sendPrivateMsg(602723113L, "早上好! Zoyn!\n" +
                    "今天是" + weather.getDate() + "\n" +
                    "今日天气: " + weather.getWeatherType() + " " + weather.getLow() + "°C" + "-" + weather.getHigh() + "°C");
        }
    }
}
