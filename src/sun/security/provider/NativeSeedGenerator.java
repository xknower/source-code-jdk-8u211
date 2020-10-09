/*    */ package sun.security.provider;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ class NativeSeedGenerator
/*    */   extends SeedGenerator
/*    */ {
/*    */   NativeSeedGenerator(String paramString) throws IOException {
/* 46 */     if (!nativeGenerateSeed(new byte[2])) {
/* 47 */       throw new IOException("Required native CryptoAPI features not  available on this machine");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static native boolean nativeGenerateSeed(byte[] paramArrayOfbyte);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void getSeedBytes(byte[] paramArrayOfbyte) {
/* 60 */     if (!nativeGenerateSeed(paramArrayOfbyte))
/*    */     {
/* 62 */       throw new InternalError("Unexpected CryptoAPI failure generating seed");
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\provider\NativeSeedGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */