// added by JavaCIP
public interface Cursor {

    public abstract String getString(boolean arg0);

    public abstract void moveToFirst();

    public abstract boolean getColumnIndex(String arg0);

    public abstract byte getCount();

    public abstract void close();
}
