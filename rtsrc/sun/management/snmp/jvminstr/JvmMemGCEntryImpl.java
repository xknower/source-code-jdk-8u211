/*    */ package sun.management.snmp.jvminstr;
/*    */ 
/*    */ import com.sun.jmx.snmp.SnmpStatusException;
/*    */ import java.lang.management.GarbageCollectorMXBean;
/*    */ import sun.management.snmp.jvmmib.JvmMemGCEntryMBean;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JvmMemGCEntryImpl
/*    */   implements JvmMemGCEntryMBean
/*    */ {
/*    */   protected final int JvmMemManagerIndex;
/*    */   protected final GarbageCollectorMXBean gcm;
/*    */   
/*    */   public JvmMemGCEntryImpl(GarbageCollectorMXBean paramGarbageCollectorMXBean, int paramInt) {
/* 64 */     this.gcm = paramGarbageCollectorMXBean;
/* 65 */     this.JvmMemManagerIndex = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Long getJvmMemGCTimeMs() throws SnmpStatusException {
/* 73 */     return new Long(this.gcm.getCollectionTime());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Long getJvmMemGCCount() throws SnmpStatusException {
/* 81 */     return new Long(this.gcm.getCollectionCount());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer getJvmMemManagerIndex() throws SnmpStatusException {
/* 88 */     return new Integer(this.JvmMemManagerIndex);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\JvmMemGCEntryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */