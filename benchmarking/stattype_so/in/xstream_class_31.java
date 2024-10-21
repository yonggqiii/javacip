public class xstream_class_31 {
	static String xml1 = "<Music><type>rock</type></Music>";

	public static void main(String[] args) {
		XStream xStream = new XStream();
		xStream.processAnnotations(Music.class);
		Music musicObject = (Music) xStream.fromXML(xml1);
		System.out.println(musicObject);
		// prints Music [type=ROCK]
	}

}

class Music {
	Genre type;

	@Override
	public String toString() {
		return "Music [type=" + type + "]";
	}

}
