// added by JavaCIP
public interface Dataset {

    public abstract boolean getDcmHandler();

    public abstract void writeDataset(ImageOutputStream arg0, DcmEncodeParam arg1);

    public abstract void writeHeader(ImageOutputStream arg0, DcmEncodeParam arg1, boolean arg2, boolean arg3, boolean arg4);
}
