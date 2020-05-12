package javax.imageio;

import javax.imageio.metadata.IIOMetadata;

public interface ImageTranscoder {
  IIOMetadata convertStreamMetadata(IIOMetadata paramIIOMetadata, ImageWriteParam paramImageWriteParam);
  
  IIOMetadata convertImageMetadata(IIOMetadata paramIIOMetadata, ImageTypeSpecifier paramImageTypeSpecifier, ImageWriteParam paramImageWriteParam);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\imageio\ImageTranscoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */