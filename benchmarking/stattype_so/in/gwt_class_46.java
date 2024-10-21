public class gwt_class_46 {
	public static void main(String arg[]) {
		VerticalPanel eventDetailWindow = new VerticalPanel();

		final DialogBox dialogBox2 = new DialogBox();
		dialogBox2.setText("Event Detail");
		dialogBox2.setAnimationEnabled(true);

		final HTML serverResponse3 = new HTML("<b> ok, this is working</b>");
		serverResponse3.addStyleName("detailView");

		eventDetailWindow.add(serverResponse3);
		eventDetailWindow.addStyleName("detailWindow");
		dialogBox2.setWidget(eventDetailWindow);
		RootPanel.get("detailWindow").add(eventDetailWindow);
	}

}
