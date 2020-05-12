/*      */ package java.lang;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
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
/*      */ public class Throwable
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -3042686055658047285L;
/*      */   private transient Object backtrace;
/*      */   private String detailMessage;
/*      */   
/*      */   private static class SentinelHolder
/*      */   {
/*  145 */     public static final StackTraceElement STACK_TRACE_ELEMENT_SENTINEL = new StackTraceElement("", "", null, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  152 */     public static final StackTraceElement[] STACK_TRACE_SENTINEL = new StackTraceElement[] { STACK_TRACE_ELEMENT_SENTINEL };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  159 */   private static final StackTraceElement[] UNASSIGNED_STACK = new StackTraceElement[0];
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
/*  197 */   private Throwable cause = this;
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
/*  210 */   private StackTraceElement[] stackTrace = UNASSIGNED_STACK;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  215 */   private static final List<Throwable> SUPPRESSED_SENTINEL = Collections.unmodifiableList(new ArrayList<>(0));
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
/*  227 */   private List<Throwable> suppressedExceptions = SUPPRESSED_SENTINEL;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String NULL_CAUSE_MESSAGE = "Cannot suppress a null exception.";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String SELF_SUPPRESSION_MESSAGE = "Self-suppression not permitted";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String CAUSE_CAPTION = "Caused by: ";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String SUPPRESSED_CAPTION = "Suppressed: ";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Throwable() {
/*  250 */     fillInStackTrace();
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
/*      */   public Throwable(String paramString) {
/*  265 */     fillInStackTrace();
/*  266 */     this.detailMessage = paramString;
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
/*      */   public Throwable(String paramString, Throwable paramThrowable) {
/*  287 */     fillInStackTrace();
/*  288 */     this.detailMessage = paramString;
/*  289 */     this.cause = paramThrowable;
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
/*      */   public Throwable(Throwable paramThrowable) {
/*  310 */     fillInStackTrace();
/*  311 */     this.detailMessage = (paramThrowable == null) ? null : paramThrowable.toString();
/*  312 */     this.cause = paramThrowable;
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
/*      */   protected Throwable(String paramString, Throwable paramThrowable, boolean paramBoolean1, boolean paramBoolean2) {
/*  359 */     if (paramBoolean2) {
/*  360 */       fillInStackTrace();
/*      */     } else {
/*  362 */       this.stackTrace = null;
/*      */     } 
/*  364 */     this.detailMessage = paramString;
/*  365 */     this.cause = paramThrowable;
/*  366 */     if (!paramBoolean1) {
/*  367 */       this.suppressedExceptions = null;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMessage() {
/*  377 */     return this.detailMessage;
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
/*      */   public String getLocalizedMessage() {
/*  391 */     return getMessage();
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
/*      */   public synchronized Throwable getCause() {
/*  415 */     return (this.cause == this) ? null : this.cause;
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
/*      */   public synchronized Throwable initCause(Throwable paramThrowable) {
/*  455 */     if (this.cause != this)
/*  456 */       throw new IllegalStateException("Can't overwrite cause with " + 
/*  457 */           Objects.toString(paramThrowable, "a null"), this); 
/*  458 */     if (paramThrowable == this)
/*  459 */       throw new IllegalArgumentException("Self-causation not permitted", this); 
/*  460 */     this.cause = paramThrowable;
/*  461 */     return this;
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
/*      */   public String toString() {
/*  479 */     String str1 = getClass().getName();
/*  480 */     String str2 = getLocalizedMessage();
/*  481 */     return (str2 != null) ? (str1 + ": " + str2) : str1;
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
/*      */   public void printStackTrace() {
/*  634 */     printStackTrace(System.err);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printStackTrace(PrintStream paramPrintStream) {
/*  643 */     printStackTrace(new WrappedPrintStream(paramPrintStream));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printStackTrace(PrintStreamOrWriter paramPrintStreamOrWriter) {
/*  650 */     Set<?> set = Collections.newSetFromMap(new IdentityHashMap<>());
/*  651 */     set.add(this);
/*      */     
/*  653 */     synchronized (paramPrintStreamOrWriter.lock()) {
/*      */       
/*  655 */       paramPrintStreamOrWriter.println(this);
/*  656 */       StackTraceElement[] arrayOfStackTraceElement = getOurStackTrace();
/*  657 */       for (StackTraceElement stackTraceElement : arrayOfStackTraceElement) {
/*  658 */         paramPrintStreamOrWriter.println("\tat " + stackTraceElement);
/*      */       }
/*      */       
/*  661 */       for (Throwable throwable1 : getSuppressed()) {
/*  662 */         throwable1.printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Suppressed: ", "\t", (Set)set);
/*      */       }
/*      */       
/*  665 */       Throwable throwable = getCause();
/*  666 */       if (throwable != null) {
/*  667 */         throwable.printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Caused by: ", "", (Set)set);
/*      */       }
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
/*      */   private void printEnclosedStackTrace(PrintStreamOrWriter paramPrintStreamOrWriter, StackTraceElement[] paramArrayOfStackTraceElement, String paramString1, String paramString2, Set<Throwable> paramSet) {
/*  680 */     assert Thread.holdsLock(paramPrintStreamOrWriter.lock());
/*  681 */     if (paramSet.contains(this)) {
/*  682 */       paramPrintStreamOrWriter.println("\t[CIRCULAR REFERENCE:" + this + "]");
/*      */     } else {
/*  684 */       paramSet.add(this);
/*      */       
/*  686 */       StackTraceElement[] arrayOfStackTraceElement = getOurStackTrace();
/*  687 */       int i = arrayOfStackTraceElement.length - 1;
/*  688 */       int j = paramArrayOfStackTraceElement.length - 1;
/*  689 */       while (i >= 0 && j >= 0 && arrayOfStackTraceElement[i].equals(paramArrayOfStackTraceElement[j])) {
/*  690 */         i--; j--;
/*      */       } 
/*  692 */       int k = arrayOfStackTraceElement.length - 1 - i;
/*      */ 
/*      */       
/*  695 */       paramPrintStreamOrWriter.println(paramString2 + paramString1 + this);
/*  696 */       for (byte b = 0; b <= i; b++)
/*  697 */         paramPrintStreamOrWriter.println(paramString2 + "\tat " + arrayOfStackTraceElement[b]); 
/*  698 */       if (k != 0) {
/*  699 */         paramPrintStreamOrWriter.println(paramString2 + "\t... " + k + " more");
/*      */       }
/*      */       
/*  702 */       for (Throwable throwable1 : getSuppressed()) {
/*  703 */         throwable1.printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Suppressed: ", paramString2 + "\t", paramSet);
/*      */       }
/*      */ 
/*      */       
/*  707 */       Throwable throwable = getCause();
/*  708 */       if (throwable != null) {
/*  709 */         throwable.printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Caused by: ", paramString2, paramSet);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printStackTrace(PrintWriter paramPrintWriter) {
/*  721 */     printStackTrace(new WrappedPrintWriter(paramPrintWriter));
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class PrintStreamOrWriter
/*      */   {
/*      */     private PrintStreamOrWriter() {}
/*      */     
/*      */     abstract Object lock();
/*      */     
/*      */     abstract void println(Object param1Object);
/*      */   }
/*      */   
/*      */   private static class WrappedPrintStream
/*      */     extends PrintStreamOrWriter
/*      */   {
/*      */     private final PrintStream printStream;
/*      */     
/*      */     WrappedPrintStream(PrintStream param1PrintStream) {
/*  740 */       this.printStream = param1PrintStream;
/*      */     }
/*      */     
/*      */     Object lock() {
/*  744 */       return this.printStream;
/*      */     }
/*      */     
/*      */     void println(Object param1Object) {
/*  748 */       this.printStream.println(param1Object);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class WrappedPrintWriter extends PrintStreamOrWriter {
/*      */     private final PrintWriter printWriter;
/*      */     
/*      */     WrappedPrintWriter(PrintWriter param1PrintWriter) {
/*  756 */       this.printWriter = param1PrintWriter;
/*      */     }
/*      */     
/*      */     Object lock() {
/*  760 */       return this.printWriter;
/*      */     }
/*      */     
/*      */     void println(Object param1Object) {
/*  764 */       this.printWriter.println(param1Object);
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
/*      */ 
/*      */   
/*      */   public synchronized Throwable fillInStackTrace() {
/*  781 */     if (this.stackTrace != null || this.backtrace != null) {
/*      */       
/*  783 */       fillInStackTrace(0);
/*  784 */       this.stackTrace = UNASSIGNED_STACK;
/*      */     } 
/*  786 */     return this;
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
/*      */   private native Throwable fillInStackTrace(int paramInt);
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
/*      */   public StackTraceElement[] getStackTrace() {
/*  816 */     return (StackTraceElement[])getOurStackTrace().clone();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized StackTraceElement[] getOurStackTrace() {
/*  822 */     if (this.stackTrace == UNASSIGNED_STACK || (this.stackTrace == null && this.backtrace != null)) {
/*      */       
/*  824 */       int i = getStackTraceDepth();
/*  825 */       this.stackTrace = new StackTraceElement[i];
/*  826 */       for (byte b = 0; b < i; b++)
/*  827 */         this.stackTrace[b] = getStackTraceElement(b); 
/*  828 */     } else if (this.stackTrace == null) {
/*  829 */       return UNASSIGNED_STACK;
/*      */     } 
/*  831 */     return this.stackTrace;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStackTrace(StackTraceElement[] paramArrayOfStackTraceElement) {
/*  864 */     StackTraceElement[] arrayOfStackTraceElement = (StackTraceElement[])paramArrayOfStackTraceElement.clone();
/*  865 */     for (byte b = 0; b < arrayOfStackTraceElement.length; b++) {
/*  866 */       if (arrayOfStackTraceElement[b] == null) {
/*  867 */         throw new NullPointerException("stackTrace[" + b + "]");
/*      */       }
/*      */     } 
/*  870 */     synchronized (this) {
/*  871 */       if (this.stackTrace == null && this.backtrace == null) {
/*      */         return;
/*      */       }
/*  874 */       this.stackTrace = arrayOfStackTraceElement;
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
/*      */   
/*      */   native int getStackTraceDepth();
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
/*      */   native StackTraceElement getStackTraceElement(int paramInt);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/*  914 */     paramObjectInputStream.defaultReadObject();
/*  915 */     if (this.suppressedExceptions != null) {
/*  916 */       List<Throwable> list = null;
/*  917 */       if (this.suppressedExceptions.isEmpty()) {
/*      */         
/*  919 */         list = SUPPRESSED_SENTINEL;
/*      */       } else {
/*  921 */         list = new ArrayList<>(1);
/*  922 */         for (Throwable throwable : this.suppressedExceptions) {
/*      */ 
/*      */           
/*  925 */           if (throwable == null)
/*  926 */             throw new NullPointerException("Cannot suppress a null exception."); 
/*  927 */           if (throwable == this)
/*  928 */             throw new IllegalArgumentException("Self-suppression not permitted"); 
/*  929 */           list.add(throwable);
/*      */         } 
/*      */       } 
/*  932 */       this.suppressedExceptions = list;
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
/*  944 */     if (this.stackTrace != null) {
/*  945 */       if (this.stackTrace.length == 0) {
/*  946 */         this.stackTrace = (StackTraceElement[])UNASSIGNED_STACK.clone();
/*  947 */       } else if (this.stackTrace.length == 1 && SentinelHolder.STACK_TRACE_ELEMENT_SENTINEL
/*      */         
/*  949 */         .equals(this.stackTrace[0])) {
/*  950 */         this.stackTrace = null;
/*      */       } else {
/*  952 */         for (StackTraceElement stackTraceElement : this.stackTrace) {
/*  953 */           if (stackTraceElement == null) {
/*  954 */             throw new NullPointerException("null StackTraceElement in serial stream. ");
/*      */           }
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  962 */       this.stackTrace = (StackTraceElement[])UNASSIGNED_STACK.clone();
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
/*      */ 
/*      */   
/*      */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/*  979 */     getOurStackTrace();
/*      */     
/*  981 */     StackTraceElement[] arrayOfStackTraceElement = this.stackTrace;
/*      */     try {
/*  983 */       if (this.stackTrace == null)
/*  984 */         this.stackTrace = SentinelHolder.STACK_TRACE_SENTINEL; 
/*  985 */       paramObjectOutputStream.defaultWriteObject();
/*      */     } finally {
/*  987 */       this.stackTrace = arrayOfStackTraceElement;
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
/*      */   public final synchronized void addSuppressed(Throwable paramThrowable) {
/* 1042 */     if (paramThrowable == this) {
/* 1043 */       throw new IllegalArgumentException("Self-suppression not permitted", paramThrowable);
/*      */     }
/* 1045 */     if (paramThrowable == null) {
/* 1046 */       throw new NullPointerException("Cannot suppress a null exception.");
/*      */     }
/* 1048 */     if (this.suppressedExceptions == null) {
/*      */       return;
/*      */     }
/* 1051 */     if (this.suppressedExceptions == SUPPRESSED_SENTINEL) {
/* 1052 */       this.suppressedExceptions = new ArrayList<>(1);
/*      */     }
/* 1054 */     this.suppressedExceptions.add(paramThrowable);
/*      */   }
/*      */   
/* 1057 */   private static final Throwable[] EMPTY_THROWABLE_ARRAY = new Throwable[0];
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
/*      */   public final synchronized Throwable[] getSuppressed() {
/* 1075 */     if (this.suppressedExceptions == SUPPRESSED_SENTINEL || this.suppressedExceptions == null)
/*      */     {
/* 1077 */       return EMPTY_THROWABLE_ARRAY;
/*      */     }
/* 1079 */     return this.suppressedExceptions.<Throwable>toArray(EMPTY_THROWABLE_ARRAY);
/*      */   }
/*      */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\lang\Throwable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */