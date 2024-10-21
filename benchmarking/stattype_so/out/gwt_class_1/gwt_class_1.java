public class gwt_class_1 extends Composite implements MouseDownHandler {

    AbsolutePanel abs = new AbsolutePanel();

    public gwt_class_1(int width) {
        System.out.println("MousyAbsolutePanel being created with width:" + width);
        JavaCIPUnknownScope.initWidget(abs);
        abs.setWidth(String.valueOf(width));
        abs.setHeight("100%");
        abs.setStyleName("mousyAbsolutePanel");
    }

    public void onMouseDown(MouseDownEvent event) {
        System.out.println("onMouseDown()");
    }
}
