// added by JavaCIP
public interface IProgress {

    public abstract void start();

    public abstract void setScale(int arg0);

    public abstract IProgress getSub(int arg0);

    public abstract void done();
}
