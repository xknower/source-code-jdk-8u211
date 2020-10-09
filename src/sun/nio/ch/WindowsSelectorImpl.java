/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.ClosedSelectorException;
/*     */ import java.nio.channels.Pipe;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WindowsSelectorImpl
/*     */   extends SelectorImpl
/*     */ {
/*  53 */   private final int INIT_CAP = 8;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_SELECTABLE_FDS = 1024;
/*     */ 
/*     */ 
/*     */   
/*  61 */   private SelectionKeyImpl[] channelArray = new SelectionKeyImpl[8];
/*     */ 
/*     */ 
/*     */   
/*     */   private PollArrayWrapper pollWrapper;
/*     */ 
/*     */   
/*  68 */   private int totalChannels = 1;
/*     */ 
/*     */ 
/*     */   
/*  72 */   private int threadsCount = 0;
/*     */ 
/*     */   
/*  75 */   private final List<SelectThread> threads = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private final Pipe wakeupPipe;
/*     */   
/*     */   private final int wakeupSourceFd;
/*     */   
/*     */   private final int wakeupSinkFd;
/*     */   
/*  84 */   private Object closeLock = new Object();
/*     */   private static final class FdMap extends HashMap<Integer, MapEntry> { static final long serialVersionUID = 0L;
/*     */     
/*     */     private FdMap() {}
/*     */     
/*     */     private WindowsSelectorImpl.MapEntry get(int param1Int) {
/*  90 */       return get(new Integer(param1Int));
/*     */     }
/*     */     private WindowsSelectorImpl.MapEntry put(SelectionKeyImpl param1SelectionKeyImpl) {
/*  93 */       return put(new Integer(param1SelectionKeyImpl.channel.getFDVal()), new WindowsSelectorImpl.MapEntry(param1SelectionKeyImpl));
/*     */     }
/*     */     private WindowsSelectorImpl.MapEntry remove(SelectionKeyImpl param1SelectionKeyImpl) {
/*  96 */       Integer integer = new Integer(param1SelectionKeyImpl.channel.getFDVal());
/*  97 */       WindowsSelectorImpl.MapEntry mapEntry = get(integer);
/*  98 */       if (mapEntry != null && mapEntry.ski.channel == param1SelectionKeyImpl.channel)
/*  99 */         return remove(integer); 
/* 100 */       return null;
/*     */     } }
/*     */ 
/*     */   
/*     */   private static final class MapEntry
/*     */   {
/*     */     SelectionKeyImpl ski;
/* 107 */     long updateCount = 0L;
/* 108 */     long clearedCount = 0L;
/*     */     MapEntry(SelectionKeyImpl param1SelectionKeyImpl) {
/* 110 */       this.ski = param1SelectionKeyImpl;
/*     */     }
/*     */   }
/* 113 */   private final FdMap fdMap = new FdMap();
/*     */ 
/*     */   
/* 116 */   private final SubSelector subSelector = new SubSelector();
/*     */ 
/*     */   
/*     */   private long timeout;
/*     */   
/* 121 */   private final Object interruptLock = new Object(); private volatile boolean interruptTriggered = false; private final StartLock startLock;
/*     */   private final FinishLock finishLock;
/*     */   private long updateCount;
/*     */   
/* 125 */   WindowsSelectorImpl(SelectorProvider paramSelectorProvider) throws IOException { super(paramSelectorProvider);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     this.startLock = new StartLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     this.finishLock = new FinishLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 488 */     this.updateCount = 0L; this.pollWrapper = new PollArrayWrapper(8); this.wakeupPipe = Pipe.open(); this.wakeupSourceFd = ((SelChImpl)this.wakeupPipe.source()).getFDVal(); SinkChannelImpl sinkChannelImpl = (SinkChannelImpl)this.wakeupPipe.sink(); sinkChannelImpl.sc.socket().setTcpNoDelay(true); this.wakeupSinkFd = sinkChannelImpl.getFDVal(); this.pollWrapper.addWakeupSocket(this.wakeupSourceFd, 0); }
/*     */   protected int doSelect(long paramLong) throws IOException { if (this.channelArray == null) throw new ClosedSelectorException();  this.timeout = paramLong; processDeregisterQueue(); if (this.interruptTriggered) { resetWakeupSocket(); return 0; }  adjustThreadsCount(); this.finishLock.reset(); this.startLock.startThreads(); try { begin(); try { this.subSelector.poll(); } catch (IOException iOException) { this.finishLock.setException(iOException); }  if (this.threads.size() > 0) this.finishLock.waitForHelperThreads();  } finally { end(); }  this.finishLock.checkForException(); processDeregisterQueue(); int i = updateSelectedKeys(); resetWakeupSocket(); return i; }
/*     */   private final class StartLock {
/*     */     private long runsCounter; private StartLock() {} private synchronized void startThreads() { this.runsCounter++; notifyAll(); } private synchronized boolean waitForStart(WindowsSelectorImpl.SelectThread param1SelectThread) { while (this.runsCounter == param1SelectThread.lastRun) { try { WindowsSelectorImpl.this.startLock.wait(); } catch (InterruptedException interruptedException) { Thread.currentThread().interrupt(); }  }  if (param1SelectThread.isZombie()) return true;  param1SelectThread.lastRun = this.runsCounter; return false; } } private final class FinishLock {
/*     */     private int threadsToFinish; IOException exception; private FinishLock() { this.exception = null; } private void reset() { this.threadsToFinish = WindowsSelectorImpl.this.threads.size(); } private synchronized void threadFinished() { if (this.threadsToFinish == WindowsSelectorImpl.this.threads.size()) WindowsSelectorImpl.this.wakeup();  this.threadsToFinish--; if (this.threadsToFinish == 0) notify();  } private synchronized void waitForHelperThreads() { if (this.threadsToFinish == WindowsSelectorImpl.this.threads.size()) WindowsSelectorImpl.this.wakeup();  while (this.threadsToFinish != 0) { try { WindowsSelectorImpl.this.finishLock.wait(); } catch (InterruptedException interruptedException) { Thread.currentThread().interrupt(); }  }  } private synchronized void setException(IOException param1IOException) { this.exception = param1IOException; } private void checkForException() throws IOException { if (this.exception == null) return;  StringBuffer stringBuffer = new StringBuffer("An exception occurred during the execution of select(): \n"); stringBuffer.append(this.exception); stringBuffer.append('\n'); this.exception = null; throw new IOException(stringBuffer.toString()); } } private final class SubSelector {
/* 493 */     private final int pollArrayIndex; private final int[] readFds = new int[1025]; private final int[] writeFds = new int[1025]; private final int[] exceptFds = new int[1025]; private SubSelector() { this.pollArrayIndex = 0; } private SubSelector(int param1Int) { this.pollArrayIndex = (param1Int + 1) * 1024; } private int poll() throws IOException { return poll0(WindowsSelectorImpl.this.pollWrapper.pollArrayAddress, Math.min(WindowsSelectorImpl.this.totalChannels, 1024), this.readFds, this.writeFds, this.exceptFds, WindowsSelectorImpl.this.timeout); } private int poll(int param1Int) throws IOException { return poll0(WindowsSelectorImpl.this.pollWrapper.pollArrayAddress + (this.pollArrayIndex * PollArrayWrapper.SIZE_POLLFD), Math.min(1024, WindowsSelectorImpl.this.totalChannels - (param1Int + 1) * 1024), this.readFds, this.writeFds, this.exceptFds, WindowsSelectorImpl.this.timeout); } private int processSelectedKeys(long param1Long) { int i = 0; i += processFDSet(param1Long, this.readFds, Net.POLLIN, false); i += processFDSet(param1Long, this.writeFds, Net.POLLCONN | Net.POLLOUT, false); i += processFDSet(param1Long, this.exceptFds, Net.POLLIN | Net.POLLCONN | Net.POLLOUT, true); return i; } private int processFDSet(long param1Long, int[] param1ArrayOfint, int param1Int, boolean param1Boolean) { byte b1 = 0; for (byte b2 = 1; b2 <= param1ArrayOfint[0]; b2++) { int i = param1ArrayOfint[b2]; if (i == WindowsSelectorImpl.this.wakeupSourceFd) { synchronized (WindowsSelectorImpl.this.interruptLock) { WindowsSelectorImpl.this.interruptTriggered = true; }  } else { WindowsSelectorImpl.MapEntry mapEntry = WindowsSelectorImpl.this.fdMap.get(i); if (mapEntry != null) { SelectionKeyImpl selectionKeyImpl = mapEntry.ski; if (!param1Boolean || !(selectionKeyImpl.channel() instanceof SocketChannelImpl) || !WindowsSelectorImpl.this.discardUrgentData(i)) if (WindowsSelectorImpl.this.selectedKeys.contains(selectionKeyImpl)) { if (mapEntry.clearedCount != param1Long) { if (selectionKeyImpl.channel.translateAndSetReadyOps(param1Int, selectionKeyImpl) && mapEntry.updateCount != param1Long) { mapEntry.updateCount = param1Long; b1++; }  } else if (selectionKeyImpl.channel.translateAndUpdateReadyOps(param1Int, selectionKeyImpl) && mapEntry.updateCount != param1Long) { mapEntry.updateCount = param1Long; b1++; }  mapEntry.clearedCount = param1Long; } else { if (mapEntry.clearedCount != param1Long) { selectionKeyImpl.channel.translateAndSetReadyOps(param1Int, selectionKeyImpl); if ((selectionKeyImpl.nioReadyOps() & selectionKeyImpl.nioInterestOps()) != 0) { WindowsSelectorImpl.this.selectedKeys.add(selectionKeyImpl); mapEntry.updateCount = param1Long; b1++; }  } else { selectionKeyImpl.channel.translateAndUpdateReadyOps(param1Int, selectionKeyImpl); if ((selectionKeyImpl.nioReadyOps() & selectionKeyImpl.nioInterestOps()) != 0) { WindowsSelectorImpl.this.selectedKeys.add(selectionKeyImpl); mapEntry.updateCount = param1Long; b1++; }  }  mapEntry.clearedCount = param1Long; }   }  }  }  return b1; } private native int poll0(long param1Long1, int param1Int, int[] param1ArrayOfint1, int[] param1ArrayOfint2, int[] param1ArrayOfint3, long param1Long2); } private int updateSelectedKeys() { this.updateCount++;
/* 494 */     int i = 0;
/* 495 */     i += this.subSelector.processSelectedKeys(this.updateCount);
/* 496 */     for (SelectThread selectThread : this.threads) {
/* 497 */       i += selectThread.subSelector.processSelectedKeys(this.updateCount);
/*     */     }
/* 499 */     return i; }
/*     */   private final class SelectThread extends Thread {
/*     */     private final int index;
/*     */     final WindowsSelectorImpl.SubSelector subSelector;
/* 503 */     private long lastRun = 0L; private volatile boolean zombie; private SelectThread(int param1Int) { this.index = param1Int; this.subSelector = new WindowsSelectorImpl.SubSelector(param1Int); this.lastRun = WindowsSelectorImpl.this.startLock.runsCounter; } void makeZombie() { this.zombie = true; } boolean isZombie() { return this.zombie; } public void run() { while (true) { if (WindowsSelectorImpl.this.startLock.waitForStart(this)) return;  try { this.subSelector.poll(this.index); } catch (IOException iOException) { WindowsSelectorImpl.this.finishLock.setException(iOException); }  WindowsSelectorImpl.this.finishLock.threadFinished(); }  } } private void adjustThreadsCount() { if (this.threadsCount > this.threads.size()) { for (int i = this.threads.size(); i < this.threadsCount; i++) { SelectThread selectThread = new SelectThread(i); this.threads.add(selectThread); selectThread.setDaemon(true); selectThread.start(); }  } else if (this.threadsCount < this.threads.size()) { for (int i = this.threads.size() - 1; i >= this.threadsCount; i--) ((SelectThread)this.threads.remove(i)).makeZombie();  }  } private void setWakeupSocket() { setWakeupSocket0(this.wakeupSinkFd); } private void resetWakeupSocket() { synchronized (this.interruptLock) { if (!this.interruptTriggered) return;  resetWakeupSocket0(this.wakeupSourceFd); this.interruptTriggered = false; }  } protected void implClose() throws IOException { synchronized (this.closeLock) {
/* 504 */       if (this.channelArray != null && 
/* 505 */         this.pollWrapper != null) {
/*     */         
/* 507 */         synchronized (this.interruptLock) {
/* 508 */           this.interruptTriggered = true;
/*     */         } 
/* 510 */         this.wakeupPipe.sink().close();
/* 511 */         this.wakeupPipe.source().close();
/* 512 */         for (byte b = 1; b < this.totalChannels; b++) {
/* 513 */           if (b % 1024 != 0) {
/* 514 */             deregister(this.channelArray[b]);
/* 515 */             SelectableChannel selectableChannel = this.channelArray[b].channel();
/* 516 */             if (!selectableChannel.isOpen() && !selectableChannel.isRegistered())
/* 517 */               ((SelChImpl)selectableChannel).kill(); 
/*     */           } 
/*     */         } 
/* 520 */         this.pollWrapper.free();
/* 521 */         this.pollWrapper = null;
/* 522 */         this.selectedKeys = null;
/* 523 */         this.channelArray = null;
/*     */         
/* 525 */         for (SelectThread selectThread : this.threads)
/* 526 */           selectThread.makeZombie(); 
/* 527 */         this.startLock.startThreads();
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void implRegister(SelectionKeyImpl paramSelectionKeyImpl) {
/* 534 */     synchronized (this.closeLock) {
/* 535 */       if (this.pollWrapper == null)
/* 536 */         throw new ClosedSelectorException(); 
/* 537 */       growIfNeeded();
/* 538 */       this.channelArray[this.totalChannels] = paramSelectionKeyImpl;
/* 539 */       paramSelectionKeyImpl.setIndex(this.totalChannels);
/* 540 */       this.fdMap.put(paramSelectionKeyImpl);
/* 541 */       this.keys.add(paramSelectionKeyImpl);
/* 542 */       this.pollWrapper.addEntry(this.totalChannels, paramSelectionKeyImpl);
/* 543 */       this.totalChannels++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void growIfNeeded() {
/* 548 */     if (this.channelArray.length == this.totalChannels) {
/* 549 */       int i = this.totalChannels * 2;
/* 550 */       SelectionKeyImpl[] arrayOfSelectionKeyImpl = new SelectionKeyImpl[i];
/* 551 */       System.arraycopy(this.channelArray, 1, arrayOfSelectionKeyImpl, 1, this.totalChannels - 1);
/* 552 */       this.channelArray = arrayOfSelectionKeyImpl;
/* 553 */       this.pollWrapper.grow(i);
/*     */     } 
/* 555 */     if (this.totalChannels % 1024 == 0) {
/* 556 */       this.pollWrapper.addWakeupSocket(this.wakeupSourceFd, this.totalChannels);
/* 557 */       this.totalChannels++;
/* 558 */       this.threadsCount++;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void implDereg(SelectionKeyImpl paramSelectionKeyImpl) throws IOException {
/* 563 */     int i = paramSelectionKeyImpl.getIndex();
/* 564 */     assert i >= 0;
/* 565 */     synchronized (this.closeLock) {
/* 566 */       if (i != this.totalChannels - 1) {
/*     */         
/* 568 */         SelectionKeyImpl selectionKeyImpl = this.channelArray[this.totalChannels - 1];
/* 569 */         this.channelArray[i] = selectionKeyImpl;
/* 570 */         selectionKeyImpl.setIndex(i);
/* 571 */         this.pollWrapper.replaceEntry(this.pollWrapper, this.totalChannels - 1, this.pollWrapper, i);
/*     */       } 
/*     */       
/* 574 */       paramSelectionKeyImpl.setIndex(-1);
/*     */     } 
/* 576 */     this.channelArray[this.totalChannels - 1] = null;
/* 577 */     this.totalChannels--;
/* 578 */     if (this.totalChannels != 1 && this.totalChannels % 1024 == 1) {
/* 579 */       this.totalChannels--;
/* 580 */       this.threadsCount--;
/*     */     } 
/* 582 */     this.fdMap.remove(paramSelectionKeyImpl);
/* 583 */     this.keys.remove(paramSelectionKeyImpl);
/* 584 */     this.selectedKeys.remove(paramSelectionKeyImpl);
/* 585 */     deregister(paramSelectionKeyImpl);
/* 586 */     SelectableChannel selectableChannel = paramSelectionKeyImpl.channel();
/* 587 */     if (!selectableChannel.isOpen() && !selectableChannel.isRegistered())
/* 588 */       ((SelChImpl)selectableChannel).kill(); 
/*     */   }
/*     */   
/*     */   public void putEventOps(SelectionKeyImpl paramSelectionKeyImpl, int paramInt) {
/* 592 */     synchronized (this.closeLock) {
/* 593 */       if (this.pollWrapper == null) {
/* 594 */         throw new ClosedSelectorException();
/*     */       }
/* 596 */       int i = paramSelectionKeyImpl.getIndex();
/* 597 */       if (i == -1)
/* 598 */         throw new CancelledKeyException(); 
/* 599 */       this.pollWrapper.putEventOps(i, paramInt);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Selector wakeup() {
/* 604 */     synchronized (this.interruptLock) {
/* 605 */       if (!this.interruptTriggered) {
/* 606 */         setWakeupSocket();
/* 607 */         this.interruptTriggered = true;
/*     */       } 
/*     */     } 
/* 610 */     return this;
/*     */   }
/*     */   
/*     */   static {
/* 614 */     IOUtil.load();
/*     */   }
/*     */   
/*     */   private native void setWakeupSocket0(int paramInt);
/*     */   
/*     */   private native void resetWakeupSocket0(int paramInt);
/*     */   
/*     */   private native boolean discardUrgentData(int paramInt);
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\sun\nio\ch\WindowsSelectorImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */