// added by JavaCIP
public interface TopicMapStoreIF {

    public abstract void setBaseAddress(LocatorIF arg0);

    public abstract TopicMapIF getTopicMap();

    public abstract void close();
}
