/*     */ package javax.print.attribute.standard;
/*     */ 
/*     */ import javax.print.attribute.Attribute;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultipleDocumentHandling
/*     */   extends EnumSyntax
/*     */   implements PrintRequestAttribute, PrintJobAttribute
/*     */ {
/*     */   private static final long serialVersionUID = 8098326460746413466L;
/* 179 */   public static final MultipleDocumentHandling SINGLE_DOCUMENT = new MultipleDocumentHandling(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 186 */   public static final MultipleDocumentHandling SEPARATE_DOCUMENTS_UNCOLLATED_COPIES = new MultipleDocumentHandling(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public static final MultipleDocumentHandling SEPARATE_DOCUMENTS_COLLATED_COPIES = new MultipleDocumentHandling(2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 200 */   public static final MultipleDocumentHandling SINGLE_DOCUMENT_NEW_SHEET = new MultipleDocumentHandling(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MultipleDocumentHandling(int paramInt) {
/* 210 */     super(paramInt);
/*     */   }
/*     */   
/* 213 */   private static final String[] myStringTable = new String[] { "single-document", "separate-documents-uncollated-copies", "separate-documents-collated-copies", "single-document-new-sheet" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   private static final MultipleDocumentHandling[] myEnumValueTable = new MultipleDocumentHandling[] { SINGLE_DOCUMENT, SEPARATE_DOCUMENTS_UNCOLLATED_COPIES, SEPARATE_DOCUMENTS_COLLATED_COPIES, SINGLE_DOCUMENT_NEW_SHEET };
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
/* 231 */     return (String[])myStringTable.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EnumSyntax[] getEnumValueTable() {
/* 238 */     return (EnumSyntax[])myEnumValueTable.clone();
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
/* 252 */     return (Class)MultipleDocumentHandling.class;
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
/* 265 */     return "multiple-document-handling";
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\print\attribute\standard\MultipleDocumentHandling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */