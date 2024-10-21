// Other methods omitted...
public interface gwt_class_17 extends ImageBundle {

    /**
     * Would match the file 'logo.jpg', 'logo.gif', or 'logo.png' located in the
     * same package as this type.
     */
    public AbstractImagePrototype logo();

    public void applyTo(Image picSquare);

    /**
     * Would match the file 'ajaxLoader.jpg', 'ajaxLoader.gif', or 'ajaxLoader.png' located in the
     * same package as this type.
     */
    public AbstractImagePrototype ajaxLoader();
}

class my_class extends Composite {

    // Data
    private String firstName = null;

    private String lastName = null;

    private String picSquareUrl = null;

    // Elements
    private Image picSquare = new Image();

    private Image logo = new Image();

    private Button logoutButton = new Button("Logout");

    private DockPanel panel = new DockPanel();

    private HTML html = new HTML("Welcome to Sandpit.");

    /**
     * Create a remote service proxy to talk to the server-side User Data
     * service.
     */
    // private final UserDataServiceAsync userDataService = GWT.create(UserDataService.class);
    public my_class() {
        // this.rpcWidget = new RPCWidget(this);
        this.initProfileImage();
        this.initLogoImage();
        panel.add(picSquare, DockPanel.WEST);
        panel.add(html, DockPanel.CENTER);
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(logo);
        verticalPanel.add(logoutButton);
        panel.add(verticalPanel, DockPanel.EAST);
        // panel.add(rpcWidget, DockPanel.SOUTH);
        JavaCIPUnknownScope.initWidget(panel);
    }

    private void initProfileImage() {
        // Display ajaxLoader.gif
        gwt_class_17 sib = GWT.create(gwt_class_17.class);
        AbstractImagePrototype aip = sib.ajaxLoader();
        sib.applyTo(this.picSquare);
    }

    private void initLogoImage() {
        // Display logo.jpg
        gwt_class_17 sib = GWT.create(gwt_class_17.class);
        AbstractImagePrototype aip = sib.logo();
        aip.applyTo(this.logo);
    }
}
