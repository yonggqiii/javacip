public class gwt_class_30 {

    static TextBox firstName = new TextBox();

    static TextBox surName = new TextBox();

    static DialogBox box;

    static public DialogBox buildNewElecPopup() {
        DialogBox box = new DialogBox();
        box.setAutoHideEnabled(true);
        box.setText("Add a New Candidate");
        box.setAnimationEnabled(true);
        box.setGlassEnabled(true);
        Grid dialogGrid = new Grid(2, 3);
        dialogGrid.setPixelSize(250, 125);
        dialogGrid.setCellPadding(10);
        dialogGrid.setWidget(0, 0, new HTML("<strong>First Name</strong>"));
        dialogGrid.setWidget(0, 1, firstName);
        dialogGrid.setWidget(1, 0, new HTML("<strong>Surname</strong>"));
        dialogGrid.setWidget(1, 1, surName);
        box.add(dialogGrid);
        return box;
    }
}
