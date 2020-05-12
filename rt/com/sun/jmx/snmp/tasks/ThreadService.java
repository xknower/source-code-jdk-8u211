/*     */ package com.sun.jmx.snmp.tasks;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ThreadService
/*     */   implements TaskServer
/*     */ {
/*     */   public ThreadService(int paramInt) {
/*  41 */     if (paramInt <= 0) {
/*  42 */       throw new IllegalArgumentException("The thread number should bigger than zero.");
/*     */     }
/*     */     
/*  45 */     this.minThreads = paramInt;
/*  46 */     this.threadList = new ExecutorThread[paramInt];
/*     */     
/*  48 */     this.priority = Thread.currentThread().getPriority();
/*  49 */     this.cloader = Thread.currentThread().getContextClassLoader();
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
/*     */   public void submitTask(Task paramTask) throws IllegalArgumentException {
/*  67 */     submitTask(paramTask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void submitTask(Runnable paramRunnable) throws IllegalArgumentException {
/*  78 */     stateCheck();
/*     */     
/*  80 */     if (paramRunnable == null) {
/*  81 */       throw new IllegalArgumentException("No task specified.");
/*     */     }
/*     */     
/*  84 */     synchronized (this.jobList) {
/*  85 */       this.jobList.add(this.jobList.size(), paramRunnable);
/*     */       
/*  87 */       this.jobList.notify();
/*     */     } 
/*     */     
/*  90 */     createThread();
/*     */   }
/*     */   
/*     */   public Runnable removeTask(Runnable paramRunnable) {
/*  94 */     stateCheck();
/*     */     
/*  96 */     Runnable runnable = null;
/*  97 */     synchronized (this.jobList) {
/*  98 */       int i = this.jobList.indexOf(paramRunnable);
/*  99 */       if (i >= 0) {
/* 100 */         runnable = this.jobList.remove(i);
/*     */       }
/*     */     } 
/* 103 */     if (runnable != null && runnable instanceof Task)
/* 104 */       ((Task)runnable).cancel(); 
/* 105 */     return runnable;
/*     */   }
/*     */   public void removeAll() {
/*     */     Object[] arrayOfObject;
/* 109 */     stateCheck();
/*     */ 
/*     */     
/* 112 */     synchronized (this.jobList) {
/* 113 */       arrayOfObject = this.jobList.toArray();
/* 114 */       this.jobList.clear();
/*     */     } 
/* 116 */     int i = arrayOfObject.length;
/* 117 */     for (byte b = 0; b < i; b++) {
/* 118 */       Object object = arrayOfObject[b];
/* 119 */       if (object != null && object instanceof Task) ((Task)object).cancel();
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void terminate() {
/* 126 */     if (this.terminated == true) {
/*     */       return;
/*     */     }
/*     */     
/* 130 */     this.terminated = true;
/*     */     
/* 132 */     synchronized (this.jobList) {
/* 133 */       this.jobList.notifyAll();
/*     */     } 
/*     */     
/* 136 */     removeAll();
/*     */     
/* 138 */     for (byte b = 0; b < this.currThreds; b++) {
/*     */       try {
/* 140 */         this.threadList[b].interrupt();
/* 141 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 146 */     this.threadList = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ExecutorThread
/*     */     extends Thread
/*     */   {
/*     */     public ExecutorThread() {
/* 156 */       super(ThreadService.this.threadGroup, "ThreadService-" + ThreadService.counter++);
/* 157 */       setDaemon(true);
/*     */ 
/*     */       
/* 160 */       setPriority(ThreadService.this.priority);
/* 161 */       setContextClassLoader(ThreadService.this.cloader);
/*     */       
/* 163 */       ThreadService.this.idle++;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 168 */       while (!ThreadService.this.terminated) {
/* 169 */         Runnable runnable = null;
/*     */         
/* 171 */         synchronized (ThreadService.this.jobList) {
/* 172 */           if (ThreadService.this.jobList.size() > 0) {
/* 173 */             runnable = ThreadService.this.jobList.remove(0);
/* 174 */             if (ThreadService.this.jobList.size() > 0) {
/* 175 */               ThreadService.this.jobList.notify();
/*     */             }
/*     */           } else {
/*     */             
/*     */             try {
/* 180 */               ThreadService.this.jobList.wait();
/* 181 */             } catch (InterruptedException interruptedException) {
/*     */             
/*     */             } finally {}
/*     */             
/*     */             continue;
/*     */           } 
/*     */         } 
/* 188 */         if (runnable != null) {
/*     */           try {
/* 190 */             ThreadService.this.idle--;
/* 191 */             runnable.run();
/* 192 */           } catch (Exception exception) {
/*     */             
/* 194 */             exception.printStackTrace();
/*     */           } finally {
/* 196 */             ThreadService.this.idle++;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 201 */         setPriority(ThreadService.this.priority);
/* 202 */         Thread.interrupted();
/* 203 */         setContextClassLoader(ThreadService.this.cloader);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void stateCheck() throws IllegalStateException {
/* 210 */     if (this.terminated) {
/* 211 */       throw new IllegalStateException("The thread service has been terminated.");
/*     */     }
/*     */   }
/*     */   
/*     */   private void createThread() {
/* 216 */     if (this.idle < 1) {
/* 217 */       synchronized (this.threadList) {
/* 218 */         if (this.jobList.size() > 0 && this.currThreds < this.minThreads) {
/* 219 */           ExecutorThread executorThread = new ExecutorThread();
/* 220 */           executorThread.start();
/* 221 */           this.threadList[this.currThreds++] = executorThread;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 230 */   private ArrayList<Runnable> jobList = new ArrayList<>(0);
/*     */   
/*     */   private ExecutorThread[] threadList;
/* 233 */   private int minThreads = 1;
/* 234 */   private int currThreds = 0;
/* 235 */   private int idle = 0;
/*     */   
/*     */   private boolean terminated = false;
/*     */   private int priority;
/* 239 */   private ThreadGroup threadGroup = new ThreadGroup("ThreadService");
/*     */   
/*     */   private ClassLoader cloader;
/* 242 */   private static long counter = 0L;
/*     */   
/* 244 */   private int addedJobs = 1;
/* 245 */   private int doneJobs = 1;
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\jmx\snmp\tasks\ThreadService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */