// added by JavaCIP
public interface PreparedStatement {

    public abstract void setString(int arg0, boolean arg1);

    public abstract void setInt(int arg0, boolean arg1);

    public abstract void setNull(int arg0, boolean arg1);

    public abstract void executeUpdate();

    public abstract void setBoolean(int arg0, boolean arg1);
}
