// added by JavaCIP
public interface AbstractHttp11Protocol<A> {

    public abstract void setCompression(String arg0);

    public abstract void setCompressionMinSize(int arg0);

    public abstract void setCompressibleMimeType(String arg0);

    public abstract void setNoCompressionUserAgents(String arg0);
}
