public class RootUriBuilderFactory extends RootUriTemplateHandler implements UriBuilderFactory {

    RootUriBuilderFactory(String rootUri) {
        super(rootUri);
    }

    RootUriBuilderFactory(String rootUri, UriTemplateHandler delegate) {
        super(rootUri, delegate);
    }

    public UriBuilder uriString(String uriTemplate) {
        return UriComponentsBuilder.fromUriString(JavaCIPUnknownScope.apply(uriTemplate));
    }

    public UriBuilder builder() {
        return UriComponentsBuilder.newInstance();
    }

    static void applyTo(RestTemplate restTemplate, String rootUri) {
        Assert.notNull(restTemplate, "RestTemplate must not be null");
        RootUriBuilderFactory handler = new RootUriBuilderFactory(rootUri, restTemplate.getUriTemplateHandler());
        restTemplate.setUriTemplateHandler(handler);
    }
}
