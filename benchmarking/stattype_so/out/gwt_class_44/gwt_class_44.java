public class gwt_class_44 extends ResizeComposite {

    static gwt_class_44 instance = null;

    public static void getInstance(final AsyncCallback<gwt_class_44> callback) {
        Gwt.runAsync(new A());
    }

    gwt_class_44() {
        DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
        dockLayoutPanel.addNorth(new Label("North!"), 7);
        dockLayoutPanel.addWest(new Label("West!"), 15);
        dockLayoutPanel.add(new Label("Center! :D"));
        JavaCIPUnknownScope.initWidget(dockLayoutPanel);
    }
}

class A implements RunAsyncCallback {

    public void onSuccess() {
        if (gwt_class_44.instance == null) {
            gwt_class_44.instance = new gwt_class_44();
        }
        JavaCIPUnknownScope.callback.onSuccess(gwt_class_44.instance);
    }

    public void onFailure(Throwable reason) {
        JavaCIPUnknownScope.callback.onFailure(reason);
    }
}
