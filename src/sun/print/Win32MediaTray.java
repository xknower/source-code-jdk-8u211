/*     */ package sun.print;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import javax.print.attribute.EnumSyntax;
/*     */ import javax.print.attribute.standard.MediaTray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Win32MediaTray
/*     */   extends MediaTray
/*     */ {
/*  40 */   static final Win32MediaTray ENVELOPE_MANUAL = new Win32MediaTray(0, 6);
/*     */   
/*  42 */   static final Win32MediaTray AUTO = new Win32MediaTray(1, 7);
/*     */   
/*  44 */   static final Win32MediaTray TRACTOR = new Win32MediaTray(2, 8);
/*     */   
/*  46 */   static final Win32MediaTray SMALL_FORMAT = new Win32MediaTray(3, 9);
/*     */   
/*  48 */   static final Win32MediaTray LARGE_FORMAT = new Win32MediaTray(4, 10);
/*     */   
/*  50 */   static final Win32MediaTray FORMSOURCE = new Win32MediaTray(5, 15);
/*     */ 
/*     */   
/*  53 */   private static ArrayList winStringTable = new ArrayList();
/*  54 */   private static ArrayList winEnumTable = new ArrayList();
/*     */   public int winID;
/*     */   
/*     */   private Win32MediaTray(int paramInt1, int paramInt2) {
/*  58 */     super(paramInt1);
/*  59 */     this.winID = paramInt2;
/*     */   }
/*     */   
/*     */   private static synchronized int nextValue(String paramString) {
/*  63 */     winStringTable.add(paramString);
/*  64 */     return getTraySize() - 1;
/*     */   }
/*     */   
/*     */   protected Win32MediaTray(int paramInt, String paramString) {
/*  68 */     super(nextValue(paramString));
/*  69 */     this.winID = paramInt;
/*  70 */     winEnumTable.add(this);
/*     */   }
/*     */   
/*     */   public int getDMBinID() {
/*  74 */     return this.winID;
/*     */   }
/*     */   
/*  77 */   private static final String[] myStringTable = new String[] { "Manual-Envelope", "Automatic-Feeder", "Tractor-Feeder", "Small-Format", "Large-Format", "Form-Source" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private static final MediaTray[] myEnumValueTable = new MediaTray[] { ENVELOPE_MANUAL, AUTO, TRACTOR, SMALL_FORMAT, LARGE_FORMAT, FORMSOURCE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int getTraySize() {
/*  96 */     return myStringTable.length + winStringTable.size();
/*     */   }
/*     */   
/*     */   protected String[] getStringTable() {
/* 100 */     ArrayList<String> arrayList = new ArrayList();
/* 101 */     for (byte b = 0; b < myStringTable.length; b++) {
/* 102 */       arrayList.add(myStringTable[b]);
/*     */     }
/* 104 */     arrayList.addAll(winStringTable);
/* 105 */     String[] arrayOfString = new String[arrayList.size()];
/* 106 */     return arrayList.<String>toArray(arrayOfString);
/*     */   }
/*     */   
/*     */   protected EnumSyntax[] getEnumValueTable() {
/* 110 */     ArrayList<MediaTray> arrayList = new ArrayList();
/* 111 */     for (byte b = 0; b < myEnumValueTable.length; b++) {
/* 112 */       arrayList.add(myEnumValueTable[b]);
/*     */     }
/* 114 */     arrayList.addAll(winEnumTable);
/* 115 */     MediaTray[] arrayOfMediaTray = new MediaTray[arrayList.size()];
/* 116 */     return (EnumSyntax[])arrayList.<MediaTray>toArray(arrayOfMediaTray);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\print\Win32MediaTray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */