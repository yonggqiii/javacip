import java.util.Map;

class WebServletHandler extends ServletComponentHandler {

	WebServletHandler() {
		super(WebServlet.class);
	}

	@Override
	public void doHandle(Map<String, Object> attributes, AnnotatedBeanDefinition beanDefinition,
			BeanDefinitionRegistry registry) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ServletRegistrationBean.class);
		builder.addPropertyValue("asyncSupported", attributes.get("asyncSupported"));
		builder.addPropertyValue("initParameters", extractInitParameters(attributes));
		builder.addPropertyValue("loadOnStartup", attributes.get("loadOnStartup"));
		String name = determineName(attributes, beanDefinition);
		builder.addPropertyValue("name", name);
		builder.addPropertyValue("servlet", beanDefinition);
		builder.addPropertyValue("urlMappings", extractUrlPatterns(attributes));
		builder.addPropertyValue("multipartConfig", determineMultipartConfig(beanDefinition));
		registry.registerBeanDefinition(name, builder.getBeanDefinition());
	}

	private String determineName(Map<String, Object> attributes, BeanDefinition beanDefinition) {
		return (String) (StringUtils.hasText((String) attributes.get("name")) ? attributes.get("name")
				: beanDefinition.getBeanClassName());
	}

	private BeanDefinition determineMultipartConfig(AnnotatedBeanDefinition beanDefinition) {
		Map<String, Object> attributes = beanDefinition.getMetadata()
			.getAnnotationAttributes(MultipartConfig.class.getName());
		if (attributes == null) {
			return null;
		}
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(MultipartConfigElement.class);
		builder.addConstructorArgValue(attributes.get("location"));
		builder.addConstructorArgValue(attributes.get("maxFileSize"));
		builder.addConstructorArgValue(attributes.get("maxRequestSize"));
		builder.addConstructorArgValue(attributes.get("fileSizeThreshold"));
		return builder.getBeanDefinition();
	}

}
