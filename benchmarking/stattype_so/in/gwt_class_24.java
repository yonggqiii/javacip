public class gwt_class_24 {


}

abstract class AbstractNotificationWidget extends Composite implements ClickHandler, HasClickHandlers {

  protected abstract String getUniqueId();

  @Override
  public HandlerRegistration addClickHandler(ClickHandler handler) {
    return addDomHandler(handler, ClickEvent.getType());
  }

  @Override
  public void onClick(ClickEvent event) {
    doClick(getUniqueId());
  }

  public abstract void doClick(String name) /*-{
                        $wnd.$("#" + name).click(function () {
                        $wnd.$(this).slideUp("slow");
                        $wnd.$("div", this).fadeOut("slow");
                        });
                        }-*/;

}

class ErrorNotificationWidget extends AbstractNotificationWidget {

  private final String uniqueId;

  public ErrorNotificationWidget(String title, String message) {
    uniqueId = DOM.createUniqueId();

    Widget w = null;
    initWidget(w);

    this.getElement().setId(uniqueId);

    this.addClickHandler(this);
  }

  @Override
  protected String getUniqueId() {
    return this.uniqueId;
  }

  @Override
  public void doClick(String name) {}
}
