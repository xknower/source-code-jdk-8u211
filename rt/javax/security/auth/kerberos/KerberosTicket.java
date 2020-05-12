/*     */ package javax.security.auth.kerberos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.Destroyable;
/*     */ import javax.security.auth.RefreshFailedException;
/*     */ import javax.security.auth.Refreshable;
/*     */ import sun.misc.HexDumpEncoder;
/*     */ import sun.security.krb5.Credentials;
/*     */ import sun.security.krb5.KrbException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class KerberosTicket
/*     */   implements Destroyable, Refreshable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7395334370157380539L;
/*     */   private static final int FORWARDABLE_TICKET_FLAG = 1;
/*     */   private static final int FORWARDED_TICKET_FLAG = 2;
/*     */   private static final int PROXIABLE_TICKET_FLAG = 3;
/*     */   private static final int PROXY_TICKET_FLAG = 4;
/*     */   private static final int POSTDATED_TICKET_FLAG = 6;
/*     */   private static final int RENEWABLE_TICKET_FLAG = 8;
/*     */   private static final int INITIAL_TICKET_FLAG = 9;
/*     */   private static final int NUM_FLAGS = 32;
/*     */   private byte[] asn1Encoding;
/*     */   private KeyImpl sessionKey;
/*     */   private boolean[] flags;
/*     */   private Date authTime;
/*     */   private Date startTime;
/*     */   private Date endTime;
/*     */   private Date renewTill;
/*     */   private KerberosPrincipal client;
/*     */   private KerberosPrincipal server;
/*     */   private InetAddress[] clientAddresses;
/*     */   private transient boolean destroyed = false;
/*     */   
/*     */   public KerberosTicket(byte[] paramArrayOfbyte1, KerberosPrincipal paramKerberosPrincipal1, KerberosPrincipal paramKerberosPrincipal2, byte[] paramArrayOfbyte2, int paramInt, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) {
/* 241 */     init(paramArrayOfbyte1, paramKerberosPrincipal1, paramKerberosPrincipal2, paramArrayOfbyte2, paramInt, paramArrayOfboolean, paramDate1, paramDate2, paramDate3, paramDate4, paramArrayOfInetAddress);
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
/*     */   private void init(byte[] paramArrayOfbyte1, KerberosPrincipal paramKerberosPrincipal1, KerberosPrincipal paramKerberosPrincipal2, byte[] paramArrayOfbyte2, int paramInt, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) {
/* 256 */     if (paramArrayOfbyte2 == null) {
/* 257 */       throw new IllegalArgumentException("Session key for ticket cannot be null");
/*     */     }
/* 259 */     init(paramArrayOfbyte1, paramKerberosPrincipal1, paramKerberosPrincipal2, new KeyImpl(paramArrayOfbyte2, paramInt), paramArrayOfboolean, paramDate1, paramDate2, paramDate3, paramDate4, paramArrayOfInetAddress);
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
/*     */   private void init(byte[] paramArrayOfbyte, KerberosPrincipal paramKerberosPrincipal1, KerberosPrincipal paramKerberosPrincipal2, KeyImpl paramKeyImpl, boolean[] paramArrayOfboolean, Date paramDate1, Date paramDate2, Date paramDate3, Date paramDate4, InetAddress[] paramArrayOfInetAddress) {
/* 274 */     if (paramArrayOfbyte == null) {
/* 275 */       throw new IllegalArgumentException("ASN.1 encoding of ticket cannot be null");
/*     */     }
/* 277 */     this.asn1Encoding = (byte[])paramArrayOfbyte.clone();
/*     */     
/* 279 */     if (paramKerberosPrincipal1 == null) {
/* 280 */       throw new IllegalArgumentException("Client name in ticket cannot be null");
/*     */     }
/* 282 */     this.client = paramKerberosPrincipal1;
/*     */     
/* 284 */     if (paramKerberosPrincipal2 == null) {
/* 285 */       throw new IllegalArgumentException("Server name in ticket cannot be null");
/*     */     }
/* 287 */     this.server = paramKerberosPrincipal2;
/*     */ 
/*     */     
/* 290 */     this.sessionKey = paramKeyImpl;
/*     */     
/* 292 */     if (paramArrayOfboolean != null) {
/* 293 */       if (paramArrayOfboolean.length >= 32) {
/* 294 */         this.flags = (boolean[])paramArrayOfboolean.clone();
/*     */       } else {
/* 296 */         this.flags = new boolean[32];
/*     */         
/* 298 */         for (byte b = 0; b < paramArrayOfboolean.length; b++)
/* 299 */           this.flags[b] = paramArrayOfboolean[b]; 
/*     */       } 
/*     */     } else {
/* 302 */       this.flags = new boolean[32];
/*     */     } 
/* 304 */     if (this.flags[8]) {
/* 305 */       if (paramDate4 == null) {
/* 306 */         throw new IllegalArgumentException("The renewable period end time cannot be null for renewable tickets.");
/*     */       }
/*     */       
/* 309 */       this.renewTill = new Date(paramDate4.getTime());
/*     */     } 
/*     */     
/* 312 */     if (paramDate1 != null) {
/* 313 */       this.authTime = new Date(paramDate1.getTime());
/*     */     }
/* 315 */     if (paramDate2 != null) {
/* 316 */       this.startTime = new Date(paramDate2.getTime());
/*     */     } else {
/* 318 */       this.startTime = this.authTime;
/*     */     } 
/*     */     
/* 321 */     if (paramDate3 == null) {
/* 322 */       throw new IllegalArgumentException("End time for ticket validity cannot be null");
/*     */     }
/* 324 */     this.endTime = new Date(paramDate3.getTime());
/*     */     
/* 326 */     if (paramArrayOfInetAddress != null) {
/* 327 */       this.clientAddresses = (InetAddress[])paramArrayOfInetAddress.clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final KerberosPrincipal getClient() {
/* 336 */     return this.client;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final KerberosPrincipal getServer() {
/* 345 */     return this.server;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final SecretKey getSessionKey() {
/* 354 */     if (this.destroyed)
/* 355 */       throw new IllegalStateException("This ticket is no longer valid"); 
/* 356 */     return this.sessionKey;
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
/*     */   public final int getSessionKeyType() {
/* 369 */     if (this.destroyed)
/* 370 */       throw new IllegalStateException("This ticket is no longer valid"); 
/* 371 */     return this.sessionKey.getKeyType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isForwardable() {
/* 380 */     return (this.flags == null) ? false : this.flags[1];
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
/*     */   public final boolean isForwarded() {
/* 392 */     return (this.flags == null) ? false : this.flags[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isProxiable() {
/* 401 */     return (this.flags == null) ? false : this.flags[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isProxy() {
/* 410 */     return (this.flags == null) ? false : this.flags[4];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isPostdated() {
/* 420 */     return (this.flags == null) ? false : this.flags[6];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isRenewable() {
/* 431 */     return (this.flags == null) ? false : this.flags[8];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isInitial() {
/* 442 */     return (this.flags == null) ? false : this.flags[9];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean[] getFlags() {
/* 453 */     return (this.flags == null) ? null : (boolean[])this.flags.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Date getAuthTime() {
/* 463 */     return (this.authTime == null) ? null : (Date)this.authTime.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Date getStartTime() {
/* 473 */     return (this.startTime == null) ? null : (Date)this.startTime.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Date getEndTime() {
/* 482 */     return (this.endTime == null) ? null : (Date)this.endTime.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Date getRenewTill() {
/* 492 */     return (this.renewTill == null) ? null : (Date)this.renewTill.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final InetAddress[] getClientAddresses() {
/* 502 */     return (this.clientAddresses == null) ? null : (InetAddress[])this.clientAddresses.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getEncoded() {
/* 511 */     if (this.destroyed)
/* 512 */       throw new IllegalStateException("This ticket is no longer valid"); 
/* 513 */     return (byte[])this.asn1Encoding.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCurrent() {
/* 518 */     return (this.endTime == null) ? false : ((System.currentTimeMillis() <= this.endTime.getTime()));
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
/*     */   public void refresh() throws RefreshFailedException {
/*     */     IOException iOException;
/* 542 */     if (this.destroyed) {
/* 543 */       throw new RefreshFailedException("A destroyed ticket cannot be renewd.");
/*     */     }
/*     */     
/* 546 */     if (!isRenewable()) {
/* 547 */       throw new RefreshFailedException("This ticket is not renewable");
/*     */     }
/* 549 */     if (System.currentTimeMillis() > getRenewTill().getTime()) {
/* 550 */       throw new RefreshFailedException("This ticket is past its last renewal time.");
/*     */     }
/* 552 */     KrbException krbException = null;
/* 553 */     Credentials credentials = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 560 */       credentials = new Credentials(this.asn1Encoding, this.client.toString(), this.server.toString(), this.sessionKey.getEncoded(), this.sessionKey.getKeyType(), this.flags, this.authTime, this.startTime, this.endTime, this.renewTill, this.clientAddresses);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 567 */       credentials = credentials.renew();
/* 568 */     } catch (KrbException krbException1) {
/* 569 */       krbException = krbException1;
/* 570 */     } catch (IOException iOException1) {
/* 571 */       iOException = iOException1;
/*     */     } 
/*     */     
/* 574 */     if (iOException != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 579 */       RefreshFailedException refreshFailedException = new RefreshFailedException("Failed to renew Kerberos Ticket for client " + this.client + " and server " + this.server + " - " + iOException.getMessage());
/* 580 */       refreshFailedException.initCause(iOException);
/* 581 */       throw refreshFailedException;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 587 */     synchronized (this) {
/*     */       try {
/* 589 */         destroy();
/* 590 */       } catch (DestroyFailedException destroyFailedException) {}
/*     */ 
/*     */       
/* 593 */       init(credentials.getEncoded(), new KerberosPrincipal(credentials
/* 594 */             .getClient().getName()), new KerberosPrincipal(credentials
/* 595 */             .getServer().getName(), 2), credentials
/*     */           
/* 597 */           .getSessionKey().getBytes(), credentials
/* 598 */           .getSessionKey().getEType(), credentials
/* 599 */           .getFlags(), credentials
/* 600 */           .getAuthTime(), credentials
/* 601 */           .getStartTime(), credentials
/* 602 */           .getEndTime(), credentials
/* 603 */           .getRenewTill(), credentials
/* 604 */           .getClientAddresses());
/* 605 */       this.destroyed = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() throws DestroyFailedException {
/* 614 */     if (!this.destroyed) {
/* 615 */       Arrays.fill(this.asn1Encoding, (byte)0);
/* 616 */       this.client = null;
/* 617 */       this.server = null;
/* 618 */       this.sessionKey.destroy();
/* 619 */       this.flags = null;
/* 620 */       this.authTime = null;
/* 621 */       this.startTime = null;
/* 622 */       this.endTime = null;
/* 623 */       this.renewTill = null;
/* 624 */       this.clientAddresses = null;
/* 625 */       this.destroyed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDestroyed() {
/* 633 */     return this.destroyed;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 637 */     if (this.destroyed) {
/* 638 */       return "Destroyed KerberosTicket";
/*     */     }
/* 640 */     StringBuffer stringBuffer = new StringBuffer();
/* 641 */     if (this.clientAddresses != null) {
/* 642 */       for (byte b = 0; b < this.clientAddresses.length; b++) {
/* 643 */         stringBuffer.append("clientAddresses[" + b + "] = " + this.clientAddresses[b]
/* 644 */             .toString());
/*     */       }
/*     */     }
/* 647 */     return "Ticket (hex) = \n" + (new HexDumpEncoder())
/* 648 */       .encodeBuffer(this.asn1Encoding) + "\nClient Principal = " + this.client
/* 649 */       .toString() + "\nServer Principal = " + this.server
/* 650 */       .toString() + "\nSession Key = " + this.sessionKey
/* 651 */       .toString() + "\nForwardable Ticket " + this.flags[1] + "\nForwarded Ticket " + this.flags[2] + "\nProxiable Ticket " + this.flags[3] + "\nProxy Ticket " + this.flags[4] + "\nPostdated Ticket " + this.flags[6] + "\nRenewable Ticket " + this.flags[8] + "\nInitial Ticket " + this.flags[8] + "\nAuth Time = " + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 659 */       String.valueOf(this.authTime) + "\nStart Time = " + 
/* 660 */       String.valueOf(this.startTime) + "\nEnd Time = " + this.endTime
/* 661 */       .toString() + "\nRenew Till = " + 
/* 662 */       String.valueOf(this.renewTill) + "\nClient Addresses " + ((this.clientAddresses == null) ? " Null " : (stringBuffer
/*     */       
/* 664 */       .toString() + "\n"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 675 */     int i = 17;
/* 676 */     if (isDestroyed()) {
/* 677 */       return i;
/*     */     }
/* 679 */     i = i * 37 + Arrays.hashCode(getEncoded());
/* 680 */     i = i * 37 + this.endTime.hashCode();
/* 681 */     i = i * 37 + this.client.hashCode();
/* 682 */     i = i * 37 + this.server.hashCode();
/* 683 */     i = i * 37 + this.sessionKey.hashCode();
/*     */ 
/*     */     
/* 686 */     if (this.authTime != null) {
/* 687 */       i = i * 37 + this.authTime.hashCode();
/*     */     }
/*     */ 
/*     */     
/* 691 */     if (this.startTime != null) {
/* 692 */       i = i * 37 + this.startTime.hashCode();
/*     */     }
/*     */ 
/*     */     
/* 696 */     if (this.renewTill != null) {
/* 697 */       i = i * 37 + this.renewTill.hashCode();
/*     */     }
/*     */ 
/*     */     
/* 701 */     i = i * 37 + Arrays.hashCode((Object[])this.clientAddresses);
/* 702 */     return i * 37 + Arrays.hashCode(this.flags);
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
/*     */   public boolean equals(Object paramObject) {
/* 719 */     if (paramObject == this) {
/* 720 */       return true;
/*     */     }
/* 722 */     if (!(paramObject instanceof KerberosTicket)) {
/* 723 */       return false;
/*     */     }
/*     */     
/* 726 */     KerberosTicket kerberosTicket = (KerberosTicket)paramObject;
/* 727 */     if (isDestroyed() || kerberosTicket.isDestroyed()) {
/* 728 */       return false;
/*     */     }
/*     */     
/* 731 */     if (!Arrays.equals(getEncoded(), kerberosTicket.getEncoded()) || 
/* 732 */       !this.endTime.equals(kerberosTicket.getEndTime()) || 
/* 733 */       !this.server.equals(kerberosTicket.getServer()) || 
/* 734 */       !this.client.equals(kerberosTicket.getClient()) || 
/* 735 */       !this.sessionKey.equals(kerberosTicket.getSessionKey()) || 
/* 736 */       !Arrays.equals((Object[])this.clientAddresses, (Object[])kerberosTicket.getClientAddresses()) || 
/* 737 */       !Arrays.equals(this.flags, kerberosTicket.getFlags())) {
/* 738 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 742 */     if (this.authTime == null) {
/* 743 */       if (kerberosTicket.getAuthTime() != null) {
/* 744 */         return false;
/*     */       }
/* 746 */     } else if (!this.authTime.equals(kerberosTicket.getAuthTime())) {
/* 747 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 751 */     if (this.startTime == null) {
/* 752 */       if (kerberosTicket.getStartTime() != null) {
/* 753 */         return false;
/*     */       }
/* 755 */     } else if (!this.startTime.equals(kerberosTicket.getStartTime())) {
/* 756 */       return false;
/*     */     } 
/*     */     
/* 759 */     if (this.renewTill == null) {
/* 760 */       if (kerberosTicket.getRenewTill() != null) {
/* 761 */         return false;
/*     */       }
/* 763 */     } else if (!this.renewTill.equals(kerberosTicket.getRenewTill())) {
/* 764 */       return false;
/*     */     } 
/*     */     
/* 767 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 772 */     paramObjectInputStream.defaultReadObject();
/* 773 */     if (this.sessionKey == null) {
/* 774 */       throw new InvalidObjectException("Session key cannot be null");
/*     */     }
/*     */     try {
/* 777 */       init(this.asn1Encoding, this.client, this.server, this.sessionKey, this.flags, this.authTime, this.startTime, this.endTime, this.renewTill, this.clientAddresses);
/*     */     
/*     */     }
/* 780 */     catch (IllegalArgumentException illegalArgumentException) {
/* 781 */       throw (InvalidObjectException)(new InvalidObjectException(illegalArgumentException
/* 782 */           .getMessage())).initCause(illegalArgumentException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\security\auth\kerberos\KerberosTicket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */