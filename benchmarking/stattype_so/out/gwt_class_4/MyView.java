// ID = 1318732
class MyView extends Composite {

    private VerticalSplitPanel mainPanel = new VerticalSplitPanel();

    public void CountryFilterView() {
        mainPanel.setSize("100%", "100%");
        mainPanel.setSplitPosition("50%");
        // Add some content
        String randomText = "This is some text to show how the contents on either " + "side of the splitter flow.   " + "This is some text to show how the contents on either " + "side of the splitter flow.   " + "This is some text to show how the contents on either " + "side of the splitter flow.   ";
        mainPanel.setTopWidget(new HTML(randomText));
        mainPanel.setBottomWidget(new HTML(randomText));
        JavaCIPUnknownScope.initWidget(mainPanel);
    }
}
