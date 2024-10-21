import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ErrorPageRegistrarBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

	private ListableBeanFactory beanFactory;

	private List<ErrorPageRegistrar> registrars;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		Assert.isInstanceOf(ListableBeanFactory.class, beanFactory,
				"ErrorPageRegistrarBeanPostProcessor can only be used with a ListableBeanFactory");
		this.beanFactory = (ListableBeanFactory) beanFactory;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof ErrorPageRegistry) {
      ErrorPageRegistry errorPageRegistry = (ErrorPageRegistry) bean;
			postProcessBeforeInitialization(errorPageRegistry);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	private void postProcessBeforeInitialization(ErrorPageRegistry registry) {
		for (ErrorPageRegistrar registrar : getRegistrars()) {
			registrar.registerErrorPages(registry);
		}
	}

	private Collection<ErrorPageRegistrar> getRegistrars() {
		if (this.registrars == null) {
			this.registrars = new ArrayList<>(
					this.beanFactory.getBeansOfType(ErrorPageRegistrar.class, false, false).values());
			this.registrars.sort(AnnotationAwareOrderComparator.INSTANCE);
			this.registrars = Collections.unmodifiableList(this.registrars);
		}
		return this.registrars;
	}

}
