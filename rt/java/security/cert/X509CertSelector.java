/*      */ package java.security.cert;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.math.BigInteger;
/*      */ import java.security.PublicKey;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.util.DerInputStream;
/*      */ import sun.security.util.DerValue;
/*      */ import sun.security.util.ObjectIdentifier;
/*      */ import sun.security.x509.AlgorithmId;
/*      */ import sun.security.x509.CertificatePoliciesExtension;
/*      */ import sun.security.x509.CertificatePolicyId;
/*      */ import sun.security.x509.CertificatePolicySet;
/*      */ import sun.security.x509.DNSName;
/*      */ import sun.security.x509.EDIPartyName;
/*      */ import sun.security.x509.ExtendedKeyUsageExtension;
/*      */ import sun.security.x509.GeneralName;
/*      */ import sun.security.x509.GeneralNameInterface;
/*      */ import sun.security.x509.GeneralNames;
/*      */ import sun.security.x509.GeneralSubtree;
/*      */ import sun.security.x509.GeneralSubtrees;
/*      */ import sun.security.x509.IPAddressName;
/*      */ import sun.security.x509.NameConstraintsExtension;
/*      */ import sun.security.x509.OIDName;
/*      */ import sun.security.x509.OtherName;
/*      */ import sun.security.x509.PolicyInformation;
/*      */ import sun.security.x509.PrivateKeyUsageExtension;
/*      */ import sun.security.x509.RFC822Name;
/*      */ import sun.security.x509.SubjectAlternativeNameExtension;
/*      */ import sun.security.x509.URIName;
/*      */ import sun.security.x509.X400Address;
/*      */ import sun.security.x509.X500Name;
/*      */ import sun.security.x509.X509CertImpl;
/*      */ import sun.security.x509.X509Key;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class X509CertSelector
/*      */   implements CertSelector
/*      */ {
/*   88 */   private static final Debug debug = Debug.getInstance("certpath");
/*      */ 
/*      */   
/*   91 */   private static final ObjectIdentifier ANY_EXTENDED_KEY_USAGE = ObjectIdentifier.newInternal(new int[] { 2, 5, 29, 37, 0 }); private BigInteger serialNumber; private X500Principal issuer; private X500Principal subject; private byte[] subjectKeyID; private byte[] authorityKeyID;
/*      */   
/*      */   static {
/*   94 */     CertPathHelperImpl.initialize();
/*      */   }
/*      */ 
/*      */   
/*      */   private Date certificateValid;
/*      */   
/*      */   private Date privateKeyValid;
/*      */   
/*      */   private ObjectIdentifier subjectPublicKeyAlgID;
/*      */   
/*      */   private PublicKey subjectPublicKey;
/*      */   
/*      */   private byte[] subjectPublicKeyBytes;
/*      */   private boolean[] keyUsage;
/*      */   private Set<String> keyPurposeSet;
/*      */   private Set<ObjectIdentifier> keyPurposeOIDSet;
/*      */   private Set<List<?>> subjectAlternativeNames;
/*      */   private Set<GeneralNameInterface> subjectAlternativeGeneralNames;
/*      */   private CertificatePolicySet policy;
/*      */   private Set<String> policySet;
/*      */   private Set<List<?>> pathToNames;
/*      */   private Set<GeneralNameInterface> pathToGeneralNames;
/*      */   private NameConstraintsExtension nc;
/*      */   private byte[] ncBytes;
/*  118 */   private int basicConstraints = -1;
/*      */   
/*      */   private X509Certificate x509Cert;
/*      */   private boolean matchAllSubjectAltNames = true;
/*  122 */   private static final Boolean FALSE = Boolean.FALSE;
/*      */   
/*      */   private static final int PRIVATE_KEY_USAGE_ID = 0;
/*      */   private static final int SUBJECT_ALT_NAME_ID = 1;
/*      */   private static final int NAME_CONSTRAINTS_ID = 2;
/*      */   private static final int CERT_POLICIES_ID = 3;
/*      */   private static final int EXTENDED_KEY_USAGE_ID = 4;
/*      */   private static final int NUM_OF_EXTENSIONS = 5;
/*  130 */   private static final String[] EXTENSION_OIDS = new String[5]; static final int NAME_ANY = 0; static final int NAME_RFC822 = 1; static final int NAME_DNS = 2; static final int NAME_X400 = 3;
/*      */   
/*      */   static {
/*  133 */     EXTENSION_OIDS[0] = "2.5.29.16";
/*  134 */     EXTENSION_OIDS[1] = "2.5.29.17";
/*  135 */     EXTENSION_OIDS[2] = "2.5.29.30";
/*  136 */     EXTENSION_OIDS[3] = "2.5.29.32";
/*  137 */     EXTENSION_OIDS[4] = "2.5.29.37";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int NAME_DIRECTORY = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int NAME_EDI = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int NAME_URI = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int NAME_IP = 7;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static final int NAME_OID = 8;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCertificate(X509Certificate paramX509Certificate) {
/*  175 */     this.x509Cert = paramX509Certificate;
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
/*      */   public void setSerialNumber(BigInteger paramBigInteger) {
/*  189 */     this.serialNumber = paramBigInteger;
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
/*      */   public void setIssuer(X500Principal paramX500Principal) {
/*  203 */     this.issuer = paramX500Principal;
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
/*      */   public void setIssuer(String paramString) throws IOException {
/*  227 */     if (paramString == null) {
/*  228 */       this.issuer = null;
/*      */     } else {
/*  230 */       this.issuer = (new X500Name(paramString)).asX500Principal();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIssuer(byte[] paramArrayOfbyte) throws IOException {
/*      */     try {
/*  278 */       this.issuer = (paramArrayOfbyte == null) ? null : new X500Principal(paramArrayOfbyte);
/*  279 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  280 */       throw new IOException("Invalid name", illegalArgumentException);
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
/*      */   public void setSubject(X500Principal paramX500Principal) {
/*  295 */     this.subject = paramX500Principal;
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
/*      */   public void setSubject(String paramString) throws IOException {
/*  318 */     if (paramString == null) {
/*  319 */       this.subject = null;
/*      */     } else {
/*  321 */       this.subject = (new X500Name(paramString)).asX500Principal();
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
/*      */   public void setSubject(byte[] paramArrayOfbyte) throws IOException {
/*      */     try {
/*  342 */       this.subject = (paramArrayOfbyte == null) ? null : new X500Principal(paramArrayOfbyte);
/*  343 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  344 */       throw new IOException("Invalid name", illegalArgumentException);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubjectKeyIdentifier(byte[] paramArrayOfbyte) {
/*  381 */     if (paramArrayOfbyte == null) {
/*  382 */       this.subjectKeyID = null;
/*      */     } else {
/*  384 */       this.subjectKeyID = (byte[])paramArrayOfbyte.clone();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAuthorityKeyIdentifier(byte[] paramArrayOfbyte) {
/*  442 */     if (paramArrayOfbyte == null) {
/*  443 */       this.authorityKeyID = null;
/*      */     } else {
/*  445 */       this.authorityKeyID = (byte[])paramArrayOfbyte.clone();
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
/*      */   public void setCertificateValid(Date paramDate) {
/*  462 */     if (paramDate == null) {
/*  463 */       this.certificateValid = null;
/*      */     } else {
/*  465 */       this.certificateValid = (Date)paramDate.clone();
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
/*      */   public void setPrivateKeyValid(Date paramDate) {
/*  483 */     if (paramDate == null) {
/*  484 */       this.privateKeyValid = null;
/*      */     } else {
/*  486 */       this.privateKeyValid = (Date)paramDate.clone();
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
/*      */   public void setSubjectPublicKeyAlgID(String paramString) throws IOException {
/*  506 */     if (paramString == null) {
/*  507 */       this.subjectPublicKeyAlgID = null;
/*      */     } else {
/*  509 */       this.subjectPublicKeyAlgID = new ObjectIdentifier(paramString);
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
/*      */   public void setSubjectPublicKey(PublicKey paramPublicKey) {
/*  522 */     if (paramPublicKey == null) {
/*  523 */       this.subjectPublicKey = null;
/*  524 */       this.subjectPublicKeyBytes = null;
/*      */     } else {
/*  526 */       this.subjectPublicKey = paramPublicKey;
/*  527 */       this.subjectPublicKeyBytes = paramPublicKey.getEncoded();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubjectPublicKey(byte[] paramArrayOfbyte) throws IOException {
/*  565 */     if (paramArrayOfbyte == null) {
/*  566 */       this.subjectPublicKey = null;
/*  567 */       this.subjectPublicKeyBytes = null;
/*      */     } else {
/*  569 */       this.subjectPublicKeyBytes = (byte[])paramArrayOfbyte.clone();
/*  570 */       this.subjectPublicKey = X509Key.parse(new DerValue(this.subjectPublicKeyBytes));
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
/*      */   public void setKeyUsage(boolean[] paramArrayOfboolean) {
/*  590 */     if (paramArrayOfboolean == null) {
/*  591 */       this.keyUsage = null;
/*      */     } else {
/*  593 */       this.keyUsage = (boolean[])paramArrayOfboolean.clone();
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
/*      */   public void setExtendedKeyUsage(Set<String> paramSet) throws IOException {
/*  617 */     if (paramSet == null || paramSet.isEmpty()) {
/*  618 */       this.keyPurposeSet = null;
/*  619 */       this.keyPurposeOIDSet = null;
/*      */     } else {
/*  621 */       this
/*  622 */         .keyPurposeSet = Collections.unmodifiableSet(new HashSet<>(paramSet));
/*  623 */       this.keyPurposeOIDSet = new HashSet<>();
/*  624 */       for (String str : this.keyPurposeSet) {
/*  625 */         this.keyPurposeOIDSet.add(new ObjectIdentifier(str));
/*      */       }
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
/*      */   public void setMatchAllSubjectAltNames(boolean paramBoolean) {
/*  647 */     this.matchAllSubjectAltNames = paramBoolean;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSubjectAlternativeNames(Collection<List<?>> paramCollection) throws IOException {
/*  699 */     if (paramCollection == null) {
/*  700 */       this.subjectAlternativeNames = null;
/*  701 */       this.subjectAlternativeGeneralNames = null;
/*      */     } else {
/*  703 */       if (paramCollection.isEmpty()) {
/*  704 */         this.subjectAlternativeNames = null;
/*  705 */         this.subjectAlternativeGeneralNames = null;
/*      */         return;
/*      */       } 
/*  708 */       Set<List<?>> set = cloneAndCheckNames(paramCollection);
/*      */       
/*  710 */       this.subjectAlternativeGeneralNames = parseNames(set);
/*  711 */       this.subjectAlternativeNames = set;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSubjectAlternativeName(int paramInt, String paramString) throws IOException {
/*  755 */     addSubjectAlternativeNameInternal(paramInt, paramString);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addSubjectAlternativeName(int paramInt, byte[] paramArrayOfbyte) throws IOException {
/*  800 */     addSubjectAlternativeNameInternal(paramInt, paramArrayOfbyte.clone());
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
/*      */   private void addSubjectAlternativeNameInternal(int paramInt, Object paramObject) throws IOException {
/*  816 */     GeneralNameInterface generalNameInterface = makeGeneralNameInterface(paramInt, paramObject);
/*  817 */     if (this.subjectAlternativeNames == null) {
/*  818 */       this.subjectAlternativeNames = new HashSet<>();
/*      */     }
/*  820 */     if (this.subjectAlternativeGeneralNames == null) {
/*  821 */       this.subjectAlternativeGeneralNames = new HashSet<>();
/*      */     }
/*  823 */     ArrayList<Integer> arrayList = new ArrayList(2);
/*  824 */     arrayList.add(Integer.valueOf(paramInt));
/*  825 */     arrayList.add(paramObject);
/*  826 */     this.subjectAlternativeNames.add(arrayList);
/*  827 */     this.subjectAlternativeGeneralNames.add(generalNameInterface);
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
/*      */   private static Set<GeneralNameInterface> parseNames(Collection<List<?>> paramCollection) throws IOException {
/*  848 */     HashSet<GeneralNameInterface> hashSet = new HashSet();
/*  849 */     for (List<Object> list : paramCollection) {
/*  850 */       if (list.size() != 2) {
/*  851 */         throw new IOException("name list size not 2");
/*      */       }
/*  853 */       Integer integer = (Integer)list.get(0);
/*  854 */       if (!(integer instanceof Integer)) {
/*  855 */         throw new IOException("expected an Integer");
/*      */       }
/*  857 */       int i = ((Integer)integer).intValue();
/*  858 */       integer = (Integer)list.get(1);
/*  859 */       hashSet.add(makeGeneralNameInterface(i, integer));
/*      */     } 
/*      */     
/*  862 */     return hashSet;
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
/*      */   static boolean equalNames(Collection<?> paramCollection1, Collection<?> paramCollection2) {
/*  876 */     if (paramCollection1 == null || paramCollection2 == null) {
/*  877 */       return (paramCollection1 == paramCollection2);
/*      */     }
/*  879 */     return paramCollection1.equals(paramCollection2);
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
/*      */   static GeneralNameInterface makeGeneralNameInterface(int paramInt, Object paramObject) throws IOException {
/*      */     OIDName oIDName;
/*  900 */     if (debug != null) {
/*  901 */       debug.println("X509CertSelector.makeGeneralNameInterface(" + paramInt + ")...");
/*      */     }
/*      */ 
/*      */     
/*  905 */     if (paramObject instanceof String) {
/*  906 */       RFC822Name rFC822Name; DNSName dNSName; X500Name x500Name; URIName uRIName; IPAddressName iPAddressName; if (debug != null) {
/*  907 */         debug.println("X509CertSelector.makeGeneralNameInterface() name is String: " + paramObject);
/*      */       }
/*      */       
/*  910 */       switch (paramInt) {
/*      */         case 1:
/*  912 */           rFC822Name = new RFC822Name((String)paramObject);
/*      */           break;
/*      */         case 2:
/*  915 */           dNSName = new DNSName((String)paramObject);
/*      */           break;
/*      */         case 4:
/*  918 */           x500Name = new X500Name((String)paramObject);
/*      */           break;
/*      */         case 6:
/*  921 */           uRIName = new URIName((String)paramObject);
/*      */           break;
/*      */         case 7:
/*  924 */           iPAddressName = new IPAddressName((String)paramObject);
/*      */           break;
/*      */         case 8:
/*  927 */           oIDName = new OIDName((String)paramObject);
/*      */           break;
/*      */         default:
/*  930 */           throw new IOException("unable to parse String names of type " + paramInt);
/*      */       } 
/*      */       
/*  933 */       if (debug != null) {
/*  934 */         debug.println("X509CertSelector.makeGeneralNameInterface() result: " + oIDName
/*  935 */             .toString());
/*      */       }
/*  937 */     } else if (paramObject instanceof byte[]) {
/*  938 */       OtherName otherName; RFC822Name rFC822Name; DNSName dNSName; X400Address x400Address; X500Name x500Name; EDIPartyName eDIPartyName; URIName uRIName; IPAddressName iPAddressName; DerValue derValue = new DerValue((byte[])paramObject);
/*  939 */       if (debug != null) {
/*  940 */         debug
/*  941 */           .println("X509CertSelector.makeGeneralNameInterface() is byte[]");
/*      */       }
/*      */       
/*  944 */       switch (paramInt) {
/*      */         case 0:
/*  946 */           otherName = new OtherName(derValue);
/*      */           break;
/*      */         case 1:
/*  949 */           rFC822Name = new RFC822Name(derValue);
/*      */           break;
/*      */         case 2:
/*  952 */           dNSName = new DNSName(derValue);
/*      */           break;
/*      */         case 3:
/*  955 */           x400Address = new X400Address(derValue);
/*      */           break;
/*      */         case 4:
/*  958 */           x500Name = new X500Name(derValue);
/*      */           break;
/*      */         case 5:
/*  961 */           eDIPartyName = new EDIPartyName(derValue);
/*      */           break;
/*      */         case 6:
/*  964 */           uRIName = new URIName(derValue);
/*      */           break;
/*      */         case 7:
/*  967 */           iPAddressName = new IPAddressName(derValue);
/*      */           break;
/*      */         case 8:
/*  970 */           oIDName = new OIDName(derValue);
/*      */           break;
/*      */         default:
/*  973 */           throw new IOException("unable to parse byte array names of type " + paramInt);
/*      */       } 
/*      */       
/*  976 */       if (debug != null) {
/*  977 */         debug.println("X509CertSelector.makeGeneralNameInterface() result: " + oIDName
/*  978 */             .toString());
/*      */       }
/*      */     } else {
/*  981 */       if (debug != null) {
/*  982 */         debug.println("X509CertSelector.makeGeneralName() input name not String or byte array");
/*      */       }
/*      */       
/*  985 */       throw new IOException("name not String or byte array");
/*      */     } 
/*  987 */     return oIDName;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNameConstraints(byte[] paramArrayOfbyte) throws IOException {
/* 1040 */     if (paramArrayOfbyte == null) {
/* 1041 */       this.ncBytes = null;
/* 1042 */       this.nc = null;
/*      */     } else {
/* 1044 */       this.ncBytes = (byte[])paramArrayOfbyte.clone();
/* 1045 */       this.nc = new NameConstraintsExtension(FALSE, paramArrayOfbyte);
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
/*      */   public void setBasicConstraints(int paramInt) {
/* 1066 */     if (paramInt < -2) {
/* 1067 */       throw new IllegalArgumentException("basic constraints less than -2");
/*      */     }
/* 1069 */     this.basicConstraints = paramInt;
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
/*      */   public void setPolicy(Set<String> paramSet) throws IOException {
/* 1093 */     if (paramSet == null) {
/* 1094 */       this.policySet = null;
/* 1095 */       this.policy = null;
/*      */     }
/*      */     else {
/*      */       
/* 1099 */       Set<?> set = Collections.unmodifiableSet(new HashSet(paramSet));
/*      */       
/* 1101 */       Iterator<?> iterator = set.iterator();
/* 1102 */       Vector<CertificatePolicyId> vector = new Vector();
/* 1103 */       while (iterator.hasNext()) {
/* 1104 */         Object object = iterator.next();
/* 1105 */         if (!(object instanceof String)) {
/* 1106 */           throw new IOException("non String in certPolicySet");
/*      */         }
/* 1108 */         vector.add(new CertificatePolicyId(new ObjectIdentifier((String)object)));
/*      */       } 
/*      */ 
/*      */       
/* 1112 */       this.policySet = (Set)set;
/* 1113 */       this.policy = new CertificatePolicySet(vector);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPathToNames(Collection<List<?>> paramCollection) throws IOException {
/* 1169 */     if (paramCollection == null || paramCollection.isEmpty()) {
/* 1170 */       this.pathToNames = null;
/* 1171 */       this.pathToGeneralNames = null;
/*      */     } else {
/* 1173 */       Set<List<?>> set = cloneAndCheckNames(paramCollection);
/* 1174 */       this.pathToGeneralNames = parseNames(set);
/*      */       
/* 1176 */       this.pathToNames = set;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void setPathToNamesInternal(Set<GeneralNameInterface> paramSet) {
/* 1184 */     this.pathToNames = Collections.emptySet();
/* 1185 */     this.pathToGeneralNames = paramSet;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPathToName(int paramInt, String paramString) throws IOException {
/* 1222 */     addPathToNameInternal(paramInt, paramString);
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
/*      */   public void addPathToName(int paramInt, byte[] paramArrayOfbyte) throws IOException {
/* 1252 */     addPathToNameInternal(paramInt, paramArrayOfbyte.clone());
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
/*      */   private void addPathToNameInternal(int paramInt, Object paramObject) throws IOException {
/* 1268 */     GeneralNameInterface generalNameInterface = makeGeneralNameInterface(paramInt, paramObject);
/* 1269 */     if (this.pathToGeneralNames == null) {
/* 1270 */       this.pathToNames = new HashSet<>();
/* 1271 */       this.pathToGeneralNames = new HashSet<>();
/*      */     } 
/* 1273 */     ArrayList<Integer> arrayList = new ArrayList(2);
/* 1274 */     arrayList.add(Integer.valueOf(paramInt));
/* 1275 */     arrayList.add(paramObject);
/* 1276 */     this.pathToNames.add(arrayList);
/* 1277 */     this.pathToGeneralNames.add(generalNameInterface);
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
/*      */   public X509Certificate getCertificate() {
/* 1290 */     return this.x509Cert;
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
/*      */   public BigInteger getSerialNumber() {
/* 1304 */     return this.serialNumber;
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
/*      */   public X500Principal getIssuer() {
/* 1318 */     return this.issuer;
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
/*      */   public String getIssuerAsString() {
/* 1340 */     return (this.issuer == null) ? null : this.issuer.getName();
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
/*      */   public byte[] getIssuerAsBytes() throws IOException {
/* 1363 */     return (this.issuer == null) ? null : this.issuer.getEncoded();
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
/*      */   public X500Principal getSubject() {
/* 1377 */     return this.subject;
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
/*      */   public String getSubjectAsString() {
/* 1399 */     return (this.subject == null) ? null : this.subject.getName();
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
/*      */   public byte[] getSubjectAsBytes() throws IOException {
/* 1422 */     return (this.subject == null) ? null : this.subject.getEncoded();
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
/*      */   public byte[] getSubjectKeyIdentifier() {
/* 1438 */     if (this.subjectKeyID == null) {
/* 1439 */       return null;
/*      */     }
/* 1441 */     return (byte[])this.subjectKeyID.clone();
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
/*      */   public byte[] getAuthorityKeyIdentifier() {
/* 1457 */     if (this.authorityKeyID == null) {
/* 1458 */       return null;
/*      */     }
/* 1460 */     return (byte[])this.authorityKeyID.clone();
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
/*      */   public Date getCertificateValid() {
/* 1476 */     if (this.certificateValid == null) {
/* 1477 */       return null;
/*      */     }
/* 1479 */     return (Date)this.certificateValid.clone();
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
/*      */   public Date getPrivateKeyValid() {
/* 1495 */     if (this.privateKeyValid == null) {
/* 1496 */       return null;
/*      */     }
/* 1498 */     return (Date)this.privateKeyValid.clone();
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
/*      */   public String getSubjectPublicKeyAlgID() {
/* 1513 */     if (this.subjectPublicKeyAlgID == null) {
/* 1514 */       return null;
/*      */     }
/* 1516 */     return this.subjectPublicKeyAlgID.toString();
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
/*      */   public PublicKey getSubjectPublicKey() {
/* 1528 */     return this.subjectPublicKey;
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
/*      */   public boolean[] getKeyUsage() {
/* 1546 */     if (this.keyUsage == null) {
/* 1547 */       return null;
/*      */     }
/* 1549 */     return (boolean[])this.keyUsage.clone();
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
/*      */   public Set<String> getExtendedKeyUsage() {
/* 1565 */     return this.keyPurposeSet;
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
/*      */   public boolean getMatchAllSubjectAltNames() {
/* 1585 */     return this.matchAllSubjectAltNames;
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
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<List<?>> getSubjectAlternativeNames() {
/* 1622 */     if (this.subjectAlternativeNames == null) {
/* 1623 */       return null;
/*      */     }
/* 1625 */     return cloneNames(this.subjectAlternativeNames);
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
/*      */   private static Set<List<?>> cloneNames(Collection<List<?>> paramCollection) {
/*      */     try {
/* 1650 */       return cloneAndCheckNames(paramCollection);
/* 1651 */     } catch (IOException iOException) {
/* 1652 */       throw new RuntimeException("cloneNames encountered IOException: " + iOException
/* 1653 */           .getMessage());
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
/*      */   private static Set<List<?>> cloneAndCheckNames(Collection<List<?>> paramCollection) throws IOException {
/* 1674 */     HashSet<List<?>> hashSet = new HashSet();
/* 1675 */     for (List<?> list : paramCollection)
/*      */     {
/* 1677 */       hashSet.add(new ArrayList(list));
/*      */     }
/*      */ 
/*      */     
/* 1681 */     for (List<Object> list1 : (Iterable<List<Object>>)hashSet) {
/*      */       
/* 1683 */       List<Object> list2 = list1;
/* 1684 */       if (list2.size() != 2) {
/* 1685 */         throw new IOException("name list size not 2");
/*      */       }
/* 1687 */       Integer integer = (Integer)list2.get(0);
/* 1688 */       if (!(integer instanceof Integer)) {
/* 1689 */         throw new IOException("expected an Integer");
/*      */       }
/* 1691 */       int i = ((Integer)integer).intValue();
/* 1692 */       if (i < 0 || i > 8) {
/* 1693 */         throw new IOException("name type not 0-8");
/*      */       }
/* 1695 */       byte[] arrayOfByte = (byte[])list2.get(1);
/* 1696 */       if (!(arrayOfByte instanceof byte[]) && !(arrayOfByte instanceof String)) {
/*      */         
/* 1698 */         if (debug != null) {
/* 1699 */           debug.println("X509CertSelector.cloneAndCheckNames() name not byte array");
/*      */         }
/*      */         
/* 1702 */         throw new IOException("name not byte array or String");
/*      */       } 
/* 1704 */       if (arrayOfByte instanceof byte[]) {
/* 1705 */         list2.set(1, ((byte[])arrayOfByte).clone());
/*      */       }
/*      */     } 
/* 1708 */     return hashSet;
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
/*      */   public byte[] getNameConstraints() {
/* 1732 */     if (this.ncBytes == null) {
/* 1733 */       return null;
/*      */     }
/* 1735 */     return (byte[])this.ncBytes.clone();
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
/*      */   public int getBasicConstraints() {
/* 1750 */     return this.basicConstraints;
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
/*      */   public Set<String> getPolicy() {
/* 1766 */     return this.policySet;
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
/*      */   
/*      */   public Collection<List<?>> getPathToNames() {
/* 1801 */     if (this.pathToNames == null) {
/* 1802 */       return null;
/*      */     }
/* 1804 */     return cloneNames(this.pathToNames);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1814 */     StringBuffer stringBuffer = new StringBuffer();
/* 1815 */     stringBuffer.append("X509CertSelector: [\n");
/* 1816 */     if (this.x509Cert != null) {
/* 1817 */       stringBuffer.append("  Certificate: " + this.x509Cert.toString() + "\n");
/*      */     }
/* 1819 */     if (this.serialNumber != null) {
/* 1820 */       stringBuffer.append("  Serial Number: " + this.serialNumber.toString() + "\n");
/*      */     }
/* 1822 */     if (this.issuer != null) {
/* 1823 */       stringBuffer.append("  Issuer: " + getIssuerAsString() + "\n");
/*      */     }
/* 1825 */     if (this.subject != null) {
/* 1826 */       stringBuffer.append("  Subject: " + getSubjectAsString() + "\n");
/*      */     }
/* 1828 */     stringBuffer.append("  matchAllSubjectAltNames flag: " + 
/* 1829 */         String.valueOf(this.matchAllSubjectAltNames) + "\n");
/* 1830 */     if (this.subjectAlternativeNames != null) {
/* 1831 */       stringBuffer.append("  SubjectAlternativeNames:\n");
/* 1832 */       Iterator<List<?>> iterator = this.subjectAlternativeNames.iterator();
/* 1833 */       while (iterator.hasNext()) {
/* 1834 */         List<String> list = (List)iterator.next();
/* 1835 */         stringBuffer.append("    type " + list.get(0) + ", name " + list
/* 1836 */             .get(1) + "\n");
/*      */       } 
/*      */     } 
/* 1839 */     if (this.subjectKeyID != null) {
/* 1840 */       HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 1841 */       stringBuffer.append("  Subject Key Identifier: " + hexDumpEncoder
/* 1842 */           .encodeBuffer(this.subjectKeyID) + "\n");
/*      */     } 
/* 1844 */     if (this.authorityKeyID != null) {
/* 1845 */       HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
/* 1846 */       stringBuffer.append("  Authority Key Identifier: " + hexDumpEncoder
/* 1847 */           .encodeBuffer(this.authorityKeyID) + "\n");
/*      */     } 
/* 1849 */     if (this.certificateValid != null) {
/* 1850 */       stringBuffer.append("  Certificate Valid: " + this.certificateValid
/* 1851 */           .toString() + "\n");
/*      */     }
/* 1853 */     if (this.privateKeyValid != null) {
/* 1854 */       stringBuffer.append("  Private Key Valid: " + this.privateKeyValid
/* 1855 */           .toString() + "\n");
/*      */     }
/* 1857 */     if (this.subjectPublicKeyAlgID != null) {
/* 1858 */       stringBuffer.append("  Subject Public Key AlgID: " + this.subjectPublicKeyAlgID
/* 1859 */           .toString() + "\n");
/*      */     }
/* 1861 */     if (this.subjectPublicKey != null) {
/* 1862 */       stringBuffer.append("  Subject Public Key: " + this.subjectPublicKey
/* 1863 */           .toString() + "\n");
/*      */     }
/* 1865 */     if (this.keyUsage != null) {
/* 1866 */       stringBuffer.append("  Key Usage: " + keyUsageToString(this.keyUsage) + "\n");
/*      */     }
/* 1868 */     if (this.keyPurposeSet != null) {
/* 1869 */       stringBuffer.append("  Extended Key Usage: " + this.keyPurposeSet
/* 1870 */           .toString() + "\n");
/*      */     }
/* 1872 */     if (this.policy != null) {
/* 1873 */       stringBuffer.append("  Policy: " + this.policy.toString() + "\n");
/*      */     }
/* 1875 */     if (this.pathToGeneralNames != null) {
/* 1876 */       stringBuffer.append("  Path to names:\n");
/* 1877 */       Iterator<GeneralNameInterface> iterator = this.pathToGeneralNames.iterator();
/* 1878 */       while (iterator.hasNext()) {
/* 1879 */         stringBuffer.append("    " + iterator.next() + "\n");
/*      */       }
/*      */     } 
/* 1882 */     stringBuffer.append("]");
/* 1883 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String keyUsageToString(boolean[] paramArrayOfboolean) {
/* 1892 */     String str = "KeyUsage [\n";
/*      */     try {
/* 1894 */       if (paramArrayOfboolean[0]) {
/* 1895 */         str = str + "  DigitalSignature\n";
/*      */       }
/* 1897 */       if (paramArrayOfboolean[1]) {
/* 1898 */         str = str + "  Non_repudiation\n";
/*      */       }
/* 1900 */       if (paramArrayOfboolean[2]) {
/* 1901 */         str = str + "  Key_Encipherment\n";
/*      */       }
/* 1903 */       if (paramArrayOfboolean[3]) {
/* 1904 */         str = str + "  Data_Encipherment\n";
/*      */       }
/* 1906 */       if (paramArrayOfboolean[4]) {
/* 1907 */         str = str + "  Key_Agreement\n";
/*      */       }
/* 1909 */       if (paramArrayOfboolean[5]) {
/* 1910 */         str = str + "  Key_CertSign\n";
/*      */       }
/* 1912 */       if (paramArrayOfboolean[6]) {
/* 1913 */         str = str + "  Crl_Sign\n";
/*      */       }
/* 1915 */       if (paramArrayOfboolean[7]) {
/* 1916 */         str = str + "  Encipher_Only\n";
/*      */       }
/* 1918 */       if (paramArrayOfboolean[8]) {
/* 1919 */         str = str + "  Decipher_Only\n";
/*      */       }
/* 1921 */     } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*      */     
/* 1923 */     str = str + "]\n";
/*      */     
/* 1925 */     return str;
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
/*      */   private static Extension getExtensionObject(X509Certificate paramX509Certificate, int paramInt) throws IOException {
/* 1949 */     if (paramX509Certificate instanceof X509CertImpl) {
/* 1950 */       X509CertImpl x509CertImpl = (X509CertImpl)paramX509Certificate;
/* 1951 */       switch (paramInt) {
/*      */         case 0:
/* 1953 */           return x509CertImpl.getPrivateKeyUsageExtension();
/*      */         case 1:
/* 1955 */           return x509CertImpl.getSubjectAlternativeNameExtension();
/*      */         case 2:
/* 1957 */           return x509CertImpl.getNameConstraintsExtension();
/*      */         case 3:
/* 1959 */           return x509CertImpl.getCertificatePoliciesExtension();
/*      */         case 4:
/* 1961 */           return x509CertImpl.getExtendedKeyUsageExtension();
/*      */       } 
/* 1963 */       return null;
/*      */     } 
/*      */     
/* 1966 */     byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue(EXTENSION_OIDS[paramInt]);
/* 1967 */     if (arrayOfByte1 == null) {
/* 1968 */       return null;
/*      */     }
/* 1970 */     DerInputStream derInputStream = new DerInputStream(arrayOfByte1);
/* 1971 */     byte[] arrayOfByte2 = derInputStream.getOctetString();
/* 1972 */     switch (paramInt) {
/*      */       case 0:
/*      */         try {
/* 1975 */           return new PrivateKeyUsageExtension(FALSE, arrayOfByte2);
/* 1976 */         } catch (CertificateException certificateException) {
/* 1977 */           throw new IOException(certificateException.getMessage());
/*      */         } 
/*      */       case 1:
/* 1980 */         return new SubjectAlternativeNameExtension(FALSE, arrayOfByte2);
/*      */       case 2:
/* 1982 */         return new NameConstraintsExtension(FALSE, arrayOfByte2);
/*      */       case 3:
/* 1984 */         return new CertificatePoliciesExtension(FALSE, arrayOfByte2);
/*      */       case 4:
/* 1986 */         return new ExtendedKeyUsageExtension(FALSE, arrayOfByte2);
/*      */     } 
/* 1988 */     return null;
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
/*      */   public boolean match(Certificate paramCertificate) {
/* 2000 */     if (!(paramCertificate instanceof X509Certificate)) {
/* 2001 */       return false;
/*      */     }
/* 2003 */     X509Certificate x509Certificate = (X509Certificate)paramCertificate;
/*      */     
/* 2005 */     if (debug != null) {
/* 2006 */       debug.println("X509CertSelector.match(SN: " + x509Certificate
/* 2007 */           .getSerialNumber().toString(16) + "\n  Issuer: " + x509Certificate
/* 2008 */           .getIssuerDN() + "\n  Subject: " + x509Certificate.getSubjectDN() + ")");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2013 */     if (this.x509Cert != null && 
/* 2014 */       !this.x509Cert.equals(x509Certificate)) {
/* 2015 */       if (debug != null) {
/* 2016 */         debug.println("X509CertSelector.match: certs don't match");
/*      */       }
/*      */       
/* 2019 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2024 */     if (this.serialNumber != null && 
/* 2025 */       !this.serialNumber.equals(x509Certificate.getSerialNumber())) {
/* 2026 */       if (debug != null) {
/* 2027 */         debug.println("X509CertSelector.match: serial numbers don't match");
/*      */       }
/*      */       
/* 2030 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2035 */     if (this.issuer != null && 
/* 2036 */       !this.issuer.equals(x509Certificate.getIssuerX500Principal())) {
/* 2037 */       if (debug != null) {
/* 2038 */         debug.println("X509CertSelector.match: issuer DNs don't match");
/*      */       }
/*      */       
/* 2041 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2046 */     if (this.subject != null && 
/* 2047 */       !this.subject.equals(x509Certificate.getSubjectX500Principal())) {
/* 2048 */       if (debug != null) {
/* 2049 */         debug.println("X509CertSelector.match: subject DNs don't match");
/*      */       }
/*      */       
/* 2052 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2057 */     if (this.certificateValid != null) {
/*      */       try {
/* 2059 */         x509Certificate.checkValidity(this.certificateValid);
/* 2060 */       } catch (CertificateException certificateException) {
/* 2061 */         if (debug != null) {
/* 2062 */           debug.println("X509CertSelector.match: certificate not within validity period");
/*      */         }
/*      */         
/* 2065 */         return false;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2070 */     if (this.subjectPublicKeyBytes != null) {
/* 2071 */       byte[] arrayOfByte = x509Certificate.getPublicKey().getEncoded();
/* 2072 */       if (!Arrays.equals(this.subjectPublicKeyBytes, arrayOfByte)) {
/* 2073 */         if (debug != null) {
/* 2074 */           debug.println("X509CertSelector.match: subject public keys don't match");
/*      */         }
/*      */         
/* 2077 */         return false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2091 */     boolean bool = (matchBasicConstraints(x509Certificate) && matchKeyUsage(x509Certificate) && matchExtendedKeyUsage(x509Certificate) && matchSubjectKeyID(x509Certificate) && matchAuthorityKeyID(x509Certificate) && matchPrivateKeyValid(x509Certificate) && matchSubjectPublicKeyAlgID(x509Certificate) && matchPolicy(x509Certificate) && matchSubjectAlternativeNames(x509Certificate) && matchPathToNames(x509Certificate) && matchNameConstraints(x509Certificate)) ? true : false;
/*      */     
/* 2093 */     if (bool && debug != null) {
/* 2094 */       debug.println("X509CertSelector.match returning: true");
/*      */     }
/* 2096 */     return bool;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchSubjectKeyID(X509Certificate paramX509Certificate) {
/* 2101 */     if (this.subjectKeyID == null) {
/* 2102 */       return true;
/*      */     }
/*      */     try {
/* 2105 */       byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.14");
/* 2106 */       if (arrayOfByte1 == null) {
/* 2107 */         if (debug != null) {
/* 2108 */           debug.println("X509CertSelector.match: no subject key ID extension");
/*      */         }
/*      */         
/* 2111 */         return false;
/*      */       } 
/* 2113 */       DerInputStream derInputStream = new DerInputStream(arrayOfByte1);
/* 2114 */       byte[] arrayOfByte2 = derInputStream.getOctetString();
/* 2115 */       if (arrayOfByte2 == null || 
/* 2116 */         !Arrays.equals(this.subjectKeyID, arrayOfByte2)) {
/* 2117 */         if (debug != null) {
/* 2118 */           debug.println("X509CertSelector.match: subject key IDs don't match");
/*      */         }
/*      */         
/* 2121 */         return false;
/*      */       } 
/* 2123 */     } catch (IOException iOException) {
/* 2124 */       if (debug != null) {
/* 2125 */         debug.println("X509CertSelector.match: exception in subject key ID check");
/*      */       }
/*      */       
/* 2128 */       return false;
/*      */     } 
/* 2130 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchAuthorityKeyID(X509Certificate paramX509Certificate) {
/* 2135 */     if (this.authorityKeyID == null) {
/* 2136 */       return true;
/*      */     }
/*      */     try {
/* 2139 */       byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.35");
/* 2140 */       if (arrayOfByte1 == null) {
/* 2141 */         if (debug != null) {
/* 2142 */           debug.println("X509CertSelector.match: no authority key ID extension");
/*      */         }
/*      */         
/* 2145 */         return false;
/*      */       } 
/* 2147 */       DerInputStream derInputStream = new DerInputStream(arrayOfByte1);
/* 2148 */       byte[] arrayOfByte2 = derInputStream.getOctetString();
/* 2149 */       if (arrayOfByte2 == null || 
/* 2150 */         !Arrays.equals(this.authorityKeyID, arrayOfByte2)) {
/* 2151 */         if (debug != null) {
/* 2152 */           debug.println("X509CertSelector.match: authority key IDs don't match");
/*      */         }
/*      */         
/* 2155 */         return false;
/*      */       } 
/* 2157 */     } catch (IOException iOException) {
/* 2158 */       if (debug != null) {
/* 2159 */         debug.println("X509CertSelector.match: exception in authority key ID check");
/*      */       }
/*      */       
/* 2162 */       return false;
/*      */     } 
/* 2164 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchPrivateKeyValid(X509Certificate paramX509Certificate) {
/* 2169 */     if (this.privateKeyValid == null) {
/* 2170 */       return true;
/*      */     }
/* 2172 */     PrivateKeyUsageExtension privateKeyUsageExtension = null;
/*      */     
/*      */     try {
/* 2175 */       privateKeyUsageExtension = (PrivateKeyUsageExtension)getExtensionObject(paramX509Certificate, 0);
/* 2176 */       if (privateKeyUsageExtension != null) {
/* 2177 */         privateKeyUsageExtension.valid(this.privateKeyValid);
/*      */       }
/* 2179 */     } catch (CertificateExpiredException certificateExpiredException) {
/* 2180 */       if (debug != null) {
/* 2181 */         String str = "n/a";
/*      */         try {
/* 2183 */           Date date = privateKeyUsageExtension.get("not_after");
/* 2184 */           str = date.toString();
/* 2185 */         } catch (CertificateException certificateException) {}
/*      */ 
/*      */         
/* 2188 */         debug.println("X509CertSelector.match: private key usage not within validity date; ext.NOT_After: " + str + "; X509CertSelector: " + 
/*      */ 
/*      */             
/* 2191 */             toString());
/* 2192 */         certificateExpiredException.printStackTrace();
/*      */       } 
/* 2194 */       return false;
/* 2195 */     } catch (CertificateNotYetValidException certificateNotYetValidException) {
/* 2196 */       if (debug != null) {
/* 2197 */         String str = "n/a";
/*      */         try {
/* 2199 */           Date date = privateKeyUsageExtension.get("not_before");
/* 2200 */           str = date.toString();
/* 2201 */         } catch (CertificateException certificateException) {}
/*      */ 
/*      */         
/* 2204 */         debug.println("X509CertSelector.match: private key usage not within validity date; ext.NOT_BEFORE: " + str + "; X509CertSelector: " + 
/*      */ 
/*      */             
/* 2207 */             toString());
/* 2208 */         certificateNotYetValidException.printStackTrace();
/*      */       } 
/* 2210 */       return false;
/* 2211 */     } catch (IOException iOException) {
/* 2212 */       if (debug != null) {
/* 2213 */         debug.println("X509CertSelector.match: IOException in private key usage check; X509CertSelector: " + 
/*      */             
/* 2215 */             toString());
/* 2216 */         iOException.printStackTrace();
/*      */       } 
/* 2218 */       return false;
/*      */     } 
/* 2220 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchSubjectPublicKeyAlgID(X509Certificate paramX509Certificate) {
/* 2225 */     if (this.subjectPublicKeyAlgID == null) {
/* 2226 */       return true;
/*      */     }
/*      */     try {
/* 2229 */       byte[] arrayOfByte = paramX509Certificate.getPublicKey().getEncoded();
/* 2230 */       DerValue derValue = new DerValue(arrayOfByte);
/* 2231 */       if (derValue.tag != 48) {
/* 2232 */         throw new IOException("invalid key format");
/*      */       }
/*      */       
/* 2235 */       AlgorithmId algorithmId = AlgorithmId.parse(derValue.data.getDerValue());
/* 2236 */       if (debug != null) {
/* 2237 */         debug.println("X509CertSelector.match: subjectPublicKeyAlgID = " + this.subjectPublicKeyAlgID + ", xcert subjectPublicKeyAlgID = " + algorithmId
/*      */             
/* 2239 */             .getOID());
/*      */       }
/* 2241 */       if (!this.subjectPublicKeyAlgID.equals(algorithmId.getOID())) {
/* 2242 */         if (debug != null) {
/* 2243 */           debug.println("X509CertSelector.match: subject public key alg IDs don't match");
/*      */         }
/*      */         
/* 2246 */         return false;
/*      */       } 
/* 2248 */     } catch (IOException iOException) {
/* 2249 */       if (debug != null) {
/* 2250 */         debug.println("X509CertSelector.match: IOException in subject public key algorithm OID check");
/*      */       }
/*      */       
/* 2253 */       return false;
/*      */     } 
/* 2255 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchKeyUsage(X509Certificate paramX509Certificate) {
/* 2260 */     if (this.keyUsage == null) {
/* 2261 */       return true;
/*      */     }
/* 2263 */     boolean[] arrayOfBoolean = paramX509Certificate.getKeyUsage();
/* 2264 */     if (arrayOfBoolean != null) {
/* 2265 */       for (byte b = 0; b < this.keyUsage.length; b++) {
/* 2266 */         if (this.keyUsage[b] && (b >= arrayOfBoolean.length || !arrayOfBoolean[b])) {
/*      */           
/* 2268 */           if (debug != null) {
/* 2269 */             debug.println("X509CertSelector.match: key usage bits don't match");
/*      */           }
/*      */           
/* 2272 */           return false;
/*      */         } 
/*      */       } 
/*      */     }
/* 2276 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchExtendedKeyUsage(X509Certificate paramX509Certificate) {
/* 2281 */     if (this.keyPurposeSet == null || this.keyPurposeSet.isEmpty()) {
/* 2282 */       return true;
/*      */     }
/*      */     
/*      */     try {
/* 2286 */       ExtendedKeyUsageExtension extendedKeyUsageExtension = (ExtendedKeyUsageExtension)getExtensionObject(paramX509Certificate, 4);
/*      */       
/* 2288 */       if (extendedKeyUsageExtension != null) {
/*      */         
/* 2290 */         Vector<ObjectIdentifier> vector = extendedKeyUsageExtension.get("usages");
/* 2291 */         if (!vector.contains(ANY_EXTENDED_KEY_USAGE) && 
/* 2292 */           !vector.containsAll(this.keyPurposeOIDSet)) {
/* 2293 */           if (debug != null) {
/* 2294 */             debug.println("X509CertSelector.match: cert failed extendedKeyUsage criterion");
/*      */           }
/*      */           
/* 2297 */           return false;
/*      */         } 
/*      */       } 
/* 2300 */     } catch (IOException iOException) {
/* 2301 */       if (debug != null) {
/* 2302 */         debug.println("X509CertSelector.match: IOException in extended key usage check");
/*      */       }
/*      */       
/* 2305 */       return false;
/*      */     } 
/* 2307 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchSubjectAlternativeNames(X509Certificate paramX509Certificate) {
/* 2312 */     if (this.subjectAlternativeNames == null || this.subjectAlternativeNames.isEmpty()) {
/* 2313 */       return true;
/*      */     }
/*      */     
/*      */     try {
/* 2317 */       SubjectAlternativeNameExtension subjectAlternativeNameExtension = (SubjectAlternativeNameExtension)getExtensionObject(paramX509Certificate, 1);
/*      */       
/* 2319 */       if (subjectAlternativeNameExtension == null) {
/* 2320 */         if (debug != null) {
/* 2321 */           debug.println("X509CertSelector.match: no subject alternative name extension");
/*      */         }
/*      */         
/* 2324 */         return false;
/*      */       } 
/*      */       
/* 2327 */       GeneralNames generalNames = subjectAlternativeNameExtension.get("subject_name");
/*      */       
/* 2329 */       Iterator<GeneralNameInterface> iterator = this.subjectAlternativeGeneralNames.iterator();
/* 2330 */       while (iterator.hasNext()) {
/* 2331 */         GeneralNameInterface generalNameInterface = iterator.next();
/* 2332 */         boolean bool = false;
/* 2333 */         Iterator<GeneralName> iterator1 = generalNames.iterator();
/* 2334 */         while (iterator1.hasNext() && !bool) {
/* 2335 */           GeneralNameInterface generalNameInterface1 = ((GeneralName)iterator1.next()).getName();
/* 2336 */           bool = generalNameInterface1.equals(generalNameInterface);
/*      */         } 
/* 2338 */         if (!bool && (this.matchAllSubjectAltNames || !iterator.hasNext())) {
/* 2339 */           if (debug != null) {
/* 2340 */             debug.println("X509CertSelector.match: subject alternative name " + generalNameInterface + " not found");
/*      */           }
/*      */           
/* 2343 */           return false;
/* 2344 */         }  if (bool && !this.matchAllSubjectAltNames) {
/*      */           break;
/*      */         }
/*      */       } 
/* 2348 */     } catch (IOException iOException) {
/* 2349 */       if (debug != null) {
/* 2350 */         debug.println("X509CertSelector.match: IOException in subject alternative name check");
/*      */       }
/* 2352 */       return false;
/*      */     } 
/* 2354 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchNameConstraints(X509Certificate paramX509Certificate) {
/* 2359 */     if (this.nc == null) {
/* 2360 */       return true;
/*      */     }
/*      */     try {
/* 2363 */       if (!this.nc.verify(paramX509Certificate)) {
/* 2364 */         if (debug != null) {
/* 2365 */           debug.println("X509CertSelector.match: name constraints not satisfied");
/*      */         }
/*      */         
/* 2368 */         return false;
/*      */       } 
/* 2370 */     } catch (IOException iOException) {
/* 2371 */       if (debug != null) {
/* 2372 */         debug.println("X509CertSelector.match: IOException in name constraints check");
/*      */       }
/*      */       
/* 2375 */       return false;
/*      */     } 
/* 2377 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchPolicy(X509Certificate paramX509Certificate) {
/* 2382 */     if (this.policy == null) {
/* 2383 */       return true;
/*      */     }
/*      */     
/*      */     try {
/* 2387 */       CertificatePoliciesExtension certificatePoliciesExtension = (CertificatePoliciesExtension)getExtensionObject(paramX509Certificate, 3);
/* 2388 */       if (certificatePoliciesExtension == null) {
/* 2389 */         if (debug != null) {
/* 2390 */           debug.println("X509CertSelector.match: no certificate policy extension");
/*      */         }
/*      */         
/* 2393 */         return false;
/*      */       } 
/* 2395 */       List<PolicyInformation> list = certificatePoliciesExtension.get("policies");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2400 */       ArrayList<CertificatePolicyId> arrayList = new ArrayList(list.size());
/* 2401 */       for (PolicyInformation policyInformation : list) {
/* 2402 */         arrayList.add(policyInformation.getPolicyIdentifier());
/*      */       }
/* 2404 */       if (this.policy != null) {
/* 2405 */         boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2411 */         if (this.policy.getCertPolicyIds().isEmpty()) {
/* 2412 */           if (arrayList.isEmpty()) {
/* 2413 */             if (debug != null) {
/* 2414 */               debug.println("X509CertSelector.match: cert failed policyAny criterion");
/*      */             }
/*      */             
/* 2417 */             return false;
/*      */           } 
/*      */         } else {
/* 2420 */           for (CertificatePolicyId certificatePolicyId : this.policy.getCertPolicyIds()) {
/* 2421 */             if (arrayList.contains(certificatePolicyId)) {
/* 2422 */               bool = true;
/*      */               break;
/*      */             } 
/*      */           } 
/* 2426 */           if (!bool) {
/* 2427 */             if (debug != null) {
/* 2428 */               debug.println("X509CertSelector.match: cert failed policyAny criterion");
/*      */             }
/*      */             
/* 2431 */             return false;
/*      */           } 
/*      */         } 
/*      */       } 
/* 2435 */     } catch (IOException iOException) {
/* 2436 */       if (debug != null) {
/* 2437 */         debug.println("X509CertSelector.match: IOException in certificate policy ID check");
/*      */       }
/*      */       
/* 2440 */       return false;
/*      */     } 
/* 2442 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchPathToNames(X509Certificate paramX509Certificate) {
/* 2447 */     if (this.pathToGeneralNames == null) {
/* 2448 */       return true;
/*      */     }
/*      */     
/*      */     try {
/* 2452 */       NameConstraintsExtension nameConstraintsExtension = (NameConstraintsExtension)getExtensionObject(paramX509Certificate, 2);
/* 2453 */       if (nameConstraintsExtension == null) {
/* 2454 */         return true;
/*      */       }
/* 2456 */       if (debug != null && Debug.isOn("certpath")) {
/* 2457 */         debug.println("X509CertSelector.match pathToNames:\n");
/*      */         
/* 2459 */         Iterator<GeneralNameInterface> iterator = this.pathToGeneralNames.iterator();
/* 2460 */         while (iterator.hasNext()) {
/* 2461 */           debug.println("    " + iterator.next() + "\n");
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2466 */       GeneralSubtrees generalSubtrees1 = nameConstraintsExtension.get("permitted_subtrees");
/*      */       
/* 2468 */       GeneralSubtrees generalSubtrees2 = nameConstraintsExtension.get("excluded_subtrees");
/* 2469 */       if (generalSubtrees2 != null && 
/* 2470 */         !matchExcluded(generalSubtrees2)) {
/* 2471 */         return false;
/*      */       }
/*      */       
/* 2474 */       if (generalSubtrees1 != null && 
/* 2475 */         !matchPermitted(generalSubtrees1)) {
/* 2476 */         return false;
/*      */       }
/*      */     }
/* 2479 */     catch (IOException iOException) {
/* 2480 */       if (debug != null) {
/* 2481 */         debug.println("X509CertSelector.match: IOException in name constraints check");
/*      */       }
/*      */       
/* 2484 */       return false;
/*      */     } 
/* 2486 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchExcluded(GeneralSubtrees paramGeneralSubtrees) {
/* 2495 */     for (GeneralSubtree generalSubtree : paramGeneralSubtrees) {
/*      */       
/* 2497 */       GeneralNameInterface generalNameInterface = generalSubtree.getName().getName();
/* 2498 */       Iterator<GeneralNameInterface> iterator = this.pathToGeneralNames.iterator();
/* 2499 */       while (iterator.hasNext()) {
/* 2500 */         GeneralNameInterface generalNameInterface1 = iterator.next();
/* 2501 */         if (generalNameInterface.getType() == generalNameInterface1.getType()) {
/* 2502 */           switch (generalNameInterface1.constrains(generalNameInterface)) {
/*      */             case 0:
/*      */             case 2:
/* 2505 */               if (debug != null) {
/* 2506 */                 debug.println("X509CertSelector.match: name constraints inhibit path to specified name");
/*      */                 
/* 2508 */                 debug.println("X509CertSelector.match: excluded name: " + generalNameInterface1);
/*      */               } 
/*      */               
/* 2511 */               return false;
/*      */           } 
/*      */         
/*      */         }
/*      */       } 
/*      */     } 
/* 2517 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean matchPermitted(GeneralSubtrees paramGeneralSubtrees) {
/* 2527 */     Iterator<GeneralNameInterface> iterator = this.pathToGeneralNames.iterator();
/* 2528 */     while (iterator.hasNext()) {
/* 2529 */       GeneralNameInterface generalNameInterface = iterator.next();
/* 2530 */       Iterator<GeneralSubtree> iterator1 = paramGeneralSubtrees.iterator();
/* 2531 */       boolean bool1 = false;
/* 2532 */       boolean bool2 = false;
/* 2533 */       String str = "";
/* 2534 */       while (iterator1.hasNext() && !bool1) {
/* 2535 */         GeneralSubtree generalSubtree = iterator1.next();
/* 2536 */         GeneralNameInterface generalNameInterface1 = generalSubtree.getName().getName();
/* 2537 */         if (generalNameInterface1.getType() == generalNameInterface.getType()) {
/* 2538 */           bool2 = true;
/* 2539 */           str = str + "  " + generalNameInterface1;
/* 2540 */           switch (generalNameInterface.constrains(generalNameInterface1)) {
/*      */             case 0:
/*      */             case 2:
/* 2543 */               bool1 = true;
/*      */           } 
/*      */ 
/*      */         
/*      */         } 
/*      */       } 
/* 2549 */       if (!bool1 && bool2) {
/* 2550 */         if (debug != null) {
/* 2551 */           debug.println("X509CertSelector.match: name constraints inhibit path to specified name; permitted names of type " + generalNameInterface
/*      */               
/* 2553 */               .getType() + ": " + str);
/*      */         }
/* 2555 */         return false;
/*      */       } 
/*      */     } 
/* 2558 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean matchBasicConstraints(X509Certificate paramX509Certificate) {
/* 2563 */     if (this.basicConstraints == -1) {
/* 2564 */       return true;
/*      */     }
/* 2566 */     int i = paramX509Certificate.getBasicConstraints();
/* 2567 */     if (this.basicConstraints == -2) {
/* 2568 */       if (i != -1) {
/* 2569 */         if (debug != null) {
/* 2570 */           debug.println("X509CertSelector.match: not an EE cert");
/*      */         }
/* 2572 */         return false;
/*      */       }
/*      */     
/* 2575 */     } else if (i < this.basicConstraints) {
/* 2576 */       if (debug != null) {
/* 2577 */         debug.println("X509CertSelector.match: cert's maxPathLen is less than the min maxPathLen set by basicConstraints. (" + i + " < " + this.basicConstraints + ")");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 2582 */       return false;
/*      */     } 
/*      */     
/* 2585 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T> Set<T> cloneSet(Set<T> paramSet) {
/* 2590 */     if (paramSet instanceof HashSet) {
/* 2591 */       Object object = ((HashSet)paramSet).clone();
/* 2592 */       return (Set<T>)object;
/*      */     } 
/* 2594 */     return new HashSet<>(paramSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/*      */     try {
/* 2605 */       X509CertSelector x509CertSelector = (X509CertSelector)super.clone();
/*      */       
/* 2607 */       if (this.subjectAlternativeNames != null) {
/* 2608 */         x509CertSelector
/* 2609 */           .subjectAlternativeNames = cloneSet(this.subjectAlternativeNames);
/* 2610 */         x509CertSelector
/* 2611 */           .subjectAlternativeGeneralNames = cloneSet(this.subjectAlternativeGeneralNames);
/*      */       } 
/* 2613 */       if (this.pathToGeneralNames != null) {
/* 2614 */         x509CertSelector.pathToNames = cloneSet(this.pathToNames);
/* 2615 */         x509CertSelector.pathToGeneralNames = cloneSet(this.pathToGeneralNames);
/*      */       } 
/* 2617 */       return x509CertSelector;
/* 2618 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*      */       
/* 2620 */       throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\security\cert\X509CertSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */