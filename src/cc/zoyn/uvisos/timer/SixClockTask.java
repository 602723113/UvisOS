package cc.zoyn.uvisos.timer;

import static cc.zoyn.uvisos.handler.Handler.CQ;

import cc.zoyn.uvisos.util.Weather;
import cc.zoyn.uvisos.util.WeatherUtils;

import static cc.zoyn.uvisos.util.CityCode.广西南宁;

import java.io.IOException;

public class SixClockTask implements Task {

	@Override
	public void run() {
		Weather weather = WeatherUtils.request(String.valueOf(广西南宁.getCode()));
		if (weather != null) {
			CQ.sendPrivateMsg(602723113L,
					"早上好! Zoyn!\n" + "今天是: " + weather.getDate() + "\n" + "今日天气: \n实时天气:" + weather.getWeatherType()
							+ " " + weather.getTemp() + "°C\n" + "风向风力: " + weather.getWind() + " "
							+ weather.getWindForce() + "\n" + "空气湿度: " + weather.getHumidity() + "\n" + "实时气压: "
							+ weather.getStp() + "Pa");

			CQ.sendGroupMsg(766342327L,
					"早上好!\n" + "今天是: " + weather.getDate() + "\n" + "今日天气: \n实时天气:" + weather.getWeatherType() + " "
							+ weather.getTemp() + "°C\n" + "风向风力: " + weather.getWind() + " " + weather.getWindForce()
							+ "\n" + "空气湿度: " + weather.getHumidity() + "\n" + "实时气压: " + weather.getStp() + "Pa");
			
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("python ");
			} catch (IOException e) {
			}
		}
	}
}
