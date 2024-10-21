// added by JavaCIP
public interface PreparedStatement {

    public abstract void setInt(int arg0, boolean arg1);

    public abstract void setInt(int arg0, Integer arg1);

    public abstract void setBigDecimal(int arg0, BigDecimal arg1);

    public abstract void setDate(int arg0, boolean arg1);

    public abstract void executeUpdate();
}
