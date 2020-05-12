/*     */ package javax.security.auth.callback;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextInputCallback
/*     */   implements Callback, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8064222478852811804L;
/*     */   private String prompt;
/*     */   private String defaultText;
/*     */   private String inputText;
/*     */   
/*     */   public TextInputCallback(String paramString) {
/*  67 */     if (paramString == null || paramString.length() == 0)
/*  68 */       throw new IllegalArgumentException(); 
/*  69 */     this.prompt = paramString;
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
/*     */   public TextInputCallback(String paramString1, String paramString2) {
/*  89 */     if (paramString1 == null || paramString1.length() == 0 || paramString2 == null || paramString2
/*  90 */       .length() == 0) {
/*  91 */       throw new IllegalArgumentException();
/*     */     }
/*  93 */     this.prompt = paramString1;
/*  94 */     this.defaultText = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPrompt() {
/* 105 */     return this.prompt;
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
/*     */   public String getDefaultText() {
/* 117 */     return this.defaultText;
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
/*     */   public void setText(String paramString) {
/* 130 */     this.inputText = paramString;
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
/*     */   public String getText() {
/* 143 */     return this.inputText;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\security\auth\callback\TextInputCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */