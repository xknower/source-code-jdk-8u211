/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.ItemSelectable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemEvent
/*     */   extends AWTEvent
/*     */ {
/*     */   public static final int ITEM_FIRST = 701;
/*     */   public static final int ITEM_LAST = 701;
/*     */   public static final int ITEM_STATE_CHANGED = 701;
/*     */   public static final int SELECTED = 1;
/*     */   public static final int DESELECTED = 2;
/*     */   Object item;
/*     */   int stateChange;
/*     */   private static final long serialVersionUID = -608708132447206933L;
/*     */   
/*     */   public ItemEvent(ItemSelectable paramItemSelectable, int paramInt1, Object paramObject, int paramInt2) {
/* 136 */     super(paramItemSelectable, paramInt1);
/* 137 */     this.item = paramObject;
/* 138 */     this.stateChange = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemSelectable getItemSelectable() {
/* 147 */     return (ItemSelectable)this.source;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getItem() {
/* 156 */     return this.item;
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
/*     */   public int getStateChange() {
/* 169 */     return this.stateChange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String paramString() {
/*     */     String str1;
/* 180 */     switch (this.id) {
/*     */       case 701:
/* 182 */         str1 = "ITEM_STATE_CHANGED";
/*     */         break;
/*     */       default:
/* 185 */         str1 = "unknown type";
/*     */         break;
/*     */     } 
/*     */     
/* 189 */     switch (this.stateChange)
/*     */     { case 1:
/* 191 */         str2 = "SELECTED";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 199 */         return str1 + ",item=" + this.item + ",stateChange=" + str2;case 2: str2 = "DESELECTED"; return str1 + ",item=" + this.item + ",stateChange=" + str2; }  String str2 = "unknown type"; return str1 + ",item=" + this.item + ",stateChange=" + str2;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\awt\event\ItemEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */