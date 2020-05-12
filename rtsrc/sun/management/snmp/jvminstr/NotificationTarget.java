package sun.management.snmp.jvminstr;

import java.net.InetAddress;

public interface NotificationTarget {
  InetAddress getAddress();
  
  int getPort();
  
  String getCommunity();
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\NotificationTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */