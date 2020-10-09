/*     */ package sun.awt.windows;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.List;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.peer.ListPeer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WListPeer
/*     */   extends WComponentPeer
/*     */   implements ListPeer
/*     */ {
/*     */   private FontMetrics fm;
/*     */   
/*     */   public boolean isFocusable() {
/*  36 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getSelectedIndexes() {
/*  43 */     List list = (List)this.target;
/*  44 */     int i = list.countItems();
/*  45 */     int[] arrayOfInt1 = new int[i];
/*  46 */     byte b1 = 0;
/*  47 */     for (byte b2 = 0; b2 < i; b2++) {
/*  48 */       if (isSelected(b2)) {
/*  49 */         arrayOfInt1[b1++] = b2;
/*     */       }
/*     */     } 
/*  52 */     int[] arrayOfInt2 = new int[b1];
/*  53 */     System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, b1);
/*  54 */     return arrayOfInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String paramString, int paramInt) {
/*  60 */     addItem(paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAll() {
/*  66 */     clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultipleMode(boolean paramBoolean) {
/*  72 */     setMultipleSelections(paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(int paramInt) {
/*  78 */     return preferredSize(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getMinimumSize(int paramInt) {
/*  84 */     return minimumSize(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addItem(String paramString, int paramInt) {
/*  89 */     addItems(new String[] { paramString }, paramInt, this.fm.stringWidth(paramString));
/*     */   }
/*     */   native void addItems(String[] paramArrayOfString, int paramInt1, int paramInt2);
/*     */   
/*     */   public native void delItems(int paramInt1, int paramInt2);
/*     */   
/*     */   public void clear() {
/*  96 */     List list = (List)this.target;
/*  97 */     delItems(0, list.countItems());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public native void select(int paramInt);
/*     */ 
/*     */   
/*     */   public native void deselect(int paramInt);
/*     */ 
/*     */   
/*     */   public Dimension preferredSize(int paramInt) {
/* 109 */     if (this.fm == null) {
/* 110 */       List list = (List)this.target;
/* 111 */       this.fm = getFontMetrics(list.getFont());
/*     */     } 
/* 113 */     Dimension dimension = minimumSize(paramInt);
/* 114 */     dimension.width = Math.max(dimension.width, getMaxWidth() + 20);
/* 115 */     return dimension;
/*     */   } public native void makeVisible(int paramInt); public native void setMultipleSelections(boolean paramBoolean); public native int getMaxWidth();
/*     */   public Dimension minimumSize(int paramInt) {
/* 118 */     return new Dimension(20 + this.fm.stringWidth("0123456789abcde"), this.fm
/* 119 */         .getHeight() * paramInt + 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   WListPeer(List paramList) {
/* 125 */     super(paramList);
/*     */   }
/*     */ 
/*     */   
/*     */   native void create(WComponentPeer paramWComponentPeer);
/*     */ 
/*     */   
/*     */   void initialize() {
/* 133 */     List list = (List)this.target;
/*     */     
/* 135 */     this.fm = getFontMetrics(list.getFont());
/*     */ 
/*     */     
/* 138 */     Font font = list.getFont();
/* 139 */     if (font != null) {
/* 140 */       setFont(font);
/*     */     }
/*     */ 
/*     */     
/* 144 */     int i = list.countItems();
/* 145 */     if (i > 0) {
/* 146 */       String[] arrayOfString = new String[i];
/* 147 */       int k = 0;
/* 148 */       int m = 0;
/* 149 */       for (byte b = 0; b < i; b++) {
/* 150 */         arrayOfString[b] = list.getItem(b);
/* 151 */         m = this.fm.stringWidth(arrayOfString[b]);
/* 152 */         if (m > k) {
/* 153 */           k = m;
/*     */         }
/*     */       } 
/* 156 */       addItems(arrayOfString, 0, k);
/*     */     } 
/*     */ 
/*     */     
/* 160 */     setMultipleSelections(list.allowsMultipleSelections());
/*     */ 
/*     */     
/* 163 */     int[] arrayOfInt = list.getSelectedIndexes(); int j;
/* 164 */     for (j = 0; j < arrayOfInt.length; j++) {
/* 165 */       select(arrayOfInt[j]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     j = list.getVisibleIndex();
/* 175 */     if (j < 0 && arrayOfInt.length > 0) {
/* 176 */       j = arrayOfInt[0];
/*     */     }
/* 178 */     if (j >= 0) {
/* 179 */       makeVisible(j);
/*     */     }
/*     */     
/* 182 */     super.initialize();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldClearRectBeforePaint() {
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private native void updateMaxItemWidth();
/*     */ 
/*     */   
/*     */   native boolean isSelected(int paramInt);
/*     */ 
/*     */   
/*     */   synchronized void _setFont(Font paramFont) {
/* 198 */     super._setFont(paramFont);
/* 199 */     this.fm = getFontMetrics(((List)this.target).getFont());
/* 200 */     updateMaxItemWidth();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void handleAction(final int index, final long when, final int modifiers) {
/* 206 */     final List l = (List)this.target;
/* 207 */     WToolkit.executeOnEventHandlerThread(list, new Runnable()
/*     */         {
/*     */           public void run() {
/* 210 */             l.select(index);
/* 211 */             WListPeer.this.postEvent(new ActionEvent(WListPeer.this.target, 1001, l
/* 212 */                   .getItem(index), when, modifiers));
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   void handleListChanged(final int index) {
/* 218 */     final List l = (List)this.target;
/* 219 */     WToolkit.executeOnEventHandlerThread(list, new Runnable()
/*     */         {
/*     */           public void run() {
/* 222 */             WListPeer.this.postEvent(new ItemEvent(l, 701, 
/* 223 */                   Integer.valueOf(index), 
/* 224 */                   WListPeer.this.isSelected(index) ? 1 : 2));
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\awt\windows\WListPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */