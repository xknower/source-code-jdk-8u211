/*      */ package sun.security.jgss.krb5;
/*      */ 
/*      */ import com.sun.security.jgss.AuthorizationDataEntry;
/*      */ import com.sun.security.jgss.InquireType;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.Key;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.Provider;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.kerberos.KerberosTicket;
/*      */ import javax.security.auth.kerberos.ServicePermission;
/*      */ import org.ietf.jgss.ChannelBinding;
/*      */ import org.ietf.jgss.GSSException;
/*      */ import org.ietf.jgss.MessageProp;
/*      */ import org.ietf.jgss.Oid;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.jgss.GSSCaller;
/*      */ import sun.security.jgss.GSSUtil;
/*      */ import sun.security.jgss.TokenTracker;
/*      */ import sun.security.jgss.spi.GSSContextSpi;
/*      */ import sun.security.jgss.spi.GSSCredentialSpi;
/*      */ import sun.security.jgss.spi.GSSNameSpi;
/*      */ import sun.security.krb5.Credentials;
/*      */ import sun.security.krb5.EncryptionKey;
/*      */ import sun.security.krb5.KrbApReq;
/*      */ import sun.security.krb5.KrbException;
/*      */ import sun.security.krb5.PrincipalName;
/*      */ import sun.security.krb5.internal.Ticket;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class Krb5Context
/*      */   implements GSSContextSpi
/*      */ {
/*      */   private static final int STATE_NEW = 1;
/*      */   private static final int STATE_IN_PROCESS = 2;
/*      */   private static final int STATE_DONE = 3;
/*      */   private static final int STATE_DELETED = 4;
/*   69 */   private int state = 1;
/*      */   
/*      */   public static final int SESSION_KEY = 0;
/*      */   
/*      */   public static final int INITIATOR_SUBKEY = 1;
/*      */   
/*      */   public static final int ACCEPTOR_SUBKEY = 2;
/*      */   
/*      */   private boolean credDelegState = false;
/*      */   
/*      */   private boolean mutualAuthState = true;
/*      */   
/*      */   private boolean replayDetState = true;
/*      */   
/*      */   private boolean sequenceDetState = true;
/*      */   
/*      */   private boolean confState = true;
/*      */   
/*      */   private boolean integState = true;
/*      */   
/*      */   private boolean delegPolicyState = false;
/*      */   private boolean isConstrainedDelegationTried = false;
/*      */   private int mySeqNumber;
/*      */   private int peerSeqNumber;
/*      */   private int keySrc;
/*      */   private TokenTracker peerTokenTracker;
/*   95 */   private CipherHelper cipherHelper = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   private Object mySeqNumberLock = new Object();
/*  107 */   private Object peerSeqNumberLock = new Object();
/*      */   
/*      */   private EncryptionKey key;
/*      */   
/*      */   private Krb5NameElement myName;
/*      */   
/*      */   private Krb5NameElement peerName;
/*      */   
/*      */   private int lifetime;
/*      */   
/*      */   private boolean initiator;
/*      */   private ChannelBinding channelBinding;
/*      */   private Krb5CredElement myCred;
/*      */   private Krb5CredElement delegatedCred;
/*      */   private Credentials serviceCreds;
/*      */   private KrbApReq apReq;
/*      */   Ticket serviceTicket;
/*      */   private final GSSCaller caller;
/*  125 */   private static final boolean DEBUG = Krb5Util.DEBUG;
/*      */   
/*      */   private boolean[] tktFlags;
/*      */   
/*      */   private String authTime;
/*      */   
/*      */   private AuthorizationDataEntry[] authzData;
/*      */ 
/*      */   
/*      */   Krb5Context(GSSCaller paramGSSCaller, Krb5NameElement paramKrb5NameElement, Krb5CredElement paramKrb5CredElement, int paramInt) throws GSSException {
/*  135 */     if (paramKrb5NameElement == null) {
/*  136 */       throw new IllegalArgumentException("Cannot have null peer name");
/*      */     }
/*  138 */     this.caller = paramGSSCaller;
/*  139 */     this.peerName = paramKrb5NameElement;
/*  140 */     this.myCred = paramKrb5CredElement;
/*  141 */     this.lifetime = paramInt;
/*  142 */     this.initiator = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Krb5Context(GSSCaller paramGSSCaller, Krb5CredElement paramKrb5CredElement) throws GSSException {
/*  151 */     this.caller = paramGSSCaller;
/*  152 */     this.myCred = paramKrb5CredElement;
/*  153 */     this.initiator = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Krb5Context(GSSCaller paramGSSCaller, byte[] paramArrayOfbyte) throws GSSException {
/*  161 */     throw new GSSException(16, -1, "GSS Import Context not available");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isTransferable() throws GSSException {
/*  170 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getLifetime() {
/*  178 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void requestLifetime(int paramInt) throws GSSException {
/*  205 */     if (this.state == 1 && isInitiator()) {
/*  206 */       this.lifetime = paramInt;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestConf(boolean paramBoolean) throws GSSException {
/*  213 */     if (this.state == 1 && isInitiator()) {
/*  214 */       this.confState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getConfState() {
/*  221 */     return this.confState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestInteg(boolean paramBoolean) throws GSSException {
/*  228 */     if (this.state == 1 && isInitiator()) {
/*  229 */       this.integState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getIntegState() {
/*  236 */     return this.integState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestCredDeleg(boolean paramBoolean) throws GSSException {
/*  244 */     if (this.state == 1 && isInitiator() && (
/*  245 */       this.myCred == null || !(this.myCred instanceof Krb5ProxyCredential))) {
/*  246 */       this.credDelegState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getCredDelegState() {
/*  255 */     if (isInitiator()) {
/*  256 */       return this.credDelegState;
/*      */     }
/*      */ 
/*      */     
/*  260 */     tryConstrainedDelegation();
/*  261 */     return (this.delegatedCred != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestMutualAuth(boolean paramBoolean) throws GSSException {
/*  271 */     if (this.state == 1 && isInitiator()) {
/*  272 */       this.mutualAuthState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getMutualAuthState() {
/*  282 */     return this.mutualAuthState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestReplayDet(boolean paramBoolean) throws GSSException {
/*  290 */     if (this.state == 1 && isInitiator()) {
/*  291 */       this.replayDetState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getReplayDetState() {
/*  299 */     return (this.replayDetState || this.sequenceDetState);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestSequenceDet(boolean paramBoolean) throws GSSException {
/*  307 */     if (this.state == 1 && isInitiator()) {
/*  308 */       this.sequenceDetState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getSequenceDetState() {
/*  316 */     return (this.sequenceDetState || this.replayDetState);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestDelegPolicy(boolean paramBoolean) {
/*  323 */     if (this.state == 1 && isInitiator()) {
/*  324 */       this.delegPolicyState = paramBoolean;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getDelegPolicyState() {
/*  331 */     return this.delegPolicyState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void requestAnonymity(boolean paramBoolean) throws GSSException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getAnonymityState() {
/*  353 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final CipherHelper getCipherHelper(EncryptionKey paramEncryptionKey) throws GSSException {
/*  365 */     EncryptionKey encryptionKey = null;
/*  366 */     if (this.cipherHelper == null) {
/*  367 */       encryptionKey = (getKey() == null) ? paramEncryptionKey : getKey();
/*  368 */       this.cipherHelper = new CipherHelper(encryptionKey);
/*      */     } 
/*  370 */     return this.cipherHelper;
/*      */   }
/*      */   
/*      */   final int incrementMySequenceNumber() {
/*      */     int i;
/*  375 */     synchronized (this.mySeqNumberLock) {
/*  376 */       i = this.mySeqNumber;
/*  377 */       this.mySeqNumber = i + 1;
/*      */     } 
/*  379 */     return i;
/*      */   }
/*      */   
/*      */   final void resetMySequenceNumber(int paramInt) {
/*  383 */     if (DEBUG) {
/*  384 */       System.out.println("Krb5Context setting mySeqNumber to: " + paramInt);
/*      */     }
/*      */     
/*  387 */     synchronized (this.mySeqNumberLock) {
/*  388 */       this.mySeqNumber = paramInt;
/*      */     } 
/*      */   }
/*      */   
/*      */   final void resetPeerSequenceNumber(int paramInt) {
/*  393 */     if (DEBUG) {
/*  394 */       System.out.println("Krb5Context setting peerSeqNumber to: " + paramInt);
/*      */     }
/*      */     
/*  397 */     synchronized (this.peerSeqNumberLock) {
/*  398 */       this.peerSeqNumber = paramInt;
/*  399 */       this.peerTokenTracker = new TokenTracker(this.peerSeqNumber);
/*      */     } 
/*      */   }
/*      */   
/*      */   final void setKey(int paramInt, EncryptionKey paramEncryptionKey) throws GSSException {
/*  404 */     this.key = paramEncryptionKey;
/*  405 */     this.keySrc = paramInt;
/*      */     
/*  407 */     this.cipherHelper = new CipherHelper(paramEncryptionKey);
/*      */   }
/*      */   
/*      */   public final int getKeySrc() {
/*  411 */     return this.keySrc;
/*      */   }
/*      */   
/*      */   private final EncryptionKey getKey() {
/*  415 */     return this.key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void setDelegCred(Krb5CredElement paramKrb5CredElement) {
/*  423 */     this.delegatedCred = paramKrb5CredElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void setCredDelegState(boolean paramBoolean) {
/*  439 */     this.credDelegState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setMutualAuthState(boolean paramBoolean) {
/*  443 */     this.mutualAuthState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setReplayDetState(boolean paramBoolean) {
/*  447 */     this.replayDetState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setSequenceDetState(boolean paramBoolean) {
/*  451 */     this.sequenceDetState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setConfState(boolean paramBoolean) {
/*  455 */     this.confState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setIntegState(boolean paramBoolean) {
/*  459 */     this.integState = paramBoolean;
/*      */   }
/*      */   
/*      */   final void setDelegPolicyState(boolean paramBoolean) {
/*  463 */     this.delegPolicyState = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setChannelBinding(ChannelBinding paramChannelBinding) throws GSSException {
/*  472 */     this.channelBinding = paramChannelBinding;
/*      */   }
/*      */   
/*      */   final ChannelBinding getChannelBinding() {
/*  476 */     return this.channelBinding;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final Oid getMech() {
/*  485 */     return Krb5MechFactory.GSS_KRB5_MECH_OID;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final GSSNameSpi getSrcName() throws GSSException {
/*  495 */     return isInitiator() ? this.myName : this.peerName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final GSSNameSpi getTargName() throws GSSException {
/*  505 */     return !isInitiator() ? this.myName : this.peerName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final GSSCredentialSpi getDelegCred() throws GSSException {
/*  520 */     if (this.state != 2 && this.state != 3)
/*  521 */       throw new GSSException(12); 
/*  522 */     if (isInitiator()) {
/*  523 */       throw new GSSException(13);
/*      */     }
/*  525 */     tryConstrainedDelegation();
/*  526 */     if (this.delegatedCred == null) {
/*  527 */       throw new GSSException(13);
/*      */     }
/*  529 */     return this.delegatedCred;
/*      */   }
/*      */   
/*      */   private void tryConstrainedDelegation() {
/*  533 */     if (this.state != 2 && this.state != 3) {
/*      */       return;
/*      */     }
/*      */     
/*  537 */     if (!this.isConstrainedDelegationTried) {
/*  538 */       if (this.delegatedCred == null) {
/*  539 */         if (DEBUG) {
/*  540 */           System.out.println(">>> Constrained deleg from " + this.caller);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  546 */           this
/*  547 */             .delegatedCred = new Krb5ProxyCredential(Krb5InitCredential.getInstance(GSSCaller.CALLER_ACCEPT, this.myName, this.lifetime), this.peerName, this.serviceTicket);
/*      */         
/*      */         }
/*  550 */         catch (GSSException gSSException) {}
/*      */       } 
/*      */ 
/*      */       
/*  554 */       this.isConstrainedDelegationTried = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isInitiator() {
/*  564 */     return this.initiator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isProtReady() {
/*  576 */     return (this.state == 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] initSecContext(InputStream paramInputStream, int paramInt) throws GSSException {
/*  595 */     byte[] arrayOfByte = null;
/*  596 */     InitSecContextToken initSecContextToken = null;
/*  597 */     byte b = 11;
/*  598 */     if (DEBUG) {
/*  599 */       System.out.println("Entered Krb5Context.initSecContext with state=" + 
/*  600 */           printState(this.state));
/*      */     }
/*  602 */     if (!isInitiator()) {
/*  603 */       throw new GSSException(11, -1, "initSecContext on an acceptor GSSContext");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  609 */       if (this.state == 1) {
/*  610 */         Credentials credentials; final Krb5ProxyCredential second; this.state = 2;
/*      */         
/*  612 */         b = 13;
/*      */         
/*  614 */         if (this.myCred == null) {
/*  615 */           this.myCred = Krb5InitCredential.getInstance(this.caller, this.myName, 0);
/*      */         }
/*  617 */         else if (!this.myCred.isInitiatorCredential()) {
/*  618 */           throw new GSSException(b, -1, "No TGT available");
/*      */         } 
/*      */         
/*  621 */         this.myName = (Krb5NameElement)this.myCred.getName();
/*      */ 
/*      */         
/*  624 */         if (this.myCred instanceof Krb5InitCredential) {
/*  625 */           krb5ProxyCredential = null;
/*  626 */           credentials = ((Krb5InitCredential)this.myCred).getKrb5Credentials();
/*      */         } else {
/*  628 */           krb5ProxyCredential = (Krb5ProxyCredential)this.myCred;
/*  629 */           credentials = krb5ProxyCredential.self.getKrb5Credentials();
/*      */         } 
/*      */         
/*  632 */         checkPermission(this.peerName.getKrb5PrincipalName().getName(), "initiate");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  641 */         final AccessControlContext acc = AccessController.getContext();
/*      */         
/*  643 */         if (GSSUtil.useSubjectCredsOnly(this.caller)) {
/*  644 */           final KerberosTicket kt = null;
/*      */           
/*      */           try {
/*  647 */             kerberosTicket = AccessController.<KerberosTicket>doPrivileged(new PrivilegedExceptionAction<KerberosTicket>()
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                   public KerberosTicket run() throws Exception
/*      */                   {
/*  655 */                     return Krb5Util.getTicket(GSSCaller.CALLER_UNKNOWN, (second == null) ? Krb5Context.this
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/*  660 */                         .myName.getKrb5PrincipalName().getName() : second
/*  661 */                         .getName().getKrb5PrincipalName().getName(), Krb5Context.this
/*  662 */                         .peerName.getKrb5PrincipalName().getName(), acc);
/*      */                   }
/*      */                 });
/*  665 */           } catch (PrivilegedActionException privilegedActionException) {
/*  666 */             if (DEBUG) {
/*  667 */               System.out.println("Attempt to obtain service ticket from the subject failed!");
/*      */             }
/*      */           } 
/*      */           
/*  671 */           if (kerberosTicket != null) {
/*  672 */             if (DEBUG) {
/*  673 */               System.out.println("Found service ticket in the subject" + kerberosTicket);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  681 */             this.serviceCreds = Krb5Util.ticketToCreds(kerberosTicket);
/*      */           } 
/*      */         } 
/*  684 */         if (this.serviceCreds == null) {
/*      */ 
/*      */           
/*  687 */           if (DEBUG) {
/*  688 */             System.out.println("Service ticket not found in the subject");
/*      */           }
/*      */ 
/*      */           
/*  692 */           if (krb5ProxyCredential == null) {
/*  693 */             this.serviceCreds = Credentials.acquireServiceCreds(this.peerName
/*  694 */                 .getKrb5PrincipalName().getName(), credentials);
/*      */           } else {
/*      */             
/*  697 */             this.serviceCreds = Credentials.acquireS4U2proxyCreds(this.peerName
/*  698 */                 .getKrb5PrincipalName().getName(), krb5ProxyCredential.tkt, krb5ProxyCredential
/*      */                 
/*  700 */                 .getName().getKrb5PrincipalName(), credentials);
/*      */           } 
/*      */           
/*  703 */           if (GSSUtil.useSubjectCredsOnly(this.caller)) {
/*      */             
/*  705 */             final Subject subject = AccessController.<Subject>doPrivileged(new PrivilegedAction<Subject>()
/*      */                 {
/*      */                   public Subject run() {
/*  708 */                     return Subject.getSubject(acc);
/*      */                   }
/*      */                 });
/*  711 */             if (subject != null && 
/*  712 */               !subject.isReadOnly()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  721 */               final KerberosTicket kt = Krb5Util.credsToTicket(this.serviceCreds);
/*  722 */               AccessController.doPrivileged(new PrivilegedAction<Void>()
/*      */                   {
/*      */                     public Void run() {
/*  725 */                       subject.getPrivateCredentials().add(kt);
/*  726 */                       return null;
/*      */                     }
/*      */                   });
/*      */             
/*      */             }
/*  731 */             else if (DEBUG) {
/*  732 */               System.out.println("Subject is readOnly;Kerberos Service ticket not stored");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  740 */         b = 11;
/*  741 */         initSecContextToken = new InitSecContextToken(this, credentials, this.serviceCreds);
/*  742 */         this.apReq = initSecContextToken.getKrbApReq();
/*  743 */         arrayOfByte = initSecContextToken.encode();
/*  744 */         this.myCred = null;
/*  745 */         if (!getMutualAuthState()) {
/*  746 */           this.state = 3;
/*      */         }
/*  748 */         if (DEBUG) {
/*  749 */           System.out.println("Created InitSecContextToken:\n" + (new HexDumpEncoder())
/*  750 */               .encodeBuffer(arrayOfByte));
/*      */         }
/*  752 */       } else if (this.state == 2) {
/*      */ 
/*      */         
/*  755 */         new AcceptSecContextToken(this, this.serviceCreds, this.apReq, paramInputStream);
/*  756 */         this.serviceCreds = null;
/*  757 */         this.apReq = null;
/*  758 */         this.state = 3;
/*      */       
/*      */       }
/*  761 */       else if (DEBUG) {
/*  762 */         System.out.println(this.state);
/*      */       }
/*      */     
/*  765 */     } catch (KrbException krbException) {
/*  766 */       if (DEBUG) {
/*  767 */         krbException.printStackTrace();
/*      */       }
/*      */       
/*  770 */       GSSException gSSException = new GSSException(b, -1, krbException.getMessage());
/*  771 */       gSSException.initCause(krbException);
/*  772 */       throw gSSException;
/*  773 */     } catch (IOException iOException) {
/*      */       
/*  775 */       GSSException gSSException = new GSSException(b, -1, iOException.getMessage());
/*  776 */       gSSException.initCause(iOException);
/*  777 */       throw gSSException;
/*      */     } 
/*  779 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */   public final boolean isEstablished() {
/*  783 */     return (this.state == 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] acceptSecContext(InputStream paramInputStream, int paramInt) throws GSSException {
/*  801 */     byte[] arrayOfByte = null;
/*      */     
/*  803 */     if (DEBUG) {
/*  804 */       System.out.println("Entered Krb5Context.acceptSecContext with state=" + 
/*  805 */           printState(this.state));
/*      */     }
/*      */     
/*  808 */     if (isInitiator()) {
/*  809 */       throw new GSSException(11, -1, "acceptSecContext on an initiator GSSContext");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  814 */       if (this.state == 1) {
/*  815 */         this.state = 2;
/*  816 */         if (this.myCred == null) {
/*  817 */           this.myCred = Krb5AcceptCredential.getInstance(this.caller, this.myName);
/*  818 */         } else if (!this.myCred.isAcceptorCredential()) {
/*  819 */           throw new GSSException(13, -1, "No Secret Key available");
/*      */         } 
/*      */         
/*  822 */         this.myName = (Krb5NameElement)this.myCred.getName();
/*      */ 
/*      */         
/*  825 */         if (this.myName != null) {
/*  826 */           Krb5MechFactory.checkAcceptCredPermission(this.myName, this.myName);
/*      */         }
/*      */         
/*  829 */         InitSecContextToken initSecContextToken = new InitSecContextToken(this, (Krb5AcceptCredential)this.myCred, paramInputStream);
/*      */         
/*  831 */         PrincipalName principalName = initSecContextToken.getKrbApReq().getClient();
/*  832 */         this.peerName = Krb5NameElement.getInstance(principalName);
/*      */ 
/*      */         
/*  835 */         if (this.myName == null) {
/*  836 */           this.myName = Krb5NameElement.getInstance(initSecContextToken
/*  837 */               .getKrbApReq().getCreds().getServer());
/*  838 */           Krb5MechFactory.checkAcceptCredPermission(this.myName, this.myName);
/*      */         } 
/*      */         
/*  841 */         if (getMutualAuthState())
/*      */         {
/*  843 */           arrayOfByte = (new AcceptSecContextToken(this, initSecContextToken.getKrbApReq())).encode();
/*      */         }
/*  845 */         this.serviceTicket = initSecContextToken.getKrbApReq().getCreds().getTicket();
/*  846 */         this.myCred = null;
/*  847 */         this.state = 3;
/*      */       
/*      */       }
/*  850 */       else if (DEBUG) {
/*  851 */         System.out.println(this.state);
/*      */       }
/*      */     
/*  854 */     } catch (KrbException krbException) {
/*      */       
/*  856 */       GSSException gSSException = new GSSException(11, -1, krbException.getMessage());
/*  857 */       gSSException.initCause(krbException);
/*  858 */       throw gSSException;
/*  859 */     } catch (IOException iOException) {
/*  860 */       if (DEBUG) {
/*  861 */         iOException.printStackTrace();
/*      */       }
/*      */       
/*  864 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/*  865 */       gSSException.initCause(iOException);
/*  866 */       throw gSSException;
/*      */     } 
/*      */     
/*  869 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getWrapSizeLimit(int paramInt1, boolean paramBoolean, int paramInt2) throws GSSException {
/*  889 */     int i = 0;
/*  890 */     if (this.cipherHelper.getProto() == 0) {
/*  891 */       i = WrapToken.getSizeLimit(paramInt1, paramBoolean, paramInt2, 
/*  892 */           getCipherHelper(null));
/*  893 */     } else if (this.cipherHelper.getProto() == 1) {
/*  894 */       i = WrapToken_v2.getSizeLimit(paramInt1, paramBoolean, paramInt2, 
/*  895 */           getCipherHelper(null));
/*      */     } 
/*  897 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/*  909 */     if (DEBUG) {
/*  910 */       System.out.println("Krb5Context.wrap: data=[" + 
/*  911 */           getHexBytes(paramArrayOfbyte, paramInt1, paramInt2) + "]");
/*      */     }
/*      */ 
/*      */     
/*  915 */     if (this.state != 3) {
/*  916 */       throw new GSSException(12, -1, "Wrap called in invalid state!");
/*      */     }
/*      */     
/*  919 */     byte[] arrayOfByte = null;
/*      */     try {
/*  921 */       if (this.cipherHelper.getProto() == 0) {
/*  922 */         WrapToken wrapToken = new WrapToken(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/*  924 */         arrayOfByte = wrapToken.encode();
/*  925 */       } else if (this.cipherHelper.getProto() == 1) {
/*  926 */         WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/*  928 */         arrayOfByte = wrapToken_v2.encode();
/*      */       } 
/*  930 */       if (DEBUG) {
/*  931 */         System.out.println("Krb5Context.wrap: token=[" + 
/*  932 */             getHexBytes(arrayOfByte, 0, arrayOfByte.length) + "]");
/*      */       }
/*      */       
/*  935 */       return arrayOfByte;
/*  936 */     } catch (IOException iOException) {
/*  937 */       arrayOfByte = null;
/*      */       
/*  939 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/*  940 */       gSSException.initCause(iOException);
/*  941 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int wrap(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, MessageProp paramMessageProp) throws GSSException {
/*  949 */     if (this.state != 3) {
/*  950 */       throw new GSSException(12, -1, "Wrap called in invalid state!");
/*      */     }
/*      */     
/*  953 */     int i = 0;
/*      */     try {
/*  955 */       if (this.cipherHelper.getProto() == 0) {
/*  956 */         WrapToken wrapToken = new WrapToken(this, paramMessageProp, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */         
/*  958 */         i = wrapToken.encode(paramArrayOfbyte2, paramInt3);
/*  959 */       } else if (this.cipherHelper.getProto() == 1) {
/*  960 */         WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramMessageProp, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */         
/*  962 */         i = wrapToken_v2.encode(paramArrayOfbyte2, paramInt3);
/*      */       } 
/*  964 */       if (DEBUG) {
/*  965 */         System.out.println("Krb5Context.wrap: token=[" + 
/*  966 */             getHexBytes(paramArrayOfbyte2, paramInt3, i) + "]");
/*      */       }
/*      */       
/*  969 */       return i;
/*  970 */     } catch (IOException iOException) {
/*  971 */       i = 0;
/*      */       
/*  973 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/*  974 */       gSSException.initCause(iOException);
/*  975 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void wrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*  983 */     if (this.state != 3) {
/*  984 */       throw new GSSException(12, -1, "Wrap called in invalid state!");
/*      */     }
/*      */     
/*  987 */     byte[] arrayOfByte = null;
/*      */     try {
/*  989 */       if (this.cipherHelper.getProto() == 0) {
/*  990 */         WrapToken wrapToken = new WrapToken(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/*  992 */         wrapToken.encode(paramOutputStream);
/*  993 */         if (DEBUG) {
/*  994 */           arrayOfByte = wrapToken.encode();
/*      */         }
/*  996 */       } else if (this.cipherHelper.getProto() == 1) {
/*  997 */         WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/*  999 */         wrapToken_v2.encode(paramOutputStream);
/* 1000 */         if (DEBUG) {
/* 1001 */           arrayOfByte = wrapToken_v2.encode();
/*      */         }
/*      */       } 
/* 1004 */     } catch (IOException iOException) {
/*      */       
/* 1006 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1007 */       gSSException.initCause(iOException);
/* 1008 */       throw gSSException;
/*      */     } 
/*      */     
/* 1011 */     if (DEBUG) {
/* 1012 */       System.out.println("Krb5Context.wrap: token=[" + 
/* 1013 */           getHexBytes(arrayOfByte, 0, arrayOfByte.length) + "]");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void wrap(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*      */     byte[] arrayOfByte;
/*      */     try {
/* 1023 */       arrayOfByte = new byte[paramInputStream.available()];
/* 1024 */       paramInputStream.read(arrayOfByte);
/* 1025 */     } catch (IOException iOException) {
/*      */       
/* 1027 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1028 */       gSSException.initCause(iOException);
/* 1029 */       throw gSSException;
/*      */     } 
/* 1031 */     wrap(arrayOfByte, 0, arrayOfByte.length, paramOutputStream, paramMessageProp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] unwrap(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 1038 */     if (DEBUG) {
/* 1039 */       System.out.println("Krb5Context.unwrap: token=[" + 
/* 1040 */           getHexBytes(paramArrayOfbyte, paramInt1, paramInt2) + "]");
/*      */     }
/*      */ 
/*      */     
/* 1044 */     if (this.state != 3) {
/* 1045 */       throw new GSSException(12, -1, " Unwrap called in invalid state!");
/*      */     }
/*      */ 
/*      */     
/* 1049 */     byte[] arrayOfByte = null;
/* 1050 */     if (this.cipherHelper.getProto() == 0) {
/* 1051 */       WrapToken wrapToken = new WrapToken(this, paramArrayOfbyte, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1053 */       arrayOfByte = wrapToken.getData();
/* 1054 */       setSequencingAndReplayProps(wrapToken, paramMessageProp);
/* 1055 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1056 */       WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramArrayOfbyte, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1058 */       arrayOfByte = wrapToken_v2.getData();
/* 1059 */       setSequencingAndReplayProps(wrapToken_v2, paramMessageProp);
/*      */     } 
/*      */     
/* 1062 */     if (DEBUG) {
/* 1063 */       System.out.println("Krb5Context.unwrap: data=[" + 
/* 1064 */           getHexBytes(arrayOfByte, 0, arrayOfByte.length) + "]");
/*      */     }
/*      */ 
/*      */     
/* 1068 */     return arrayOfByte;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int unwrap(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, MessageProp paramMessageProp) throws GSSException {
/* 1075 */     if (this.state != 3) {
/* 1076 */       throw new GSSException(12, -1, "Unwrap called in invalid state!");
/*      */     }
/*      */     
/* 1079 */     if (this.cipherHelper.getProto() == 0) {
/* 1080 */       WrapToken wrapToken = new WrapToken(this, paramArrayOfbyte1, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1082 */       paramInt2 = wrapToken.getData(paramArrayOfbyte2, paramInt3);
/* 1083 */       setSequencingAndReplayProps(wrapToken, paramMessageProp);
/* 1084 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1085 */       WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramArrayOfbyte1, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1087 */       paramInt2 = wrapToken_v2.getData(paramArrayOfbyte2, paramInt3);
/* 1088 */       setSequencingAndReplayProps(wrapToken_v2, paramMessageProp);
/*      */     } 
/* 1090 */     return paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int unwrap(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt, MessageProp paramMessageProp) throws GSSException {
/* 1097 */     if (this.state != 3) {
/* 1098 */       throw new GSSException(12, -1, "Unwrap called in invalid state!");
/*      */     }
/*      */     
/* 1101 */     int i = 0;
/* 1102 */     if (this.cipherHelper.getProto() == 0) {
/* 1103 */       WrapToken wrapToken = new WrapToken(this, paramInputStream, paramMessageProp);
/* 1104 */       i = wrapToken.getData(paramArrayOfbyte, paramInt);
/* 1105 */       setSequencingAndReplayProps(wrapToken, paramMessageProp);
/* 1106 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1107 */       WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramInputStream, paramMessageProp);
/* 1108 */       i = wrapToken_v2.getData(paramArrayOfbyte, paramInt);
/* 1109 */       setSequencingAndReplayProps(wrapToken_v2, paramMessageProp);
/*      */     } 
/* 1111 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void unwrap(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/* 1118 */     if (this.state != 3) {
/* 1119 */       throw new GSSException(12, -1, "Unwrap called in invalid state!");
/*      */     }
/*      */     
/* 1122 */     byte[] arrayOfByte = null;
/* 1123 */     if (this.cipherHelper.getProto() == 0) {
/* 1124 */       WrapToken wrapToken = new WrapToken(this, paramInputStream, paramMessageProp);
/* 1125 */       arrayOfByte = wrapToken.getData();
/* 1126 */       setSequencingAndReplayProps(wrapToken, paramMessageProp);
/* 1127 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1128 */       WrapToken_v2 wrapToken_v2 = new WrapToken_v2(this, paramInputStream, paramMessageProp);
/* 1129 */       arrayOfByte = wrapToken_v2.getData();
/* 1130 */       setSequencingAndReplayProps(wrapToken_v2, paramMessageProp);
/*      */     } 
/*      */     
/*      */     try {
/* 1134 */       paramOutputStream.write(arrayOfByte);
/* 1135 */     } catch (IOException iOException) {
/*      */       
/* 1137 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1138 */       gSSException.initCause(iOException);
/* 1139 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] getMIC(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 1147 */     byte[] arrayOfByte = null;
/*      */     try {
/* 1149 */       if (this.cipherHelper.getProto() == 0) {
/* 1150 */         MicToken micToken = new MicToken(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/* 1152 */         arrayOfByte = micToken.encode();
/* 1153 */       } else if (this.cipherHelper.getProto() == 1) {
/* 1154 */         MicToken_v2 micToken_v2 = new MicToken_v2(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/* 1156 */         arrayOfByte = micToken_v2.encode();
/*      */       } 
/* 1158 */       return arrayOfByte;
/* 1159 */     } catch (IOException iOException) {
/* 1160 */       arrayOfByte = null;
/*      */       
/* 1162 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1163 */       gSSException.initCause(iOException);
/* 1164 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getMIC(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, MessageProp paramMessageProp) throws GSSException {
/* 1173 */     int i = 0;
/*      */     try {
/* 1175 */       if (this.cipherHelper.getProto() == 0) {
/* 1176 */         MicToken micToken = new MicToken(this, paramMessageProp, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */         
/* 1178 */         i = micToken.encode(paramArrayOfbyte2, paramInt3);
/* 1179 */       } else if (this.cipherHelper.getProto() == 1) {
/* 1180 */         MicToken_v2 micToken_v2 = new MicToken_v2(this, paramMessageProp, paramArrayOfbyte1, paramInt1, paramInt2);
/*      */         
/* 1182 */         i = micToken_v2.encode(paramArrayOfbyte2, paramInt3);
/*      */       } 
/* 1184 */       return i;
/* 1185 */     } catch (IOException iOException) {
/* 1186 */       i = 0;
/*      */       
/* 1188 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1189 */       gSSException.initCause(iOException);
/* 1190 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getMIC(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*      */     try {
/* 1206 */       if (this.cipherHelper.getProto() == 0) {
/* 1207 */         MicToken micToken = new MicToken(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/* 1209 */         micToken.encode(paramOutputStream);
/* 1210 */       } else if (this.cipherHelper.getProto() == 1) {
/* 1211 */         MicToken_v2 micToken_v2 = new MicToken_v2(this, paramMessageProp, paramArrayOfbyte, paramInt1, paramInt2);
/*      */         
/* 1213 */         micToken_v2.encode(paramOutputStream);
/*      */       } 
/* 1215 */     } catch (IOException iOException) {
/*      */       
/* 1217 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1218 */       gSSException.initCause(iOException);
/* 1219 */       throw gSSException;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void getMIC(InputStream paramInputStream, OutputStream paramOutputStream, MessageProp paramMessageProp) throws GSSException {
/*      */     byte[] arrayOfByte;
/*      */     try {
/* 1227 */       arrayOfByte = new byte[paramInputStream.available()];
/* 1228 */       paramInputStream.read(arrayOfByte);
/* 1229 */     } catch (IOException iOException) {
/*      */       
/* 1231 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1232 */       gSSException.initCause(iOException);
/* 1233 */       throw gSSException;
/*      */     } 
/* 1235 */     getMIC(arrayOfByte, 0, arrayOfByte.length, paramOutputStream, paramMessageProp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void verifyMIC(byte[] paramArrayOfbyte1, int paramInt1, int paramInt2, byte[] paramArrayOfbyte2, int paramInt3, int paramInt4, MessageProp paramMessageProp) throws GSSException {
/* 1243 */     if (this.cipherHelper.getProto() == 0) {
/* 1244 */       MicToken micToken = new MicToken(this, paramArrayOfbyte1, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1246 */       micToken.verify(paramArrayOfbyte2, paramInt3, paramInt4);
/* 1247 */       setSequencingAndReplayProps(micToken, paramMessageProp);
/* 1248 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1249 */       MicToken_v2 micToken_v2 = new MicToken_v2(this, paramArrayOfbyte1, paramInt1, paramInt2, paramMessageProp);
/*      */       
/* 1251 */       micToken_v2.verify(paramArrayOfbyte2, paramInt3, paramInt4);
/* 1252 */       setSequencingAndReplayProps(micToken_v2, paramMessageProp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifyMIC(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, MessageProp paramMessageProp) throws GSSException {
/* 1261 */     if (this.cipherHelper.getProto() == 0) {
/* 1262 */       MicToken micToken = new MicToken(this, paramInputStream, paramMessageProp);
/* 1263 */       micToken.verify(paramArrayOfbyte, paramInt1, paramInt2);
/* 1264 */       setSequencingAndReplayProps(micToken, paramMessageProp);
/* 1265 */     } else if (this.cipherHelper.getProto() == 1) {
/* 1266 */       MicToken_v2 micToken_v2 = new MicToken_v2(this, paramInputStream, paramMessageProp);
/* 1267 */       micToken_v2.verify(paramArrayOfbyte, paramInt1, paramInt2);
/* 1268 */       setSequencingAndReplayProps(micToken_v2, paramMessageProp);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public final void verifyMIC(InputStream paramInputStream1, InputStream paramInputStream2, MessageProp paramMessageProp) throws GSSException {
/*      */     byte[] arrayOfByte;
/*      */     try {
/* 1276 */       arrayOfByte = new byte[paramInputStream2.available()];
/* 1277 */       paramInputStream2.read(arrayOfByte);
/* 1278 */     } catch (IOException iOException) {
/*      */       
/* 1280 */       GSSException gSSException = new GSSException(11, -1, iOException.getMessage());
/* 1281 */       gSSException.initCause(iOException);
/* 1282 */       throw gSSException;
/*      */     } 
/* 1284 */     verifyMIC(paramInputStream1, arrayOfByte, 0, arrayOfByte.length, paramMessageProp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final byte[] export() throws GSSException {
/* 1296 */     throw new GSSException(16, -1, "GSS Export Context not available");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void dispose() throws GSSException {
/* 1308 */     this.state = 4;
/* 1309 */     this.delegatedCred = null;
/*      */   }
/*      */   
/*      */   public final Provider getProvider() {
/* 1313 */     return Krb5MechFactory.PROVIDER;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSequencingAndReplayProps(MessageToken paramMessageToken, MessageProp paramMessageProp) {
/* 1322 */     if (this.replayDetState || this.sequenceDetState) {
/* 1323 */       int i = paramMessageToken.getSequenceNumber();
/* 1324 */       this.peerTokenTracker.getProps(i, paramMessageProp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSequencingAndReplayProps(MessageToken_v2 paramMessageToken_v2, MessageProp paramMessageProp) {
/* 1334 */     if (this.replayDetState || this.sequenceDetState) {
/* 1335 */       int i = paramMessageToken_v2.getSequenceNumber();
/* 1336 */       this.peerTokenTracker.getProps(i, paramMessageProp);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkPermission(String paramString1, String paramString2) {
/* 1341 */     SecurityManager securityManager = System.getSecurityManager();
/* 1342 */     if (securityManager != null) {
/* 1343 */       ServicePermission servicePermission = new ServicePermission(paramString1, paramString2);
/*      */       
/* 1345 */       securityManager.checkPermission(servicePermission);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getHexBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 1351 */     StringBuffer stringBuffer = new StringBuffer();
/* 1352 */     for (byte b = 0; b < paramInt2; b++) {
/*      */       
/* 1354 */       int i = paramArrayOfbyte[b] >> 4 & 0xF;
/* 1355 */       int j = paramArrayOfbyte[b] & 0xF;
/*      */       
/* 1357 */       stringBuffer.append(Integer.toHexString(i));
/* 1358 */       stringBuffer.append(Integer.toHexString(j));
/* 1359 */       stringBuffer.append(' ');
/*      */     } 
/* 1361 */     return stringBuffer.toString();
/*      */   }
/*      */   
/*      */   private static String printState(int paramInt) {
/* 1365 */     switch (paramInt) {
/*      */       case 1:
/* 1367 */         return "STATE_NEW";
/*      */       case 2:
/* 1369 */         return "STATE_IN_PROCESS";
/*      */       case 3:
/* 1371 */         return "STATE_DONE";
/*      */       case 4:
/* 1373 */         return "STATE_DELETED";
/*      */     } 
/* 1375 */     return "Unknown state " + paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   GSSCaller getCaller() {
/* 1381 */     return this.caller;
/*      */   }
/*      */ 
/*      */   
/*      */   static class KerberosSessionKey
/*      */     implements Key
/*      */   {
/*      */     private static final long serialVersionUID = 699307378954123869L;
/*      */     
/*      */     private final EncryptionKey key;
/*      */     
/*      */     KerberosSessionKey(EncryptionKey param1EncryptionKey) {
/* 1393 */       this.key = param1EncryptionKey;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getAlgorithm() {
/* 1398 */       return Integer.toString(this.key.getEType());
/*      */     }
/*      */ 
/*      */     
/*      */     public String getFormat() {
/* 1403 */       return "RAW";
/*      */     }
/*      */ 
/*      */     
/*      */     public byte[] getEncoded() {
/* 1408 */       return (byte[])this.key.getBytes().clone();
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1413 */       return "Kerberos session key: etype: " + this.key.getEType() + "\n" + (new HexDumpEncoder())
/* 1414 */         .encodeBuffer(this.key.getBytes());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object inquireSecContext(InquireType paramInquireType) throws GSSException {
/* 1423 */     if (!isEstablished()) {
/* 1424 */       throw new GSSException(12, -1, "Security context not established.");
/*      */     }
/*      */     
/* 1427 */     switch (paramInquireType) {
/*      */       case KRB5_GET_SESSION_KEY:
/* 1429 */         return new KerberosSessionKey(this.key);
/*      */       case KRB5_GET_TKT_FLAGS:
/* 1431 */         return this.tktFlags.clone();
/*      */       case KRB5_GET_AUTHZ_DATA:
/* 1433 */         if (isInitiator()) {
/* 1434 */           throw new GSSException(16, -1, "AuthzData not available on initiator side.");
/*      */         }
/*      */         
/* 1437 */         return (this.authzData == null) ? null : this.authzData.clone();
/*      */       
/*      */       case KRB5_GET_AUTHTIME:
/* 1440 */         return this.authTime;
/*      */     } 
/* 1442 */     throw new GSSException(16, -1, "Inquire type not supported.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTktFlags(boolean[] paramArrayOfboolean) {
/* 1452 */     this.tktFlags = paramArrayOfboolean;
/*      */   }
/*      */   
/*      */   public void setAuthTime(String paramString) {
/* 1456 */     this.authTime = paramString;
/*      */   }
/*      */   
/*      */   public void setAuthzData(AuthorizationDataEntry[] paramArrayOfAuthorizationDataEntry) {
/* 1460 */     this.authzData = paramArrayOfAuthorizationDataEntry;
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\jgss\krb5\Krb5Context.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */