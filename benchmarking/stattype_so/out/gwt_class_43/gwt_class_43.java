public class gwt_class_43 extends Composite implements ClickHandler, MouseOutHandler {

    private Label lbl;

    // private gwt_class_43Handler lblHandler = null;
    private HorizontalPanel hp;

    public void onClick(ClickEvent event) {
        hp.setStyleName("background-gwt_class_43");
        Object folder = null;
        if (event.getSource().equals(folder) || event.getSource().equals(lbl)) {
            // lblHandler.onGroupLabelSelect(this);
        }
    }

    public Widget getWidget() {
        return hp;
    }

    public void onMouseOut(MouseOutEvent event) {
        hp.removeStyleName("background-gwt_class_43");
    }
}
