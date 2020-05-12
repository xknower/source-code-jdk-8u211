package com.sun.jmx.snmp;

public interface SnmpUsmKeyHandler {
  public static final int DES_KEY_SIZE = 16;
  
  public static final int DES_DELTA_SIZE = 16;
  
  byte[] password_to_key(String paramString1, String paramString2) throws IllegalArgumentException;
  
  byte[] localizeAuthKey(String paramString, byte[] paramArrayOfbyte, SnmpEngineId paramSnmpEngineId) throws IllegalArgumentException;
  
  byte[] localizePrivKey(String paramString, byte[] paramArrayOfbyte, SnmpEngineId paramSnmpEngineId, int paramInt) throws IllegalArgumentException;
  
  byte[] calculateAuthDelta(String paramString, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3) throws IllegalArgumentException;
  
  byte[] calculatePrivDelta(String paramString, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, byte[] paramArrayOfbyte3, int paramInt) throws IllegalArgumentException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\SnmpUsmKeyHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */