/*      */ package javax.swing.table;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.FocusListener;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.Transient;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.util.Locale;
/*      */ import javax.accessibility.Accessible;
/*      */ import javax.accessibility.AccessibleAction;
/*      */ import javax.accessibility.AccessibleComponent;
/*      */ import javax.accessibility.AccessibleContext;
/*      */ import javax.accessibility.AccessibleRole;
/*      */ import javax.accessibility.AccessibleSelection;
/*      */ import javax.accessibility.AccessibleState;
/*      */ import javax.accessibility.AccessibleStateSet;
/*      */ import javax.accessibility.AccessibleText;
/*      */ import javax.accessibility.AccessibleValue;
/*      */ import javax.swing.JComponent;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.ToolTipManager;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ListSelectionEvent;
/*      */ import javax.swing.event.TableColumnModelEvent;
/*      */ import javax.swing.event.TableColumnModelListener;
/*      */ import javax.swing.plaf.TableHeaderUI;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.swing.table.DefaultTableCellHeaderRenderer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class JTableHeader
/*      */   extends JComponent
/*      */   implements TableColumnModelListener, Accessible
/*      */ {
/*      */   private static final String uiClassID = "TableHeaderUI";
/*      */   protected JTable table;
/*      */   protected TableColumnModel columnModel;
/*      */   protected boolean reorderingAllowed;
/*      */   protected boolean resizingAllowed;
/*      */   protected boolean updateTableInRealTime;
/*      */   protected transient TableColumn resizingColumn;
/*      */   protected transient TableColumn draggedColumn;
/*      */   protected transient int draggedDistance;
/*      */   private TableCellRenderer defaultRenderer;
/*      */   
/*      */   public JTableHeader() {
/*  135 */     this((TableColumnModel)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JTableHeader(TableColumnModel paramTableColumnModel) {
/*  153 */     if (paramTableColumnModel == null)
/*  154 */       paramTableColumnModel = createDefaultColumnModel(); 
/*  155 */     setColumnModel(paramTableColumnModel);
/*      */ 
/*      */     
/*  158 */     initializeLocalVars();
/*      */ 
/*      */     
/*  161 */     updateUI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTable(JTable paramJTable) {
/*  176 */     JTable jTable = this.table;
/*  177 */     this.table = paramJTable;
/*  178 */     firePropertyChange("table", jTable, paramJTable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public JTable getTable() {
/*  186 */     return this.table;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReorderingAllowed(boolean paramBoolean) {
/*  200 */     boolean bool = this.reorderingAllowed;
/*  201 */     this.reorderingAllowed = paramBoolean;
/*  202 */     firePropertyChange("reorderingAllowed", bool, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getReorderingAllowed() {
/*  214 */     return this.reorderingAllowed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResizingAllowed(boolean paramBoolean) {
/*  228 */     boolean bool = this.resizingAllowed;
/*  229 */     this.resizingAllowed = paramBoolean;
/*  230 */     firePropertyChange("resizingAllowed", bool, paramBoolean);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getResizingAllowed() {
/*  242 */     return this.resizingAllowed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TableColumn getDraggedColumn() {
/*  254 */     return this.draggedColumn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDraggedDistance() {
/*  268 */     return this.draggedDistance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TableColumn getResizingColumn() {
/*  279 */     return this.resizingColumn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUpdateTableInRealTime(boolean paramBoolean) {
/*  295 */     this.updateTableInRealTime = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUpdateTableInRealTime() {
/*  312 */     return this.updateTableInRealTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultRenderer(TableCellRenderer paramTableCellRenderer) {
/*  322 */     this.defaultRenderer = paramTableCellRenderer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Transient
/*      */   public TableCellRenderer getDefaultRenderer() {
/*  333 */     return this.defaultRenderer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int columnAtPoint(Point paramPoint) {
/*  344 */     int i = paramPoint.x;
/*  345 */     if (!getComponentOrientation().isLeftToRight()) {
/*  346 */       i = getWidthInRightToLeft() - i - 1;
/*      */     }
/*  348 */     return getColumnModel().getColumnIndexAtX(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getHeaderRect(int paramInt) {
/*  360 */     Rectangle rectangle = new Rectangle();
/*  361 */     TableColumnModel tableColumnModel = getColumnModel();
/*      */     
/*  363 */     rectangle.height = getHeight();
/*      */     
/*  365 */     if (paramInt < 0) {
/*      */       
/*  367 */       if (!getComponentOrientation().isLeftToRight()) {
/*  368 */         rectangle.x = getWidthInRightToLeft();
/*      */       }
/*      */     }
/*  371 */     else if (paramInt >= tableColumnModel.getColumnCount()) {
/*  372 */       if (getComponentOrientation().isLeftToRight()) {
/*  373 */         rectangle.x = getWidth();
/*      */       }
/*      */     } else {
/*      */       
/*  377 */       for (byte b = 0; b < paramInt; b++) {
/*  378 */         rectangle.x += tableColumnModel.getColumn(b).getWidth();
/*      */       }
/*  380 */       if (!getComponentOrientation().isLeftToRight()) {
/*  381 */         rectangle.x = getWidthInRightToLeft() - rectangle.x - tableColumnModel.getColumn(paramInt).getWidth();
/*      */       }
/*      */       
/*  384 */       rectangle.width = tableColumnModel.getColumn(paramInt).getWidth();
/*      */     } 
/*  386 */     return rectangle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getToolTipText(MouseEvent paramMouseEvent) {
/*  397 */     String str = null;
/*  398 */     Point point = paramMouseEvent.getPoint();
/*      */     
/*      */     int i;
/*      */     
/*  402 */     if ((i = columnAtPoint(point)) != -1) {
/*  403 */       TableColumn tableColumn = this.columnModel.getColumn(i);
/*  404 */       TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/*  405 */       if (tableCellRenderer == null) {
/*  406 */         tableCellRenderer = this.defaultRenderer;
/*      */       }
/*  408 */       Component component = tableCellRenderer.getTableCellRendererComponent(
/*  409 */           getTable(), tableColumn.getHeaderValue(), false, false, -1, i);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  414 */       if (component instanceof JComponent) {
/*      */ 
/*      */         
/*  417 */         Rectangle rectangle = getHeaderRect(i);
/*      */         
/*  419 */         point.translate(-rectangle.x, -rectangle.y);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  424 */         MouseEvent mouseEvent = new MouseEvent(component, paramMouseEvent.getID(), paramMouseEvent.getWhen(), paramMouseEvent.getModifiers(), point.x, point.y, paramMouseEvent.getXOnScreen(), paramMouseEvent.getYOnScreen(), paramMouseEvent.getClickCount(), paramMouseEvent.isPopupTrigger(), 0);
/*  425 */         AWTAccessor.MouseEventAccessor mouseEventAccessor = AWTAccessor.getMouseEventAccessor();
/*  426 */         mouseEventAccessor.setCausedByTouchEvent(mouseEvent, mouseEventAccessor
/*  427 */             .isCausedByTouchEvent(paramMouseEvent));
/*      */         
/*  429 */         str = ((JComponent)component).getToolTipText(mouseEvent);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  434 */     if (str == null) {
/*  435 */       str = getToolTipText();
/*      */     }
/*  437 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TableHeaderUI getUI() {
/*  450 */     return (TableHeaderUI)this.ui;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUI(TableHeaderUI paramTableHeaderUI) {
/*  460 */     if (this.ui != paramTableHeaderUI) {
/*  461 */       setUI(paramTableHeaderUI);
/*  462 */       repaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateUI() {
/*  475 */     setUI((TableHeaderUI)UIManager.getUI(this));
/*      */     
/*  477 */     TableCellRenderer tableCellRenderer = getDefaultRenderer();
/*  478 */     if (tableCellRenderer instanceof Component) {
/*  479 */       SwingUtilities.updateComponentTreeUI((Component)tableCellRenderer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUIClassID() {
/*  494 */     return "TableHeaderUI";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setColumnModel(TableColumnModel paramTableColumnModel) {
/*  516 */     if (paramTableColumnModel == null) {
/*  517 */       throw new IllegalArgumentException("Cannot set a null ColumnModel");
/*      */     }
/*  519 */     TableColumnModel tableColumnModel = this.columnModel;
/*  520 */     if (paramTableColumnModel != tableColumnModel) {
/*  521 */       if (tableColumnModel != null) {
/*  522 */         tableColumnModel.removeColumnModelListener(this);
/*      */       }
/*  524 */       this.columnModel = paramTableColumnModel;
/*  525 */       paramTableColumnModel.addColumnModelListener(this);
/*      */       
/*  527 */       firePropertyChange("columnModel", tableColumnModel, paramTableColumnModel);
/*  528 */       resizeAndRepaint();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TableColumnModel getColumnModel() {
/*  540 */     return this.columnModel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void columnAdded(TableColumnModelEvent paramTableColumnModelEvent) {
/*  556 */     resizeAndRepaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void columnRemoved(TableColumnModelEvent paramTableColumnModelEvent) {
/*  568 */     resizeAndRepaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void columnMoved(TableColumnModelEvent paramTableColumnModelEvent) {
/*  580 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void columnMarginChanged(ChangeEvent paramChangeEvent) {
/*  592 */     resizeAndRepaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void columnSelectionChanged(ListSelectionEvent paramListSelectionEvent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TableColumnModel createDefaultColumnModel() {
/*  623 */     return new DefaultTableColumnModel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TableCellRenderer createDefaultRenderer() {
/*  634 */     return new DefaultTableCellHeaderRenderer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initializeLocalVars() {
/*  643 */     setOpaque(true);
/*  644 */     this.table = null;
/*  645 */     this.reorderingAllowed = true;
/*  646 */     this.resizingAllowed = true;
/*  647 */     this.draggedColumn = null;
/*  648 */     this.draggedDistance = 0;
/*  649 */     this.resizingColumn = null;
/*  650 */     this.updateTableInRealTime = true;
/*      */ 
/*      */ 
/*      */     
/*  654 */     ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
/*  655 */     toolTipManager.registerComponent(this);
/*  656 */     setDefaultRenderer(createDefaultRenderer());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resizeAndRepaint() {
/*  664 */     revalidate();
/*  665 */     repaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDraggedColumn(TableColumn paramTableColumn) {
/*  678 */     this.draggedColumn = paramTableColumn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDraggedDistance(int paramInt) {
/*  686 */     this.draggedDistance = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResizingColumn(TableColumn paramTableColumn) {
/*  699 */     this.resizingColumn = paramTableColumn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  708 */     paramObjectOutputStream.defaultWriteObject();
/*  709 */     if (this.ui != null && getUIClassID().equals("TableHeaderUI")) {
/*  710 */       this.ui.installUI(this);
/*      */     }
/*      */   }
/*      */   
/*      */   private int getWidthInRightToLeft() {
/*  715 */     if (this.table != null && this.table
/*  716 */       .getAutoResizeMode() != 0) {
/*  717 */       return this.table.getWidth();
/*      */     }
/*  719 */     return getWidth();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String paramString() {
/*  735 */     String str1 = this.reorderingAllowed ? "true" : "false";
/*      */     
/*  737 */     String str2 = this.resizingAllowed ? "true" : "false";
/*      */     
/*  739 */     String str3 = this.updateTableInRealTime ? "true" : "false";
/*      */ 
/*      */     
/*  742 */     return super.paramString() + ",draggedDistance=" + this.draggedDistance + ",reorderingAllowed=" + str1 + ",resizingAllowed=" + str2 + ",updateTableInRealTime=" + str3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AccessibleContext getAccessibleContext() {
/*  763 */     if (this.accessibleContext == null) {
/*  764 */       this.accessibleContext = new AccessibleJTableHeader();
/*      */     }
/*  766 */     return this.accessibleContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class AccessibleJTableHeader
/*      */     extends JComponent.AccessibleJComponent
/*      */   {
/*      */     public AccessibleRole getAccessibleRole() {
/*  798 */       return AccessibleRole.PANEL;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleAt(Point param1Point) {
/*      */       int i;
/*  814 */       if ((i = JTableHeader.this.columnAtPoint(param1Point)) != -1) {
/*  815 */         TableColumn tableColumn = JTableHeader.this.columnModel.getColumn(i);
/*  816 */         TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/*  817 */         if (tableCellRenderer == null) {
/*  818 */           if (JTableHeader.this.defaultRenderer != null) {
/*  819 */             tableCellRenderer = JTableHeader.this.defaultRenderer;
/*      */           } else {
/*  821 */             return null;
/*      */           } 
/*      */         }
/*  824 */         Component component = tableCellRenderer.getTableCellRendererComponent(JTableHeader.this
/*  825 */             .getTable(), tableColumn
/*  826 */             .getHeaderValue(), false, false, -1, i);
/*      */ 
/*      */         
/*  829 */         return new AccessibleJTableHeaderEntry(i, JTableHeader.this, JTableHeader.this.table);
/*      */       } 
/*  831 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getAccessibleChildrenCount() {
/*  843 */       return JTableHeader.this.columnModel.getColumnCount();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Accessible getAccessibleChild(int param1Int) {
/*  853 */       if (param1Int < 0 || param1Int >= getAccessibleChildrenCount()) {
/*  854 */         return null;
/*      */       }
/*  856 */       TableColumn tableColumn = JTableHeader.this.columnModel.getColumn(param1Int);
/*      */       
/*  858 */       TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/*  859 */       if (tableCellRenderer == null) {
/*  860 */         if (JTableHeader.this.defaultRenderer != null) {
/*  861 */           tableCellRenderer = JTableHeader.this.defaultRenderer;
/*      */         } else {
/*  863 */           return null;
/*      */         } 
/*      */       }
/*  866 */       Component component = tableCellRenderer.getTableCellRendererComponent(JTableHeader.this
/*  867 */           .getTable(), tableColumn
/*  868 */           .getHeaderValue(), false, false, -1, param1Int);
/*      */ 
/*      */       
/*  871 */       return new AccessibleJTableHeaderEntry(param1Int, JTableHeader.this, JTableHeader.this.table);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected class AccessibleJTableHeaderEntry
/*      */       extends AccessibleContext
/*      */       implements Accessible, AccessibleComponent
/*      */     {
/*      */       private JTableHeader parent;
/*      */ 
/*      */       
/*      */       private int column;
/*      */ 
/*      */       
/*      */       private JTable table;
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleJTableHeaderEntry(int param2Int, JTableHeader param2JTableHeader, JTable param2JTable) {
/*  891 */         this.parent = param2JTableHeader;
/*  892 */         this.column = param2Int;
/*  893 */         this.table = param2JTable;
/*  894 */         setAccessibleParent(this.parent);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleContext getAccessibleContext() {
/*  906 */         return this;
/*      */       }
/*      */       
/*      */       private AccessibleContext getCurrentAccessibleContext() {
/*  910 */         TableColumnModel tableColumnModel = this.table.getColumnModel();
/*  911 */         if (tableColumnModel != null) {
/*      */ 
/*      */           
/*  914 */           if (this.column < 0 || this.column >= tableColumnModel.getColumnCount()) {
/*  915 */             return null;
/*      */           }
/*  917 */           TableColumn tableColumn = tableColumnModel.getColumn(this.column);
/*  918 */           TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/*  919 */           if (tableCellRenderer == null) {
/*  920 */             if (JTableHeader.this.defaultRenderer != null) {
/*  921 */               tableCellRenderer = JTableHeader.this.defaultRenderer;
/*      */             } else {
/*  923 */               return null;
/*      */             } 
/*      */           }
/*  926 */           Component component = tableCellRenderer.getTableCellRendererComponent(JTableHeader.this
/*  927 */               .getTable(), tableColumn
/*  928 */               .getHeaderValue(), false, false, -1, this.column);
/*      */           
/*  930 */           if (component instanceof Accessible) {
/*  931 */             return ((Accessible)component).getAccessibleContext();
/*      */           }
/*      */         } 
/*  934 */         return null;
/*      */       }
/*      */       
/*      */       private Component getCurrentComponent() {
/*  938 */         TableColumnModel tableColumnModel = this.table.getColumnModel();
/*  939 */         if (tableColumnModel != null) {
/*      */ 
/*      */           
/*  942 */           if (this.column < 0 || this.column >= tableColumnModel.getColumnCount()) {
/*  943 */             return null;
/*      */           }
/*  945 */           TableColumn tableColumn = tableColumnModel.getColumn(this.column);
/*  946 */           TableCellRenderer tableCellRenderer = tableColumn.getHeaderRenderer();
/*  947 */           if (tableCellRenderer == null) {
/*  948 */             if (JTableHeader.this.defaultRenderer != null) {
/*  949 */               tableCellRenderer = JTableHeader.this.defaultRenderer;
/*      */             } else {
/*  951 */               return null;
/*      */             } 
/*      */           }
/*  954 */           return tableCellRenderer.getTableCellRendererComponent(JTableHeader.this
/*  955 */               .getTable(), tableColumn
/*  956 */               .getHeaderValue(), false, false, -1, this.column);
/*      */         } 
/*      */         
/*  959 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getAccessibleName() {
/*  966 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/*  967 */         if (accessibleContext != null) {
/*  968 */           String str1 = accessibleContext.getAccessibleName();
/*  969 */           if (str1 != null && str1 != "")
/*      */           {
/*  971 */             return str1;
/*      */           }
/*      */         } 
/*  974 */         if (this.accessibleName != null && this.accessibleName != "") {
/*  975 */           return this.accessibleName;
/*      */         }
/*      */         
/*  978 */         String str = (String)JTableHeader.this.getClientProperty("AccessibleName");
/*  979 */         if (str != null) {
/*  980 */           return str;
/*      */         }
/*  982 */         return this.table.getColumnName(this.column);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setAccessibleName(String param2String) {
/*  988 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/*  989 */         if (accessibleContext != null) {
/*  990 */           accessibleContext.setAccessibleName(param2String);
/*      */         } else {
/*  992 */           super.setAccessibleName(param2String);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public String getAccessibleDescription() {
/* 1000 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1001 */         if (accessibleContext != null) {
/* 1002 */           return accessibleContext.getAccessibleDescription();
/*      */         }
/* 1004 */         return super.getAccessibleDescription();
/*      */       }
/*      */ 
/*      */       
/*      */       public void setAccessibleDescription(String param2String) {
/* 1009 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1010 */         if (accessibleContext != null) {
/* 1011 */           accessibleContext.setAccessibleDescription(param2String);
/*      */         } else {
/* 1013 */           super.setAccessibleDescription(param2String);
/*      */         } 
/*      */       }
/*      */       
/*      */       public AccessibleRole getAccessibleRole() {
/* 1018 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1019 */         if (accessibleContext != null) {
/* 1020 */           return accessibleContext.getAccessibleRole();
/*      */         }
/* 1022 */         return AccessibleRole.COLUMN_HEADER;
/*      */       }
/*      */ 
/*      */       
/*      */       public AccessibleStateSet getAccessibleStateSet() {
/* 1027 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1028 */         if (accessibleContext != null) {
/* 1029 */           AccessibleStateSet accessibleStateSet = accessibleContext.getAccessibleStateSet();
/* 1030 */           if (isShowing()) {
/* 1031 */             accessibleStateSet.add(AccessibleState.SHOWING);
/*      */           }
/* 1033 */           return accessibleStateSet;
/*      */         } 
/* 1035 */         return new AccessibleStateSet();
/*      */       }
/*      */ 
/*      */       
/*      */       public int getAccessibleIndexInParent() {
/* 1040 */         return this.column;
/*      */       }
/*      */       
/*      */       public int getAccessibleChildrenCount() {
/* 1044 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1045 */         if (accessibleContext != null) {
/* 1046 */           return accessibleContext.getAccessibleChildrenCount();
/*      */         }
/* 1048 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*      */       public Accessible getAccessibleChild(int param2Int) {
/* 1053 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1054 */         if (accessibleContext != null) {
/* 1055 */           Accessible accessible = accessibleContext.getAccessibleChild(param2Int);
/* 1056 */           accessibleContext.setAccessibleParent(this);
/* 1057 */           return accessible;
/*      */         } 
/* 1059 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public Locale getLocale() {
/* 1064 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1065 */         if (accessibleContext != null) {
/* 1066 */           return accessibleContext.getLocale();
/*      */         }
/* 1068 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public void addPropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 1073 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1074 */         if (accessibleContext != null) {
/* 1075 */           accessibleContext.addPropertyChangeListener(param2PropertyChangeListener);
/*      */         } else {
/* 1077 */           super.addPropertyChangeListener(param2PropertyChangeListener);
/*      */         } 
/*      */       }
/*      */       
/*      */       public void removePropertyChangeListener(PropertyChangeListener param2PropertyChangeListener) {
/* 1082 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1083 */         if (accessibleContext != null) {
/* 1084 */           accessibleContext.removePropertyChangeListener(param2PropertyChangeListener);
/*      */         } else {
/* 1086 */           super.removePropertyChangeListener(param2PropertyChangeListener);
/*      */         } 
/*      */       }
/*      */       
/*      */       public AccessibleAction getAccessibleAction() {
/* 1091 */         return getCurrentAccessibleContext().getAccessibleAction();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public AccessibleComponent getAccessibleComponent() {
/* 1103 */         return this;
/*      */       }
/*      */       
/*      */       public AccessibleSelection getAccessibleSelection() {
/* 1107 */         return getCurrentAccessibleContext().getAccessibleSelection();
/*      */       }
/*      */       
/*      */       public AccessibleText getAccessibleText() {
/* 1111 */         return getCurrentAccessibleContext().getAccessibleText();
/*      */       }
/*      */       
/*      */       public AccessibleValue getAccessibleValue() {
/* 1115 */         return getCurrentAccessibleContext().getAccessibleValue();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Color getBackground() {
/* 1122 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1123 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1124 */           return ((AccessibleComponent)accessibleContext).getBackground();
/*      */         }
/* 1126 */         Component component = getCurrentComponent();
/* 1127 */         if (component != null) {
/* 1128 */           return component.getBackground();
/*      */         }
/* 1130 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setBackground(Color param2Color) {
/* 1136 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1137 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1138 */           ((AccessibleComponent)accessibleContext).setBackground(param2Color);
/*      */         } else {
/* 1140 */           Component component = getCurrentComponent();
/* 1141 */           if (component != null) {
/* 1142 */             component.setBackground(param2Color);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Color getForeground() {
/* 1148 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1149 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1150 */           return ((AccessibleComponent)accessibleContext).getForeground();
/*      */         }
/* 1152 */         Component component = getCurrentComponent();
/* 1153 */         if (component != null) {
/* 1154 */           return component.getForeground();
/*      */         }
/* 1156 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setForeground(Color param2Color) {
/* 1162 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1163 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1164 */           ((AccessibleComponent)accessibleContext).setForeground(param2Color);
/*      */         } else {
/* 1166 */           Component component = getCurrentComponent();
/* 1167 */           if (component != null) {
/* 1168 */             component.setForeground(param2Color);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Cursor getCursor() {
/* 1174 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1175 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1176 */           return ((AccessibleComponent)accessibleContext).getCursor();
/*      */         }
/* 1178 */         Component component = getCurrentComponent();
/* 1179 */         if (component != null) {
/* 1180 */           return component.getCursor();
/*      */         }
/* 1182 */         Accessible accessible = getAccessibleParent();
/* 1183 */         if (accessible instanceof AccessibleComponent) {
/* 1184 */           return ((AccessibleComponent)accessible).getCursor();
/*      */         }
/* 1186 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setCursor(Cursor param2Cursor) {
/* 1193 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1194 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1195 */           ((AccessibleComponent)accessibleContext).setCursor(param2Cursor);
/*      */         } else {
/* 1197 */           Component component = getCurrentComponent();
/* 1198 */           if (component != null) {
/* 1199 */             component.setCursor(param2Cursor);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Font getFont() {
/* 1205 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1206 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1207 */           return ((AccessibleComponent)accessibleContext).getFont();
/*      */         }
/* 1209 */         Component component = getCurrentComponent();
/* 1210 */         if (component != null) {
/* 1211 */           return component.getFont();
/*      */         }
/* 1213 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setFont(Font param2Font) {
/* 1219 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1220 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1221 */           ((AccessibleComponent)accessibleContext).setFont(param2Font);
/*      */         } else {
/* 1223 */           Component component = getCurrentComponent();
/* 1224 */           if (component != null) {
/* 1225 */             component.setFont(param2Font);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public FontMetrics getFontMetrics(Font param2Font) {
/* 1231 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1232 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1233 */           return ((AccessibleComponent)accessibleContext).getFontMetrics(param2Font);
/*      */         }
/* 1235 */         Component component = getCurrentComponent();
/* 1236 */         if (component != null) {
/* 1237 */           return component.getFontMetrics(param2Font);
/*      */         }
/* 1239 */         return null;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean isEnabled() {
/* 1245 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1246 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1247 */           return ((AccessibleComponent)accessibleContext).isEnabled();
/*      */         }
/* 1249 */         Component component = getCurrentComponent();
/* 1250 */         if (component != null) {
/* 1251 */           return component.isEnabled();
/*      */         }
/* 1253 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setEnabled(boolean param2Boolean) {
/* 1259 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1260 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1261 */           ((AccessibleComponent)accessibleContext).setEnabled(param2Boolean);
/*      */         } else {
/* 1263 */           Component component = getCurrentComponent();
/* 1264 */           if (component != null) {
/* 1265 */             component.setEnabled(param2Boolean);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public boolean isVisible() {
/* 1271 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1272 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1273 */           return ((AccessibleComponent)accessibleContext).isVisible();
/*      */         }
/* 1275 */         Component component = getCurrentComponent();
/* 1276 */         if (component != null) {
/* 1277 */           return component.isVisible();
/*      */         }
/* 1279 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void setVisible(boolean param2Boolean) {
/* 1285 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1286 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1287 */           ((AccessibleComponent)accessibleContext).setVisible(param2Boolean);
/*      */         } else {
/* 1289 */           Component component = getCurrentComponent();
/* 1290 */           if (component != null) {
/* 1291 */             component.setVisible(param2Boolean);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public boolean isShowing() {
/* 1297 */         if (isVisible() && JTableHeader.this.isShowing()) {
/* 1298 */           return true;
/*      */         }
/* 1300 */         return false;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean contains(Point param2Point) {
/* 1305 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1306 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1307 */           Rectangle rectangle = ((AccessibleComponent)accessibleContext).getBounds();
/* 1308 */           return rectangle.contains(param2Point);
/*      */         } 
/* 1310 */         Component component = getCurrentComponent();
/* 1311 */         if (component != null) {
/* 1312 */           Rectangle rectangle = component.getBounds();
/* 1313 */           return rectangle.contains(param2Point);
/*      */         } 
/* 1315 */         return getBounds().contains(param2Point);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public Point getLocationOnScreen() {
/* 1321 */         if (this.parent != null) {
/* 1322 */           Point point1 = this.parent.getLocationOnScreen();
/* 1323 */           Point point2 = getLocation();
/* 1324 */           point2.translate(point1.x, point1.y);
/* 1325 */           return point2;
/*      */         } 
/* 1327 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public Point getLocation() {
/* 1332 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1333 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1334 */           Rectangle rectangle = ((AccessibleComponent)accessibleContext).getBounds();
/* 1335 */           return rectangle.getLocation();
/*      */         } 
/* 1337 */         Component component = getCurrentComponent();
/* 1338 */         if (component != null) {
/* 1339 */           Rectangle rectangle = component.getBounds();
/* 1340 */           return rectangle.getLocation();
/*      */         } 
/* 1342 */         return getBounds().getLocation();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setLocation(Point param2Point) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public Rectangle getBounds() {
/* 1354 */         Rectangle rectangle = this.table.getCellRect(-1, this.column, false);
/* 1355 */         rectangle.y = 0;
/* 1356 */         return rectangle;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setBounds(Rectangle param2Rectangle) {
/* 1374 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1375 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1376 */           ((AccessibleComponent)accessibleContext).setBounds(param2Rectangle);
/*      */         } else {
/* 1378 */           Component component = getCurrentComponent();
/* 1379 */           if (component != null) {
/* 1380 */             component.setBounds(param2Rectangle);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Dimension getSize() {
/* 1386 */         return getBounds().getSize();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void setSize(Dimension param2Dimension) {
/* 1403 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1404 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1405 */           ((AccessibleComponent)accessibleContext).setSize(param2Dimension);
/*      */         } else {
/* 1407 */           Component component = getCurrentComponent();
/* 1408 */           if (component != null) {
/* 1409 */             component.setSize(param2Dimension);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public Accessible getAccessibleAt(Point param2Point) {
/* 1415 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1416 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1417 */           return ((AccessibleComponent)accessibleContext).getAccessibleAt(param2Point);
/*      */         }
/* 1419 */         return null;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean isFocusTraversable() {
/* 1424 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1425 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1426 */           return ((AccessibleComponent)accessibleContext).isFocusTraversable();
/*      */         }
/* 1428 */         Component component = getCurrentComponent();
/* 1429 */         if (component != null) {
/* 1430 */           return component.isFocusTraversable();
/*      */         }
/* 1432 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public void requestFocus() {
/* 1438 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1439 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1440 */           ((AccessibleComponent)accessibleContext).requestFocus();
/*      */         } else {
/* 1442 */           Component component = getCurrentComponent();
/* 1443 */           if (component != null) {
/* 1444 */             component.requestFocus();
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public void addFocusListener(FocusListener param2FocusListener) {
/* 1450 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1451 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1452 */           ((AccessibleComponent)accessibleContext).addFocusListener(param2FocusListener);
/*      */         } else {
/* 1454 */           Component component = getCurrentComponent();
/* 1455 */           if (component != null) {
/* 1456 */             component.addFocusListener(param2FocusListener);
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*      */       public void removeFocusListener(FocusListener param2FocusListener) {
/* 1462 */         AccessibleContext accessibleContext = getCurrentAccessibleContext();
/* 1463 */         if (accessibleContext instanceof AccessibleComponent) {
/* 1464 */           ((AccessibleComponent)accessibleContext).removeFocusListener(param2FocusListener);
/*      */         } else {
/* 1466 */           Component component = getCurrentComponent();
/* 1467 */           if (component != null)
/* 1468 */             component.removeFocusListener(param2FocusListener); 
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\table\JTableHeader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */