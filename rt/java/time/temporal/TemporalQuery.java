package java.time.temporal;

@FunctionalInterface
public interface TemporalQuery<R> {
  R queryFrom(TemporalAccessor paramTemporalAccessor);
}


/* Location:              D:\tools\env\Java\jdk1.8.0_211\rt.jar!\java\time\temporal\TemporalQuery.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */