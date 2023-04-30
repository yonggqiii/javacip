// added by JavaCIP
public interface HtmlNodeAbstract<A> {

    public abstract String getAttributeText(String arg0);

    public abstract List<HtmlNodeAbstract<?>> getAllNodes(String arg0, String arg1);

    public abstract String getNodeName();

    public abstract List<HtmlNodeAbstract<?>> getNodes(String arg0, String arg1);

    public abstract List<HtmlNodeAbstract<?>> getNodes(String arg0);

    public abstract List<HtmlNodeAbstract<?>> getNodes(String[] arg0);
}
