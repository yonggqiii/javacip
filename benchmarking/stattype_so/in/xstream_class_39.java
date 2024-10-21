public class xstream_class_39 {

	private static XStream xstream = new XStream(new A());
}
class B extends PrettyPrintWriter {

  B(Writer w) { super(w); }

				// Add CDATA block  
				boolean cdata;//I need to add cdata for some fields only  
				boolean isNeedUpper;//only some will need this

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else if (isNeedUpper) {
						writer.write(text.toUpperCase());
					} else
						writer.write(text);
				}

}
class A extends XppDriver {
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new B(out);
		}
}
