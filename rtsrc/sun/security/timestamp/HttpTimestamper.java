/*     */ package sun.security.timestamp;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import sun.misc.IOUtils;
/*     */ import sun.security.util.Debug;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpTimestamper
/*     */   implements Timestamper
/*     */ {
/*     */   private static final int CONNECT_TIMEOUT = 15000;
/*     */   private static final String TS_QUERY_MIME_TYPE = "application/timestamp-query";
/*     */   private static final String TS_REPLY_MIME_TYPE = "application/timestamp-reply";
/*  61 */   private static final Debug debug = Debug.getInstance("ts");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private URI tsaURI = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpTimestamper(URI paramURI) {
/*  75 */     if (!paramURI.getScheme().equalsIgnoreCase("http") && 
/*  76 */       !paramURI.getScheme().equalsIgnoreCase("https")) {
/*  77 */       throw new IllegalArgumentException("TSA must be an HTTP or HTTPS URI");
/*     */     }
/*     */     
/*  80 */     this.tsaURI = paramURI;
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
/*     */   public TSResponse generateTimestamp(TSRequest paramTSRequest) throws IOException {
/*  94 */     HttpURLConnection httpURLConnection = (HttpURLConnection)this.tsaURI.toURL().openConnection();
/*  95 */     httpURLConnection.setDoOutput(true);
/*  96 */     httpURLConnection.setUseCaches(false);
/*  97 */     httpURLConnection.setRequestProperty("Content-Type", "application/timestamp-query");
/*  98 */     httpURLConnection.setRequestMethod("POST");
/*     */     
/* 100 */     httpURLConnection.setConnectTimeout(15000);
/*     */     
/* 102 */     if (debug != null) {
/*     */       
/* 104 */       Set<Map.Entry<String, List<String>>> set = httpURLConnection.getRequestProperties().entrySet();
/* 105 */       debug.println(httpURLConnection.getRequestMethod() + " " + this.tsaURI + " HTTP/1.1");
/*     */       
/* 107 */       for (Map.Entry<String, List<String>> entry : set) {
/* 108 */         debug.println("  " + entry);
/*     */       }
/* 110 */       debug.println();
/*     */     } 
/* 112 */     httpURLConnection.connect();
/*     */ 
/*     */     
/* 115 */     DataOutputStream dataOutputStream = null;
/*     */     try {
/* 117 */       dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
/* 118 */       byte[] arrayOfByte1 = paramTSRequest.encode();
/* 119 */       dataOutputStream.write(arrayOfByte1, 0, arrayOfByte1.length);
/* 120 */       dataOutputStream.flush();
/* 121 */       if (debug != null) {
/* 122 */         debug.println("sent timestamp query (length=" + arrayOfByte1.length + ")");
/*     */       }
/*     */     } finally {
/*     */       
/* 126 */       if (dataOutputStream != null) {
/* 127 */         dataOutputStream.close();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 132 */     BufferedInputStream bufferedInputStream = null;
/* 133 */     byte[] arrayOfByte = null;
/*     */     try {
/* 135 */       bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
/* 136 */       if (debug != null) {
/* 137 */         String str = httpURLConnection.getHeaderField(0);
/* 138 */         debug.println(str);
/* 139 */         byte b = 1;
/* 140 */         while ((str = httpURLConnection.getHeaderField(b)) != null) {
/* 141 */           String str1 = httpURLConnection.getHeaderFieldKey(b);
/* 142 */           debug.println("  " + ((str1 == null) ? "" : (str1 + ": ")) + str);
/*     */           
/* 144 */           b++;
/*     */         } 
/* 146 */         debug.println();
/*     */       } 
/* 148 */       verifyMimeType(httpURLConnection.getContentType());
/*     */       
/* 150 */       int i = httpURLConnection.getContentLength();
/* 151 */       arrayOfByte = IOUtils.readFully(bufferedInputStream, i, false);
/*     */       
/* 153 */       if (debug != null) {
/* 154 */         debug.println("received timestamp response (length=" + arrayOfByte.length + ")");
/*     */       }
/*     */     } finally {
/*     */       
/* 158 */       if (bufferedInputStream != null) {
/* 159 */         bufferedInputStream.close();
/*     */       }
/*     */     } 
/* 162 */     return new TSResponse(arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void verifyMimeType(String paramString) throws IOException {
/* 172 */     if (!"application/timestamp-reply".equalsIgnoreCase(paramString))
/* 173 */       throw new IOException("MIME Content-Type is not application/timestamp-reply"); 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\timestamp\HttpTimestamper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */