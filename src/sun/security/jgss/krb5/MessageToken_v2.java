/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.Arrays;
/*     */ import org.ietf.jgss.GSSException;
/*     */ import org.ietf.jgss.MessageProp;
/*     */ import sun.security.jgss.GSSToken;
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
/*     */ abstract class MessageToken_v2
/*     */   extends Krb5Token
/*     */ {
/*     */   protected static final int TOKEN_HEADER_SIZE = 16;
/*     */   private static final int TOKEN_ID_POS = 0;
/*     */   private static final int TOKEN_FLAG_POS = 2;
/*     */   private static final int TOKEN_EC_POS = 4;
/*     */   private static final int TOKEN_RRC_POS = 6;
/*     */   protected static final int CONFOUNDER_SIZE = 16;
/*     */   static final int KG_USAGE_ACCEPTOR_SEAL = 22;
/*     */   static final int KG_USAGE_ACCEPTOR_SIGN = 23;
/*     */   static final int KG_USAGE_INITIATOR_SEAL = 24;
/*     */   static final int KG_USAGE_INITIATOR_SIGN = 25;
/*     */   private static final int FLAG_SENDER_IS_ACCEPTOR = 1;
/*     */   private static final int FLAG_WRAP_CONFIDENTIAL = 2;
/*     */   private static final int FLAG_ACCEPTOR_SUBKEY = 4;
/*     */   private static final int FILLER = 255;
/* 123 */   private MessageTokenHeader tokenHeader = null;
/*     */ 
/*     */   
/* 126 */   private int tokenId = 0;
/*     */   
/*     */   private int seqNumber;
/*     */   
/*     */   protected byte[] tokenData;
/*     */   protected int tokenDataLen;
/* 132 */   private int key_usage = 0;
/*     */ 
/*     */   
/* 135 */   private int ec = 0;
/* 136 */   private int rrc = 0;
/*     */ 
/*     */   
/* 139 */   byte[] checksum = null;
/*     */   
/*     */   private boolean confState = true;
/*     */   
/*     */   private boolean initiator = true;
/*     */   
/*     */   private boolean have_acceptor_subkey = false;
/*     */   
/* 147 */   CipherHelper cipherHelper = null;
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
/*     */   MessageToken_v2(int paramInt1, Krb5Context paramKrb5Context, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, MessageProp paramMessageProp) throws GSSException {
/* 165 */     this(paramInt1, paramKrb5Context, new ByteArrayInputStream(paramArrayOfbyte, paramInt2, paramInt3), paramMessageProp);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MessageToken_v2(int paramInt, Krb5Context paramKrb5Context, InputStream paramInputStream, MessageProp paramMessageProp) throws GSSException {
/* 188 */     init(paramInt, paramKrb5Context);
/*     */     
/*     */     try {
/* 191 */       if (!this.confState) {
/* 192 */         paramMessageProp.setPrivacy(false);
/*     */       }
/* 194 */       this.tokenHeader = new MessageTokenHeader(paramInputStream, paramMessageProp, paramInt);
/*     */ 
/*     */       
/* 197 */       if (paramInt == 1284) {
/* 198 */         this.key_usage = !this.initiator ? 24 : 22;
/*     */       }
/* 200 */       else if (paramInt == 1028) {
/* 201 */         this.key_usage = !this.initiator ? 25 : 23;
/*     */       } 
/*     */ 
/*     */       
/* 205 */       int i = 0;
/* 206 */       if (paramInt == 1284 && paramMessageProp.getPrivacy()) {
/*     */         
/* 208 */         i = 32 + this.cipherHelper.getChecksumLength();
/*     */       } else {
/* 210 */         i = this.cipherHelper.getChecksumLength();
/*     */       } 
/*     */ 
/*     */       
/* 214 */       if (paramInt == 1028) {
/*     */         
/* 216 */         this.tokenDataLen = i;
/* 217 */         this.tokenData = new byte[i];
/* 218 */         readFully(paramInputStream, this.tokenData);
/*     */       } else {
/* 220 */         this.tokenDataLen = paramInputStream.available();
/* 221 */         if (this.tokenDataLen >= i) {
/* 222 */           this.tokenData = new byte[this.tokenDataLen];
/* 223 */           readFully(paramInputStream, this.tokenData);
/*     */         } else {
/* 225 */           byte[] arrayOfByte = new byte[i];
/* 226 */           readFully(paramInputStream, arrayOfByte);
/*     */ 
/*     */           
/* 229 */           int j = paramInputStream.available();
/* 230 */           this.tokenDataLen = i + j;
/* 231 */           this.tokenData = Arrays.copyOf(arrayOfByte, this.tokenDataLen);
/* 232 */           readFully(paramInputStream, this.tokenData, i, j);
/*     */         } 
/*     */       } 
/*     */       
/* 236 */       if (paramInt == 1284) {
/* 237 */         rotate();
/*     */       }
/*     */       
/* 240 */       if (paramInt == 1028 || (paramInt == 1284 && 
/* 241 */         !paramMessageProp.getPrivacy()))
/*     */       {
/* 243 */         int j = this.cipherHelper.getChecksumLength();
/* 244 */         this.checksum = new byte[j];
/* 245 */         System.arraycopy(this.tokenData, this.tokenDataLen - j, this.checksum, 0, j);
/*     */ 
/*     */ 
/*     */         
/* 249 */         if (paramInt == 1284 && !paramMessageProp.getPrivacy() && 
/* 250 */           j != this.ec) {
/* 251 */           throw new GSSException(10, -1, 
/* 252 */               getTokenName(paramInt) + ":EC incorrect!");
/*     */         }
/*     */       }
/*     */     
/* 256 */     } catch (IOException iOException) {
/* 257 */       throw new GSSException(10, -1, 
/* 258 */           getTokenName(paramInt) + ":" + iOException.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getTokenId() {
/* 267 */     return this.tokenId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getKeyUsage() {
/* 275 */     return this.key_usage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean getConfState() {
/* 284 */     return this.confState;
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
/*     */ 
/*     */   
/*     */   public void genSignAndSeqNumber(MessageProp paramMessageProp, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws GSSException {
/* 304 */     int i = paramMessageProp.getQOP();
/* 305 */     if (i != 0) {
/* 306 */       i = 0;
/* 307 */       paramMessageProp.setQOP(i);
/*     */     } 
/*     */     
/* 310 */     if (!this.confState) {
/* 311 */       paramMessageProp.setPrivacy(false);
/*     */     }
/*     */ 
/*     */     
/* 315 */     this.tokenHeader = new MessageTokenHeader(this.tokenId, paramMessageProp.getPrivacy());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 320 */     if (this.tokenId == 1284) {
/* 321 */       this.key_usage = this.initiator ? 24 : 22;
/*     */     }
/* 323 */     else if (this.tokenId == 1028) {
/* 324 */       this.key_usage = this.initiator ? 25 : 23;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 329 */     if (this.tokenId == 1028 || (
/* 330 */       !paramMessageProp.getPrivacy() && this.tokenId == 1284)) {
/* 331 */       this.checksum = getChecksum(paramArrayOfbyte, paramInt1, paramInt2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 338 */     if (!paramMessageProp.getPrivacy() && this.tokenId == 1284) {
/* 339 */       byte[] arrayOfByte = this.tokenHeader.getBytes();
/* 340 */       arrayOfByte[4] = (byte)(this.checksum.length >>> 8);
/* 341 */       arrayOfByte[5] = (byte)this.checksum.length;
/*     */     } 
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
/*     */   
/*     */   public final boolean verifySign(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws GSSException {
/* 361 */     byte[] arrayOfByte = getChecksum(paramArrayOfbyte, paramInt1, paramInt2);
/*     */ 
/*     */     
/* 364 */     if (MessageDigest.isEqual(this.checksum, arrayOfByte))
/*     */     {
/* 366 */       return true;
/*     */     }
/* 368 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotate() {
/* 377 */     if (this.rrc % this.tokenDataLen != 0) {
/* 378 */       this.rrc %= this.tokenDataLen;
/* 379 */       byte[] arrayOfByte = new byte[this.tokenDataLen];
/*     */       
/* 381 */       System.arraycopy(this.tokenData, this.rrc, arrayOfByte, 0, this.tokenDataLen - this.rrc);
/* 382 */       System.arraycopy(this.tokenData, 0, arrayOfByte, this.tokenDataLen - this.rrc, this.rrc);
/*     */       
/* 384 */       this.tokenData = arrayOfByte;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final int getSequenceNumber() {
/* 389 */     return this.seqNumber;
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
/*     */   byte[] getChecksum(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws GSSException {
/* 417 */     byte[] arrayOfByte = this.tokenHeader.getBytes();
/*     */ 
/*     */     
/* 420 */     int i = arrayOfByte[2] & 0x2;
/*     */ 
/*     */ 
/*     */     
/* 424 */     if (i == 0 && this.tokenId == 1284) {
/* 425 */       arrayOfByte[4] = 0;
/* 426 */       arrayOfByte[5] = 0;
/* 427 */       arrayOfByte[6] = 0;
/* 428 */       arrayOfByte[7] = 0;
/*     */     } 
/* 430 */     return this.cipherHelper.calculateChecksum(arrayOfByte, paramArrayOfbyte, paramInt1, paramInt2, this.key_usage);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MessageToken_v2(int paramInt, Krb5Context paramKrb5Context) throws GSSException {
/* 453 */     init(paramInt, paramKrb5Context);
/* 454 */     this.seqNumber = paramKrb5Context.incrementMySequenceNumber();
/*     */   }
/*     */   
/*     */   private void init(int paramInt, Krb5Context paramKrb5Context) throws GSSException {
/* 458 */     this.tokenId = paramInt;
/*     */     
/* 460 */     this.confState = paramKrb5Context.getConfState();
/*     */     
/* 462 */     this.initiator = paramKrb5Context.isInitiator();
/*     */     
/* 464 */     this.have_acceptor_subkey = (paramKrb5Context.getKeySrc() == 2);
/*     */     
/* 466 */     this.cipherHelper = paramKrb5Context.getCipherHelper(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeHeader(OutputStream paramOutputStream) throws IOException {
/* 477 */     this.tokenHeader.encode(paramOutputStream);
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
/*     */   protected final byte[] getTokenHeader() {
/* 489 */     return this.tokenHeader.getBytes();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void encode(OutputStream paramOutputStream) throws IOException;
/*     */ 
/*     */ 
/*     */   
/*     */   class MessageTokenHeader
/*     */   {
/*     */     private int tokenId;
/*     */ 
/*     */     
/* 503 */     private byte[] bytes = new byte[16];
/*     */ 
/*     */ 
/*     */     
/*     */     public MessageTokenHeader(int param1Int, boolean param1Boolean) throws GSSException {
/* 508 */       this.tokenId = param1Int;
/*     */       
/* 510 */       this.bytes[0] = (byte)(param1Int >>> 8);
/* 511 */       this.bytes[1] = (byte)param1Int;
/*     */ 
/*     */       
/* 514 */       int i = 0;
/*     */ 
/*     */ 
/*     */       
/* 518 */       i = (MessageToken_v2.this.initiator ? 0 : 1) | ((param1Boolean && param1Int != 1028) ? 2 : 0) | (MessageToken_v2.this.have_acceptor_subkey ? 4 : 0);
/* 519 */       this.bytes[2] = (byte)i;
/*     */ 
/*     */       
/* 522 */       this.bytes[3] = -1;
/*     */       
/* 524 */       if (param1Int == 1284) {
/*     */         
/* 526 */         this.bytes[4] = 0;
/* 527 */         this.bytes[5] = 0;
/*     */         
/* 529 */         this.bytes[6] = 0;
/* 530 */         this.bytes[7] = 0;
/* 531 */       } else if (param1Int == 1028) {
/*     */         
/* 533 */         for (byte b = 4; b < 8; b++) {
/* 534 */           this.bytes[b] = -1;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 539 */       GSSToken.writeBigEndian(MessageToken_v2.this.seqNumber, this.bytes, 12);
/*     */     }
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
/*     */     public MessageTokenHeader(InputStream param1InputStream, MessageProp param1MessageProp, int param1Int) throws IOException, GSSException {
/* 555 */       GSSToken.readFully(param1InputStream, this.bytes, 0, 16);
/* 556 */       this.tokenId = GSSToken.readInt(this.bytes, 0);
/*     */ 
/*     */       
/* 559 */       if (this.tokenId != param1Int) {
/* 560 */         throw new GSSException(10, -1, 
/* 561 */             Krb5Token.getTokenName(this.tokenId) + ":Defective Token ID!");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 570 */       byte b = MessageToken_v2.this.initiator ? 1 : 0;
/* 571 */       int i = this.bytes[2] & 0x1;
/* 572 */       if (i != b) {
/* 573 */         throw new GSSException(10, -1, 
/* 574 */             Krb5Token.getTokenName(this.tokenId) + ":Acceptor Flag Error!");
/*     */       }
/*     */ 
/*     */       
/* 578 */       int j = this.bytes[2] & 0x2;
/* 579 */       if (j == 2 && this.tokenId == 1284) {
/*     */         
/* 581 */         param1MessageProp.setPrivacy(true);
/*     */       } else {
/* 583 */         param1MessageProp.setPrivacy(false);
/*     */       } 
/*     */       
/* 586 */       if (this.tokenId == 1284) {
/*     */         
/* 588 */         if ((this.bytes[3] & 0xFF) != 255) {
/* 589 */           throw new GSSException(10, -1, 
/* 590 */               Krb5Token.getTokenName(this.tokenId) + ":Defective Token Filler!");
/*     */         }
/*     */ 
/*     */         
/* 594 */         MessageToken_v2.this.ec = GSSToken.readBigEndian(this.bytes, 4, 2);
/*     */ 
/*     */         
/* 597 */         MessageToken_v2.this.rrc = GSSToken.readBigEndian(this.bytes, 6, 2);
/* 598 */       } else if (this.tokenId == 1028) {
/* 599 */         for (byte b1 = 3; b1 < 8; b1++) {
/* 600 */           if ((this.bytes[b1] & 0xFF) != 255) {
/* 601 */             throw new GSSException(10, -1, 
/* 602 */                 Krb5Token.getTokenName(this.tokenId) + ":Defective Token Filler!");
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 609 */       param1MessageProp.setQOP(0);
/*     */ 
/*     */       
/* 612 */       MessageToken_v2.this.seqNumber = GSSToken.readBigEndian(this.bytes, 0, 8);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void encode(OutputStream param1OutputStream) throws IOException {
/* 621 */       param1OutputStream.write(this.bytes);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int getTokenId() {
/* 632 */       return this.tokenId;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final byte[] getBytes() {
/* 640 */       return this.bytes;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\jgss\krb5\MessageToken_v2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */