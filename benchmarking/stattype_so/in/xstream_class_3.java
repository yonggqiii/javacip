public class xstream_class_3 {

	public static void main(String[] args) {
		JettisonMappedXmlDriver xmlDriver = new JettisonMappedXmlDriver();
		XStream xstream = new XStream(xmlDriver);

		MockMessage mock1 = new MockMessage();
		MockMessage mock2 = new MockMessage();
		MockMessageOther mock3 = new MockMessageOther();

		List messages = new ArrayList();
		messages.add(mock1);
		messages.add(mock2);
		messages.add(mock3);

		String jsonString = xstream.toXML(messages);

		//JSON list format is non-intuitive single element array with class name fields
		System.out.println(jsonString);
		List xstreamJSONUnmarshalledMessages = (List) xstream.fromXML(jsonString);
		//This will print 3 messages unmarshalled
		System.out.println(
				"XStream format JSON Number of messages unmarshalled: " + xstreamJSONUnmarshalledMessages.size());

		//Attempt to deserialize a reasonable looking JSON string
		String jsonTest = "{" + "\"list\" : [" + "{" + "\"MockMessage\" : {" + "\"val\" : 1" + "}" + "}, {"
				+ "\"MockMessage\" : {" + "\"val\" : 1" + "}" + "}, {" + "\"MockMessageOther\" : {" + "\"otherVal\" : 1"
				+ "}" + "} ]" + "};";

		List unmarshalledMessages = (List) xstream.fromXML(jsonTest);

		//We expect 3 messages but XStream only deserializes one
		System.out.println("Normal format JSON Number of messages unmarshalled: " + unmarshalledMessages.size());
	}

}

class MockMessage {
  int val = 1;
}

class MockMessageOther {
  int otherVal = 1;
}

