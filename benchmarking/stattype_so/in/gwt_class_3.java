public class gwt_class_3 implements EntryPoint {

	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server. Please check your network connection and try again.";

	// private final RPCCallsServiceAsync rpccallService = GWT.create(RPCCallsService.class);

	TextBox nameTxt = new TextBox();
	Button btnSave = getBtnSave();

	public void onModuleLoad() {

		RootPanel.get("inputName").add(nameTxt);
		RootPanel.get("btnSave").add(btnSave);
	}

	private Button getBtnSave() {

		Button btnSave = new Button("SAVE");

		return btnSave;
	}

	void saveData2DB(String name) {
		AsyncCallback<String> callback = new MyAsyncCallback<String>();
	}
}

class MyAsyncCallback<T> implements AsyncCallback<T> {
			public void onFailure(Throwable caught) {
				Window.alert("WOOOHOOO, ERROR: " + SERVER_ERROR);
				// TODO: Do something with errors.
			}

			public void onSuccess(String result) {
				Window.alert("Server is saying: ' " + result + "'");
			}

}
