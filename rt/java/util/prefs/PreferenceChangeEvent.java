/*     */ package java.util.prefs;
/*     */ 
/*     */ import java.io.NotSerializableException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.EventObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PreferenceChangeEvent
/*     */   extends EventObject
/*     */ {
/*     */   private String key;
/*     */   private String newValue;
/*     */   private static final long serialVersionUID = 793724513368024975L;
/*     */   
/*     */   public PreferenceChangeEvent(Preferences paramPreferences, String paramString1, String paramString2) {
/*  71 */     super(paramPreferences);
/*  72 */     this.key = paramString1;
/*  73 */     this.newValue = paramString2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Preferences getNode() {
/*  82 */     return (Preferences)getSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKey() {
/*  91 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNewValue() {
/* 101 */     return this.newValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws NotSerializableException {
/* 110 */     throw new NotSerializableException("Not serializable.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws NotSerializableException {
/* 119 */     throw new NotSerializableException("Not serializable.");
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\prefs\PreferenceChangeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */