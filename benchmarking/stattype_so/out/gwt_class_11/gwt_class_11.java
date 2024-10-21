public class gwt_class_11 implements EntryPoint {

    HorizontalPanel dictionaryPanel;

    Label wordLabel;

    public gwt_class_11() {
        dictionaryPanel = new HorizontalPanel();
        wordLabel = new Label("Word");
    }

    public void onModuleLoad() {
        dictionaryPanel.add(wordLabel);
        RootPanel.get("dictionary").add(dictionaryPanel);
    }
}
