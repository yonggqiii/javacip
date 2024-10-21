import java.net.URI;
import java.util.Map;
import java.util.function.Function;

public class RootUriTemplateHandler implements UriTemplateHandler {

    private final String rootUri;

    private final UriTemplateHandler handler;

    protected RootUriTemplateHandler(UriTemplateHandler handler) {
        Assert.notNull(handler, "Handler must not be null");
        this.rootUri = null;
        this.handler = handler;
    }

    public RootUriTemplateHandler(String rootUri) {
        this(rootUri, new DefaultUriBuilderFactory());
    }

    public RootUriTemplateHandler(String rootUri, UriTemplateHandler handler) {
        Assert.notNull(rootUri, "RootUri must not be null");
        Assert.notNull(handler, "Handler must not be null");
        this.rootUri = rootUri;
        this.handler = handler;
    }

    public URI expand(String uriTemplate, Map<String, ?> uriVariables) {
        return this.handler.expand(apply(uriTemplate), uriVariables);
    }

    public URI expand(String uriTemplate, Object[] uriVariables) {
        return this.handler.expand(apply(uriTemplate), uriVariables);
    }

    String apply(String uriTemplate) {
        if (StringUtils.startsWithIgnoreCase(uriTemplate, "/")) {
            return getRootUri() + uriTemplate;
        }
        return uriTemplate;
    }

    public String getRootUri() {
        return this.rootUri;
    }

    public RootUriTemplateHandler withHandlerWrapper(Function<UriTemplateHandler, UriTemplateHandler> wrapper) {
        return new RootUriTemplateHandler(getRootUri(), wrapper.apply(this.handler));
    }

    public static RootUriTemplateHandler addTo(RestTemplate restTemplate, String rootUri) {
        Assert.notNull(restTemplate, "RestTemplate must not be null");
        RootUriTemplateHandler handler = new RootUriTemplateHandler(rootUri, restTemplate.getUriTemplateHandler());
        restTemplate.setUriTemplateHandler(handler);
        return handler;
    }
}
