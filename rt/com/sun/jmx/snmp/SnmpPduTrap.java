package com.sun.jmx.snmp;

public class SnmpPduTrap extends SnmpPduPacket {
  private static final long serialVersionUID = -3670886636491433011L;
  
  public SnmpOid enterprise;
  
  public SnmpIpAddress agentAddr;
  
  public int genericTrap;
  
  public int specificTrap;
  
  public long timeStamp;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpPduTrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */