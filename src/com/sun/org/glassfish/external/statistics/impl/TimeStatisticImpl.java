/*     */ package com.sun.org.glassfish.external.statistics.impl;
/*     */ 
/*     */ import com.sun.org.glassfish.external.statistics.TimeStatistic;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TimeStatisticImpl
/*     */   extends StatisticImpl
/*     */   implements TimeStatistic, InvocationHandler
/*     */ {
/*  40 */   private long count = 0L;
/*  41 */   private long maxTime = 0L;
/*  42 */   private long minTime = 0L;
/*  43 */   private long totTime = 0L;
/*     */   
/*     */   private final long initCount;
/*     */   
/*     */   private final long initMaxTime;
/*     */   private final long initMinTime;
/*     */   private final long initTotTime;
/*  50 */   private final TimeStatistic ts = (TimeStatistic)Proxy.newProxyInstance(TimeStatistic.class
/*  51 */       .getClassLoader(), new Class[] { TimeStatistic.class }, this);
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized String toString() {
/*  56 */     return super.toString() + NEWLINE + "Count: " + 
/*  57 */       getCount() + NEWLINE + "MinTime: " + 
/*  58 */       getMinTime() + NEWLINE + "MaxTime: " + 
/*  59 */       getMaxTime() + NEWLINE + "TotalTime: " + 
/*  60 */       getTotalTime();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeStatisticImpl(long counter, long maximumTime, long minimumTime, long totalTime, String name, String unit, String desc, long startTime, long sampleTime) {
/*  66 */     super(name, unit, desc, startTime, sampleTime);
/*  67 */     this.count = counter;
/*  68 */     this.initCount = counter;
/*  69 */     this.maxTime = maximumTime;
/*  70 */     this.initMaxTime = maximumTime;
/*  71 */     this.minTime = minimumTime;
/*  72 */     this.initMinTime = minimumTime;
/*  73 */     this.totTime = totalTime;
/*  74 */     this.initTotTime = totalTime;
/*     */   }
/*     */   
/*     */   public synchronized TimeStatistic getStatistic() {
/*  78 */     return this.ts;
/*     */   }
/*     */   
/*     */   public synchronized Map getStaticAsMap() {
/*  82 */     Map<String, Long> m = super.getStaticAsMap();
/*  83 */     m.put("count", Long.valueOf(getCount()));
/*  84 */     m.put("maxtime", Long.valueOf(getMaxTime()));
/*  85 */     m.put("mintime", Long.valueOf(getMinTime()));
/*  86 */     m.put("totaltime", Long.valueOf(getTotalTime()));
/*  87 */     return m;
/*     */   }
/*     */   
/*     */   public synchronized void incrementCount(long current) {
/*  91 */     if (this.count == 0L) {
/*  92 */       this.totTime = current;
/*  93 */       this.maxTime = current;
/*  94 */       this.minTime = current;
/*     */     } else {
/*  96 */       this.totTime += current;
/*  97 */       this.maxTime = (current >= this.maxTime) ? current : this.maxTime;
/*  98 */       this.minTime = (current >= this.minTime) ? this.minTime : current;
/*     */     } 
/* 100 */     this.count++;
/* 101 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getCount() {
/* 108 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getMaxTime() {
/* 116 */     return this.maxTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getMinTime() {
/* 124 */     return this.minTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getTotalTime() {
/* 132 */     return this.totTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void reset() {
/* 137 */     super.reset();
/* 138 */     this.count = this.initCount;
/* 139 */     this.maxTime = this.initMaxTime;
/* 140 */     this.minTime = this.initMinTime;
/* 141 */     this.totTime = this.initTotTime;
/* 142 */     this.sampleTime = -1L;
/*     */   }
/*     */   
/*     */   public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
/*     */     Object result;
/* 147 */     checkMethod(m);
/*     */ 
/*     */     
/*     */     try {
/* 151 */       result = m.invoke(this, args);
/* 152 */     } catch (InvocationTargetException e) {
/* 153 */       throw e.getTargetException();
/* 154 */     } catch (Exception e) {
/* 155 */       throw new RuntimeException("unexpected invocation exception: " + e
/* 156 */           .getMessage());
/*     */     } 
/* 158 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\org\glassfish\external\statistics\impl\TimeStatisticImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */