class TitledPanel extends Widget {
  private Element legend;
  private Widget content = null;

  public TitledPanel() {
    Element fieldset = DOM.createFieldSet();
    legend = DOM.createLegend();
    DOM.appendChild(fieldset, legend);
    setElement(fieldset);
  }

  public TitledPanel(String title) {
    this();
    setTitle(title);
  }

  @Override
  public String getTitle() {
    return DOM.getInnerHTML(legend);
  }

  @Override
  public void setTitle(String html) {
    DOM.setInnerHTML(legend, html);
  }

  public Widget getContent() {
    return content;
  }

  public void setContent(Widget content) {
    if (this.content != null) {
      DOM.removeChild(getElement(), this.content.getElement());
    }

    this.content = content;

    DOM.appendChild(getElement(), content.getElement());
  }
}
