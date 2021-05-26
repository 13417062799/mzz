package com.distlab.hqsms.common.net;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class IPUtil {

  /**
   * 获取本机所有IP
   * @return  IP列表
   */
  public static List<String> getLocalIPS() {
    String ip;
    ArrayList<String> ips = new ArrayList<>();
    try {
      InetAddress localHost = InetAddress.getLocalHost();
      String hostName = localHost.getHostName();
      InetAddress[] ipsAddr = InetAddress.getAllByName(hostName);
      for (InetAddress inetAddress : ipsAddr) {
        ip = inetAddress.getHostAddress();
        ips.add(ip);
      }
    } catch (Exception e) {
      return null;
    }

    return ips;
  }
}
