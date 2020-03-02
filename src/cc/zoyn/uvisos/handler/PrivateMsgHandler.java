package cc.zoyn.uvisos.handler;

import cc.zoyn.uvisos.UvisOS;
import cc.zoyn.uvisos.keyword.KeywordManager;
import cc.zoyn.uvisos.timer.Timing;
import cc.zoyn.uvisos.util.*;
import cc.zoyn.uvisos.util.eval.MathEvalUtils;
import cc.zoyn.uvisos.util.eval.ScriptContainsErrorKeywordExcpetion;
import com.sobte.cqp.jcq.entity.CQImage;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;

import static com.sobte.cqp.jcq.event.JcqApp.CC;

/**
 * @author Zoyn
 * @since 2019/8/25
 */
public class PrivateMsgHandler extends Handler {

	public static void handle(int subType, int msgId, long fromQQ, String msg, int font) {
		if (msg.startsWith("/添加关键词 ")) {
			String content = processCommand(msg, "/添加关键词 ");
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入你要添加的内容!正确格式:/添加关键词 <关键词> <回复内容>");
				return;
			}
			String[] keyAndData = content.split(" ");
			if (keyAndData.length < 2) {
				CQ.sendPrivateMsg(fromQQ, "请输入你要添加的内容!正确格式:/添加关键词 <关键词> <回复内容>");
				return;
			}
			String key = keyAndData[0];
			StringBuilder data = new StringBuilder();
			for (int i = 1; i < keyAndData.length; i++) {
				data.append(keyAndData[i]);
			}
			KeywordManager.setKeyword(key, data.toString());
			CQ.sendPrivateMsg(fromQQ, "添加关键词: " + key + " 成功!");
			return;
		}
		if (msg.startsWith("/修改关键词 ")) {
			String content = processCommand(msg, "/修改关键词 ");
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入你要添加的内容!正确格式:/修改关键词 <关键词> <回复内容>");
				return;
			}
			String[] keyAndData = content.split(" ");
			if (keyAndData.length < 2) {
				CQ.sendPrivateMsg(fromQQ, "请输入你要添加的内容!正确格式:/修改关键词 <关键词> <回复内容>");
				return;
			}
			String key = keyAndData[0];
			StringBuilder data = new StringBuilder();
			for (int i = 1; i < keyAndData.length; i++) {
				data.append(keyAndData[i]);
			}
			KeywordManager.setKeyword(key, data.toString());
			CQ.sendPrivateMsg(fromQQ, "修改关键词: " + key + " 成功!");
			return;
		}
		if (msg.startsWith("/删除关键词 ")) {
			String content = processCommand(msg, "/删除关键词 ");
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入你要删除的关键词!正确格式:/删除关键词 <关键词>");
				return;
			}
			String[] keyAndData = content.split(" ");
			if (keyAndData.length < 1) {

			}
			String key = keyAndData[0];
			KeywordManager.removeKeyword(key);
			CQ.sendPrivateMsg(fromQQ, "移除关键词: " + key + " 成功!");
			return;
		}

		if (msg.startsWith("/查看关键词")) {
			if (fromQQ != 602723113L) {
				CQ.sendPrivateMsg(fromQQ, "你的权限不足!");
				return;
			}
			StringBuilder builder = new StringBuilder("关键词数据(共" + KeywordManager.getWords().size() + "个数据) >> \n");
			KeywordManager.getWords().forEach((key, word) -> {
				builder.append(key + ": " + word + "\n");
			});
			CQ.sendPrivateMsg(fromQQ, builder.toString());
			return;
		}
		if (msg.startsWith("/读取关键词")) {
			KeywordManager.load();
			CQ.sendPrivateMsg(fromQQ, "读取关键词成功!");
			return;
		}

		if (msg.startsWith("/qrcode")) {
			String content = processCommand(msg, "/qrcode");
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入你要转换的内容!");
				return;
			}
			File data = new File(UvisOS.getInstance().getDataFolder(), "qrcode");
			if (!data.exists()) {
				data.mkdirs();
			}
			File file = QRCodeUtils.createCodeToFile(content, data, null);
			if (file == null) {
				CQ.sendPrivateMsg(fromQQ, "出现了不可知的错误!");
				return;
			}
			// 将图片进行缓存, 并编码成CQCode
			String cqCode = null;
			try {
				cqCode = CC.image(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 发送二维码
			CQ.sendPrivateMsg(fromQQ, cqCode);
			return;
		}
		if (msg.startsWith("/deqrcode")) {
			// 此方法为简便方法，获取第一个CQ:image里的图片数据，错误时打印异常到控制台，返回 null
			CQImage image = CC.getCQImage(msg);
			if (image == null) {
				CQ.sendPrivateMsg(fromQQ, "请加上图片, 懂?");
				return;
			}
			String content = null;
			try {
				content = QRCodeUtils.parseQRCodeByBytes(image.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			CQ.sendPrivateMsg(fromQQ, "解码内容:" + content);
			return;
		}
		if (msg.startsWith("/eval")) {
			String content = processCommand(msg, "/eval").trim();
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入你要计算的内容!");
				return;
			}

			try {
				CQ.sendPrivateMsg(fromQQ, MathEvalUtils.eval(content));
			} catch (ScriptException e) {
				CQ.sendPrivateMsg(fromQQ, "输入的数学表达式有误!");
			} catch (ScriptContainsErrorKeywordExcpetion e) {
				CQ.sendPrivateMsg(fromQQ, "请不要输入限制级关键字!");
			} catch (Exception e) {
				CQ.sendPrivateMsg(fromQQ, "出现了预期之外的错误! 请检查你所输入的内容!");
			}
			return;
		}
		if (msg.startsWith("/ping")) {
			String content = processCommand(msg, "/ping").trim();
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入 IP:端口 格式的内容!");
				return;
			}
			// 现在应该是 ip:端口
			if (content.contains(":")) {
				String[] temp = content.split(":");
				long time = PingUtils.ping(temp[0], Integer.parseInt(temp[1]), 200);
				if (time == -1) {
					CQ.sendPrivateMsg(fromQQ, "连接超时!");
					return;
				}
				CQ.sendPrivateMsg(fromQQ, "连接延迟: " + time + "ms");
			} else {
				CQ.sendPrivateMsg(fromQQ, "请附加端口! 默认端口可填写 80端口");
			}
			return;
		}
		if (msg.startsWith("/weather")) {
			String content = processCommand(msg, "/weather").trim();
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入省+城市名!");
				return;
			}

			CityCode code;
			try {
				code = CityCode.valueOf(content);
			} catch (Exception e) {
				CQ.sendPrivateMsg(fromQQ, "未识别出该城市id, 请检查是否为 省+城市名 的格式!");
				return;
			}
			Weather weather = WeatherUtils.request(String.valueOf(code.getCode()));
			if (weather == null) {
				CQ.sendPrivateMsg(fromQQ, "未找到相关数据!");
				return;
			}
			CQ.sendPrivateMsg(fromQQ,
					"实时天气: " + weather.getWeatherType() + " " + weather.getTemp() + "°C\n" + "风向风力: "
							+ weather.getWind() + " " + weather.getWindForce() + "\n" + "空气湿度: " + weather.getHumidity()
							+ "\n" + "实时气压: " + weather.getStp() + "Pa");
			return;
		}
		if (msg.startsWith("/getcover")) {
			String content = processCommand(msg, "/getcover").trim();
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入av号!");
				return;
			}

			String result = BilibiliUtils.getBilibiliVideoCover(content);
			if (result == null) {
				CQ.sendPrivateMsg(fromQQ, "未找到相关数据!");
				return;
			}
			CQ.sendPrivateMsg(fromQQ, result);
			return;
		}

		if (msg.startsWith("/timing")) {
			if (fromQQ != 602723113L) {
				CQ.sendPrivateMsg(fromQQ, "你的权限不足!");
				return;
			}
			String content = processCommand(msg, "/timing").trim();
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入true或false!");
				return;
			}

			boolean result = Boolean.parseBoolean(content);
//            System.out.println(UvisOS.getInstance().getTiming());
			if (result) {
				// 检测是否在关闭状态
				if (!Timing.getInstance().isRun()) {
					Timing.getInstance().setRun(true);
					// 线程重启
					Timing.getInstance().startup();
				}
			} else {
				Timing.getInstance().setRun(false);
			}

			CQ.sendPrivateMsg(fromQQ, "每日任务线程: " + result);
			return;
		}

		if (msg.startsWith("/bencode")) {
			String content = processCommand(msg, "/bencode").trim();
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入文本!");
				return;
			}
			CQ.sendPrivateMsg(fromQQ, Base64Utils.encode(content));
			return;
		}

		if (msg.startsWith("/bdecode")) {
			String content = processCommand(msg, "/bdecode").trim();
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入文本!");
				return;
			}
			CQ.sendPrivateMsg(fromQQ, Base64Utils.decode(content));
			return;
		}

		if (msg.startsWith("/uencode")) {
			String content = processCommand(msg, "/uencode").trim();
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入文本!");
				return;
			}
			CQ.sendPrivateMsg(fromQQ, UnicodeUtils.stringToUnicode(content));
			return;
		}

		if (msg.startsWith("/udecode")) {
			String content = processCommand(msg, "/udecode").trim();
			if (content.equalsIgnoreCase("") || content.isEmpty()) {
				CQ.sendPrivateMsg(fromQQ, "请输入文本!");
				return;
			}
			CQ.sendPrivateMsg(fromQQ, UnicodeUtils.unicodeToString(content));
			return;
		}

		CQ.sendPrivateMsg(fromQQ, "[UvisOS] 抱歉, 我貌似没听懂你想说什么, 请输入/help来获取帮助!");
	}

}
