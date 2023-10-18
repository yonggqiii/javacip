// added by JavaCIP
public interface JobResources {

    public abstract Collection<Long> getUniqIds();

    public abstract boolean getBaseDir();

    public abstract File getSwissprotFile(Long arg0);
}
