// added by JavaCIP
public interface Session {

    public static Session getDefaultInstance(Properties arg0) {
        return null;
    }

    public abstract Store getStore(URLName arg0);
}
