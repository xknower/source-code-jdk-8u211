/*     */ package com.sun.security.sasl.gsskerb;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.sasl.SaslClient;
/*     */ import javax.security.sasl.SaslException;
/*     */ import org.ietf.jgss.GSSCredential;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSManager;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.MessageProp;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class GssKrb5Client
/*     */   extends GssKrb5Base
/*     */   implements SaslClient
/*     */ {
/*  83 */   private static final String MY_CLASS_NAME = GssKrb5Client.class.getName();
/*     */ 
/*     */   
/*     */   private boolean finalHandshake = false;
/*     */ 
/*     */   
/*     */   private boolean mutual = false;
/*     */ 
/*     */   
/*     */   private byte[] authzID;
/*     */ 
/*     */ 
/*     */   
/*     */   GssKrb5Client(String paramString1, String paramString2, String paramString3, Map<String, ?> paramMap, CallbackHandler paramCallbackHandler) throws SaslException {
/*  97 */     super(paramMap, MY_CLASS_NAME);
/*     */     
/*  99 */     String str = paramString2 + "@" + paramString3;
/* 100 */     logger.log(Level.FINE, "KRB5CLNT01:Requesting service name: {0}", str);
/*     */ 
/*     */     
/*     */     try {
/* 104 */       GSSManager gSSManager = GSSManager.getInstance();
/*     */ 
/*     */       
/* 107 */       GSSName gSSName = gSSManager.createName(str, GSSName.NT_HOSTBASED_SERVICE, KRB5_OID);
/*     */ 
/*     */ 
/*     */       
/* 111 */       GSSCredential gSSCredential = null;
/* 112 */       if (paramMap != null) {
/* 113 */         Object object = paramMap.get("javax.security.sasl.credentials");
/* 114 */         if (object != null && object instanceof GSSCredential) {
/* 115 */           gSSCredential = (GSSCredential)object;
/* 116 */           logger.log(Level.FINE, "KRB5CLNT01:Using the credentials supplied in javax.security.sasl.credentials");
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       this.secCtx = gSSManager.createContext(gSSName, KRB5_OID, gSSCredential, 2147483647);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 129 */       if (gSSCredential != null) {
/* 130 */         this.secCtx.requestCredDeleg(true);
/*     */       }
/*     */ 
/*     */       
/* 134 */       if (paramMap != null) {
/*     */         
/* 136 */         String str1 = (String)paramMap.get("javax.security.sasl.server.authentication");
/* 137 */         if (str1 != null) {
/* 138 */           this.mutual = "true".equalsIgnoreCase(str1);
/*     */         }
/*     */       } 
/* 141 */       this.secCtx.requestMutualAuth(this.mutual);
/*     */ 
/*     */ 
/*     */       
/* 145 */       this.secCtx.requestConf(true);
/* 146 */       this.secCtx.requestInteg(true);
/*     */     }
/* 148 */     catch (GSSException gSSException) {
/* 149 */       throw new SaslException("Failure to initialize security context", gSSException);
/*     */     } 
/*     */     
/* 152 */     if (paramString1 != null && paramString1.length() > 0) {
/*     */       try {
/* 154 */         this.authzID = paramString1.getBytes("UTF8");
/* 155 */       } catch (IOException iOException) {
/* 156 */         throw new SaslException("Cannot encode authorization ID", iOException);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasInitialResponse() {
/* 162 */     return true;
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
/*     */ 
/*     */   
/*     */   public byte[] evaluateChallenge(byte[] paramArrayOfbyte) throws SaslException {
/* 180 */     if (this.completed) {
/* 181 */       throw new IllegalStateException("GSSAPI authentication already complete");
/*     */     }
/*     */ 
/*     */     
/* 185 */     if (this.finalHandshake) {
/* 186 */       return doFinalHandshake(paramArrayOfbyte);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 192 */       byte[] arrayOfByte = this.secCtx.initSecContext(paramArrayOfbyte, 0, paramArrayOfbyte.length);
/*     */       
/* 194 */       if (logger.isLoggable(Level.FINER)) {
/* 195 */         traceOutput(MY_CLASS_NAME, "evaluteChallenge", "KRB5CLNT02:Challenge: [raw]", paramArrayOfbyte);
/*     */         
/* 197 */         traceOutput(MY_CLASS_NAME, "evaluateChallenge", "KRB5CLNT03:Response: [after initSecCtx]", arrayOfByte);
/*     */       } 
/*     */ 
/*     */       
/* 201 */       if (this.secCtx.isEstablished()) {
/* 202 */         this.finalHandshake = true;
/* 203 */         if (arrayOfByte == null)
/*     */         {
/* 205 */           return EMPTY;
/*     */         }
/*     */       } 
/*     */       
/* 209 */       return arrayOfByte;
/* 210 */     } catch (GSSException gSSException) {
/* 211 */       throw new SaslException("GSS initiate failed", gSSException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] doFinalHandshake(byte[] paramArrayOfbyte) throws SaslException {
/*     */     try {
/* 221 */       if (logger.isLoggable(Level.FINER)) {
/* 222 */         traceOutput(MY_CLASS_NAME, "doFinalHandshake", "KRB5CLNT04:Challenge [raw]:", paramArrayOfbyte);
/*     */       }
/*     */ 
/*     */       
/* 226 */       if (paramArrayOfbyte.length == 0)
/*     */       {
/* 228 */         return EMPTY;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 233 */       byte[] arrayOfByte1 = this.secCtx.unwrap(paramArrayOfbyte, 0, paramArrayOfbyte.length, new MessageProp(0, false));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 238 */       if (logger.isLoggable(Level.FINE)) {
/* 239 */         if (logger.isLoggable(Level.FINER)) {
/* 240 */           traceOutput(MY_CLASS_NAME, "doFinalHandshake", "KRB5CLNT05:Challenge [unwrapped]:", arrayOfByte1);
/*     */         }
/*     */         
/* 243 */         logger.log(Level.FINE, "KRB5CLNT06:Server protections: {0}", new Byte(arrayOfByte1[0]));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       byte b = findPreferredMask(arrayOfByte1[0], this.qop);
/* 250 */       if (b == 0) {
/* 251 */         throw new SaslException("No common protection layer between client and server");
/*     */       }
/*     */ 
/*     */       
/* 255 */       if ((b & 0x4) != 0) {
/* 256 */         this.privacy = true;
/* 257 */         this.integrity = true;
/* 258 */       } else if ((b & 0x2) != 0) {
/* 259 */         this.integrity = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 264 */       int i = networkByteOrderToInt(arrayOfByte1, 1, 3);
/*     */ 
/*     */ 
/*     */       
/* 268 */       this
/* 269 */         .sendMaxBufSize = (this.sendMaxBufSize == 0) ? i : Math.min(this.sendMaxBufSize, i);
/*     */ 
/*     */       
/* 272 */       this.rawSendSize = this.secCtx.getWrapSizeLimit(0, this.privacy, this.sendMaxBufSize);
/*     */ 
/*     */       
/* 275 */       if (logger.isLoggable(Level.FINE)) {
/* 276 */         logger.log(Level.FINE, "KRB5CLNT07:Client max recv size: {0}; server max recv size: {1}; rawSendSize: {2}", new Object[] { new Integer(this.recvMaxBufSize), new Integer(i), new Integer(this.rawSendSize) });
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 285 */       int j = 4;
/* 286 */       if (this.authzID != null) {
/* 287 */         j += this.authzID.length;
/*     */       }
/*     */       
/* 290 */       byte[] arrayOfByte2 = new byte[j];
/* 291 */       arrayOfByte2[0] = b;
/*     */       
/* 293 */       if (logger.isLoggable(Level.FINE)) {
/* 294 */         logger.log(Level.FINE, "KRB5CLNT08:Selected protection: {0}; privacy: {1}; integrity: {2}", new Object[] { new Byte(b), 
/*     */ 
/*     */               
/* 297 */               Boolean.valueOf(this.privacy), 
/* 298 */               Boolean.valueOf(this.integrity) });
/*     */       }
/*     */       
/* 301 */       intToNetworkByteOrder(this.recvMaxBufSize, arrayOfByte2, 1, 3);
/* 302 */       if (this.authzID != null) {
/*     */         
/* 304 */         System.arraycopy(this.authzID, 0, arrayOfByte2, 4, this.authzID.length);
/* 305 */         logger.log(Level.FINE, "KRB5CLNT09:Authzid: {0}", this.authzID);
/*     */       } 
/*     */       
/* 308 */       if (logger.isLoggable(Level.FINER)) {
/* 309 */         traceOutput(MY_CLASS_NAME, "doFinalHandshake", "KRB5CLNT10:Response [raw]", arrayOfByte2);
/*     */       }
/*     */ 
/*     */       
/* 313 */       arrayOfByte1 = this.secCtx.wrap(arrayOfByte2, 0, arrayOfByte2.length, new MessageProp(0, false));
/*     */ 
/*     */ 
/*     */       
/* 317 */       if (logger.isLoggable(Level.FINER)) {
/* 318 */         traceOutput(MY_CLASS_NAME, "doFinalHandshake", "KRB5CLNT11:Response [after wrap]", arrayOfByte1);
/*     */       }
/*     */ 
/*     */       
/* 322 */       this.completed = true;
/*     */       
/* 324 */       return arrayOfByte1;
/* 325 */     } catch (GSSException gSSException) {
/* 326 */       throw new SaslException("Final handshake failed", gSSException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\security\sasl\gsskerb\GssKrb5Client.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */