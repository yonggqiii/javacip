// added by JavaCIP
public interface Fragment {

    public static Fragment EMPTY = null;

    public abstract Fragment plus(String arg0);

    public abstract Fragment plus(String arg0, String arg1, String arg2);

    public abstract Fragment plus(Fragment arg0, Fragment arg1);
}
