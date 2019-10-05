package cc.zoyn.uvisos.handler;

import com.sobte.cqp.jcq.entity.CoolQ;
import com.sobte.cqp.jcq.event.JcqApp;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Handler {

    public static CoolQ CQ = JcqApp.CQ;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH");
    private static final String HELP_MSG = " %s\n" +
            "/help 查询帮助\n" +
            "/eval <表达式> 使用给定的数学表达式进行运算\n" +
            "/qrcode <内容> 使用给定的内容对进行编码成二维码\n" +
            "/deqrcode <二维码> 使用给定的二维码进行解码\n" +
            "/weather <城市名> 使用给定的城市名获取天气\n" +
            "/ping <域名/ip>:<端口>\n" +
            "/getcover <视频av号> 根据给定的B站视频号获取封面\n"+
            "UvisOS Powered By Zoyn";

    private static String getWelcomeMsg() {
        String hourString = FORMAT.format(new Date());
        short hour = Short.parseShort(hourString);
        String msg;
        // 0-6 凌晨
        // 6-8 早上
        // 7-11 上午
        // 11-13 中午
        // 13-17 下午
        // 17-19 傍晚
        // 19-24 晚上
        if (hour < 6) {
            msg = "凌晨好!";
        } else if (hour < 8) {
            msg = "早上好!";
        } else if (hour < 11) {
            msg = "上午好!";
        } else if (hour < 14) {
            msg = "中午好!";
        } else if (hour < 17) {
            msg = "下午好!";
        } else if (hour < 19) {
            msg = "傍晚好!";
        } else if (hour < 24) {
            msg = "晚上好!";
        } else {
            msg = "你好!";
        }
        return msg;
    }

    public static void sendHelp(long groupId, long qqId, ActionType type) {
        if (type.equals(ActionType.SEND_PRIVATE_MSG)) {
            CQ.sendPrivateMsg(qqId, getWelcomeMsg() + String.format(HELP_MSG, CQ.getStrangerInfo(qqId).getNick()));
        } else {
            CQ.sendGroupMsg(groupId, getWelcomeMsg() + String.format(HELP_MSG, CQ.getGroupMemberInfo(groupId, qqId).getNick()));
        }
    }

    /**
     * 处理来自信息的指令
     *
     * @param msg         信息
     * @param commandName 指令名
     * @return 指令后的的参数
     */
    static String processCommand(String msg, String commandName) {
        return msg.replace(commandName, "");
    }

}
