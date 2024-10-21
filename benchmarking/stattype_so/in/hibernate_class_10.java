public class hibernate_class_10 implements Serializable {
	private String providerId;
	private String providerName;

	//private int patientId;
	//private int providerSpec; 

	private static final long serialVersionUID = 81073;

	public static void load(Session session, List<String> values) {
		hibernate_class_10 ProviderInfo = new hibernate_class_10();

		ProviderInfo.setProviderId(values.get(0));
		ProviderInfo.setProviderName(values.get(1));
		//ProviderInfo.setProviderSpec( Integer.parseInt(values.get(2)) );

		session.save(ProviderInfo);
	}

	/**
	* @return the ProviderName
	*/
	public String getProviderName() {
		return providerName;
	}

	/**
	* @param ProviderName the ProviderName to set
	*/
	public void setProviderName(String name) {
		this.providerName = name;
	}

	/**
	* @return the ProvId
	*/
	public String getProviderId() {
		return this.providerId;
	}

	/**
	* @param id the ProvId to set
	*/
	public void setProviderId(String id) {
		this.providerId = id;
	}

	/*
	public int getPatientId() {
	    return this.patientId;
	}
	
	public void setPatientId( int id ) {
	    this.patientId = id;
	}
	*/

	/**
	* @param args
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
