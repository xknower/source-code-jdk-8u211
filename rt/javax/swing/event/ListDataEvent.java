/*     */ package javax.swing.event;
/*     */ 
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
/*     */ public class ListDataEvent
/*     */   extends EventObject
/*     */ {
/*     */   public static final int CONTENTS_CHANGED = 0;
/*     */   public static final int INTERVAL_ADDED = 1;
/*     */   public static final int INTERVAL_REMOVED = 2;
/*     */   private int type;
/*     */   private int index0;
/*     */   private int index1;
/*     */   
/*     */   public int getType() {
/*  69 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex0() {
/*  78 */     return this.index0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex1() {
/*  85 */     return this.index1;
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
/*     */   public ListDataEvent(Object paramObject, int paramInt1, int paramInt2, int paramInt3) {
/*  99 */     super(paramObject);
/* 100 */     this.type = paramInt1;
/* 101 */     this.index0 = Math.min(paramInt2, paramInt3);
/* 102 */     this.index1 = Math.max(paramInt2, paramInt3);
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
/*     */   public String toString() {
/* 116 */     return getClass().getName() + "[type=" + this.type + ",index0=" + this.index0 + ",index1=" + this.index1 + "]";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\event\ListDataEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */