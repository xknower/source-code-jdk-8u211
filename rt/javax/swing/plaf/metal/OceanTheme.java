/*     */ package javax.swing.plaf.metal;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Insets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.swing.AbstractButton;
/*     */ import javax.swing.ButtonModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.plaf.ColorUIResource;
/*     */ import javax.swing.plaf.IconUIResource;
/*     */ import sun.swing.PrintColorUIResource;
/*     */ import sun.swing.SwingLazyValue;
/*     */ import sun.swing.SwingUtilities2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OceanTheme
/*     */   extends DefaultMetalTheme
/*     */ {
/*  55 */   private static final ColorUIResource PRIMARY1 = new ColorUIResource(6521535);
/*     */   
/*  57 */   private static final ColorUIResource PRIMARY2 = new ColorUIResource(10729676);
/*     */   
/*  59 */   private static final ColorUIResource PRIMARY3 = new ColorUIResource(12111845);
/*     */   
/*  61 */   private static final ColorUIResource SECONDARY1 = new ColorUIResource(8030873);
/*     */   
/*  63 */   private static final ColorUIResource SECONDARY2 = new ColorUIResource(12111845);
/*     */   
/*  65 */   private static final ColorUIResource SECONDARY3 = new ColorUIResource(15658734);
/*     */ 
/*     */   
/*  68 */   private static final ColorUIResource CONTROL_TEXT_COLOR = new PrintColorUIResource(3355443, Color.BLACK);
/*     */   
/*  70 */   private static final ColorUIResource INACTIVE_CONTROL_TEXT_COLOR = new ColorUIResource(10066329);
/*     */   
/*  72 */   private static final ColorUIResource MENU_DISABLED_FOREGROUND = new ColorUIResource(10066329);
/*     */   
/*  74 */   private static final ColorUIResource OCEAN_BLACK = new PrintColorUIResource(3355443, Color.BLACK);
/*     */ 
/*     */   
/*  77 */   private static final ColorUIResource OCEAN_DROP = new ColorUIResource(13822463);
/*     */ 
/*     */   
/*     */   private static class COIcon
/*     */     extends IconUIResource
/*     */   {
/*     */     private Icon rtl;
/*     */     
/*     */     public COIcon(Icon param1Icon1, Icon param1Icon2) {
/*  86 */       super(param1Icon1);
/*  87 */       this.rtl = param1Icon2;
/*     */     }
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/*  91 */       if (MetalUtils.isLeftToRight(param1Component)) {
/*  92 */         super.paintIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*     */       } else {
/*  94 */         this.rtl.paintIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class IFIcon
/*     */     extends IconUIResource
/*     */   {
/*     */     private Icon pressed;
/*     */     
/*     */     public IFIcon(Icon param1Icon1, Icon param1Icon2) {
/* 105 */       super(param1Icon1);
/* 106 */       this.pressed = param1Icon2;
/*     */     }
/*     */     
/*     */     public void paintIcon(Component param1Component, Graphics param1Graphics, int param1Int1, int param1Int2) {
/* 110 */       ButtonModel buttonModel = ((AbstractButton)param1Component).getModel();
/* 111 */       if (buttonModel.isPressed() && buttonModel.isArmed()) {
/* 112 */         this.pressed.paintIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*     */       } else {
/* 114 */         super.paintIcon(param1Component, param1Graphics, param1Int1, param1Int2);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCustomEntriesToTable(UIDefaults paramUIDefaults) {
/* 134 */     SwingLazyValue swingLazyValue = new SwingLazyValue("javax.swing.plaf.BorderUIResource$LineBorderUIResource", new Object[] { getPrimary1() });
/*     */     
/* 136 */     List list1 = Arrays.asList(new Object[] { new Float(0.3F), new Float(0.0F), new ColorUIResource(14543091), 
/*     */           
/* 138 */           getWhite(), getSecondary2() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     ColorUIResource colorUIResource1 = new ColorUIResource(13421772);
/* 148 */     ColorUIResource colorUIResource2 = new ColorUIResource(14342874);
/* 149 */     ColorUIResource colorUIResource3 = new ColorUIResource(13164018);
/* 150 */     Object object1 = getIconResource("icons/ocean/directory.gif");
/* 151 */     Object object2 = getIconResource("icons/ocean/file.gif");
/* 152 */     List list2 = Arrays.asList(new Object[] { new Float(0.3F), new Float(0.2F), colorUIResource3, 
/*     */           
/* 154 */           getWhite(), new ColorUIResource(SECONDARY2) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     Object[] arrayOfObject = { "Button.gradient", list1, "Button.rollover", Boolean.TRUE, "Button.toolBarBorderBackground", INACTIVE_CONTROL_TEXT_COLOR, "Button.disabledToolBarBorderBackground", colorUIResource1, "Button.rolloverIconType", "ocean", "CheckBox.rollover", Boolean.TRUE, "CheckBox.gradient", list1, "CheckBoxMenuItem.gradient", list1, "FileChooser.homeFolderIcon", getIconResource("icons/ocean/homeFolder.gif"), "FileChooser.newFolderIcon", getIconResource("icons/ocean/newFolder.gif"), "FileChooser.upFolderIcon", getIconResource("icons/ocean/upFolder.gif"), "FileView.computerIcon", getIconResource("icons/ocean/computer.gif"), "FileView.directoryIcon", object1, "FileView.hardDriveIcon", getIconResource("icons/ocean/hardDrive.gif"), "FileView.fileIcon", object2, "FileView.floppyDriveIcon", getIconResource("icons/ocean/floppy.gif"), "Label.disabledForeground", getInactiveControlTextColor(), "Menu.opaque", Boolean.FALSE, "MenuBar.gradient", Arrays.asList(new Object[] { new Float(1.0F), new Float(0.0F), getWhite(), colorUIResource2, new ColorUIResource(colorUIResource2) }), "MenuBar.borderColor", colorUIResource1, "InternalFrame.activeTitleGradient", list1, "InternalFrame.closeIcon", new UIDefaults.LazyValue() { public Object createValue(UIDefaults param1UIDefaults) { return new OceanTheme.IFIcon(OceanTheme.this.getHastenedIcon("icons/ocean/close.gif", param1UIDefaults), OceanTheme.this.getHastenedIcon("icons/ocean/close-pressed.gif", param1UIDefaults)); } }, "InternalFrame.iconifyIcon", new UIDefaults.LazyValue() { public Object createValue(UIDefaults param1UIDefaults) { return new OceanTheme.IFIcon(OceanTheme.this.getHastenedIcon("icons/ocean/iconify.gif", param1UIDefaults), OceanTheme.this.getHastenedIcon("icons/ocean/iconify-pressed.gif", param1UIDefaults)); } }, "InternalFrame.minimizeIcon", new UIDefaults.LazyValue() { public Object createValue(UIDefaults param1UIDefaults) { return new OceanTheme.IFIcon(OceanTheme.this.getHastenedIcon("icons/ocean/minimize.gif", param1UIDefaults), OceanTheme.this.getHastenedIcon("icons/ocean/minimize-pressed.gif", param1UIDefaults)); } }, "InternalFrame.icon", getIconResource("icons/ocean/menu.gif"), "InternalFrame.maximizeIcon", new UIDefaults.LazyValue() { public Object createValue(UIDefaults param1UIDefaults) { return new OceanTheme.IFIcon(OceanTheme.this.getHastenedIcon("icons/ocean/maximize.gif", param1UIDefaults), OceanTheme.this.getHastenedIcon("icons/ocean/maximize-pressed.gif", param1UIDefaults)); } }, "InternalFrame.paletteCloseIcon", new UIDefaults.LazyValue() { public Object createValue(UIDefaults param1UIDefaults) { return new OceanTheme.IFIcon(OceanTheme.this.getHastenedIcon("icons/ocean/paletteClose.gif", param1UIDefaults), OceanTheme.this.getHastenedIcon("icons/ocean/paletteClose-pressed.gif", param1UIDefaults)); } }, "List.focusCellHighlightBorder", swingLazyValue, "MenuBarUI", "javax.swing.plaf.metal.MetalMenuBarUI", "OptionPane.errorIcon", getIconResource("icons/ocean/error.png"), "OptionPane.informationIcon", getIconResource("icons/ocean/info.png"), "OptionPane.questionIcon", getIconResource("icons/ocean/question.png"), "OptionPane.warningIcon", getIconResource("icons/ocean/warning.png"), "RadioButton.gradient", list1, "RadioButton.rollover", Boolean.TRUE, "RadioButtonMenuItem.gradient", list1, "ScrollBar.gradient", list1, "Slider.altTrackColor", new ColorUIResource(13820655), "Slider.gradient", list2, "Slider.focusGradient", list2, "SplitPane.oneTouchButtonsOpaque", Boolean.FALSE, "SplitPane.dividerFocusColor", colorUIResource3, "TabbedPane.borderHightlightColor", getPrimary1(), "TabbedPane.contentAreaColor", colorUIResource3, "TabbedPane.contentBorderInsets", new Insets(4, 2, 3, 3), "TabbedPane.selected", colorUIResource3, "TabbedPane.tabAreaBackground", colorUIResource2, "TabbedPane.tabAreaInsets", new Insets(2, 2, 0, 6), "TabbedPane.unselectedBackground", SECONDARY3, "Table.focusCellHighlightBorder", swingLazyValue, "Table.gridColor", SECONDARY1, "TableHeader.focusCellBackground", colorUIResource3, "ToggleButton.gradient", list1, "ToolBar.borderColor", colorUIResource1, "ToolBar.isRollover", Boolean.TRUE, "Tree.closedIcon", object1, "Tree.collapsedIcon", new UIDefaults.LazyValue() { public Object createValue(UIDefaults param1UIDefaults) { return new OceanTheme.COIcon(OceanTheme.this.getHastenedIcon("icons/ocean/collapsed.gif", param1UIDefaults), OceanTheme.this.getHastenedIcon("icons/ocean/collapsed-rtl.gif", param1UIDefaults)); } }, "Tree.expandedIcon", getIconResource("icons/ocean/expanded.gif"), "Tree.leafIcon", object2, "Tree.openIcon", object1, "Tree.selectionBorderColor", getPrimary1(), "Tree.dropLineColor", getPrimary1(), "Table.dropLineColor", getPrimary1(), "Table.dropLineShortColor", OCEAN_BLACK, "Table.dropCellBackground", OCEAN_DROP, "Tree.dropCellBackground", OCEAN_DROP, "List.dropCellBackground", OCEAN_DROP, "List.dropLineColor", getPrimary1() };
/*     */     
/* 313 */     paramUIDefaults.putDefaults(arrayOfObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isSystemTheme() {
/* 320 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 329 */     return "Ocean";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getPrimary1() {
/* 340 */     return PRIMARY1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getPrimary2() {
/* 351 */     return PRIMARY2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getPrimary3() {
/* 362 */     return PRIMARY3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getSecondary1() {
/* 373 */     return SECONDARY1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getSecondary2() {
/* 384 */     return SECONDARY2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getSecondary3() {
/* 395 */     return SECONDARY3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ColorUIResource getBlack() {
/* 406 */     return OCEAN_BLACK;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorUIResource getDesktopColor() {
/* 417 */     return MetalTheme.white;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorUIResource getInactiveControlTextColor() {
/* 427 */     return INACTIVE_CONTROL_TEXT_COLOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorUIResource getControlTextColor() {
/* 437 */     return CONTROL_TEXT_COLOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorUIResource getMenuDisabledForeground() {
/* 447 */     return MENU_DISABLED_FOREGROUND;
/*     */   }
/*     */   
/*     */   private Object getIconResource(String paramString) {
/* 451 */     return SwingUtilities2.makeIcon(getClass(), OceanTheme.class, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Icon getHastenedIcon(String paramString, UIDefaults paramUIDefaults) {
/* 457 */     Object object = getIconResource(paramString);
/* 458 */     return (Icon)((UIDefaults.LazyValue)object).createValue(paramUIDefaults);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\swing\plaf\metal\OceanTheme.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */