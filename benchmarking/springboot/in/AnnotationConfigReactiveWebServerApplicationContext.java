import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class AnnotationConfigReactiveWebServerApplicationContext extends ReactiveWebServerApplicationContext
		implements AnnotationConfigRegistry {

	private final AnnotatedBeanDefinitionReader reader;

	private final ClassPathBeanDefinitionScanner scanner;

	private final Set<Class<?>> annotatedClasses = new LinkedHashSet<>();

	private String[] basePackages;

	public AnnotationConfigReactiveWebServerApplicationContext() {
		this.reader = new AnnotatedBeanDefinitionReader(this);
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}

	public AnnotationConfigReactiveWebServerApplicationContext(DefaultListableBeanFactory beanFactory) {
		super(beanFactory);
		this.reader = new AnnotatedBeanDefinitionReader(this);
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}

	public AnnotationConfigReactiveWebServerApplicationContext(Class<?>[] annotatedClasses) {
		this();
		register(annotatedClasses);
		refresh();
	}

	public AnnotationConfigReactiveWebServerApplicationContext(String[] basePackages) {
		this();
		scan(basePackages);
		refresh();
	}

	@Override
	public void setEnvironment(ConfigurableEnvironment environment) {
		super.setEnvironment(environment);
		this.reader.setEnvironment(environment);
		this.scanner.setEnvironment(environment);
	}

	public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
		this.reader.setBeanNameGenerator(beanNameGenerator);
		this.scanner.setBeanNameGenerator(beanNameGenerator);
		getBeanFactory().registerSingleton(AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR, beanNameGenerator);
	}

	public void setScopeMetadataResolver(ScopeMetadataResolver scopeMetadataResolver) {
		this.reader.setScopeMetadataResolver(scopeMetadataResolver);
		this.scanner.setScopeMetadataResolver(scopeMetadataResolver);
	}

	@Override
	public final void register(Class<?>[] a) {
		Assert.notEmpty(a, "At least one annotated class must be specified");
    for (Class<?> c : a)
      this.annotatedClasses.add(c);
	}

	@Override
	public final void scan(String[] b) {
		Assert.notEmpty(b, "At least one base package must be specified");
		this.basePackages = b;
	}

	@Override
	public void prepareRefresh() {
		this.scanner.clearCache();
		super.prepareRefresh();
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		super.postProcessBeanFactory(beanFactory);
		if (!ObjectUtils.isEmpty(this.basePackages)) {
			this.scanner.scan(this.basePackages);
		}
		if (!this.annotatedClasses.isEmpty()) {
			this.reader.register(ClassUtils.toClassArray(this.annotatedClasses));
		}
	}

}
