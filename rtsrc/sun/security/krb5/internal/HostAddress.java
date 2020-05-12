/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.util.DerInputStream;
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
/*     */ 
/*     */ public class HostAddress
/*     */   implements Cloneable
/*     */ {
/*     */   int addrType;
/*  62 */   byte[] address = null;
/*     */   
/*     */   private static InetAddress localInetAddress;
/*  65 */   private static final boolean DEBUG = Krb5.DEBUG;
/*  66 */   private volatile int hashCode = 0;
/*     */   
/*     */   private HostAddress(int paramInt) {}
/*     */   
/*     */   public Object clone() {
/*  71 */     HostAddress hostAddress = new HostAddress(0);
/*  72 */     hostAddress.addrType = this.addrType;
/*  73 */     if (this.address != null) {
/*  74 */       hostAddress.address = (byte[])this.address.clone();
/*     */     }
/*  76 */     return hostAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  81 */     if (this.hashCode == 0) {
/*  82 */       int i = 17;
/*  83 */       i = 37 * i + this.addrType;
/*  84 */       if (this.address != null) {
/*  85 */         for (byte b = 0; b < this.address.length; b++) {
/*  86 */           i = 37 * i + this.address[b];
/*     */         }
/*     */       }
/*  89 */       this.hashCode = i;
/*     */     } 
/*  91 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  96 */     if (this == paramObject) {
/*  97 */       return true;
/*     */     }
/*     */     
/* 100 */     if (!(paramObject instanceof HostAddress)) {
/* 101 */       return false;
/*     */     }
/*     */     
/* 104 */     HostAddress hostAddress = (HostAddress)paramObject;
/* 105 */     if (this.addrType != hostAddress.addrType || (this.address != null && hostAddress.address == null) || (this.address == null && hostAddress.address != null))
/*     */     {
/*     */       
/* 108 */       return false; } 
/* 109 */     if (this.address != null && hostAddress.address != null) {
/* 110 */       if (this.address.length != hostAddress.address.length)
/* 111 */         return false; 
/* 112 */       for (byte b = 0; b < this.address.length; b++) {
/* 113 */         if (this.address[b] != hostAddress.address[b])
/* 114 */           return false; 
/*     */       } 
/* 116 */     }  return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized InetAddress getLocalInetAddress() throws UnknownHostException {
/* 122 */     if (localInetAddress == null) {
/* 123 */       localInetAddress = InetAddress.getLocalHost();
/*     */     }
/* 125 */     if (localInetAddress == null) {
/* 126 */       throw new UnknownHostException();
/*     */     }
/* 128 */     return localInetAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress getInetAddress() throws UnknownHostException {
/* 139 */     if (this.addrType == 2 || this.addrType == 24)
/*     */     {
/* 141 */       return InetAddress.getByAddress(this.address);
/*     */     }
/*     */     
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getAddrType(InetAddress paramInetAddress) {
/* 149 */     byte b = 0;
/* 150 */     if (paramInetAddress instanceof java.net.Inet4Address) {
/* 151 */       b = 2;
/* 152 */     } else if (paramInetAddress instanceof java.net.Inet6Address) {
/* 153 */       b = 24;
/* 154 */     }  return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public HostAddress() throws UnknownHostException {
/* 159 */     InetAddress inetAddress = getLocalInetAddress();
/* 160 */     this.addrType = getAddrType(inetAddress);
/* 161 */     this.address = inetAddress.getAddress();
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
/*     */   public HostAddress(int paramInt, byte[] paramArrayOfbyte) throws KrbApErrException, UnknownHostException {
/* 176 */     switch (paramInt) {
/*     */       case 2:
/* 178 */         if (paramArrayOfbyte.length != 4)
/* 179 */           throw new KrbApErrException(0, "Invalid Internet address"); 
/*     */         break;
/*     */       case 5:
/* 182 */         if (paramArrayOfbyte.length != 2) {
/* 183 */           throw new KrbApErrException(0, "Invalid CHAOSnet address");
/*     */         }
/*     */         break;
/*     */       
/*     */       case 6:
/* 188 */         if (paramArrayOfbyte.length != 6)
/* 189 */           throw new KrbApErrException(0, "Invalid XNS address"); 
/*     */         break;
/*     */       case 16:
/* 192 */         if (paramArrayOfbyte.length != 3)
/* 193 */           throw new KrbApErrException(0, "Invalid DDP address"); 
/*     */         break;
/*     */       case 12:
/* 196 */         if (paramArrayOfbyte.length != 2)
/* 197 */           throw new KrbApErrException(0, "Invalid DECnet Phase IV address"); 
/*     */         break;
/*     */       case 24:
/* 200 */         if (paramArrayOfbyte.length != 16) {
/* 201 */           throw new KrbApErrException(0, "Invalid Internet IPv6 address");
/*     */         }
/*     */         break;
/*     */     } 
/* 205 */     this.addrType = paramInt;
/* 206 */     if (paramArrayOfbyte != null) {
/* 207 */       this.address = (byte[])paramArrayOfbyte.clone();
/*     */     }
/* 209 */     if (DEBUG && (
/* 210 */       this.addrType == 2 || this.addrType == 24))
/*     */     {
/* 212 */       System.out.println("Host address is " + 
/* 213 */           InetAddress.getByAddress(this.address));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public HostAddress(InetAddress paramInetAddress) {
/* 219 */     this.addrType = getAddrType(paramInetAddress);
/* 220 */     this.address = paramInetAddress.getAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HostAddress(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 231 */     DerValue derValue = paramDerValue.getData().getDerValue();
/* 232 */     if ((derValue.getTag() & 0x1F) == 0) {
/* 233 */       this.addrType = derValue.getData().getBigInteger().intValue();
/*     */     } else {
/*     */       
/* 236 */       throw new Asn1Exception(906);
/* 237 */     }  derValue = paramDerValue.getData().getDerValue();
/* 238 */     if ((derValue.getTag() & 0x1F) == 1) {
/* 239 */       this.address = derValue.getData().getOctetString();
/*     */     } else {
/*     */       
/* 242 */       throw new Asn1Exception(906);
/* 243 */     }  if (paramDerValue.getData().available() > 0) {
/* 244 */       throw new Asn1Exception(906);
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 256 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 257 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/* 258 */     derOutputStream2.putInteger(this.addrType);
/* 259 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)0), derOutputStream2);
/* 260 */     derOutputStream2 = new DerOutputStream();
/* 261 */     derOutputStream2.putOctetString(this.address);
/* 262 */     derOutputStream1.write(DerValue.createTag(-128, true, (byte)1), derOutputStream2);
/* 263 */     derOutputStream2 = new DerOutputStream();
/* 264 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 265 */     return derOutputStream2.toByteArray();
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
/*     */   public static HostAddress parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 284 */     if (paramBoolean && (
/* 285 */       (byte)paramDerInputStream.peekByte() & 0x1F) != paramByte) {
/* 286 */       return null;
/*     */     }
/* 288 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 289 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 290 */       throw new Asn1Exception(906);
/*     */     }
/*     */     
/* 293 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 294 */     return new HostAddress(derValue2);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\HostAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */