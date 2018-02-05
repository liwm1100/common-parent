package com.my.common.util.ip;

import java.net.*;
import java.util.Enumeration;

/**
 * 获得服务所在电脑的ip地址
 * @author
 * @version 1.0
 * @project common
 * @date 2016/6/30
 */
public class LocalAddress {

    private static final String skipIP = "127.0.0.1";
    private static final String defaultIP = "unknow";

    /**
     * 返回ipv4
     *
     * @return 返回ip地址格式为“192.168.10.10” ，如果当前电脑有多个ip地址则返回“192.168.10.10/192.168.10.11”<br/>
     *          如果无法获得ip地址或仅能获得“127.0.0.1”，则返回“unknown”
     */
    public static String getIPV4() {
        try {
            String i = getWindowsIPV4();
            if (null != i )
                return i;
        } catch (UnknownHostException e) {
        }
        try {
            String i = getLinuxIPV4();
            if (null != i)
                return i;
        } catch (SocketException e) {
        }
        return defaultIP;
    }

    /**
     * 查询windows环境下的ip地址
     *
     * @return ip ，如果无法获得ip地址或仅能获得“127.0.0.1”，则返回 null
     * @throws UnknownHostException
     */
    private static String getWindowsIPV4() throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        if(skipIP.equals(hostAddress)){
            return null;
        }else{
            return hostAddress;
        }
    }

    /**
     * 查询linux环境下的ip地址
     *
     * @return ip ，如果无法获得ip地址或仅能获得“127.0.0.1”，则返回 null
     * @throws SocketException
     */
    private static String getLinuxIPV4() throws SocketException {
        String ips = null;
        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = (InetAddress) addresses.nextElement();
                if (address != null && address instanceof Inet4Address) {
                    String ipstr = address.getHostAddress();
                    if(skipIP.equals(ipstr)){
                        continue;
                    }else{
                        if(null == ips){
                            ips = ipstr;
                        }else{
                            ips = ips.concat("/").concat(ipstr) ;
                        }
                    }
                }
            }
        }
        return ips;
    }
}
