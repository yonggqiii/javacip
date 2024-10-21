import java.util.*;
public class xstream_class_34 extends ParameterHolder {
  private List<ResourceSettings> resources;

  private List<Environment> environments;

  public xstream_class_34() {
  }

  public List<Environment> getEnvironments() {
    if (environments == null)
      environments = new ArrayList<Environment>();
    return environments;
  }

  public void setEnvironments(List<Environment> environments) {
    this.environments = environments;
  }

  public void setEnvironment(Environment environment) {
    deleteEnvironment(environment.getName());
    getEnvironments().add(environment);
  }

  public Environment getEnvironment(String env) {
    for (Environment environment : getEnvironments()) {
      if (env.equals(environment.getName()))
        return environment;
    }
    return null;
  }

  public void deleteEnvironment(String name) {
    Iterator<Environment> it = getEnvironments().iterator();
    while (it.hasNext()) {
      if (it.next().getName().equals(name)) {
        it.remove();
      }
    }
  }

  public List<ResourceSettings> getResources() {
    if (resources == null)
      resources = new ArrayList<ResourceSettings>();
    return resources;
  }

  public void setResources(List<ResourceSettings> resources) {
    this.resources = resources;
  }

  public void setResource(ResourceSettings resource) {
    deleteResource(resource.getName());
    getResources().add(resource);
  }

  public ResourceSettings getResource(String name) {
    for (ResourceSettings resource : getResources()) {
      if (resource.getName().equals(name))
        return resource;
    }
    return null;
  }

  public void deleteResource(String name) {
    Iterator<ResourceSettings> it = getResources().iterator();
    while (it.hasNext()) {
      if (it.next().getName().equals(name)) {
        it.remove();
      }
    }
  }

  private XStream createXStream() {
    XStream xstream = new A();

    xstream.setClassLoader(xstream_class_34.class.getClassLoader());
    xstream.processAnnotations(ResourceSettings.class);
    xstream.processAnnotations(Environment.class);
    xstream.processAnnotations(xstream_class_34.class);
    xstream.processAnnotations(ParameterHolder.class);
    return xstream;
  }

  /**
   * Returns a string representation of XML. 
   */
  public String toXML() {
    return createXStream().toXML(this);
  }

  public xstream_class_34 parse(String xml) {
    if (xml == null)
      return new xstream_class_34();
    return (xstream_class_34) createXStream().fromXML(xml);
  }
}

class B extends MapperWrapper {
  B(MapperWrapper m) { super(m); }
  public boolean shouldSerializeMember(Class definedIn, String fieldName) {
    return definedIn != Object.class && super.shouldSerializeMember(definedIn, fieldName);
  }
}
class A extends XStream {
  protected MapperWrapper wrapMapper(MapperWrapper next) {
    return new B(next);      }
}
