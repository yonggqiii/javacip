public class gwt_class_27 {

    public static void main(String[] arg) {
        final Button sendButton = new Button("Send");
        sendButton.getElement().setId("button");
        RootPanel.get().getElement().appendChild(sendButton.getElement());
        NodeList buttonElement = Document.get().getElementsByTagName("button");
        if (buttonElement != null && buttonElement.getLength() > 0) {
            buttonElement.getItem(0).setNodeValue("Changed");
        } else {
            Gwt.log("Can't select button");
        }
    }
}
