/*     */ package sun.security.jgss.krb5;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.security.auth.DestroyFailedException;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.kerberos.KerberosKey;
/*     */ import javax.security.auth.kerberos.KerberosPrincipal;
/*     */ import javax.security.auth.kerberos.KerberosTicket;
/*     */ import javax.security.auth.kerberos.KeyTab;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SubjectComber
/*     */ {
/*  49 */   private static final boolean DEBUG = Krb5Util.DEBUG;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> T find(Subject paramSubject, String paramString1, String paramString2, Class<T> paramClass) {
/*  61 */     return paramClass.cast(findAux(paramSubject, paramString1, paramString2, paramClass, true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> List<T> findMany(Subject paramSubject, String paramString1, String paramString2, Class<T> paramClass) {
/*  69 */     return (List<T>)findAux(paramSubject, paramString1, paramString2, paramClass, false);
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
/*     */   private static <T> Object findAux(Subject paramSubject, String paramString1, String paramString2, Class<T> paramClass, boolean paramBoolean) {
/*  83 */     if (paramSubject == null) {
/*  84 */       return null;
/*     */     }
/*  86 */     ArrayList arrayList = paramBoolean ? null : new ArrayList();
/*     */     
/*  88 */     if (paramClass == KeyTab.class) {
/*     */       
/*  90 */       Iterator<KeyTab> iterator = paramSubject.<KeyTab>getPrivateCredentials(KeyTab.class).iterator();
/*  91 */       while (iterator.hasNext()) {
/*  92 */         KeyTab keyTab = iterator.next();
/*  93 */         if (paramString1 != null && keyTab.isBound()) {
/*  94 */           KerberosPrincipal kerberosPrincipal = keyTab.getPrincipal();
/*  95 */           if (kerberosPrincipal != null) {
/*  96 */             if (!paramString1.equals(kerberosPrincipal.getName())) {
/*     */               continue;
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 102 */             boolean bool = false;
/*     */             
/* 104 */             for (KerberosPrincipal kerberosPrincipal1 : paramSubject.<KerberosPrincipal>getPrincipals(KerberosPrincipal.class)) {
/* 105 */               if (kerberosPrincipal1.getName().equals(paramString1)) {
/* 106 */                 bool = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 110 */             if (!bool)
/*     */               continue; 
/*     */           } 
/*     */         } 
/* 114 */         if (DEBUG) {
/* 115 */           System.out.println("Found " + paramClass.getSimpleName() + " " + keyTab);
/*     */         }
/*     */         
/* 118 */         if (paramBoolean) {
/* 119 */           return keyTab;
/*     */         }
/* 121 */         arrayList.add(paramClass.cast(keyTab));
/*     */       }
/*     */     
/* 124 */     } else if (paramClass == KerberosKey.class) {
/*     */ 
/*     */       
/* 127 */       Iterator<KerberosKey> iterator = paramSubject.<KerberosKey>getPrivateCredentials(KerberosKey.class).iterator();
/* 128 */       while (iterator.hasNext()) {
/* 129 */         KerberosKey kerberosKey = iterator.next();
/* 130 */         String str = kerberosKey.getPrincipal().getName();
/* 131 */         if (paramString1 == null || paramString1.equals(str)) {
/* 132 */           if (DEBUG) {
/* 133 */             System.out.println("Found " + paramClass
/* 134 */                 .getSimpleName() + " for " + str);
/*     */           }
/* 136 */           if (paramBoolean) {
/* 137 */             return kerberosKey;
/*     */           }
/* 139 */           arrayList.add(paramClass.cast(kerberosKey));
/*     */         }
/*     */       
/*     */       } 
/* 143 */     } else if (paramClass == KerberosTicket.class) {
/*     */ 
/*     */       
/* 146 */       Set set = paramSubject.getPrivateCredentials();
/* 147 */       synchronized (set) {
/* 148 */         Iterator<Object> iterator = set.iterator();
/* 149 */         while (iterator.hasNext()) {
/* 150 */           KerberosTicket kerberosTicket = (KerberosTicket)iterator.next();
/* 151 */           if (kerberosTicket instanceof KerberosTicket) {
/*     */             
/* 153 */             KerberosTicket kerberosTicket1 = kerberosTicket;
/* 154 */             if (DEBUG) {
/* 155 */               System.out.println("Found ticket for " + kerberosTicket1
/* 156 */                   .getClient() + " to go to " + kerberosTicket1
/*     */                   
/* 158 */                   .getServer() + " expiring on " + kerberosTicket1
/*     */                   
/* 160 */                   .getEndTime());
/*     */             }
/* 162 */             if (!kerberosTicket1.isCurrent()) {
/*     */ 
/*     */ 
/*     */               
/* 166 */               if (!paramSubject.isReadOnly()) {
/* 167 */                 iterator.remove();
/*     */                 try {
/* 169 */                   kerberosTicket1.destroy();
/* 170 */                   if (DEBUG) {
/* 171 */                     System.out.println("Removed and destroyed the expired Ticket \n" + kerberosTicket1);
/*     */                   
/*     */                   }
/*     */                 
/*     */                 }
/* 176 */                 catch (DestroyFailedException destroyFailedException) {
/* 177 */                   if (DEBUG) {
/* 178 */                     System.out.println("Expired ticket not detroyed successfully. " + destroyFailedException);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */               
/*     */               continue;
/*     */             } 
/* 185 */             if (paramString1 == null || kerberosTicket1
/* 186 */               .getServer().getName().equals(paramString1))
/*     */             {
/* 188 */               if (paramString2 == null || paramString2
/* 189 */                 .equals(kerberosTicket1
/* 190 */                   .getClient().getName())) {
/* 191 */                 if (paramBoolean) {
/* 192 */                   return kerberosTicket1;
/*     */                 }
/*     */ 
/*     */                 
/* 196 */                 if (paramString2 == null)
/*     */                 {
/* 198 */                   paramString2 = kerberosTicket1.getClient().getName();
/*     */                 }
/* 200 */                 if (paramString1 == null)
/*     */                 {
/* 202 */                   paramString1 = kerberosTicket1.getServer().getName();
/*     */                 }
/* 204 */                 arrayList.add(paramClass.cast(kerberosTicket1));
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 213 */     return arrayList;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\security\jgss\krb5\SubjectComber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */