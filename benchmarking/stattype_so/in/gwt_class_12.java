public class gwt_class_12 implements EntryPoint {
	public void onModuleLoad() {
		AbsolutePanel ap = new AbsolutePanel();
		ap.setSize("600px", "600px");
		RootPanel.get().add(ap, 10, 10);
		Label l = new Label("Example");
		ap.add(l);
		Label l1 = new Label("Label for VP");
		TextBox t1 = new TextBox();
		t1.setText("Hello");
		VerticalPanel vp = new VerticalPanel();
		vp.add(t1);
		vp.add(l1);
		ap.add(vp);

	}
}
