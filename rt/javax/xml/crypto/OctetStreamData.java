/*     */ package javax.xml.crypto;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OctetStreamData
/*     */   implements Data
/*     */ {
/*     */   private InputStream octetStream;
/*     */   private String uri;
/*     */   private String mimeType;
/*     */   
/*     */   public OctetStreamData(InputStream paramInputStream) {
/*  51 */     if (paramInputStream == null) {
/*  52 */       throw new NullPointerException("octetStream is null");
/*     */     }
/*  54 */     this.octetStream = paramInputStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OctetStreamData(InputStream paramInputStream, String paramString1, String paramString2) {
/*  70 */     if (paramInputStream == null) {
/*  71 */       throw new NullPointerException("octetStream is null");
/*     */     }
/*  73 */     this.octetStream = paramInputStream;
/*  74 */     this.uri = paramString1;
/*  75 */     this.mimeType = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getOctetStream() {
/*  84 */     return this.octetStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI() {
/*  94 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/* 104 */     return this.mimeType;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\xml\crypto\OctetStreamData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */