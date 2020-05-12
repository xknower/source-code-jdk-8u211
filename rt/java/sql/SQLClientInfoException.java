/*     */ package java.sql;
/*     */ 
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SQLClientInfoException
/*     */   extends SQLException
/*     */ {
/*     */   private Map<String, ClientInfoStatus> failedProperties;
/*     */   private static final long serialVersionUID = -4319604256824655880L;
/*     */   
/*     */   public SQLClientInfoException() {
/*  69 */     this.failedProperties = null;
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
/*     */   public SQLClientInfoException(Map<String, ClientInfoStatus> paramMap) {
/*  94 */     this.failedProperties = paramMap;
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
/*     */ 
/*     */   
/*     */   public SQLClientInfoException(Map<String, ClientInfoStatus> paramMap, Throwable paramThrowable) {
/* 121 */     super((paramThrowable != null) ? paramThrowable.toString() : null);
/* 122 */     initCause(paramThrowable);
/* 123 */     this.failedProperties = paramMap;
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
/*     */ 
/*     */   
/*     */   public SQLClientInfoException(String paramString, Map<String, ClientInfoStatus> paramMap) {
/* 150 */     super(paramString);
/* 151 */     this.failedProperties = paramMap;
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
/*     */ 
/*     */   
/*     */   public SQLClientInfoException(String paramString, Map<String, ClientInfoStatus> paramMap, Throwable paramThrowable) {
/* 178 */     super(paramString);
/* 179 */     initCause(paramThrowable);
/* 180 */     this.failedProperties = paramMap;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLClientInfoException(String paramString1, String paramString2, Map<String, ClientInfoStatus> paramMap) {
/* 208 */     super(paramString1, paramString2);
/* 209 */     this.failedProperties = paramMap;
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
/*     */ 
/*     */   
/*     */   public SQLClientInfoException(String paramString1, String paramString2, Map<String, ClientInfoStatus> paramMap, Throwable paramThrowable) {
/* 236 */     super(paramString1, paramString2);
/* 237 */     initCause(paramThrowable);
/* 238 */     this.failedProperties = paramMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLClientInfoException(String paramString1, String paramString2, int paramInt, Map<String, ClientInfoStatus> paramMap) {
/* 267 */     super(paramString1, paramString2, paramInt);
/* 268 */     this.failedProperties = paramMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SQLClientInfoException(String paramString1, String paramString2, int paramInt, Map<String, ClientInfoStatus> paramMap, Throwable paramThrowable) {
/* 298 */     super(paramString1, paramString2, paramInt);
/* 299 */     initCause(paramThrowable);
/* 300 */     this.failedProperties = paramMap;
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
/*     */   public Map<String, ClientInfoStatus> getFailedProperties() {
/* 317 */     return this.failedProperties;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\sql\SQLClientInfoException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */