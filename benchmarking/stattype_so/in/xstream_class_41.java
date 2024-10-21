
public class xstream_class_41 {
	public static void main(String arg[]) {
		XStream xstream = new XStream(new StaxDriver());
		xstream.alias("data", DetList.class);
		xstream.alias("i", Details.class);
		String s = new String("<data>\n" + "\t<i Name='asia' type='continent' id='11'></i>\n"
				+ "\t<i Name='africa' type='continent' id='12'></i>\n"
				+ "\t<i Name='japan' type='country' id='13'></i>\n" + "</data>");
		System.out.println(s);
		DetList data = (DetList) xstream.fromXML(s);
	}


}

	class DetList {
		private List<Details> detlist;

		public List<Details> getDetlist() {
			return detlist;
		}

		public void setDetlist(List<Details> detlist) {
			this.detlist = detlist;
		}
	}

	class Details {

		private String Name;
		private String type;
		private String id;

		//Getters and Setters are here.
	}
