// added by JavaCIP
public interface Builder {

    public abstract void setDirectBuffers(Boolean arg0);

    public abstract void addHttpListener(int arg0, CharSequence arg1);

    public abstract void setBufferSize(Integer arg0);

    public abstract void setServerOption(boolean arg0, boolean arg1);

    public abstract void setServerOption(boolean arg0, int arg1);

    public abstract void setIoThreads(Integer arg0);

    public abstract void setWorkerThreads(Integer arg0);
}
