package com.sun.jmx.snmp;

import java.io.Serializable;

public abstract class SnmpPduPacket extends SnmpPdu implements Serializable {
  public byte[] community;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpPduPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */