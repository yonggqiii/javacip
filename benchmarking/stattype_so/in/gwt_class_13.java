class ERD1 implements EntryPoint {

  public void onModuleLoad() {

    AbsolutePanel boundaryPanel = new AbsolutePanel();
    boundaryPanel.setPixelSize(1000, 1000);

    final Diagram d = new Diagram(boundaryPanel);

    Button b = new Button();
    b.addClickListener(new A());

    boundaryPanel.add(b, 10, 40);

    RootPanel.get().add(boundaryPanel);
  }
}

class A implements ClickListener {
      public void onClick1(Widget sender) {
      }

      @Override
      public void onClick(Widget sender) {
        // TODO Auto-generated method stub

      }
}
