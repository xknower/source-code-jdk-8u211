package com.sun.image.codec.jpeg;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;
import java.io.InputStream;

public interface JPEGImageDecoder {
  JPEGDecodeParam getJPEGDecodeParam();
  
  void setJPEGDecodeParam(JPEGDecodeParam paramJPEGDecodeParam);
  
  InputStream getInputStream();
  
  Raster decodeAsRaster() throws IOException, ImageFormatException;
  
  BufferedImage decodeAsBufferedImage() throws IOException, ImageFormatException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\com\sun\image\codec\jpeg\JPEGImageDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */