package cc.zoyn.uvisos.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Utils {

    public static String encode(String text) {
        Base64.Encoder encoder = Base64.getEncoder();
        final byte[] textByte;
        try {
            textByte = text.getBytes("UTF-8");
            return encoder.encodeToString(textByte);
        } catch (UnsupportedEncodingException ignored) {
        }
        return null;
    }

    public static String decode(String text) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            return new String(decoder.decode(text), "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
        }
        return null;
    }


//    public static void main(String[] args) {
//        String text = encode("你好233");
//        System.out.println(text);
//
//        String decodedText = decode(text);
//        System.out.println(decodedText);
//    }


}
