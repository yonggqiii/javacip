import java.util.List;
import java.util.Map;
import java.util.Set;

class RestTemplateBuilderClientHttpRequestInitializer implements ClientHttpRequestInitializer {

    private final BasicAuthentication basicAuthentication;

    private final Map<String, List<String>> defaultHeaders;

    private final Set<RestTemplateRequestCustomizer<?>> requestCustomizers;

    RestTemplateBuilderClientHttpRequestInitializer(BasicAuthentication basicAuthentication, Map<String, List<String>> defaultHeaders, Set<RestTemplateRequestCustomizer<?>> requestCustomizers) {
        this.basicAuthentication = basicAuthentication;
        this.defaultHeaders = defaultHeaders;
        this.requestCustomizers = requestCustomizers;
    }

    public void initialize(ClientHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        if (this.basicAuthentication != null) {
            this.basicAuthentication.applyTo(headers);
        }
    }
}
