// added by JavaCIP
public interface Sequence {

    public static Sequence EMPTY_SEQUENCE = null;

    public abstract void add(IntegerValue arg0);

    public abstract boolean isEmpty();

    public abstract SequenceIterator iterate();
}
