/*     */ package com.sun.jmx.snmp.IPAcl;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import java.io.Serializable;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.security.Principal;
/*     */ import java.security.acl.Group;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NetMaskImpl
/*     */   extends PrincipalImpl
/*     */   implements Group, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7332541893877932896L;
/*  51 */   protected byte[] subnet = null;
/*  52 */   protected int prefix = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   public NetMaskImpl() throws UnknownHostException {}
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] extractSubNet(byte[] paramArrayOfbyte) {
/*  61 */     int i = paramArrayOfbyte.length;
/*  62 */     byte[] arrayOfByte = null;
/*  63 */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/*  64 */       JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", "BINARY ARRAY :");
/*     */       
/*  66 */       StringBuffer stringBuffer = new StringBuffer();
/*  67 */       for (byte b4 = 0; b4 < i; b4++) {
/*  68 */         stringBuffer.append((paramArrayOfbyte[b4] & 0xFF) + ":");
/*     */       }
/*  70 */       JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", stringBuffer
/*  71 */           .toString());
/*     */     } 
/*     */ 
/*     */     
/*  75 */     int j = this.prefix / 8;
/*  76 */     if (j == i) {
/*  77 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/*  78 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", "The mask is the complete address, strange..." + i);
/*     */       }
/*     */       
/*  81 */       arrayOfByte = paramArrayOfbyte;
/*  82 */       return arrayOfByte;
/*     */     } 
/*  84 */     if (j > i) {
/*  85 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/*  86 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", "The number of covered byte is longer than the address. BUG");
/*     */       }
/*     */       
/*  89 */       throw new IllegalArgumentException("The number of covered byte is longer than the address.");
/*     */     } 
/*  91 */     int k = j;
/*  92 */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/*  93 */       JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", "Partially covered index : " + k);
/*     */     }
/*     */     
/*  96 */     byte b = paramArrayOfbyte[k];
/*  97 */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/*  98 */       JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", "Partially covered byte : " + b);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 103 */     int m = this.prefix % 8;
/* 104 */     int n = 0;
/*     */     
/* 106 */     if (m == 0) {
/* 107 */       n = k;
/*     */     } else {
/* 109 */       n = k + 1;
/*     */     } 
/* 111 */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 112 */       JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", "Remains : " + m);
/*     */     }
/*     */ 
/*     */     
/* 116 */     byte b1 = 0; byte b2;
/* 117 */     for (b2 = 0; b2 < m; b2++) {
/* 118 */       b1 = (byte)(b1 | 1 << 7 - b2);
/*     */     }
/* 120 */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 121 */       JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", "Mask value : " + (b1 & 0xFF));
/*     */     }
/*     */ 
/*     */     
/* 125 */     b2 = (byte)(b & b1);
/*     */     
/* 127 */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 128 */       JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", "Masked byte : " + (b2 & 0xFF));
/*     */     }
/*     */     
/* 131 */     arrayOfByte = new byte[n];
/* 132 */     if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 133 */       JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", "Resulting subnet : ");
/*     */     }
/*     */     
/* 136 */     for (byte b3 = 0; b3 < k; b3++) {
/* 137 */       arrayOfByte[b3] = paramArrayOfbyte[b3];
/*     */       
/* 139 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 140 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", (arrayOfByte[b3] & 0xFF) + ":");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 145 */     if (m != 0) {
/* 146 */       arrayOfByte[k] = b2;
/* 147 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 148 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "extractSubNet", "Last subnet byte : " + (arrayOfByte[k] & 0xFF));
/*     */       }
/*     */     } 
/*     */     
/* 152 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NetMaskImpl(String paramString, int paramInt) throws UnknownHostException {
/* 162 */     super(paramString);
/* 163 */     this.prefix = paramInt;
/* 164 */     this.subnet = extractSubNet(getAddress().getAddress());
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
/*     */   public boolean addMember(Principal paramPrincipal) {
/* 176 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 180 */     return super.hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/* 192 */     if (paramObject instanceof PrincipalImpl || paramObject instanceof NetMaskImpl) {
/* 193 */       PrincipalImpl principalImpl = (PrincipalImpl)paramObject;
/* 194 */       InetAddress inetAddress = principalImpl.getAddress();
/* 195 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 196 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "equals", "Received Address : " + inetAddress);
/*     */       }
/*     */       
/* 199 */       byte[] arrayOfByte = inetAddress.getAddress();
/* 200 */       for (byte b = 0; b < this.subnet.length; b++) {
/* 201 */         if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 202 */           JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "equals", "(recAddr[i]) : " + (arrayOfByte[b] & 0xFF));
/*     */           
/* 204 */           JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "equals", "(recAddr[i] & subnet[i]) : " + (arrayOfByte[b] & this.subnet[b] & 0xFF) + " subnet[i] : " + (this.subnet[b] & 0xFF));
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 209 */         if ((arrayOfByte[b] & this.subnet[b]) != this.subnet[b]) {
/* 210 */           if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 211 */             JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "equals", "FALSE");
/*     */           }
/*     */           
/* 214 */           return false;
/*     */         } 
/*     */       } 
/* 217 */       if (JmxProperties.SNMP_LOGGER.isLoggable(Level.FINEST)) {
/* 218 */         JmxProperties.SNMP_LOGGER.logp(Level.FINEST, NetMaskImpl.class.getName(), "equals", "TRUE");
/*     */       }
/*     */       
/* 221 */       return true;
/*     */     } 
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMember(Principal paramPrincipal) {
/* 232 */     if ((paramPrincipal.hashCode() & super.hashCode()) == paramPrincipal.hashCode()) return true; 
/* 233 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<? extends Principal> members() {
/* 242 */     Vector<NetMaskImpl> vector = new Vector(1);
/* 243 */     vector.addElement(this);
/* 244 */     return vector.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeMember(Principal paramPrincipal) {
/* 254 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 263 */     return "NetMaskImpl :" + getAddress().toString() + "/" + this.prefix;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\IPAcl\NetMaskImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */