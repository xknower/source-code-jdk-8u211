/*     */ package javax.swing.table;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.Serializable;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.event.SwingPropertyChangeSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TableColumn
/*     */   implements Serializable
/*     */ {
/*     */   public static final String COLUMN_WIDTH_PROPERTY = "columWidth";
/*     */   public static final String HEADER_VALUE_PROPERTY = "headerValue";
/*     */   public static final String HEADER_RENDERER_PROPERTY = "headerRenderer";
/*     */   public static final String CELL_RENDERER_PROPERTY = "cellRenderer";
/*     */   protected int modelIndex;
/*     */   protected Object identifier;
/*     */   protected int width;
/*     */   protected int minWidth;
/*     */   private int preferredWidth;
/*     */   protected int maxWidth;
/*     */   protected TableCellRenderer headerRenderer;
/*     */   protected Object headerValue;
/*     */   protected TableCellRenderer cellRenderer;
/*     */   protected TableCellEditor cellEditor;
/*     */   protected boolean isResizable;
/*     */   @Deprecated
/*     */   protected transient int resizedPostingDisableCount;
/*     */   private SwingPropertyChangeSupport changeSupport;
/*     */   
/*     */   public TableColumn() {
/* 196 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableColumn(int paramInt) {
/* 205 */     this(paramInt, 75, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableColumn(int paramInt1, int paramInt2) {
/* 214 */     this(paramInt1, paramInt2, null, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableColumn(int paramInt1, int paramInt2, TableCellRenderer paramTableCellRenderer, TableCellEditor paramTableCellEditor) {
/* 258 */     this.modelIndex = paramInt1;
/* 259 */     this.preferredWidth = this.width = Math.max(paramInt2, 0);
/*     */     
/* 261 */     this.cellRenderer = paramTableCellRenderer;
/* 262 */     this.cellEditor = paramTableCellEditor;
/*     */ 
/*     */     
/* 265 */     this.minWidth = Math.min(15, this.width);
/* 266 */     this.maxWidth = Integer.MAX_VALUE;
/* 267 */     this.isResizable = true;
/* 268 */     this.resizedPostingDisableCount = 0;
/* 269 */     this.headerValue = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void firePropertyChange(String paramString, Object paramObject1, Object paramObject2) {
/* 277 */     if (this.changeSupport != null) {
/* 278 */       this.changeSupport.firePropertyChange(paramString, paramObject1, paramObject2);
/*     */     }
/*     */   }
/*     */   
/*     */   private void firePropertyChange(String paramString, int paramInt1, int paramInt2) {
/* 283 */     if (paramInt1 != paramInt2) {
/* 284 */       firePropertyChange(paramString, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
/*     */     }
/*     */   }
/*     */   
/*     */   private void firePropertyChange(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
/* 289 */     if (paramBoolean1 != paramBoolean2) {
/* 290 */       firePropertyChange(paramString, Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2));
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
/*     */ 
/*     */   
/*     */   public void setModelIndex(int paramInt) {
/* 305 */     int i = this.modelIndex;
/* 306 */     this.modelIndex = paramInt;
/* 307 */     firePropertyChange("modelIndex", i, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getModelIndex() {
/* 315 */     return this.modelIndex;
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
/*     */   public void setIdentifier(Object paramObject) {
/* 332 */     Object object = this.identifier;
/* 333 */     this.identifier = paramObject;
/* 334 */     firePropertyChange("identifier", object, paramObject);
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
/*     */   public Object getIdentifier() {
/* 350 */     return (this.identifier != null) ? this.identifier : getHeaderValue();
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
/*     */   public void setHeaderValue(Object paramObject) {
/* 366 */     Object object = this.headerValue;
/* 367 */     this.headerValue = paramObject;
/* 368 */     firePropertyChange("headerValue", object, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getHeaderValue() {
/* 379 */     return this.headerValue;
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
/*     */   public void setHeaderRenderer(TableCellRenderer paramTableCellRenderer) {
/* 402 */     TableCellRenderer tableCellRenderer = this.headerRenderer;
/* 403 */     this.headerRenderer = paramTableCellRenderer;
/* 404 */     firePropertyChange("headerRenderer", tableCellRenderer, paramTableCellRenderer);
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
/*     */   public TableCellRenderer getHeaderRenderer() {
/* 420 */     return this.headerRenderer;
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
/*     */   public void setCellRenderer(TableCellRenderer paramTableCellRenderer) {
/* 434 */     TableCellRenderer tableCellRenderer = this.cellRenderer;
/* 435 */     this.cellRenderer = paramTableCellRenderer;
/* 436 */     firePropertyChange("cellRenderer", tableCellRenderer, paramTableCellRenderer);
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
/*     */   public TableCellRenderer getCellRenderer() {
/* 455 */     return this.cellRenderer;
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
/*     */   public void setCellEditor(TableCellEditor paramTableCellEditor) {
/* 468 */     TableCellEditor tableCellEditor = this.cellEditor;
/* 469 */     this.cellEditor = paramTableCellEditor;
/* 470 */     firePropertyChange("cellEditor", tableCellEditor, paramTableCellEditor);
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
/*     */   public TableCellEditor getCellEditor() {
/* 486 */     return this.cellEditor;
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
/*     */   public void setWidth(int paramInt) {
/* 512 */     int i = this.width;
/* 513 */     this.width = Math.min(Math.max(paramInt, this.minWidth), this.maxWidth);
/* 514 */     firePropertyChange("width", i, this.width);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 525 */     return this.width;
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
/*     */   public void setPreferredWidth(int paramInt) {
/* 546 */     int i = this.preferredWidth;
/* 547 */     this.preferredWidth = Math.min(Math.max(paramInt, this.minWidth), this.maxWidth);
/* 548 */     firePropertyChange("preferredWidth", i, this.preferredWidth);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPreferredWidth() {
/* 559 */     return this.preferredWidth;
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
/*     */   public void setMinWidth(int paramInt) {
/* 585 */     int i = this.minWidth;
/* 586 */     this.minWidth = Math.max(Math.min(paramInt, this.maxWidth), 0);
/* 587 */     if (this.width < this.minWidth) {
/* 588 */       setWidth(this.minWidth);
/*     */     }
/* 590 */     if (this.preferredWidth < this.minWidth) {
/* 591 */       setPreferredWidth(this.minWidth);
/*     */     }
/* 593 */     firePropertyChange("minWidth", i, this.minWidth);
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
/*     */   public int getMinWidth() {
/* 606 */     return this.minWidth;
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
/*     */   public void setMaxWidth(int paramInt) {
/* 630 */     int i = this.maxWidth;
/* 631 */     this.maxWidth = Math.max(this.minWidth, paramInt);
/* 632 */     if (this.width > this.maxWidth) {
/* 633 */       setWidth(this.maxWidth);
/*     */     }
/* 635 */     if (this.preferredWidth > this.maxWidth) {
/* 636 */       setPreferredWidth(this.maxWidth);
/*     */     }
/* 638 */     firePropertyChange("maxWidth", i, this.maxWidth);
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
/*     */   public int getMaxWidth() {
/* 651 */     return this.maxWidth;
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
/*     */   public void setResizable(boolean paramBoolean) {
/* 664 */     boolean bool = this.isResizable;
/* 665 */     this.isResizable = paramBoolean;
/* 666 */     firePropertyChange("isResizable", bool, this.isResizable);
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
/*     */   public boolean getResizable() {
/* 679 */     return this.isResizable;
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
/*     */   public void sizeWidthToFit() {
/* 695 */     if (this.headerRenderer == null) {
/*     */       return;
/*     */     }
/* 698 */     Component component = this.headerRenderer.getTableCellRendererComponent(null, 
/* 699 */         getHeaderValue(), false, false, 0, 0);
/*     */     
/* 701 */     setMinWidth((component.getMinimumSize()).width);
/* 702 */     setMaxWidth((component.getMaximumSize()).width);
/* 703 */     setPreferredWidth((component.getPreferredSize()).width);
/*     */     
/* 705 */     setWidth(getPreferredWidth());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void disableResizedPosting() {
/* 716 */     this.resizedPostingDisableCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void enableResizedPosting() {
/* 727 */     this.resizedPostingDisableCount--;
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
/*     */   public synchronized void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 751 */     if (this.changeSupport == null) {
/* 752 */       this.changeSupport = new SwingPropertyChangeSupport(this);
/*     */     }
/* 754 */     this.changeSupport.addPropertyChangeListener(paramPropertyChangeListener);
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
/*     */   public synchronized void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) {
/* 768 */     if (this.changeSupport != null) {
/* 769 */       this.changeSupport.removePropertyChangeListener(paramPropertyChangeListener);
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
/*     */   public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
/* 782 */     if (this.changeSupport == null) {
/* 783 */       return new PropertyChangeListener[0];
/*     */     }
/* 785 */     return this.changeSupport.getPropertyChangeListeners();
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
/*     */   protected TableCellRenderer createDefaultHeaderRenderer() {
/* 804 */     DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer()
/*     */       {
/*     */         public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) {
/* 807 */           if (param1JTable != null) {
/* 808 */             JTableHeader jTableHeader = param1JTable.getTableHeader();
/* 809 */             if (jTableHeader != null) {
/* 810 */               setForeground(jTableHeader.getForeground());
/* 811 */               setBackground(jTableHeader.getBackground());
/* 812 */               setFont(jTableHeader.getFont());
/*     */             } 
/*     */           } 
/*     */           
/* 816 */           setText((param1Object == null) ? "" : param1Object.toString());
/* 817 */           setBorder(UIManager.getBorder("TableHeader.cellBorder"));
/* 818 */           return this;
/*     */         }
/*     */       };
/* 821 */     defaultTableCellRenderer.setHorizontalAlignment(0);
/* 822 */     return defaultTableCellRenderer;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\table\TableColumn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */