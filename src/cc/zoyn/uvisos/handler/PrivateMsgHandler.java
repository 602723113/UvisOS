package cc.zoyn.uvisos.handler;

import cc.zoyn.uvisos.UvisOS;
import cc.zoyn.uvisos.util.PingUtils;
import cc.zoyn.uvisos.util.QRCodeUtils;
import cc.zoyn.uvisos.util.WeatherUtils;
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
                CQ.sendPrivateMsg(fromQQ, "请输入城市名!");
                return;
            }

            WeatherUtils.request(content, fromQQ);
            return;
        }

        CQ.sendPrivateMsg(fromQQ, "[UvisOS] 抱歉, 我貌似没听懂你想说什么, 请输入/help来获取帮助!");
    }

}