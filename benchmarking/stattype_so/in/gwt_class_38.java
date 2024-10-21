public class gwt_class_38 extends Composite {

  private VerticalPanel vPanel;
  private Hyperlink registerUserLink;

  public gwt_class_38() {
    vPanel = new VerticalPanel();
    initWidget(vPanel);
  }

  public Widget asWidget() {
    return this;
  }

  public void addLink(String s) {

    registerUserLink = new Hyperlink(s, "this is new target");
    this.vPanel.add(registerUserLink);
    registerUserLink.setVisible(true);
  }

  public HasClickHandlers getRegisterUserLink() {
    return registerUserLink;
  }
}
