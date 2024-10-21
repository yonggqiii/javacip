public class gwt_class_34 {

    public static void main(String[] arg) {
        FormPanel form = null;
        Button submit = null;
        form = FormPanel.wrap(JavaCIPUnknownScope.DOM.getElementById("MyForm"));
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        submit = Button.wrap(JavaCIPUnknownScope.DOM.getElementById("OK"));
        submit.addClickHandler(new B());
        HandlerRegistration formSubmitHandler = form.addSubmitHandler(new A());
    }
}

class A implements SubmitHandler {

    public void onClick(ClickEvent event) {
        // button clicked confirmed
    }
}

class B implements ClickHandler {

    public void onSubmit(SubmitEvent event) {
    }
}
