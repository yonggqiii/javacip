public class gwt_class_41 extends Composite {

    public gwt_class_41(final String fileName) {
        final Element downloadIframe = RootPanel.get("__download").getElement();
        VerticalPanel content = new VerticalPanel();
        content.add(new Label("Download For this File : " + fileName));
        Button button = new Button("Download");
        button.addClickHandler(new A());
    }
}

class A implements ClickHandler {

    public void onClick(ClickEvent event) {
        // i do not know what to do here
    }
}
