public class xstream_class_8 extends Activity {

    // our webservice method name
    private String METHOD_NAME = "getName";

    // Here package
    private String NAMESPACE = "http://webservice.app";

    // name in
    // webservice
    // with reverse
    // order.
    // NAMESPACE
    private String SOAP_ACTION = "http://webservice.app/getName";

    // +
    // method
    // name
    public TextView t;

    private static String URL = "http://ip/WS/services/Service?wsdl";

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize soap request + add parameters
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("user", "user");
        request.addProperty("passwort", "passwort");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        // Make the soap call.
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        // this is the actual part that will call the webservice
        // try {
        androidHttpTransport.call(SOAP_ACTION, envelope);
        SoapObject result = (SoapObject) envelope.bodyIn;
        // SoapObject result = (SoapObject) envelope.response();
        if (result != null) {
            XStream xstreams = new XStream(new DomDriver());
            String text = result.getProperty(0).toString();
        }
        // } catch (Exception e) {
        // TODO Auto-generated catch block
        t.setText("FEHLER");
        // }
    }
}
