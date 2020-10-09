/*     */ package sun.security.krb5;
/*     */ 
/*     */ import sun.security.krb5.internal.KDCRep;
/*     */ import sun.security.krb5.internal.KDCReq;
/*     */ import sun.security.krb5.internal.Krb5;
/*     */ import sun.security.krb5.internal.KrbApErrException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class KrbKdcRep
/*     */ {
/*     */   static void check(boolean paramBoolean, KDCReq paramKDCReq, KDCRep paramKDCRep) throws KrbApErrException {
/*  43 */     if (paramBoolean && !paramKDCReq.reqBody.cname.equals(paramKDCRep.cname)) {
/*  44 */       paramKDCRep.encKDCRepPart.key.destroy();
/*  45 */       throw new KrbApErrException(41);
/*     */     } 
/*     */     
/*  48 */     if (!paramKDCReq.reqBody.sname.equals(paramKDCRep.encKDCRepPart.sname)) {
/*  49 */       paramKDCRep.encKDCRepPart.key.destroy();
/*  50 */       throw new KrbApErrException(41);
/*     */     } 
/*     */     
/*  53 */     if (paramKDCReq.reqBody.getNonce() != paramKDCRep.encKDCRepPart.nonce) {
/*  54 */       paramKDCRep.encKDCRepPart.key.destroy();
/*  55 */       throw new KrbApErrException(41);
/*     */     } 
/*     */     
/*  58 */     if (paramKDCReq.reqBody.addresses != null && paramKDCRep.encKDCRepPart.caddr != null && 
/*     */       
/*  60 */       !paramKDCReq.reqBody.addresses.equals(paramKDCRep.encKDCRepPart.caddr)) {
/*  61 */       paramKDCRep.encKDCRepPart.key.destroy();
/*  62 */       throw new KrbApErrException(41);
/*     */     } 
/*     */ 
/*     */     
/*  66 */     for (byte b = 2; b < 6; b++) {
/*  67 */       if (paramKDCReq.reqBody.kdcOptions.get(b) != paramKDCRep.encKDCRepPart.flags
/*  68 */         .get(b)) {
/*  69 */         if (Krb5.DEBUG) {
/*  70 */           System.out.println("> KrbKdcRep.check: at #" + b + ". request for " + paramKDCReq.reqBody.kdcOptions
/*  71 */               .get(b) + ", received " + paramKDCRep.encKDCRepPart.flags
/*  72 */               .get(b));
/*     */         }
/*  74 */         throw new KrbApErrException(41);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     if (paramKDCReq.reqBody.kdcOptions.get(8) && 
/*  82 */       !paramKDCRep.encKDCRepPart.flags.get(8)) {
/*  83 */       throw new KrbApErrException(41);
/*     */     }
/*  85 */     if (paramKDCReq.reqBody.from == null || paramKDCReq.reqBody.from.isZero())
/*     */     {
/*  87 */       if (paramKDCRep.encKDCRepPart.starttime != null && 
/*  88 */         !paramKDCRep.encKDCRepPart.starttime.inClockSkew()) {
/*  89 */         paramKDCRep.encKDCRepPart.key.destroy();
/*  90 */         throw new KrbApErrException(37);
/*     */       } 
/*     */     }
/*  93 */     if (paramKDCReq.reqBody.from != null && !paramKDCReq.reqBody.from.isZero())
/*     */     {
/*  95 */       if (paramKDCRep.encKDCRepPart.starttime != null && 
/*  96 */         !paramKDCReq.reqBody.from.equals(paramKDCRep.encKDCRepPart.starttime)) {
/*  97 */         paramKDCRep.encKDCRepPart.key.destroy();
/*  98 */         throw new KrbApErrException(41);
/*     */       } 
/*     */     }
/* 101 */     if (!paramKDCReq.reqBody.till.isZero() && paramKDCRep.encKDCRepPart.endtime
/* 102 */       .greaterThan(paramKDCReq.reqBody.till)) {
/* 103 */       paramKDCRep.encKDCRepPart.key.destroy();
/* 104 */       throw new KrbApErrException(41);
/*     */     } 
/*     */     
/* 107 */     if (paramKDCReq.reqBody.kdcOptions.get(8) && 
/* 108 */       paramKDCReq.reqBody.rtime != null && !paramKDCReq.reqBody.rtime.isZero())
/*     */     {
/* 110 */       if (paramKDCRep.encKDCRepPart.renewTill == null || paramKDCRep.encKDCRepPart.renewTill
/* 111 */         .greaterThan(paramKDCReq.reqBody.rtime)) {
/*     */         
/* 113 */         paramKDCRep.encKDCRepPart.key.destroy();
/* 114 */         throw new KrbApErrException(41);
/*     */       } 
/*     */     }
/* 117 */     if (paramKDCReq.reqBody.kdcOptions.get(27) && paramKDCRep.encKDCRepPart.flags
/* 118 */       .get(8) && 
/* 119 */       !paramKDCReq.reqBody.till.isZero())
/*     */     {
/* 121 */       if (paramKDCRep.encKDCRepPart.renewTill == null || paramKDCRep.encKDCRepPart.renewTill
/* 122 */         .greaterThan(paramKDCReq.reqBody.till)) {
/*     */         
/* 124 */         paramKDCRep.encKDCRepPart.key.destroy();
/* 125 */         throw new KrbApErrException(41);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\krb5\KrbKdcRep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */