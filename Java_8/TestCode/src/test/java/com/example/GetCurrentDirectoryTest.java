package com.example;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author WangYunwei [2024-06-24]
 */
public class GetCurrentDirectoryTest {

    public static void main(String[] args) throws UnknownHostException, SocketException {
        String currentDir = System.getProperty("user.dir");
//        System.out.println("Current directory: " + currentDir);

        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        System.out.println(interfaces);
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            // 如果不是环回接口
            if (!networkInterface.isLoopback()) {
                List<InetAddress> collect =
                        Collections.list(networkInterface.getInetAddresses()).stream().filter(item -> !item.isLoopbackAddress() && item instanceof Inet4Address ).collect(Collectors.toList());
                System.out.println(collect);
            }
        }
    }
}
