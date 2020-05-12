package org.omg.IOP;

import org.omg.IOP.CodecFactoryPackage.UnknownEncoding;

public interface CodecFactoryOperations {
  Codec create_codec(Encoding paramEncoding) throws UnknownEncoding;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\org\omg\IOP\CodecFactoryOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */