/*     */ package com.sun.image.codec.jpeg;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.Raster;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import sun.awt.image.codec.JPEGImageDecoderImpl;
/*     */ import sun.awt.image.codec.JPEGImageEncoderImpl;
/*     */ import sun.awt.image.codec.JPEGParam;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JPEGCodec
/*     */ {
/*     */   public static JPEGImageDecoder createJPEGDecoder(InputStream paramInputStream) {
/*  51 */     return new JPEGImageDecoderImpl(paramInputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JPEGImageDecoder createJPEGDecoder(InputStream paramInputStream, JPEGDecodeParam paramJPEGDecodeParam) {
/*  60 */     return new JPEGImageDecoderImpl(paramInputStream, paramJPEGDecodeParam);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JPEGImageEncoder createJPEGEncoder(OutputStream paramOutputStream) {
/*  68 */     return new JPEGImageEncoderImpl(paramOutputStream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JPEGImageEncoder createJPEGEncoder(OutputStream paramOutputStream, JPEGEncodeParam paramJPEGEncodeParam) {
/*  76 */     return new JPEGImageEncoderImpl(paramOutputStream, paramJPEGEncodeParam);
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
/*     */   public static JPEGEncodeParam getDefaultJPEGEncodeParam(BufferedImage paramBufferedImage) {
/*  88 */     int i = JPEGParam.getDefaultColorId(paramBufferedImage.getColorModel());
/*  89 */     return getDefaultJPEGEncodeParam(paramBufferedImage.getRaster(), i);
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
/*     */   public static JPEGEncodeParam getDefaultJPEGEncodeParam(Raster paramRaster, int paramInt) {
/* 106 */     JPEGParam jPEGParam = new JPEGParam(paramInt, paramRaster.getNumBands());
/* 107 */     jPEGParam.setWidth(paramRaster.getWidth());
/* 108 */     jPEGParam.setHeight(paramRaster.getHeight());
/*     */     
/* 110 */     return jPEGParam;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JPEGEncodeParam getDefaultJPEGEncodeParam(int paramInt1, int paramInt2) throws ImageFormatException {
/* 135 */     return new JPEGParam(paramInt2, paramInt1);
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
/*     */   public static JPEGEncodeParam getDefaultJPEGEncodeParam(JPEGDecodeParam paramJPEGDecodeParam) throws ImageFormatException {
/* 149 */     return new JPEGParam(paramJPEGDecodeParam);
/*     */   }
/*     */ }


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\image\codec\jpeg\JPEGCodec.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */