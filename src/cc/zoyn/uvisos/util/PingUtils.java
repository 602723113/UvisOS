package cc.zoyn.uvisos.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PingUtils {

    private static Map<String, Short> times = new HashMap<>();

    public synchronized static boolean ping(String ip, int port, int timeout) {
        Socket server = new Socket();
        InetSocketAddress address = new InetSocketAddress(ip, port);
        try {
            server.connect(address, timeout);
            server.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(ping("www.zoyn.top", 443, 200));
        System.out.println(ping("zoyn.cc", 80, 200));
        System.out.println(ping("www.mcbbs.net", 80, 200));
    }

}
