public class gwt_class_50 extends ComplexPanel implements HasText, HasHTML, HasClickHandlers, HasKeyDownHandlers, HasBlurHandlers {

    HandlerRegistration clickHandler;

    public gwt_class_50() {
        JavaCIPUnknownScope.setElement(DOM.createElement("LI"));
    }

    public void add(Widget w) {
        super.add(w, JavaCIPUnknownScope.getElement());
    }

    public void insert(Widget w, int beforeIndex) {
        super.insert(w, JavaCIPUnknownScope.getElement(), beforeIndex, true);
    }

    public String getText() {
        return DOM.getInnerText(JavaCIPUnknownScope.getElement());
    }

    public void setText(String text) {
        DOM.setInnerText(JavaCIPUnknownScope.getElement(), (text == null) ? "" : text);
    }

    public void setId(String id) {
        DOM.setElementAttribute(JavaCIPUnknownScope.getElement(), "id", id);
    }

    public String getHTML() {
        return DOM.getInnerHTML(JavaCIPUnknownScope.getElement());
    }

    public void setHTML(String html) {
        DOM.setInnerHTML(JavaCIPUnknownScope.getElement(), (html == null) ? "" : html);
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return JavaCIPUnknownScope.addDomHandler(handler, ClickEvent.getType());
    }

    public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
        return JavaCIPUnknownScope.addDomHandler(handler, KeyDownEvent.getType());
    }

    public HandlerRegistration addBlurHandler(BlurHandler handler) {
        return JavaCIPUnknownScope.addDomHandler(handler, BlurEvent.getType());
    }
}
