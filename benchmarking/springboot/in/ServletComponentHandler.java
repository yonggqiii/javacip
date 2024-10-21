import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

abstract class ServletComponentHandler {

	private final Class<? extends Annotation> annotationType;

	private final TypeFilter typeFilter;

	protected ServletComponentHandler(Class<? extends Annotation> annotationType) {
		this.typeFilter = new AnnotationTypeFilter(annotationType);
		this.annotationType = annotationType;
	}

	TypeFilter getTypeFilter() {
		return this.typeFilter;
	}

	protected String[] extractUrlPatterns(Map<String, Object> attributes) {
		String[] value = (String[]) attributes.get("value");
		String[] urlPatterns = (String[]) attributes.get("urlPatterns");
		if (urlPatterns.length > 0) {
			Assert.state(value.length == 0, "The urlPatterns and value attributes are mutually exclusive.");
			return urlPatterns;
		}
		return value;
	}

	protected final Map<String, String> extractInitParameters(Map<String, Object> attributes) {
		Map<String, String> initParameters = new HashMap<>();
		for (AnnotationAttributes initParam : (AnnotationAttributes[]) attributes.get("initParams")) {
			String name = (String) initParam.get("name");
			String value = (String) initParam.get("value");
			initParameters.put(name, value);
		}
		return initParameters;
	}

	void handle(AnnotatedBeanDefinition beanDefinition, BeanDefinitionRegistry registry) {
		Map<String, Object> attributes = beanDefinition.getMetadata()
			.getAnnotationAttributes(this.annotationType.getName());
		if (attributes != null) {
			doHandle(attributes, beanDefinition, registry);
		}
	}

	protected abstract void doHandle(Map<String, Object> attributes, AnnotatedBeanDefinition beanDefinition,
			BeanDefinitionRegistry registry);

}
