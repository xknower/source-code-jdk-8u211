/*     */ package sun.security.x509;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
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
/*     */ public class GeneralName
/*     */ {
/*  55 */   private GeneralNameInterface name = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralName(GeneralNameInterface paramGeneralNameInterface) {
/*  64 */     if (paramGeneralNameInterface == null) {
/*  65 */       throw new NullPointerException("GeneralName must not be null");
/*     */     }
/*  67 */     this.name = paramGeneralNameInterface;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralName(DerValue paramDerValue) throws IOException {
/*  76 */     this(paramDerValue, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralName(DerValue paramDerValue, boolean paramBoolean) throws IOException {
/*  87 */     short s = (short)(byte)(paramDerValue.tag & 0x1F);
/*     */ 
/*     */ 
/*     */     
/*  91 */     switch (s) {
/*     */       case 0:
/*  93 */         if (paramDerValue.isContextSpecific() && paramDerValue.isConstructed()) {
/*  94 */           paramDerValue.resetTag((byte)48);
/*  95 */           this.name = new OtherName(paramDerValue);
/*     */         } else {
/*  97 */           throw new IOException("Invalid encoding of Other-Name");
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 1:
/* 102 */         if (paramDerValue.isContextSpecific() && !paramDerValue.isConstructed()) {
/* 103 */           paramDerValue.resetTag((byte)22);
/* 104 */           this.name = new RFC822Name(paramDerValue);
/*     */         } else {
/* 106 */           throw new IOException("Invalid encoding of RFC822 name");
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 2:
/* 111 */         if (paramDerValue.isContextSpecific() && !paramDerValue.isConstructed()) {
/* 112 */           paramDerValue.resetTag((byte)22);
/* 113 */           this.name = new DNSName(paramDerValue);
/*     */         } else {
/* 115 */           throw new IOException("Invalid encoding of DNS name");
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 6:
/* 120 */         if (paramDerValue.isContextSpecific() && !paramDerValue.isConstructed()) {
/* 121 */           paramDerValue.resetTag((byte)22);
/* 122 */           this.name = paramBoolean ? URIName.nameConstraint(paramDerValue) : new URIName(paramDerValue);
/*     */         } else {
/*     */           
/* 125 */           throw new IOException("Invalid encoding of URI");
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 7:
/* 130 */         if (paramDerValue.isContextSpecific() && !paramDerValue.isConstructed()) {
/* 131 */           paramDerValue.resetTag((byte)4);
/* 132 */           this.name = new IPAddressName(paramDerValue);
/*     */         } else {
/* 134 */           throw new IOException("Invalid encoding of IP address");
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 8:
/* 139 */         if (paramDerValue.isContextSpecific() && !paramDerValue.isConstructed()) {
/* 140 */           paramDerValue.resetTag((byte)6);
/* 141 */           this.name = new OIDName(paramDerValue);
/*     */         } else {
/* 143 */           throw new IOException("Invalid encoding of OID name");
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 4:
/* 148 */         if (paramDerValue.isContextSpecific() && paramDerValue.isConstructed()) {
/* 149 */           this.name = new X500Name(paramDerValue.getData());
/*     */         } else {
/* 151 */           throw new IOException("Invalid encoding of Directory name");
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 5:
/* 156 */         if (paramDerValue.isContextSpecific() && paramDerValue.isConstructed()) {
/* 157 */           paramDerValue.resetTag((byte)48);
/* 158 */           this.name = new EDIPartyName(paramDerValue);
/*     */         } else {
/* 160 */           throw new IOException("Invalid encoding of EDI name");
/*     */         } 
/*     */         return;
/*     */     } 
/*     */     
/* 165 */     throw new IOException("Unrecognized GeneralName tag, (" + s + ")");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 174 */     return this.name.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralNameInterface getName() {
/* 182 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 189 */     return this.name.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 199 */     if (this == paramObject) {
/* 200 */       return true;
/*     */     }
/* 202 */     if (!(paramObject instanceof GeneralName))
/* 203 */       return false; 
/* 204 */     GeneralNameInterface generalNameInterface = ((GeneralName)paramObject).name;
/*     */     try {
/* 206 */       return (this.name.constrains(generalNameInterface) == 0);
/* 207 */     } catch (UnsupportedOperationException unsupportedOperationException) {
/* 208 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 218 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/* 228 */     DerOutputStream derOutputStream = new DerOutputStream();
/* 229 */     this.name.encode(derOutputStream);
/* 230 */     int i = this.name.getType();
/* 231 */     if (i == 0 || i == 3 || i == 5) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 236 */       paramDerOutputStream.writeImplicit(DerValue.createTag(-128, true, (byte)i), derOutputStream);
/*     */     }
/* 238 */     else if (i == 4) {
/*     */ 
/*     */       
/* 241 */       paramDerOutputStream.write(DerValue.createTag(-128, true, (byte)i), derOutputStream);
/*     */     }
/*     */     else {
/*     */       
/* 245 */       paramDerOutputStream.writeImplicit(DerValue.createTag(-128, false, (byte)i), derOutputStream);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\x509\GeneralName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */