/*     */ package javax.print.attribute;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Locale;
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
/*     */ public abstract class TextSyntax
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = -8130648736378144102L;
/*     */   private String value;
/*     */   private Locale locale;
/*     */   
/*     */   protected TextSyntax(String paramString, Locale paramLocale) {
/*  71 */     this.value = verify(paramString);
/*  72 */     this.locale = verify(paramLocale);
/*     */   }
/*     */   
/*     */   private static String verify(String paramString) {
/*  76 */     if (paramString == null) {
/*  77 */       throw new NullPointerException(" value is null");
/*     */     }
/*  79 */     return paramString;
/*     */   }
/*     */   
/*     */   private static Locale verify(Locale paramLocale) {
/*  83 */     if (paramLocale == null) {
/*  84 */       return Locale.getDefault();
/*     */     }
/*  86 */     return paramLocale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  94 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/* 102 */     return this.locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return this.value.hashCode() ^ this.locale.hashCode();
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
/*     */   public boolean equals(Object paramObject) {
/* 136 */     return (paramObject != null && paramObject instanceof TextSyntax && this.value
/*     */       
/* 138 */       .equals(((TextSyntax)paramObject).value) && this.locale
/* 139 */       .equals(((TextSyntax)paramObject).locale));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 149 */     return this.value;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\print\attribute\TextSyntax.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */