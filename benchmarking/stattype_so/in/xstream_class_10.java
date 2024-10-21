public class xstream_class_10 {
	private String generateResponse(final XStream xStream) {
		StringWriter writer = new StringWriter();
		xStream.marshal(this, new A(writer));
		return writer.toString();
	}
}
class A extends PrettyPrintWriter {

  A(StringWriter w) {
    super(w);
  }
  @Override
  public void addAttribute(final String key, final String value) {
    if (!key.equals("class")) {
      super.addAttribute(key, value);
    }
  }
}
