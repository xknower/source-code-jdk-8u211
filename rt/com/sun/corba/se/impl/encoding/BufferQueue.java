/*    */ package com.sun.corba.se.impl.encoding;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BufferQueue
/*    */ {
/* 37 */   private LinkedList list = new LinkedList();
/*    */ 
/*    */   
/*    */   public void enqueue(ByteBufferWithInfo paramByteBufferWithInfo) {
/* 41 */     this.list.addLast(paramByteBufferWithInfo);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBufferWithInfo dequeue() throws NoSuchElementException {
/* 46 */     return this.list.removeFirst();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 51 */     return this.list.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void push(ByteBufferWithInfo paramByteBufferWithInfo) {
/* 58 */     this.list.addFirst(paramByteBufferWithInfo);
/*    */   }
/*    */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\corba\se\impl\encoding\BufferQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */