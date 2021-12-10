package com.example.serverv2.networks;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class serverNetwork {

    public static String getIp(){
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("error");
        }
        assert localHost != null;
        return localHost.getHostAddress();

    }
}
