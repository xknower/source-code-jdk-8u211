package javax.print.event;

public interface PrintJobListener {
  void printDataTransferCompleted(PrintJobEvent paramPrintJobEvent);
  
  void printJobCompleted(PrintJobEvent paramPrintJobEvent);
  
  void printJobFailed(PrintJobEvent paramPrintJobEvent);
  
  void printJobCanceled(PrintJobEvent paramPrintJobEvent);
  
  void printJobNoMoreEvents(PrintJobEvent paramPrintJobEvent);
  
  void printJobRequiresAttention(PrintJobEvent paramPrintJobEvent);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\print\event\PrintJobListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */