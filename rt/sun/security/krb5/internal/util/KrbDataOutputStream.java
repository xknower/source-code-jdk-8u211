/*    */ package sun.security.krb5.internal.util;
/*    */ 
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ public class KrbDataOutputStream
/*    */   extends BufferedOutputStream
/*    */ {
/*    */   public KrbDataOutputStream(OutputStream paramOutputStream) {
/* 46 */     super(paramOutputStream);
/*    */   }
/*    */   public void write32(int paramInt) throws IOException {
/* 49 */     byte[] arrayOfByte = new byte[4];
/* 50 */     arrayOfByte[0] = (byte)((paramInt & 0xFF000000) >> 24 & 0xFF);
/* 51 */     arrayOfByte[1] = (byte)((paramInt & 0xFF0000) >> 16 & 0xFF);
/* 52 */     arrayOfByte[2] = (byte)((paramInt & 0xFF00) >> 8 & 0xFF);
/* 53 */     arrayOfByte[3] = (byte)(paramInt & 0xFF);
/* 54 */     write(arrayOfByte, 0, 4);
/*    */   }
/*    */   
/*    */   public void write16(int paramInt) throws IOException {
/* 58 */     byte[] arrayOfByte = new byte[2];
/* 59 */     arrayOfByte[0] = (byte)((paramInt & 0xFF00) >> 8 & 0xFF);
/* 60 */     arrayOfByte[1] = (byte)(paramInt & 0xFF);
/* 61 */     write(arrayOfByte, 0, 2);
/*    */   }
/*    */   
/*    */   public void write8(int paramInt) throws IOException {
/* 65 */     write(paramInt & 0xFF);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\interna\\util\KrbDataOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */