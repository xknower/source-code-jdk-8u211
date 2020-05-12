/*     */ package com.sun.xml.internal.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.internal.bind.v2.runtime.Name;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.xml.sax.Attributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TagName
/*     */ {
/*     */   public String uri;
/*     */   public String local;
/*     */   public Attributes atts;
/*     */   
/*     */   public final boolean matches(String nsUri, String local) {
/*  85 */     return (this.uri == nsUri && this.local == local);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean matches(Name name) {
/*  92 */     return (this.local == name.localName && this.uri == name.nsUri);
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
/*     */   public String toString() {
/* 106 */     return '{' + this.uri + '}' + this.local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getQname();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 122 */     String qname = getQname();
/* 123 */     int idx = qname.indexOf(':');
/* 124 */     if (idx < 0) return ""; 
/* 125 */     return qname.substring(0, idx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName createQName() {
/* 132 */     return new QName(this.uri, this.local, getPrefix());
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtim\\unmarshaller\TagName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */