public class xstream_class_29 {
	private static void createXmlEmail() {
		XStream xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);
		xstream.alias("email", EmailPojo.class);
		xstream.alias("recipient", Recipient.class);

		EmailPojo ep = new EmailPojo();

		List<Recipient> toRecipient = new ArrayList<Recipient>();
		toRecipient.add(new Recipient("user1@somecompany.com"));
		toRecipient.add(new Recipient("user2@somecompany.com"));

		List<Recipient> ccRecipient = new ArrayList<Recipient>();
		ccRecipient.add(new Recipient("user3@somecompany.com"));
		ccRecipient.add(new Recipient("user4@somecompany.com"));

		List<Recipient> bccRecipient = new ArrayList<Recipient>();
		bccRecipient.add(new Recipient("user5@somecompany.com"));
		bccRecipient.add(new Recipient("user6@somecompany.com"));

		ep.setTo(toRecipient);
		ep.setCc(ccRecipient);
		ep.setBcc(bccRecipient);
		ep.setSubject("subject test");
		ep.setBody("body test");

		String xml = xstream.toXML(ep);
		System.out.println(xml);
	}

}

class EmailPojo {
  private List<Recipient> to;
  private List<Recipient> cc;
  private List<Recipient> bcc;
  private String subject;
  private String body;

  public List<Recipient> getTo() {
    return to;
  }

  public void setTo(List<Recipient> to) {
    this.to = to;
  }

  public List<Recipient> getCc() {
    return cc;
  }

  public void setCc(List<Recipient> cc) {
    this.cc = cc;
  }

  public List<Recipient> getBcc() {
    return bcc;
  }

  public void setBcc(List<Recipient> bcc) {
    this.bcc = bcc;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}

class Recipient {
  private String recipient;

  public Recipient(String recipient) {
    this.recipient = recipient;
  }

  public String getRecipient() {
    return recipient;
  }

  public void setRecipient(String recipient) {
    this.recipient = recipient;
  }
}
