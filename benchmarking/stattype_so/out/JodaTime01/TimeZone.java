// added by JavaCIP
public interface TimeZone {

    public static TimeZone getDefault() {
        return null;
    }

    public abstract Object getID();

    public abstract int getDisplayName();
}
