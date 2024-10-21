// added by JavaCIP
public interface DcmParser {

    public abstract void setDcmHandler(boolean arg0);

    public abstract FileFormat detectFileFormat();

    public abstract int getReadLength();

    public abstract void parseDcmFile(FileFormat arg0, boolean arg1);

    public abstract boolean getReadTag();
}
