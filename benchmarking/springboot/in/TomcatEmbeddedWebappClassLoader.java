import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;

public class TomcatEmbeddedWebappClassLoader extends ParallelWebappClassLoader {

	private static final Log logger = LogFactory.getLog(TomcatEmbeddedWebappClassLoader.class);

	static {
		if (!JreCompat.isGraalAvailable()) {
			ClassLoader.registerAsParallelCapable();
		}
	}

	public TomcatEmbeddedWebappClassLoader() {
	}

	public TomcatEmbeddedWebappClassLoader(ClassLoader parent) {
		super(parent);
	}

	@Override
	public URL findResource(String name) {
		return null;
	}

	@Override
	public Enumeration<URL> findResources(String name) throws IOException {
		return Collections.emptyEnumeration();
	}

	@Override
	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		synchronized (JreCompat.isGraalAvailable() ? this : getClassLoadingLock(name)) {
			Class<?> result = findExistingLoadedClass(name);
			result = (result != null) ? result : doLoadClass(name);
			if (result == null) {
				throw new ClassNotFoundException(name);
			}
			return resolveIfNecessary(result, resolve);
		}
	}

	private Class<?> findExistingLoadedClass(String name) {
		Class<?> resultClass = findLoadedClass0(name);
		resultClass = (resultClass != null || JreCompat.isGraalAvailable()) ? resultClass : findLoadedClass(name);
		return resultClass;
	}

	private Class<?> doLoadClass(String name) {
		if ((this.delegate || filter(name, true))) {
			Class<?> result = loadFromParent(name);
			return (result != null) ? result : findClassIgnoringNotFound(name);
		}
		Class<?> result = findClassIgnoringNotFound(name);
		return (result != null) ? result : loadFromParent(name);
	}

	private Class<?> resolveIfNecessary(Class<?> resultClass, boolean resolve) {
		if (resolve) {
			resolveClass(resultClass);
		}
		return (resultClass);
	}

	@Override
	protected void addURL(URL url) {
		if (logger.isTraceEnabled()) {
			logger.trace("Ignoring request to add " + url + " to the tomcat classloader");
		}
	}

	private Class<?> loadFromParent(String name) {
		if (this.parent == null) {
			return null;
		}
		try {
			return Class.forName(name, false, this.parent);
		}
		catch (ClassNotFoundException ex) {
			return null;
		}
	}

	private Class<?> findClassIgnoringNotFound(String name) {
		try {
			return findClass(name);
		}
		catch (ClassNotFoundException ex) {
			return null;
		}
	}

}
