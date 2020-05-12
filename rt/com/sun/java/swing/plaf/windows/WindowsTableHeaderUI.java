/*     */ package com.sun.java.swing.plaf.windows;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.SortOrder;
/*     */ import javax.swing.border.Border;
/*     */ import javax.swing.plaf.ComponentUI;
/*     */ import javax.swing.plaf.UIResource;
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
/*     */ public class WindowsTableHeaderUI
/*     */   extends BasicTableHeaderUI
/*     */ {
/*     */   private TableCellRenderer originalHeaderRenderer;
/*     */   
/*     */   public static ComponentUI createUI(JComponent paramJComponent) {
/*  45 */     return new WindowsTableHeaderUI();
/*     */   }
/*     */   
/*     */   public void installUI(JComponent paramJComponent) {
/*  49 */     super.installUI(paramJComponent);
/*     */     
/*  51 */     if (XPStyle.getXP() != null) {
/*  52 */       this.originalHeaderRenderer = this.header.getDefaultRenderer();
/*  53 */       if (this.originalHeaderRenderer instanceof UIResource) {
/*  54 */         this.header.setDefaultRenderer(new XPDefaultRenderer());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void uninstallUI(JComponent paramJComponent) {
/*  60 */     if (this.header.getDefaultRenderer() instanceof XPDefaultRenderer) {
/*  61 */       this.header.setDefaultRenderer(this.originalHeaderRenderer);
/*     */     }
/*  63 */     super.uninstallUI(paramJComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rolloverColumnUpdated(int paramInt1, int paramInt2) {
/*  68 */     if (XPStyle.getXP() != null) {
/*  69 */       this.header.repaint(this.header.getHeaderRect(paramInt1));
/*  70 */       this.header.repaint(this.header.getHeaderRect(paramInt2));
/*     */     } 
/*     */   }
/*     */   
/*     */   private class XPDefaultRenderer
/*     */     extends DefaultTableCellHeaderRenderer {
/*     */     XPStyle.Skin skin;
/*     */     boolean isSelected;
/*     */     
/*     */     XPDefaultRenderer() {
/*  80 */       setHorizontalAlignment(10);
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
/*     */     boolean hasFocus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean hasRollover;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int column;
/*     */ 
/*     */ 
/*     */ 
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
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: aload_2
/*     */       //   3: iload_3
/*     */       //   4: iload #4
/*     */       //   6: iload #5
/*     */       //   8: iload #6
/*     */       //   10: invokespecial getTableCellRendererComponent : (Ljavax/swing/JTable;Ljava/lang/Object;ZZII)Ljava/awt/Component;
/*     */       //   13: pop
/*     */       //   14: aload_0
/*     */       //   15: iload_3
/*     */       //   16: putfield isSelected : Z
/*     */       //   19: aload_0
/*     */       //   20: iload #4
/*     */       //   22: putfield hasFocus : Z
/*     */       //   25: aload_0
/*     */       //   26: iload #6
/*     */       //   28: putfield column : I
/*     */       //   31: aload_0
/*     */       //   32: iload #6
/*     */       //   34: aload_0
/*     */       //   35: getfield this$0 : Lcom/sun/java/swing/plaf/windows/WindowsTableHeaderUI;
/*     */       //   38: invokestatic access$000 : (Lcom/sun/java/swing/plaf/windows/WindowsTableHeaderUI;)I
/*     */       //   41: if_icmpne -> 48
/*     */       //   44: iconst_1
/*     */       //   45: goto -> 49
/*     */       //   48: iconst_0
/*     */       //   49: putfield hasRollover : Z
/*     */       //   52: aload_0
/*     */       //   53: getfield skin : Lcom/sun/java/swing/plaf/windows/XPStyle$Skin;
/*     */       //   56: ifnonnull -> 92
/*     */       //   59: invokestatic getXP : ()Lcom/sun/java/swing/plaf/windows/XPStyle;
/*     */       //   62: astore #7
/*     */       //   64: aload_0
/*     */       //   65: aload #7
/*     */       //   67: ifnull -> 88
/*     */       //   70: aload #7
/*     */       //   72: aload_0
/*     */       //   73: getfield this$0 : Lcom/sun/java/swing/plaf/windows/WindowsTableHeaderUI;
/*     */       //   76: invokestatic access$100 : (Lcom/sun/java/swing/plaf/windows/WindowsTableHeaderUI;)Ljavax/swing/table/JTableHeader;
/*     */       //   79: getstatic com/sun/java/swing/plaf/windows/TMSchema$Part.HP_HEADERITEM : Lcom/sun/java/swing/plaf/windows/TMSchema$Part;
/*     */       //   82: invokevirtual getSkin : (Ljava/awt/Component;Lcom/sun/java/swing/plaf/windows/TMSchema$Part;)Lcom/sun/java/swing/plaf/windows/XPStyle$Skin;
/*     */       //   85: goto -> 89
/*     */       //   88: aconst_null
/*     */       //   89: putfield skin : Lcom/sun/java/swing/plaf/windows/XPStyle$Skin;
/*     */       //   92: aload_0
/*     */       //   93: getfield skin : Lcom/sun/java/swing/plaf/windows/XPStyle$Skin;
/*     */       //   96: ifnull -> 109
/*     */       //   99: aload_0
/*     */       //   100: getfield skin : Lcom/sun/java/swing/plaf/windows/XPStyle$Skin;
/*     */       //   103: invokevirtual getContentMargin : ()Ljava/awt/Insets;
/*     */       //   106: goto -> 110
/*     */       //   109: aconst_null
/*     */       //   110: astore #7
/*     */       //   112: aconst_null
/*     */       //   113: astore #8
/*     */       //   115: iconst_0
/*     */       //   116: istore #9
/*     */       //   118: iconst_0
/*     */       //   119: istore #10
/*     */       //   121: iconst_0
/*     */       //   122: istore #11
/*     */       //   124: iconst_0
/*     */       //   125: istore #12
/*     */       //   127: aload #7
/*     */       //   129: ifnull -> 160
/*     */       //   132: aload #7
/*     */       //   134: getfield top : I
/*     */       //   137: istore #9
/*     */       //   139: aload #7
/*     */       //   141: getfield left : I
/*     */       //   144: istore #10
/*     */       //   146: aload #7
/*     */       //   148: getfield bottom : I
/*     */       //   151: istore #11
/*     */       //   153: aload #7
/*     */       //   155: getfield right : I
/*     */       //   158: istore #12
/*     */       //   160: iinc #10, 5
/*     */       //   163: iinc #11, 4
/*     */       //   166: iinc #12, 5
/*     */       //   169: invokestatic isOnVista : ()Z
/*     */       //   172: ifeq -> 362
/*     */       //   175: aload_0
/*     */       //   176: invokevirtual getIcon : ()Ljavax/swing/Icon;
/*     */       //   179: dup
/*     */       //   180: astore #13
/*     */       //   182: instanceof javax/swing/plaf/UIResource
/*     */       //   185: ifne -> 193
/*     */       //   188: aload #13
/*     */       //   190: ifnonnull -> 362
/*     */       //   193: iinc #9, 1
/*     */       //   196: aload_0
/*     */       //   197: aconst_null
/*     */       //   198: invokevirtual setIcon : (Ljavax/swing/Icon;)V
/*     */       //   201: aconst_null
/*     */       //   202: astore #13
/*     */       //   204: aload_1
/*     */       //   205: iload #6
/*     */       //   207: invokestatic getColumnSortOrder : (Ljavax/swing/JTable;I)Ljavax/swing/SortOrder;
/*     */       //   210: astore #14
/*     */       //   212: aload #14
/*     */       //   214: ifnull -> 269
/*     */       //   217: getstatic com/sun/java/swing/plaf/windows/WindowsTableHeaderUI$1.$SwitchMap$javax$swing$SortOrder : [I
/*     */       //   220: aload #14
/*     */       //   222: invokevirtual ordinal : ()I
/*     */       //   225: iaload
/*     */       //   226: lookupswitch default -> 269, 1 -> 252, 2 -> 262
/*     */       //   252: ldc 'Table.ascendingSortIcon'
/*     */       //   254: invokestatic getIcon : (Ljava/lang/Object;)Ljavax/swing/Icon;
/*     */       //   257: astore #13
/*     */       //   259: goto -> 269
/*     */       //   262: ldc 'Table.descendingSortIcon'
/*     */       //   264: invokestatic getIcon : (Ljava/lang/Object;)Ljavax/swing/Icon;
/*     */       //   267: astore #13
/*     */       //   269: aload #13
/*     */       //   271: ifnull -> 305
/*     */       //   274: aload #13
/*     */       //   276: invokeinterface getIconHeight : ()I
/*     */       //   281: istore #11
/*     */       //   283: new com/sun/java/swing/plaf/windows/WindowsTableHeaderUI$IconBorder
/*     */       //   286: dup
/*     */       //   287: aload #13
/*     */       //   289: iload #9
/*     */       //   291: iload #10
/*     */       //   293: iload #11
/*     */       //   295: iload #12
/*     */       //   297: invokespecial <init> : (Ljavax/swing/Icon;IIII)V
/*     */       //   300: astore #8
/*     */       //   302: goto -> 359
/*     */       //   305: ldc 'Table.ascendingSortIcon'
/*     */       //   307: invokestatic getIcon : (Ljava/lang/Object;)Ljavax/swing/Icon;
/*     */       //   310: astore #13
/*     */       //   312: aload #13
/*     */       //   314: ifnull -> 327
/*     */       //   317: aload #13
/*     */       //   319: invokeinterface getIconHeight : ()I
/*     */       //   324: goto -> 328
/*     */       //   327: iconst_0
/*     */       //   328: istore #15
/*     */       //   330: iload #15
/*     */       //   332: ifeq -> 339
/*     */       //   335: iload #15
/*     */       //   337: istore #11
/*     */       //   339: new javax/swing/border/EmptyBorder
/*     */       //   342: dup
/*     */       //   343: iload #15
/*     */       //   345: iload #9
/*     */       //   347: iadd
/*     */       //   348: iload #10
/*     */       //   350: iload #11
/*     */       //   352: iload #12
/*     */       //   354: invokespecial <init> : (IIII)V
/*     */       //   357: astore #8
/*     */       //   359: goto -> 382
/*     */       //   362: iinc #9, 3
/*     */       //   365: new javax/swing/border/EmptyBorder
/*     */       //   368: dup
/*     */       //   369: iload #9
/*     */       //   371: iload #10
/*     */       //   373: iload #11
/*     */       //   375: iload #12
/*     */       //   377: invokespecial <init> : (IIII)V
/*     */       //   380: astore #8
/*     */       //   382: aload_0
/*     */       //   383: aload #8
/*     */       //   385: invokevirtual setBorder : (Ljavax/swing/border/Border;)V
/*     */       //   388: aload_0
/*     */       //   389: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #86	-> 0
/*     */       //   #88	-> 14
/*     */       //   #89	-> 19
/*     */       //   #90	-> 25
/*     */       //   #91	-> 31
/*     */       //   #92	-> 52
/*     */       //   #93	-> 59
/*     */       //   #94	-> 64
/*     */       //   #96	-> 92
/*     */       //   #97	-> 112
/*     */       //   #98	-> 115
/*     */       //   #99	-> 118
/*     */       //   #100	-> 121
/*     */       //   #101	-> 124
/*     */       //   #102	-> 127
/*     */       //   #103	-> 132
/*     */       //   #104	-> 139
/*     */       //   #105	-> 146
/*     */       //   #106	-> 153
/*     */       //   #114	-> 160
/*     */       //   #115	-> 163
/*     */       //   #116	-> 166
/*     */       //   #122	-> 169
/*     */       //   #123	-> 176
/*     */       //   #125	-> 193
/*     */       //   #126	-> 196
/*     */       //   #127	-> 201
/*     */       //   #128	-> 204
/*     */       //   #129	-> 207
/*     */       //   #130	-> 212
/*     */       //   #131	-> 217
/*     */       //   #133	-> 252
/*     */       //   #134	-> 254
/*     */       //   #135	-> 259
/*     */       //   #137	-> 262
/*     */       //   #138	-> 264
/*     */       //   #142	-> 269
/*     */       //   #143	-> 274
/*     */       //   #144	-> 283
/*     */       //   #147	-> 305
/*     */       //   #148	-> 307
/*     */       //   #149	-> 312
/*     */       //   #150	-> 319
/*     */       //   #151	-> 330
/*     */       //   #152	-> 335
/*     */       //   #154	-> 339
/*     */       //   #159	-> 359
/*     */       //   #160	-> 362
/*     */       //   #161	-> 365
/*     */       //   #164	-> 382
/*     */       //   #165	-> 388
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
/*     */     public void paint(Graphics param1Graphics) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: invokevirtual getSize : ()Ljava/awt/Dimension;
/*     */       //   4: astore_2
/*     */       //   5: getstatic com/sun/java/swing/plaf/windows/TMSchema$State.NORMAL : Lcom/sun/java/swing/plaf/windows/TMSchema$State;
/*     */       //   8: astore_3
/*     */       //   9: aload_0
/*     */       //   10: getfield this$0 : Lcom/sun/java/swing/plaf/windows/WindowsTableHeaderUI;
/*     */       //   13: invokestatic access$200 : (Lcom/sun/java/swing/plaf/windows/WindowsTableHeaderUI;)Ljavax/swing/table/JTableHeader;
/*     */       //   16: invokevirtual getDraggedColumn : ()Ljavax/swing/table/TableColumn;
/*     */       //   19: astore #4
/*     */       //   21: aload #4
/*     */       //   23: ifnull -> 58
/*     */       //   26: aload_0
/*     */       //   27: getfield column : I
/*     */       //   30: aload_0
/*     */       //   31: getfield this$0 : Lcom/sun/java/swing/plaf/windows/WindowsTableHeaderUI;
/*     */       //   34: invokestatic access$300 : (Lcom/sun/java/swing/plaf/windows/WindowsTableHeaderUI;)Ljavax/swing/table/JTableHeader;
/*     */       //   37: invokevirtual getColumnModel : ()Ljavax/swing/table/TableColumnModel;
/*     */       //   40: aload #4
/*     */       //   42: invokevirtual getModelIndex : ()I
/*     */       //   45: invokestatic convertColumnIndexToView : (Ljavax/swing/table/TableColumnModel;I)I
/*     */       //   48: if_icmpne -> 58
/*     */       //   51: getstatic com/sun/java/swing/plaf/windows/TMSchema$State.PRESSED : Lcom/sun/java/swing/plaf/windows/TMSchema$State;
/*     */       //   54: astore_3
/*     */       //   55: goto -> 83
/*     */       //   58: aload_0
/*     */       //   59: getfield isSelected : Z
/*     */       //   62: ifne -> 79
/*     */       //   65: aload_0
/*     */       //   66: getfield hasFocus : Z
/*     */       //   69: ifne -> 79
/*     */       //   72: aload_0
/*     */       //   73: getfield hasRollover : Z
/*     */       //   76: ifeq -> 83
/*     */       //   79: getstatic com/sun/java/swing/plaf/windows/TMSchema$State.HOT : Lcom/sun/java/swing/plaf/windows/TMSchema$State;
/*     */       //   82: astore_3
/*     */       //   83: invokestatic isOnVista : ()Z
/*     */       //   86: ifeq -> 205
/*     */       //   89: aload_0
/*     */       //   90: getfield this$0 : Lcom/sun/java/swing/plaf/windows/WindowsTableHeaderUI;
/*     */       //   93: invokestatic access$400 : (Lcom/sun/java/swing/plaf/windows/WindowsTableHeaderUI;)Ljavax/swing/table/JTableHeader;
/*     */       //   96: invokevirtual getTable : ()Ljavax/swing/JTable;
/*     */       //   99: aload_0
/*     */       //   100: getfield column : I
/*     */       //   103: invokestatic getColumnSortOrder : (Ljavax/swing/JTable;I)Ljavax/swing/SortOrder;
/*     */       //   106: astore #5
/*     */       //   108: aload #5
/*     */       //   110: ifnull -> 205
/*     */       //   113: getstatic com/sun/java/swing/plaf/windows/WindowsTableHeaderUI$1.$SwitchMap$javax$swing$SortOrder : [I
/*     */       //   116: aload #5
/*     */       //   118: invokevirtual ordinal : ()I
/*     */       //   121: iaload
/*     */       //   122: lookupswitch default -> 205, 1 -> 148, 2 -> 148
/*     */       //   148: getstatic com/sun/java/swing/plaf/windows/WindowsTableHeaderUI$1.$SwitchMap$com$sun$java$swing$plaf$windows$TMSchema$State : [I
/*     */       //   151: aload_3
/*     */       //   152: invokevirtual ordinal : ()I
/*     */       //   155: iaload
/*     */       //   156: tableswitch default -> 205, 1 -> 184, 2 -> 191, 3 -> 198
/*     */       //   184: getstatic com/sun/java/swing/plaf/windows/TMSchema$State.SORTEDNORMAL : Lcom/sun/java/swing/plaf/windows/TMSchema$State;
/*     */       //   187: astore_3
/*     */       //   188: goto -> 205
/*     */       //   191: getstatic com/sun/java/swing/plaf/windows/TMSchema$State.SORTEDPRESSED : Lcom/sun/java/swing/plaf/windows/TMSchema$State;
/*     */       //   194: astore_3
/*     */       //   195: goto -> 205
/*     */       //   198: getstatic com/sun/java/swing/plaf/windows/TMSchema$State.SORTEDHOT : Lcom/sun/java/swing/plaf/windows/TMSchema$State;
/*     */       //   201: astore_3
/*     */       //   202: goto -> 205
/*     */       //   205: aload_0
/*     */       //   206: getfield skin : Lcom/sun/java/swing/plaf/windows/XPStyle$Skin;
/*     */       //   209: aload_1
/*     */       //   210: iconst_0
/*     */       //   211: iconst_0
/*     */       //   212: aload_2
/*     */       //   213: getfield width : I
/*     */       //   216: iconst_1
/*     */       //   217: isub
/*     */       //   218: aload_2
/*     */       //   219: getfield height : I
/*     */       //   222: iconst_1
/*     */       //   223: isub
/*     */       //   224: aload_3
/*     */       //   225: invokevirtual paintSkin : (Ljava/awt/Graphics;IIIILcom/sun/java/swing/plaf/windows/TMSchema$State;)V
/*     */       //   228: aload_0
/*     */       //   229: aload_1
/*     */       //   230: invokespecial paint : (Ljava/awt/Graphics;)V
/*     */       //   233: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #169	-> 0
/*     */       //   #170	-> 5
/*     */       //   #171	-> 9
/*     */       //   #172	-> 21
/*     */       //   #174	-> 34
/*     */       //   #173	-> 45
/*     */       //   #175	-> 51
/*     */       //   #176	-> 58
/*     */       //   #177	-> 79
/*     */       //   #180	-> 83
/*     */       //   #181	-> 89
/*     */       //   #182	-> 108
/*     */       //   #183	-> 113
/*     */       //   #187	-> 148
/*     */       //   #189	-> 184
/*     */       //   #190	-> 188
/*     */       //   #192	-> 191
/*     */       //   #193	-> 195
/*     */       //   #195	-> 198
/*     */       //   #196	-> 202
/*     */       //   #205	-> 205
/*     */       //   #206	-> 228
/*     */       //   #207	-> 233
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
/*     */   
/*     */   private static class IconBorder
/*     */     implements Border, UIResource
/*     */   {
/*     */     private final Icon icon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int top;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int left;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int bottom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int right;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public IconBorder(Icon param1Icon, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 227 */       this.icon = param1Icon;
/* 228 */       this.top = param1Int1;
/* 229 */       this.left = param1Int2;
/* 230 */       this.bottom = param1Int3;
/* 231 */       this.right = param1Int4;
/*     */     }
/*     */     public Insets getBorderInsets(Component param1Component) {
/* 234 */       return new Insets(this.icon.getIconHeight() + this.top, this.left, this.bottom, this.right);
/*     */     }
/*     */     public boolean isBorderOpaque() {
/* 237 */       return false;
/*     */     }
/*     */     
/*     */     public void paintBorder(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 241 */       this.icon.paintIcon(param1Component, param1Graphics, param1Int1 + this.left + (param1Int3 - this.left - this.right - this.icon
/* 242 */           .getIconWidth()) / 2, param1Int2 + this.top);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\java\swing\plaf\windows\WindowsTableHeaderUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */