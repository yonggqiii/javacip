// added by JavaCIP
public interface Calendar {

    public static boolean HOUR = false;

    public static Calendar getInstance() {
        return null;
    }

    public abstract void setTimeInMillis(long arg0);

    public abstract void add(boolean arg0, int arg1);

    public abstract boolean before(Calendar arg0);
}
