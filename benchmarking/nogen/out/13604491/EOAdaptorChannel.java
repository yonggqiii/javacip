// added by JavaCIP
public interface EOAdaptorChannel {

    public abstract boolean isOpen();

    public abstract void openChannel();

    public abstract JDBCContext adaptorContext();

    public abstract void closeChannel();
}
