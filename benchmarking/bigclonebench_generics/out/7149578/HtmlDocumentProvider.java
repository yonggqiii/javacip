// added by JavaCIP
public interface HtmlDocumentProvider {

    public static String getMetaContent(HtmlNodeAbstract<?> arg0) {
        return null;
    }

    public abstract HtmlNodeAbstract<Object> getRootNode();

    public abstract String getMetaCharset();

    public abstract boolean getTitle();

    public abstract boolean getName();

    public abstract URL getBaseHref();

    public abstract Iterable<HtmlNodeAbstract<Object>> getMetas();

    public abstract String getMetaHttpEquiv(String arg0);
}
