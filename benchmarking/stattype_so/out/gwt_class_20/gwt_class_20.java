public class gwt_class_20 implements EntryPoint {

    public void onModuleLoad() {
        Button b = new Button("Click me", new A());
        RootPanel.get().add(b);
    }
}

class A implements ClickHandler {

    public void onClick(ClickEvent event) {
        Window.alert("Hello, AJAX");
    }
}
