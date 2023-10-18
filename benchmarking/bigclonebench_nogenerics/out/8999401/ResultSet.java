// added by JavaCIP
public interface ResultSet {

    public abstract boolean next();

    public abstract boolean getInt(int arg0);

    public abstract boolean getString(int arg0);

    public abstract void close();
}
