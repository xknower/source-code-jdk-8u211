/*     */ package javax.print.attribute.standard;
/*     */ 
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.DocAttribute;
/*     */ import javax.print.attribute.EnumSyntax;
/*     */ import javax.print.attribute.PrintJobAttribute;
/*     */ import javax.print.attribute.PrintRequestAttribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OrientationRequested
/*     */   extends EnumSyntax
/*     */   implements DocAttribute, PrintRequestAttribute, PrintJobAttribute
/*     */ {
/*     */   private static final long serialVersionUID = -4447437289862822276L;
/*  74 */   public static final OrientationRequested PORTRAIT = new OrientationRequested(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public static final OrientationRequested LANDSCAPE = new OrientationRequested(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public static final OrientationRequested REVERSE_LANDSCAPE = new OrientationRequested(5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static final OrientationRequested REVERSE_PORTRAIT = new OrientationRequested(6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected OrientationRequested(int paramInt) {
/* 123 */     super(paramInt);
/*     */   }
/*     */   
/* 126 */   private static final String[] myStringTable = new String[] { "portrait", "landscape", "reverse-landscape", "reverse-portrait" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   private static final OrientationRequested[] myEnumValueTable = new OrientationRequested[] { PORTRAIT, LANDSCAPE, REVERSE_LANDSCAPE, REVERSE_PORTRAIT };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] getStringTable() {
/* 144 */     return myStringTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumSyntax[] getEnumValueTable() {
/* 151 */     return (EnumSyntax[])myEnumValueTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getOffset() {
/* 158 */     return 3;
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
/*     */   public final Class<? extends Attribute> getCategory() {
/* 172 */     return (Class)OrientationRequested.class;
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
/*     */   public final String getName() {
/* 185 */     return "orientation-requested";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\print\attribute\standard\OrientationRequested.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */