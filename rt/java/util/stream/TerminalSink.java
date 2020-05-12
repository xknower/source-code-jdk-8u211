package java.util.stream;

import java.util.function.Supplier;

interface TerminalSink<T, R> extends Sink<T>, Supplier<R> {}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\jav\\util\stream\TerminalSink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */