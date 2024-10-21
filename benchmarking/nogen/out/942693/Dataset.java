// added by JavaCIP
public interface Dataset {

    public abstract void putUS(boolean arg0, int arg1);

    public abstract void putCS(boolean arg0, String arg1);

    public abstract void writeFile(OutputStream arg0, boolean arg1);

    public abstract byte getInt(boolean arg0, int arg1);

    public abstract boolean contains(boolean arg0);

    public abstract boolean getDcmHandler();

    public abstract void setFileMetaInfo(boolean arg0);

    public abstract void putUI(boolean arg0, boolean arg1);
}
