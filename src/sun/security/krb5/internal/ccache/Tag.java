/*    */ package sun.security.krb5.internal.ccache;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
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
/*    */ public class Tag
/*    */ {
/*    */   int length;
/*    */   int tag;
/*    */   int tagLen;
/*    */   Integer time_offset;
/*    */   Integer usec_offset;
/*    */   
/*    */   public Tag(int paramInt1, int paramInt2, Integer paramInteger1, Integer paramInteger2) {
/* 49 */     this.tag = paramInt2;
/* 50 */     this.tagLen = 8;
/* 51 */     this.time_offset = paramInteger1;
/* 52 */     this.usec_offset = paramInteger2;
/* 53 */     this.length = 4 + this.tagLen;
/*    */   }
/*    */   public Tag(int paramInt) {
/* 56 */     this.tag = paramInt;
/* 57 */     this.tagLen = 0;
/* 58 */     this.length = 4 + this.tagLen;
/*    */   }
/*    */   public byte[] toByteArray() {
/* 61 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 62 */     byteArrayOutputStream.write(this.length);
/* 63 */     byteArrayOutputStream.write(this.tag);
/* 64 */     byteArrayOutputStream.write(this.tagLen);
/* 65 */     if (this.time_offset != null) {
/* 66 */       byteArrayOutputStream.write(this.time_offset.intValue());
/*    */     }
/* 68 */     if (this.usec_offset != null) {
/* 69 */       byteArrayOutputStream.write(this.usec_offset.intValue());
/*    */     }
/* 71 */     return byteArrayOutputStream.toByteArray();
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\ccache\Tag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */