public class gwt_class_36 {
	public static void main(String arg[]) {
		int width = 200;
		int height = 200;
		int overHeight = 40;

		AbsolutePanel absPanel = new AbsolutePanel();
		absPanel.setSize(width + "px", height + "px");

		SimplePanel underPanel = new SimplePanel();
		underPanel.setWidth(width + "px");
		underPanel.setHeight(height + "px");
		underPanel.getElement().getStyle().setBackgroundColor("red");

		SimplePanel overPanel = new SimplePanel();
		overPanel.setWidth(width + "px");
		overPanel.setHeight(overHeight + "px");
		overPanel.getElement().getStyle().setBackgroundColor("black");
		//Setting the IE opacity to 20% on the black element in order to obtain the see through effect.
		overPanel.getElement().getStyle().setProperty("filter", "alpha(opacity=20)");

		absPanel.add(underPanel, 0, 0);
		absPanel.add(overPanel, 0, 0);

		RootPanel.get("test").add(absPanel);

		//The next line causes the problem. 
		absPanel.getElement().getStyle().setProperty("filter", "alpha(opacity=100)");
	}
}
