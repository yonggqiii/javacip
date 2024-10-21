// added by JavaCIP
public interface FileChannel {

    public abstract double position();

    public abstract void position(double arg0);

    public abstract double size();

    public abstract void close();

    public abstract void transferTo(double arg0, int arg1, FileChannel arg2);
}
