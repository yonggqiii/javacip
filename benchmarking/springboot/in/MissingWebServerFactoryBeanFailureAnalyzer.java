import java.util.Locale;

class MissingWebServerFactoryBeanFailureAnalyzer extends AbstractFailureAnalyzer<MissingWebServerFactoryBeanException> {

	@Override
	public FailureAnalysis analyze(Throwable rootFailure, MissingWebServerFactoryBeanException cause) {
		return new FailureAnalysis(
				"Web application could not be started as there was no " + cause.getBeanType().getName()
						+ " bean defined in the context.",
				"Check your application's dependencies for a supported "
						+ cause.getWebApplicationType().name().toLowerCase(Locale.ENGLISH) + " web server.\n"
						+ "Check the configured web application type.",
				cause);
	}

}
