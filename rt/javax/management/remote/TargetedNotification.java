/*     */ package javax.management.remote;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import javax.management.Notification;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TargetedNotification
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7676132089779300926L;
/*     */   private Notification notif;
/*     */   private Integer id;
/*     */   
/*     */   public TargetedNotification(Notification paramNotification, Integer paramInteger) {
/*  79 */     validate(paramNotification, paramInteger);
/*     */ 
/*     */     
/*  82 */     this.notif = paramNotification;
/*  83 */     this.id = paramInteger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Notification getNotification() {
/*  92 */     return this.notif;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getListenerID() {
/* 102 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 111 */     return "{" + this.notif + ", " + this.id + "}";
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 135 */     paramObjectInputStream.defaultReadObject();
/*     */     try {
/* 137 */       validate(this.notif, this.id);
/* 138 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 139 */       throw new InvalidObjectException(illegalArgumentException.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void validate(Notification paramNotification, Integer paramInteger) throws IllegalArgumentException {
/* 144 */     if (paramNotification == null) {
/* 145 */       throw new IllegalArgumentException("Invalid notification: null");
/*     */     }
/* 147 */     if (paramInteger == null)
/* 148 */       throw new IllegalArgumentException("Invalid listener ID: null"); 
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\management\remote\TargetedNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */