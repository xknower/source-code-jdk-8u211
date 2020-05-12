/*     */ package sun.management.snmp.jvminstr;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NotificationTargetImpl
/*     */   implements NotificationTarget
/*     */ {
/*     */   private InetAddress address;
/*     */   private int port;
/*     */   private String community;
/*     */   
/*     */   public NotificationTargetImpl(String paramString) throws IllegalArgumentException, UnknownHostException {
/*  48 */     parseTarget(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NotificationTargetImpl(String paramString1, int paramInt, String paramString2) throws UnknownHostException {
/*  54 */     this(InetAddress.getByName(paramString1), paramInt, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   public NotificationTargetImpl(InetAddress paramInetAddress, int paramInt, String paramString) {
/*  59 */     this.address = paramInetAddress;
/*  60 */     this.port = paramInt;
/*  61 */     this.community = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseTarget(String paramString) throws IllegalArgumentException, UnknownHostException {
/*     */     String str;
/*  67 */     if (paramString == null || paramString
/*  68 */       .length() == 0) throw new IllegalArgumentException("Invalid target [" + paramString + "]");
/*     */ 
/*     */ 
/*     */     
/*  72 */     if (paramString.startsWith("[")) {
/*  73 */       int j = paramString.indexOf("]");
/*  74 */       int k = paramString.lastIndexOf(":");
/*  75 */       if (j == -1) {
/*  76 */         throw new IllegalArgumentException("Host starts with [ but does not end with ]");
/*     */       }
/*  78 */       str = paramString.substring(1, j);
/*  79 */       this.port = Integer.parseInt(paramString.substring(j + 2, k));
/*     */       
/*  81 */       if (!isNumericIPv6Address(str)) {
/*  82 */         throw new IllegalArgumentException("Address inside [...] must be numeric IPv6 address");
/*     */       }
/*     */       
/*  85 */       if (str.startsWith("["))
/*  86 */         throw new IllegalArgumentException("More than one [[...]]"); 
/*     */     } else {
/*  88 */       int j = paramString.indexOf(":");
/*  89 */       int k = paramString.lastIndexOf(":");
/*  90 */       if (j == -1) throw new IllegalArgumentException("Missing port separator \":\"");
/*     */       
/*  92 */       str = paramString.substring(0, j);
/*     */       
/*  94 */       this.port = Integer.parseInt(paramString.substring(j + 1, k));
/*     */     } 
/*     */ 
/*     */     
/*  98 */     this.address = InetAddress.getByName(str);
/*     */ 
/*     */     
/* 101 */     int i = paramString.lastIndexOf(":");
/*     */     
/* 103 */     this.community = paramString.substring(i + 1, paramString.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isNumericIPv6Address(String paramString) {
/* 112 */     return (paramString.indexOf(':') >= 0);
/*     */   }
/*     */   
/*     */   public String getCommunity() {
/* 116 */     return this.community;
/*     */   }
/*     */   
/*     */   public InetAddress getAddress() {
/* 120 */     return this.address;
/*     */   }
/*     */   
/*     */   public int getPort() {
/* 124 */     return this.port;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 128 */     return "address : " + this.address + " port : " + this.port + " community : " + this.community;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\management\snmp\jvminstr\NotificationTargetImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */