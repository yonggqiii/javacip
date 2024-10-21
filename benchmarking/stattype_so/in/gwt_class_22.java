public class my_class extends Composite {

	private static gwt_class_22 uiBinder = Gwt.create(gwt_class_22.class);


	Label testObjectStringLabel;
	Label innerObjectStringLabel;
	VerticalPanel listObjectsPanel;
	Button button;
	Label errorMessageLabel;

	public my_class(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	void onClick(ClickEvent e) {

	}

}
	interface gwt_class_22 extends UiBinder<VerticalPanel, my_class> {
	}
