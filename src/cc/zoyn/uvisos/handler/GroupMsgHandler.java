package cc.zoyn.uvisos.handler;

import cc.zoyn.uvisos.UvisOS;
import cc.zoyn.uvisos.util.*;
import cc.zoyn.uvisos.util.eval.MathEvalUtils;
import cc.zoyn.uvisos.util.eval.ScriptContainsEnglishException;
import cc.zoyn.uvisos.util.eval.ScriptContainsErrorKeywordExcpetion;
import com.sobte.cqp.jcq.entity.CQImage;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;

import static com.sobte.cqp.jcq.event.JcqApp.CC;

public class GroupMsgHandler extends Handler {

    public static void handle(int subType, int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg,
                              int font) {
        if (msg.startsWith("/qrcode")) {
            String content = processCommand(msg, "/qrcode");
            if (content.equalsIgnoreCase("") || content.isEmpty()) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "请输入你要转换的内容!");
                return;
            }
            File data = new File(UvisOS.getInstance().getDataFolder(), "qrcode");
            if (!data.exists()) {
                data.mkdirs();
            }
            File file = QRCodeUtils.createCodeToFile(content, data, null);
            if (file == null) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "出现了不可知的错误!");
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
            CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + cqCode);
            return;
        }
        if (msg.startsWith("/deqrcode")) {
            // 此方法为简便方法，获取第一个CQ:image里的图片数据，错误时打印异常到控制台，返回 null
            CQImage image = CC.getCQImage(msg);
            if (image == null) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "请加上图片, 懂?");
                return;
            }
            String content = null;
            try {
                content = QRCodeUtils.parseQRCodeByBytes(image.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "解码内容:" + content);
            return;
        }
        if (msg.startsWith("/eval")) {
            String content = processCommand(msg, "/eval").trim();
            if (content.equalsIgnoreCase("") || content.isEmpty()) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "请输入你要计算的内容!");
                return;
            }

            try {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + MathEvalUtils.eval(content));
            } catch (ScriptException e) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "输入的数学表达式有误!");
            } catch (ScriptContainsErrorKeywordExcpetion e) {
                CQ.setGroupBan(fromGroup, fromQQ, 10 * 60);
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "请不要输入限制级关键字!");
            } catch (Exception e) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "出现了预期之外的错误! 请检查你所输入的内容!");
            }
        }
        if (msg.startsWith("/ping")) {
            String content = processCommand(msg, "/ping").trim();
            if (content.equalsIgnoreCase("") || content.isEmpty()) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "请输入 IP:端口 格式的内容!");
                return;
            }
            // 现在应该是 ip:端口
            if (content.contains(":")) {
                String[] temp = content.split(":");
                long time = PingUtils.ping(temp[0], Integer.parseInt(temp[1]), 200);
                if (time == -1) {
                    CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "连接超时!");
                    return;
                }
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "连接延迟: " + time + "ms");
            } else {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "请附加端口! 默认端口可填写 80端口");
            }
            return;
        }
        if (msg.startsWith("/weather")) {
            String content = processCommand(msg, "/weather").trim();
            if (content.equalsIgnoreCase("") || content.isEmpty()) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "请输入城市名!");
                return;
            }

            Weather weather = WeatherUtils.request(content);
            if (weather == null) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "未找到相关数据!");
                return;
            }
            CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + weather.getWeatherType() + " " + weather.getLow() + "°C" + "-" + weather.getHigh() + "°C");
            return;
        }
        if (msg.startsWith("/getcover")) {
            String content = processCommand(msg, "/getcover").trim();
            if (content.equalsIgnoreCase("") || content.isEmpty()) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "请输入城市名!");
                return;
            }

            String result = BilibiliUtils.getBilibiliVideoCover(content);
            if (result == null) {
                CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + "未找到相关数据!");
                return;
            }
            CQ.sendGroupMsg(fromGroup, CC.at(fromQQ) + result);
        }
    }

}
