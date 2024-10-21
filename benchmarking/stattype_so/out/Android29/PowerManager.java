// added by JavaCIP
public interface PowerManager {

    public static boolean FULL_WAKE_LOCK = false;

    public abstract WakeLock newWakeLock(boolean arg0, String arg1);
}
