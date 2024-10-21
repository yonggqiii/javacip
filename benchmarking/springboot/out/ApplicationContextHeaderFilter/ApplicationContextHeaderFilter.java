import java.io.IOException;

public class ApplicationContextHeaderFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "X-Application-Context";

    private final ApplicationContext applicationContext;

    public ApplicationContextHeaderFilter(ApplicationContext context) {
        this.applicationContext = context;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.addHeader(HEADER_NAME, this.applicationContext.getId());
        filterChain.doFilter(request, response);
    }
}
