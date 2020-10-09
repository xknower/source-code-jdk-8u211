/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
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
/*     */ public class OCSPNoCheckExtension
/*     */   extends Extension
/*     */   implements CertAttrSet<String>
/*     */ {
/*     */   public static final String IDENT = "x509.info.extensions.OCSPNoCheck";
/*     */   public static final String NAME = "OCSPNoCheck";
/*     */   
/*     */   public OCSPNoCheckExtension() throws IOException {
/*  72 */     this.extensionId = PKIXExtensions.OCSPNoCheck_Id;
/*  73 */     this.critical = false;
/*  74 */     this.extensionValue = new byte[0];
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
/*     */   public OCSPNoCheckExtension(Boolean paramBoolean, Object paramObject) throws IOException {
/*  87 */     this.extensionId = PKIXExtensions.OCSPNoCheck_Id;
/*  88 */     this.critical = paramBoolean.booleanValue();
/*     */ 
/*     */     
/*  91 */     this.extensionValue = new byte[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String paramString, Object paramObject) throws IOException {
/*  98 */     throw new IOException("No attribute is allowed by CertAttrSet:OCSPNoCheckExtension.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(String paramString) throws IOException {
/* 106 */     throw new IOException("No attribute is allowed by CertAttrSet:OCSPNoCheckExtension.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(String paramString) throws IOException {
/* 114 */     throw new IOException("No attribute is allowed by CertAttrSet:OCSPNoCheckExtension.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<String> getElements() {
/* 123 */     return (new AttributeNameEnumeration()).elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 130 */     return "OCSPNoCheck";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\x509\OCSPNoCheckExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */