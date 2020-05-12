/*    */ package sun.security.util;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ public class ByteArrayTagOrder
/*    */   implements Comparator<byte[]>
/*    */ {
/*    */   public final int compare(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/* 57 */     return (paramArrayOfbyte1[0] | 0x20) - (paramArrayOfbyte2[0] | 0x20);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\securit\\util\ByteArrayTagOrder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */