/*      */ package com.sun.security.auth.module;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.security.AccessController;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import javax.security.auth.DestroyFailedException;
/*      */ import javax.security.auth.RefreshFailedException;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.callback.Callback;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.auth.callback.NameCallback;
/*      */ import javax.security.auth.callback.PasswordCallback;
/*      */ import javax.security.auth.callback.UnsupportedCallbackException;
/*      */ import javax.security.auth.kerberos.KerberosKey;
/*      */ import javax.security.auth.kerberos.KerberosPrincipal;
/*      */ import javax.security.auth.kerberos.KerberosTicket;
/*      */ import javax.security.auth.kerberos.KeyTab;
/*      */ import javax.security.auth.login.LoginException;
/*      */ import javax.security.auth.spi.LoginModule;
/*      */ import jdk.Exported;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.jgss.krb5.Krb5Util;
/*      */ import sun.security.krb5.Config;
/*      */ import sun.security.krb5.Credentials;
/*      */ import sun.security.krb5.EncryptionKey;
/*      */ import sun.security.krb5.KrbAsReqBuilder;
/*      */ import sun.security.krb5.KrbException;
/*      */ import sun.security.krb5.PrincipalName;
/*      */ import sun.security.krb5.internal.ktab.KeyTab;
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
/*      */ @Exported
/*      */ public class Krb5LoginModule
/*      */   implements LoginModule
/*      */ {
/*      */   private Subject subject;
/*      */   private CallbackHandler callbackHandler;
/*      */   private Map<String, Object> sharedState;
/*      */   private Map<String, ?> options;
/*      */   private boolean debug = false;
/*      */   private boolean storeKey = false;
/*      */   private boolean doNotPrompt = false;
/*      */   private boolean useTicketCache = false;
/*      */   private boolean useKeyTab = false;
/*  397 */   private String ticketCacheName = null;
/*  398 */   private String keyTabName = null;
/*  399 */   private String princName = null;
/*      */   
/*      */   private boolean useFirstPass = false;
/*      */   
/*      */   private boolean tryFirstPass = false;
/*      */   
/*      */   private boolean storePass = false;
/*      */   
/*      */   private boolean clearPass = false;
/*      */   
/*      */   private boolean refreshKrb5Config = false;
/*      */   
/*      */   private boolean renewTGT = false;
/*      */   
/*      */   private boolean isInitiator = true;
/*      */   
/*      */   private boolean succeeded = false;
/*      */   
/*      */   private boolean commitSucceeded = false;
/*      */   private String username;
/*  419 */   private EncryptionKey[] encKeys = null;
/*      */   
/*  421 */   KeyTab ktab = null;
/*      */   
/*  423 */   private Credentials cred = null;
/*      */   
/*  425 */   private PrincipalName principal = null;
/*  426 */   private KerberosPrincipal kerbClientPrinc = null;
/*  427 */   private KerberosTicket kerbTicket = null;
/*  428 */   private KerberosKey[] kerbKeys = null;
/*  429 */   private StringBuffer krb5PrincName = null;
/*      */   private boolean unboundServer = false;
/*  431 */   private char[] password = null;
/*      */   private static final String NAME = "javax.security.auth.login.name";
/*      */   private static final String PWD = "javax.security.auth.login.password";
/*      */   
/*  435 */   private static final ResourceBundle rb = AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>()
/*      */       {
/*      */         public ResourceBundle run() {
/*  438 */           return ResourceBundle.getBundle("sun.security.util.AuthResources");
/*      */         }
/*      */       });
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
/*      */   
/*      */   public void initialize(Subject paramSubject, CallbackHandler paramCallbackHandler, Map<String, ?> paramMap1, Map<String, ?> paramMap2) {
/*  470 */     this.subject = paramSubject;
/*  471 */     this.callbackHandler = paramCallbackHandler;
/*  472 */     this.sharedState = (Map)paramMap1;
/*  473 */     this.options = paramMap2;
/*      */ 
/*      */ 
/*      */     
/*  477 */     this.debug = "true".equalsIgnoreCase((String)paramMap2.get("debug"));
/*  478 */     this.storeKey = "true".equalsIgnoreCase((String)paramMap2.get("storeKey"));
/*  479 */     this.doNotPrompt = "true".equalsIgnoreCase((String)paramMap2
/*  480 */         .get("doNotPrompt"));
/*  481 */     this.useTicketCache = "true".equalsIgnoreCase((String)paramMap2
/*  482 */         .get("useTicketCache"));
/*  483 */     this.useKeyTab = "true".equalsIgnoreCase((String)paramMap2.get("useKeyTab"));
/*  484 */     this.ticketCacheName = (String)paramMap2.get("ticketCache");
/*  485 */     this.keyTabName = (String)paramMap2.get("keyTab");
/*  486 */     if (this.keyTabName != null) {
/*  487 */       this.keyTabName = KeyTab.normalize(this.keyTabName);
/*      */     }
/*      */     
/*  490 */     this.princName = (String)paramMap2.get("principal");
/*  491 */     this
/*  492 */       .refreshKrb5Config = "true".equalsIgnoreCase((String)paramMap2.get("refreshKrb5Config"));
/*  493 */     this
/*  494 */       .renewTGT = "true".equalsIgnoreCase((String)paramMap2.get("renewTGT"));
/*      */ 
/*      */     
/*  497 */     String str = (String)paramMap2.get("isInitiator");
/*  498 */     if (str != null)
/*      */     {
/*      */       
/*  501 */       this.isInitiator = "true".equalsIgnoreCase(str);
/*      */     }
/*      */     
/*  504 */     this
/*      */       
/*  506 */       .tryFirstPass = "true".equalsIgnoreCase((String)paramMap2.get("tryFirstPass"));
/*  507 */     this
/*      */       
/*  509 */       .useFirstPass = "true".equalsIgnoreCase((String)paramMap2.get("useFirstPass"));
/*  510 */     this
/*  511 */       .storePass = "true".equalsIgnoreCase((String)paramMap2.get("storePass"));
/*  512 */     this
/*  513 */       .clearPass = "true".equalsIgnoreCase((String)paramMap2.get("clearPass"));
/*  514 */     if (this.debug) {
/*  515 */       System.out.print("Debug is  " + this.debug + " storeKey " + this.storeKey + " useTicketCache " + this.useTicketCache + " useKeyTab " + this.useKeyTab + " doNotPrompt " + this.doNotPrompt + " ticketCache is " + this.ticketCacheName + " isInitiator " + this.isInitiator + " KeyTab is " + this.keyTabName + " refreshKrb5Config is " + this.refreshKrb5Config + " principal is " + this.princName + " tryFirstPass is " + this.tryFirstPass + " useFirstPass is " + this.useFirstPass + " storePass is " + this.storePass + " clearPass is " + this.clearPass + "\n");
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
/*      */   public boolean login() throws LoginException {
/*  548 */     if (this.refreshKrb5Config) {
/*      */       try {
/*  550 */         if (this.debug) {
/*  551 */           System.out.println("Refreshing Kerberos configuration");
/*      */         }
/*  553 */         Config.refresh();
/*  554 */       } catch (KrbException krbException) {
/*  555 */         LoginException loginException = new LoginException(krbException.getMessage());
/*  556 */         loginException.initCause(krbException);
/*  557 */         throw loginException;
/*      */       } 
/*      */     }
/*      */     
/*  561 */     String str = System.getProperty("sun.security.krb5.principal");
/*  562 */     if (str != null) {
/*  563 */       this.krb5PrincName = new StringBuffer(str);
/*      */     }
/*  565 */     else if (this.princName != null) {
/*  566 */       this.krb5PrincName = new StringBuffer(this.princName);
/*      */     } 
/*      */ 
/*      */     
/*  570 */     validateConfiguration();
/*      */     
/*  572 */     if (this.krb5PrincName != null && this.krb5PrincName.toString().equals("*")) {
/*  573 */       this.unboundServer = true;
/*      */     }
/*      */     
/*  576 */     if (this.tryFirstPass) {
/*      */       try {
/*  578 */         attemptAuthentication(true);
/*  579 */         if (this.debug) {
/*  580 */           System.out.println("\t\t[Krb5LoginModule] authentication succeeded");
/*      */         }
/*  582 */         this.succeeded = true;
/*  583 */         cleanState();
/*  584 */         return true;
/*  585 */       } catch (LoginException loginException) {
/*      */         
/*  587 */         cleanState();
/*  588 */         if (this.debug) {
/*  589 */           System.out.println("\t\t[Krb5LoginModule] tryFirstPass failed with:" + loginException
/*      */               
/*  591 */               .getMessage());
/*      */         }
/*      */       } 
/*  594 */     } else if (this.useFirstPass) {
/*      */       try {
/*  596 */         attemptAuthentication(true);
/*  597 */         this.succeeded = true;
/*  598 */         cleanState();
/*  599 */         return true;
/*  600 */       } catch (LoginException loginException) {
/*      */         
/*  602 */         if (this.debug) {
/*  603 */           System.out.println("\t\t[Krb5LoginModule] authentication failed \n" + loginException
/*      */               
/*  605 */               .getMessage());
/*      */         }
/*  607 */         this.succeeded = false;
/*  608 */         cleanState();
/*  609 */         throw loginException;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  617 */       attemptAuthentication(false);
/*  618 */       this.succeeded = true;
/*  619 */       cleanState();
/*  620 */       return true;
/*  621 */     } catch (LoginException loginException) {
/*      */       
/*  623 */       if (this.debug) {
/*  624 */         System.out.println("\t\t[Krb5LoginModule] authentication failed \n" + loginException
/*      */             
/*  626 */             .getMessage());
/*      */       }
/*  628 */       this.succeeded = false;
/*  629 */       cleanState();
/*  630 */       throw loginException;
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
/*      */ 
/*      */   
/*      */   private void attemptAuthentication(boolean paramBoolean) throws LoginException {
/*  647 */     if (this.krb5PrincName != null) {
/*      */       try {
/*  649 */         this
/*  650 */           .principal = new PrincipalName(this.krb5PrincName.toString(), 1);
/*      */       }
/*  652 */       catch (KrbException krbException) {
/*  653 */         LoginException loginException = new LoginException(krbException.getMessage());
/*  654 */         loginException.initCause(krbException);
/*  655 */         throw loginException;
/*      */       } 
/*      */     }
/*      */     
/*      */     try {
/*  660 */       if (this.useTicketCache) {
/*      */         
/*  662 */         if (this.debug)
/*  663 */           System.out.println("Acquire TGT from Cache"); 
/*  664 */         this
/*  665 */           .cred = Credentials.acquireTGTFromCache(this.principal, this.ticketCacheName);
/*      */         
/*  667 */         if (this.cred != null)
/*      */         {
/*  669 */           if (!isCurrent(this.cred)) {
/*  670 */             if (this.renewTGT) {
/*  671 */               this.cred = renewCredentials(this.cred);
/*      */             } else {
/*      */               
/*  674 */               this.cred = null;
/*  675 */               if (this.debug) {
/*  676 */                 System.out.println("Credentials are no longer valid");
/*      */               }
/*      */             } 
/*      */           }
/*      */         }
/*      */         
/*  682 */         if (this.cred != null)
/*      */         {
/*  684 */           if (this.principal == null) {
/*  685 */             this.principal = this.cred.getClient();
/*      */           }
/*      */         }
/*  688 */         if (this.debug) {
/*  689 */           System.out.println("Principal is " + this.principal);
/*  690 */           if (this.cred == null) {
/*  691 */             System.out
/*  692 */               .println("null credentials from Ticket Cache");
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  700 */       if (this.cred == null) {
/*      */         KrbAsReqBuilder krbAsReqBuilder;
/*      */         
/*  703 */         if (this.principal == null) {
/*  704 */           promptForName(paramBoolean);
/*  705 */           this
/*  706 */             .principal = new PrincipalName(this.krb5PrincName.toString(), 1);
/*      */         } 
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
/*  729 */         if (this.useKeyTab) {
/*  730 */           if (!this.unboundServer) {
/*      */             
/*  732 */             KerberosPrincipal kerberosPrincipal = new KerberosPrincipal(this.principal.getName());
/*  733 */             this
/*      */               
/*  735 */               .ktab = (this.keyTabName == null) ? KeyTab.getInstance(kerberosPrincipal) : KeyTab.getInstance(kerberosPrincipal, new File(this.keyTabName));
/*      */           } else {
/*  737 */             this
/*      */               
/*  739 */               .ktab = (this.keyTabName == null) ? KeyTab.getUnboundInstance() : KeyTab.getUnboundInstance(new File(this.keyTabName));
/*      */           } 
/*  741 */           if (this.isInitiator && (
/*  742 */             Krb5Util.keysFromJavaxKeyTab(this.ktab, this.principal)).length == 0) {
/*      */             
/*  744 */             this.ktab = null;
/*  745 */             if (this.debug) {
/*  746 */               System.out
/*  747 */                 .println("Key for the principal " + this.principal + " not available in " + ((this.keyTabName == null) ? "default key tab" : this.keyTabName));
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  759 */         if (this.ktab == null) {
/*  760 */           promptForPass(paramBoolean);
/*  761 */           krbAsReqBuilder = new KrbAsReqBuilder(this.principal, this.password);
/*  762 */           if (this.isInitiator)
/*      */           {
/*      */ 
/*      */             
/*  766 */             this.cred = krbAsReqBuilder.action().getCreds();
/*      */           }
/*  768 */           if (this.storeKey) {
/*  769 */             this.encKeys = krbAsReqBuilder.getKeys(this.isInitiator);
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  774 */           krbAsReqBuilder = new KrbAsReqBuilder(this.principal, this.ktab);
/*  775 */           if (this.isInitiator) {
/*  776 */             this.cred = krbAsReqBuilder.action().getCreds();
/*      */           }
/*      */         } 
/*  779 */         krbAsReqBuilder.destroy();
/*      */         
/*  781 */         if (this.debug) {
/*  782 */           System.out.println("principal is " + this.principal);
/*  783 */           HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/*  784 */           if (this.ktab != null) {
/*  785 */             System.out.println("Will use keytab");
/*  786 */           } else if (this.storeKey) {
/*  787 */             for (byte b = 0; b < this.encKeys.length; b++) {
/*  788 */               System.out.println("EncryptionKey: keyType=" + this.encKeys[b]
/*  789 */                   .getEType() + " keyBytes (hex dump)=" + hexDumpEncoder
/*      */                   
/*  791 */                   .encodeBuffer(this.encKeys[b].getBytes()));
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  797 */         if (this.isInitiator && this.cred == null) {
/*  798 */           throw new LoginException("TGT Can not be obtained from the KDC ");
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*  803 */     catch (KrbException krbException) {
/*  804 */       LoginException loginException = new LoginException(krbException.getMessage());
/*  805 */       loginException.initCause(krbException);
/*  806 */       throw loginException;
/*  807 */     } catch (IOException iOException) {
/*  808 */       LoginException loginException = new LoginException(iOException.getMessage());
/*  809 */       loginException.initCause(iOException);
/*  810 */       throw loginException;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void promptForName(boolean paramBoolean) throws LoginException {
/*  816 */     this.krb5PrincName = new StringBuffer("");
/*  817 */     if (paramBoolean) {
/*      */       
/*  819 */       this.username = (String)this.sharedState.get("javax.security.auth.login.name");
/*  820 */       if (this.debug) {
/*  821 */         System.out
/*  822 */           .println("username from shared state is " + this.username + "\n");
/*      */       }
/*  824 */       if (this.username == null) {
/*  825 */         System.out
/*  826 */           .println("username from shared state is null\n");
/*  827 */         throw new LoginException("Username can not be obtained from sharedstate ");
/*      */       } 
/*      */       
/*  830 */       if (this.debug) {
/*  831 */         System.out
/*  832 */           .println("username from shared state is " + this.username + "\n");
/*      */       }
/*  834 */       if (this.username != null && this.username.length() > 0) {
/*  835 */         this.krb5PrincName.insert(0, this.username);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  840 */     if (this.doNotPrompt) {
/*  841 */       throw new LoginException("Unable to obtain Principal Name for authentication ");
/*      */     }
/*      */     
/*  844 */     if (this.callbackHandler == null) {
/*  845 */       throw new LoginException("No CallbackHandler available to garner authentication information from the user");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  850 */       String str = System.getProperty("user.name");
/*      */       
/*  852 */       Callback[] arrayOfCallback = new Callback[1];
/*      */       
/*  854 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Kerberos.username.defUsername."));
/*      */       
/*  856 */       Object[] arrayOfObject = { str };
/*  857 */       arrayOfCallback[0] = new NameCallback(messageFormat.format(arrayOfObject));
/*  858 */       this.callbackHandler.handle(arrayOfCallback);
/*  859 */       this.username = ((NameCallback)arrayOfCallback[0]).getName();
/*  860 */       if (this.username == null || this.username.length() == 0)
/*  861 */         this.username = str; 
/*  862 */       this.krb5PrincName.insert(0, this.username);
/*      */     }
/*  864 */     catch (IOException iOException) {
/*  865 */       throw new LoginException(iOException.getMessage());
/*  866 */     } catch (UnsupportedCallbackException unsupportedCallbackException) {
/*  867 */       throw new LoginException(unsupportedCallbackException
/*  868 */           .getMessage() + " not available to garner  authentication information  from the user");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void promptForPass(boolean paramBoolean) throws LoginException {
/*  879 */     if (paramBoolean) {
/*      */       
/*  881 */       this.password = (char[])this.sharedState.get("javax.security.auth.login.password");
/*  882 */       if (this.password == null) {
/*  883 */         if (this.debug) {
/*  884 */           System.out
/*  885 */             .println("Password from shared state is null");
/*      */         }
/*  887 */         throw new LoginException("Password can not be obtained from sharedstate ");
/*      */       } 
/*      */       
/*  890 */       if (this.debug) {
/*  891 */         System.out
/*  892 */           .println("password is " + new String(this.password));
/*      */       }
/*      */       return;
/*      */     } 
/*  896 */     if (this.doNotPrompt) {
/*  897 */       throw new LoginException("Unable to obtain password from user\n");
/*      */     }
/*      */     
/*  900 */     if (this.callbackHandler == null) {
/*  901 */       throw new LoginException("No CallbackHandler available to garner authentication information from the user");
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  906 */       Callback[] arrayOfCallback = new Callback[1];
/*  907 */       String str = this.krb5PrincName.toString();
/*      */       
/*  909 */       MessageFormat messageFormat = new MessageFormat(rb.getString("Kerberos.password.for.username."));
/*      */       
/*  911 */       Object[] arrayOfObject = { str };
/*  912 */       arrayOfCallback[0] = new PasswordCallback(messageFormat
/*  913 */           .format(arrayOfObject), false);
/*      */       
/*  915 */       this.callbackHandler.handle(arrayOfCallback);
/*      */       
/*  917 */       char[] arrayOfChar = ((PasswordCallback)arrayOfCallback[0]).getPassword();
/*  918 */       if (arrayOfChar == null) {
/*  919 */         throw new LoginException("No password provided");
/*      */       }
/*  921 */       this.password = new char[arrayOfChar.length];
/*  922 */       System.arraycopy(arrayOfChar, 0, this.password, 0, arrayOfChar.length);
/*      */       
/*  924 */       ((PasswordCallback)arrayOfCallback[0]).clearPassword();
/*      */ 
/*      */ 
/*      */       
/*  928 */       for (byte b = 0; b < arrayOfChar.length; b++)
/*  929 */         arrayOfChar[b] = ' '; 
/*  930 */       arrayOfChar = null;
/*  931 */       if (this.debug) {
/*  932 */         System.out.println("\t\t[Krb5LoginModule] user entered username: " + this.krb5PrincName);
/*      */ 
/*      */         
/*  935 */         System.out.println();
/*      */       } 
/*  937 */     } catch (IOException iOException) {
/*  938 */       throw new LoginException(iOException.getMessage());
/*  939 */     } catch (UnsupportedCallbackException unsupportedCallbackException) {
/*  940 */       throw new LoginException(unsupportedCallbackException.getMessage() + " not available to garner  authentication information from the user");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateConfiguration() throws LoginException {
/*  949 */     if (this.doNotPrompt && !this.useTicketCache && !this.useKeyTab && !this.tryFirstPass && !this.useFirstPass)
/*      */     {
/*  951 */       throw new LoginException("Configuration Error - either doNotPrompt should be  false or at least one of useTicketCache,  useKeyTab, tryFirstPass and useFirstPass should be true");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  957 */     if (this.ticketCacheName != null && !this.useTicketCache) {
/*  958 */       throw new LoginException("Configuration Error  - useTicketCache should be set to true to use the ticket cache" + this.ticketCacheName);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  963 */     if ((((this.keyTabName != null) ? 1 : 0) & (!this.useKeyTab ? 1 : 0)) != 0) {
/*  964 */       throw new LoginException("Configuration Error - useKeyTab should be set to true to use the keytab" + this.keyTabName);
/*      */     }
/*      */     
/*  967 */     if (this.storeKey && this.doNotPrompt && !this.useKeyTab && !this.tryFirstPass && !this.useFirstPass)
/*      */     {
/*  969 */       throw new LoginException("Configuration Error - either doNotPrompt should be set to  false or at least one of tryFirstPass, useFirstPass or useKeyTab must be set to true for storeKey option");
/*      */     }
/*      */ 
/*      */     
/*  973 */     if (this.renewTGT && !this.useTicketCache) {
/*  974 */       throw new LoginException("Configuration Error - either useTicketCache should be  true or renewTGT should be false");
/*      */     }
/*      */ 
/*      */     
/*  978 */     if (this.krb5PrincName != null && this.krb5PrincName.toString().equals("*") && 
/*  979 */       this.isInitiator) {
/*  980 */       throw new LoginException("Configuration Error - principal cannot be * when isInitiator is true");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isCurrent(Credentials paramCredentials) {
/*  989 */     Date date = paramCredentials.getEndTime();
/*  990 */     if (date != null) {
/*  991 */       return (System.currentTimeMillis() <= date.getTime());
/*      */     }
/*  993 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private Credentials renewCredentials(Credentials paramCredentials) {
/*      */     Credentials credentials;
/*      */     try {
/* 1000 */       if (!paramCredentials.isRenewable()) {
/* 1001 */         throw new RefreshFailedException("This ticket is not renewable");
/*      */       }
/* 1003 */       if (System.currentTimeMillis() > this.cred.getRenewTill().getTime()) {
/* 1004 */         throw new RefreshFailedException("This ticket is past its last renewal time.");
/*      */       }
/* 1006 */       credentials = paramCredentials.renew();
/* 1007 */       if (this.debug)
/* 1008 */         System.out.println("Renewed Kerberos Ticket"); 
/* 1009 */     } catch (Exception exception) {
/* 1010 */       credentials = null;
/* 1011 */       if (this.debug)
/* 1012 */         System.out.println("Ticket could not be renewed : " + exception
/* 1013 */             .getMessage()); 
/*      */     } 
/* 1015 */     return credentials;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean commit() throws LoginException {
/* 1049 */     if (!this.succeeded) {
/* 1050 */       return false;
/*      */     }
/*      */     
/* 1053 */     if (this.isInitiator && this.cred == null) {
/* 1054 */       this.succeeded = false;
/* 1055 */       throw new LoginException("Null Client Credential");
/*      */     } 
/*      */     
/* 1058 */     if (this.subject.isReadOnly()) {
/* 1059 */       cleanKerberosCred();
/* 1060 */       throw new LoginException("Subject is Readonly");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1070 */     Set<Object> set = this.subject.getPrivateCredentials();
/* 1071 */     Set<Principal> set1 = this.subject.getPrincipals();
/* 1072 */     this.kerbClientPrinc = new KerberosPrincipal(this.principal.getName());
/*      */ 
/*      */     
/* 1075 */     if (this.isInitiator) {
/* 1076 */       this.kerbTicket = Krb5Util.credsToTicket(this.cred);
/*      */     }
/*      */     
/* 1079 */     if (this.storeKey && this.encKeys != null) {
/* 1080 */       if (this.encKeys.length == 0) {
/* 1081 */         this.succeeded = false;
/* 1082 */         throw new LoginException("Null Server Key ");
/*      */       } 
/*      */       
/* 1085 */       this.kerbKeys = new KerberosKey[this.encKeys.length];
/* 1086 */       for (byte b = 0; b < this.encKeys.length; b++) {
/* 1087 */         Integer integer = this.encKeys[b].getKeyVersionNumber();
/* 1088 */         this.kerbKeys[b] = new KerberosKey(this.kerbClientPrinc, this.encKeys[b]
/* 1089 */             .getBytes(), this.encKeys[b]
/* 1090 */             .getEType(), (integer == null) ? 0 : integer
/*      */             
/* 1092 */             .intValue());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1100 */     if (!this.unboundServer && 
/* 1101 */       !set1.contains(this.kerbClientPrinc)) {
/* 1102 */       set1.add(this.kerbClientPrinc);
/*      */     }
/*      */ 
/*      */     
/* 1106 */     if (this.kerbTicket != null && 
/* 1107 */       !set.contains(this.kerbTicket)) {
/* 1108 */       set.add(this.kerbTicket);
/*      */     }
/*      */     
/* 1111 */     if (this.storeKey) {
/* 1112 */       if (this.encKeys == null) {
/* 1113 */         if (this.ktab != null) {
/* 1114 */           if (!set.contains(this.ktab)) {
/* 1115 */             set.add(this.ktab);
/*      */           }
/*      */         } else {
/* 1118 */           this.succeeded = false;
/* 1119 */           throw new LoginException("No key to store");
/*      */         } 
/*      */       } else {
/* 1122 */         for (byte b = 0; b < this.kerbKeys.length; b++) {
/* 1123 */           if (!set.contains(this.kerbKeys[b])) {
/* 1124 */             set.add(this.kerbKeys[b]);
/*      */           }
/* 1126 */           this.encKeys[b].destroy();
/* 1127 */           this.encKeys[b] = null;
/* 1128 */           if (this.debug) {
/* 1129 */             System.out.println("Added server's key" + this.kerbKeys[b]);
/*      */             
/* 1131 */             System.out.println("\t\t[Krb5LoginModule] added Krb5Principal  " + this.kerbClientPrinc
/*      */                 
/* 1133 */                 .toString() + " to Subject");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1140 */     this.commitSucceeded = true;
/* 1141 */     if (this.debug)
/* 1142 */       System.out.println("Commit Succeeded \n"); 
/* 1143 */     return true;
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
/*      */   public boolean abort() throws LoginException {
/* 1166 */     if (!this.succeeded)
/* 1167 */       return false; 
/* 1168 */     if (this.succeeded == true && !this.commitSucceeded) {
/*      */       
/* 1170 */       this.succeeded = false;
/* 1171 */       cleanKerberosCred();
/*      */     }
/*      */     else {
/*      */       
/* 1175 */       logout();
/*      */     } 
/* 1177 */     return true;
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
/*      */   public boolean logout() throws LoginException {
/* 1195 */     if (this.debug) {
/* 1196 */       System.out.println("\t\t[Krb5LoginModule]: Entering logout");
/*      */     }
/*      */ 
/*      */     
/* 1200 */     if (this.subject.isReadOnly()) {
/* 1201 */       cleanKerberosCred();
/* 1202 */       throw new LoginException("Subject is Readonly");
/*      */     } 
/*      */     
/* 1205 */     this.subject.getPrincipals().remove(this.kerbClientPrinc);
/*      */     
/* 1207 */     Iterator<Object> iterator = this.subject.getPrivateCredentials().iterator();
/* 1208 */     while (iterator.hasNext()) {
/* 1209 */       Object object = iterator.next();
/* 1210 */       if (object instanceof KerberosTicket || object instanceof KerberosKey || object instanceof KeyTab)
/*      */       {
/*      */         
/* 1213 */         iterator.remove();
/*      */       }
/*      */     } 
/*      */     
/* 1217 */     cleanKerberosCred();
/*      */     
/* 1219 */     this.succeeded = false;
/* 1220 */     this.commitSucceeded = false;
/* 1221 */     if (this.debug) {
/* 1222 */       System.out.println("\t\t[Krb5LoginModule]: logged out Subject");
/*      */     }
/*      */     
/* 1225 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cleanKerberosCred() throws LoginException {
/*      */     try {
/* 1234 */       if (this.kerbTicket != null)
/* 1235 */         this.kerbTicket.destroy(); 
/* 1236 */       if (this.kerbKeys != null) {
/* 1237 */         for (byte b = 0; b < this.kerbKeys.length; b++) {
/* 1238 */           this.kerbKeys[b].destroy();
/*      */         }
/*      */       }
/* 1241 */     } catch (DestroyFailedException destroyFailedException) {
/* 1242 */       throw new LoginException("Destroy Failed on Kerberos Private Credentials");
/*      */     } 
/*      */     
/* 1245 */     this.kerbTicket = null;
/* 1246 */     this.kerbKeys = null;
/* 1247 */     this.kerbClientPrinc = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cleanState() {
/* 1257 */     if (this.succeeded) {
/* 1258 */       if (this.storePass && 
/* 1259 */         !this.sharedState.containsKey("javax.security.auth.login.name") && 
/* 1260 */         !this.sharedState.containsKey("javax.security.auth.login.password")) {
/* 1261 */         this.sharedState.put("javax.security.auth.login.name", this.username);
/* 1262 */         this.sharedState.put("javax.security.auth.login.password", this.password);
/*      */       } 
/*      */     } else {
/*      */       
/* 1266 */       this.encKeys = null;
/* 1267 */       this.ktab = null;
/* 1268 */       this.principal = null;
/*      */     } 
/* 1270 */     this.username = null;
/* 1271 */     this.password = null;
/* 1272 */     if (this.krb5PrincName != null && this.krb5PrincName.length() != 0)
/* 1273 */       this.krb5PrincName.delete(0, this.krb5PrincName.length()); 
/* 1274 */     this.krb5PrincName = null;
/* 1275 */     if (this.clearPass) {
/* 1276 */       this.sharedState.remove("javax.security.auth.login.name");
/* 1277 */       this.sharedState.remove("javax.security.auth.login.password");
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\security\auth\module\Krb5LoginModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */