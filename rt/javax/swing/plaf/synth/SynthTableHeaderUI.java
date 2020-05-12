/*     */ package javax.swing.plaf.synth;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.SortOrder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.basic.BasicTableHeaderUI;
/*     */ import javax.swing.table.JTableHeader;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import sun.swing.table.DefaultTableCellHeaderRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynthTableHeaderUI
/*     */   extends BasicTableHeaderUI
/*     */   implements PropertyChangeListener, SynthUI
/*     */ {
/*  52 */   private TableCellRenderer prevRenderer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SynthStyle style;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  63 */     return new SynthTableHeaderUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installDefaults() {
/*  71 */     this.prevRenderer = this.header.getDefaultRenderer();
/*  72 */     if (this.prevRenderer instanceof javax.swing.plaf.UIResource) {
/*  73 */       this.header.setDefaultRenderer(new HeaderRenderer());
/*     */     }
/*  75 */     updateStyle(this.header);
/*     */   }
/*     */   
/*     */   private void updateStyle(JTableHeader paramJTableHeader) {
/*  79 */     SynthContext synthContext = getContext(paramJTableHeader, 1);
/*  80 */     SynthStyle synthStyle = this.style;
/*  81 */     this.style = SynthLookAndFeel.updateStyle(synthContext, this);
/*  82 */     if (this.style != synthStyle && 
/*  83 */       synthStyle != null) {
/*  84 */       uninstallKeyboardActions();
/*  85 */       installKeyboardActions();
/*     */     } 
/*     */     
/*  88 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void installListeners() {
/*  96 */     super.installListeners();
/*  97 */     this.header.addPropertyChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallDefaults() {
/* 105 */     if (this.header.getDefaultRenderer() instanceof HeaderRenderer) {
/* 106 */       this.header.setDefaultRenderer(this.prevRenderer);
/*     */     }
/*     */     
/* 109 */     SynthContext synthContext = getContext(this.header, 1);
/*     */     
/* 111 */     this.style.uninstallDefaults(synthContext);
/* 112 */     synthContext.dispose();
/* 113 */     this.style = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void uninstallListeners() {
/* 121 */     this.header.removePropertyChangeListener(this);
/* 122 */     super.uninstallListeners();
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
/*     */   public void update(Graphics paramGraphics, JComponent paramJComponent) {
/* 139 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 141 */     SynthLookAndFeel.update(synthContext, paramGraphics);
/* 142 */     synthContext.getPainter().paintTableHeaderBackground(synthContext, paramGraphics, 0, 0, paramJComponent
/* 143 */         .getWidth(), paramJComponent.getHeight());
/* 144 */     paint(synthContext, paramGraphics);
/* 145 */     synthContext.dispose();
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
/*     */   public void paint(Graphics paramGraphics, JComponent paramJComponent) {
/* 159 */     SynthContext synthContext = getContext(paramJComponent);
/*     */     
/* 161 */     paint(synthContext, paramGraphics);
/* 162 */     synthContext.dispose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void paint(SynthContext paramSynthContext, Graphics paramGraphics) {
/* 173 */     super.paint(paramGraphics, paramSynthContext.getComponent());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintBorder(SynthContext paramSynthContext, Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 182 */     paramSynthContext.getPainter().paintTableHeaderBorder(paramSynthContext, paramGraphics, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SynthContext getContext(JComponent paramJComponent) {
/* 192 */     return getContext(paramJComponent, SynthLookAndFeel.getComponentState(paramJComponent));
/*     */   }
/*     */   
/*     */   private SynthContext getContext(JComponent paramJComponent, int paramInt) {
/* 196 */     return SynthContext.getContext(paramJComponent, this.style, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rolloverColumnUpdated(int paramInt1, int paramInt2) {
/* 204 */     this.header.repaint(this.header.getHeaderRect(paramInt1));
/* 205 */     this.header.repaint(this.header.getHeaderRect(paramInt2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
/* 213 */     if (SynthLookAndFeel.shouldUpdateStyle(paramPropertyChangeEvent))
/* 214 */       updateStyle((JTableHeader)paramPropertyChangeEvent.getSource()); 
/*     */   }
/*     */   
/*     */   private class HeaderRenderer
/*     */     extends DefaultTableCellHeaderRenderer {
/*     */     HeaderRenderer() {
/* 220 */       setHorizontalAlignment(10);
/* 221 */       setName("TableHeader.renderer");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Component getTableCellRendererComponent(JTable param1JTable, Object param1Object, boolean param1Boolean1, boolean param1Boolean2, int param1Int1, int param1Int2) {
/*     */       // Byte code:
/*     */       //   0: iload #6
/*     */       //   2: aload_0
/*     */       //   3: getfield this$0 : Ljavax/swing/plaf/synth/SynthTableHeaderUI;
/*     */       //   6: invokestatic access$000 : (Ljavax/swing/plaf/synth/SynthTableHeaderUI;)I
/*     */       //   9: if_icmpne -> 16
/*     */       //   12: iconst_1
/*     */       //   13: goto -> 17
/*     */       //   16: iconst_0
/*     */       //   17: istore #7
/*     */       //   19: iload_3
/*     */       //   20: ifne -> 33
/*     */       //   23: iload #7
/*     */       //   25: ifne -> 33
/*     */       //   28: iload #4
/*     */       //   30: ifeq -> 60
/*     */       //   33: aload_0
/*     */       //   34: invokevirtual getUI : ()Ljavax/swing/plaf/LabelUI;
/*     */       //   37: ldc javax/swing/plaf/synth/SynthLabelUI
/*     */       //   39: invokestatic getUIOfType : (Ljavax/swing/plaf/ComponentUI;Ljava/lang/Class;)Ljava/lang/Object;
/*     */       //   42: checkcast javax/swing/plaf/synth/SynthLabelUI
/*     */       //   45: iload_3
/*     */       //   46: iload #4
/*     */       //   48: aload_1
/*     */       //   49: invokevirtual isEnabled : ()Z
/*     */       //   52: iload #7
/*     */       //   54: invokestatic setSelectedUI : (Ljavax/swing/plaf/ComponentUI;ZZZZ)V
/*     */       //   57: goto -> 63
/*     */       //   60: invokestatic resetSelectedUI : ()V
/*     */       //   63: aload_1
/*     */       //   64: ifnonnull -> 71
/*     */       //   67: aconst_null
/*     */       //   68: goto -> 75
/*     */       //   71: aload_1
/*     */       //   72: invokevirtual getRowSorter : ()Ljavax/swing/RowSorter;
/*     */       //   75: astore #8
/*     */       //   77: aload #8
/*     */       //   79: ifnonnull -> 86
/*     */       //   82: aconst_null
/*     */       //   83: goto -> 91
/*     */       //   86: aload #8
/*     */       //   88: invokevirtual getSortKeys : ()Ljava/util/List;
/*     */       //   91: astore #9
/*     */       //   93: aload #9
/*     */       //   95: ifnull -> 223
/*     */       //   98: aload #9
/*     */       //   100: invokeinterface size : ()I
/*     */       //   105: ifle -> 223
/*     */       //   108: aload #9
/*     */       //   110: iconst_0
/*     */       //   111: invokeinterface get : (I)Ljava/lang/Object;
/*     */       //   116: checkcast javax/swing/RowSorter$SortKey
/*     */       //   119: invokevirtual getColumn : ()I
/*     */       //   122: aload_1
/*     */       //   123: iload #6
/*     */       //   125: invokevirtual convertColumnIndexToModel : (I)I
/*     */       //   128: if_icmpne -> 223
/*     */       //   131: getstatic javax/swing/plaf/synth/SynthTableHeaderUI$1.$SwitchMap$javax$swing$SortOrder : [I
/*     */       //   134: aload #9
/*     */       //   136: iconst_0
/*     */       //   137: invokeinterface get : (I)Ljava/lang/Object;
/*     */       //   142: checkcast javax/swing/RowSorter$SortKey
/*     */       //   145: invokevirtual getSortOrder : ()Ljavax/swing/SortOrder;
/*     */       //   148: invokevirtual ordinal : ()I
/*     */       //   151: iaload
/*     */       //   152: tableswitch default -> 213, 1 -> 180, 2 -> 191, 3 -> 202
/*     */       //   180: aload_0
/*     */       //   181: ldc 'Table.sortOrder'
/*     */       //   183: ldc 'ASCENDING'
/*     */       //   185: invokevirtual putClientProperty : (Ljava/lang/Object;Ljava/lang/Object;)V
/*     */       //   188: goto -> 231
/*     */       //   191: aload_0
/*     */       //   192: ldc 'Table.sortOrder'
/*     */       //   194: ldc 'DESCENDING'
/*     */       //   196: invokevirtual putClientProperty : (Ljava/lang/Object;Ljava/lang/Object;)V
/*     */       //   199: goto -> 231
/*     */       //   202: aload_0
/*     */       //   203: ldc 'Table.sortOrder'
/*     */       //   205: ldc 'UNSORTED'
/*     */       //   207: invokevirtual putClientProperty : (Ljava/lang/Object;Ljava/lang/Object;)V
/*     */       //   210: goto -> 231
/*     */       //   213: new java/lang/AssertionError
/*     */       //   216: dup
/*     */       //   217: ldc 'Cannot happen'
/*     */       //   219: invokespecial <init> : (Ljava/lang/Object;)V
/*     */       //   222: athrow
/*     */       //   223: aload_0
/*     */       //   224: ldc 'Table.sortOrder'
/*     */       //   226: ldc 'UNSORTED'
/*     */       //   228: invokevirtual putClientProperty : (Ljava/lang/Object;Ljava/lang/Object;)V
/*     */       //   231: aload_0
/*     */       //   232: aload_1
/*     */       //   233: aload_2
/*     */       //   234: iload_3
/*     */       //   235: iload #4
/*     */       //   237: iload #5
/*     */       //   239: iload #6
/*     */       //   241: invokespecial getTableCellRendererComponent : (Ljavax/swing/JTable;Ljava/lang/Object;ZZII)Ljava/awt/Component;
/*     */       //   244: pop
/*     */       //   245: aload_0
/*     */       //   246: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #230	-> 0
/*     */       //   #231	-> 19
/*     */       //   #232	-> 33
/*     */       //   #233	-> 34
/*     */       //   #234	-> 49
/*     */       //   #232	-> 54
/*     */       //   #237	-> 60
/*     */       //   #242	-> 63
/*     */       //   #243	-> 77
/*     */       //   #244	-> 93
/*     */       //   #245	-> 125
/*     */       //   #246	-> 131
/*     */       //   #248	-> 180
/*     */       //   #249	-> 188
/*     */       //   #251	-> 191
/*     */       //   #252	-> 199
/*     */       //   #254	-> 202
/*     */       //   #255	-> 210
/*     */       //   #257	-> 213
/*     */       //   #260	-> 223
/*     */       //   #263	-> 231
/*     */       //   #266	-> 245
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setBorder(Border param1Border) {
/* 271 */       if (param1Border instanceof SynthBorder)
/* 272 */         super.setBorder(param1Border); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\synth\SynthTableHeaderUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */