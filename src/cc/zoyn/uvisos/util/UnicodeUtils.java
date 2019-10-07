package cc.zoyn.uvisos.util;

public class UnicodeUtils {

    /**
     * 字符串转unicode
     *
     * @param str 给定的字符串
     * @return 对应的unicode码
     */
    public static String stringToUnicode(String str) {
        StringBuilder sb = new StringBuilder();
        char[] c = str.toCharArray();
        for (char aC : c) {
            sb.append("\\u").append(Integer.toHexString(aC));
        }
        return sb.toString();
    }

    /**
     * unicode转字符串
     *
     * @param unicode 给定的unicode码
     * @return 对应的字符串
     */
    public static String unicodeToString(String unicode) {
        StringBuilder sb = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }
}
