/*     */ package com.sun.org.apache.xerces.internal.impl.dv.xs;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.InvalidDatatypeValueException;
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.ValidationContext;
/*     */ import com.sun.org.apache.xerces.internal.xs.datatypes.XSFloat;
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
/*     */ public class FloatDV
/*     */   extends TypeValidator
/*     */ {
/*     */   public short getAllowedFacets() {
/*  40 */     return 2552;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
/*     */     try {
/*  46 */       return new XFloat(content);
/*  47 */     } catch (NumberFormatException ex) {
/*  48 */       throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { content, "float" });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int compare(Object value1, Object value2) {
/*  54 */     return ((XFloat)value1).compareTo((XFloat)value2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIdentical(Object value1, Object value2) {
/*  60 */     if (value2 instanceof XFloat) {
/*  61 */       return ((XFloat)value1).isIdentical((XFloat)value2);
/*     */     }
/*  63 */     return false;
/*     */   }
/*     */   
/*     */   private static final class XFloat implements XSFloat {
/*     */     private final float value;
/*     */     
/*     */     public XFloat(String s) throws NumberFormatException {
/*  70 */       if (DoubleDV.isPossibleFP(s)) {
/*  71 */         this.value = Float.parseFloat(s);
/*     */       }
/*  73 */       else if (s.equals("INF")) {
/*  74 */         this.value = Float.POSITIVE_INFINITY;
/*     */       }
/*  76 */       else if (s.equals("-INF")) {
/*  77 */         this.value = Float.NEGATIVE_INFINITY;
/*     */       }
/*  79 */       else if (s.equals("NaN")) {
/*  80 */         this.value = Float.NaN;
/*     */       } else {
/*     */         
/*  83 */         throw new NumberFormatException(s);
/*     */       } 
/*     */     }
/*     */     private String canonical;
/*     */     public boolean equals(Object val) {
/*  88 */       if (val == this) {
/*  89 */         return true;
/*     */       }
/*  91 */       if (!(val instanceof XFloat))
/*  92 */         return false; 
/*  93 */       XFloat oval = (XFloat)val;
/*     */ 
/*     */       
/*  96 */       if (this.value == oval.value) {
/*  97 */         return true;
/*     */       }
/*  99 */       if (this.value != this.value && oval.value != oval.value) {
/* 100 */         return true;
/*     */       }
/* 102 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 107 */       return (this.value == 0.0F) ? 0 : Float.floatToIntBits(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isIdentical(XFloat val) {
/* 112 */       if (val == this) {
/* 113 */         return true;
/*     */       }
/*     */       
/* 116 */       if (this.value == val.value) {
/* 117 */         return (this.value != 0.0F || 
/* 118 */           Float.floatToIntBits(this.value) == Float.floatToIntBits(val.value));
/*     */       }
/*     */       
/* 121 */       if (this.value != this.value && val.value != val.value) {
/* 122 */         return true;
/*     */       }
/* 124 */       return false;
/*     */     }
/*     */     
/*     */     private int compareTo(XFloat val) {
/* 128 */       float oval = val.value;
/*     */ 
/*     */       
/* 131 */       if (this.value < oval) {
/* 132 */         return -1;
/*     */       }
/* 134 */       if (this.value > oval) {
/* 135 */         return 1;
/*     */       }
/*     */       
/* 138 */       if (this.value == oval) {
/* 139 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 143 */       if (this.value != this.value) {
/*     */         
/* 145 */         if (oval != oval) {
/* 146 */           return 0;
/*     */         }
/* 148 */         return 2;
/*     */       } 
/*     */ 
/*     */       
/* 152 */       return 2;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized String toString() {
/* 157 */       if (this.canonical == null) {
/* 158 */         if (this.value == Float.POSITIVE_INFINITY) {
/* 159 */           this.canonical = "INF";
/* 160 */         } else if (this.value == Float.NEGATIVE_INFINITY) {
/* 161 */           this.canonical = "-INF";
/* 162 */         } else if (this.value != this.value) {
/* 163 */           this.canonical = "NaN";
/*     */         }
/* 165 */         else if (this.value == 0.0F) {
/* 166 */           this.canonical = "0.0E1";
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 171 */           this.canonical = Float.toString(this.value);
/*     */ 
/*     */           
/* 174 */           if (this.canonical.indexOf('E') == -1) {
/* 175 */             int len = this.canonical.length();
/*     */             
/* 177 */             char[] chars = new char[len + 3];
/* 178 */             this.canonical.getChars(0, len, chars, 0);
/*     */             
/* 180 */             int edp = (chars[0] == '-') ? 2 : 1;
/*     */             
/* 182 */             if (this.value >= 1.0F || this.value <= -1.0F) {
/*     */               
/* 184 */               int dp = this.canonical.indexOf('.');
/*     */               
/* 186 */               for (int i = dp; i > edp; i--) {
/* 187 */                 chars[i] = chars[i - 1];
/*     */               }
/* 189 */               chars[edp] = '.';
/*     */               
/* 191 */               while (chars[len - 1] == '0') {
/* 192 */                 len--;
/*     */               }
/* 194 */               if (chars[len - 1] == '.') {
/* 195 */                 len++;
/*     */               }
/* 197 */               chars[len++] = 'E';
/*     */               
/* 199 */               int shift = dp - edp;
/*     */ 
/*     */               
/* 202 */               chars[len++] = (char)(shift + 48);
/*     */             }
/*     */             else {
/*     */               
/* 206 */               int nzp = edp + 1;
/*     */               
/* 208 */               while (chars[nzp] == '0') {
/* 209 */                 nzp++;
/*     */               }
/* 211 */               chars[edp - 1] = chars[nzp];
/* 212 */               chars[edp] = '.';
/*     */               
/* 214 */               for (int i = nzp + 1, j = edp + 1; i < len; i++, j++) {
/* 215 */                 chars[j] = chars[i];
/*     */               }
/* 217 */               len -= nzp - edp;
/*     */               
/* 219 */               if (len == edp + 1) {
/* 220 */                 chars[len++] = '0';
/*     */               }
/* 222 */               chars[len++] = 'E';
/* 223 */               chars[len++] = '-';
/*     */               
/* 225 */               int shift = nzp - edp;
/*     */ 
/*     */               
/* 228 */               chars[len++] = (char)(shift + 48);
/*     */             } 
/* 230 */             this.canonical = new String(chars, 0, len);
/*     */           } 
/*     */         } 
/*     */       }
/* 234 */       return this.canonical;
/*     */     }
/*     */     
/*     */     public float getValue() {
/* 238 */       return this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\apache\xerces\internal\impl\dv\xs\FloatDV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */