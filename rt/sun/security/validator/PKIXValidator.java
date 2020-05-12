/*     */ package sun.security.validator;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.AlgorithmConstraints;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.Timestamp;
/*     */ import java.security.cert.CertPath;
/*     */ import java.security.cert.CertPathBuilder;
/*     */ import java.security.cert.CertPathValidator;
/*     */ import java.security.cert.CertStore;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.CollectionCertStoreParameters;
/*     */ import java.security.cert.PKIXBuilderParameters;
/*     */ import java.security.cert.PKIXCertPathBuilderResult;
/*     */ import java.security.cert.PKIXCertPathValidatorResult;
/*     */ import java.security.cert.TrustAnchor;
/*     */ import java.security.cert.X509CertSelector;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ import sun.security.action.GetBooleanAction;
/*     */ import sun.security.provider.certpath.AlgorithmChecker;
/*     */ import sun.security.provider.certpath.PKIXExtendedParameters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PKIXValidator
/*     */   extends Validator
/*     */ {
/*  62 */   private static final boolean checkTLSRevocation = ((Boolean)AccessController.<Boolean>doPrivileged(new GetBooleanAction("com.sun.net.ssl.checkRevocation"))).booleanValue();
/*     */   
/*     */   private static final boolean TRY_VALIDATOR = true;
/*     */   
/*     */   private final Set<X509Certificate> trustedCerts;
/*     */   
/*     */   private final PKIXBuilderParameters parameterTemplate;
/*  69 */   private int certPathLength = -1;
/*     */   
/*     */   private final Map<X500Principal, List<PublicKey>> trustedSubjects;
/*     */   
/*     */   private final CertificateFactory factory;
/*     */   
/*     */   private final boolean plugin;
/*     */   
/*     */   PKIXValidator(String paramString, Collection<X509Certificate> paramCollection) {
/*  78 */     super("PKIX", paramString);
/*  79 */     if (paramCollection instanceof Set) {
/*  80 */       this.trustedCerts = (Set<X509Certificate>)paramCollection;
/*     */     } else {
/*  82 */       this.trustedCerts = new HashSet<>(paramCollection);
/*     */     } 
/*  84 */     HashSet<TrustAnchor> hashSet = new HashSet();
/*  85 */     for (X509Certificate x509Certificate : paramCollection) {
/*  86 */       hashSet.add(new TrustAnchor(x509Certificate, null));
/*     */     }
/*     */     try {
/*  89 */       this.parameterTemplate = new PKIXBuilderParameters(hashSet, null);
/*  90 */     } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/*  91 */       throw new RuntimeException("Unexpected error: " + invalidAlgorithmParameterException.toString(), invalidAlgorithmParameterException);
/*     */     } 
/*  93 */     setDefaultParameters(paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     this.trustedSubjects = new HashMap<>();
/* 101 */     for (X509Certificate x509Certificate : paramCollection) {
/* 102 */       List<PublicKey> list; X500Principal x500Principal = x509Certificate.getSubjectX500Principal();
/*     */       
/* 104 */       if (this.trustedSubjects.containsKey(x500Principal)) {
/* 105 */         list = this.trustedSubjects.get(x500Principal);
/*     */       } else {
/* 107 */         list = new ArrayList();
/* 108 */         this.trustedSubjects.put(x500Principal, list);
/*     */       } 
/* 110 */       list.add(x509Certificate.getPublicKey());
/*     */     } 
/*     */     try {
/* 113 */       this.factory = CertificateFactory.getInstance("X.509");
/* 114 */     } catch (CertificateException certificateException) {
/* 115 */       throw new RuntimeException("Internal error", certificateException);
/*     */     } 
/* 117 */     this.plugin = paramString.equals("plugin code signing");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PKIXValidator(String paramString, PKIXBuilderParameters paramPKIXBuilderParameters) {
/* 124 */     super("PKIX", paramString);
/* 125 */     this.trustedCerts = new HashSet<>();
/* 126 */     for (TrustAnchor trustAnchor : paramPKIXBuilderParameters.getTrustAnchors()) {
/* 127 */       X509Certificate x509Certificate = trustAnchor.getTrustedCert();
/* 128 */       if (x509Certificate != null) {
/* 129 */         this.trustedCerts.add(x509Certificate);
/*     */       }
/*     */     } 
/* 132 */     this.parameterTemplate = paramPKIXBuilderParameters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.trustedSubjects = new HashMap<>();
/* 140 */     for (X509Certificate x509Certificate : this.trustedCerts) {
/* 141 */       List<PublicKey> list; X500Principal x500Principal = x509Certificate.getSubjectX500Principal();
/*     */       
/* 143 */       if (this.trustedSubjects.containsKey(x500Principal)) {
/* 144 */         list = this.trustedSubjects.get(x500Principal);
/*     */       } else {
/* 146 */         list = new ArrayList();
/* 147 */         this.trustedSubjects.put(x500Principal, list);
/*     */       } 
/* 149 */       list.add(x509Certificate.getPublicKey());
/*     */     } 
/*     */     try {
/* 152 */       this.factory = CertificateFactory.getInstance("X.509");
/* 153 */     } catch (CertificateException certificateException) {
/* 154 */       throw new RuntimeException("Internal error", certificateException);
/*     */     } 
/* 156 */     this.plugin = paramString.equals("plugin code signing");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<X509Certificate> getTrustedCertificates() {
/* 163 */     return this.trustedCerts;
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
/*     */   public int getCertPathLength() {
/* 177 */     return this.certPathLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDefaultParameters(String paramString) {
/* 185 */     if (paramString == "tls server" || paramString == "tls client") {
/*     */       
/* 187 */       this.parameterTemplate.setRevocationEnabled(checkTLSRevocation);
/*     */     } else {
/* 189 */       this.parameterTemplate.setRevocationEnabled(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PKIXBuilderParameters getParameters() {
/* 199 */     return this.parameterTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   X509Certificate[] engineValidate(X509Certificate[] paramArrayOfX509Certificate, Collection<X509Certificate> paramCollection, AlgorithmConstraints paramAlgorithmConstraints, Object paramObject) throws CertificateException {
/* 207 */     if (paramArrayOfX509Certificate == null || paramArrayOfX509Certificate.length == 0) {
/* 208 */       throw new CertificateException("null or zero-length certificate chain");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 213 */     PKIXExtendedParameters pKIXExtendedParameters = null;
/*     */     
/*     */     try {
/* 216 */       pKIXExtendedParameters = new PKIXExtendedParameters((PKIXBuilderParameters)this.parameterTemplate.clone(), (paramObject instanceof Timestamp) ? (Timestamp)paramObject : null, this.variant);
/*     */ 
/*     */     
/*     */     }
/* 220 */     catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     if (paramAlgorithmConstraints != null) {
/* 226 */       pKIXExtendedParameters.addCertPathChecker(new AlgorithmChecker(paramAlgorithmConstraints, null, this.variant));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     X500Principal x500Principal1 = null;
/* 234 */     for (byte b = 0; b < paramArrayOfX509Certificate.length; b++) {
/* 235 */       X509Certificate x509Certificate1 = paramArrayOfX509Certificate[b];
/* 236 */       X500Principal x500Principal = x509Certificate1.getSubjectX500Principal();
/* 237 */       if (b != 0 && 
/* 238 */         !x500Principal.equals(x500Principal1))
/*     */       {
/* 240 */         return doBuild(paramArrayOfX509Certificate, paramCollection, pKIXExtendedParameters);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       if (this.trustedCerts.contains(x509Certificate1) || (this.trustedSubjects
/* 250 */         .containsKey(x500Principal) && ((List)this.trustedSubjects
/* 251 */         .get(x500Principal)).contains(x509Certificate1
/* 252 */           .getPublicKey()))) {
/* 253 */         if (b == 0) {
/* 254 */           return new X509Certificate[] { paramArrayOfX509Certificate[0] };
/*     */         }
/*     */         
/* 257 */         X509Certificate[] arrayOfX509Certificate = new X509Certificate[b];
/* 258 */         System.arraycopy(paramArrayOfX509Certificate, 0, arrayOfX509Certificate, 0, b);
/* 259 */         return doValidate(arrayOfX509Certificate, pKIXExtendedParameters);
/*     */       } 
/* 261 */       x500Principal1 = x509Certificate1.getIssuerX500Principal();
/*     */     } 
/*     */ 
/*     */     
/* 265 */     X509Certificate x509Certificate = paramArrayOfX509Certificate[paramArrayOfX509Certificate.length - 1];
/* 266 */     X500Principal x500Principal2 = x509Certificate.getIssuerX500Principal();
/* 267 */     X500Principal x500Principal3 = x509Certificate.getSubjectX500Principal();
/* 268 */     if (this.trustedSubjects.containsKey(x500Principal2) && 
/* 269 */       isSignatureValid(this.trustedSubjects.get(x500Principal2), x509Certificate)) {
/* 270 */       return doValidate(paramArrayOfX509Certificate, pKIXExtendedParameters);
/*     */     }
/*     */ 
/*     */     
/* 274 */     if (this.plugin) {
/*     */ 
/*     */ 
/*     */       
/* 278 */       if (paramArrayOfX509Certificate.length > 1) {
/* 279 */         X509Certificate[] arrayOfX509Certificate = new X509Certificate[paramArrayOfX509Certificate.length - 1];
/*     */         
/* 281 */         System.arraycopy(paramArrayOfX509Certificate, 0, arrayOfX509Certificate, 0, arrayOfX509Certificate.length);
/*     */ 
/*     */         
/*     */         try {
/* 285 */           pKIXExtendedParameters
/* 286 */             .setTrustAnchors(Collections.singleton(new TrustAnchor(paramArrayOfX509Certificate[paramArrayOfX509Certificate.length - 1], null)));
/*     */         }
/* 288 */         catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
/*     */           
/* 290 */           throw new CertificateException(invalidAlgorithmParameterException);
/*     */         } 
/* 292 */         doValidate(arrayOfX509Certificate, pKIXExtendedParameters);
/*     */       } 
/*     */ 
/*     */       
/* 296 */       throw new ValidatorException(ValidatorException.T_NO_TRUST_ANCHOR);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     return doBuild(paramArrayOfX509Certificate, paramCollection, pKIXExtendedParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSignatureValid(List<PublicKey> paramList, X509Certificate paramX509Certificate) {
/* 307 */     if (this.plugin) {
/* 308 */       for (PublicKey publicKey : paramList) {
/*     */         try {
/* 310 */           paramX509Certificate.verify(publicKey);
/* 311 */           return true;
/* 312 */         } catch (Exception exception) {}
/*     */       } 
/*     */ 
/*     */       
/* 316 */       return false;
/*     */     } 
/* 318 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static X509Certificate[] toArray(CertPath paramCertPath, TrustAnchor paramTrustAnchor) throws CertificateException {
/* 324 */     List<? extends Certificate> list = paramCertPath.getCertificates();
/* 325 */     X509Certificate[] arrayOfX509Certificate = new X509Certificate[list.size() + 1];
/* 326 */     list.toArray(arrayOfX509Certificate);
/* 327 */     X509Certificate x509Certificate = paramTrustAnchor.getTrustedCert();
/* 328 */     if (x509Certificate == null) {
/* 329 */       throw new ValidatorException("TrustAnchor must be specified as certificate");
/*     */     }
/*     */     
/* 332 */     arrayOfX509Certificate[arrayOfX509Certificate.length - 1] = x509Certificate;
/* 333 */     return arrayOfX509Certificate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDate(PKIXBuilderParameters paramPKIXBuilderParameters) {
/* 341 */     Date date = this.validationDate;
/* 342 */     if (date != null) {
/* 343 */       paramPKIXBuilderParameters.setDate(date);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private X509Certificate[] doValidate(X509Certificate[] paramArrayOfX509Certificate, PKIXBuilderParameters paramPKIXBuilderParameters) throws CertificateException {
/*     */     try {
/* 350 */       setDate(paramPKIXBuilderParameters);
/*     */ 
/*     */       
/* 353 */       CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX");
/* 354 */       CertPath certPath = this.factory.generateCertPath(Arrays.asList((Certificate[])paramArrayOfX509Certificate));
/* 355 */       this.certPathLength = paramArrayOfX509Certificate.length;
/*     */       
/* 357 */       PKIXCertPathValidatorResult pKIXCertPathValidatorResult = (PKIXCertPathValidatorResult)certPathValidator.validate(certPath, paramPKIXBuilderParameters);
/*     */       
/* 359 */       return toArray(certPath, pKIXCertPathValidatorResult.getTrustAnchor());
/* 360 */     } catch (GeneralSecurityException generalSecurityException) {
/* 361 */       throw new ValidatorException("PKIX path validation failed: " + generalSecurityException
/* 362 */           .toString(), generalSecurityException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private X509Certificate[] doBuild(X509Certificate[] paramArrayOfX509Certificate, Collection<X509Certificate> paramCollection, PKIXBuilderParameters paramPKIXBuilderParameters) throws CertificateException {
/*     */     try {
/* 371 */       setDate(paramPKIXBuilderParameters);
/*     */ 
/*     */       
/* 374 */       X509CertSelector x509CertSelector = new X509CertSelector();
/* 375 */       x509CertSelector.setCertificate(paramArrayOfX509Certificate[0]);
/* 376 */       paramPKIXBuilderParameters.setTargetCertConstraints(x509CertSelector);
/*     */ 
/*     */       
/* 379 */       ArrayList<X509Certificate> arrayList = new ArrayList();
/*     */       
/* 381 */       arrayList.addAll(Arrays.asList(paramArrayOfX509Certificate));
/* 382 */       if (paramCollection != null) {
/* 383 */         arrayList.addAll(paramCollection);
/*     */       }
/* 385 */       CertStore certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(arrayList));
/*     */       
/* 387 */       paramPKIXBuilderParameters.addCertStore(certStore);
/*     */ 
/*     */       
/* 390 */       CertPathBuilder certPathBuilder = CertPathBuilder.getInstance("PKIX");
/*     */       
/* 392 */       PKIXCertPathBuilderResult pKIXCertPathBuilderResult = (PKIXCertPathBuilderResult)certPathBuilder.build(paramPKIXBuilderParameters);
/*     */       
/* 394 */       return toArray(pKIXCertPathBuilderResult.getCertPath(), pKIXCertPathBuilderResult.getTrustAnchor());
/* 395 */     } catch (GeneralSecurityException generalSecurityException) {
/* 396 */       throw new ValidatorException("PKIX path building failed: " + generalSecurityException
/* 397 */           .toString(), generalSecurityException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\validator\PKIXValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */