package cc.zoyn.uvisos.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class BilibiliUtils {

    public synchronized static String getBilibiliVideoCover(String avNumber) {
        String result = null;
        String url = "http://www.galmoe.com/t.php";
        Map params = new HashMap();
        params.put("aid", avNumber);

        try {
            result = NetUtils.net(url, params, "GET");
            // 处理json
            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(result);
            JsonObject object = root.getAsJsonObject();
            if (object.get("result").getAsInt() == 1) {
                return object.get("url").getAsString();
            } else {
                return null;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getBilibiliVideoCover("av170001"));
    }

}
