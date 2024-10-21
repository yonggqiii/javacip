// added by JavaCIP
public interface ResultSet {

    public abstract boolean next();

    public abstract boolean getInt(String arg0);

    public abstract boolean getBinaryStream(String arg0);

    public abstract void close();
}
