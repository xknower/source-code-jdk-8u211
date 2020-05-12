/*     */ package com.sun.corba.se.spi.ior.iiop;
/*     */ 
/*     */ import com.sun.corba.se.spi.ior.IOR;
/*     */ import com.sun.corba.se.spi.orb.ORB;
/*     */ import com.sun.corba.se.spi.orb.ORBVersion;
/*     */ import com.sun.corba.se.spi.orb.ORBVersionFactory;
/*     */ import org.omg.CORBA.portable.InputStream;
/*     */ import org.omg.CORBA.portable.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GIOPVersion
/*     */ {
/*  41 */   public static final GIOPVersion V1_0 = new GIOPVersion((byte)1, (byte)0);
/*  42 */   public static final GIOPVersion V1_1 = new GIOPVersion((byte)1, (byte)1);
/*  43 */   public static final GIOPVersion V1_2 = new GIOPVersion((byte)1, (byte)2);
/*  44 */   public static final GIOPVersion V1_3 = new GIOPVersion((byte)1, (byte)3);
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final GIOPVersion V13_XX = new GIOPVersion((byte)13, (byte)1);
/*     */ 
/*     */   
/*  51 */   public static final GIOPVersion DEFAULT_VERSION = V1_2;
/*     */   
/*     */   public static final int VERSION_1_0 = 256;
/*     */   
/*     */   public static final int VERSION_1_1 = 257;
/*     */   
/*     */   public static final int VERSION_1_2 = 258;
/*     */   
/*     */   public static final int VERSION_1_3 = 259;
/*     */   
/*     */   public static final int VERSION_13_XX = 3329;
/*  62 */   private byte major = 0;
/*  63 */   private byte minor = 0;
/*     */ 
/*     */   
/*     */   public GIOPVersion() {}
/*     */ 
/*     */   
/*     */   public GIOPVersion(byte paramByte1, byte paramByte2) {
/*  70 */     this.major = paramByte1;
/*  71 */     this.minor = paramByte2;
/*     */   }
/*     */   
/*     */   public GIOPVersion(int paramInt1, int paramInt2) {
/*  75 */     this.major = (byte)paramInt1;
/*  76 */     this.minor = (byte)paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getMajor() {
/*  82 */     return this.major;
/*     */   }
/*     */   
/*     */   public byte getMinor() {
/*  86 */     return this.minor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(GIOPVersion paramGIOPVersion) {
/*  92 */     return (paramGIOPVersion.major == this.major && paramGIOPVersion.minor == this.minor);
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  96 */     if (paramObject != null && paramObject instanceof GIOPVersion) {
/*  97 */       return equals((GIOPVersion)paramObject);
/*     */     }
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 104 */     return 37 * this.major + this.minor;
/*     */   }
/*     */   
/*     */   public boolean lessThan(GIOPVersion paramGIOPVersion) {
/* 108 */     if (this.major < paramGIOPVersion.major)
/* 109 */       return true; 
/* 110 */     if (this.major == paramGIOPVersion.major && 
/* 111 */       this.minor < paramGIOPVersion.minor) {
/* 112 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int intValue() {
/* 121 */     return this.major << 8 | this.minor;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 126 */     return this.major + "." + this.minor;
/*     */   }
/*     */ 
/*     */   
/*     */   public static GIOPVersion getInstance(byte paramByte1, byte paramByte2) {
/* 131 */     switch (paramByte1 << 8 | paramByte2) {
/*     */       case 256:
/* 133 */         return V1_0;
/*     */       case 257:
/* 135 */         return V1_1;
/*     */       case 258:
/* 137 */         return V1_2;
/*     */       case 259:
/* 139 */         return V1_3;
/*     */       case 3329:
/* 141 */         return V13_XX;
/*     */     } 
/* 143 */     return new GIOPVersion(paramByte1, paramByte2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static GIOPVersion parseVersion(String paramString) {
/* 149 */     int i = paramString.indexOf('.');
/*     */     
/* 151 */     if (i < 1 || i == paramString.length() - 1) {
/* 152 */       throw new NumberFormatException("GIOP major, minor, and decimal point required: " + paramString);
/*     */     }
/* 154 */     int j = Integer.parseInt(paramString.substring(0, i));
/* 155 */     int k = Integer.parseInt(paramString.substring(i + 1, paramString.length()));
/*     */     
/* 157 */     return getInstance((byte)j, (byte)k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GIOPVersion chooseRequestVersion(ORB paramORB, IOR paramIOR) {
/* 168 */     GIOPVersion gIOPVersion1 = paramORB.getORBData().getGIOPVersion();
/* 169 */     IIOPProfile iIOPProfile = paramIOR.getProfile();
/* 170 */     GIOPVersion gIOPVersion2 = iIOPProfile.getGIOPVersion();
/*     */ 
/*     */ 
/*     */     
/* 174 */     ORBVersion oRBVersion = iIOPProfile.getORBVersion();
/* 175 */     if (!oRBVersion.equals(ORBVersionFactory.getFOREIGN()) && oRBVersion
/* 176 */       .lessThan(ORBVersionFactory.getNEWER()))
/*     */     {
/*     */       
/* 179 */       return V1_0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 184 */     byte b1 = gIOPVersion2.getMajor();
/* 185 */     byte b2 = gIOPVersion2.getMinor();
/*     */     
/* 187 */     byte b3 = gIOPVersion1.getMajor();
/* 188 */     byte b4 = gIOPVersion1.getMinor();
/*     */     
/* 190 */     if (b3 < b1)
/* 191 */       return gIOPVersion1; 
/* 192 */     if (b3 > b1) {
/* 193 */       return gIOPVersion2;
/*     */     }
/* 195 */     if (b4 <= b2) {
/* 196 */       return gIOPVersion1;
/*     */     }
/* 198 */     return gIOPVersion2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsIORIIOPProfileComponents() {
/* 205 */     return (getMinor() > 0 || getMajor() > 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void read(InputStream paramInputStream) {
/* 211 */     this.major = paramInputStream.read_octet();
/* 212 */     this.minor = paramInputStream.read_octet();
/*     */   }
/*     */   
/*     */   public void write(OutputStream paramOutputStream) {
/* 216 */     paramOutputStream.write_octet(this.major);
/* 217 */     paramOutputStream.write_octet(this.minor);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\spi\ior\iiop\GIOPVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */