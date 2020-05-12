/*     */ package com.sun.corba.se.impl.presentation.rmi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IDLType
/*     */ {
/*     */   private Class cl_;
/*     */   private String[] modules_;
/*     */   private String memberName_;
/*     */   
/*     */   public IDLType(Class paramClass, String[] paramArrayOfString, String paramString) {
/*  43 */     this.cl_ = paramClass;
/*  44 */     this.modules_ = paramArrayOfString;
/*  45 */     this.memberName_ = paramString;
/*     */   }
/*     */   
/*     */   public IDLType(Class paramClass, String paramString) {
/*  49 */     this(paramClass, new String[0], paramString);
/*     */   }
/*     */   
/*     */   public Class getJavaClass() {
/*  53 */     return this.cl_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getModules() {
/*  58 */     return this.modules_;
/*     */   }
/*     */   
/*     */   public String makeConcatenatedName(char paramChar, boolean paramBoolean) {
/*  62 */     StringBuffer stringBuffer = new StringBuffer();
/*  63 */     for (byte b = 0; b < this.modules_.length; b++) {
/*  64 */       String str = this.modules_[b];
/*  65 */       if (b > 0) {
/*  66 */         stringBuffer.append(paramChar);
/*     */       }
/*  68 */       if (paramBoolean && IDLNameTranslatorImpl.isIDLKeyword(str)) {
/*  69 */         str = IDLNameTranslatorImpl.mangleIDLKeywordClash(str);
/*     */       }
/*  71 */       stringBuffer.append(str);
/*     */     } 
/*     */     
/*  74 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModuleName() {
/*  85 */     return makeConcatenatedName('_', false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExceptionName() {
/*  94 */     String str1 = makeConcatenatedName('/', true);
/*     */     
/*  96 */     String str2 = "Exception";
/*  97 */     String str3 = this.memberName_;
/*  98 */     if (str3.endsWith(str2)) {
/*  99 */       int i = str3.length() - str2.length();
/* 100 */       str3 = str3.substring(0, i);
/*     */     } 
/*     */ 
/*     */     
/* 104 */     str3 = str3 + "Ex";
/*     */     
/* 106 */     if (str1.length() == 0) {
/* 107 */       return "IDL:" + str3 + ":1.0";
/*     */     }
/* 109 */     return "IDL:" + str1 + '/' + str3 + ":1.0";
/*     */   }
/*     */   
/*     */   public String getMemberName() {
/* 113 */     return this.memberName_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasModule() {
/* 122 */     return (this.modules_.length > 0);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\presentation\rmi\IDLType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */