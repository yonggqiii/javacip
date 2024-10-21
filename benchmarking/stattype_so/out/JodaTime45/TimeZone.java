// added by JavaCIP
public interface TimeZone {

    public static TimeZone getTimeZone(String arg0) {
        return null;
    }

    public abstract int getRawOffset();

    public abstract boolean inDaylightTime(Date arg0);

    public abstract byte getDSTSavings();
}
