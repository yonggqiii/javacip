// added by JavaCIP
public interface HttpEntity {

    public abstract InputStream getContent();

    public abstract void consumeContent();
}
