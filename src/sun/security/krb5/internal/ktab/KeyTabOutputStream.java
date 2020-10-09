/*    */ package sun.security.krb5.internal.ktab;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import sun.security.krb5.internal.util.KrbDataOutputStream;
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
/*    */ public class KeyTabOutputStream
/*    */   extends KrbDataOutputStream
/*    */   implements KeyTabConstants
/*    */ {
/*    */   private KeyTabEntry entry;
/*    */   private int keyType;
/*    */   private byte[] keyValue;
/*    */   public int version;
/*    */   
/*    */   public KeyTabOutputStream(OutputStream paramOutputStream) {
/* 54 */     super(paramOutputStream);
/*    */   }
/*    */   
/*    */   public void writeVersion(int paramInt) throws IOException {
/* 58 */     this.version = paramInt;
/* 59 */     write16(paramInt);
/*    */   }
/*    */   
/*    */   public void writeEntry(KeyTabEntry paramKeyTabEntry) throws IOException {
/* 63 */     write32(paramKeyTabEntry.entryLength());
/* 64 */     String[] arrayOfString = paramKeyTabEntry.service.getNameStrings();
/* 65 */     int i = arrayOfString.length;
/* 66 */     if (this.version == 1281) {
/* 67 */       write16(i + 1);
/*    */     } else {
/* 69 */       write16(i);
/*    */     } 
/* 71 */     byte[] arrayOfByte = null;
/*    */     try {
/* 73 */       arrayOfByte = paramKeyTabEntry.service.getRealmString().getBytes("8859_1");
/* 74 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*    */ 
/*    */     
/* 77 */     write16(arrayOfByte.length);
/* 78 */     write(arrayOfByte);
/* 79 */     for (byte b = 0; b < i; b++) {
/*    */       try {
/* 81 */         write16((arrayOfString[b].getBytes("8859_1")).length);
/* 82 */         write(arrayOfString[b].getBytes("8859_1"));
/* 83 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*    */     } 
/*    */     
/* 86 */     write32(paramKeyTabEntry.service.getNameType());
/*    */     
/* 88 */     write32((int)(paramKeyTabEntry.timestamp.getTime() / 1000L));
/*    */ 
/*    */     
/* 91 */     write8(paramKeyTabEntry.keyVersion % 256);
/* 92 */     write16(paramKeyTabEntry.keyType);
/* 93 */     write16(paramKeyTabEntry.keyblock.length);
/* 94 */     write(paramKeyTabEntry.keyblock);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\ktab\KeyTabOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */