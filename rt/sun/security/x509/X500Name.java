/*      */ package sun.security.x509;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.security.AccessController;
/*      */ import java.security.Principal;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.security.util.DerInputStream;
/*      */ import sun.security.util.DerOutputStream;
/*      */ import sun.security.util.DerValue;
/*      */ import sun.security.util.ObjectIdentifier;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class X500Name
/*      */   implements GeneralNameInterface, Principal
/*      */ {
/*      */   private String dn;
/*      */   private String rfc1779Dn;
/*      */   private String rfc2253Dn;
/*      */   private String canonicalDn;
/*      */   private RDN[] names;
/*      */   private X500Principal x500Principal;
/*      */   private byte[] encoded;
/*      */   private volatile List<RDN> rdnList;
/*      */   private volatile List<AVA> allAvaList;
/*      */   
/*      */   public X500Name(String paramString) throws IOException {
/*  150 */     this(paramString, Collections.emptyMap());
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
/*      */   public X500Name(String paramString, Map<String, String> paramMap) throws IOException {
/*  163 */     parseDN(paramString, paramMap);
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
/*      */   public X500Name(String paramString1, String paramString2) throws IOException {
/*  177 */     if (paramString1 == null) {
/*  178 */       throw new NullPointerException("Name must not be null");
/*      */     }
/*  180 */     if (paramString2.equalsIgnoreCase("RFC2253")) {
/*  181 */       parseRFC2253DN(paramString1);
/*  182 */     } else if (paramString2.equalsIgnoreCase("DEFAULT")) {
/*  183 */       parseDN(paramString1, Collections.emptyMap());
/*      */     } else {
/*  185 */       throw new IOException("Unsupported format " + paramString2);
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
/*      */   public X500Name(String paramString1, String paramString2, String paramString3, String paramString4) throws IOException {
/*  205 */     this.names = new RDN[4];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  210 */     this.names[3] = new RDN(1);
/*  211 */     (this.names[3]).assertion[0] = new AVA(commonName_oid, new DerValue(paramString1));
/*      */     
/*  213 */     this.names[2] = new RDN(1);
/*  214 */     (this.names[2]).assertion[0] = new AVA(orgUnitName_oid, new DerValue(paramString2));
/*      */     
/*  216 */     this.names[1] = new RDN(1);
/*  217 */     (this.names[1]).assertion[0] = new AVA(orgName_oid, new DerValue(paramString3));
/*      */     
/*  219 */     this.names[0] = new RDN(1);
/*  220 */     (this.names[0]).assertion[0] = new AVA(countryName_oid, new DerValue(paramString4));
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
/*      */   public X500Name(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws IOException {
/*  243 */     this.names = new RDN[6];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  248 */     this.names[5] = new RDN(1);
/*  249 */     (this.names[5]).assertion[0] = new AVA(commonName_oid, new DerValue(paramString1));
/*      */     
/*  251 */     this.names[4] = new RDN(1);
/*  252 */     (this.names[4]).assertion[0] = new AVA(orgUnitName_oid, new DerValue(paramString2));
/*      */     
/*  254 */     this.names[3] = new RDN(1);
/*  255 */     (this.names[3]).assertion[0] = new AVA(orgName_oid, new DerValue(paramString3));
/*      */     
/*  257 */     this.names[2] = new RDN(1);
/*  258 */     (this.names[2]).assertion[0] = new AVA(localityName_oid, new DerValue(paramString4));
/*      */     
/*  260 */     this.names[1] = new RDN(1);
/*  261 */     (this.names[1]).assertion[0] = new AVA(stateName_oid, new DerValue(paramString5));
/*      */     
/*  263 */     this.names[0] = new RDN(1);
/*  264 */     (this.names[0]).assertion[0] = new AVA(countryName_oid, new DerValue(paramString6));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X500Name(RDN[] paramArrayOfRDN) throws IOException {
/*  275 */     if (paramArrayOfRDN == null) {
/*  276 */       this.names = new RDN[0];
/*      */     } else {
/*  278 */       this.names = (RDN[])paramArrayOfRDN.clone();
/*  279 */       for (byte b = 0; b < this.names.length; b++) {
/*  280 */         if (this.names[b] == null) {
/*  281 */           throw new IOException("Cannot create an X500Name");
/*      */         }
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
/*      */   public X500Name(DerValue paramDerValue) throws IOException {
/*  296 */     this(paramDerValue.toDerInputStream());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X500Name(DerInputStream paramDerInputStream) throws IOException {
/*  306 */     parseDER(paramDerInputStream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X500Name(byte[] paramArrayOfbyte) throws IOException {
/*  315 */     DerInputStream derInputStream = new DerInputStream(paramArrayOfbyte);
/*  316 */     parseDER(derInputStream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<RDN> rdns() {
/*  323 */     List<RDN> list = this.rdnList;
/*  324 */     if (list == null) {
/*  325 */       list = Collections.unmodifiableList(Arrays.asList(this.names));
/*  326 */       this.rdnList = list;
/*      */     } 
/*  328 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  335 */     return this.names.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<AVA> allAvas() {
/*  343 */     List<AVA> list = this.allAvaList;
/*  344 */     if (list == null) {
/*  345 */       list = new ArrayList<>();
/*  346 */       for (byte b = 0; b < this.names.length; b++) {
/*  347 */         list.addAll(this.names[b].avas());
/*      */       }
/*  349 */       list = Collections.unmodifiableList(list);
/*  350 */       this.allAvaList = list;
/*      */     } 
/*  352 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int avaSize() {
/*  360 */     return allAvas().size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  368 */     int i = this.names.length;
/*  369 */     for (byte b = 0; b < i; b++) {
/*  370 */       if ((this.names[b]).assertion.length != 0) {
/*  371 */         return false;
/*      */       }
/*      */     } 
/*  374 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  382 */     return getRFC2253CanonicalName().hashCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/*  391 */     if (this == paramObject) {
/*  392 */       return true;
/*      */     }
/*  394 */     if (!(paramObject instanceof X500Name)) {
/*  395 */       return false;
/*      */     }
/*  397 */     X500Name x500Name = (X500Name)paramObject;
/*      */     
/*  399 */     if (this.canonicalDn != null && x500Name.canonicalDn != null) {
/*  400 */       return this.canonicalDn.equals(x500Name.canonicalDn);
/*      */     }
/*      */     
/*  403 */     int i = this.names.length;
/*  404 */     if (i != x500Name.names.length) {
/*  405 */       return false;
/*      */     }
/*  407 */     for (byte b = 0; b < i; b++) {
/*  408 */       RDN rDN1 = this.names[b];
/*  409 */       RDN rDN2 = x500Name.names[b];
/*  410 */       if (rDN1.assertion.length != rDN2.assertion.length) {
/*  411 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  415 */     String str1 = getRFC2253CanonicalName();
/*  416 */     String str2 = x500Name.getRFC2253CanonicalName();
/*  417 */     return str1.equals(str2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getString(DerValue paramDerValue) throws IOException {
/*  425 */     if (paramDerValue == null)
/*  426 */       return null; 
/*  427 */     String str = paramDerValue.getAsString();
/*      */     
/*  429 */     if (str == null) {
/*  430 */       throw new IOException("not a DER string encoding, " + paramDerValue.tag);
/*      */     }
/*      */     
/*  433 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getType() {
/*  440 */     return 4;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCountry() throws IOException {
/*  450 */     DerValue derValue = findAttribute(countryName_oid);
/*      */     
/*  452 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOrganization() throws IOException {
/*  463 */     DerValue derValue = findAttribute(orgName_oid);
/*      */     
/*  465 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOrganizationalUnit() throws IOException {
/*  476 */     DerValue derValue = findAttribute(orgUnitName_oid);
/*      */     
/*  478 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCommonName() throws IOException {
/*  489 */     DerValue derValue = findAttribute(commonName_oid);
/*      */     
/*  491 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocality() throws IOException {
/*  502 */     DerValue derValue = findAttribute(localityName_oid);
/*      */     
/*  504 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getState() throws IOException {
/*  514 */     DerValue derValue = findAttribute(stateName_oid);
/*      */     
/*  516 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDomain() throws IOException {
/*  526 */     DerValue derValue = findAttribute(DOMAIN_COMPONENT_OID);
/*      */     
/*  528 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDNQualifier() throws IOException {
/*  538 */     DerValue derValue = findAttribute(DNQUALIFIER_OID);
/*      */     
/*  540 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSurname() throws IOException {
/*  550 */     DerValue derValue = findAttribute(SURNAME_OID);
/*      */     
/*  552 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getGivenName() throws IOException {
/*  562 */     DerValue derValue = findAttribute(GIVENNAME_OID);
/*      */     
/*  564 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getInitials() throws IOException {
/*  574 */     DerValue derValue = findAttribute(INITIALS_OID);
/*      */     
/*  576 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getGeneration() throws IOException {
/*  586 */     DerValue derValue = findAttribute(GENERATIONQUALIFIER_OID);
/*      */     
/*  588 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getIP() throws IOException {
/*  598 */     DerValue derValue = findAttribute(ipAddress_oid);
/*      */     
/*  600 */     return getString(derValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  610 */     if (this.dn == null) {
/*  611 */       generateDN();
/*      */     }
/*  613 */     return this.dn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRFC1779Name() {
/*  622 */     return getRFC1779Name(Collections.emptyMap());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRFC1779Name(Map<String, String> paramMap) throws IllegalArgumentException {
/*  633 */     if (paramMap.isEmpty()) {
/*      */       
/*  635 */       if (this.rfc1779Dn != null) {
/*  636 */         return this.rfc1779Dn;
/*      */       }
/*  638 */       this.rfc1779Dn = generateRFC1779DN(paramMap);
/*  639 */       return this.rfc1779Dn;
/*      */     } 
/*      */     
/*  642 */     return generateRFC1779DN(paramMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRFC2253Name() {
/*  651 */     return getRFC2253Name(Collections.emptyMap());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRFC2253Name(Map<String, String> paramMap) {
/*  662 */     if (paramMap.isEmpty()) {
/*  663 */       if (this.rfc2253Dn != null) {
/*  664 */         return this.rfc2253Dn;
/*      */       }
/*  666 */       this.rfc2253Dn = generateRFC2253DN(paramMap);
/*  667 */       return this.rfc2253Dn;
/*      */     } 
/*      */     
/*  670 */     return generateRFC2253DN(paramMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String generateRFC2253DN(Map<String, String> paramMap) {
/*  678 */     if (this.names.length == 0) {
/*  679 */       return "";
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
/*  691 */     StringBuilder stringBuilder = new StringBuilder(48);
/*  692 */     for (int i = this.names.length - 1; i >= 0; i--) {
/*  693 */       if (i < this.names.length - 1) {
/*  694 */         stringBuilder.append(',');
/*      */       }
/*  696 */       stringBuilder.append(this.names[i].toRFC2253String(paramMap));
/*      */     } 
/*  698 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getRFC2253CanonicalName() {
/*  703 */     if (this.canonicalDn != null) {
/*  704 */       return this.canonicalDn;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  710 */     if (this.names.length == 0) {
/*  711 */       this.canonicalDn = "";
/*  712 */       return this.canonicalDn;
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
/*  724 */     StringBuilder stringBuilder = new StringBuilder(48);
/*  725 */     for (int i = this.names.length - 1; i >= 0; i--) {
/*  726 */       if (i < this.names.length - 1) {
/*  727 */         stringBuilder.append(',');
/*      */       }
/*  729 */       stringBuilder.append(this.names[i].toRFC2253String(true));
/*      */     } 
/*  731 */     this.canonicalDn = stringBuilder.toString();
/*  732 */     return this.canonicalDn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/*  739 */     return toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DerValue findAttribute(ObjectIdentifier paramObjectIdentifier) {
/*  746 */     if (this.names != null) {
/*  747 */       for (byte b = 0; b < this.names.length; b++) {
/*  748 */         DerValue derValue = this.names[b].findAttribute(paramObjectIdentifier);
/*  749 */         if (derValue != null) {
/*  750 */           return derValue;
/*      */         }
/*      */       } 
/*      */     }
/*  754 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DerValue findMostSpecificAttribute(ObjectIdentifier paramObjectIdentifier) {
/*  762 */     if (this.names != null) {
/*  763 */       for (int i = this.names.length - 1; i >= 0; i--) {
/*  764 */         DerValue derValue = this.names[i].findAttribute(paramObjectIdentifier);
/*  765 */         if (derValue != null) {
/*  766 */           return derValue;
/*      */         }
/*      */       } 
/*      */     }
/*  770 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void parseDER(DerInputStream paramDerInputStream) throws IOException {
/*  781 */     DerValue[] arrayOfDerValue = null;
/*  782 */     byte[] arrayOfByte = paramDerInputStream.toByteArray();
/*      */     
/*      */     try {
/*  785 */       arrayOfDerValue = paramDerInputStream.getSequence(5);
/*  786 */     } catch (IOException iOException) {
/*  787 */       if (arrayOfByte == null) {
/*  788 */         arrayOfDerValue = null;
/*      */       } else {
/*  790 */         DerValue derValue = new DerValue((byte)48, arrayOfByte);
/*      */         
/*  792 */         arrayOfByte = derValue.toByteArray();
/*  793 */         arrayOfDerValue = (new DerInputStream(arrayOfByte)).getSequence(5);
/*      */       } 
/*      */     } 
/*      */     
/*  797 */     if (arrayOfDerValue == null) {
/*  798 */       this.names = new RDN[0];
/*      */     } else {
/*  800 */       this.names = new RDN[arrayOfDerValue.length];
/*  801 */       for (byte b = 0; b < arrayOfDerValue.length; b++) {
/*  802 */         this.names[b] = new RDN(arrayOfDerValue[b]);
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
/*      */   @Deprecated
/*      */   public void emit(DerOutputStream paramDerOutputStream) throws IOException {
/*  815 */     encode(paramDerOutputStream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void encode(DerOutputStream paramDerOutputStream) throws IOException {
/*  824 */     DerOutputStream derOutputStream = new DerOutputStream();
/*  825 */     for (byte b = 0; b < this.names.length; b++) {
/*  826 */       this.names[b].encode(derOutputStream);
/*      */     }
/*  828 */     paramDerOutputStream.write((byte)48, derOutputStream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getEncodedInternal() throws IOException {
/*  837 */     if (this.encoded == null) {
/*  838 */       DerOutputStream derOutputStream1 = new DerOutputStream();
/*  839 */       DerOutputStream derOutputStream2 = new DerOutputStream();
/*  840 */       for (byte b = 0; b < this.names.length; b++) {
/*  841 */         this.names[b].encode(derOutputStream2);
/*      */       }
/*  843 */       derOutputStream1.write((byte)48, derOutputStream2);
/*  844 */       this.encoded = derOutputStream1.toByteArray();
/*      */     } 
/*  846 */     return this.encoded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getEncoded() throws IOException {
/*  855 */     return (byte[])getEncodedInternal().clone();
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
/*      */   private void parseDN(String paramString, Map<String, String> paramMap) throws IOException {
/*  873 */     if (paramString == null || paramString.length() == 0) {
/*  874 */       this.names = new RDN[0];
/*      */       
/*      */       return;
/*      */     } 
/*  878 */     ArrayList<RDN> arrayList = new ArrayList();
/*  879 */     int i = 0;
/*      */ 
/*      */     
/*  882 */     int j = 0;
/*      */     
/*  884 */     String str2 = paramString;
/*      */     
/*  886 */     int k = 0;
/*  887 */     int m = str2.indexOf(',');
/*  888 */     int n = str2.indexOf(';');
/*  889 */     while (m >= 0 || n >= 0) {
/*      */       int i1;
/*  891 */       if (n < 0) {
/*  892 */         i1 = m;
/*  893 */       } else if (m < 0) {
/*  894 */         i1 = n;
/*      */       } else {
/*  896 */         i1 = Math.min(m, n);
/*      */       } 
/*  898 */       j += countQuotes(str2, k, i1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  907 */       if (i1 >= 0 && j != 1 && 
/*  908 */         !escaped(i1, k, str2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  913 */         String str = str2.substring(i, i1);
/*      */ 
/*      */         
/*  916 */         RDN rDN1 = new RDN(str, paramMap);
/*  917 */         arrayList.add(rDN1);
/*      */ 
/*      */         
/*  920 */         i = i1 + 1;
/*      */ 
/*      */         
/*  923 */         j = 0;
/*      */       } 
/*      */       
/*  926 */       k = i1 + 1;
/*  927 */       m = str2.indexOf(',', k);
/*  928 */       n = str2.indexOf(';', k);
/*      */     } 
/*      */ 
/*      */     
/*  932 */     String str1 = str2.substring(i);
/*  933 */     RDN rDN = new RDN(str1, paramMap);
/*  934 */     arrayList.add(rDN);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  940 */     Collections.reverse(arrayList);
/*  941 */     this.names = arrayList.<RDN>toArray(new RDN[arrayList.size()]);
/*      */   }
/*      */   
/*      */   private void parseRFC2253DN(String paramString) throws IOException {
/*  945 */     if (paramString.length() == 0) {
/*  946 */       this.names = new RDN[0];
/*      */       
/*      */       return;
/*      */     } 
/*  950 */     ArrayList<RDN> arrayList = new ArrayList();
/*  951 */     int i = 0;
/*      */     
/*  953 */     int j = 0;
/*  954 */     int k = paramString.indexOf(',');
/*  955 */     while (k >= 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  963 */       if (k > 0 && !escaped(k, j, paramString)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  968 */         String str1 = paramString.substring(i, k);
/*      */ 
/*      */         
/*  971 */         RDN rDN1 = new RDN(str1, "RFC2253");
/*  972 */         arrayList.add(rDN1);
/*      */ 
/*      */         
/*  975 */         i = k + 1;
/*      */       } 
/*      */       
/*  978 */       j = k + 1;
/*  979 */       k = paramString.indexOf(',', j);
/*      */     } 
/*      */ 
/*      */     
/*  983 */     String str = paramString.substring(i);
/*  984 */     RDN rDN = new RDN(str, "RFC2253");
/*  985 */     arrayList.add(rDN);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  991 */     Collections.reverse(arrayList);
/*  992 */     this.names = arrayList.<RDN>toArray(new RDN[arrayList.size()]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int countQuotes(String paramString, int paramInt1, int paramInt2) {
/* 1000 */     byte b = 0;
/*      */     
/* 1002 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 1003 */       if ((paramString.charAt(i) == '"' && i == paramInt1) || (paramString
/* 1004 */         .charAt(i) == '"' && paramString.charAt(i - 1) != '\\')) {
/* 1005 */         b++;
/*      */       }
/*      */     } 
/*      */     
/* 1009 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean escaped(int paramInt1, int paramInt2, String paramString) {
/* 1015 */     if (paramInt1 == 1 && paramString.charAt(paramInt1 - 1) == '\\')
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1020 */       return true;
/*      */     }
/* 1022 */     if (paramInt1 > 1 && paramString.charAt(paramInt1 - 1) == '\\' && paramString
/* 1023 */       .charAt(paramInt1 - 2) != '\\')
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1028 */       return true;
/*      */     }
/* 1030 */     if (paramInt1 > 1 && paramString.charAt(paramInt1 - 1) == '\\' && paramString
/* 1031 */       .charAt(paramInt1 - 2) == '\\') {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1036 */       byte b = 0;
/* 1037 */       paramInt1--;
/* 1038 */       while (paramInt1 >= paramInt2) {
/* 1039 */         if (paramString.charAt(paramInt1) == '\\') {
/* 1040 */           b++;
/*      */         }
/* 1042 */         paramInt1--;
/*      */       } 
/*      */ 
/*      */       
/* 1046 */       return (b % 2 != 0);
/*      */     } 
/*      */     
/* 1049 */     return false;
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
/*      */   private void generateDN() {
/* 1061 */     if (this.names.length == 1) {
/* 1062 */       this.dn = this.names[0].toString();
/*      */       
/*      */       return;
/*      */     } 
/* 1066 */     StringBuilder stringBuilder = new StringBuilder(48);
/* 1067 */     if (this.names != null) {
/* 1068 */       for (int i = this.names.length - 1; i >= 0; i--) {
/* 1069 */         if (i != this.names.length - 1) {
/* 1070 */           stringBuilder.append(", ");
/*      */         }
/* 1072 */         stringBuilder.append(this.names[i].toString());
/*      */       } 
/*      */     }
/* 1075 */     this.dn = stringBuilder.toString();
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
/*      */   private String generateRFC1779DN(Map<String, String> paramMap) {
/* 1088 */     if (this.names.length == 1) {
/* 1089 */       return this.names[0].toRFC1779String(paramMap);
/*      */     }
/*      */     
/* 1092 */     StringBuilder stringBuilder = new StringBuilder(48);
/* 1093 */     if (this.names != null) {
/* 1094 */       for (int i = this.names.length - 1; i >= 0; i--) {
/* 1095 */         if (i != this.names.length - 1) {
/* 1096 */           stringBuilder.append(", ");
/*      */         }
/* 1098 */         stringBuilder.append(this.names[i].toRFC1779String(paramMap));
/*      */       } 
/*      */     }
/* 1101 */     return stringBuilder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static ObjectIdentifier intern(ObjectIdentifier paramObjectIdentifier) {
/* 1111 */     ObjectIdentifier objectIdentifier = internedOIDs.putIfAbsent(paramObjectIdentifier, paramObjectIdentifier);
/* 1112 */     return (objectIdentifier == null) ? paramObjectIdentifier : objectIdentifier;
/*      */   }
/*      */   
/* 1115 */   private static final Map<ObjectIdentifier, ObjectIdentifier> internedOIDs = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1123 */   private static final int[] commonName_data = new int[] { 2, 5, 4, 3 };
/* 1124 */   private static final int[] SURNAME_DATA = new int[] { 2, 5, 4, 4 };
/* 1125 */   private static final int[] SERIALNUMBER_DATA = new int[] { 2, 5, 4, 5 };
/* 1126 */   private static final int[] countryName_data = new int[] { 2, 5, 4, 6 };
/* 1127 */   private static final int[] localityName_data = new int[] { 2, 5, 4, 7 };
/* 1128 */   private static final int[] stateName_data = new int[] { 2, 5, 4, 8 };
/* 1129 */   private static final int[] streetAddress_data = new int[] { 2, 5, 4, 9 };
/* 1130 */   private static final int[] orgName_data = new int[] { 2, 5, 4, 10 };
/* 1131 */   private static final int[] orgUnitName_data = new int[] { 2, 5, 4, 11 };
/* 1132 */   private static final int[] title_data = new int[] { 2, 5, 4, 12 };
/* 1133 */   private static final int[] GIVENNAME_DATA = new int[] { 2, 5, 4, 42 };
/* 1134 */   private static final int[] INITIALS_DATA = new int[] { 2, 5, 4, 43 };
/* 1135 */   private static final int[] GENERATIONQUALIFIER_DATA = new int[] { 2, 5, 4, 44 };
/* 1136 */   private static final int[] DNQUALIFIER_DATA = new int[] { 2, 5, 4, 46 };
/*      */   
/* 1138 */   private static final int[] ipAddress_data = new int[] { 1, 3, 6, 1, 4, 1, 42, 2, 11, 2, 1 };
/* 1139 */   private static final int[] DOMAIN_COMPONENT_DATA = new int[] { 0, 9, 2342, 19200300, 100, 1, 25 };
/*      */   
/* 1141 */   private static final int[] userid_data = new int[] { 0, 9, 2342, 19200300, 100, 1, 1 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1165 */   public static final ObjectIdentifier commonName_oid = intern(ObjectIdentifier.newInternal(commonName_data)); public static final ObjectIdentifier countryName_oid; public static final ObjectIdentifier localityName_oid; public static final ObjectIdentifier orgName_oid; public static final ObjectIdentifier orgUnitName_oid; public static final ObjectIdentifier stateName_oid; public static final ObjectIdentifier streetAddress_oid; public static final ObjectIdentifier title_oid; public static final ObjectIdentifier DNQUALIFIER_OID; public static final ObjectIdentifier SURNAME_OID; public static final ObjectIdentifier GIVENNAME_OID; public static final ObjectIdentifier INITIALS_OID;
/*      */   public static final ObjectIdentifier GENERATIONQUALIFIER_OID;
/*      */   public static final ObjectIdentifier ipAddress_oid;
/*      */   public static final ObjectIdentifier DOMAIN_COMPONENT_OID;
/*      */   public static final ObjectIdentifier userid_oid;
/* 1170 */   public static final ObjectIdentifier SERIALNUMBER_OID = intern(ObjectIdentifier.newInternal(SERIALNUMBER_DATA));
/*      */   private static final Constructor<X500Principal> principalConstructor; private static final Field principalField; public int constrains(GeneralNameInterface paramGeneralNameInterface) throws UnsupportedOperationException { byte b; if (paramGeneralNameInterface == null) { b = -1; } else if (paramGeneralNameInterface.getType() != 4) { b = -1; } else { X500Name x500Name = (X500Name)paramGeneralNameInterface; if (x500Name.equals(this)) { b = 0; } else if (x500Name.names.length == 0) { b = 2; } else if (this.names.length == 0) { b = 1; } else if (x500Name.isWithinSubtree(this)) { b = 1; } else if (isWithinSubtree(x500Name)) { b = 2; } else { b = 3; }
/*      */        }
/* 1173 */      return b; } static { countryName_oid = intern(ObjectIdentifier.newInternal(countryName_data));
/*      */ 
/*      */     
/* 1176 */     localityName_oid = intern(ObjectIdentifier.newInternal(localityName_data));
/*      */ 
/*      */     
/* 1179 */     orgName_oid = intern(ObjectIdentifier.newInternal(orgName_data));
/*      */ 
/*      */     
/* 1182 */     orgUnitName_oid = intern(ObjectIdentifier.newInternal(orgUnitName_data));
/*      */ 
/*      */     
/* 1185 */     stateName_oid = intern(ObjectIdentifier.newInternal(stateName_data));
/*      */ 
/*      */     
/* 1188 */     streetAddress_oid = intern(ObjectIdentifier.newInternal(streetAddress_data));
/*      */ 
/*      */     
/* 1191 */     title_oid = intern(ObjectIdentifier.newInternal(title_data));
/*      */ 
/*      */ 
/*      */     
/* 1195 */     DNQUALIFIER_OID = intern(ObjectIdentifier.newInternal(DNQUALIFIER_DATA));
/*      */ 
/*      */     
/* 1198 */     SURNAME_OID = intern(ObjectIdentifier.newInternal(SURNAME_DATA));
/*      */ 
/*      */     
/* 1201 */     GIVENNAME_OID = intern(ObjectIdentifier.newInternal(GIVENNAME_DATA));
/*      */ 
/*      */     
/* 1204 */     INITIALS_OID = intern(ObjectIdentifier.newInternal(INITIALS_DATA));
/*      */ 
/*      */ 
/*      */     
/* 1208 */     GENERATIONQUALIFIER_OID = intern(ObjectIdentifier.newInternal(GENERATIONQUALIFIER_DATA));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1215 */     ipAddress_oid = intern(ObjectIdentifier.newInternal(ipAddress_data));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1226 */     DOMAIN_COMPONENT_OID = intern(ObjectIdentifier.newInternal(DOMAIN_COMPONENT_DATA));
/*      */ 
/*      */     
/* 1229 */     userid_oid = intern(ObjectIdentifier.newInternal(userid_data));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1379 */     PrivilegedExceptionAction<Object[]> privilegedExceptionAction = new PrivilegedExceptionAction<Object[]>()
/*      */       {
/*      */         public Object[] run() throws Exception {
/* 1382 */           Class<X500Principal> clazz = X500Principal.class;
/* 1383 */           Class[] arrayOfClass = { X500Name.class };
/* 1384 */           Constructor<X500Principal> constructor = clazz.getDeclaredConstructor(arrayOfClass);
/* 1385 */           constructor.setAccessible(true);
/* 1386 */           Field field = clazz.getDeclaredField("thisX500Name");
/* 1387 */           field.setAccessible(true);
/* 1388 */           return new Object[] { constructor, field };
/*      */         }
/*      */       };
/*      */     try {
/* 1392 */       Object[] arrayOfObject = AccessController.<Object[]>doPrivileged(privilegedExceptionAction);
/*      */       
/* 1394 */       Constructor<X500Principal> constructor = (Constructor)arrayOfObject[0];
/*      */       
/* 1396 */       principalConstructor = constructor;
/* 1397 */       principalField = (Field)arrayOfObject[1];
/* 1398 */     } catch (Exception exception) {
/* 1399 */       throw new InternalError("Could not obtain X500Principal access", exception);
/*      */     }  }
/*      */   private boolean isWithinSubtree(X500Name paramX500Name) { if (this == paramX500Name)
/*      */       return true;  if (paramX500Name == null)
/*      */       return false;  if (paramX500Name.names.length == 0)
/*      */       return true;  if (this.names.length == 0)
/*      */       return false;  if (this.names.length < paramX500Name.names.length)
/*      */       return false;  for (byte b = 0; b < paramX500Name.names.length; b++) {
/*      */       if (!this.names[b].equals(paramX500Name.names[b]))
/*      */         return false; 
/*      */     } 
/* 1410 */     return true; } public X500Principal asX500Principal() { if (this.x500Principal == null) {
/*      */       try {
/* 1412 */         Object[] arrayOfObject = { this };
/* 1413 */         this.x500Principal = principalConstructor.newInstance(arrayOfObject);
/* 1414 */       } catch (Exception exception) {
/* 1415 */         throw new RuntimeException("Unexpected exception", exception);
/*      */       } 
/*      */     }
/* 1418 */     return this.x500Principal; } public int subtreeDepth() throws UnsupportedOperationException { return this.names.length; }
/*      */   public X500Name commonAncestor(X500Name paramX500Name) { if (paramX500Name == null)
/*      */       return null;  int i = paramX500Name.names.length; int j = this.names.length; if (j == 0 || i == 0)
/*      */       return null;  int k = (j < i) ? j : i; byte b1 = 0; for (; b1 < k; b1++) { if (!this.names[b1].equals(paramX500Name.names[b1])) { if (b1 == 0)
/*      */           return null;  break; }
/*      */        }
/*      */      RDN[] arrayOfRDN = new RDN[b1]; for (byte b2 = 0; b2 < b1; b2++)
/*      */       arrayOfRDN[b2] = this.names[b2];  X500Name x500Name = null; try { x500Name = new X500Name(arrayOfRDN); }
/*      */     catch (IOException iOException) { return null; }
/*      */      return x500Name; }
/* 1428 */   public static X500Name asX500Name(X500Principal paramX500Principal) { try { X500Name x500Name = (X500Name)principalField.get(paramX500Principal);
/* 1429 */       x500Name.x500Principal = paramX500Principal;
/* 1430 */       return x500Name; }
/* 1431 */     catch (Exception exception)
/* 1432 */     { throw new RuntimeException("Unexpected exception", exception); }
/*      */      }
/*      */ 
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\x509\X500Name.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */