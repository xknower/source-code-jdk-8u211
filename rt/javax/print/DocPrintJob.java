package javax.print;

import javax.print.attribute.PrintJobAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.event.PrintJobAttributeListener;
import javax.print.event.PrintJobListener;

public interface DocPrintJob {
  PrintService getPrintService();
  
  PrintJobAttributeSet getAttributes();
  
  void addPrintJobListener(PrintJobListener paramPrintJobListener);
  
  void removePrintJobListener(PrintJobListener paramPrintJobListener);
  
  void addPrintJobAttributeListener(PrintJobAttributeListener paramPrintJobAttributeListener, PrintJobAttributeSet paramPrintJobAttributeSet);
  
  void removePrintJobAttributeListener(PrintJobAttributeListener paramPrintJobAttributeListener);
  
  void print(Doc paramDoc, PrintRequestAttributeSet paramPrintRequestAttributeSet) throws PrintException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\print\DocPrintJob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */