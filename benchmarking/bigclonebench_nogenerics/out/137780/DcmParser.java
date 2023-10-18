// added by JavaCIP
public interface DcmParser {

    public abstract void setDcmHandler(boolean arg0);

    public abstract boolean getReadVR();

    public abstract UNKNOWN_183 getDcmDecodeParam();

    public abstract boolean getReadLength();

    public abstract void parseDcmFile(Object arg0, boolean arg1);
}
