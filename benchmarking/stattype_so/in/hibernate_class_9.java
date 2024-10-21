public class hibernate_class_9 implements Serializable {
	private int patientId;
	private String patientName;
	private String providerId; // foreign key to provider

	private static final long serialVersionUID = 81073;

	public static void load(Session session, List<String> values) {
		hibernate_class_9 PatientInfo = new hibernate_class_9();

		PatientInfo.setPatientId(Integer.parseInt(values.get(0)));
		PatientInfo.setPatientName(values.get(1));
		PatientInfo.setProviderId(values.get(2));

		session.save(PatientInfo);
	}

	/**
	* @return the PatientId
	*/
	public int getPatientId() {
		return patientId;
	}

	/**
	* @param PatientId the PatientId to set
	*/
	public void setPatientId(int PatientId) {
		this.patientId = PatientId;
	}

	/**
	* @return the PatientName
	*/
	public String getPatientName() {
		return this.patientName;
	}

	/**
	* @param PatientName the PatientName to set
	*/
	public void setPatientName(String PatientName) {
		this.patientName = PatientName;
	}

	/**
	* @return the ProvId
	*/
	public String getProviderId() {
		return this.providerId;
	}

	/**
	* @param id the ProviderId to set
	*/
	public void setProviderId(String id) {
		this.providerId = id;
	}

	/**
	* @return the ProvId
	*/
	public String getProvider() {
		return this.providerId;
	}

	/**
	* @param id the ProviderId to set
	*/
	public void setProvider(String id) {
		this.providerId = id;
	}

	/**
	* @param args
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
