/*     */ package javax.print.attribute.standard;
/*     */ 
/*     */ import javax.print.attribute.Attribute;
/*     */ import javax.print.attribute.DocAttribute;
/*     */ import javax.print.attribute.EnumSyntax;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Compression
/*     */   extends EnumSyntax
/*     */   implements DocAttribute
/*     */ {
/*     */   private static final long serialVersionUID = -5716748913324997674L;
/*  54 */   public static final Compression NONE = new Compression(0);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public static final Compression DEFLATE = new Compression(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final Compression GZIP = new Compression(2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static final Compression COMPRESS = new Compression(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Compression(int paramInt) {
/*  79 */     super(paramInt);
/*     */   }
/*     */ 
/*     */   
/*  83 */   private static final String[] myStringTable = new String[] { "none", "deflate", "gzip", "compress" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private static final Compression[] myEnumValueTable = new Compression[] { NONE, DEFLATE, GZIP, COMPRESS };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] getStringTable() {
/*  97 */     return (String[])myStringTable.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumSyntax[] getEnumValueTable() {
/* 104 */     return (EnumSyntax[])myEnumValueTable.clone();
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
/* 118 */     return (Class)Compression.class;
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
/* 131 */     return "compression";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\print\attribute\standard\Compression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */