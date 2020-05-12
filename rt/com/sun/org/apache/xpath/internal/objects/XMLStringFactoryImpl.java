/*     */ package com.sun.org.apache.xpath.internal.objects;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.utils.FastStringBuffer;
/*     */ import com.sun.org.apache.xml.internal.utils.XMLString;
/*     */ import com.sun.org.apache.xml.internal.utils.XMLStringFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStringFactoryImpl
/*     */   extends XMLStringFactory
/*     */ {
/*  36 */   private static XMLStringFactory m_xstringfactory = new XMLStringFactoryImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStringFactory getFactory() {
/*  47 */     return m_xstringfactory;
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
/*     */   public XMLString newstr(String string) {
/*  60 */     return new XString(string);
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
/*     */   public XMLString newstr(FastStringBuffer fsb, int start, int length) {
/*  75 */     return new XStringForFSB(fsb, start, length);
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
/*     */   public XMLString newstr(char[] string, int start, int length) {
/*  90 */     return new XStringForChars(string, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLString emptystr() {
/* 100 */     return XString.EMPTYSTRING;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xpath\internal\objects\XMLStringFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */