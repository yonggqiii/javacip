// added by JavaCIP
public interface PreparedStatement {

    public abstract void setTimestamp(int arg0, Timestamp arg1);

    public abstract void executeBatch();

    public abstract void setString(int arg0, boolean arg1);

    public abstract void setShort(int arg0, boolean arg1);

    public abstract void setLong(int arg0, long arg1);

    public abstract void close();

    public abstract void addBatch();
}
