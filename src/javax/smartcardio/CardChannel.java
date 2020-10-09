package javax.smartcardio;

import java.nio.ByteBuffer;

public abstract class CardChannel {
  public abstract Card getCard();
  
  public abstract int getChannelNumber();
  
  public abstract ResponseAPDU transmit(CommandAPDU paramCommandAPDU) throws CardException;
  
  public abstract int transmit(ByteBuffer paramByteBuffer1, ByteBuffer paramByteBuffer2) throws CardException;
  
  public abstract void close() throws CardException;
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\javax\smartcardio\CardChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */