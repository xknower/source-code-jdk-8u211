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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextOutputCallback
/*     */   implements Callback, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1689502495511663102L;
/*     */   public static final int INFORMATION = 0;
/*     */   public static final int WARNING = 1;
/*     */   public static final int ERROR = 2;
/*     */   private int messageType;
/*     */   private String message;
/*     */   
/*     */   public TextOutputCallback(int paramInt, String paramString) {
/*  76 */     if ((paramInt != 0 && paramInt != 1 && paramInt != 2) || paramString == null || paramString
/*     */       
/*  78 */       .length() == 0) {
/*  79 */       throw new IllegalArgumentException();
/*     */     }
/*  81 */     this.messageType = paramInt;
/*  82 */     this.message = paramString;
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
/*     */   public int getMessageType() {
/*  94 */     return this.messageType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 105 */     return this.message;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\security\auth\callback\TextOutputCallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */