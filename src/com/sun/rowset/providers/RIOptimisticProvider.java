/*     */ package com.sun.rowset.providers;
/*     */ 
/*     */ import com.sun.rowset.JdbcRowSetResourceBundle;
/*     */ import com.sun.rowset.internal.CachedRowSetReader;
/*     */ import com.sun.rowset.internal.CachedRowSetWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.sql.SQLException;
/*     */ import javax.sql.RowSetReader;
/*     */ import javax.sql.RowSetWriter;
/*     */ import javax.sql.rowset.spi.SyncProvider;
/*     */ import javax.sql.rowset.spi.SyncProviderException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RIOptimisticProvider
/*     */   extends SyncProvider
/*     */   implements Serializable
/*     */ {
/*     */   private CachedRowSetReader reader;
/*     */   private CachedRowSetWriter writer;
/*  98 */   private String providerID = "com.sun.rowset.providers.RIOptimisticProvider";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private String vendorName = "Oracle Corporation";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private String versionNumber = "1.0";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JdbcRowSetResourceBundle resBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final long serialVersionUID = -3143367176751761936L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RIOptimisticProvider() {
/* 124 */     this.providerID = getClass().getName();
/* 125 */     this.reader = new CachedRowSetReader();
/* 126 */     this.writer = new CachedRowSetWriter();
/*     */     try {
/* 128 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 129 */     } catch (IOException iOException) {
/* 130 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getProviderID() {
/* 141 */     return this.providerID;
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
/*     */   public RowSetWriter getRowSetWriter() {
/*     */     try {
/* 154 */       this.writer.setReader(this.reader);
/* 155 */     } catch (SQLException sQLException) {}
/* 156 */     return this.writer;
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
/*     */   public RowSetReader getRowSetReader() {
/* 168 */     return this.reader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProviderGrade() {
/* 179 */     return 2;
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
/*     */   public void setDataSourceLock(int paramInt) throws SyncProviderException {
/* 198 */     if (paramInt != 1) {
/* 199 */       throw new SyncProviderException(this.resBundle.handleGetObject("riop.locking").toString());
/*     */     }
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
/*     */   public int getDataSourceLock() throws SyncProviderException {
/* 212 */     return 1;
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
/*     */   public int supportsUpdatableView() {
/* 225 */     return 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 235 */     return this.versionNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVendor() {
/* 246 */     return this.vendorName;
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 251 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */     try {
/* 254 */       this.resBundle = JdbcRowSetResourceBundle.getJdbcRowSetResourceBundle();
/* 255 */     } catch (IOException iOException) {
/* 256 */       throw new RuntimeException(iOException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\rowset\providers\RIOptimisticProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */