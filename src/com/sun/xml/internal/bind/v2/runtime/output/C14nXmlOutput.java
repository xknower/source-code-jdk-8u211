/*     */ package com.sun.xml.internal.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.istack.internal.FinalArrayList;
/*     */ import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;
/*     */ import com.sun.xml.internal.bind.v2.runtime.Name;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class C14nXmlOutput
/*     */   extends UTF8XmlOutput
/*     */ {
/*     */   private StaticAttribute[] staticAttributes;
/*     */   private int len;
/*     */   private int[] nsBuf;
/*     */   private final FinalArrayList<DynamicAttribute> otherAttributes;
/*     */   private final boolean namedAttributesAreOrdered;
/*     */   
/*     */   public C14nXmlOutput(OutputStream out, Encoded[] localNames, boolean namedAttributesAreOrdered, CharacterEscapeHandler escapeHandler) {
/*  46 */     super(out, localNames, escapeHandler);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.staticAttributes = new StaticAttribute[8];
/*  59 */     this.len = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     this.nsBuf = new int[8];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     this.otherAttributes = new FinalArrayList<>();
/*     */     this.namedAttributesAreOrdered = namedAttributesAreOrdered;
/*     */     for (int i = 0; i < this.staticAttributes.length; i++) {
/*     */       this.staticAttributes[i] = new StaticAttribute();
/*     */     }
/*     */   }
/*     */   
/*     */   final class StaticAttribute
/*     */     implements Comparable<StaticAttribute>
/*     */   {
/*     */     Name name;
/*     */     String value;
/*     */     
/*     */     public void set(Name name, String value) {
/*  87 */       this.name = name;
/*  88 */       this.value = value;
/*     */     }
/*     */     
/*     */     void write() throws IOException {
/*  92 */       C14nXmlOutput.this.attribute(this.name, this.value);
/*     */     }
/*     */     
/*     */     C14nXmlOutput.DynamicAttribute toDynamicAttribute() {
/*  96 */       int prefix, nsUriIndex = this.name.nsUriIndex;
/*     */       
/*  98 */       if (nsUriIndex == -1) {
/*  99 */         prefix = -1;
/*     */       } else {
/* 101 */         prefix = C14nXmlOutput.this.nsUriIndex2prefixIndex[nsUriIndex];
/* 102 */       }  return new C14nXmlOutput.DynamicAttribute(prefix, this.name.localName, this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(StaticAttribute that) {
/* 107 */       return this.name.compareTo(that.name);
/*     */     }
/*     */   }
/*     */   
/*     */   final class DynamicAttribute
/*     */     implements Comparable<DynamicAttribute> {
/*     */     final int prefix;
/*     */     final String localName;
/*     */     final String value;
/*     */     
/*     */     public DynamicAttribute(int prefix, String localName, String value) {
/* 118 */       this.prefix = prefix;
/* 119 */       this.localName = localName;
/* 120 */       this.value = value;
/*     */     }
/*     */     
/*     */     private String getURI() {
/* 124 */       if (this.prefix == -1) return ""; 
/* 125 */       return C14nXmlOutput.this.nsContext.getNamespaceURI(this.prefix);
/*     */     }
/*     */     
/*     */     public int compareTo(DynamicAttribute that) {
/* 129 */       int r = getURI().compareTo(that.getURI());
/* 130 */       if (r != 0) return r; 
/* 131 */       return this.localName.compareTo(that.localName);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void attribute(Name name, String value) throws IOException {
/* 137 */     if (this.staticAttributes.length == this.len) {
/*     */       
/* 139 */       int newLen = this.len * 2;
/* 140 */       StaticAttribute[] newbuf = new StaticAttribute[newLen];
/* 141 */       System.arraycopy(this.staticAttributes, 0, newbuf, 0, this.len);
/* 142 */       for (int i = this.len; i < newLen; i++)
/* 143 */         this.staticAttributes[i] = new StaticAttribute(); 
/* 144 */       this.staticAttributes = newbuf;
/*     */     } 
/*     */     
/* 147 */     this.staticAttributes[this.len++].set(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void attribute(int prefix, String localName, String value) throws IOException {
/* 152 */     this.otherAttributes.add(new DynamicAttribute(prefix, localName, value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void endStartTag() throws IOException {
/* 157 */     if (this.otherAttributes.isEmpty()) {
/* 158 */       if (this.len != 0)
/*     */       {
/*     */         
/* 161 */         if (!this.namedAttributesAreOrdered) {
/* 162 */           Arrays.sort((Object[])this.staticAttributes, 0, this.len);
/*     */         }
/* 164 */         for (int i = 0; i < this.len; i++)
/* 165 */           this.staticAttributes[i].write(); 
/* 166 */         this.len = 0;
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 172 */       for (int i = 0; i < this.len; i++)
/* 173 */         this.otherAttributes.add(this.staticAttributes[i].toDynamicAttribute()); 
/* 174 */       this.len = 0;
/* 175 */       Collections.sort(this.otherAttributes);
/*     */ 
/*     */       
/* 178 */       int size = this.otherAttributes.size();
/* 179 */       for (int j = 0; j < size; j++) {
/* 180 */         DynamicAttribute a = this.otherAttributes.get(j);
/* 181 */         super.attribute(a.prefix, a.localName, a.value);
/*     */       } 
/* 183 */       this.otherAttributes.clear();
/*     */     } 
/* 185 */     super.endStartTag();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeNsDecls(int base) throws IOException {
/* 193 */     int count = this.nsContext.getCurrent().count();
/*     */     
/* 195 */     if (count == 0) {
/*     */       return;
/*     */     }
/* 198 */     if (count > this.nsBuf.length)
/* 199 */       this.nsBuf = new int[count]; 
/*     */     int i;
/* 201 */     for (i = count - 1; i >= 0; i--) {
/* 202 */       this.nsBuf[i] = base + i;
/*     */     }
/*     */ 
/*     */     
/* 206 */     for (i = 0; i < count; i++) {
/* 207 */       for (int j = i + 1; j < count; j++) {
/* 208 */         String p = this.nsContext.getPrefix(this.nsBuf[i]);
/* 209 */         String q = this.nsContext.getPrefix(this.nsBuf[j]);
/* 210 */         if (p.compareTo(q) > 0) {
/*     */           
/* 212 */           int t = this.nsBuf[j];
/* 213 */           this.nsBuf[j] = this.nsBuf[i];
/* 214 */           this.nsBuf[i] = t;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 220 */     for (i = 0; i < count; i++)
/* 221 */       writeNsDecl(this.nsBuf[i]); 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\xml\internal\bind\v2\runtime\output\C14nXmlOutput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */