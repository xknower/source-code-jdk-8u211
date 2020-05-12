/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Vector;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.KrbException;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.internal.ccache.CCacheOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HostAddresses
/*     */   implements Cloneable
/*     */ {
/*  69 */   private static boolean DEBUG = Krb5.DEBUG;
/*  70 */   private HostAddress[] addresses = null;
/*  71 */   private volatile int hashCode = 0;
/*     */   
/*     */   public HostAddresses(HostAddress[] paramArrayOfHostAddress) throws IOException {
/*  74 */     if (paramArrayOfHostAddress != null) {
/*  75 */       this.addresses = new HostAddress[paramArrayOfHostAddress.length];
/*  76 */       for (byte b = 0; b < paramArrayOfHostAddress.length; b++) {
/*  77 */         if (paramArrayOfHostAddress[b] == null) {
/*  78 */           throw new IOException("Cannot create a HostAddress");
/*     */         }
/*  80 */         this.addresses[b] = (HostAddress)paramArrayOfHostAddress[b].clone();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public HostAddresses() throws UnknownHostException {
/*  87 */     this.addresses = new HostAddress[1];
/*  88 */     this.addresses[0] = new HostAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   private HostAddresses(int paramInt) {}
/*     */ 
/*     */   
/*     */   public HostAddresses(PrincipalName paramPrincipalName) throws UnknownHostException, KrbException {
/*  96 */     String[] arrayOfString = paramPrincipalName.getNameStrings();
/*     */     
/*  98 */     if (paramPrincipalName.getNameType() != 3 || arrayOfString.length < 2)
/*     */     {
/* 100 */       throw new KrbException(60, "Bad name");
/*     */     }
/* 102 */     String str = arrayOfString[1];
/* 103 */     InetAddress[] arrayOfInetAddress = InetAddress.getAllByName(str);
/* 104 */     HostAddress[] arrayOfHostAddress = new HostAddress[arrayOfInetAddress.length];
/*     */     
/* 106 */     for (byte b = 0; b < arrayOfInetAddress.length; b++) {
/* 107 */       arrayOfHostAddress[b] = new HostAddress(arrayOfInetAddress[b]);
/*     */     }
/*     */     
/* 110 */     this.addresses = arrayOfHostAddress;
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 114 */     HostAddresses hostAddresses = new HostAddresses(0);
/* 115 */     if (this.addresses != null) {
/* 116 */       hostAddresses.addresses = new HostAddress[this.addresses.length];
/* 117 */       for (byte b = 0; b < this.addresses.length; b++) {
/* 118 */         hostAddresses.addresses[b] = (HostAddress)this.addresses[b]
/* 119 */           .clone();
/*     */       }
/*     */     } 
/* 122 */     return hostAddresses;
/*     */   }
/*     */   
/*     */   public boolean inList(HostAddress paramHostAddress) {
/* 126 */     if (this.addresses != null)
/* 127 */       for (byte b = 0; b < this.addresses.length; b++) {
/* 128 */         if (this.addresses[b].equals(paramHostAddress))
/* 129 */           return true; 
/*     */       }  
/* 131 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 135 */     if (this.hashCode == 0) {
/* 136 */       int i = 17;
/* 137 */       if (this.addresses != null) {
/* 138 */         for (byte b = 0; b < this.addresses.length; b++) {
/* 139 */           i = 37 * i + this.addresses[b].hashCode();
/*     */         }
/*     */       }
/* 142 */       this.hashCode = i;
/*     */     } 
/* 144 */     return this.hashCode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 150 */     if (this == paramObject) {
/* 151 */       return true;
/*     */     }
/*     */     
/* 154 */     if (!(paramObject instanceof HostAddresses)) {
/* 155 */       return false;
/*     */     }
/*     */     
/* 158 */     HostAddresses hostAddresses = (HostAddresses)paramObject;
/* 159 */     if ((this.addresses == null && hostAddresses.addresses != null) || (this.addresses != null && hostAddresses.addresses == null))
/*     */     {
/* 161 */       return false; } 
/* 162 */     if (this.addresses != null && hostAddresses.addresses != null) {
/* 163 */       if (this.addresses.length != hostAddresses.addresses.length)
/* 164 */         return false; 
/* 165 */       for (byte b = 0; b < this.addresses.length; b++) {
/* 166 */         if (!this.addresses[b].equals(hostAddresses.addresses[b]))
/* 167 */           return false; 
/*     */       } 
/* 169 */     }  return true;
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
/*     */   public HostAddresses(DerValue paramDerValue) throws Asn1Exception, IOException {
/* 182 */     Vector<HostAddress> vector = new Vector();
/* 183 */     DerValue derValue = null;
/* 184 */     while (paramDerValue.getData().available() > 0) {
/* 185 */       derValue = paramDerValue.getData().getDerValue();
/* 186 */       vector.addElement(new HostAddress(derValue));
/*     */     } 
/* 188 */     if (vector.size() > 0) {
/* 189 */       this.addresses = new HostAddress[vector.size()];
/* 190 */       vector.copyInto((Object[])this.addresses);
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
/*     */   public byte[] asn1Encode() throws Asn1Exception, IOException {
/* 204 */     DerOutputStream derOutputStream1 = new DerOutputStream();
/* 205 */     DerOutputStream derOutputStream2 = new DerOutputStream();
/*     */     
/* 207 */     if (this.addresses != null && this.addresses.length > 0)
/* 208 */       for (byte b = 0; b < this.addresses.length; b++) {
/* 209 */         derOutputStream1.write(this.addresses[b].asn1Encode());
/*     */       } 
/* 211 */     derOutputStream2.write((byte)48, derOutputStream1);
/* 212 */     return derOutputStream2.toByteArray();
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
/*     */   public static HostAddresses parse(DerInputStream paramDerInputStream, byte paramByte, boolean paramBoolean) throws Asn1Exception, IOException {
/* 231 */     if (paramBoolean && (
/* 232 */       (byte)paramDerInputStream.peekByte() & 0x1F) != paramByte)
/* 233 */       return null; 
/* 234 */     DerValue derValue1 = paramDerInputStream.getDerValue();
/* 235 */     if (paramByte != (derValue1.getTag() & 0x1F)) {
/* 236 */       throw new Asn1Exception(906);
/*     */     }
/* 238 */     DerValue derValue2 = derValue1.getData().getDerValue();
/* 239 */     return new HostAddresses(derValue2);
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
/*     */   public void writeAddrs(CCacheOutputStream paramCCacheOutputStream) throws IOException {
/* 253 */     paramCCacheOutputStream.write32(this.addresses.length);
/* 254 */     for (byte b = 0; b < this.addresses.length; b++) {
/* 255 */       paramCCacheOutputStream.write16((this.addresses[b]).addrType);
/* 256 */       paramCCacheOutputStream.write32((this.addresses[b]).address.length);
/* 257 */       paramCCacheOutputStream.write((this.addresses[b]).address, 0, (this.addresses[b]).address.length);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress[] getInetAddresses() {
/* 265 */     if (this.addresses == null || this.addresses.length == 0) {
/* 266 */       return null;
/*     */     }
/* 268 */     ArrayList<InetAddress> arrayList = new ArrayList(this.addresses.length);
/*     */     
/* 270 */     for (byte b = 0; b < this.addresses.length; b++) {
/*     */       try {
/* 272 */         if ((this.addresses[b]).addrType == 2 || (this.addresses[b]).addrType == 24)
/*     */         {
/* 274 */           arrayList.add(this.addresses[b].getInetAddress());
/*     */         }
/* 276 */       } catch (UnknownHostException unknownHostException) {
/*     */         
/* 278 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 282 */     InetAddress[] arrayOfInetAddress = new InetAddress[arrayList.size()];
/* 283 */     return arrayList.<InetAddress>toArray(arrayOfInetAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HostAddresses getLocalAddresses() throws IOException {
/* 292 */     String str = null;
/* 293 */     InetAddress[] arrayOfInetAddress = null;
/*     */     try {
/* 295 */       InetAddress inetAddress = InetAddress.getLocalHost();
/* 296 */       str = inetAddress.getHostName();
/* 297 */       arrayOfInetAddress = InetAddress.getAllByName(str);
/* 298 */       HostAddress[] arrayOfHostAddress = new HostAddress[arrayOfInetAddress.length]; byte b;
/* 299 */       for (b = 0; b < arrayOfInetAddress.length; b++)
/*     */       {
/* 301 */         arrayOfHostAddress[b] = new HostAddress(arrayOfInetAddress[b]);
/*     */       }
/* 303 */       if (DEBUG) {
/* 304 */         System.out.println(">>> KrbKdcReq local addresses for " + str + " are: ");
/*     */ 
/*     */         
/* 307 */         for (b = 0; b < arrayOfInetAddress.length; b++) {
/* 308 */           System.out.println("\n\t" + arrayOfInetAddress[b]);
/* 309 */           if (arrayOfInetAddress[b] instanceof java.net.Inet4Address)
/* 310 */             System.out.println("IPv4 address"); 
/* 311 */           if (arrayOfInetAddress[b] instanceof java.net.Inet6Address)
/* 312 */             System.out.println("IPv6 address"); 
/*     */         } 
/*     */       } 
/* 315 */       return new HostAddresses(arrayOfHostAddress);
/* 316 */     } catch (Exception exception) {
/* 317 */       throw new IOException(exception.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HostAddresses(InetAddress[] paramArrayOfInetAddress) {
/* 328 */     if (paramArrayOfInetAddress == null) {
/*     */       
/* 330 */       this.addresses = null;
/*     */       
/*     */       return;
/*     */     } 
/* 334 */     this.addresses = new HostAddress[paramArrayOfInetAddress.length];
/* 335 */     for (byte b = 0; b < paramArrayOfInetAddress.length; b++)
/* 336 */       this.addresses[b] = new HostAddress(paramArrayOfInetAddress[b]); 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\HostAddresses.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */