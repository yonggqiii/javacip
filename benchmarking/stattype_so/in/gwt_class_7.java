class GWTMemoryIssue implements EntryPoint {
  //Run with or without table
  private static final boolean WITH_TABLE = false;

  //		private final TestServiceAsync rpcService = GWT.create(TestService.class);

  public static Panel panel;
  public static Timer timer;
  public static Table table;

  public void onModuleLoad() {
    RootPanel rootPanel = RootPanel.get();

    this.panel = new VerticalPanel();
    this.panel.setSize("100%", "100%");

    rootPanel.add(panel);

    if (WITH_TABLE) {
      loadTable();
    } else {
      startUpdateTask();
    }

  }

  static void startUpdateTask() {
    timer = new Timer();
    timer.scheduleRepeating(2000);
  }

  public static void loadTable() {
    Runnable onLoadCallback = new MyRunnable();
  }
}

class MyRunnable implements Runnable {
      public void run() {
        GWTMemoryIssue.table.setSize("100%", "100%");
        GWTMemoryIssue.panel.add(GWTMemoryIssue.table);
        GWTMemoryIssue.startUpdateTask();
      }

}
