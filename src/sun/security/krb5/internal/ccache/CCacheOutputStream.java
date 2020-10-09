/*     */ package sun.security.krb5.internal.ccache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import sun.security.krb5.Asn1Exception;
/*     */ import sun.security.krb5.PrincipalName;
/*     */ import sun.security.krb5.internal.Ticket;
/*     */ import sun.security.krb5.internal.TicketFlags;
/*     */ import sun.security.krb5.internal.util.KrbDataOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CCacheOutputStream
/*     */   extends KrbDataOutputStream
/*     */   implements FileCCacheConstants
/*     */ {
/*     */   public CCacheOutputStream(OutputStream paramOutputStream) {
/*  48 */     super(paramOutputStream);
/*     */   }
/*     */   
/*     */   public void writeHeader(PrincipalName paramPrincipalName, int paramInt) throws IOException {
/*  52 */     write((paramInt & 0xFF00) >> 8);
/*  53 */     write(paramInt & 0xFF);
/*  54 */     paramPrincipalName.writePrincipal(this);
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
/*     */   public void addCreds(Credentials paramCredentials) throws IOException, Asn1Exception {
/*  70 */     paramCredentials.cname.writePrincipal(this);
/*  71 */     paramCredentials.sname.writePrincipal(this);
/*  72 */     paramCredentials.key.writeKey(this);
/*  73 */     write32((int)(paramCredentials.authtime.getTime() / 1000L));
/*  74 */     if (paramCredentials.starttime != null)
/*  75 */     { write32((int)(paramCredentials.starttime.getTime() / 1000L)); }
/*  76 */     else { write32(0); }
/*  77 */      write32((int)(paramCredentials.endtime.getTime() / 1000L));
/*  78 */     if (paramCredentials.renewTill != null) {
/*  79 */       write32((int)(paramCredentials.renewTill.getTime() / 1000L));
/*     */     } else {
/*  81 */       write32(0);
/*  82 */     }  if (paramCredentials.isEncInSKey) {
/*  83 */       write8(1);
/*     */     } else {
/*  85 */       write8(0);
/*  86 */     }  writeFlags(paramCredentials.flags);
/*  87 */     if (paramCredentials.caddr == null) {
/*  88 */       write32(0);
/*     */     } else {
/*  90 */       paramCredentials.caddr.writeAddrs(this);
/*     */     } 
/*  92 */     if (paramCredentials.authorizationData == null) {
/*  93 */       write32(0);
/*     */     } else {
/*     */       
/*  96 */       paramCredentials.authorizationData.writeAuth(this);
/*  97 */     }  writeTicket(paramCredentials.ticket);
/*  98 */     writeTicket(paramCredentials.secondTicket);
/*     */   }
/*     */   
/*     */   void writeTicket(Ticket paramTicket) throws IOException, Asn1Exception {
/* 102 */     if (paramTicket == null) {
/* 103 */       write32(0);
/*     */     } else {
/*     */       
/* 106 */       byte[] arrayOfByte = paramTicket.asn1Encode();
/* 107 */       write32(arrayOfByte.length);
/* 108 */       write(arrayOfByte, 0, arrayOfByte.length);
/*     */     } 
/*     */   }
/*     */   
/*     */   void writeFlags(TicketFlags paramTicketFlags) throws IOException {
/* 113 */     int i = 0;
/* 114 */     boolean[] arrayOfBoolean = paramTicketFlags.toBooleanArray();
/* 115 */     if (arrayOfBoolean[1] == true) {
/* 116 */       i |= 0x40000000;
/*     */     }
/* 118 */     if (arrayOfBoolean[2] == true) {
/* 119 */       i |= 0x20000000;
/*     */     }
/* 121 */     if (arrayOfBoolean[3] == true) {
/* 122 */       i |= 0x10000000;
/*     */     }
/* 124 */     if (arrayOfBoolean[4] == true) {
/* 125 */       i |= 0x8000000;
/*     */     }
/* 127 */     if (arrayOfBoolean[5] == true) {
/* 128 */       i |= 0x4000000;
/*     */     }
/* 130 */     if (arrayOfBoolean[6] == true) {
/* 131 */       i |= 0x2000000;
/*     */     }
/* 133 */     if (arrayOfBoolean[7] == true) {
/* 134 */       i |= 0x1000000;
/*     */     }
/* 136 */     if (arrayOfBoolean[8] == true) {
/* 137 */       i |= 0x800000;
/*     */     }
/* 139 */     if (arrayOfBoolean[9] == true) {
/* 140 */       i |= 0x400000;
/*     */     }
/* 142 */     if (arrayOfBoolean[10] == true) {
/* 143 */       i |= 0x200000;
/*     */     }
/* 145 */     if (arrayOfBoolean[11] == true) {
/* 146 */       i |= 0x100000;
/*     */     }
/* 148 */     write32(i);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\internal\ccache\CCacheOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */