public class gwt_class_47 implements EntryPoint {
	private static final Unit PCT = null;

	@Override
	public void onModuleLoad() {
		Widget childone = new HTML("left"), childtwo = new HTML("right");
		LayoutPanel p = new LayoutPanel();
		p.add(childone);
		p.add(childtwo);
		p.setWidgetLeftWidth(childone, 0, PCT, 50, PCT);
		p.setWidgetRightWidth(childtwo, 0, PCT, 50, PCT);
		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(p);
	}
}
