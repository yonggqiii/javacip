@FunctionalInterface
public interface ServletContextInitializer {

	void onStartup(ServletContext servletContext) throws ServletException;

}
