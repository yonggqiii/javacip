public class gwt_class_21 extends PopupPanel {

    Button connect = new Button("Connect");

    Button delete = new Button("Delete");

    Button detail = new Button("Detail");

    public gwt_class_21(final Widget widget) {
        super(true);
        VerticalPanel vp = new VerticalPanel();
        vp.add(connect);
        vp.add(delete);
        vp.add(detail);
        JavaCIPUnknownScope.setWidget(vp);
    }
}
