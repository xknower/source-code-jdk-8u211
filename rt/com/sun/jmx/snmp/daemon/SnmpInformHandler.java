package com.sun.jmx.snmp.daemon;

import com.sun.jmx.snmp.SnmpDefinitions;
import com.sun.jmx.snmp.SnmpVarBindList;

public interface SnmpInformHandler extends SnmpDefinitions {
  void processSnmpPollData(SnmpInformRequest paramSnmpInformRequest, int paramInt1, int paramInt2, SnmpVarBindList paramSnmpVarBindList);
  
  void processSnmpPollTimeout(SnmpInformRequest paramSnmpInformRequest);
  
  void processSnmpInternalError(SnmpInformRequest paramSnmpInformRequest, String paramString);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\daemon\SnmpInformHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */