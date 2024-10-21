public class gwt_class_24 {
}

abstract class AbstractNotificationWidget extends Composite implements ClickHandler, HasClickHandlers {

    protected abstract String getUniqueId();

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return JavaCIPUnknownScope.addDomHandler(handler, ClickEvent.getType());
    }

    public void onClick(ClickEvent event) {
        doClick(getUniqueId());
    }

    public abstract void doClick(String name);
}

class ErrorNotificationWidget extends AbstractNotificationWidget {

    private final String uniqueId;

    public ErrorNotificationWidget(String title, String message) {
        uniqueId = JavaCIPUnknownScope.DOM.createUniqueId();
        Widget w = null;
        JavaCIPUnknownScope.initWidget(w);
        this.getElement().setId(uniqueId);
        this.addClickHandler(this);
    }

    protected String getUniqueId() {
        return this.uniqueId;
    }

    public void doClick(String name) {
    }
}
