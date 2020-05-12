/*     */ package com.sun.org.apache.xerces.internal.util;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
/*     */ import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
/*     */ import javax.xml.transform.Source;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XMLInputSourceAdaptor
/*     */   implements Source
/*     */ {
/*     */   public final XMLInputSource fSource;
/*     */   
/*     */   public XMLInputSourceAdaptor(XMLInputSource core) {
/*  92 */     this.fSource = core;
/*     */   }
/*     */   
/*     */   public void setSystemId(String systemId) {
/*  96 */     this.fSource.setSystemId(systemId);
/*     */   }
/*     */   
/*     */   public String getSystemId() {
/*     */     try {
/* 101 */       return XMLEntityManager.expandSystemId(this.fSource
/* 102 */           .getSystemId(), this.fSource.getBaseSystemId(), false);
/* 103 */     } catch (MalformedURIException e) {
/* 104 */       return this.fSource.getSystemId();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\interna\\util\XMLInputSourceAdaptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */