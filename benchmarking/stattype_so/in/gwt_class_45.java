public class gwt_class_45 implements EntryPoint {

	@Override
	public void onModuleLoad() {
		Label label = new Label("hello world");
		Button button = new Button("say something");
		button.addClickHandler(new A());

		RootPanel.get().add(label);
		RootPanel.get().add(button);

	}
}

class A implements ClickHandler {
    @Override
    public void onClick(ClickEvent event) {
      Window.alert("hello again");
    }
}
