/*     */ package sun.security.jgss;
/*     */ 
/*     */ import com.sun.security.jgss.ExtendedGSSContext;
/*     */ import com.sun.security.jgss.InquireSecContextPermission;
/*     */ import com.sun.security.jgss.InquireType;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.ietf.jgss.ChannelBinding;
/*     */ import org.ietf.jgss.GSSCredential;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.GSSName;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ import org.ietf.jgss.Oid;
/*     */ import sun.security.jgss.spi.GSSContextSpi;
/*     */ import sun.security.jgss.spi.GSSCredentialSpi;
/*     */ import sun.security.jgss.spi.GSSNameSpi;
/*     */ import sun.security.util.ObjectIdentifier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GSSContextImpl
/*     */   implements ExtendedGSSContext
/*     */ {
/*     */   private final GSSManagerImpl gssManager;
/*     */   private final boolean initiator;
/*     */   private static final int PRE_INIT = 1;
/*     */   private static final int IN_PROGRESS = 2;
/*     */   private static final int READY = 3;
/*     */   private static final int DELETED = 4;
/* 102 */   private int currentState = 1;
/*     */   
/* 104 */   private GSSContextSpi mechCtxt = null;
/* 105 */   private Oid mechOid = null;
/* 106 */   private ObjectIdentifier objId = null;
/*     */   
/* 108 */   private GSSCredentialImpl myCred = null;
/*     */   
/* 110 */   private GSSNameImpl srcName = null;
/* 111 */   private GSSNameImpl targName = null;
/*     */   
/* 113 */   private int reqLifetime = Integer.MAX_VALUE;
/* 114 */   private ChannelBinding channelBindings = null;
/*     */   
/*     */   private boolean reqConfState = true;
/*     */   
/*     */   private boolean reqIntegState = true;
/*     */   
/*     */   private boolean reqMutualAuthState = true;
/*     */   
/*     */   private boolean reqReplayDetState = true;
/*     */   
/*     */   private boolean reqSequenceDetState = true;
/*     */   
/*     */   private boolean reqCredDelegState = false;
/*     */   private boolean reqAnonState = false;
/*     */   private boolean reqDelegPolicyState = false;
/*     */   
/*     */   public GSSContextImpl(GSSManagerImpl paramGSSManagerImpl, GSSName paramGSSName, Oid paramOid, GSSCredential paramGSSCredential, int paramInt) throws GSSException {
/* 131 */     if (paramGSSName == null || !(paramGSSName instanceof GSSNameImpl)) {
/* 132 */       throw new GSSException(3);
/*     */     }
/* 134 */     if (paramOid == null) paramOid = ProviderList.DEFAULT_MECH_OID;
/*     */     
/* 136 */     this.gssManager = paramGSSManagerImpl;
/* 137 */     this.myCred = (GSSCredentialImpl)paramGSSCredential;
/* 138 */     this.reqLifetime = paramInt;
/* 139 */     this.targName = (GSSNameImpl)paramGSSName;
/* 140 */     this.mechOid = paramOid;
/* 141 */     this.initiator = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSContextImpl(GSSManagerImpl paramGSSManagerImpl, GSSCredential paramGSSCredential) throws GSSException {
/* 149 */     this.gssManager = paramGSSManagerImpl;
/* 150 */     this.myCred = (GSSCredentialImpl)paramGSSCredential;
/* 151 */     this.initiator = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GSSContextImpl(GSSManagerImpl paramGSSManagerImpl, byte[] paramArrayOfbyte) throws GSSException {
/* 162 */     this.gssManager = paramGSSManagerImpl;
/* 163 */     this.mechCtxt = paramGSSManagerImpl.getMechanismContext(paramArrayOfbyte);
/* 164 */     this.initiator = this.mechCtxt.isInitiator();
/* 165 */     this.mechOid = this.mechCtxt.getMech();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] initSecContext(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws GSSException {
/* 176 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(600);
/* 177 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     
/* 179 */     int i = initSecContext(byteArrayInputStream, byteArrayOutputStream);
/* 180 */     return (i == 0) ? null : byteArrayOutputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int initSecContext(InputStream paramInputStream, OutputStream paramOutputStream) throws GSSException {
/* 186 */     if (this.mechCtxt != null && this.currentState != 2) {
/* 187 */       throw new GSSExceptionImpl(11, "Illegal call to initSecContext");
/*     */     }
/*     */ 
/*     */     
/* 191 */     GSSHeader gSSHeader = null;
/* 192 */     int i = -1;
/* 193 */     GSSCredentialSpi gSSCredentialSpi = null;
/* 194 */     boolean bool = false;
/*     */     
/*     */     try {
/* 197 */       if (this.mechCtxt == null) {
/* 198 */         if (this.myCred != null) {
/*     */           try {
/* 200 */             gSSCredentialSpi = this.myCred.getElement(this.mechOid, true);
/* 201 */           } catch (GSSException gSSException) {
/* 202 */             if (GSSUtil.isSpNegoMech(this.mechOid) && gSSException
/* 203 */               .getMajor() == 13) {
/*     */               
/* 205 */               gSSCredentialSpi = this.myCred.getElement(this.myCred.getMechs()[0], true);
/*     */             } else {
/* 207 */               throw gSSException;
/*     */             } 
/*     */           } 
/*     */         }
/* 211 */         GSSNameSpi gSSNameSpi = this.targName.getElement(this.mechOid);
/* 212 */         this.mechCtxt = this.gssManager.getMechanismContext(gSSNameSpi, gSSCredentialSpi, this.reqLifetime, this.mechOid);
/*     */ 
/*     */ 
/*     */         
/* 216 */         this.mechCtxt.requestConf(this.reqConfState);
/* 217 */         this.mechCtxt.requestInteg(this.reqIntegState);
/* 218 */         this.mechCtxt.requestCredDeleg(this.reqCredDelegState);
/* 219 */         this.mechCtxt.requestMutualAuth(this.reqMutualAuthState);
/* 220 */         this.mechCtxt.requestReplayDet(this.reqReplayDetState);
/* 221 */         this.mechCtxt.requestSequenceDet(this.reqSequenceDetState);
/* 222 */         this.mechCtxt.requestAnonymity(this.reqAnonState);
/* 223 */         this.mechCtxt.setChannelBinding(this.channelBindings);
/* 224 */         this.mechCtxt.requestDelegPolicy(this.reqDelegPolicyState);
/*     */         
/* 226 */         this.objId = new ObjectIdentifier(this.mechOid.toString());
/*     */         
/* 228 */         this.currentState = 2;
/* 229 */         bool = true;
/*     */       }
/* 231 */       else if (!this.mechCtxt.getProvider().getName().equals("SunNativeGSS") && 
/* 232 */         !GSSUtil.isSpNegoMech(this.mechOid)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 237 */         gSSHeader = new GSSHeader(paramInputStream);
/* 238 */         if (!gSSHeader.getOid().equals(this.objId)) {
/* 239 */           throw new GSSExceptionImpl(10, "Mechanism not equal to " + this.mechOid
/*     */ 
/*     */               
/* 242 */               .toString() + " in initSecContext token");
/*     */         }
/* 244 */         i = gSSHeader.getMechTokenLength();
/*     */       } 
/*     */ 
/*     */       
/* 248 */       byte[] arrayOfByte = this.mechCtxt.initSecContext(paramInputStream, i);
/*     */       
/* 250 */       int j = 0;
/*     */       
/* 252 */       if (arrayOfByte != null) {
/* 253 */         j = arrayOfByte.length;
/* 254 */         if (!this.mechCtxt.getProvider().getName().equals("SunNativeGSS") && (bool || 
/* 255 */           !GSSUtil.isSpNegoMech(this.mechOid))) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 260 */           gSSHeader = new GSSHeader(this.objId, arrayOfByte.length);
/* 261 */           j += gSSHeader.encode(paramOutputStream);
/*     */         } 
/* 263 */         paramOutputStream.write(arrayOfByte);
/*     */       } 
/*     */       
/* 266 */       if (this.mechCtxt.isEstablished()) {
/* 267 */         this.currentState = 3;
/*     */       }
/* 269 */       return j;
/*     */     }
/* 271 */     catch (IOException iOException) {
/* 272 */       throw new GSSExceptionImpl(10, iOException
/* 273 */           .getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] acceptSecContext(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws GSSException {
/* 284 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
/* 285 */     acceptSecContext(new ByteArrayInputStream(paramArrayOfbyte, paramInt1, paramInt2), byteArrayOutputStream);
/*     */     
/* 287 */     byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
/* 288 */     return (arrayOfByte.length == 0) ? null : arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptSecContext(InputStream paramInputStream, OutputStream paramOutputStream) throws GSSException {
/* 294 */     if (this.mechCtxt != null && this.currentState != 2) {
/* 295 */       throw new GSSExceptionImpl(11, "Illegal call to acceptSecContext");
/*     */     }
/*     */ 
/*     */     
/* 299 */     GSSHeader gSSHeader = null;
/* 300 */     int i = -1;
/* 301 */     GSSCredentialSpi gSSCredentialSpi = null;
/*     */     
/*     */     try {
/* 304 */       if (this.mechCtxt == null) {
/*     */         
/* 306 */         gSSHeader = new GSSHeader(paramInputStream);
/* 307 */         i = gSSHeader.getMechTokenLength();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 312 */         this.objId = gSSHeader.getOid();
/* 313 */         this.mechOid = new Oid(this.objId.toString());
/*     */ 
/*     */         
/* 316 */         if (this.myCred != null) {
/* 317 */           gSSCredentialSpi = this.myCred.getElement(this.mechOid, false);
/*     */         }
/*     */         
/* 320 */         this.mechCtxt = this.gssManager.getMechanismContext(gSSCredentialSpi, this.mechOid);
/*     */         
/* 322 */         this.mechCtxt.setChannelBinding(this.channelBindings);
/*     */         
/* 324 */         this.currentState = 2;
/*     */       }
/* 326 */       else if (!this.mechCtxt.getProvider().getName().equals("SunNativeGSS") && 
/* 327 */         !GSSUtil.isSpNegoMech(this.mechOid)) {
/*     */ 
/*     */ 
/*     */         
/* 331 */         gSSHeader = new GSSHeader(paramInputStream);
/* 332 */         if (!gSSHeader.getOid().equals(this.objId)) {
/* 333 */           throw new GSSExceptionImpl(10, "Mechanism not equal to " + this.mechOid
/*     */ 
/*     */               
/* 336 */               .toString() + " in acceptSecContext token");
/*     */         }
/* 338 */         i = gSSHeader.getMechTokenLength();
/*     */       } 
/*     */ 
/*     */       
/* 342 */       byte[] arrayOfByte = this.mechCtxt.acceptSecContext(paramInputStream, i);
/*     */       
/* 344 */       if (arrayOfByte != null) {
/* 345 */         int j = arrayOfByte.length;
/* 346 */         if (!this.mechCtxt.getProvider().getName().equals("SunNativeGSS") && 
/* 347 */           !GSSUtil.isSpNegoMech(this.mechOid)) {
/*     */ 
/*     */ 
/*     */           
/* 351 */           gSSHeader = new GSSHeader(this.objId, arrayOfByte.length);
/* 352 */           j += gSSHeader.encode(paramOutputStream);
/*     */         } 
/* 354 */         paramOutputStream.write(arrayOfByte);
/*     */       } 
/*     */       
/* 357 */       if (this.mechCtxt.isEstablished()) {
/* 358 */         this.currentState = 3;
/*     */       }
/* 360 */     } catch (IOException iOException) {
/* 361 */       throw new GSSExceptionImpl(10, iOException
/* 362 */           .getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEstablished() {
/* 367 */     if (this.mechCtxt == null) {
/* 368 */       return false;
/*     */     }
/* 370 */     return (this.currentState == 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWrapSizeLimit(int paramInt1, boolean paramBoolean, int paramInt2) throws GSSException {
/* 375 */     if (this.mechCtxt != null) {
/* 376 */       return this.mechCtxt.getWrapSizeLimit(paramInt1, paramBoolean, paramInt2);
/*     */     }
/* 378 */     throw new GSSExceptionImpl(12, "No mechanism context yet!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 384 */     if (this.mechCtxt != null) {
/* 385 */       return this.mechCtxt.wrap(paramArrayOfbyte, paramInt1, paramInt2, paramMessageProp);
/*     */     }
/* 387 */     throw new GSSExceptionImpl(12, "No mechanism context yet!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void wrap(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/* 393 */     if (this.mechCtxt != null) {
/* 394 */       this.mechCtxt.wrap(paramInputStream, paramOutputStream, paramMessageProp);
/*     */     } else {
/* 396 */       throw new GSSExceptionImpl(12, "No mechanism context yet!");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 402 */     if (this.mechCtxt != null) {
/* 403 */       return this.mechCtxt.unwrap(paramArrayOfbyte, paramInt1, paramInt2, paramMessageProp);
/*     */     }
/* 405 */     throw new GSSExceptionImpl(12, "No mechanism context yet!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void unwrap(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/* 411 */     if (this.mechCtxt != null) {
/* 412 */       this.mechCtxt.unwrap(paramInputStream, paramOutputStream, paramMessageProp);
/*     */     } else {
/* 414 */       throw new GSSExceptionImpl(12, "No mechanism context yet!");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getMIC(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 420 */     if (this.mechCtxt != null) {
/* 421 */       return this.mechCtxt.getMIC(paramArrayOfbyte, paramInt1, paramInt2, paramMessageProp);
/*     */     }
/* 423 */     throw new GSSExceptionImpl(12, "No mechanism context yet!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getMIC(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/* 429 */     if (this.mechCtxt != null) {
/* 430 */       this.mechCtxt.getMIC(paramInputStream, paramOutputStream, paramMessageProp);
/*     */     } else {
/* 432 */       throw new GSSExceptionImpl(12, "No mechanism context yet!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void verifyMIC(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4, MessageProp paramMessageProp) throws GSSException {
/* 439 */     if (this.mechCtxt != null) {
/* 440 */       this.mechCtxt.verifyMIC(paramArrayOfbyte1, paramInt1, paramInt2, paramArrayOfbyte2, paramInt3, paramInt4, paramMessageProp);
/*     */     } else {
/*     */       
/* 443 */       throw new GSSExceptionImpl(12, "No mechanism context yet!");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void verifyMIC(InputStream paramInputStream1, InputStream paramInputStream2, MessageProp paramMessageProp) throws GSSException {
/* 449 */     if (this.mechCtxt != null) {
/* 450 */       this.mechCtxt.verifyMIC(paramInputStream1, paramInputStream2, paramMessageProp);
/*     */     } else {
/* 452 */       throw new GSSExceptionImpl(12, "No mechanism context yet!");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] export() throws GSSException {
/* 458 */     byte[] arrayOfByte = null;
/*     */ 
/*     */     
/* 461 */     if (this.mechCtxt.isTransferable() && this.mechCtxt
/* 462 */       .getProvider().getName().equals("SunNativeGSS")) {
/* 463 */       arrayOfByte = this.mechCtxt.export();
/*     */     }
/* 465 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public void requestMutualAuth(boolean paramBoolean) throws GSSException {
/* 469 */     if (this.mechCtxt == null && this.initiator)
/* 470 */       this.reqMutualAuthState = paramBoolean; 
/*     */   }
/*     */   
/*     */   public void requestReplayDet(boolean paramBoolean) throws GSSException {
/* 474 */     if (this.mechCtxt == null && this.initiator)
/* 475 */       this.reqReplayDetState = paramBoolean; 
/*     */   }
/*     */   
/*     */   public void requestSequenceDet(boolean paramBoolean) throws GSSException {
/* 479 */     if (this.mechCtxt == null && this.initiator)
/* 480 */       this.reqSequenceDetState = paramBoolean; 
/*     */   }
/*     */   
/*     */   public void requestCredDeleg(boolean paramBoolean) throws GSSException {
/* 484 */     if (this.mechCtxt == null && this.initiator)
/* 485 */       this.reqCredDelegState = paramBoolean; 
/*     */   }
/*     */   
/*     */   public void requestAnonymity(boolean paramBoolean) throws GSSException {
/* 489 */     if (this.mechCtxt == null && this.initiator)
/* 490 */       this.reqAnonState = paramBoolean; 
/*     */   }
/*     */   
/*     */   public void requestConf(boolean paramBoolean) throws GSSException {
/* 494 */     if (this.mechCtxt == null && this.initiator)
/* 495 */       this.reqConfState = paramBoolean; 
/*     */   }
/*     */   
/*     */   public void requestInteg(boolean paramBoolean) throws GSSException {
/* 499 */     if (this.mechCtxt == null && this.initiator)
/* 500 */       this.reqIntegState = paramBoolean; 
/*     */   }
/*     */   
/*     */   public void requestLifetime(int paramInt) throws GSSException {
/* 504 */     if (this.mechCtxt == null && this.initiator) {
/* 505 */       this.reqLifetime = paramInt;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChannelBinding(ChannelBinding paramChannelBinding) throws GSSException {
/* 511 */     if (this.mechCtxt == null) {
/* 512 */       this.channelBindings = paramChannelBinding;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getCredDelegState() {
/* 517 */     if (this.mechCtxt != null) {
/* 518 */       return this.mechCtxt.getCredDelegState();
/*     */     }
/* 520 */     return this.reqCredDelegState;
/*     */   }
/*     */   
/*     */   public boolean getMutualAuthState() {
/* 524 */     if (this.mechCtxt != null) {
/* 525 */       return this.mechCtxt.getMutualAuthState();
/*     */     }
/* 527 */     return this.reqMutualAuthState;
/*     */   }
/*     */   
/*     */   public boolean getReplayDetState() {
/* 531 */     if (this.mechCtxt != null) {
/* 532 */       return this.mechCtxt.getReplayDetState();
/*     */     }
/* 534 */     return this.reqReplayDetState;
/*     */   }
/*     */   
/*     */   public boolean getSequenceDetState() {
/* 538 */     if (this.mechCtxt != null) {
/* 539 */       return this.mechCtxt.getSequenceDetState();
/*     */     }
/* 541 */     return this.reqSequenceDetState;
/*     */   }
/*     */   
/*     */   public boolean getAnonymityState() {
/* 545 */     if (this.mechCtxt != null) {
/* 546 */       return this.mechCtxt.getAnonymityState();
/*     */     }
/* 548 */     return this.reqAnonState;
/*     */   }
/*     */   
/*     */   public boolean isTransferable() throws GSSException {
/* 552 */     if (this.mechCtxt != null) {
/* 553 */       return this.mechCtxt.isTransferable();
/*     */     }
/* 555 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isProtReady() {
/* 559 */     if (this.mechCtxt != null) {
/* 560 */       return this.mechCtxt.isProtReady();
/*     */     }
/* 562 */     return false;
/*     */   }
/*     */   
/*     */   public boolean getConfState() {
/* 566 */     if (this.mechCtxt != null) {
/* 567 */       return this.mechCtxt.getConfState();
/*     */     }
/* 569 */     return this.reqConfState;
/*     */   }
/*     */   
/*     */   public boolean getIntegState() {
/* 573 */     if (this.mechCtxt != null) {
/* 574 */       return this.mechCtxt.getIntegState();
/*     */     }
/* 576 */     return this.reqIntegState;
/*     */   }
/*     */   
/*     */   public int getLifetime() {
/* 580 */     if (this.mechCtxt != null) {
/* 581 */       return this.mechCtxt.getLifetime();
/*     */     }
/* 583 */     return this.reqLifetime;
/*     */   }
/*     */   
/*     */   public GSSName getSrcName() throws GSSException {
/* 587 */     if (this.srcName == null) {
/* 588 */       this
/* 589 */         .srcName = GSSNameImpl.wrapElement(this.gssManager, this.mechCtxt.getSrcName());
/*     */     }
/* 591 */     return this.srcName;
/*     */   }
/*     */   
/*     */   public GSSName getTargName() throws GSSException {
/* 595 */     if (this.targName == null) {
/* 596 */       this
/* 597 */         .targName = GSSNameImpl.wrapElement(this.gssManager, this.mechCtxt.getTargName());
/*     */     }
/* 599 */     return this.targName;
/*     */   }
/*     */   
/*     */   public Oid getMech() throws GSSException {
/* 603 */     if (this.mechCtxt != null) {
/* 604 */       return this.mechCtxt.getMech();
/*     */     }
/* 606 */     return this.mechOid;
/*     */   }
/*     */ 
/*     */   
/*     */   public GSSCredential getDelegCred() throws GSSException {
/* 611 */     if (this.mechCtxt == null) {
/* 612 */       throw new GSSExceptionImpl(12, "No mechanism context yet!");
/*     */     }
/* 614 */     GSSCredentialSpi gSSCredentialSpi = this.mechCtxt.getDelegCred();
/* 615 */     return (gSSCredentialSpi == null) ? null : new GSSCredentialImpl(this.gssManager, gSSCredentialSpi);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInitiator() throws GSSException {
/* 620 */     return this.initiator;
/*     */   }
/*     */   
/*     */   public void dispose() throws GSSException {
/* 624 */     this.currentState = 4;
/* 625 */     if (this.mechCtxt != null) {
/* 626 */       this.mechCtxt.dispose();
/* 627 */       this.mechCtxt = null;
/*     */     } 
/* 629 */     this.myCred = null;
/* 630 */     this.srcName = null;
/* 631 */     this.targName = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object inquireSecContext(InquireType paramInquireType) throws GSSException {
/* 638 */     SecurityManager securityManager = System.getSecurityManager();
/* 639 */     if (securityManager != null) {
/* 640 */       securityManager.checkPermission(new InquireSecContextPermission(paramInquireType.toString()));
/*     */     }
/* 642 */     if (this.mechCtxt == null) {
/* 643 */       throw new GSSException(12);
/*     */     }
/* 645 */     return this.mechCtxt.inquireSecContext(paramInquireType);
/*     */   }
/*     */ 
/*     */   
/*     */   public void requestDelegPolicy(boolean paramBoolean) throws GSSException {
/* 650 */     if (this.mechCtxt == null && this.initiator) {
/* 651 */       this.reqDelegPolicyState = paramBoolean;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getDelegPolicyState() {
/* 656 */     if (this.mechCtxt != null) {
/* 657 */       return this.mechCtxt.getDelegPolicyState();
/*     */     }
/* 659 */     return this.reqDelegPolicyState;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\jgss\GSSContextImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */