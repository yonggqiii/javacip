// added by JavaCIP
public interface DateTimeZone {

    public static DateTimeZone getDefault() {
        return null;
    }

    public abstract char getID();

    public abstract char getName(long arg0);
}
