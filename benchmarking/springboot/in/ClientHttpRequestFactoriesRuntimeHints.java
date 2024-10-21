import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;

class ClientHttpRequestFactoriesRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		if (ClassUtils.isPresent("org.springframework.http.client.ClientHttpRequestFactory", classLoader)) {
			registerHints(hints.reflection(), classLoader);
		}
	}

	private void registerHints(ReflectionHints hints, ClassLoader classLoader) {
		hints.registerField(findField(AbstractClientHttpRequestFactoryWrapper.class, "requestFactory"));
		registerOkHttpHints(hints, classLoader);
	}

	private void registerOkHttpHints(ReflectionHints hints, ClassLoader classLoader) {
	}

	private void registerReflectionHints(ReflectionHints hints,
			Class<? extends ClientHttpRequestFactory> requestFactoryType) {
		registerReflectionHints(hints, requestFactoryType, int.class);
	}

	private void registerReflectionHints(ReflectionHints hints,
			Class<? extends ClientHttpRequestFactory> requestFactoryType, Class<?> readTimeoutType) {
		registerMethod(hints, requestFactoryType, "setConnectTimeout", int.class);
		registerMethod(hints, requestFactoryType, "setReadTimeout", readTimeoutType);
	}

	private void registerMethod(ReflectionHints hints, Class<? extends ClientHttpRequestFactory> requestFactoryType,
			String methodName, Class<?>... parameterTypes) {
		Method method = ReflectionUtils.findMethod(requestFactoryType, methodName, parameterTypes);
		if (method != null) {
			hints.registerMethod(method, ExecutableMode.INVOKE);
		}
	}

	private Field findField(Class<?> type, String name) {
		Field field = ReflectionUtils.findField(type, name);
		return field;
	}

}
